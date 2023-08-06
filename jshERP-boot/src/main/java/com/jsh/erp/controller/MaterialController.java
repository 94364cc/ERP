package com.jsh.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.jsh.erp.datasource.entities.MaterialVo4Unit;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.unit.UnitService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
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

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji|sheng|hua jshERP
 */
@RestController
@RequestMapping(value = "/material")
@Api(tags = {"商品管理"})
public class MaterialController {
    private Logger logger = LoggerFactory.getLogger(MaterialController.class);

    @Resource
    private MaterialService materialService;

    @Resource
    private UnitService unitService;

    @Resource
    private DepotService depotService;

    /**
     * 检查商品是否存在
     * @param id
     * @param name
     * @param model
     * @param standard
     * @param unitId
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/checkIsExist")
    @ApiOperation(value = "检查商品是否存在")
    public String checkIsExist(@RequestParam("name") String name,
                               @RequestParam("model") String model,
                               @RequestParam("standard") String standard,
                               @RequestParam("unitId") Long unitId,
                               HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int exist = materialService.checkIsExist(name, StringUtil.toNull(model), StringUtil.toNull(standard), unitId);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 根据id来查询商品名称
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/findById")
    @ApiOperation(value = "根据id来查询商品名称")
    public BaseResponseInfo findById(@RequestParam("id") Long id, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<MaterialVo4Unit> list = materialService.findById(id);
            res.code = 200;
            res.data = list;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 添加商品
     * @param obj
     * @param request
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加商品")
    public BaseResponseInfo add(@RequestBody JSONObject obj, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = materialService.insertMaterial(obj,request);
            res.code = 200;
            res.data = result;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }


    /**
     * 更新商品
     * @param obj
     * @param request
     * @return
     */
    @PostMapping(value = "/put")
    @ApiOperation(value = "更新商品")
    public BaseResponseInfo updateMaterial(@RequestBody JSONObject obj, HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            int result = materialService.updateMaterial(obj,request);
            res.code = 200;
            res.data = result;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
    ///**
    // * 根据meId来查询商品名称
    // * @param meId
    // * @param request
    // * @return
    // */
    //@GetMapping(value = "/findByIdWithBarCode")
    //@ApiOperation(value = "根据meId来查询商品名称")
    //public BaseResponseInfo findByIdWithBarCode(@RequestParam("meId") Long meId,
    //                                            @RequestParam("mpList") String mpList,
    //                                            HttpServletRequest request) throws Exception{
    //    BaseResponseInfo res = new BaseResponseInfo();
    //    try {
    //        String[] mpArr = mpList.split(",");
    //        MaterialVo4Unit mu = new MaterialVo4Unit();
    //        List<MaterialVo4Unit> list = materialService.findByIdWithBarCode(meId);
    //        res.code = 200;
    //        res.data = mu;
    //    } catch(Exception e){
    //        e.printStackTrace();
    //        res.code = 500;
    //        res.data = "获取数据失败";
    //    }
    //    return res;
    //}


    ///**
    // * 查找商品信息-下拉框
    // * @param mpList
    // * @param request
    // * @return
    // */
    //@GetMapping(value = "/findBySelect")
    //@ApiOperation(value = "查找商品信息")
    //public JSONObject findBySelect(@RequestParam(value = "categoryId", required = false) Long categoryId,
    //                              @RequestParam(value = "q", required = false) String q,
    //                              @RequestParam(value = "mpList", required = false) String mpList,
    //                              @RequestParam(value = "depotId", required = false) Long depotId,
    //                              @RequestParam(value = "enableSerialNumber", required = false) String enableSerialNumber,
    //                              @RequestParam(value = "enableBatchNumber", required = false) String enableBatchNumber,
    //                              @RequestParam("page") Integer currentPage,
    //                              @RequestParam("rows") Integer pageSize,
    //                              HttpServletRequest request) throws Exception{
    //    JSONObject object = new JSONObject();
    //    try {
    //        String[] mpArr = new String[]{};
    //        if(StringUtil.isNotEmpty(mpList)){
    //            mpArr= mpList.split(",");
    //        }
    //        List<MaterialVo4Unit> dataList = materialService.findBySelectWithBarCode(categoryId, q, enableSerialNumber,
    //                enableBatchNumber, (currentPage-1)*pageSize, pageSize);
    //        int total = materialService.findBySelectWithBarCodeCount(categoryId, q, enableSerialNumber,
    //                enableBatchNumber);
    //        object.put("total", total);
    //        JSONArray dataArray = new JSONArray();
    //        //存放数据json数组
    //        if (null != dataList) {
    //            for (MaterialVo4Unit material : dataList) {
    //                JSONObject item = new JSONObject();
    //                item.put("id", material.getMeId()); //商品扩展表的id
    //                String ratioStr = ""; //比例
    //                Unit unit = new Unit();
    //                if (material.getUnitId() == null) {
    //                    ratioStr = "";
    //                } else {
    //                    unit = unitService.getUnit(material.getUnitId());
    //                    //拼接副单位的比例
    //                    String commodityUnit = material.getCommodityUnit();
    //                    if(commodityUnit.equals(unit.getBasicUnit())) {
    //                        ratioStr = "[基本]";
    //                    }
    //                }
    //                item.put("mBarCode", material.getmBarCode());
    //                item.put("name", material.getName());
    //                item.put("categoryName", material.getCategoryName());
    //                item.put("standard", material.getStandard());
    //                item.put("model", material.getModel());
    //                item.put("unit", material.getCommodityUnit() + ratioStr);
    //                item.put("sku", material.getSku());
    //                BigDecimal stock;
    //                if(StringUtil.isNotEmpty(material.getSku())){
    //                    stock = depotItemService.getSkuStockByParam(depotId,material.getMeId(),null,null);
    //                } else {
    //                    stock = depotItemService.getStockByParam(depotId,material.getId(),null,null);
    //                }
    //                item.put("stock", stock);
    //                dataArray.add(item);
    //            }
    //        }
    //        object.put("rows", dataArray);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    return object;
    //}

    /**
     * 根据商品id查找商品信息
     * @param meId
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getMaterialByMeId")
    @ApiOperation(value = "根据商品id查找商品信息")
    public JSONObject getMaterialByMeId(@RequestParam(value = "meId", required = false) Long meId,
                                        HttpServletRequest request) throws Exception{
        JSONObject item = new JSONObject();
        try {
            List<MaterialVo4Unit> materialList = materialService.getMaterialByMeId(meId);
            if(materialList!=null && materialList.size()!=1) {
                return item;
            } else if(materialList.size() == 1) {
                MaterialVo4Unit material = materialList.get(0);
                item.put("Id", material.getId()); //商品扩展表的id
                item.put("name", material.getName());
                item.put("model", material.getModel());
                item.put("standard", material.getStandard());
                item.put("unitName", material.getUnitName());
                item.put("remark", material.getRemark());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    ///**
    // * 获取商品序列号
    // * @param q
    // * @param currentPage
    // * @param pageSize
    // * @param request
    // * @param response
    // * @return
    // * @throws Exception
    // */
    //@GetMapping(value = "/getMaterialEnableSerialNumberList")
    //@ApiOperation(value = "获取商品序列号")
    //public JSONObject getMaterialEnableSerialNumberList(
    //                            @RequestParam(value = "q", required = false) String q,
    //                            @RequestParam("page") Integer currentPage,
    //                            @RequestParam("rows") Integer pageSize,
    //                            HttpServletRequest request,
    //                            HttpServletResponse response)throws Exception {
    //    JSONObject object= new JSONObject();
    //    try {
    //        List<MaterialVo4Unit> list = materialService.getMaterialEnableSerialNumberList(q, (currentPage-1)*pageSize, pageSize);
    //        Long count = materialService.getMaterialEnableSerialNumberCount(q);
    //        object.put("rows", list);
    //        object.put("total", count);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    return object;
    //}

    /**
     * 商品名称模糊匹配
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getMaterialNameList")
    @ApiOperation(value = "商品名称模糊匹配")
    public JSONArray getMaterialNameList() throws Exception {
        JSONArray arr = new JSONArray();
        try {
            List<String> list = materialService.getMaterialNameList();
            for (String s : list) {
                JSONObject item = new JSONObject();
                item.put("value", s);
                item.put("text", s);
                arr.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    ///**
    // * 商品库存查询
    // * @param currentPage
    // * @param pageSize
    // * @param depotIds
    // * @param categoryId
    // * @param materialParam
    // * @param mpList
    // * @param column
    // * @param order
    // * @param request
    // * @return
    // * @throws Exception
    // */
    //@GetMapping(value = "/getListWithStock")
    //@ApiOperation(value = "商品库存查询")
    //public BaseResponseInfo getListWithStock(@RequestParam("currentPage") Integer currentPage,
    //                                         @RequestParam("pageSize") Integer pageSize,
    //                                         @RequestParam(value = "depotIds", required = false) String depotIds,
    //                                         @RequestParam(value = "categoryId", required = false) Long categoryId,
    //                                         @RequestParam(value = "position", required = false) String position,
    //                                         @RequestParam("materialParam") String materialParam,
    //                                         @RequestParam("zeroStock") Integer zeroStock,
    //                                         @RequestParam("mpList") String mpList,
    //                                         @RequestParam("column") String column,
    //                                         @RequestParam("order") String order,
    //                                         HttpServletRequest request)throws Exception {
    //    BaseResponseInfo res = new BaseResponseInfo();
    //    Map<String, Object> map = new HashMap<>();
    //    try {
    //        List<Long> idList = new ArrayList<>();
    //        List<Long> depotList = new ArrayList<>();
    //        if(categoryId != null){
    //            idList = materialService.getListByParentId(categoryId);
    //        }
    //        if(StringUtil.isNotEmpty(depotIds)) {
    //            depotList = StringUtil.strToLongList(depotIds);
    //        } else {
    //            //未选择仓库时默认为当前用户有权限的仓库
    //            JSONArray depotArr = depotService.findDepotByCurrentUser();
    //            for(Object obj: depotArr) {
    //                JSONObject object = JSONObject.parseObject(obj.toString());
    //                depotList.add(object.getLong("id"));
    //            }
    //        }
    //        List<MaterialVo4Unit> dataList = materialService.getListWithStock(depotList, idList, StringUtil.toNull(position), StringUtil.toNull(materialParam),
    //                zeroStock, StringUtil.safeSqlParse(column), StringUtil.safeSqlParse(order), (currentPage-1)*pageSize, pageSize);
    //        int total = materialService.getListWithStockCount(depotList, idList, StringUtil.toNull(position), StringUtil.toNull(materialParam), zeroStock);
    //        MaterialVo4Unit materialVo4Unit= materialService.getTotalStockAndPrice(depotList, idList, StringUtil.toNull(position), StringUtil.toNull(materialParam));
    //        map.put("total", total);
    //        map.put("currentStock", materialVo4Unit.getCurrentStock()!=null?materialVo4Unit.getCurrentStock():BigDecimal.ZERO);
    //        map.put("currentStockPrice", materialVo4Unit.getCurrentStockPrice()!=null?materialVo4Unit.getCurrentStockPrice():BigDecimal.ZERO);
    //        map.put("currentWeight", materialVo4Unit.getCurrentWeight()!=null?materialVo4Unit.getCurrentWeight():BigDecimal.ZERO);
    //        map.put("rows", dataList);
    //        res.code = 200;
    //        res.data = map;
    //    } catch(Exception e){
    //        e.printStackTrace();
    //        res.code = 500;
    //        res.data = "获取数据失败";
    //    }
    //    return res;
    //}
    //
    ///**
    // * 批量设置商品当前的实时库存（按每个仓库）
    // * @param jsonObject
    // * @param request
    // * @return
    // * @throws Exception
    // */
    //@PostMapping(value = "/batchSetMaterialCurrentStock")
    //@ApiOperation(value = "批量设置商品当前的实时库存（按每个仓库）")
    //public String batchSetMaterialCurrentStock(@RequestBody JSONObject jsonObject,
    //                             HttpServletRequest request)throws Exception {
    //    String ids = jsonObject.getString("ids");
    //    Map<String, Object> objectMap = new HashMap<>();
    //    int res = materialService.batchSetMaterialCurrentStock(ids);
    //    if(res > 0) {
    //        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    //    } else {
    //        return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
    //    }
    //}

}
