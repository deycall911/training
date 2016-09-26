package com.image.galery;

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

    public Images(Integer id, String name, File image) {
        this.id = id;
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
