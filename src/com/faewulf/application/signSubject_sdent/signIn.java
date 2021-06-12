package com.faewulf.application.signSubject_sdent;

import Database.currentDKHP;
import Database.dkhp;
import Database.hp;
import Database.kydkhp;
import com.faewulf.application.allData;
import com.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class signIn {
	private JScrollPane scrpane;
	private JButton addButton;
	private JPanel panel1;
	private JButton cancel;


	public JPanel getPane(StudentDB current) {

		CurrentDB temp = currentDKHP.getCurrentKy();

		KydkhpDB curr = allData.kydkhpList.stream().filter(E -> E.getId() == temp.getId()).findFirst().get();
		addButton.setEnabled(true);

		JTable[] tableDB_ =  {dkhp.toTable(current.getId(), curr.getId())};

		if(tableDB_[0].getRowCount() >= 8){
			addButton.setEnabled(false);
		};

		Timestamp now = new Timestamp(System.currentTimeMillis());
		cancel.setVisible(false);

		if(now.after(curr.getEnd())){
			cancel.setText("Course registration session ended.");
			cancel.setVisible(true);
			scrpane.setViewportView(cancel);
			panel1.setEnabled(false);
			return panel1;
		}
		else
			if(now.before(curr.getStart())){
				cancel.setText("There is no course registration session at the current time.");
				cancel.setVisible(true);
				scrpane.setViewportView(cancel);
				panel1.setEnabled(false);
				return panel1;
			}


		JTable[] tableDB = {hp.toTableStudent(current.getId(), curr.getId())};

		scrpane.setViewportView(tableDB[0]);

		tableDB[0].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tableDB[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				addButton.setEnabled(tableDB[0].getSelectedRows().length == 1);
				if(tableDB_[0].getRowCount() >= 8){
					addButton.setEnabled(false);
				};
			}
		});

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ID = (int) tableDB[0].getValueAt(tableDB[0].getSelectedRow(), 0);
				DkhpDB temp = new DkhpDB();
				temp.setHpId(ID);
				temp.setStudentId(current.getId());
				dkhp.createdkhp(temp);

				tableDB[0].setModel(hp.modelUpdateStudent(current.getId(), curr.getId()));
			}
		});

		return panel1;
	}
}
