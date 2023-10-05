package com.example.infosystems.repository;

import com.example.infosystems.model.entity.FileEntity;
import com.example.infosystems.model.enums.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    Optional<FileEntity> findByFileTypeAndRecordIdAndName(FileType fileType, Integer recordId, String name);
}
