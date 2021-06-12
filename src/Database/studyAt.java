package Database;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.StudentDB;
import com.model.StudyatDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

public class studyAt {
	public static StudyatDB getStudyAt(String id) {
		StudyatDB result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			result = session.get(StudyatDB.class, id);
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return result;
	}

	public static List<StudyatDB> getStudyAtList() {
		List<StudyatDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT ac FROM StudyatDB ac";
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}

	public static boolean deleteStudyAt(StudyatDB acc) {
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
		allData.studyAtList.remove(acc);
		return true;
	}



	public static boolean createStudyAt(StudyatDB acc) {
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
		allData.studyAtList.clear();
		allData.studyAtList = getStudyAtList();
		return true;
	}

	public static JTable toTable(String classId) {
		String[] columns = {"Student ID","Name", "Birthday", "Sex", "Birth place"};
		int number = 0;
		for (StudyatDB studyatDB : allData.studyAtList) {
			if(studyatDB.getClassId().equals(classId)){
				number++;
			}
		}
		Object[][] result = new Object[number][columns.length];

		int index = 0;
		for (StudyatDB studyatDB : allData.studyAtList) {

			if(studyatDB.getClassId().equals(classId)) {
				StudentDB temp =  allData.studentList.stream().filter(E -> E.getId() == studyatDB.getStudentId()).findFirst().get();
				result[index][0] = temp.getMssv();
				result[index][1] = temp.getName();
				result[index][2] = temp.getBirthday();
				result[index][3] = temp.getSex() == 1 ? "Male" : "Female";
				result[index++][4] = temp.getBirthPlace();
			}
		}

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

	public static TableModel updateTable(String classId) {
		String[] columns = {"Student ID","Name", "Birthday", "Sex", "Birth place"};
		int number = 0;
		for (StudyatDB studyatDB : allData.studyAtList) {
			if(studyatDB.getClassId().equals(classId)){
				number++;
			}
		}
		Object[][] result = new Object[number][columns.length];

		int index = 0;
		for (StudyatDB studyatDB : allData.studyAtList) {

			if(studyatDB.getClassId().equals(classId)) {
				StudentDB temp =  allData.studentList.stream().filter(E -> E.getId() == studyatDB.getStudentId()).findFirst().get();
				result[index][0] = temp.getMssv();
				result[index][1] = temp.getName();
				result[index][2] = temp.getBirthday();
				result[index][3] = temp.getSex() == 1 ? "Male" : "Female";
				result[index++][4] = temp.getBirthPlace();
			}
		}

		return new DefaultTableModel(result, columns);
	}

	public static List<StudyatDB> getAllFromClass(String clazz){
		List<StudyatDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT ac FROM StudyatDB ac where ac.classId = :clazzid";
			Query query = session.createQuery(hql);
			query.setParameter("clazzid", clazz);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}
	public static List<StudyatDB> getAllFromStudent(int id){
		List<StudyatDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT ac FROM StudyatDB ac where ac.studentId = :clazzid";
			Query query = session.createQuery(hql);
			query.setParameter("clazzid", id);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}
}
