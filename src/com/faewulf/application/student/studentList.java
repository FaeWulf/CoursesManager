package com.faewulf.application.student;

import Database.student;
import Database.studyAt;
import com.faewulf.application.Student;
import com.faewulf.application.Util.doubleCheck;
import com.faewulf.application.allData;
import com.model.StudentDB;
import com.model.StudyatDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.List;

public class studentList extends JDialog {
	private JPanel contentPane;
	private JButton newButton;
	private JButton deleteButton;
	private JScrollPane scrpane;
	private JButton editButton;
	private JButton resetButton;
	private JButton signedSubjectsButton;

	public studentList() {
		setContentPane(contentPane);
		setModal(true);
		pack();
		setTitle("Students Manager");
		deleteButton.setEnabled(false);

		JTable[] tableDB = {student.toTable()};
		scrpane.setViewportView(tableDB[0]);

		tableDB[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				deleteButton.setEnabled(tableDB[0].getSelectedRows().length > 0);
				if(tableDB[0].getSelectedRows().length == 1){
					editButton.setEnabled(true);
					resetButton.setEnabled(true);
					signedSubjectsButton.setEnabled(true);
				}
				else {
					editButton.setEnabled(false);
					resetButton.setEnabled(false);
					signedSubjectsButton.setEnabled(false);
				}
			}
		});

		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createStudent tab = new createStudent();
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);

				if(tab.isOK){
					student.createStudent(tab.result);
					tableDB[0].setModel(student.modelUpdate());
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				doubleCheck tab = new doubleCheck("Are you sure to delete all selected students?");
				tab.pack();
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);

				if(tab.isAccept()){
					int[] all = tableDB[0].getSelectedRows();
					for (int i : all) {
						String id = (String) tableDB[0].getValueAt(i, 0);
						StudentDB temp =  allData.studentList.stream().filter(E -> E.getMssv() == id).findFirst().get();
						List<StudyatDB> list = studyAt.getAllFromStudent(temp.getId());
						for (StudyatDB studyatDB : list) {
							studyAt.deleteStudyAt(studyatDB);
						}
						student.deleteStudent(temp);
					}
					tableDB[0].setModel(student.modelUpdate());
				}
			}
		});

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doubleCheck tab = new doubleCheck("Are you sure to reset password of selected students?");
				tab.pack();
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);
				if(tab.isAccept()){
					int[] all = tableDB[0].getSelectedRows();
					for (int i : all) {
						String id = (String) tableDB[0].getValueAt(i, 0);
						StudentDB temp =  allData.studentList.stream().filter(E -> E.getMssv() == id).findFirst().get();
						temp.setPassword(temp.getMssv());
						student.updateStudent(temp);
					}
				}
			}
		});

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableDB[0].getSelectedRow();
				String id = (String) tableDB[0].getValueAt(row, 0);
				StudentDB temp =  allData.studentList.stream().filter(E -> E.getMssv() == id).findFirst().get();
				edit tab = new edit(temp);
				tab.pack();
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);
				if(tab.isOK){
					student.updateStudent(tab.result);
					tableDB[0].setModel(student.modelUpdate());
				}
			}
		});
	}

	public JPanel newPanel(){
		return contentPane;
	}
}
