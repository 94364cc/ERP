package com.jsh.erp.datasource.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsh.erp.datasource.entities.Material;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaterialWithStockPage extends Page<Material> {
        @ApiModelProperty(value = "客户id")
        private Long supplierId;

        @ApiModelProperty(value = "仓库id")
        private Long depotId;

        @ApiModelProperty(value = "商品id")
        private Long materialId;

        @ApiModelProperty(value = "商品名称/款号")
        private String queryParam;
}