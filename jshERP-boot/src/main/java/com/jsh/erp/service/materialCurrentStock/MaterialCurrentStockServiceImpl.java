package com.jsh.erp.service.materialCurrentStock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.entities.MaterialCurrentStockPage;
import com.jsh.erp.datasource.mappers.NMaterialCurrentStockMapper;
import com.jsh.erp.datasource.page.MaterialWithStockPage;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.materialCurrentStock.Interface.IMaterialCurrentStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialCurrentStockServiceImpl extends ServiceImpl<NMaterialCurrentStockMapper, MaterialCurrentStock> implements
    IMaterialCurrentStockService {

    @Autowired
    INMaterialService materialService;

    @Override
    public void add(MaterialCurrentStock materialCurrentStock) {
        MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId(),materialCurrentStock.getSupplierId());
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
    public void inUpdate(MaterialCurrentStock materialCurrentStock,Integer operNumber) {
        MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId(),materialCurrentStock.getSupplierId());
        ResultEnum.MATERIAL_STOCK_NOT_EXISTS.notNull(query);
        BigDecimal result = query.getCurrentNumber().subtract(new BigDecimal(operNumber)).add(materialCurrentStock.getCurrentNumber());
        ResultEnum.OUT_DAYU_IN.isTrue(result.compareTo(new BigDecimal(0))>=0);
        query.setCurrentNumber(result);
        this.updateById(query);
    }

    /**
     * 出库单据更新
     * @param materialCurrentStock
     * @param oldOperNumber
     */
    @Override
    public void outUpdate(MaterialCurrentStock materialCurrentStock, Integer oldOperNumber) {
        MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId(),materialCurrentStock.getSupplierId());
        ResultEnum.MATERIAL_STOCK_NOT_EXISTS.notNull(query);
        BigDecimal result =query.getCurrentNumber().add(new BigDecimal(oldOperNumber)).subtract(materialCurrentStock.getCurrentNumber());
        ResultEnum.OUT_DAYU_IN.isTrue(result.compareTo(new BigDecimal(0))>=0);
        query.setCurrentNumber(result);
        this.updateById(query);
    }

    /**
     * 减去原来的数量
     * @param materialCurrentStock
     */
    @Override
    public void delete(MaterialCurrentStock materialCurrentStock) {
        MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId(),materialCurrentStock.getSupplierId());
        ResultEnum.MATERIAL_STOCK_NOT_EXISTS.notNull(query);
        BigDecimal result = query.getCurrentNumber().subtract(materialCurrentStock.getCurrentNumber());
        ResultEnum.OUT_DAYU_IN.isTrue(result.compareTo(new BigDecimal(0))>=0);
        query.setCurrentNumber(result);
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
            MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId(),materialCurrentStock.getSupplierId());
            ResultEnum.MATERIAL_STOCK_NOT_EXISTS.notNull(query);
            BigDecimal result = query.getCurrentNumber().subtract(materialCurrentStock.getCurrentNumber());
            ResultEnum.OUT_DAYU_IN.isTrue(result.compareTo(new BigDecimal(0))>=0);
            query.setCurrentNumber(result);
            updateList.add(query);
        }
        this.updateBatchById(updateList);
    }

    /**
     * 新根据主体id加上单据详情（出库单场景）
     * @param materialCurrentStocks
     */
    @Override
    public void addBatch(List<MaterialCurrentStock> materialCurrentStocks) {
        if(CollUtil.isEmpty(materialCurrentStocks)){
            return;
        }
        List<MaterialCurrentStock> updateList = new ArrayList<>();
        for(MaterialCurrentStock materialCurrentStock : materialCurrentStocks){
            MaterialCurrentStock query = this.getByMaterialId(materialCurrentStock.getDepotId(),materialCurrentStock.getMaterialId(),materialCurrentStock.getSupplierId());
            query.getCurrentNumber().add(materialCurrentStock.getCurrentNumber());
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
    public MaterialCurrentStock getByMaterialId(Long depotId, Long materialId,Long supplierId) {
         MaterialCurrentStock materialCurrentStock = this.getOne(Wrappers.lambdaQuery(MaterialCurrentStock.class)
             .eq(MaterialCurrentStock::getMaterialId,materialId)
             .eq(MaterialCurrentStock::getSupplierId,supplierId)
             .eq(MaterialCurrentStock::getDepotId,depotId)
         );
         return materialCurrentStock;
    }

    /**
     * 根据用户id，仓库id，商品id获取信息
     * @return
     */
    @Override
    public Page<MaterialCurrentStock> getPageByExample(MaterialCurrentStockPage query) {
        return this.page(query,Wrappers.<MaterialCurrentStock>lambdaQuery()
            .eq(ObjectUtil.isNotNull(query.getSupplierId()),MaterialCurrentStock::getSupplierId,query.getSupplierId())
            .eq(ObjectUtil.isNotNull(query.getMaterialId()),MaterialCurrentStock::getMaterialId,query.getMaterialId())
            .eq(ObjectUtil.isNotNull(query.getDepotId()),MaterialCurrentStock::getDepotId,query.getDepotId())
        );
    }
}
