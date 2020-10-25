package com.pm.repository;

import com.pm.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    @Query(value = "SELECT id,title,summary,category_id,null as content, MATCH(content) AGAINST (:keyword) as score FROM post " +
            "WHERE MATCH(content) AGAINST (:keyword) > :accuracy order by score desc", nativeQuery = true)
    List<PostEntity> searchPost(@Param("keyword") String q, @Param("accuracy") double accuracy);
}
