package com.pm.repository;

import com.pm.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query("SELECT c FROM CategoryEntity c JOIN c.posts")
    @EntityGraph(attributePaths = {"posts"})
    List<CategoryEntity> findAllCategoriesAndPost();
}
