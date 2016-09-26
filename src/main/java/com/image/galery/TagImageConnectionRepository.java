package com.image.galery;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagImageConnectionRepository extends CrudRepository<TagImageConnection , Long> {

    @Query("SELECT tagId FROM TagImageConnection WHERE imageId=?1")
    List<Integer> getTagIdsFromImageId(Integer imageId);

    @Query("SELECT imageId FROM TagImageConnection WHERE tagId=?1")
    List<Integer> getImageIdsFromTagId(Integer tagId);

    @Query("SELECT max(id) FROM TagImageConnection")
    Integer getHighestIdValue();
}
