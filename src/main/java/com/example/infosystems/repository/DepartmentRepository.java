package com.example.infosystems.repository;

import com.example.infosystems.model.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {

    Optional<DepartmentEntity> findDepartmentEntityByName(String name);

    @Query("SELECT d FROM DepartmentEntity d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%',:query, '%')) " +
            "AND d.isActive IS TRUE " +
            "OR LOWER(d.description) LIKE LOWER(CONCAT('%',:query, '%'))" +
            "AND d.isActive is TRUE")
    List<DepartmentEntity> searchDepartments(String query);
}
