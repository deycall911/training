package com.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tags {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    public Tags() {
    }

    public Tags(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
