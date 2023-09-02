package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.jsh.erp.datasource.entities.InDocumentPrint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InDocumentPrintVO extends DocumentPrintVO{

    @ApiModelProperty(value = "打印")
    List<InDocumentPrint> inDocumentPrints;

}