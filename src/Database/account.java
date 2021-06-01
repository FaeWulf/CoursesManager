package Database;

import java.util.Arrays;
import java.util.List;

import com.HibernateUtil.HibernateUtil;
import com.model.accountDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class account {
    public static List<accountDB> getAccountList() {
        List<accountDB> list = null;
        new HibernateUtil();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "SELECT ac FROM accountDB ac";
            Query query = session.createQuery(hql);
            list = query.list();
        }
        catch (HibernateError ex){
            System.err.println(ex);
        }
        return list;
    }

    public static boolean updateAccount(accountDB _old, accountDB _new){
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
        }
        catch (HibernateError ex){
            session.getTransaction().rollback();
            System.err.println(ex);
            return false;
        }
        return true;
    }

    public static Object[][] getObjectList() {
        List<accountDB> list = getAccountList();

        Object[][] result = new Object[list.size()][7];

        for(int i = 0; i < list.size(); i++) {
            result[i][0] = list.get(i).getId();
            result[i][1] = list.get(i).getFullName();
            result[i][2] = list.get(i).getUsername();
            result[i][3] = list.get(i).getPassword();
            result[i][4] = list.get(i).getBirth().toString();
            result[i][5] = list.get(i).getBirthPlace();
            result[i][6] = list.get(i).getLivePlace();
        }

        return result;
    }
}
