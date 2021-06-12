package Database;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.StudyatDB;
import com.model.clazzDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class clazz {
	public static clazzDB getClazz(String id) {
		clazzDB result = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			result = session.get(clazzDB.class, id);
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return result;
	}

	public static List<clazzDB> getClazzList() {
		List<clazzDB> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			String hql = "SELECT ac FROM clazzDB ac";
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (HibernateError ex) {
			System.err.println(ex);
		}
		return list;
	}

	public static boolean updateClazz(clazzDB obj) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		if(getClazz(obj.getId()) == null)
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

	public static boolean deleteClazz(clazzDB acc) {
		if(getClazz(acc.getId()) == null)
			return false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;


		List<StudyatDB> temp = new ArrayList<>();
		for (StudyatDB studyatDB : allData.studyAtList) {
			if(studyatDB.getClassId().equals(acc.getId())){
				temp.add(studyatDB);
			}
		}
		for (StudyatDB studyatDB : temp) {
			studyAt.deleteStudyAt(studyatDB);
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

		allData.clazzList.remove(acc);
		return true;
	}

	public static boolean createClazz(clazzDB acc) {
		if(getClazz(acc.getId()) != null)
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
		allData.clazzList.clear();
		allData.clazzList = getClazzList();
		return true;
	}

	public static JTable toTable() {
		String[] columns = {"Id","Year", "Number of Students", "Female", "Male"};
		Object[][] result = new Object[allData.clazzList.size()][columns.length];

		for(int i = 0; i < allData.clazzList.size(); i++) {
			result[i][0] = allData.clazzList.get(i).getId();
			result[i][1] = allData.clazzList.get(i).getYear();
			result[i][2] = allData.clazzList.get(i).getSize();
			result[i][3] = allData.clazzList.get(i).getFemale();
			result[i][4] = allData.clazzList.get(i).getMale();
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
		String[] columns = {"Id","Year", "Number of Students", "Female", "Male"};
		Object[][] result = new Object[allData.clazzList.size()][columns.length];

		for(int i = 0; i < allData.clazzList.size(); i++) {
			result[i][0] = allData.clazzList.get(i).getId();
			result[i][1] = allData.clazzList.get(i).getYear();
			result[i][2] = allData.clazzList.get(i).getSize();
			result[i][3] = allData.clazzList.get(i).getFemale();
			result[i][4] = allData.clazzList.get(i).getMale();
		};
		return new DefaultTableModel(result, columns);
	}
	public static TableModel modelUpdate(String searchString){
		String[] columns = {"Id","Year", "Number of Students", "Female", "Male"};
		int resultSize = 0;
		for(int i = 0; i < allData.clazzList.size(); i++)
			if(String.valueOf(allData.clazzList.get(i).getId()).equals(searchString))
				resultSize++;

		Object[][] result = new Object[resultSize][columns.length];
		int index = 0;
		for(int i = 0; i < allData.clazzList.size(); i++) {
			if(searchString.length() != 0 && !String.valueOf(allData.clazzList.get(i).getId()).equals(searchString))
				continue;
			result[index][0] = allData.clazzList.get(i).getId();
			result[index][1] = allData.clazzList.get(i).getYear();
			result[index][2] = allData.clazzList.get(i).getSize();
			result[index][3] = allData.clazzList.get(i).getFemale();
			result[index][4] = allData.clazzList.get(i).getMale();
		};
		return new DefaultTableModel(result, columns);
	}
}

