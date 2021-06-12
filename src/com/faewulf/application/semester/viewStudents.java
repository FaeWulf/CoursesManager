package com.faewulf.application.semester;

import Database.student;
import Database.subject;
import com.faewulf.application.allData;
import com.model.HpDB;
import com.model.subjectDB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;

public class viewStudents extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JScrollPane scrpane;
	private JTextField textField1;
	private JTextField search;

	public viewStudents(HpDB temp) {
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		subjectDB sub = subject.getSubject(temp.getSubjectId());
		String[] weekName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		textField1.setText("ID: " + sub.getId() + ", Name: " + sub.getName() +  ", Teacher ID: " + temp.getTeacherId() + ", Schedule: " + weekName[temp.getWeekDay()] + " " + allData.scheduleTimeList.get(temp.getTime() - 1));

		JTable table = student.toTable(student.getstudentListfromHP(temp.getId()));
		scrpane.setViewportView(table);

		search.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) table.getModel()));
				sorter.setRowFilter(RowFilter.regexFilter(search.getText()));
				table.setRowSorter(sorter);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(((DefaultTableModel) table.getModel()));
				sorter.setRowFilter(RowFilter.regexFilter(search.getText()));
				table.setRowSorter(sorter);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});


		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});

		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		});

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK() {
		// add your code here
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
