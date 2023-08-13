package com.jsh.erp.datasource.mappers;

import java.util.List;

import com.jsh.erp.datasource.entities.MaterialInitialStock;

public interface MaterialInitialStockMapperEx {

    int batchInsert(List<MaterialInitialStock> list);

    List<MaterialInitialStock> getListExceptZero();

}