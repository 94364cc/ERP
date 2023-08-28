package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MaterialCurrentStock {
    private Long id;

    private Long materialId;

    private Long depotId;

    private Long supplierId;

    private BigDecimal currentNumber;

    private Long tenantId;

    private String deleteFlag;

}