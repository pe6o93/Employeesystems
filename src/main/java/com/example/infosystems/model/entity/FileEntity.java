package com.example.infosystems.model.entity;

import com.example.infosystems.model.enums.FileStatus;
import com.example.infosystems.model.enums.FileType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "files")
@Getter
@Setter
public class FileEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Length(min = 1, max = 120, message = "File name must be between 3 and 120 chars.")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", length = 55)
    private FileType fileType;

    @Column(name = "record_id", nullable = false, length = 60)
    private Integer recordId;

    @Enumerated(EnumType.STRING)
    @Column(length = 55)
    private FileStatus status;

    @Column(name = "bucket", nullable = false, length = 50)
    private String bucket;
}
