package com.faewulf.application;

import com.model.accountDB;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;

public class changePass extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextPane usernamePane;
    private JTextPane passwordPane;
    private JTextPane fullnamePane;
    private JTextPane BirthdayPane;
    private JTextPane birthPlacePane;
    private JTextPane livePlacePane;
    private boolean changed = false;
    public changePass(accountDB _new) {
        setContentPane(contentPane);
        setModal(true);
        setSize(500, 500);
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
