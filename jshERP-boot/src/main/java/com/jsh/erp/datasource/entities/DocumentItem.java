package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("jsh_document_item")
public class DocumentItem {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "主体id")
    @TableField(value = "head_id")
    private Long headId;

    @ApiModelProperty(value = "商品id")
    @TableField(value = "material_id")
    private Long materialId;

    @ApiModelProperty(value = "商品款号")
    @TableField(exist = false)
    private String model;

    @ApiModelProperty(value = "数量")
    @TableField(value = "oper_number")
    private Integer operNumber;

    @ApiModelProperty(value = "箱规 长*宽*高")
    @TableField(value = "box_size")
    private String boxSize;

    @ApiModelProperty(value = "描述")
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty(value = "仓库ID")
    @TableField(value = "depot_id")
    private Long depotId;

    @ApiModelProperty(value = "仓库名称")
    @TableField(exist = false)
    private String depotName;

    @ApiModelProperty(value = "调拨时，对方仓库Id")
    @TableField(value = "another_depot_id")
    private Long anotherDepotId;

    @ApiModelProperty(value = "仓库名称")
    @TableField(exist = false)
    private String anotherDepotName;

    @ApiModelProperty(value = "批号")
    @TableField(value = "batch_number")
    private String batchNumber;

    @ApiModelProperty(value = "租户id")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @ApiModelProperty(value = "删除标记，0未删除，1删除")
    @TableField(value = "delete_flag")
    private String deleteFlag;

}