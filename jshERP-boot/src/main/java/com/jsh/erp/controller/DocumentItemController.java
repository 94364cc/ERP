package com.jsh.erp.controller;

import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.page.DocumentHeadPage;
import com.jsh.erp.service.document.DocumentStrategyFactory;
import com.jsh.erp.service.document.Interface.IDocumentHeadService;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/documentItem")
@Api(tags = {"新单据详情管理"})
public class DocumentItemController {
    private Logger logger = LoggerFactory.getLogger(DocumentItemController.class);

    @Autowired
    IDocumentItemService documentItemService;

    /**
     * 新增制单详情
     * @param documentItem
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "新增制单详情")
    public BaseResponseInfo insert(@RequestBody DocumentItem documentItem) throws Exception{
        documentItemService.add(documentItem);
        return BaseResponseInfo.success();
    }

    /**
     * 修改制单详情
     * @param documentItem
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "修改制单详情")
    public BaseResponseInfo update(@RequestBody DocumentItem documentItem) throws Exception{
        documentItemService.update(documentItem);
        return BaseResponseInfo.success();
    }

    /**
     * 删除制单详情
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/delete/{id}")
    @ApiOperation(value = "删除制单详情")
    public BaseResponseInfo delete(@PathVariable Long id) throws Exception{
        documentItemService.delete(id);
        return BaseResponseInfo.success();
    }


    /**
     * 根据主体id查询
     * @param headId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/head")
    @ApiOperation(value = "根据主体id查询")
    public BaseResponseInfo getByHeadId(@RequestParam("headId")Long headId) throws Exception{
        return BaseResponseInfo.data(documentItemService.getByHeadId(headId));
    }
}
