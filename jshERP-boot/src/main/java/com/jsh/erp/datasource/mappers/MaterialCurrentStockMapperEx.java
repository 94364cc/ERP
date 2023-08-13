package com.jsh.erp.datasource.mappers;

import java.util.List;

import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import org.apache.ibatis.annotations.Param;

public interface MaterialCurrentStockMapperEx {

    int batchInsert(List<MaterialCurrentStock> list);

    List<MaterialCurrentStock> getCurrentStockMapByIdList(
            @Param("materialIdList") List<Long> materialIdList);
}