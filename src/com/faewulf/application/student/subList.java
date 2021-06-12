package com.faewulf.application.student;

import Database.currentDKHP;
import Database.dkhp;
import com.faewulf.application.allData;
import com.model.CurrentDB;
import com.model.KydkhpDB;
import com.model.StudentDB;

import javax.swing.*;
import java.awt.event.*;

public class subList extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JScrollPane scrpane;

	public subList(StudentDB current) {
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

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

		CurrentDB temp = currentDKHP.getCurrentKy();
		KydkhpDB curr = allData.kydkhpList.stream().filter(E -> E.getId() == temp.getId()).findFirst().get();
		JTable[] tableDB =  {dkhp.toTable(current.getId(), curr.getId())};
		tableDB[0].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrpane.setViewportView(tableDB[0]);
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
