package com.shijianwei.api.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Goods implements Serializable {
    private Integer id;

    private String goodsname;

    private Integer count;

}