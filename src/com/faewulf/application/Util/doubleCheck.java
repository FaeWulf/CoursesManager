package com.faewulf.application.Util;

import javax.swing.*;
import java.awt.event.*;

public class doubleCheck extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel question;
    private boolean answer = false;
    public doubleCheck(String question) {
        setContentPane(contentPane);
        setResizable(false);
        setSize(300,150);
        this.question.setText(question);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.setText("Yes");
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
        answer = true;
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public boolean isAccept() {
        return answer;
    }
}
