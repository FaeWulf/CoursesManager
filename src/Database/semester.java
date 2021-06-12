package Database;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.*;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class semester {
    public static semesterDB getSemester(String id, int year) {
        semesterDB result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            result = session.get(semesterDB.class, new semesterDB(id, year));
        } catch (HibernateError ex) {
            System.err.println(ex);
        }
        return result;
    }

    public static List<semesterDB> getSemesterList() {
        List<semesterDB> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "SELECT ac FROM semesterDB ac";
            Query query = session.createQuery(hql);
            list = query.list();
        } catch (HibernateError ex) {
            System.err.println(ex);
        }
        return list;
    }

    public static boolean updateSemester(semesterDB obj, semesterDB obj2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if(getSemester(obj2.getId(), obj2.getYear()) == null)
            return  false;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(obj);
            session.delete(obj2);
            transaction.commit();
        } catch (HibernateError ex) {
            transaction.rollback();
            System.err.println(ex);
            return false;
        }
        return true;
    }

    public static boolean deleteSemester(semesterDB acc) {
        if(getSemester(acc.getId(), acc.getYear()) == null)
            return false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        List<KydkhpDB> temp = new ArrayList<>();

        for (KydkhpDB kydkhpDB : allData.kydkhpList) {
            if(kydkhpDB.getSemesterId().equals(acc.getId()) && kydkhpDB.getYear() == acc.getYear())
            	temp.add(kydkhpDB);
        }

        for (KydkhpDB kydkhpDB : temp) {
            kydkhp.deleteKydkhp(kydkhpDB);
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
        allData.semesterList.remove(acc);
        return true;
    }

    public static boolean createSemester(semesterDB acc) {
        if(getSemester(acc.getId(), acc.getYear()) != null)
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
        allData.semesterList.clear();
        allData.semesterList = getSemesterList();
        return true;
    }

    public static JTable toTable() {
        String[] columns = {"Id","Year", "Date Start", "Date End"};
        Object[][] result = new Object[allData.semesterList.size()][columns.length];

        for(int i = 0; i < allData.semesterList.size(); i++) {
            result[i][0] = allData.semesterList.get(i).getId();
            result[i][1] = allData.semesterList.get(i).getYear();
            result[i][2] = allData.semesterList.get(i).getStart();
            result[i][3] = allData.semesterList.get(i).getEnd();
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
        String[] columns = {"Id","Year", "Date Start", "Date End"};
        Object[][] result = new Object[allData.semesterList.size()][columns.length];

        for(int i = 0; i < allData.semesterList.size(); i++) {
            result[i][0] = allData.semesterList.get(i).getId();
            result[i][1] = allData.semesterList.get(i).getYear();
            result[i][2] = allData.semesterList.get(i).getStart();
            result[i][3] = allData.semesterList.get(i).getEnd();
        };
        return new DefaultTableModel(result, columns);
    }
    public static TableModel modelUpdate(String searchString){
        String[] columns = {"Id","Year", "Date Start", "Date End"};
        int resultSize = 0;
        for(int i = 0; i < allData.semesterList.size(); i++)
            if(String.valueOf(allData.semesterList.get(i).getId()).equals(searchString))
                resultSize++;

        Object[][] result = new Object[resultSize][columns.length];
        int index = 0;
        for(int i = 0; i < allData.semesterList.size(); i++) {
            if(searchString.length() != 0 && !String.valueOf(allData.semesterList.get(i).getId()).equals(searchString))
                continue;
            result[index][0] = allData.semesterList.get(i).getId();
            result[index][1] = allData.semesterList.get(i).getYear();
            result[index][2] = allData.semesterList.get(i).getStart();
            result[index][3] = allData.semesterList.get(i).getEnd();
        };
        return new DefaultTableModel(result, columns);
    }
}
