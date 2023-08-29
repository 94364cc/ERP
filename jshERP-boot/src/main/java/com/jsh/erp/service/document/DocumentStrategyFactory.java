package com.jsh.erp.service.document;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.jsh.erp.datasource.enumPackage.DocumentTypeEnum;
import com.jsh.erp.service.document.Interface.IDocumentHeadService;

/**
 * @describe 单子注册工厂
 * @author: zhengchenchen
 * @create: 2022-01-14 13:40:52
 **/
public class DocumentStrategyFactory {

    private static final Map<Integer, IDocumentHeadService> strategyMap = new ConcurrentHashMap<>();

    public static IDocumentHeadService getByType(Integer type) {
        IDocumentHeadService documentHeadService = strategyMap.get(type);
        if(ObjectUtil.isNull(documentHeadService)){
            documentHeadService= strategyMap.get(DocumentTypeEnum.IN);
        }
        return documentHeadService;
    }

    public static  void register(Integer type, IDocumentHeadService documentHeadService) {
        Assert.notNull(type,"type can't be null");
        strategyMap.put(type,documentHeadService);
    }

}
