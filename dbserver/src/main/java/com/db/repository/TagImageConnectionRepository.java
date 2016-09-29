package com.db.repository;

import com.db.entity.TagImageConnection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagImageConnectionRepository extends CrudRepository<TagImageConnection, Integer> {

    List<TagImageConnection> findByImageId(Integer imageId);
}
