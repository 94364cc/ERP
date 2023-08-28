package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DocumentItemPrintVO {

    @ApiModelProperty(value = "商品款号")
    private String model;

    @ApiModelProperty(value = "商品箱子数量")
    private Integer operNumber;

    @ApiModelProperty(value = "单位体积，长*宽*高")
    private String standard;

    @ApiModelProperty(value = "体积")
    private BigDecimal volume;

    @ApiModelProperty(value = "仓库名称")
    private String depotName;

    @ApiModelProperty(value = "描述")
    private String remark;


}