package com.jsh.erp.datasource.entities;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ApiModelProperty(value = "商品类别名称")
    @TableField(exist = false)
    private String categoryName;

    @ApiModelProperty(value = "款号")
    @TableField(value = "model")
    private String model;

    @NotBlank(message = "箱规不能为空")
    @ApiModelProperty(value = "箱规，长*宽*高")
    @TableField(value = "standard")
    private String standard;

    @ApiModelProperty(value = "单位id")
    @TableField(value = "unit_id")
    private Long unitId;

    @ApiModelProperty(value = "单位id")
    @TableField(exist = false)
    private String unitName;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty(value = "租户，暂时没用")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @ApiModelProperty(value = "逻辑删除标记 0-否 1-是")
    @TableField(value = "delete_flag")
    @TableLogic
    private String deleteFlag;

}