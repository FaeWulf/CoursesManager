package com.faewulf.application;
import Database.account;
import com.model.accountDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel Panel1;
    private String username, password;
    private int userType;

    public Main() {

        add(Panel1);
        setSize(400, 200);
        setName("Courses Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                password = String.valueOf(passwordField.getPassword());

                List<accountDB> list = account.getAccountList();
                accountDB temp = null;


                for (com.model.accountDB accountDB : list) {
                    if (username.equals(accountDB.getUsername())) {
                        temp = accountDB;
                    }
                }

                if (temp == null){
                    popup warn = new popup("Username does not exist.");
                    warn.setSize(200, 100);
                    warn.setLocationRelativeTo(null);
                    warn.setVisible(true);
                }
                else {
                    if(temp.getPassword().equals(password)){
                        if(temp.getAccountType() == 0) {
                            Officer tab = new Officer(temp);
                            dispose();
                            tab.setLocationRelativeTo(null);
                            tab.setVisible(true);
                        } else  {
                            Student tab = new Student();
                            dispose();
                            tab.setLocationRelativeTo(null);
                            tab.setVisible(true);
                        }
                    }
                }
            }
        });
    }




}