package com.faewulf.application;

import Database.account;
import com.model.accountDB;
import net.bytebuddy.implementation.bytecode.collection.ArrayAccess;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class accountPanel extends JPanel {
    private final List<accountDB> list = account.getAccountList();
    private JPanel panel;
    private JScrollPane scrpane;

    public JPanel newPanel(){
        String[] columnNames = {"Id", "Fullname","Username", "Password","Birth Day", "Birth Place", "Living Place"};
        JTable tableDat = new JTable(account.getObjectList(),columnNames);
        scrpane.setViewportView(tableDat);
        this.setVisible(true);
        return panel;
    }
}
