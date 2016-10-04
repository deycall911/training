package com.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.File;

@Entity
public class Images {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private File image;

    public Images() {
    }

    public Images(String name, File image) {
        this.image = image;
        this.name = name;
    }

    public File getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
