package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DocumentPrintVO {

    @ApiModelProperty(value = "票据号")
    private String number;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "入库单，包类型 1-全托 2-半托")
    private String packageTypeName;

    @ApiModelProperty(value = "客户手机号")
    private String telephone;

    @ApiModelProperty(value = "客户名称")
    private String supplierName;

    @ApiModelProperty(value = "车牌号")
    private String carNumber;

    @ApiModelProperty(value = "创建时间")
    private LocalDate createTime;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "单据详情")
    private List<DocumentItemPrintVO> documentItemPrintVOList;

    @ApiModelProperty(value = "数量汇总")
    private Integer numberCount;

    @ApiModelProperty(value = "立方数汇总")
    private BigDecimal volumeCount;

}