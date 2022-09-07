package com.example.infosystems.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class DepartmentEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private DirectorateEntity directorate;

    @OneToOne(fetch = FetchType.LAZY)
    private EmployeeEntity boss;
}
