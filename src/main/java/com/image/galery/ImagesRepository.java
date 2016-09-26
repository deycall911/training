package com.image.galery;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;


public interface ImagesRepository extends CrudRepository<Images, Long> {

    @Query("SELECT name FROM Images WHERE id=?1")
    String getNameForId(Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Images SET name=?1 WHERE id=?2")
    void updateNameWhereId(String name, Long id);

    @Query("SELECT image FROM Images WHERE id=?1")
    File getImageWhereId(Integer id);

    @Query("SELECT max(id) FROM Images ")
    Integer getHighestIdValue();

    @Query("SELECT id FROM Images")
    List<Integer> getAllIds();


}
