package com.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TagImageRelation {

    private Integer imageId;
    private Integer tagId;

    @Id
    @GeneratedValue
    private Integer id;

    public TagImageRelation() {
    }

    public TagImageRelation(Integer imageId, Integer tagId) {
        this.imageId = imageId;
        this.tagId = tagId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public Integer getId() {
        return id;
    }
}
