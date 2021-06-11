package Database;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.KydkhpDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class kydkhp {
	public static KydkhpDB getKydkhp(String id) {
		KydkhpDB result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			result = session.get(KydkhpDB.class, id);
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return result;
	}

	public static List<KydkhpDB> getKydkhpList() {
		List<KydkhpDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT a FROM KydkhpDB a";
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}

	public static boolean updateKydkhp(KydkhpDB obj) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		if (getKydkhp(obj.getSemesterId()) == null)
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

	public static boolean deleteKydkhp(KydkhpDB acc) {
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
		allData.kydkhpList.remove(acc);
		return true;
	}

	public static boolean createKydkhp(KydkhpDB acc) {
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
		allData.kydkhpList.clear();
		allData.kydkhpList = getKydkhpList();
		return true;
	}

	public static JTable toTable() {
		String[] columns = {"#","Semester ID", "Year", "Day Start", "Day End"};
		List<KydkhpDB> list = allData.kydkhpList;
		Object[][] result = new Object[list.size()][columns.length];

		for (int i = 0; i < list.size(); i++) {
			result[i][0] = list.get(i).getId();
			result[i][1] = list.get(i).getSemesterId();
			result[i][2] = list.get(i).getYear();
			result[i][3] = list.get(i).getStart();
			result[i][4] = list.get(i).getEnd();
		}
		;
		TableModel model = new DefaultTableModel(result, columns);
		JTable table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
		table.getTableHeader().setReorderingAllowed(false);
		return table;
	}

	public static TableModel modelUpdate() {
		String[] columns = {"#","Semester ID", "Year", "Day Start", "Day End"};
		List<KydkhpDB> list = allData.kydkhpList;
		Object[][] result = new Object[list.size()][columns.length];

		for (int i = 0; i < list.size(); i++) {
			result[i][0] = list.get(i).getId();
			result[i][1] = list.get(i).getSemesterId();
			result[i][2] = list.get(i).getYear();
			result[i][3] = list.get(i).getStart();
			result[i][4] = list.get(i).getEnd();
		}
		;
		return new DefaultTableModel(result, columns);
	}
}