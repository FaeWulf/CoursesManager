package com.faewulf.application.subject;

import com.model.subjectDB;
import com.faewulf.application.faewulfUtil;

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
    private JTextField subjectNameInput;
    private JTextField creditInput;
    public boolean isOk = false;
    public subjectDB result;
    public create() {
        setSize(300, 150);
        setResizable(false);
        setTitle("Create new subject");
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
        List<JTextField> doc = new ArrayList<>();
        creditInput.setName("number");
        doc.add(subjectNameInput);
        doc.add(creditInput);

        for (JTextField jTextField : doc) {
            jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    boolean enable = true;
                    for (JTextField textField : doc) {
                        if(textField.getText().isEmpty()){
                            enable = false;
                            break;
                        }

                        if(textField.getName() != null && textField.getName().equals("number") && !faewulfUtil.isNumerizable(textField.getText())) {
                            enable = false;
                            textField.setBackground(Color.red);
                            break;
                        }
                        else
                            textField.setBackground(null);

                    }

                    buttonOK.setEnabled(enable);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    boolean enable = true;
                    for (JTextField textField : doc) {
                        if(textField.getText().isEmpty()){
                            enable = false;
                            break;
                        }

                        if(textField.getName() != null && textField.getName().equals("number") && !faewulfUtil.isNumerizable(textField.getText())) {
                            enable = false;
                            textField.setBackground(Color.red);
                            break;
                        }
                        else
                            textField.setBackground(null);

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
        subjectDB result = new subjectDB();
        result.setName(subjectNameInput.getText());
        result.setCredit(Integer.parseInt(creditInput.getText()));
        this.result = result;
        isOk = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
