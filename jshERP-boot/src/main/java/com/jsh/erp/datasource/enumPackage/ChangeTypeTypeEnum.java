package com.jsh.erp.datasource.enumPackage;

import java.math.BigDecimal;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 1-出库 2-入库 3-库存盘点单 4-退货入库 5-库存调拨
 */
@Getter
@AllArgsConstructor
public enum ChangeTypeTypeEnum {

    ADD(1, "新增"),
    SUB(2, "减少"),
    ;
    final int type;
    final String name;

    public static String getNameById(Integer type){
        return Arrays.stream(values())
            .filter(documentTypeEnum -> documentTypeEnum.getType()== type)
            .map(documentTypeEnum ->documentTypeEnum.getName())
            .findFirst()
            .get();
    }
}
