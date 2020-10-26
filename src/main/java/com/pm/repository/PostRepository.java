package com.pm.repository;

import com.pm.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    @Query(value = "SELECT id,title,summary,category_id,created_at,created_by,updated_at,updated_by,NULL AS 'content', " +
            "MATCH(content) AGAINST (:keyword) AS score " +
            "FROM post " +
            "WHERE MATCH(content) AGAINST (:keyword) > :accuracy ORDER BY score DESC ", nativeQuery = true)
    List<PostEntity> searchPost(@Param("keyword") String q, @Param("accuracy") double accuracy);
}
