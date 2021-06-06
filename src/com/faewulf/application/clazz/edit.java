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

public class edit extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField femaleInput;
	private JTextField maleInput;
	private JTextField yearInput;
	public clazzDB temp;
	public boolean isOk = false;
	public edit(clazzDB current) {
		this.temp = current;
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		setSize(300, 300);
		setResizable(false);

		yearInput.setText(String.valueOf( current.getYear()));
		femaleInput.setText(String.valueOf( current.getFemale()));
		maleInput.setText(String.valueOf( current.getMale()));

		List<JTextField> list = new ArrayList<>();
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
		this.temp.setYear(Integer.parseInt(yearInput.getText()));
		this.temp.setFemale(Integer.parseInt(femaleInput.getText()));
		this.temp.setMale(Integer.parseInt(maleInput.getText()));
		this.temp.setSize(temp.getMale() + temp.getFemale());
		isOk = true;
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}
