package com.faewulf.application;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

                if (username.equals("a") && password.equals("a")) {
                    Officer tab = new Officer();
                    dispose();
                    tab.setVisible(true);


                }
            }
        });
    }




}