package com.faewulf.application.semester;

import Database.hp;
import Database.semester;
import com.faewulf.application.Util.doubleCheck;
import com.faewulf.application.allData;
import com.model.HpDB;
import com.model.KydkhpDB;
import com.model.semesterDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Database.kydkhp;
import com.model.subjectDB;

public class semesterPanel {
    private JPanel panel1;
    private JButton editButton;
    private JButton createButton;
    private JButton deleteButton;
    private JScrollPane scrpane;
    private JScrollPane scrpaneSubject;
    private JButton addSubjectButton;
    private JTextField textField1;
    private JButton enableForCurrentSemesterButton;
    private JButton createNewSessionForButton;
    private JScrollPane scrpaneCourses;
    private JScrollPane scrpaneSessions;
    private JButton removeButton;
    private JButton deleteSessionButton;

    public JPanel newPanel() {
        JTable[] tableDB = {semester.toTable()};
        deleteSessionButton.setEnabled(false);
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        createNewSessionForButton.setEnabled(false);
        tableDB[0].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDB[0].setAutoCreateRowSorter(true);
        JTable[] tableKydkhp = {kydkhp.toTable()};
        JTable[] tableCourse = {hp.toTable(-1)};
        tableKydkhp[0].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrpaneCourses.setViewportView(tableCourse[0]);
        scrpane.setViewportView(tableDB[0]);
        scrpaneSessions.setViewportView(tableKydkhp[0]);
        removeButton.setEnabled(false);
        addSubjectButton.setEnabled(false);


        tableDB[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(tableDB[0].getSelectedRows().length == 1){
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    createNewSessionForButton.setEnabled(true);
                }
                else
                if(tableDB[0].getSelectedRows().length > 1){
                    deleteButton.setEnabled(true);
                    editButton.setEnabled(false);
                }
                else {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    createNewSessionForButton.setEnabled(false);
                }
            }
        });

        tableKydkhp[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(tableKydkhp[0].getSelectedRows().length != 1){
                    addSubjectButton.setEnabled(false);
                }
                else
                    addSubjectButton.setEnabled(true);
                if(tableKydkhp[0].getSelectedRow() != -1){
                    int ID = (int) tableKydkhp[0].getValueAt(tableKydkhp[0].getSelectedRow(), 0);
                    int kyID = allData.kydkhpList.stream().filter(E -> E.getId() == ID).findFirst().get().getId();
                    List<subjectDB> listNotIn = new ArrayList<>();
                    listNotIn = hp.toListNotIn(kyID);
                    addSubjectButton.setEnabled(listNotIn.size() >= 1);
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

        tableCourse[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                removeButton.setEnabled(tableCourse[0].getSelectedRows().length > 0);
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
                doubleCheck tab = new doubleCheck("Are you sure to delete selected semester?");
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

        createNewSessionForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = (String) tableDB[0].getValueAt(tableDB[0].getSelectedRow(),0);
                int year = (int) tableDB[0].getValueAt(tableDB[0].getSelectedRow(),1);
                KydkhpDB temp = new KydkhpDB();
                temp.setSemesterId(ID);
                temp.setYear(year);
                temp.setStart(Timestamp.valueOf("2020-1-1 00:00:00"));
                temp.setEnd(Timestamp.valueOf("2020-1-1 00:00:00"));
                kydkhp.createKydkhp(temp);

                tableKydkhp[0].setModel(kydkhp.modelUpdate());
            }
        });

        tableKydkhp[0].getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            	if(tableKydkhp[0].getSelectedRow() == -1){
            	    deleteSessionButton.setEnabled(false);
                    return;
                }
                int ID = (int) tableKydkhp[0].getValueAt(tableKydkhp[0].getSelectedRow(), 0);
                int kyID = allData.kydkhpList.stream().filter(E -> E.getId() == ID).findFirst().get().getId();
                tableCourse[0].setModel(hp.modelUpdate(kyID));
                deleteSessionButton.setEnabled(true);
            }
        });

        addSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ID = (int) tableKydkhp[0].getValueAt(tableKydkhp[0].getSelectedRow(), 0);
                int kyID = allData.kydkhpList.stream().filter(E -> E.getId() == ID).findFirst().get().getId();
                addSubject tab = new addSubject(kyID);
                tab.pack();
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);

                if(tab.isOK){
                    hp.createhp(tab.result);
                    tableCourse[0].setModel(hp.modelUpdate(kyID));

                    List<subjectDB> listNotIn = new ArrayList<>();
                    listNotIn = hp.toListNotIn(kyID);
                    addSubjectButton.setEnabled(listNotIn.size() != 0);
                }
            }
        });

        deleteSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ID = (int) tableKydkhp[0].getValueAt(tableKydkhp[0].getSelectedRow(), 0);
                KydkhpDB kyID = allData.kydkhpList.stream().filter(E -> E.getId() == ID).findFirst().get();
                kydkhp.deleteKydkhp(kyID);
                tableKydkhp[0].setModel(kydkhp.modelUpdate());
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doubleCheck tab = new doubleCheck("Are you sure to remove all selected subjects?");
                tab.pack();
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
                int ID = (int) tableKydkhp[0].getValueAt(tableKydkhp[0].getSelectedRow(), 0);
                int kyID = allData.kydkhpList.stream().filter(E -> E.getId() == ID).findFirst().get().getId();
                if(tab.isAccept()){
                    int[] rows = tableCourse[0].getSelectedRows();
                    for (int row : rows) {
                        int id = (int) tableCourse[0].getValueAt(row, 0);
                        HpDB temp = allData.hpList.stream().filter(E -> E.getId() == id).findFirst().get();
                        hp.deletehp(temp);
                    }
                    tableCourse[0].setModel(hp.modelUpdate(kyID));
                    List<subjectDB> listNotIn = new ArrayList<>();
                    listNotIn = hp.toListNotIn(kyID);
                    addSubjectButton.setEnabled(listNotIn.size() != 0);
                }
            }
        });

        return panel1;
    }
}
