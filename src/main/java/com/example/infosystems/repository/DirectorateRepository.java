package com.example.infosystems.repository;

import com.example.infosystems.model.entity.DirectorateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DirectorateRepository extends JpaRepository<DirectorateEntity, Integer> {

    @Query("SELECT d FROM DirectorateEntity d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%',:query, '%')) " +
            "AND d.isActive IS TRUE " +
            "OR LOWER(d.description) LIKE LOWER(CONCAT('%',:query, '%')) " +
            "AND d.isActive IS TRUE")
    List<DirectorateEntity> searchDirectorates(String query);



}
