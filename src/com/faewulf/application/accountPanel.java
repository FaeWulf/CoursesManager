package com.faewulf.application;

import Database.account;
import com.model.accountDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class accountPanel extends JPanel {
    private final List<accountDB> list = allData.accountList;
    private JPanel panel;
    private JScrollPane scrpane;
    private JButton editButton;
    private JButton resetButton;
    private JButton deleteButton;

    public JPanel newPanel(accountDB currentUseAccount){
        editButton.setEnabled(false);
        resetButton.setEnabled(false);
        deleteButton.setEnabled(false);
        final JTable[] tableDat = {account.toTable()};
        scrpane.setViewportView(tableDat[0]);
        this.setVisible(true);

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
                tab.setSize(500,500);
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
                        list.remove(temp);
                    }

                    tableDat[0].setModel(account.modelUpdate());
                    scrpane.setViewportView(tableDat[0]);
                };

                }
        });

        return panel;
    }
}
