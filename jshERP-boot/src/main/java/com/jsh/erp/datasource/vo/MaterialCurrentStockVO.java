package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class MaterialCurrentStockVO {

    private String materialName;

    private String depotName;

    private String supplierName;

    private BigDecimal currentNumber;
}