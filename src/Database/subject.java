package Database;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.HpDB;
import com.model.subjectDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class subject {

    public static subjectDB getSubject(int id) {
        subjectDB result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
           result = session.get(subjectDB.class, id);
        } catch (HibernateError ex) {
            System.err.println(ex);
        }
        return result;
    }

    public static List<subjectDB> getSubjectList() {
        List<subjectDB> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "SELECT ac FROM subjectDB ac";
            Query query = session.createQuery(hql);
            list = query.list();
        } catch (HibernateError ex) {
            System.err.println(ex);
        }
        return list;
    }

    public static boolean updateSubject(subjectDB obj) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if(getSubject(obj.getId()) == null)
            return false;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(obj);
            transaction.commit();
        } catch (HibernateError ex) {
            transaction.rollback();
            System.err.println(ex);
            return false;
        }
        return true;
    }

    public static boolean deleteSubject(subjectDB acc) {
        if(getSubject(acc.getId()) == null)
            return false;

        List<HpDB> temp = new ArrayList<>();
        for (HpDB hpDB : allData.hpList) {
            if(hpDB.getSubjectId() == acc.getId())
                temp.add(hpDB);
        }

        for (HpDB hpDB : temp) {
            hp.deletehp(hpDB);
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<HpDB> list = new ArrayList<>();
        try {
            String hql = "SELECT a FROM HpDB a where  a.subjectId = :sId";
            Query query = session.createQuery(hql);
            query.setParameter("sId", acc.getId());
            list = query.list();
        } catch (HibernateError ex) {
            System.err.println(ex);
        }

        for (HpDB hpDB : list) {
        	hp.deletehp(hpDB);
        }

        try {
            transaction = session.beginTransaction();
            session.delete(acc);
            transaction.commit();
        } catch (HibernateError ex) {
            transaction.rollback();
            System.err.println(ex);
            return false;
        }
        allData.subjectList.remove(acc);
        return true;
    }

    public static boolean createSubject(subjectDB acc) {
        if(getSubject(acc.getId()) != null)
            return false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(acc);
            transaction.commit();
        } catch (HibernateError ex) {
            transaction.rollback();
            System.err.println(ex);
            return false;
        }
        allData.subjectList.clear();
        allData.subjectList = getSubjectList();
        return true;
    }

    public static JTable toTable() {
        String[] columns = {"Id","Name", "Credit"};
        Object[][] result = new Object[allData.subjectList.size()][columns.length];

        for(int i = 0; i < allData.subjectList.size(); i++) {
            result[i][0] = allData.subjectList.get(i).getId();
            result[i][1] = allData.subjectList.get(i).getName();
            result[i][2] = allData.subjectList.get(i).getCredit();
        };
        TableModel model = new DefaultTableModel(result, columns);
        JTable table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        return table;
    }

    public static TableModel modelUpdate(){
        String[] columns = {"Id","Name", "Credit"};
        Object[][] result = new Object[allData.subjectList.size()][columns.length];

        for(int i = 0; i < allData.subjectList.size(); i++) {
            result[i][0] = allData.subjectList.get(i).getId();
            result[i][1] = allData.subjectList.get(i).getName();
            result[i][2] = allData.subjectList.get(i).getCredit();
        };
        return new DefaultTableModel(result, columns);
    }
    public static TableModel modelUpdate(String searchString){
        String[] columns = {"Id","Name", "Credit"};
        int resultSize = 0;
        for(int i = 0; i < allData.subjectList.size(); i++)
            if(String.valueOf(allData.subjectList.get(i).getId()).equals(searchString))
                resultSize++;

        Object[][] result = new Object[resultSize][columns.length];
        int index = 0;
        for(int i = 0; i < allData.subjectList.size(); i++) {
            if(searchString.length() != 0 && !String.valueOf(allData.subjectList.get(i).getId()).equals(searchString))
                continue;
            result[index][0] = allData.subjectList.get(i).getId();
            result[index][1] = allData.subjectList.get(i).getName();
            result[index][2] = allData.subjectList.get(i).getCredit();
        };
        return new DefaultTableModel(result, columns);
    }
}
