package com.jsh.erp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.page.MaterialPage;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhengchenchen
 */
@RestController
@RequestMapping(value = "/material")
@Api(tags = {"商品管理"})
@Slf4j
public class MaterialController {

    @Autowired
    private INMaterialService nMaterialService;



    /**
     * 添加商品
     * @param material
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加商品")
    public BaseResponseInfo add(@Valid @RequestBody Material material) throws Exception{
        nMaterialService.insertMaterial(material);
        return BaseResponseInfo.success();
    }

    /**
     * 更新商品
     * @param material
     * @return
     */
    @PutMapping(value = "/update")
    @ApiOperation(value = "更新商品")
    public BaseResponseInfo update(@RequestBody Material material) throws Exception{
        nMaterialService.updateMaterial(material);
        return BaseResponseInfo.success();
    }

    /**
     * 批量删除商品
     * @param
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除商品")
    public BaseResponseInfo delete(@RequestParam String ids) throws Exception{
        List<Long> idList = StringUtil.split2List(ids);
        nMaterialService.batchDeleteMaterial(idList);
        return BaseResponseInfo.success();
    }


    /**
     * 删除商品
     * @param
     * @return
     */
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除商品")
    public BaseResponseInfo delete(@RequestParam Long id) throws Exception{
        nMaterialService.deleteMaterial(id);
        return BaseResponseInfo.success();
    }

    /**
     * 查看商品列表
     * @param
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "查看商品分页")
    public BaseResponseInfo getPage(MaterialPage materialPage){
        return BaseResponseInfo.data(nMaterialService.getPage(materialPage));
    }

}
