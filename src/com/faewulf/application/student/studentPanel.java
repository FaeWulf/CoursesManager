package com.faewulf.application.student;

import Database.clazz;
import Database.student;
import Database.studyAt;
import com.faewulf.application.allData;
import com.model.StudentDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class studentPanel {
	private JPanel panel1;
	private JPanel panelClass;
	private JScrollPane scrpaneStudent;
	private JScrollPane scrpaneClass;
	private JPanel panelStudent;
	private JButton addStudentToThisButton;
	private JButton removeFromThisClassButton;
	private JSplitPane splitpane;

	public JPanel newPanel() {
		JTable[] tableStudent = {studyAt.toTable("")};
		JTable[] tableClass = {clazz.toTable()};
		tableClass[0].setAutoCreateRowSorter(true);
		tableClass[0].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpaneClass.setViewportView(tableClass[0]);
		scrpaneStudent.setViewportView(tableStudent[0]);
		addStudentToThisButton.setVisible(false);
		removeFromThisClassButton.setVisible(false);
		removeFromThisClassButton.setEnabled(false);



		tableClass[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				int row = tableClass[0].getSelectedRow();
				if(row == -1){
					tableStudent[0].setModel(studyAt.updateTable(""));
					addStudentToThisButton.setVisible(false);
					removeFromThisClassButton.setVisible(false);
					return;
				}
				String classID = (String) tableClass[0].getValueAt(row, 0);
				tableStudent[0].setModel(studyAt.updateTable(classID));
				if(tableStudent[0].getModel().getRowCount() != 0){
					addStudentToThisButton.setVisible(true);
					removeFromThisClassButton.setVisible(true);
				}
				else {
					addStudentToThisButton.setVisible(false);
					removeFromThisClassButton.setVisible(false);
				}
			}
		});

		addStudentToThisButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int row = tableClass[0].getSelectedRow();
				String classID = (String) tableClass[0].getValueAt(row, 0);
				List<StudentDB> list = student.getStudentNotIn(classID);
				for (StudentDB studentDB : list) {
					System.out.println(studentDB.getName());
				}
			}
		});

		return panel1;
	}
}
