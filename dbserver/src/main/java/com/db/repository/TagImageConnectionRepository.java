package com.db.repository;

import com.db.entity.TagImageRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagImageConnectionRepository extends CrudRepository<TagImageRelation, Integer> {

    List<TagImageRelation> findByImageId(Integer imageId);
}
