package com.faewulf.application;

import Database.student;
import com.faewulf.application.studentTab.infoChange;
import com.model.StudentDB;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.faewulf.application.signSubject_sdent.*;
public class Student extends JFrame{
	private JPanel panel1;
	private JPanel infoTab;
	private JPanel generalTab;
	private JButton editInfoButton;
	private JTextArea infoTabPanel;
	private JTextArea textArea2;
	private JButton logoutButton;
	private JLabel infoLabel;
	private JTabbedPane tabbedPane1;
	private JSplitPane a;
	private JButton refreshButton;

	public Student (StudentDB current) {
		setTitle("Courses Manager: login as Student");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1200, 750);
		add(panel1);

		infoTabPanel.setText("Student ID: " + current.getMssv() + "\nFull Name: " + current.getName() + "\n Birthday: " + current.getBirthday() + "\n Birth place: " + current.getBirthPlace());

		editInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				infoChange tab = new infoChange(current);
				tab.pack();
				tab.setLocationRelativeTo(null);
				tab.setResizable(false);
				tab.setVisible(true);

				if(tab.isOK){
					student.updateStudent(tab.result);
					dispose();
					Student temp = new Student(current);
					temp.setVisible(true);
				}
			}
		});

		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Main tab = new Main();
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);
			}
		});

		JPanel sign = new signIn().getPane(current);
		JPanel list = new viewHP().newPanel(current);

		tabbedPane1.addTab("Sign Subjects", sign);
		tabbedPane1.addTab("View Signed list", list);


		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Student tab = new Student(current);
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);
			}
		});
	}
}

