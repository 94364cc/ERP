package com.jsh.erp.service.materialCurrentStock.Interface;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.entities.MaterialCurrentStockQuery;
import com.jsh.erp.datasource.vo.DocumentItemPrintVO;
import io.swagger.models.auth.In;

public interface IMaterialCurrentStockService extends IService<MaterialCurrentStock> {

    /**
     * 新增单据详情
     * @param materialCurrentStock
     */
    void add(MaterialCurrentStock materialCurrentStock);

    /**
     * 新入库更新单据详情
     * @param materialCurrentStock
     * @param oldOperNumber
     */
    void inUpdate(MaterialCurrentStock materialCurrentStock,Integer oldOperNumber);


    /**
     * 新更新单据详情
     * @param materialCurrentStock
     * @param oldOperNumber
     */
    void outUpdate(MaterialCurrentStock materialCurrentStock,Integer oldOperNumber);

    /**
     * 新更新单据详情
     * @param materialCurrentStock
     */
    void delete(MaterialCurrentStock materialCurrentStock);

    /**
     * 新根据主体id删除单据详情
     * @param materialCurrentStocks
     */
    void deleteBatch(List<MaterialCurrentStock> materialCurrentStocks);

    /**
     * 新根据主体id加上单据详情（出库单场景）
     * @param materialCurrentStocks
     */
    void addBatch(List<MaterialCurrentStock> materialCurrentStocks);

    /**
     * 根据商品详情查询
    ntegeraterialId
     */
    MaterialCurrentStock getByMaterialId(Long depotId, Long materialId,Long supplierId);

    /**
     * 根据商品详情查询
     ntegeraterialId
     */
    List<MaterialCurrentStock> getByExample(MaterialCurrentStockQuery query);
}
