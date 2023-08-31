package com.jsh.erp.service.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.dto.DocumentItemAddDto;
import com.jsh.erp.datasource.dto.DocumentItemUpdateDto;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.enumPackage.DocumentTypeEnum;
import com.jsh.erp.exception.ResultEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutDocumentItemService extends AbsDocumentItemService implements InitializingBean {

    private final static String LOG_NAME = "出库单详情";
    @Override
    public void afterPropertiesSet() throws Exception {
        DocumentItemStrategyFactory.register(DocumentTypeEnum.OUT.getType(),this);
    }

    /**
     * 新增
     * @param documentItemAddDto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DocumentItemAddDto documentItemAddDto){
        DocumentItem documentItem = new DocumentItem();
        BeanUtil.copyProperties(documentItemAddDto,documentItem);

        this.save(documentItem);

        //获取客户
        DocumentHead documentHead =documentHeadService.getById(documentItemAddDto.getHeadId());
        ResultEnum.DOCUMENT_HEAD_NOT_EXISTS.isTrue(ObjectUtil.isNull(documentHead));

        //更新仓库货物
        MaterialCurrentStock materialCurrentStock = new MaterialCurrentStock();
        materialCurrentStock.setCurrentNumber(new BigDecimal(documentItem.getOperNumber()));
        materialCurrentStock.setMaterialId(documentItem.getMaterialId());
        materialCurrentStock.setDepotId(documentItem.getDepotId());
        materialCurrentStock.setSupplierId(documentHead.getSupplierId());
        materialCurrentStockService.delete(materialCurrentStock);

        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_ADD);
        logService.insertLog(LOG_NAME, sb.toString());
    }

    /**
     * 修改
     * @param documentItemUpdateDto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(DocumentItemUpdateDto documentItemUpdateDto){
        DocumentItem documentItem = new DocumentItem();
        BeanUtil.copyProperties(documentItemUpdateDto,documentItem);
        DocumentItem old = this.getById(documentItem.getId());
        //查询单据
        DocumentHead documentHead = documentHeadService.getById(documentItemUpdateDto.getHeadId());
        //如果修改了数量，更新仓库货物
        if(old.getOperNumber()!=documentItem.getOperNumber()){
            MaterialCurrentStock materialCurrentStock = new MaterialCurrentStock();
            materialCurrentStock.setCurrentNumber(new BigDecimal(documentItem.getOperNumber()));
            materialCurrentStock.setMaterialId(documentItem.getMaterialId());
            materialCurrentStock.setDepotId(documentItem.getDepotId());
            materialCurrentStock.setSupplierId(documentHead.getSupplierId());
            materialCurrentStockService.outUpdate(materialCurrentStock,old.getOperNumber());
        }
        this.updateById(documentItem);


        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_EDIT);
        logService.insertLog(LOG_NAME, sb.toString());
    }


    /**
     * 删除
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id){
        DocumentItem documentItem = this.getById(id);
        //查询单据的客户id
        Long supplierId = documentHeadService.getSupplierIdByItemId(id);
        //库存减去
        MaterialCurrentStock materialCurrentStock = new MaterialCurrentStock();
        materialCurrentStock.setCurrentNumber(new BigDecimal(documentItem.getOperNumber()));
        materialCurrentStock.setMaterialId(documentItem.getMaterialId());
        materialCurrentStock.setDepotId(documentItem.getDepotId());
        materialCurrentStock.setSupplierId(supplierId);
        materialCurrentStockService.add(materialCurrentStock);
        //删除单据详情
        this.removeById(id);


        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        logService.insertLog(LOG_NAME, sb.toString());
    }

    /**
     * 删除
     * @param headId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByHeadId(Long headId){
        //查询单据主体
        DocumentHead documentHead = documentHeadService.getById(headId);
        ResultEnum.DOCUMENT_HEAD_NOT_EXISTS.isTrue(ObjectUtil.isNull(documentHead));

        //查询单据详情
        List<DocumentItem> documentItems = this.list(
            Wrappers.<DocumentItem>lambdaQuery().eq(DocumentItem::getHeadId,headId));
        if(CollUtil.isEmpty(documentItems)){
            return ;
        }
        List<Long> documentItemIds = documentItems.stream().map(DocumentItem::getId).collect(Collectors.toList());
        //库存加上
        List<MaterialCurrentStock> materialCurrentStocks = new ArrayList<>();
        for(DocumentItem documentItem : documentItems){
            MaterialCurrentStock materialCurrentStock = new MaterialCurrentStock();
            materialCurrentStock.setCurrentNumber(new BigDecimal(documentItem.getOperNumber()));
            materialCurrentStock.setMaterialId(documentItem.getMaterialId());
            materialCurrentStock.setDepotId(documentItem.getDepotId());
            materialCurrentStock.setSupplierId(documentHead.getSupplierId());
            materialCurrentStocks.add(materialCurrentStock);
        }
        materialCurrentStockService.addBatch(materialCurrentStocks);
        //删除单据详情
        this.removeByIds(documentItemIds);
    }
}
