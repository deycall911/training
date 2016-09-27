package com.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TagImageConnection {


    private Integer imageId;
    private Integer tagId;


    @Id
    @GeneratedValue
    private Long id;

    public TagImageConnection() {

    }

    public TagImageConnection(Integer imageId, Integer tagId) {
        this.imageId = imageId;
        this.tagId = tagId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public Long getId() {
        return id;
    }
}
