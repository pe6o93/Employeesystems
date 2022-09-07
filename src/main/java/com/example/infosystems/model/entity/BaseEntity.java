package com.example.infosystems.model.entity;


import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean isActive;

    @PrePersist
    void activeUserAfterCreate(){
        this.isActive=true;
    }
}
