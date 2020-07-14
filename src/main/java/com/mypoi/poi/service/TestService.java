package com.mypoi.poi.service;

import com.mypoi.poi.entity.Test;

import java.util.List;

public interface TestService {

    Test save(Test Entity);

    List<Test> getData();
}
