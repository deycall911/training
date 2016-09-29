package com.db.repository;

import com.db.entity.Tags;
import org.springframework.data.repository.CrudRepository;

public interface TagsRepository extends CrudRepository<Tags, Integer> {
    Tags findByName(String name);
}
