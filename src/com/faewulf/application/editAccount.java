package com.faewulf.application;

import com.model.accountDB;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;

public class editAccount extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JEditorPane namePane;
    private JEditorPane livePane;
    private JEditorPane birthPane;
    private JEditorPane birthPlacePane;
    private JLabel birth;
    private JLabel birthPlace;
    private JLabel livingPlace;
    private JLabel name;
    private boolean isChanged = false;
    private accountDB dat;
    public editAccount(accountDB data){
        this.dat = data;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        namePane.setText(data.getFullName());
        birthPane.setText(data.getBirth().toString());
        birthPlacePane.setText(data.getBirthPlace());
        livePane.setText(data.getLivePlace());

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
        dat.setFullName(this.namePane.getText());
        dat.setBirth(Date.valueOf(birthPane.getText()));
        dat.setLivePlace(livePane.getText());
        dat.setBirthPlace(birthPlacePane.getText());
        isChanged = true;
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public boolean isChanged() {
        return this.isChanged;
    }
}
