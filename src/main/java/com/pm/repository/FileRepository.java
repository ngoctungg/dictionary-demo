package com.pm.repository;

import com.pm.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findByIdAndPost_Id(Integer id, Integer postId);

    @Query("select f from FileEntity f where f.id in :ids and f.post.id = :postId")
    List<FileEntity> getAllByIds(List<Integer> ids, Integer postId);
}
