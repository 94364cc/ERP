package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Depot {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "区域")
    private String area;

    @ApiModelProperty(value = "仓储费")
    private BigDecimal warehousing;

    @ApiModelProperty(value = "负责人")
    private Long principal;

    @ApiModelProperty(value = "排序")
    private String sort;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "租户")
    private Long tenantId;

    @ApiModelProperty(value = "删除标记，0未删除，1删除")
    private String deleteFlag;


}