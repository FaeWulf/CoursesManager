package com.faewulf.application.account;

import com.faewulf.application.faewulfUtil;
import com.model.accountDB;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class createAccount extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField fullNameInput;
    private JTextField userNameInput;
    private JTextField passwordInput;
    private JTextField birthDayInput;
    private JTextField birthPlaceInput;
    private JTextField livePlaceInput;
    private JLabel fullNameLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel birthdayLabel;
    private JLabel birthPlaceLabel;
    private JLabel livePlaceLabel;
    private JLabel BrithdayCheck;
    public accountDB result;
    public boolean isInsert = false;
    public createAccount() {

        setSize(400,250);
        setResizable(false);
        birthDayInput.setName("birthDayInput");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.setEnabled(false);
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
        list.add(userNameInput);
        list.add(passwordInput);
        list.add(fullNameInput);
        list.add(birthDayInput);
        list.add(birthPlaceInput);
        list.add(livePlaceInput);

        for (JTextField jTextField : list) {
            jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    boolean disable = true;
                    for (JTextField jTextField : list) {
                        if(jTextField.getText().isEmpty())
                        {
                            disable = false;
                            break;
                        }
                        else
                            if(jTextField.getName() != null && jTextField.getName().equals("birthDayInput")){
                                if (faewulfUtil.isValidDate(jTextField.getText())){
                                    jTextField.setBackground(null);
                                }
                                else{
                                    disable = false;
                                    jTextField.setBackground(Color.red);
                                    break;
                                }
                            }
                    }

                    buttonOK.setEnabled(disable);
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    boolean disable = true;
                    for (JTextField jTextField : list) {
                        if(jTextField.getText().isEmpty())
                        {
                            disable = false;
                            break;
                        }
                        else
                        if(jTextField.getName() != null && jTextField.getName().equals("birthDayInput")){
                            if (faewulfUtil.isValidDate(jTextField.getText())){
                                jTextField.setBackground(null);
                            }
                            else{
                                disable = false;
                                jTextField.setBackground(Color.red);
                                break;
                            }
                        }
                    }

                    buttonOK.setEnabled(disable);
                }

                @Override
                public void changedUpdate(DocumentEvent e) { }
            });
        }
    }

    private void onOK() {
        accountDB temp = new accountDB();
        temp.setFullName(fullNameInput.getText());
        temp.setUsername(userNameInput.getText());
        temp.setPassword(passwordInput.getText());
        temp.setBirth(Date.valueOf(birthDayInput.getText()));
        temp.setBirthPlace(birthPlaceInput.getText());
        temp.setLivePlace(livePlaceInput.getText());
        result = temp;
        isInsert = true;
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
