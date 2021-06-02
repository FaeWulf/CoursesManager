package com.faewulf.application;

import Database.account;
import com.model.accountDB;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class accountPanel extends JPanel {
    private final List<accountDB> list = allData.accountList;
    private JPanel panel;
    private JScrollPane scrpane;

    public JPanel newPanel(){
        JTable tableDat = account.toTable();
        scrpane.setViewportView(tableDat);
        this.setVisible(true);
        tableDat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableDat.rowAtPoint(e.getPoint());
                int column = tableDat.columnAtPoint(e.getPoint());

                if (column == 7){
                    int a = (int) tableDat.getModel().getValueAt(row, 0);
                    System.out.println(a);
                    List<accountDB> list = allData.accountList;
                    accountDB result = list.stream().filter(E -> E.getId() == a).findFirst().get();
                    editAccount tab = new editAccount(result);
                    tab.setSize(500,500);
                    tab.setLocationRelativeTo(null);
                    tab.setVisible(true);
                    System.out.println(result.getBirth());
                    if(tab.isChanged()){
                        account.updateAccount(result, result);
                    }

                }
            }
        });
        return panel;
    }
}
