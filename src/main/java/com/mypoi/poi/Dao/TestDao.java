package com.mypoi.poi.Dao;

import com.mypoi.poi.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends JpaSpecificationExecutor<Test>, JpaRepository<Test, Integer> {
}
