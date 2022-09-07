package com.example.infosystems.model.entity;

import com.example.infosystems.model.enums.PositionEnum;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class EmployeeEntity extends BaseEntity{

    @Column(length = 30)
    private String firstName;
    @Column(length = 30)
    private String lastName;
    @Column(length = 10)
    private String EGN;
    private Byte age;
    @Column(length = 35)
    private String username;
    private String password;
    private String token;

    @ManyToOne
    private DepartmentEntity department;

    @Enumerated(EnumType.STRING)
    private PositionEnum position;

    public EmployeeEntity() {
    }
    public EmployeeEntity(String firstName, String lastName, String EGN, Byte age, String username, String password, DepartmentEntity department, PositionEnum position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.EGN = EGN;
        this.age = age;
        this.username = username;
        this.password = password;
        this.department = department;
        this.position = position;
    }
}
