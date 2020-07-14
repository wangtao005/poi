package com.mypoi.poi.service.impl;

import com.mypoi.poi.Dao.TestDao;
import com.mypoi.poi.entity.Test;
import com.mypoi.poi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao dao;

    @Override
    public Test save(Test entity) {
        Test save = dao.save(entity);
        return save;
    }

    @Override
    public List<Test> getData() {
        List<Test> list = dao.findAll();
        return list;
    }


}