package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaterialCurrentStockPage extends Page<MaterialCurrentStock> {
    @ApiModelProperty(value = "客户id")
    private Long supplierId;

    @ApiModelProperty(value = "仓库id")
    private Long depotId;

    @ApiModelProperty(value = "商品id")
    private Long materialId;
}