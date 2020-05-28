package com.example.poi.service;

import com.example.poi.entity.Test;

import java.util.List;

public interface TestService {

    Test save(Test Entity);

    List<Test> getData();
}
