package com.faewulf.application;

import java.util.List;
import com.model.accountDB;
import Database.*;
public class App {
    public static void main(String[] args) {
        List<accountDB> list = account.getAccountList();
        Main app = new Main();
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }
}
