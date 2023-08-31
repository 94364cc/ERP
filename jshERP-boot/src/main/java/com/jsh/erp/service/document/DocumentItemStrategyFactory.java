package com.jsh.erp.service.document;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.enumPackage.DocumentTypeEnum;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.document.Interface.IDocumentHeadService;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import com.jsh.erp.utils.SpringContextUtils;

/**
 * @describe 单据详情注册工厂
 * @author: zhengchenchen
 * @create: 2022-01-14 13:40:52
 **/
public class DocumentItemStrategyFactory {

    private static final Map<Integer, IDocumentItemService> strategyMap = new ConcurrentHashMap<>();

    public static IDocumentItemService getByType(Long headId) {
        IDocumentHeadService documentHeadService = SpringContextUtils.getContext().getBean(IDocumentHeadService.class);
        DocumentHead documentHead = documentHeadService.getById(headId);
        ResultEnum.DOCUMENT_HEAD_NOT_EXISTS.notNull(documentHead);

        IDocumentItemService documentItemService = strategyMap.get(documentHead.getType());
        if(ObjectUtil.isNull(documentHeadService)){
            documentItemService= strategyMap.get(DocumentTypeEnum.IN);
        }
        return documentItemService;
    }

    public static  void register(Integer type, IDocumentItemService documentHeadService) {
        Assert.notNull(type,"type can't be null");
        strategyMap.put(type,documentHeadService);
    }

}
