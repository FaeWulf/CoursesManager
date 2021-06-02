package Database;

import java.awt.*;
import java.util.List;

import com.HibernateUtil.HibernateUtil;
import com.faewulf.application.allData;
import com.model.accountDB;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

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

    public static JTable toTable() {
        JTable table = new JTable(new JTableButtonModel());
        TableCellRenderer tableRenderer = table.getDefaultRenderer(JButton.class);
        table.setDefaultRenderer(JButton.class, new JTableButtonRenderer(tableRenderer));
        table.getTableHeader().setReorderingAllowed(false);

        return table;
    }

}

class JTableButtonRenderer implements TableCellRenderer {
    private TableCellRenderer defaultRenderer;
    public JTableButtonRenderer(TableCellRenderer renderer){
        defaultRenderer = renderer;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value instanceof Component)
            return (Component) value;
        return defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}

class JTableButtonModel extends AbstractTableModel {
    private final List<accountDB> list = allData.accountList;
    private final String[] columns = {"Id", "Fullname","Username", "Password","Birth Day", "Birth Place", "Living Place", "Option"};
    private Object[][] result = new Object[list.size()][columns.length];

    public JTableButtonModel() {
        for(int i = 0; i < list.size(); i++) {
            result[i][0] = list.get(i).getId();
            result[i][1] = list.get(i).getFullName();
            result[i][2] = list.get(i).getUsername();
            result[i][3] = list.get(i).getPassword();
            result[i][4] = list.get(i).getBirth().toString();
            result[i][5] = list.get(i).getBirthPlace();
            result[i][6] = list.get(i).getLivePlace();
            result[i][7] = new JButton("Edit");
        }
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return result.length;
    }

    public int getColumnCount() {
        return columns.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return result[rowIndex][columnIndex];
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }
}
