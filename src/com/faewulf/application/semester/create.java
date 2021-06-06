package com.faewulf.application.semester;


import com.faewulf.application.faewulfUtil;
import com.model.semesterDB;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import com.faewulf.application.allData;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class create extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox semesterInput;
    private JTextField yearInput;
    private JTextField startDateInput;
    private JTextField endDateInput;
    private JLabel warning;
    public boolean isOk = false;
    public semesterDB result = new semesterDB();
    public semesterDB current;
    public create(semesterDB current) {
        setContentPane(contentPane);
        setTitle("Create new semester");
        setResizable(false);
        setSize(350, 230);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.current = current;
        if(current == null)
        buttonOK.setEnabled(false);

        semesterInput.addItem(1);
        semesterInput.addItem(2);
        semesterInput.addItem(3);
        String currentSemester = null;
        int currentYear = -1;
        if(current != null){
            yearInput.setText(String.valueOf(current.getYear()));
            startDateInput.setText(current.getStart().toString());
            endDateInput.setText(current.getEnd().toString());
            semesterInput.setSelectedIndex(Integer.parseInt(String.valueOf(current.getId().charAt(2))) - 1);
            this.current = current;
            currentSemester = current.getId();
            currentYear = current.getYear();
        }

        List<JTextField> list = new ArrayList<>();
        list.add(yearInput);
        list.add(startDateInput);
        list.add(endDateInput);
        yearInput.setName("number");
        startDateInput.setName("date");
        endDateInput.setName("date");
        for (JTextField jTextField : list) {
            String finalCurrentSemester_ = currentSemester;
            int finalCurrentYear_ = currentYear;
            jTextField.getDocument().addDocumentListener(new DocumentListener() {
               private final String finalCurrentSemester = finalCurrentSemester_;
               private final int finalCurrentYear = finalCurrentYear_;
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
                       warning.setText("End date larger than Start date");
                   }
                   else
                       endDateInput.setBackground(null);
                   if(current == null) {
                       if (!yearInput.getText().isEmpty() && faewulfUtil.isNumerizable(yearInput.getText()))
                           for (semesterDB semester : allData.semesterList) {
                               if (semester.getId().equals("HK" + semesterInput.getSelectedItem().toString()) && semester.getYear() == Integer.parseInt(yearInput.getText())) {
                                   enable = false;
                                   warning.setText("Already have this semester.");
                                   break;
                               } else
                                   warning.setText("*");

                           }
                   }
                   else
                   if(!yearInput.getText().isEmpty() && faewulfUtil.isNumerizable(yearInput.getText()))
                       for (semesterDB semester : allData.semesterList) {

                           if(finalCurrentSemester.equals("HK" + semesterInput.getSelectedItem().toString()) && finalCurrentYear == Integer.parseInt(yearInput.getText()))
                               continue;
                           if(semester.getId().equals("HK" + semesterInput.getSelectedItem().toString()) && semester.getYear() == Integer.parseInt(yearInput.getText())){
                               enable = false;
                               warning.setText("Already have this semester.");
                               break;
                           }
                           else
                               warning.setText("*");
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

                   if(enable && !Date.valueOf(endDateInput.getText()).after(Date.valueOf(startDateInput.getText()))){
                       endDateInput.setBackground(Color.yellow);
                       warning.setText("End date larger than Start date.");
                       enable = false;
                   }
                       endDateInput.setBackground(null);

                   if(current == null) {
                       if (!yearInput.getText().isEmpty() && faewulfUtil.isNumerizable(yearInput.getText()))
                           for (semesterDB semester : allData.semesterList) {
                               if (semester.getId().equals("HK" + semesterInput.getSelectedItem().toString()) && semester.getYear() == Integer.parseInt(yearInput.getText())) {
                                   enable = false;
                                   warning.setText("Already have this semester.");
                                   break;
                               } else
                                   warning.setText("*");

                           }
                   }
                   else
                   if(!yearInput.getText().isEmpty() && faewulfUtil.isNumerizable(yearInput.getText()))
                       for (semesterDB semester : allData.semesterList) {

                           if(finalCurrentSemester.equals("HK" + semesterInput.getSelectedItem().toString()) && finalCurrentYear == Integer.parseInt(yearInput.getText()))
                               continue;
                           if(semester.getId().equals("HK" + semesterInput.getSelectedItem().toString()) && semester.getYear() == Integer.parseInt(yearInput.getText())){
                               enable = false;
                               warning.setText("Already have this semester.");
                               break;
                           }
                           else
                               warning.setText("*");
                       }
                   buttonOK.setEnabled(enable);


                   buttonOK.setEnabled(enable);
               }

               @Override
               public void changedUpdate(DocumentEvent e) {}
           });
        }

        int finalCurrentYear = currentYear;
        String finalCurrentSemester = currentSemester;
        semesterInput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
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
                    warning.setText("End date larger than Start date.");
                    enable = false;
                }
                endDateInput.setBackground(null);

                if(current == null) {
                    if (!yearInput.getText().isEmpty() && faewulfUtil.isNumerizable(yearInput.getText()))
                        for (semesterDB semester : allData.semesterList) {
                            if (semester.getId().equals("HK" + semesterInput.getSelectedItem().toString()) && semester.getYear() == Integer.parseInt(yearInput.getText())) {
                                enable = false;
                                warning.setText("Already have this semester.");
                                break;
                            } else
                                warning.setText("*");
                        }
                }
                else
                    if(!yearInput.getText().isEmpty() && faewulfUtil.isNumerizable(yearInput.getText()))
                        for (semesterDB semester : allData.semesterList) {

                            if(finalCurrentSemester.equals("HK" + semesterInput.getSelectedItem().toString()) && finalCurrentYear == Integer.parseInt(yearInput.getText()))
                                continue;
                            if(semester.getId().equals("HK" + semesterInput.getSelectedItem().toString()) && semester.getYear() == Integer.parseInt(yearInput.getText())){
                                enable = false;
                                warning.setText("Already have this semester.");
                                break;
                            }
                            else
                                warning.setText("*");
                        }
                buttonOK.setEnabled(enable);
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
    }

    private void onOK() {
        result.setId("HK" + semesterInput.getSelectedItem().toString());
        result.setStart(Date.valueOf(startDateInput.getText()));
        result.setEnd(Date.valueOf(endDateInput.getText()));
        result.setYear(Integer.parseInt(yearInput.getText()));
        isOk = true;

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
