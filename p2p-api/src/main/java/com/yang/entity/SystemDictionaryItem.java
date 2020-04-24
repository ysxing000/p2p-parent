package com.yang.entity;

import lombok.Data;

import java.util.Map;

@Data
public class SystemDictionaryItem {

    private Long id;

    private Long parentId;//系统mulu

    private String title;//名称

    private String tvalue;//值

    private Integer sequence;

    private String intro;


    /*public String getJsonSrring(){

        Map<String,Object>

    }*/







}
