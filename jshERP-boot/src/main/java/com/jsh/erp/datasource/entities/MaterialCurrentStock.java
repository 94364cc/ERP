package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("jsh_material_current_stock")
public class MaterialCurrentStock {
    private Long id;

    private Long materialId;

    private Long depotId;

    private Long supplierId;

    private BigDecimal currentNumber;

    private Long tenantId;

    private String deleteFlag;

}