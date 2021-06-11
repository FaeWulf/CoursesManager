package com.faewulf.application.semester;

import Database.hp;
import com.faewulf.application.allData;
import com.faewulf.application.faewulfUtil;
import com.model.HpDB;
import com.model.subjectDB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class addSubject extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JComboBox subjectInput;
	private JTextField subjectInfo;
	private JTextField roomInput;
	private JComboBox weekInput;
	private JComboBox timeInput;
	private JTextField slotInput;
	private JTextField teacherInput;
	private int kyID;
	private String[] weekName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public boolean isOK = false;
	public HpDB result = new HpDB();
	public addSubject(int kyID) {
		this.kyID = kyID;
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		buttonOK.setEnabled(false);
		setResizable(false);

		List<subjectDB> listNotIn = new ArrayList<>();
		List<HpDB> listIn = new ArrayList<>();
		listNotIn = hp.toListNotIn(kyID);
		listIn = hp.toListIn(kyID);

		for (subjectDB subjectDB : listNotIn) {
			subjectInput.addItem(subjectDB);
		}



		subjectDB sub = (subjectDB) subjectInput.getSelectedItem();
		assert sub != null;
		subjectInfo.setText("ID: " + sub.getId() + ", credit: " + sub.getCredit());

		subjectInput.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				subjectDB temp = (subjectDB) subjectInput.getSelectedItem();
				assert temp != null;
				subjectInfo.setText("ID: " + temp.getId() + ", credit: " + temp.getCredit());
			}
		});

		int[] weeks = {0,1,2,3,4,5,6};
		Map<String, List<allData.scheduleTime>> chooseTime = new HashMap<>();
		for (int week : weeks) {
			List<allData.scheduleTime> temp = new ArrayList<>(allData.scheduleTimeList);
			for (HpDB hpDB : listIn) {
				if(hpDB.getWeekDay() == week && temp.stream().anyMatch(E -> E.index == hpDB.getTime()))
					temp.remove(allData.scheduleTimeList.get(hpDB.getTime() - 1));
			}
			 if(temp.size() != 0){
			 	chooseTime.put(weekName[week], temp);
			 }
		}

		chooseTime.forEach((K,V) -> {
			weekInput.addItem(K);
		});

		String item = (String) weekInput.getSelectedItem();
		timeInput.removeAllItems();
		for (allData.scheduleTime scheduleTime : chooseTime.get(item)) {
			timeInput.addItem(scheduleTime);
		}

		timeInput.setSelectedIndex(0);
		weekInput.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String item = (String) weekInput.getSelectedItem();
				timeInput.removeAllItems();
				for (allData.scheduleTime scheduleTime : chooseTime.get(item)) {
					timeInput.addItem(scheduleTime);
				}
			}
		});

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
		list.add(roomInput);
		list.add(slotInput);
		list.add(teacherInput);
		teacherInput.setName("number");
		slotInput.setName("number");
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

	}

	private void onOK() {
		result.setKydkhpId(kyID);
		result.setClassName(roomInput.getText());
		result.setSlot(Integer.parseInt(slotInput.getText()));
		subjectDB sub = (subjectDB) subjectInput.getSelectedItem();
		result.setSubjectId(sub.getId());
		allData.scheduleTime time_temp = (allData.scheduleTime) timeInput.getSelectedItem();
		result.setTime(time_temp.index);
		result.setTeacherId(Integer.parseInt(teacherInput.getText()));

		String week = (String) weekInput.getSelectedItem();
		result.setWeekDay(Arrays.asList(weekName).indexOf(week));
		isOK = true;
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}
}