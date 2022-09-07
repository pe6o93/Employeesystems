package com.example.infosystems.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "directorates")
@Getter @Setter
public class DirectorateEntity extends BaseEntity {


    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    private EmployeeEntity director;

}
