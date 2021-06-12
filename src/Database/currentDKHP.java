package Database;
import com.HibernateUtil.HibernateUtil;
import com.model.CurrentDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.plaf.ColorUIResource;
import java.util.ArrayList;
import java.util.List;

public class currentDKHP {
	public static CurrentDB getCurrent(String id) {
		CurrentDB result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			result = session.get(CurrentDB.class, id);
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return result;
	}

	public static boolean deleteCurrent(CurrentDB acc) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(acc);
			transaction.commit();
		} catch (HibernateError ex) {
			transaction.rollback();
			System.err.println(ex);
			return false;
		}
		return true;
	}

	public static boolean createCurrent(CurrentDB acc) {
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
		return true;
	}
	public static boolean updateCurrent(CurrentDB acc) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "delete FROM CurrentDB c";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			session.save(acc);
			transaction.commit();
		} catch (HibernateError ex) {
			transaction.rollback();
			System.err.println(ex);
			return false;
		}
		return true;
	}
	public static CurrentDB getCurrentKy() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<CurrentDB> temp = new ArrayList<>();
		try {
			String hql = "select c from CurrentDB c";
			Query query = session.createQuery(hql);
			temp = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return temp.get(0);
	}
}
