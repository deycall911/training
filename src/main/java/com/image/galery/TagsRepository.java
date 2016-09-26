package com.image.galery;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagsRepository extends CrudRepository<Tags, Long> {

    @Query("SELECT name FROM Tags WHERE id=?1")
    String getNameById(Integer id);

    @Query("SELECT id FROM Tags WHERE name=?1")
    Integer getIdByName(String name);

    @Query("SELECT max(id) FROM Tags")
    Integer getHighestIdValue();
}
