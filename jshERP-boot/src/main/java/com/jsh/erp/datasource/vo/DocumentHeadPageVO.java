package com.jsh.erp.datasource.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsh.erp.datasource.entities.DocumentHead;
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

    @ApiModelProperty(value = "入库单，包类型 1-全托 2-半托")
    private Integer packageType;

    @ApiModelProperty(value = "创建时间")
    private LocalDate createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

}
