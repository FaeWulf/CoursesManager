package com.faewulf.application.student;

import com.faewulf.application.faewulfUtil;
import com.model.StudentDB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class edit extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField nameInput;
	private JTextField birthdayInput;
	private JTextField birthPlaceInput;
	private JComboBox sexInput;
	public boolean isOK = false;
	public StudentDB result;
	public edit(StudentDB current) {
		result = current;
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		setResizable(false);
		sexInput.addItem("Female");
		sexInput.addItem("Male");

		nameInput.setText(current.getName());
		birthdayInput.setText(current.getBirthday());
		birthPlaceInput.setText(current.getBirthPlace());

		sexInput.setSelectedIndex(current.getSex());

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
		list.add(birthdayInput);
		list.add(birthPlaceInput);

		birthPlaceInput.setName("date");
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
		isOK = true;
		result.setName(nameInput.getText());
		result.setSex((byte) (sexInput.getSelectedItem().equals("Male") ? 1 : 0));
		result.setBirthPlace(birthPlaceInput.getText());
		result.setBirthday(birthdayInput.getText());
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
