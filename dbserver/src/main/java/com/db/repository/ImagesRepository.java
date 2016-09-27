package com.db.repository;

import com.db.entity.Images;
import org.springframework.data.repository.CrudRepository;


public interface ImagesRepository extends CrudRepository<Images, Integer> {

//    @Query("SELECT name FROM Images WHERE id=?1")
//    String getNameForId(Integer id);
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE Images SET name=?1 WHERE id=?2")
//    void updateNameWhereId(String name, Long id);
//
//    @Query("SELECT front FROM Images WHERE id=?1")
//    File getImageWhereId(Integer id);
//
//    @Query("SELECT max(id) FROM Images ")
//    Integer getHighestIdValue();
//
//    @Query("SELECT id FROM Images")
//    List<Integer> getAllIds();


}
