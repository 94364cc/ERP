package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MaterialVo4Unit extends Material{

    private String unitName;

    private String categoryName;

    private BigDecimal stock;


}