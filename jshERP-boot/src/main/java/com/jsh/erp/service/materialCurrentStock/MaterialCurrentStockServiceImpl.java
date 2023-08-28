package com.jsh.erp.service.materialCurrentStock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.mappers.MaterialCurrentStockMapper;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.materialCurrentStock.Interface.IMaterialCurrentStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialCurrentStockServiceImpl extends ServiceImpl<MaterialCurrentStockMapper, MaterialCurrentStock> implements
    IMaterialCurrentStockService {

    @Autowired
    INMaterialService materialService;

    @Override
    public void add(MaterialCurrentStock materialCurrentStock) {
        MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId());
        if(ObjectUtil.isNull(query)){
            //没有记录就新增
            this.save(materialCurrentStock);
        }else{
            //有记录就更新
            this.addNumber(query,materialCurrentStock.getCurrentNumber());
        }
    }

    /**
     * 减去原来的数量，加上新的数量
     * @param materialCurrentStock
     */
    @Override
    public void update(MaterialCurrentStock materialCurrentStock,Integer operNumber) {
        MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId());
        query.getCurrentNumber().subtract(new BigDecimal(operNumber)).add(materialCurrentStock.getCurrentNumber());
        this.updateById(query);
    }


    /**
     * 减去原来的数量
     * @param materialCurrentStock
     */
    @Override
    public void delete(MaterialCurrentStock materialCurrentStock) {
        MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId());
        query.getCurrentNumber().subtract(materialCurrentStock.getCurrentNumber());
        this.updateById(query);
    }

    /**
     * 根据单据id删除全部详情
     * @param materialCurrentStocks
     */
    @Override
    public void deleteBatch(List<MaterialCurrentStock> materialCurrentStocks) {
        if(CollUtil.isEmpty(materialCurrentStocks)){
            return;
        }
        List<MaterialCurrentStock> updateList = new ArrayList<>();
        for(MaterialCurrentStock materialCurrentStock : materialCurrentStocks){
            MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId());
            query.getCurrentNumber().subtract(materialCurrentStock.getCurrentNumber());
            updateList.add(query);
        }
        this.updateBatchById(updateList);
    }

    /**
     * 新增
     * @param old
     * @param addNumber
     * @return
     */
    private synchronized void addNumber(MaterialCurrentStock old,BigDecimal addNumber) {
        if(ObjectUtil.isNull(addNumber)){
            return;
        }
        old.setCurrentNumber(old.getCurrentNumber().add(addNumber));
        this.updateById(old);
    }

    /**
     * 根据商品id查询
     * @param depotId
     * @param materialId
     * @return
     */
    @Override
    public MaterialCurrentStock getByMaterialId(Long depotId, Long materialId) {
         MaterialCurrentStock materialCurrentStock = this.getOne(Wrappers.lambdaQuery(MaterialCurrentStock.class)
             .eq(MaterialCurrentStock::getMaterialId,materialId)
             .eq(MaterialCurrentStock::getDepotId,depotId)
         );
         return materialCurrentStock;
    }
}
