package com.faewulf.application.clazz;

import Database.clazz;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Database.studyAt;
import com.faewulf.application.Util.*;
import com.faewulf.application.allData;
import com.faewulf.application.student.create;
import com.model.StudyatDB;
import com.model.clazzDB;

public class clazzPanel {
	private JPanel panel1;
	private JScrollPane scrpane;
	private JButton createButton;
	private JButton deleteButton;
	private JTextField search;
	
	public JPanel newPanel() {

		deleteButton.setEnabled(false);

		JTable[] tableDB = {clazz.toTable()};
		tableDB[0].setAutoCreateRowSorter(true);
		scrpane.setViewportView(tableDB[0]);

		tableDB[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tableDB[0].getSelectedRows().length == 1){
					deleteButton.setEnabled(true);
				}
				else
				if(tableDB[0].getSelectedRows().length > 1){
					deleteButton.setEnabled(true);
				}
				else {
					deleteButton.setEnabled(false);
				}
			}
		});

		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				create tab = new create();
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);

				if(tab.isOK){
					clazz.createClazz(tab.result);
					tableDB[0].setModel(clazz.modelUpdate());
				}
			}
		});


		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doubleCheck tab = new doubleCheck("Are you sure to delete all selected classes?");
				tab.setLocationRelativeTo(null);
				tab.setVisible(true);

				if(tab.isAccept()){
					int[] rows = tableDB[0].getSelectedRows();

					for (int row : rows) {
						String id = (String) tableDB[0].getModel().getValueAt(row, 0);
						clazzDB temp = (clazzDB) allData.clazzList.stream().filter(E -> E.getId().equals(id)).findFirst().get();
						clazz.deleteClazz(temp);
						List<StudyatDB> list = studyAt.getAllFromClass(id);
						for (StudyatDB studyatDB : list) {
							studyAt.deleteStudyAt(studyatDB);
						}

					}
					tableDB[0].setModel(clazz.modelUpdate());
				}
			}
		});
		return panel1;
	}
}
