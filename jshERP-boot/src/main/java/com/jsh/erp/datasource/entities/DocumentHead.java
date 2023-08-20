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

    @ApiModelProperty(value = "票据号")
    @TableField(value = "number")
    private Long number;

    @ApiModelProperty(value = "类型(出库/入库/库存盘点单/退货入库/库存调拨)")
    @TableField(value = "type")
    private Long type;

    @ApiModelProperty(value = "仓库id")
    @TableField(value = "depot_id")
    private BigDecimal depotId;

    @ApiModelProperty(value = "入库单，包类型 1-全托 2-半托")
    @TableField(value="package_type")
    private Integer packageType;

    @ApiModelProperty(value = "客户id")
    @TableField(value = "supplier_id")
    private Long supplierId;

    @ApiModelProperty(value = "车牌号")
    @TableField(value = "car_number")
    private String carNumber;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "creator")
    private String creator;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty(value = "租户id")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @ApiModelProperty(value = "删除标记，0未删除，1删除")
    @TableField(value = "delete_flag")
    private String deleteFlag;

}