package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
@TableName("jsh_document_head")
public class DocumentHead {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "单号")
    @TableField(value = "number")
    private String number;

    @ApiModelProperty(value = "类型(1-出库 2-入库 3-库存盘点单 4-退货入库 5-库存调拨)")
    @TableField(value = "type")
    private Integer type;

    @ApiModelProperty(value = "类型名称")
    @TableField(exist = false)
    private String typeName;

    @ApiModelProperty(value = "入库单，包类型 1-全托 2-半托")
    @TableField(value="package_type")
    private Integer packageType;

    @ApiModelProperty(value = "入库单，包类型 1-全托 2-半托")
    @TableField(exist = false)
    private String packageTypeName;

    @ApiModelProperty(value = "客户id")
    @TableField(value = "supplier_id")
    private Long supplierId;

    @ApiModelProperty(value = "车牌号")
    @TableField(value = "car_number")
    private String carNumber;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private LocalDate createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "creator")
    private String creator;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty(value = "租户id")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @ApiModelProperty(value = "巴恰费")
    @TableField(value = "handling_fee")
    private BigDecimal handlingFee;

    @ApiModelProperty(value = "服务费")
    @TableField(value = "server_fee")
    private BigDecimal serverFee;

    @ApiModelProperty(value = "车费")
    @TableField(value = "car_fee")
    private BigDecimal carFee;

    @ApiModelProperty(value = "送货人")
    @TableField(value = "sender")
    private String sender;

    @ApiModelProperty(value = "收货人")
    @TableField(value = "receiver")
    private String receiver;

    @ApiModelProperty(value = "删除标记，0未删除，1删除")
    @TableField(value = "delete_flag")
    private String deleteFlag;

}