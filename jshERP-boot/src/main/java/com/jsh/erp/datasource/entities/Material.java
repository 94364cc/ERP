package com.jsh.erp.datasource.entities;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("jsh_material")
public class Material {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "列表id")
    @TableField(value = "category_id")
    private Long categoryId;

    @ApiModelProperty(value = "列表id")
    @TableField(exist = false)
    private String categoryName;

    @ApiModelProperty(value = "款号")
    @TableField(value = "model")
    private String model;

    @ApiModelProperty(value = "单位体积，单位立方米")
    @TableField(value = "standard")
    private String standard;

    @ApiModelProperty(value = "单位id")
    @TableField(value = "unit_id")
    private Long unitId;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty(value = "租户，暂时没用")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除标记 0-否 1-是")
    @TableField(value = "delete_flag")
    private String deleteFlag;

}