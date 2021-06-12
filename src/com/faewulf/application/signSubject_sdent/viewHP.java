package com.faewulf.application.signSubject_sdent;

import Database.currentDKHP;
import Database.dkhp;
import Database.hp;
import com.faewulf.application.allData;
import com.model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class viewHP {
	private JPanel panel1;
	private JButton unsignButton;
	private JScrollPane scrpane;

	public JPanel newPanel(StudentDB current){

		CurrentDB temp = currentDKHP.getCurrentKy();

		KydkhpDB curr = allData.kydkhpList.stream().filter(E -> E.getId() == temp.getId()).findFirst().get();
		JTable[] tableDB =  {dkhp.toTable(current.getId(), curr.getId())};
		tableDB[0].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrpane.setViewportView(tableDB[0]);
		unsignButton.setEnabled(false);
		unsignButton.setVisible(false);

		tableDB[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				unsignButton.setEnabled(tableDB[0].getSelectedRows().length == 1);
				unsignButton.setVisible(tableDB[0].getSelectedRows().length == 1);
			}
		});

		Timestamp now = new Timestamp(System.currentTimeMillis());

		if (now.after(curr.getEnd())){
			scrpane.setEnabled(false);
			tableDB[0].setEnabled(false);
		}

		unsignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int ID = (int) tableDB[0].getValueAt(tableDB[0].getSelectedRow(), 0);
				DkhpDB temp = allData.dkhpList.stream().filter(E -> E.getHpId() == ID && E.getStudentId() == current.getId()).findFirst().get();
				dkhp.deletedkhp(temp);
				tableDB[0].setModel(dkhp.updateTable(current.getId(), curr.getId()));
			}
		});

		return panel1;
	}

}
