package Database;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.DkhpDB;
import com.model.HpDB;
import com.model.subjectDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

public class dkhp {
	public static DkhpDB getdkhp(int id) {
		DkhpDB result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			result = session.get(DkhpDB.class, id);
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return result;
	}

	public static List<DkhpDB> getdkhpList() {
		List<DkhpDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT a FROM DkhpDB a";
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}

	public static boolean updatedkhp(DkhpDB obj) {
		Session session = HibernateUtil.getSessionFactory().openSession();
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

	public static boolean deletedkhp(DkhpDB acc) {
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
		allData.dkhpList.remove(acc);
		return true;
	}

	public static boolean createdkhp(DkhpDB acc) {
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
		allData.dkhpList.clear();
		allData.dkhpList = getdkhpList();
		return true;
	}


	public static List<HpDB> gethpListFromStudent(int StudentID, int KyId) {
		List<HpDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT a FROM HpDB a inner join DkhpDB d on a.id = d.hpId where (d.studentId = :studentID and a.kydkhpId = :kyID)";
			Query query = session.createQuery(hql);
			query.setParameter("studentID", StudentID);
			query.setParameter("kyID", KyId);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}

	public static JTable toTable(int StudentID, int kyID) {
		String[] columns = {"ID", "Subject name", "credit", "Classname", "Slot", "teacher ID", "Schedule Time", "Week day"};
		List<HpDB> list = gethpListFromStudent(StudentID, kyID);
		Object[][] result = new Object[list.size()][columns.length];
		String[] weekName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (int i = 0; i < list.size(); i++) {
			int finalI = i;
			subjectDB temp = allData.subjectList.stream().filter(E -> E.getId() == list.get(finalI).getSubjectId()).findFirst().get();
			result[i][0] = list.get(i).getId();
			result[i][1] = temp.getName();
			result[i][2] = temp.getCredit();
			result[i][3] = list.get(i).getClassName();
			result[i][4] = list.get(i).getSlot();
			result[i][5] = list.get(i).getTeacherId();
			result[i][6] = "#" +  allData.scheduleTimeList.get(list.get(i).getTime() - 1).index + " " + allData.scheduleTimeList.get(list.get(i).getTime() - 1).time;
			result[i][7] = weekName[list.get(i).getWeekDay()];
		}
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

	public static TableModel updateTable(int StudentID, int kyID) {
		String[] columns = {"ID", "Subject name", "credit", "Classname", "Slot", "teacher ID", "Schedule Time", "Week day"};
		List<HpDB> list = gethpListFromStudent(StudentID, kyID);
		Object[][] result = new Object[list.size()][columns.length];
		String[] weekName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		for (int i = 0; i < list.size(); i++) {
			int finalI = i;
			subjectDB temp = allData.subjectList.stream().filter(E -> E.getId() == list.get(finalI).getSubjectId()).findFirst().get();
			result[i][0] = list.get(i).getId();
			result[i][1] = temp.getName();
			result[i][2] = temp.getCredit();
			result[i][3] = list.get(i).getClassName();
			result[i][4] = list.get(i).getSlot();
			result[i][5] = list.get(i).getTeacherId();
			result[i][6] = "#" +  allData.scheduleTimeList.get(list.get(i).getTime() - 1).index + " " + allData.scheduleTimeList.get(list.get(i).getTime() - 1).time;
			result[i][7] = weekName[list.get(i).getWeekDay()];
		}
		TableModel model = new DefaultTableModel(result, columns);
		return model;
	}

}
