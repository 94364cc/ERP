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
import org.springframework.web.bind.annotation.PathVariable;
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
                                          @RequestParam("currentPage") Integer currentPage,
                                          @RequestParam("pageSize") Integer pageSize) throws Exception{
        String supplierId = StringUtil.getInfo(search, "supplierId");
        String number = StringUtil.getInfo(search, "number");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String type = StringUtil.getInfo(search, "type");
        DocumentHeadPage documentHeadPage = new DocumentHeadPage();
        documentHeadPage.setBeginDate(beginTime);
        documentHeadPage.setCurrent(currentPage);
        documentHeadPage.setSize(pageSize);
        documentHeadPage.setEndDate(endTime);
        documentHeadPage.setSupplierId(supplierId);
        documentHeadPage.setNumber(number);
        IDocumentHeadService documentHeadService = DocumentStrategyFactory.getByType(Integer.parseInt(type));
        return BaseResponseInfo.data(documentHeadService.getPage(documentHeadPage));
    }


    /**
     * 新增制单
     * @param documentHead
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "新增制单")
    public BaseResponseInfo insert(@RequestBody DocumentHead documentHead) throws Exception{
        IDocumentHeadService documentHeadService = DocumentStrategyFactory.getByType(documentHead.getType());
        documentHeadService.add(documentHead);
        return BaseResponseInfo.success();
    }

    /**
     * 新增制单
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping
    @ApiOperation(value = "根据id查询单子")
    public BaseResponseInfo getById(@RequestParam("id")Long id,@RequestParam("type") Integer type) throws Exception{
        IDocumentHeadService documentHeadService = DocumentStrategyFactory.getByType(type);
        documentHeadService.getDocumentHeadById(id);
        return BaseResponseInfo.success();
    }

    /**
     * 修改制单
     * @param documentHead
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "修改制单")
    public BaseResponseInfo update(@RequestBody DocumentHead documentHead) throws Exception{
        IDocumentHeadService documentHeadService = DocumentStrategyFactory.getByType(documentHead.getType());
        documentHeadService.updateDocumentHead(documentHead);
        return BaseResponseInfo.success();
    }


    /**
     * 删除制单
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/delete/{id}")
    @ApiOperation(value = "删除制单")
    public BaseResponseInfo delete(@PathVariable("id") Long id,@RequestParam Integer type) throws Exception{
        IDocumentHeadService documentHeadService = DocumentStrategyFactory.getByType(type);
        documentHeadService.deleteDocumentHead(id);
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


    /**
     * 打印单据信息
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/print")
    @ApiOperation(value = "打印单据")
    public BaseResponseInfo print(@RequestParam("id") Long id,Integer type) {
        IDocumentHeadService documentHeadService = DocumentStrategyFactory.getByType(type);
        return BaseResponseInfo.data(documentHeadService.print(id));
    }
}
