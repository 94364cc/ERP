package com.jsh.erp.datasource.entities;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("jsh_sequence")
public class Sequence {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "序列名称")
    @TableField(value = "seq_name")
    private String seqName;

    @ApiModelProperty(value = "记录日期")
    @TableField(value = "date")
    private LocalDate date;

    @ApiModelProperty(value = "当前值")
    @TableField(value = "current_val")
    private Integer currentVal;

    @ApiModelProperty(value = "增长步数")
    @TableField(value="increment_val")
    private Integer incrementVal;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private Long remark;
}