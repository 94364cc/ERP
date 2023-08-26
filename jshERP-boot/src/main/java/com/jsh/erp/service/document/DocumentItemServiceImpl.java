package com.jsh.erp.service.document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.mappers.DocumentItemMapper;
import com.jsh.erp.datasource.vo.DocumentItemPrintVO;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentItemServiceImpl extends ServiceImpl<DocumentItemMapper, DocumentItem> implements IDocumentItemService {

    private final static String LOG_NAME = "单据详情";
    @Autowired
    UserService userService;
    @Autowired
    INMaterialService materialService;
    @Autowired
    DepotService depotService;
    @Autowired
    LogService logService;
    /**
     * 新增
     * @param documentItem
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DocumentItem documentItem){
        this.save(documentItem);

        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_ADD);
        logService.insertLog(LOG_NAME, sb.toString());
    }


    /**
     * 修改
     * @param documentItem
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(DocumentItem documentItem){
        this.update(documentItem);

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
    @Override
    public void deleteByHeadId(Long headId){
        this.remove(Wrappers.<DocumentItem>lambdaQuery().eq(DocumentItem::getHeadId,headId));
    }

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
        List<Long> materialIds = documentItems.stream().map(DocumentItem::getId).collect(Collectors.toList());
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
            documentItemPrintVO.setModel(material.getModel());
            BigDecimal volumn = StringUtil.standard2Volume(material.getStandard());
            documentItemPrintVO.setVolume(volumn);
            documentItemPrintVOList.add(documentItemPrintVO);
        }
        return documentItemPrintVOList;
    }

}
