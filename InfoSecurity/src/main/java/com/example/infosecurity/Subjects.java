package com.example.infosecurity;

import javafx.collections.ObservableList;

public class Subjects {
    String subject;
    ObservableList<Objectkey> table;

    public Subjects(String subject, ObservableList<Objectkey> table) {
        this.subject = subject;
        this.table = table;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ObservableList<Objectkey> getTable() {
        return table;
    }

    public void setTable(ObservableList<Objectkey> table) {
        this.table = table;
    }
}
