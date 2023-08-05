package com.jsh.erp.datasource.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Material {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "款号")
    private String model;

    @ApiModelProperty(value = "单位体积")
    private String standard;

    @ApiModelProperty(value = "单位id")
    private Long unitId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "租户，暂时没用")
    private Long tenantId;

    @ApiModelProperty(value = "逻辑删除标记 0-否 1-是")
    private String deleteFlag;

}