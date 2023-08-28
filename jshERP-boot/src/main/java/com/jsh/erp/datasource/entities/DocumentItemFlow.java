package com.jsh.erp.datasource.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DocumentItemFlow {
    @ApiModelProperty(value = "单据编号")
    private String number;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品款号")
    private String model;

    @ApiModelProperty(value = "数量")
    private Integer operNumber;

    @ApiModelProperty(value = "仓库名称")
    private String depotName;


}