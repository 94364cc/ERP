package com.jsh.erp.datasource.vo;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jsh.erp.datasource.entities.Material;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaterialWithInitStockVO{

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "客户名称")
    private String supplierName;

    @ApiModelProperty(value = "仓库名称")
    private String depotName;

    @ApiModelProperty(value = "商品类别名称")
    @TableField(exist = false)
    private String categoryName;

    @ApiModelProperty(value = "款号")
    private String model;

    @ApiModelProperty(value = "箱规，长*宽*高")
    private String standard;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "备注")
    private String remark;
}