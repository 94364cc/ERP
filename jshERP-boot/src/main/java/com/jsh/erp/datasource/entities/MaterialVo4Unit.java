package com.jsh.erp.datasource.entities;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaterialVo4Unit extends Material{

    @ApiModelProperty(value = "单位名称")
    private String unitName;

}