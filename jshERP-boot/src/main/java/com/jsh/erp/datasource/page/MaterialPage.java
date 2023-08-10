package com.jsh.erp.datasource.page;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsh.erp.datasource.entities.Material;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MaterialPage extends Page<Material> {
    @ApiModelProperty(value = "查询参数，商品名称，编号")
    private String queryParam;
}