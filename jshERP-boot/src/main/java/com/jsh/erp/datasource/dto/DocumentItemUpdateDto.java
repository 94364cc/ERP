package com.jsh.erp.datasource.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DocumentItemUpdateDto {
    @ApiModelProperty(value = "主体id")
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long materialId;

    @ApiModelProperty(value = "headId")
    private Long headId;

    @ApiModelProperty(value = "调整方式 1-新增 2-减少")
    private Integer changeType;

    @ApiModelProperty(value = "数量")
    private Integer operNumber;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "仓库ID")
    private Long depotId;

    @ApiModelProperty(value = "调拨时，对方仓库Id")
    private Long anotherDepotId;

    @ApiModelProperty(value = "租户id")
    @TableField(value = "tenant_id")
    private Long tenantId=1L;

}
