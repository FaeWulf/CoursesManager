package com.model;

import javax.persistence.*;

@Entity
@Table(name = "subject", schema = "coursesmanager", catalog = "")
public class subjectDB {
    private int id;
    private String name;
    private int credit;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "credit", nullable = false)
    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        subjectDB subjectDB = (com.model.subjectDB) o;

        if (id != subjectDB.id) return false;
        if (credit != subjectDB.credit) return false;
        if (name != null ? !name.equals(subjectDB.name) : subjectDB.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + credit;
        return result;
    }
}
