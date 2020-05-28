package com.example.poi.entity;

import lombok.Data;

import java.util.List;

@Data
public class ReturnData {
    private int code;
    private String msg;
    private Integer count;
    private List<Test> data;
}
