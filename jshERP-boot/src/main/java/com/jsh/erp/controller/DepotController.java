package com.jsh.erp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.material.Interface.IMaterialService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji sheng hua 752*718*920
 */
@RestController
@RequestMapping(value = "/depot")
@Api(tags = {"仓库管理"})
public class DepotController {
    private Logger logger = LoggerFactory.getLogger(DepotController.class);

    @Resource
    private DepotService depotService;

    @Resource
    private UserBusinessService userBusinessService;

    @Resource
    private IMaterialService materialService;

    /**
     * 仓库列表
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getAllList")
    @ApiOperation(value = "仓库列表")
    public BaseResponseInfo getAllList(HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<Depot> depotList = depotService.getAllList();
            res.code = 200;
            res.data = depotList;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 新增仓库
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "新增仓库")
    public BaseResponseInfo insertDepot(@RequestBody JSONObject obj, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = depotService.insertDepot(obj,request);
            res.code = 200;
            res.data = result;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "新增数据失败";
        }
        return res;
    }


    /**
     * 更新仓库
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/update")
    @ApiOperation(value = "更新仓库")
    public BaseResponseInfo updateDepot(@RequestBody JSONObject obj, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = depotService.updateDepot(obj,request);
            res.code = 200;
            res.data = result;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "修改数据失败";
        }
        return res;
    }


    /**
     * 删除仓库
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除仓库")
    public BaseResponseInfo deleteUnit(Long id, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = depotService.deleteDepot(id,request);
            res.code = 200;
            res.data = result;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "删除仓库失败";
        }
        return res;
    }


    /**
     * 批量删除仓库
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除仓库")
    public BaseResponseInfo batchDeleteUnit(String ids,HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = depotService.batchDeleteDepot(ids,request);
            res.code = 200;
            res.data = result;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "批量删除单位失败";
        }
        return res;
    }

    /**
     * 用户对应仓库显示
     * @param type
     * @param keyId
     * @param request
     * @return
     */
    @GetMapping(value = "/findUserDepot")
    @ApiOperation(value = "用户对应仓库显示")
    public JSONArray findUserDepot(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                 HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            //获取权限信息
            String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, keyId);
            List<Depot> dataList = depotService.findUserDepot();
            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 0);
            outer.put("key", 0);
            outer.put("value", 0);
            outer.put("title", "仓库列表");
            outer.put("attributes", "仓库列表");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Depot depot : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", depot.getId());
                    item.put("key", depot.getId());
                    item.put("value", depot.getId());
                    item.put("title", depot.getName());
                    item.put("attributes", depot.getName());
                    Boolean flag = ubValue.contains("[" + depot.getId().toString() + "]");
                    if (flag) {
                        item.put("checked", true);
                    }
                    dataArray.add(item);
                }
            }
            outer.put("children", dataArray);
            arr.add(outer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * 获取当前用户拥有权限的仓库列表
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/findDepotByCurrentUser")
    @ApiOperation(value = "获取当前用户拥有权限的仓库列表")
    public BaseResponseInfo findDepotByCurrentUser(HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            JSONArray arr = depotService.findDepotByCurrentUser();
            res.code = 200;
            res.data = arr;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 仓库列表-带库存
     * @param mId
     * @param request
     * @return
     */
    @GetMapping(value = "/getAllListWithStock")
    @ApiOperation(value = "仓库列表-带库存")
    public BaseResponseInfo getAllList(@RequestParam("mId") Long mId,
                                       HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        //try {
        //    List<Depot> list = depotService.getAllList();
        //    List<DepotEx> depotList = new ArrayList<DepotEx>();
        //    for(Depot depot: list) {
        //        DepotEx de = new DepotEx();
        //        if(mId!=0) {
        //            BigDecimal initStock = materialService.getInitStock(mId, depot.getId());
        //            BigDecimal currentStock = materialService.getCurrentStockByMaterialIdAndDepotId(mId, depot.getId());
        //            de.setInitStock(initStock);
        //            de.setCurrentStock(currentStock);
        //            MaterialInitialStock materialInitialStock = materialService.getSafeStock(mId, depot.getId());
        //            de.setLowSafeStock(materialInitialStock.getLowSafeStock());
        //            de.setHighSafeStock(materialInitialStock.getHighSafeStock());
        //        } else {
        //            de.setInitStock(BigDecimal.ZERO);
        //            de.setCurrentStock(BigDecimal.ZERO);
        //        }
        //        de.setId(depot.getId());
        //        de.setName(depot.getName());
        //        depotList.add(de);
        //    }
        //    res.code = 200;
        //    res.data = depotList;
        //} catch(Exception e){
        //    e.printStackTrace();
        //    res.code = 500;
        //    res.data = "获取数据失败";
        //}
        return res;
    }
}
