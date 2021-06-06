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

public class changePass extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField usernamePane;
    private JTextField BirthdayPane;
    private JTextField livePlacePane;
    private JTextField passwordPane;
    private JTextField birthPlacePane;
    private JTextField fullnamePane;
    private boolean changed = false;
    public changePass(accountDB _new) {
        setContentPane(contentPane);
        setModal(true);
        setSize(400, 250);
        getRootPane().setDefaultButton(buttonOK);

        usernamePane.setText(_new.getUsername());
        passwordPane.setText(_new.getPassword());
        fullnamePane.setText(_new.getFullName());
        birthPlacePane.setText(_new.getBirthPlace());
        BirthdayPane.setText(_new.getBirth().toString());
        livePlacePane.setText(_new.getLivePlace());

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(_new);
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
        BirthdayPane.setName("birthDayInput");
        List<JTextField> list = new ArrayList<>();
        list.add(usernamePane);
        list.add(passwordPane);
        list.add(fullnamePane);
        list.add(birthPlacePane);
        list.add(BirthdayPane);
        list.add(livePlacePane);

        for (JTextField jTextField : list) {
            jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    boolean disable = true;
                    for (JTextField jTextField : list) {
                        if (jTextField.getText().isEmpty()) {
                            disable = false;
                            break;
                        } else if (jTextField.getName() != null && jTextField.getName().equals("birthDayInput")) {
                            if (faewulfUtil.isValidDate(jTextField.getText())) {
                                jTextField.setBackground(null);
                            } else {
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
                        if (jTextField.getText().isEmpty()) {
                            disable = false;
                            break;
                        } else if (jTextField.getName() != null && jTextField.getName().equals("birthDayInput")) {
                            if (faewulfUtil.isValidDate(jTextField.getText())) {
                                jTextField.setBackground(null);
                            } else {
                                disable = false;
                                jTextField.setBackground(Color.red);
                                break;
                            }
                        }
                    }

                    buttonOK.setEnabled(disable);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }
            });
        }
    }

    private void onOK(accountDB _new) {
        // add your code here
        _new.setUsername(usernamePane.getText());
        _new.setPassword(passwordPane.getText());
        _new.setBirth(Date.valueOf(BirthdayPane.getText()));
        _new.setBirthPlace(birthPlacePane.getText());
        _new.setFullName(fullnamePane.getText());
        _new.setLivePlace(livePlacePane.getText());
        dispose();
        changed = true;

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public boolean isChanged() {
        return changed;
    }
}
