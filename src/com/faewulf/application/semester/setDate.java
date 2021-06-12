package com.faewulf.application.semester;

import com.faewulf.application.allData;
import com.faewulf.application.faewulfUtil;
import com.model.KydkhpDB;
import com.model.semesterDB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class setDate extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField startDateInput;
	private JTextField endDateInput;
	public boolean isOK =false;
	public KydkhpDB result;
	public setDate(KydkhpDB current) {
		result = current;
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

		List<JTextField> list = new ArrayList<>();
		list.add(startDateInput);
		list.add(endDateInput);
		startDateInput.setName("date");
		endDateInput.setName("date");
		for (JTextField jTextField : list) {
			jTextField.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					boolean enable = true;

					for (JTextField textField : list) {
						if(textField.getText().isEmpty()){
							enable = false;
							break;
						}

						if(textField.getName() != null && textField.getName().equals("number")){
							if(!faewulfUtil.isNumerizable(textField.getText())){
								enable = false;
								textField.setBackground(Color.red);
								break;
							}else
								textField.setBackground(null);
						}
						else
						if(textField.getName() != null && textField.getName().equals("date")){
							if(!faewulfUtil.isValidDate(textField.getText())){
								enable = false;
								textField.setBackground(Color.red);
								break;
							}
							else
								textField.setBackground(null);
						}
					}

					if(enable && !Date.valueOf(endDateInput.getText()).after(Date.valueOf(startDateInput.getText()))){
						endDateInput.setBackground(Color.yellow);
						enable = false;
					}
					else
						endDateInput.setBackground(null);
					buttonOK.setEnabled(enable);
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					boolean enable = true;

					for (JTextField textField : list) {
						if(textField.getText().isEmpty()){
							enable = false;
							break;
						}

						if(textField.getName() != null && textField.getName().equals("number")){
							if(!faewulfUtil.isNumerizable(textField.getText())){
								enable = false;
								textField.setBackground(Color.red);
								break;
							}else
								textField.setBackground(null);
						}
						else
						if(textField.getName() != null && textField.getName().equals("date")){
							if(!faewulfUtil.isValidDate(textField.getText())){
								enable = false;
								textField.setBackground(Color.red);
								break;
							}
							else
								textField.setBackground(null);
						}
					}

					if(enable && !Date.valueOf(endDateInput.getText()).after(Date.valueOf(startDateInput.getText()))){
						endDateInput.setBackground(Color.yellow);
						enable = false;
					}
					else
					endDateInput.setBackground(null);

					buttonOK.setEnabled(enable);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {}
			});
		}

	}

	private void onOK() {
		result.setStart(Timestamp.valueOf(startDateInput.getText() + " 00:00:00"));
		result.setEnd(Timestamp.valueOf(endDateInput.getText() + " 00:00:00"));
		isOK = true;
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
