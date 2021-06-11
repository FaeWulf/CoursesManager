package com.faewulf.application.student;

import com.faewulf.application.allData;
import com.faewulf.application.faewulfUtil;
import com.model.StudentDB;
import com.model.semesterDB;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class createStudent extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField idInput;
	private JTextField nameInput;
	private JTextField BirhtdayInput;
	private JTextField BirthPlaceInput;
	private JComboBox sexInput;
	public boolean isOK = false;
	public StudentDB result = new StudentDB();
	public createStudent() {
		setContentPane(contentPane);
		setModal(true);
		pack();
		setResizable(false);
		buttonOK.setEnabled(false);
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
		list.add(nameInput);
		list.add(idInput);
		list.add(BirhtdayInput);
		list.add(BirthPlaceInput);

		sexInput.addItem("Male");
		sexInput.addItem("Female");

		BirhtdayInput.setName("date");

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
					buttonOK.setEnabled(enable);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {}
			});
		}

	}

	private void onOK() {
		result.setMssv(idInput.getText());
		result.setBirthday(BirhtdayInput.getText());
		result.setBirthPlace(BirthPlaceInput.getText());
		result.setSex((byte) (sexInput.getSelectedItem().equals("Male") ? 1 : 0));
		result.setName(nameInput.getText());
		result.setPassword(idInput.getText());
		result.setUsername(idInput.getText());
		isOK = true;
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
