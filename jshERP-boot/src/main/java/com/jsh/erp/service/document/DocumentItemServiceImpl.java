package com.jsh.erp.service.document;

import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.mappers.DocumentItemMapper;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.user.UserService;
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
     * 根据主体id获取详情列表
     * @param headId
     * @return
     */
    @Override
    public List<DocumentItem> getByHeadId(Long headId) {
        List<DocumentItem> documentItems =  this.list(Wrappers.<DocumentItem>lambdaQuery().eq(DocumentItem::getHeadId,headId));
        if(CollUtil.isEmpty(documentItems)){
            return documentItems;
        }

        for(DocumentItem documentItem : documentItems){
            Material material = materialService.getById(documentItem.getMaterialId());
            if(ObjectUtil.isNotNull(material)){
                documentItem.setMaterialName(material.getName());
            }
            Depot depot = depotService.getDepot(documentItem.getDepotId());
            if(ObjectUtil.isNotNull(depot)){
                documentItem.setDepotName(depot.getName());
            }

            Depot antherDepotName = depotService.getDepot(documentItem.getAnotherDepotId());
            if(ObjectUtil.isNotNull(antherDepotName)){
                documentItem.setDepotName(antherDepotName.getName());
            }
        }
        return documentItems;
    }
}
