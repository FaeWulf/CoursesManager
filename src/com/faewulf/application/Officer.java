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

    public Officer() {
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(Officer);

    }
}
