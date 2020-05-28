package com.example.poi.service.impl;

import com.example.poi.Dao.TestDao;
import com.example.poi.entity.Test;
import com.example.poi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao dao;

    @Override
    public Test save(Test entity) {
        Test save = dao.save(entity);
        return save;
    }



}