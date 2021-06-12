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
import java.util.ArrayList;
import java.util.List;

public class hp {
	public static HpDB gethp(int id) {
		HpDB result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			result = session.get(HpDB.class, id);
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return result;
	}

	public static List<HpDB> getHpList() {
		List<HpDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT a FROM HpDB a";
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}

	public static List<HpDB> getHp4Student(int StudentID, int kyID) {
		List<HpDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT hp FROM HpDB hp where hp.id not in (select hp2.id from HpDB hp2 inner join DkhpDB d on d.hpId = hp2.id where d.studentId = :studentID and hp2.kydkhpId = :kyID) and hp.kydkhpId = :kyID";
			Query query = session.createQuery(hql);
			query.setParameter("studentID", StudentID);
			query.setParameter("kyID", kyID);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}

		return list;
	}

	public static List<HpDB> getHp4Table(int kyDKHP_ID) {
		List<HpDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT a FROM HpDB a";
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}

		List<HpDB> result = new ArrayList<>();

		for (HpDB hpDB : list) {
			if(hpDB.getKydkhpId() == kyDKHP_ID){
				result.add(hpDB);
			}
		}

		return result;
	}

	public static boolean updatehp(HpDB obj) {
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

	public static boolean deletehp(HpDB acc) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<DkhpDB> temp = new ArrayList<>();
		for (DkhpDB dkhpDB : allData.dkhpList) {
			if(dkhpDB.getHpId() == acc.getId())
				temp.add(dkhpDB);
		}

		for (DkhpDB dkhpDB : temp) {
			dkhp.deletedkhp(dkhpDB);
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
		allData.hpList.remove(acc);
		return true;
	}

	public static boolean createhp(HpDB acc) {
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
		allData.hpList.clear();
		allData.hpList = getHpList();
		return true;
	}

	public static JTable toTable(int kyID) {
		String[] columns = {"ID", "Subject name", "credit", "Classname", "Slot", "teacher ID", "Schedule Time", "Week day"};
		List<HpDB> list = getHp4Table(kyID);
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

	public static TableModel modelUpdate(int kyID) {
		String[] columns = {"ID", "Subject name", "credit", "Classname", "Slot", "teacher ID", "Schedule Time", "Week day"};
		List<HpDB> list = getHp4Table(kyID);
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
		return new DefaultTableModel(result, columns);
	}

	public static List<subjectDB> toListNotIn(int kyID) {
		List<subjectDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT s FROM subjectDB s left outer join HpDB hp on hp.subjectId = s.id where s.id not in (select s1 from subjectDB s1 left join HpDB h1 on h1.subjectId = s1.id where h1.kydkhpId = :kyID)";
			Query query = session.createQuery(hql);
			query.setParameter("kyID", kyID);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}

		return list;
	}

	public static List<HpDB> toListIn(int kyID) {
		List<HpDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT hp FROM  HpDB hp  where hp.kydkhpId = :kyID";
			Query query = session.createQuery(hql);
			query.setParameter("kyID", kyID);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}

		return list;
	}

	public static JTable toTableStudent(int StudentID, int kyID) {
		String[] columns = {"ID", "Subject name", "credit", "Classname", "Slot", "teacher ID", "Schedule Time", "Week day"};
		List<HpDB> list = getHp4Student(StudentID, kyID);
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

	public static TableModel modelUpdateStudent(int StudentID, int kyID) {
		String[] columns = {"ID", "Subject name", "credit", "Classname", "Slot", "teacher ID", "Schedule Time", "Week day"};
		List<HpDB> list = getHp4Student(StudentID, kyID);
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
		return new DefaultTableModel(result, columns);
	}
}
