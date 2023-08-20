package com.jsh.erp.service.document;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.jsh.erp.datasource.enumPackage.DocumentTypeEnum;

/**
 * @describe 单子注册工厂
 * @author: zhengchenchen
 * @create: 2022-01-14 13:40:52
 **/
public class DocumentStrategyFactory {

    private static final Map<Integer, InDocumentHeadService> strategyMap = new ConcurrentHashMap<>();

    public static InDocumentHeadService getByType(Integer type) {
        InDocumentHeadService inDocumentHeadService = strategyMap.get(type);
        if(ObjectUtil.isNull(inDocumentHeadService)){
            inDocumentHeadService= strategyMap.get(DocumentTypeEnum.IN);
        }
        return inDocumentHeadService;
    }

    public static  void register(Integer type,InDocumentHeadService inDocumentHeadService) {
        Assert.notNull(type,"type can't be null");
        strategyMap.put(type,inDocumentHeadService);
    }

}
