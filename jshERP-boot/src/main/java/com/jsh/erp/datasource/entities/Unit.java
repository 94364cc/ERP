package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Unit {
    private Long id;

    private String name;

    private String basicUnit;

    private Long tenantId;

    private String deleteFlag;
}

