package com.faewulf.application.student;

import com.faewulf.application.faewulfUtil;
import com.model.clazzDB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class create extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField idInput;
	private JTextField yearInput;
	public boolean isOK = false;
	public clazzDB result = new clazzDB();

	public create() {
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		setSize(300, 150);
		setResizable(false);

		buttonOK.setEnabled(false);

		List<JTextField> list = new ArrayList<>();
		list.add(idInput);
		list.add(yearInput);

		yearInput.setName("number");
		for (JTextField jTextField : list) {

			jTextField.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					boolean enable = true;

					for (JTextField textField : list) {

						if(textField.getText().isEmpty())
							enable = false;

						if(textField.getName() != null && textField.getName().equals("number")){
							if(!faewulfUtil.isNumerizable(textField.getText())){
								enable = false;
								textField.setBackground(Color.red);
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

						if(textField.getText().isEmpty())
							enable = false;

						if(textField.getName() != null && textField.getName().equals("number")){
							if(!faewulfUtil.isNumerizable(textField.getText())){
								enable = false;
								textField.setBackground(Color.red);
							}
							else
								textField.setBackground(null);
						}
					}
					buttonOK.setEnabled(enable);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {

				}
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
		result.setId(idInput.getText());
		result.setFemale(0);
		result.setMale(0);
		result.setSize(0);
		result.setYear(Integer.parseInt(yearInput.getText()));
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
