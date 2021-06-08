package com.faewulf.application.student;

import Database.clazz;
import Database.student;
import Database.studyAt;
import com.faewulf.application.Util.doubleCheck;
import com.faewulf.application.allData;
import com.model.StudentDB;
import com.model.StudyatDB;
import com.model.clazzDB;

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
	private JButton createNewClassButton;
	private JButton deleteClassButton;

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
		deleteClassButton.setVisible(false);



		tableClass[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				int row = tableClass[0].getSelectedRow();
				if(row == -1){
					tableStudent[0].setModel(studyAt.updateTable(""));
					addStudentToThisButton.setVisible(false);
					removeFromThisClassButton.setVisible(false);
					deleteClassButton.setVisible(false);
					return;
				}
				else{
					deleteClassButton.setVisible(true);
					addStudentToThisButton.setVisible(true);
				}
				String classID = (String) tableClass[0].getValueAt(row, 0);
				tableStudent[0].setModel(studyAt.updateTable(classID));
				if(tableStudent[0].getModel().getRowCount() != 0){
					removeFromThisClassButton.setVisible(true);
				}
				else {
					removeFromThisClassButton.setVisible(false);
				}
			}
		});

		tableStudent[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int[] row = tableStudent[0].getSelectedRows();
				removeFromThisClassButton.setEnabled(row.length > 0);
			}
		});

		addStudentToThisButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int row = tableClass[0].getSelectedRow();
				String classID = (String) tableClass[0].getValueAt(row, 0);
				addStudent tab = new addStudent(classID);
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);
				if(tab.isOK){
					tableStudent[0].setModel(studyAt.updateTable(classID));
				}
			}
		});

		removeFromThisClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String classID = (String) tableClass[0].getValueAt(tableClass[0].getSelectedRow(), 0);
				doubleCheck tab = new doubleCheck("Are you sure to remove all selected students out of "+ classID + "?");
				tab.setLocationRelativeTo(null);
				tab.setSize(350,150);
				tab.pack();
				tab.setVisible(true);
				if(tab.isAccept()){
					int[] rows = tableStudent[0].getSelectedRows();
					for (int row : rows) {
						String id = (String) tableStudent[0].getValueAt(row, 0);
						StudentDB realID = (StudentDB) allData.studentList.stream().filter(E -> E.getMssv().equals(id)).findAny().get();
						StudyatDB temp = new StudyatDB();
						temp.setStudentId(realID.getId());
						temp.setClassId(classID);
						studyAt.deleteStudyAt(temp);
					}

					tableStudent[0].setModel(studyAt.updateTable(classID));
				}
			}
		});

		createNewClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				create tab = new create();
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);

				if(tab.isOK){
					clazz.createClazz(tab.result);
					tableClass[0].setModel(clazz.modelUpdate());
				}
			}
		});

		deleteClassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doubleCheck tab = new doubleCheck("Are you sure to delete all selected classes?");
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);

				if(tab.isAccept()){
					int[] rows = tableClass[0].getSelectedRows();

					for (int row : rows) {
						String id = (String) tableClass[0].getModel().getValueAt(row, 0);
						clazzDB temp = (clazzDB) allData.clazzList.stream().filter(E -> E.getId().equals(id)).findFirst().get();
						List<StudyatDB> list = studyAt.getAllFromClass(id);
						for (StudyatDB studyatDB : list) {
							studyAt.deleteStudyAt(studyatDB);
						}
						clazz.deleteClazz(temp);
					}
					tableClass[0].setModel(clazz.modelUpdate());
				}

			}
		});

		return panel1;
	}
}
