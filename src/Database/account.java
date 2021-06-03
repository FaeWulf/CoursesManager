package Database;

import java.util.List;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.accountDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class account {
    public static List<accountDB> getAccountList() {
        List<accountDB> list = null;
        new HibernateUtil();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "SELECT ac FROM accountDB ac";
            Query query = session.createQuery(hql);
            list = query.list();
        } catch (HibernateError ex) {
            System.err.println(ex);
        }
        return list;
    }

    public static boolean updateAccount(accountDB _old, accountDB _new) {
        List<accountDB> list = null;
        new HibernateUtil();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "UPDATE accountDB SET username = :username, password = :password, fullName = :fullName,  birth = :birth, accountType = :accountType, birthPlace = :birthPlace, livePlace = :livePlace WHERE id = :id";
            session.getTransaction().begin();
            Query query = session.createQuery(hql);
            query.setParameter("username", _new.getUsername());
            query.setParameter("password", _new.getPassword());
            query.setParameter("fullName", _new.getFullName());
            query.setParameter("birth", _new.getBirth());
            query.setParameter("accountType", _new.getAccountType());
            query.setParameter("birthPlace", _new.getBirthPlace());
            query.setParameter("livePlace", _new.getLivePlace());
            query.setParameter("id", _old.getId());
            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateError ex) {
            session.getTransaction().rollback();
            System.err.println(ex);
            return false;
        }
        return true;
    }

    public static boolean deleteAccount(accountDB acc) {
        List<accountDB> list = null;
        new HibernateUtil();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "DELETE FROM accountDB a WHERE a.id = :id";
            session.getTransaction().begin();
            Query query = session.createQuery(hql);
            query.setParameter("id", acc.getId());
            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateError ex) {
            session.getTransaction().rollback();
            System.err.println(ex);
            return false;
        }
        return true;
    }

    public static JTable toTable() {
        String[] columns = {"Id", "Fullname","Username", "Password","Birth Day", "Birth Place", "Living Place"};
        Object[][] result = new Object[allData.accountList.size()][columns.length];

        for(int i = 0; i < allData.accountList.size(); i++) {
            result[i][0] = allData.accountList.get(i).getId();
            result[i][1] = allData.accountList.get(i).getFullName();
            result[i][2] = allData.accountList.get(i).getUsername();
            result[i][3] = allData.accountList.get(i).getPassword();
            result[i][4] = allData.accountList.get(i).getBirth().toString();
            result[i][5] = allData.accountList.get(i).getBirthPlace();
            result[i][6] = allData.accountList.get(i).getLivePlace();
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
        String[] columns = {"Id", "Fullname","Username", "Password","Birth Day", "Birth Place", "Living Place"};
        Object[][] result = new Object[allData.accountList.size()][columns.length];

        for(int i = 0; i < allData.accountList.size(); i++) {
            result[i][0] = allData.accountList.get(i).getId();
            result[i][1] = allData.accountList.get(i).getFullName();
            result[i][2] = allData.accountList.get(i).getUsername();
            result[i][3] = allData.accountList.get(i).getPassword();
            result[i][4] = allData.accountList.get(i).getBirth().toString();
            result[i][5] = allData.accountList.get(i).getBirthPlace();
            result[i][6] = allData.accountList.get(i).getLivePlace();
        };
        return new DefaultTableModel(result, columns);
    }
}

