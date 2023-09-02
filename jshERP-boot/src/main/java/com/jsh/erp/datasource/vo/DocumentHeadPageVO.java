package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DocumentHeadPageVO  {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "客户id")
    private Long supplierId;

    @ApiModelProperty(value = "客户名称")
    private String supplierName;

    @ApiModelProperty(value = "订单编号")
    private String number;

    @ApiModelProperty(value = "车牌号")
    private String carNumber;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "入库单，包类型 1-全托 2-半托")
    private Integer packageType;

    @ApiModelProperty(value = "巴恰费")
    private BigDecimal handlingFee;

    @ApiModelProperty(value = "服务费")
    private BigDecimal serverFee;

    @ApiModelProperty(value = "车费")
    private BigDecimal carFee;

    @ApiModelProperty(value = "送货人")
    private String sender;

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "创建时间")
    private LocalDate createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

}
