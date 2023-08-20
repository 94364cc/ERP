package com.jsh.erp.datasource.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.Material;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.datetime.DateFormatter;

@Data
public class DocumentHeadPage extends Page<DocumentHead> {
    @ApiModelProperty(value = "客户id")
    private Long supplierId;

    @ApiModelProperty(value = "仓库id")
    private Long depotId;

    @ApiModelProperty(value = "单据类型 1-出库 2-入库 3-库存盘点单 4-退货入库 5-库存调拨")
    private String type;

    @ApiModelProperty(value = "订单编号")
    private String number;

    @ApiModelProperty(value = "开始时间")
    private LocalDate beginDate;

    @ApiModelProperty(value = "结束时间")
    private LocalDate endDate;

    public void setSupplierId(String supplierId) {
        if(StrUtil.isNotBlank(supplierId)){
            this.supplierId = Long.parseLong(supplierId);
        }
    }

    public void setDepotId(String depotId) {
        if(StrUtil.isNotBlank(depotId)){
            this.depotId = Long.parseLong(depotId);
        }
    }

    public void setBeginDate(String beginDate) {
        if(StrUtil.isNotBlank(beginDate)){
            this.beginDate =LocalDate.parse(beginDate,DateTimeFormatter.ISO_LOCAL_DATE );
        }
    }

    public void setEndDate(String endDate) {
        if(StrUtil.isNotBlank(endDate)){
            this.endDate =LocalDate.parse(endDate,DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }
}
