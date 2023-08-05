package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Unit;
import com.jsh.erp.service.unit.UnitService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * Description
 *
 * @Author: zhengchenchen
 * @Date: 2019/4/1 15:38
 */
@RestController
@RequestMapping(value = "/unit")
@Api(tags = {"单位管理"})
public class UnitController {

    @Resource
    private UnitService unitService;

    /**
     * 单位列表
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getAllList")
    @ApiOperation(value = "单位列表")
    public BaseResponseInfo getAllList(HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<Unit> unitList = unitService.getUnit();
            res.code = 200;
            res.data = unitList;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }


    /**
     * 单位列表
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "新增单位")
    public BaseResponseInfo insertUnit(@RequestBody JSONObject obj,HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = unitService.insertUnit(obj,request);
            res.code = 200;
            res.data = result;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "新增单位失败";
        }
        return res;
    }


    /**
     * 更新单位列表
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/update")
    @ApiOperation(value = "更新单位")
    public BaseResponseInfo updateUnit(@RequestBody JSONObject obj,HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = unitService.updateUnit(obj,request);
            res.code = 200;
            res.data = result;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "更新单位失败";
        }
        return res;
    }


    /**
     * 删除单位列表
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除单位列表")
    public BaseResponseInfo deleteUnit(Long id, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = unitService.deleteUnit(id,request);
            res.code = 200;
            res.data = result;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "删除单位失败";
        }
        return res;
    }


    /**
     * 批量删除单位列表
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除单位列表")
    public BaseResponseInfo batchDeleteUnit(@RequestBody String ids,HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = unitService.batchDeleteUnit(ids,request);
            res.code = 200;
            res.data = result;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "批量删除单位失败";
        }
        return res;
    }
}
