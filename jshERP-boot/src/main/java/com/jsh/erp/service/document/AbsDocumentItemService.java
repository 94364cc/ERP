package com.jsh.erp.service.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.print.Doc;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.dto.DocumentItemAddDto;
import com.jsh.erp.datasource.dto.DocumentItemUpdateDto;
import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.entities.DocumentItemFlow;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.entities.MaterialCurrentStockQuery;
import com.jsh.erp.datasource.mappers.DocumentItemMapper;
import com.jsh.erp.datasource.vo.DocumentItemPrintVO;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.document.Interface.IDocumentHeadService;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.materialCurrentStock.Interface.IMaterialCurrentStockService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbsDocumentItemService extends ServiceImpl<DocumentItemMapper, DocumentItem> implements IDocumentItemService {

    @Autowired
    UserService userService;
    @Autowired
    INMaterialService materialService;
    @Autowired
    IDocumentHeadService documentHeadService;
    @Autowired
    IMaterialCurrentStockService materialCurrentStockService;
    @Autowired
    DepotService depotService;
    @Autowired
    LogService logService;

    /**
     * 根据主体id获取详情列表
     * @param headId
     * @return
     */
    @Override
    public List<DocumentItem> getByHeadId(Long headId) throws Exception {
        List<DocumentItem> documentItems =  this.list(Wrappers.<DocumentItem>lambdaQuery().eq(DocumentItem::getHeadId,headId));
        if(CollUtil.isEmpty(documentItems)){
            return documentItems;
        }
        List<Long> materialIds = documentItems.stream().map(DocumentItem::getMaterialId).collect(Collectors.toList());
        Map<Long,String> materialMap = materialService.getMayByIds(materialIds);

        //仓库
        List<Long> depotIds = documentItems.stream().map(DocumentItem::getDepotId).collect(Collectors.toList());
        List<Long> anotherDepotIds = documentItems.stream()
            .filter(depotId -> ObjectUtil.isNotNull(depotId))
            .map(DocumentItem::getDepotId)
            .collect(Collectors.toList());
        depotIds.addAll(anotherDepotIds);
        Map<Long,String> depotMap = depotService.getMapByIds(depotIds);

        for(DocumentItem documentItem : documentItems){
            documentItem.setModel(materialMap.get(documentItem.getMaterialId()));
            documentItem.setDepotName(depotMap.get(documentItem.getDepotId()));
            documentItem.setAnotherDepotName(depotMap.get(documentItem.getAnotherDepotId()));
        }
        return documentItems;
    }

    /**
     * 打印
     * @param headId
     * @return
     */
    @Override
    public List<DocumentItemPrintVO> printByHeadId(Long headId) {
        List<DocumentItemPrintVO> documentItemPrintVOList = CollUtil.newArrayList();
        List<DocumentItem> documentItems =  this.list(Wrappers.<DocumentItem>lambdaQuery().eq(DocumentItem::getHeadId,headId));
        if(CollUtil.isEmpty(documentItems)){
            return documentItemPrintVOList;
        }
        List<Long> materialIds = documentItems.stream().map(DocumentItem::getId).collect(Collectors.toList());
        Map<Long,Material> materialMap = materialService.getEntityMayByIds(materialIds);

        //仓库
        List<Long> depotIds = documentItems.stream().map(DocumentItem::getDepotId).collect(Collectors.toList());
        List<Long> anotherDepotIds = documentItems.stream()
            .filter(depotId -> ObjectUtil.isNotNull(depotId))
            .map(DocumentItem::getDepotId)
            .collect(Collectors.toList());
        depotIds.addAll(anotherDepotIds);
        Map<Long,String> depotMap = depotService.getMapByIds(depotIds);

        for(DocumentItem documentItem : documentItems){
            DocumentItemPrintVO documentItemPrintVO =new DocumentItemPrintVO();
            documentItemPrintVO.setOperNumber(documentItem.getOperNumber());
            //库房
            documentItemPrintVO.setDepotName(depotMap.get(documentItem.getDepotId()));
            //备注
            documentItemPrintVO.setRemark(documentItem.getRemark());
            //获取箱规和款号
            Material material = materialMap.get(documentItem.getMaterialId());
            if(ObjectUtil.isNotNull(material)){
                documentItemPrintVO.setModel(material.getModel());
                BigDecimal volumn = StringUtil.standard2Volume(material.getStandard());
                documentItemPrintVO.setVolume(volumn);
            }
            documentItemPrintVOList.add(documentItemPrintVO);
        }
        return documentItemPrintVOList;
    }

    /**
     * 根据商品id获取流水
     * @param materialId
     * @return
     */
    @Override
    public Page<DocumentItemFlow> getFlowByMaterialId(Integer pageSize,
                                                      Integer currentPage,
                                                      String number,
                                                      String beginTime,
                                                      String endTime,
                                                      Long materialId) {

        Page<DocumentItemFlow> resultPage = new Page<>(currentPage,pageSize);
        //根据单据号查询单据主体
        List<Long> documentHeadIds = CollUtil.newArrayList();
        if(StrUtil.isNotBlank(number)){
            List<DocumentHead> documentHeads = documentHeadService.list(Wrappers.lambdaQuery(DocumentHead.class).like(DocumentHead::getNumber,number));
            documentHeadIds = documentHeads.stream().map(DocumentHead::getId).collect(Collectors.toList());
        }

        Page page = new Page(currentPage,pageSize);
        Page<DocumentItem> documentItemPage = this.page(page,Wrappers.lambdaQuery(DocumentItem.class)
            .eq(DocumentItem::getMaterialId,materialId)
            .in(CollUtil.isNotEmpty(documentHeadIds),DocumentItem::getHeadId,documentHeadIds)
            .ge(StrUtil.isNotBlank(beginTime),DocumentItem::getCreateTime,beginTime)
            .le(StrUtil.isNotBlank(endTime),DocumentItem::getCreateTime,endTime)
        );
        List<DocumentItemFlow> documentItemFlows = new ArrayList<>();
        List<DocumentItem> documentItems = documentItemPage.getRecords();
        //获取商品全量信息
        List<Long> materialLIdist = documentItems.stream().map(DocumentItem::getMaterialId).collect(Collectors.toList());
        Map<Long,Material> materialMap = materialService.getEntityMayByIds(materialLIdist);

        for(DocumentItem documentItem: documentItemPage.getRecords()){
            DocumentItemFlow documentItemFlow = new DocumentItemFlow();
            //填充编号和类型
            DocumentHead documentHead = documentHeadService.getById(documentItem.getHeadId());
            if(ObjectUtil.isNotNull(documentHead)){
                documentItemFlow.setType(documentHead.getType());
                documentItemFlow.setNumber(documentHead.getNumber());
            }

            //填充仓库名称
            Depot depot = depotService.getDepot(documentItem.getDepotId());
            if(ObjectUtil.isNotNull(depot)){
                documentItemFlow.setDepotName(depot.getName());
            }

            //填充商品信息
            Material material = materialMap.get(documentItem.getMaterialId());
            if(ObjectUtil.isNotNull(material)){
                documentItemFlow.setName(material.getName());
                documentItemFlow.setModel(material.getModel());
            }
            documentItemFlow.setOperNumber(documentItem.getOperNumber());
            documentItemFlows.add(documentItemFlow);
        }
        resultPage.setRecords(documentItemFlows);
        return resultPage;
    }

}
