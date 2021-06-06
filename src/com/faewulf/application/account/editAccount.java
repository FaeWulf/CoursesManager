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

public class editAccount extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField namePane;
    private JTextField livePane;
    private JTextField birthPane;
    private JTextField birthPlacePane;
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
        setResizable(false);
        setSize(400,200);
        birthPane.setName("birthDayInput");
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

        List<JTextField> list = new ArrayList<>();
        list.add(namePane);
        list.add(birthPane);
        list.add(birthPlacePane);
        list.add(livePane);

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
