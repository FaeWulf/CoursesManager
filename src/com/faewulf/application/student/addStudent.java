package com.faewulf.application.student;

import Database.student;
import Database.studyAt;
import com.faewulf.application.allData;
import com.model.StudentDB;
import com.model.StudyatDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class addStudent extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JScrollPane scrpane;
	private List<StudentDB> studentDBList = new ArrayList<>();
	private String clazz;
	public boolean isOK = false;
	public addStudent(String clazz) {
		this.clazz = clazz;
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		setSize(500, 500);
		setResizable(false);
		buttonOK.setEnabled(false);
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

		JTable[] tableDB = {student.getStudentNotIn(clazz)};
		scrpane.setViewportView(tableDB[0]);

		tableDB[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tableDB[0].getSelectedRows().length > 0){
					studentDBList.clear();
					buttonOK.setEnabled(true);
					int[] temp = tableDB[0].getSelectedRows();
					for (int i : temp) {
						String id = (String) tableDB[0].getValueAt(i, 0);
						studentDBList.add(allData.studentList.stream().filter(E -> E.getMssv().equals(id)).findFirst().get());
					}
				}
				else
					buttonOK.setEnabled(false);
			}
		});



	}

	private void onOK() {
		for (StudentDB studentDB : studentDBList) {
			StudyatDB temp = new StudyatDB();
			temp.setStudentId(studentDB.getId());
			temp.setClassId(this.clazz);
			studyAt.createStudyAt(temp);
		}
		isOK = true;
		dispose();

	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
