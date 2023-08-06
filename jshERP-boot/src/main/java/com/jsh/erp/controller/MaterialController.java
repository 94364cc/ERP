package com.jsh.erp.controller;

import javax.annotation.Resource;

import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.service.material.Interface.IMaterialService;
import com.jsh.erp.utils.BaseResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhengchenchen
 */
@RestController
@RequestMapping(value = "/material")
@Api(tags = {"商品管理"})
@Slf4j
public class MaterialController {

    @Resource
    private IMaterialService materialService;


    /**
     * 添加商品
     * @param material
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加商品")
    public BaseResponseInfo add(@RequestBody Material material) throws Exception{
        materialService.insertMaterial(material);
        return BaseResponseInfo.success();
    }

}
