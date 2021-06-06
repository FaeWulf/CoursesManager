package com.faewulf.application;

import Database.account;
import com.faewulf.application.account.accountPanel;
import com.faewulf.application.account.changePass;
import com.faewulf.application.semester.semesterPanel;
import com.faewulf.application.student.studentPanel;
import com.faewulf.application.subject.subjectPanel;
import com.faewulf.application.clazz.clazzPanel;
import com.model.accountDB;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Officer extends JFrame{
    private JPanel Officer;
    private JTabbedPane tabbedPane1;
    private JButton editInfoButton;
    private JTextPane InfoTab;
    private JTextArea helpTab;
    private JButton logoutButton;
    private JLabel labelTab;
    private JButton refresh;
    private accountDB currentUseAccount;

    public Officer(accountDB account_) {
        currentUseAccount = account_;
        setSize(1200, 750);
        setTitle("Courses Manager: login as Officer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(Officer);

        InfoTab.setText("ID:	" + account_.getId() + "\n" +
                        "Full name: 	" + account_.getFullName() + "\n" +
                        "Date of birth:	" + account_.getBirth() + "\n" +
                        "Place of birth:	" + account_.getBirthPlace() + "\n" +
                        "Live in:	" + account_.getLivePlace() + "\n" +
                        "Account type:	" + account_.getAccountType() + "\n" +
                        "Username:	" + account_.getUsername() + "\n"
                );

        JPanel subTab = new subjectPanel().newPanel();
        JPanel accountTab = new accountPanel().newPanel(currentUseAccount);
        JPanel semesterTab = new semesterPanel().newPanel();
        JPanel classTab = new clazzPanel().newPanel();
        JPanel studentTab = new studentPanel().newPanel();

        tabbedPane1.addTab("Accounts", accountTab);
        tabbedPane1.addTab("Subjects", subTab);
        tabbedPane1.addTab("Semesters", semesterTab);
        tabbedPane1.addTab("Classes", classTab);
        tabbedPane1.addTab("Students and Classes", studentTab);
        editInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePass tabchange = new changePass(account_);
                tabchange.setLocationRelativeTo(null);
                tabchange.setVisible(true);
                if(tabchange.isChanged()){
                    account.updateAccount(account_, account_);
                    dispose();
                    Officer newTab = new Officer(account_);
                    newTab.setLocationRelativeTo(null);
                    newTab.setVisible(true);
                }

            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main tab = new Main();
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
            }
        });
   }
}
