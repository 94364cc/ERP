package com.jsh.erp.datasource.entities;

import com.jsh.erp.datasource.vo.DocumentPrintVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InDocumentPrint{


    @ApiModelProperty(value = "款号")
    private String model;

    @ApiModelProperty(value = "数量")
    private Integer operNumber;


}