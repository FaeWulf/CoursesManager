package com.faewulf.application.clazz;

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
	private JTextField femaleInput;
	private JTextField maleInput;
	public boolean isOK = false;
	public clazzDB result = new clazzDB();

	public create() {
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		setSize(300, 250);
		setResizable(false);

		buttonOK.setEnabled(false);

		List<JTextField> list = new ArrayList<>();
		list.add(idInput);
		list.add(yearInput);
		list.add(femaleInput);
		list.add(maleInput);

		yearInput.setName("number");
		femaleInput.setName("number");
		maleInput.setName("number");
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
		result.setFemale(Integer.parseInt(femaleInput.getText()));
		result.setMale(Integer.parseInt(maleInput.getText()));
		result.setSize(result.getMale() + result.getFemale());
		result.setYear(Integer.parseInt(yearInput.getText()));
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
