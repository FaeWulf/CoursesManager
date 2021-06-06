package com.faewulf.application.semester;

import Database.semester;
import com.faewulf.application.Util.doubleCheck;
import com.faewulf.application.allData;
import com.model.semesterDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class semesterPanel {
    private JPanel panel1;
    private JButton editButton;
    private JButton createButton;
    private JButton deleteButton;
    private JScrollPane scrpane;

    public JPanel newPanel() {
        JTable[] tableDB = {semester.toTable()};
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);

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
                create tab = new create(null);
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                if(tab.isOk){
                    semester.createSemester(tab.result);
                    tableDB[0].setModel(semester.modelUpdate());
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String index =(String) tableDB[0].getValueAt(tableDB[0].getSelectedRow(), 0);
                int year =(int) tableDB[0].getValueAt(tableDB[0].getSelectedRow(), 1);

                semesterDB current = allData.semesterList.stream().filter(E -> E.getId().equals(index) && E.getYear() == year).findFirst().get();
                create tab = new create(current);
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                if(tab.isOk){
                    semester.updateSemester(tab.result, current);
                    current.setId(tab.result.getId());
                    current.setYear(tab.result.getYear());
                    current.setStart(tab.result.getStart());
                    current.setEnd(tab.result.getEnd());
                    tableDB[0].setModel(semester.modelUpdate());

                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doubleCheck tab = new doubleCheck("Are you sure to delete all selected semesters?");
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                if(tab.isAccept()){
                    int[] rows = tableDB[0].getSelectedRows();
                    for (int row : rows) {
                        String index = (String) tableDB[0].getValueAt(row, 0);
                        int year = (int) tableDB[0].getValueAt(row, 1);
                        semesterDB temp = allData.semesterList.stream().filter(E -> E.getId().equals(index) && E.getYear() == year).findFirst().get();
                        if(temp != null){
                            semester.deleteSemester(temp);
                        }
                    }
                    tableDB[0].setModel(semester.modelUpdate());
                }

            }
        });

        return panel1;
    }
}
