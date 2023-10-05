package com.example.infosystems.repository;

import com.example.infosystems.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Query("SELECT e FROM EmployeeEntity e WHERE " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%',:query, '%')) " +
            "AND e.isActive IS TRUE " +
            "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%',:query, '%')) " +
            "AND e.isActive IS TRUE ")
    List<EmployeeEntity> searchEmployee(String query);

    Boolean existsEmployeeEntityByEgn(String EGN);

    Optional<EmployeeEntity> findEmployeeEntityByUsername(String username);

    @Query(value = "SELECT e FROM EmployeeEntity e where e.username = ?1 and e.password = ?2 ")
    Optional<EmployeeEntity> login(String username,String password);

    Optional<EmployeeEntity> findByToken(String token);
}
