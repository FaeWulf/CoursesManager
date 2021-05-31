package Database;

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
}
