package com.faewulf.application.subject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.*;
import com.faewulf.application.Util.doubleCheck;
import com.faewulf.application.allData;
import com.model.subjectDB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class subjectPanel {
    private JPanel panel1;
    private JScrollPane scrpane;
    private JButton editButton;
    private JButton createButton;
    private JButton deleteButton;
    private JTextField textField1;

    public JPanel newPanel(){

       editButton.setEnabled(false);
       deleteButton.setEnabled(false);

        JTable tableDB[] = {subject.toTable()};
        tableDB[0].setAutoCreateRowSorter(true);
        scrpane.setViewportView(tableDB[0]);

        tableDB[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(tableDB[0].getSelectedRows().length == 1){
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
                else
                    if(tableDB[0].getSelectedRows().length > 1){
                        deleteButton.setEnabled(true);
                        editButton.setEnabled(false);
                    }
                    else {
                        editButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                    }
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                create tab = new create();
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);

                if(tab.isOk){
                    subject.createSubject(tab.result);
                    tableDB[0].setModel(subject.modelUpdate());
                }

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doubleCheck tab = new doubleCheck("Are you sure to delete all selected sucjects?");
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                if(tab.isAccept()){
                    int[] rows = tableDB[0].getSelectedRows();
                    for (int row : rows) {
                        int id = (int) tableDB[0].getModel().getValueAt(row, 0);
                        subjectDB temp = allData.subjectList.stream().filter(E -> E.getId() == id).findFirst().get();
                        subject.deleteSubject(temp);
                    }
                    tableDB[0].setModel(subject.modelUpdate());
                }

            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subjectDB temp = allData.subjectList.stream().filter(E -> E.getId() == (int) tableDB[0].getModel().getValueAt(tableDB[0].getSelectedRow(), 0)).findFirst().get();
                edit tab = new edit(temp);
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);

                if(tab.isOk){
                    subject.updateSubject(tab.result);
                    tableDB[0].setModel(subject.modelUpdate());
                }
            }
        });



        return panel1;
    }
}
