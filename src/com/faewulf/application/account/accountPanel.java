package com.faewulf.application.account;

import Database.account;
import com.faewulf.application.allData;
import com.faewulf.application.Util.doubleCheck;
import com.model.accountDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class accountPanel extends JPanel {
    private final List<accountDB> list = allData.accountList;
    private JPanel panel;
    private JScrollPane scrpane;
    private JButton editButton;
    private JButton resetButton;
    private JButton deleteButton;
    private JButton createButton;
    private JTextField searchBar;
    private JLabel searchLabel;

    public JPanel newPanel(accountDB currentUseAccount){
        editButton.setEnabled(false);
        resetButton.setEnabled(false);
        deleteButton.setEnabled(false);
        final JTable[] tableDat = {account.toTable()};
        scrpane.setViewportView(tableDat[0]);
        this.setVisible(true);

        searchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                String temp = searchBar.getText();
                if(temp.length() == 0){
                    tableDat[0].setModel(account.modelUpdate());
                    scrpane.setViewportView(tableDat[0]);
                }
                else {
                    tableDat[0].setModel(account.modelUpdate(temp));
                    scrpane.setViewportView(tableDat[0]);
                }

            }
        });

        tableDat[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rows = tableDat[0].getSelectedRows().length;
                if(rows == 1) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            editButton.setEnabled(true);
                            resetButton.setEnabled(true);
                            deleteButton.setEnabled(true);
                        }
                    });
                }
                else
                    if (rows > 1){
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                editButton.setEnabled(false);
                                resetButton.setEnabled(true);
                                deleteButton.setEnabled(true);
                            }
                        });
                    }
                    else {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                editButton.setEnabled(false);
                                resetButton.setEnabled(false);
                                deleteButton.setEnabled(false);
                            }
                        });
                    }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = (int) tableDat[0].getModel().getValueAt(tableDat[0].getSelectedRow(),0);
                List<accountDB> list = allData.accountList;
                accountDB result = list.stream().filter(E -> E.getId() == row).findFirst().get();
                editAccount tab = new editAccount(result);
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                if(tab.isChanged()){
                    account.updateAccount(result, result);
                    tableDat[0] = account.toTable();
                    scrpane.setViewportView(tableDat[0]);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doubleCheck tab = new doubleCheck("Are you sure to reset all selected accounts?");
                tab.setSize(300,150);
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                if(tab.isAccept()) {
                    int[] rows = tableDat[0].getSelectedRows();
                    List<accountDB> list = allData.accountList;
                    for (int row : rows) {
                       int ID = (int) tableDat[0].getModel().getValueAt(row, 0);
                       accountDB temp = list.stream().filter(K -> K.getId() == ID).findFirst().get();
                       temp.setPassword("admin");
                       account.updateAccount(temp, temp);
                    }

                    tableDat[0].setModel(account.modelUpdate());
                    scrpane.setViewportView(tableDat[0]);
                };

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doubleCheck tab = new doubleCheck("Are you sure to delete all selected accounts?");
                tab.setSize(300,150);
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                if(tab.isAccept()) {
                    int[] rows = tableDat[0].getSelectedRows();

                    List<accountDB> list = allData.accountList;
                    for (int row : rows) {
                        int ID = (int) tableDat[0].getModel().getValueAt(row, 0);
                        if(ID == currentUseAccount.getId())
                            continue;
                        accountDB temp = list.stream().filter(K -> K.getId() == ID).findFirst().get();
                        account.deleteAccount(temp);
                    }

                    tableDat[0].setModel(account.modelUpdate());
                    scrpane.setViewportView(tableDat[0]);
                };

                }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount tab = new createAccount();
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                if(tab.isInsert){
                    tab.result.setAccountType(0);
                    account.createAccount(tab.result);

                    tableDat[0].setModel(account.modelUpdate());
                    scrpane.setViewportView(tableDat[0]);
                }
            }
        });
        return panel;
    }
}
