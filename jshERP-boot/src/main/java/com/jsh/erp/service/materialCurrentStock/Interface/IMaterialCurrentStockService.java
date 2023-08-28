package com.jsh.erp.service.materialCurrentStock.Interface;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.vo.DocumentItemPrintVO;
import io.swagger.models.auth.In;

public interface IMaterialCurrentStockService extends IService<MaterialCurrentStock> {

    /**
     * 新增单据详情
     * @param materialCurrentStock
     */
    void add(MaterialCurrentStock materialCurrentStock);

    /**
     * 新更新单据详情
     * @param materialCurrentStock
     * @param oldOperNumber
     */
    void update(MaterialCurrentStock materialCurrentStock,Integer oldOperNumber);

    /**
     * 新更新单据详情
     * @param materialCurrentStock
     */
    void delete(MaterialCurrentStock materialCurrentStock);

    /**
     * 根据商品详情查询
    ntegeraterialId
     */
    MaterialCurrentStock getByMaterialId(Long depotId, Long materialId);
}
