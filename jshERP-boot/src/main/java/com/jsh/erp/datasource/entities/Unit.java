package com.jsh.erp.datasource.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Unit {
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "基本单位")
    private String basicUnit;

    @ApiModelProperty(value = "租户")
    private Long tenantId;

    @ApiModelProperty(value = "逻辑删除标记")
    private String deleteFlag;
}

