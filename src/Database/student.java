package Database;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.StudentDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

public class student {
	public static StudentDB getStudent(int id) {
		StudentDB result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			result = session.get(StudentDB.class, id);
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return result;
	}

	public static List<StudentDB> getstudentList() {
		List<StudentDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT ac FROM StudentDB ac";
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}

	public static boolean updateStudent(StudentDB obj) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		if(getStudent(obj.getId()) == null)
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

	public static boolean deleteStudent(StudentDB acc) {
		if(getStudent(acc.getId()) == null)
			return false;
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
		allData.studentList.remove(acc);
		return true;
	}

	public static boolean createStudent(StudentDB acc) {
		if(getStudent(acc.getId()) != null)
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
		allData.studentList.add(acc);
		return true;
	}

	public static JTable toTable() {
		String[] columns = {"Student ID","Name", "Birthday", "Sex", "Birth place"};
		Object[][] result = new Object[allData.studentList.size()][columns.length];

		for(int i = 0; i < allData.studentList.size(); i++) {
			result[i][0] = allData.studentList.get(i).getId();
			result[i][1] = allData.studentList.get(i).getName();
			result[i][2] = allData.studentList.get(i).getBirthday();
			result[i][3] = allData.studentList.get(i).getSex();
			result[i][4] = allData.studentList.get(i).getBirthPlace();
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
		String[] columns = {"Student ID","Name", "Birthday", "Sex", "Birth place"};
		Object[][] result = new Object[allData.studentList.size()][columns.length];

		for(int i = 0; i < allData.studentList.size(); i++) {
			result[i][0] = allData.studentList.get(i).getId();
			result[i][1] = allData.studentList.get(i).getName();
			result[i][2] = allData.studentList.get(i).getBirthday();
			result[i][3] = allData.studentList.get(i).getSex();
			result[i][4] = allData.studentList.get(i).getBirthPlace();
		};
		return new DefaultTableModel(result, columns);
	}
	public static TableModel modelUpdate(String searchString){
		String[] columns = {"Student ID","Name", "Birthday", "Sex", "Birth place"};
		int resultSize = 0;
		for(int i = 0; i < allData.studentList.size(); i++)
			if(String.valueOf(allData.studentList.get(i).getId()).equals(searchString))
				resultSize++;

		Object[][] result = new Object[resultSize][columns.length];
		int index = 0;
		for(int i = 0; i < allData.studentList.size(); i++) {
			if(searchString.length() != 0 && !String.valueOf(allData.studentList.get(i).getId()).equals(searchString))
				continue;
			result[index][0] = allData.studentList.get(i).getId();
			result[index][1] = allData.studentList.get(i).getName();
			result[index][2] = allData.studentList.get(i).getBirthday();
			result[index][3] = allData.studentList.get(i).getSex();
			result[index][4] = allData.studentList.get(i).getBirthPlace();
		};
		return new DefaultTableModel(result, columns);
	}

	public static List<StudentDB> getStudentNotIn(String clazz) {
		List<StudentDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT at FROM StudyatDB at join fetch at.studentId where ";
			Query query = session.createQuery(hql);
			query.setParameter("clazz_id", clazz);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}

}
