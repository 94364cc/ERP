package com.jsh.erp.datasource.entities;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("jsh_material_category")
public class NMaterialCategory {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "等级")
    @TableField(value = "category_level")
    private Short categoryLevel;

    @ApiModelProperty(value = "上级id")
    @TableField(value = "parent_id")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序")
    @TableField(value = "sort")
    private String sort;

    @ApiModelProperty(value = "编号")
    @TableField(value = "serial_no")
    private String serialNo;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "租户id")
    @TableField(value = "tenant_id")
    private Long tenantId;

    @ApiModelProperty(value = "删除标记，0未删除，1删除")
    @TableField(value = "delete_flag")
    private String deleteFlag;

}