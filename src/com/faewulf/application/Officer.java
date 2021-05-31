package com.faewulf.application;

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
    private JPanel subTab = new Subjects().newPanel();
    private JPanel classTab = new Subjects().newPanel();
    private JPanel studentTab = new Subjects().newPanel();
    private JPanel semesterTab = new Subjects().newPanel();
    private JPanel accountTab = new Subjects().newPanel();

    public Officer() {
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(Officer);
        tabbedPane1.addTab("Accounts", accountTab);
        tabbedPane1.addTab("Students", studentTab);
        tabbedPane1.addTab("Classes", classTab);
        tabbedPane1.addTab("Semesters", semesterTab);
        tabbedPane1.addTab("Subjects", subTab);
    }
}
