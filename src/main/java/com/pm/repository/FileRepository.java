package com.pm.repository;

import com.pm.entity.FileEntity;
import com.pm.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}