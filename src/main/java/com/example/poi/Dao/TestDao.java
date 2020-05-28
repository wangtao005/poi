package com.example.poi.Dao;

import com.example.poi.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao extends JpaSpecificationExecutor<Test>, JpaRepository<Test, Integer> {
}
