package com.faewulf.application.studentTab;

import com.faewulf.application.Student;
import com.faewulf.application.faewulfUtil;
import com.model.StudentDB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class infoChange extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField nameInput;
	private JTextField passInput;
	private JTextField birhtdayInput;
	private JTextField placeInput;
	public boolean isOK = false;
	public StudentDB result;
	public infoChange(StudentDB current) {
		result = current;
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		nameInput.setText(current.getName());
		passInput.setText(current.getPassword());
		birhtdayInput.setText(current.getBirthday());
		placeInput.setText(current.getBirthPlace());

		List<JTextField> list = new ArrayList<>();

		list.add(nameInput);
		list.add(passInput);
		list.add(birhtdayInput);
		list.add(placeInput);

		birhtdayInput.setName("date");

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



	}

	private void onOK() {
		isOK = true;
		result.setPassword(passInput.getText());
		result.setBirthday(birhtdayInput.getText());
		result.setBirthPlace(placeInput.getText());
		result.setName(nameInput.getText());
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
