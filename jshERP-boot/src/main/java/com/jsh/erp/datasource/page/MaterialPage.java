package com.jsh.erp.datasource.page;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsh.erp.datasource.entities.Material;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaterialPage extends Page<Material> {
    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "款号")
    private String model;
}