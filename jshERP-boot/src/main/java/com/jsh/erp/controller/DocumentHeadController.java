package com.jsh.erp.controller;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;

import cn.hutool.core.collection.CollUtil;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.DocumentHeadVo4Body;
import com.jsh.erp.datasource.page.DocumentHeadPage;
import com.jsh.erp.service.document.DocumentStrategyFactory;
import com.jsh.erp.service.document.Interface.IDocumentHeadService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ji-sheng-hua 752*718*920
 */
@RestController
@RequestMapping(value = "/documentHead")
@Api(tags = {"新单据管理"})
public class DocumentHeadController {
    private Logger logger = LoggerFactory.getLogger(DocumentHeadController.class);

    @Resource
    IDocumentHeadService documentHeadService;
    /**
     * 获取用户信息
     * @return 返回插件信息
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取单据信息")
    public BaseResponseInfo getPluginInfo(@RequestParam(value = "search",required = false) String search,
                                          @RequestParam("type") Integer type,
                                          @RequestParam("currentPage") Integer currentPage,
                                          @RequestParam("pageSize") Integer pageSize) throws Exception{
        String supplierId = StringUtil.getInfo(search, "supplierId");
        String number = StringUtil.getInfo(search, "number");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        DocumentHeadPage documentHeadPage = new DocumentHeadPage();
        documentHeadPage.setBeginDate(beginTime);
        documentHeadPage.setCurrent(currentPage);
        documentHeadPage.setSize(pageSize);
        documentHeadPage.setEndDate(endTime);
        documentHeadPage.setSupplierId(supplierId);
        documentHeadPage.setNumber(number);
        IDocumentHeadService documentHeadService = DocumentStrategyFactory.getByType(type);
        return BaseResponseInfo.data(documentHeadService.getPage(documentHeadPage));
    }


    /**
     * 单位列表
     * @param documentHead
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "新增制单")
    public BaseResponseInfo insertUnit(@RequestBody DocumentHead documentHead) throws Exception{
        IDocumentHeadService documentHeadService = DocumentStrategyFactory.getByType(documentHead.getType());
        documentHeadService.add(documentHead);
        return BaseResponseInfo.success();
    }


    /**
     * 根据id查询单据信息
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/id")
    @ApiOperation(value = "根据id查询单据信息")
    public BaseResponseInfo getById(@RequestParam("id") Long id) {
        return BaseResponseInfo.data(documentHeadService.getDocumentHeadById(id));
    }





}
