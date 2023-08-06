package com.jsh.erp.service.material;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.DepotItem;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.entities.MaterialCurrentStockExample;
import com.jsh.erp.datasource.entities.MaterialExample;
import com.jsh.erp.datasource.entities.MaterialInitialStock;
import com.jsh.erp.datasource.entities.MaterialInitialStockExample;
import com.jsh.erp.datasource.entities.MaterialInitialStockWithMaterial;
import com.jsh.erp.datasource.entities.MaterialVo4Unit;
import com.jsh.erp.datasource.entities.Unit;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.MaterialMapper;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.unit.UnitService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.ExcelUtils;
import com.jsh.erp.utils.StringUtil;
import jxl.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class MaterialService {
    private Logger logger = LoggerFactory.getLogger(MaterialService.class);

    @Resource
    private MaterialMapper materialMapper;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;


    public Material getMaterial(long id)throws Exception {
        Material result=null;
        try{
            result=materialMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Material> getMaterialListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Material> list = new ArrayList<>();
        try{
            MaterialExample example = new MaterialExample();
            example.createCriteria().andIdIn(idList);
            list = materialMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }



    public List<MaterialVo4Unit> select(String materialParam, String color, String materialOther, String weight, String expiryNum,
                                        String enableSerialNumber, String enableBatchNumber, String position, String enabled,
                                        String remark, String categoryId, String mpList, int offset, int rows)
            throws Exception{
        //String[] mpArr = new String[]{};
        //if(StringUtil.isNotEmpty(mpList)){
        //    mpArr= mpList.split(",");
        //}
        List<MaterialVo4Unit> resList = new ArrayList<>();
        //List<MaterialVo4Unit> list =null;
        //try{
        //    List<Long> idList = new ArrayList<>();
        //    if(StringUtil.isNotEmpty(categoryId)){
        //        idList = getListByParentId(Long.parseLong(categoryId));
        //    }
        //    list= materialMapperEx.selectByConditionMaterial(materialParam, color, materialOther, weight, expiryNum,
        //            enableSerialNumber, enableBatchNumber, position, enabled, remark, idList, mpList, offset, rows);
        //    if (null != list && list.size()>0) {
        //        Map<Long,BigDecimal> currentStockMap = getCurrentStockMapByMaterialList(list);
        //        for (MaterialVo4Unit m : list) {
        //            m.setStock(currentStockMap.get(m.getId())!=null? currentStockMap.get(m.getId()): BigDecimal.ZERO);
        //            m.setBigUnitStock(getBigUnitStock(m.getStock(), m.getUnitId()));
        //            resList.add(m);
        //        }
        //    }
        //}catch(Exception e){
        //    JshException.readFail(logger, e);
        //}
        return resList;
    }


    //@Transactional(value = "transactionManager", rollbackFor = Exception.class)
    //public int insertMaterial(JSONObject obj, HttpServletRequest request)throws Exception {
    //    Material m = JSONObject.parseObject(obj.toJSONString(), Material.class);
    //    try{
    //        this.checkIsExist(m.getName(),m.getModel(),m.getStandard(),m.getUnitId());
    //        materialMapperEx.insertSelectiveEx(m);
    //        logService.insertLog("商品",
    //                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(m.getName()).toString(), request);
    //        return 1;
    //    }catch (BusinessRunTimeException ex) {
    //        throw new BusinessRunTimeException(ex.getCode(), ex.getMessage());
    //    }catch(Exception e){
    //        JshException.writeFail(logger, e);
    //        return 0;
    //    }
    //}
    //
    //@Transactional(value = "transactionManager", rollbackFor = Exception.class)
    //public int updateMaterial(JSONObject obj, HttpServletRequest request) throws Exception{
    //    Material material = JSONObject.parseObject(obj.toJSONString(), Material.class);
    //    try{
    //        int isExists = this.checkIsExist(material.getName(),material.getModel(),material.getStandard(),material.getUnitId());
    //        if(isExists>0){
    //            throw new BusinessRunTimeException("商品已经存在");
    //        }
    //        materialMapper.updateByPrimaryKeySelective(material);
    //        logService.insertLog("商品",
    //                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(material.getName()).toString(), request);
    //        return 1;
    //    }catch(Exception e){
    //        JshException.writeFail(logger, e);
    //        return 0;
    //    }
    //}

    //@Transactional(value = "transactionManager", rollbackFor = Exception.class)
    //public int deleteMaterial(Long id, HttpServletRequest request)throws Exception {
    //    return batchDeleteMaterialByIds(id.toString());
    //}

    //@Transactional(value = "transactionManager", rollbackFor = Exception.class)
    //public int batchDeleteMaterial(String ids, HttpServletRequest request)throws Exception {
    //    return batchDeleteMaterialByIds(ids);
    //}

    //@Transactional(value = "transactionManager", rollbackFor = Exception.class)
    //public int batchDeleteMaterialByIds(String ids) throws Exception{
    //    String [] idArray=ids.split(",");
    //    //校验单据子表	jsh_depot_item
    //    List<DepotItem> depotItemList =null;
    //    try{
    //        depotItemList=  depotItemMapperEx.getDepotItemListListByMaterialIds(idArray);
    //    }catch(Exception e){
    //        JshException.readFail(logger, e);
    //    }
    //    if(depotItemList!=null&&depotItemList.size()>0){
    //        logger.error("异常码[{}],异常提示[{}],参数,MaterialIds[{}]",
    //                ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
    //        throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
    //                ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
    //    }
    //    //记录日志
    //    StringBuffer sb = new StringBuffer();
    //    sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
    //    List<Material> list = getMaterialListByIds(ids);
    //    for(Material material: list){
    //        sb.append("[").append(material.getName()).append("]");
    //    }
    //    logService.insertLog("商品", sb.toString(),
    //            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    //    User userInfo=userService.getCurrentUser();
    //    //校验通过执行删除操作
    //    try{
    //        //逻辑删除商品
    //        materialMapperEx.batchDeleteMaterialByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    //        return 1;
    //    }catch(Exception e){
    //        JshException.writeFail(logger, e);
    //        return 0;
    //    }
    //}

    public int checkIsNameExist(Long id, String name)throws Exception {
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Material> list =null;
        try{
            list=  materialMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    //
    //
    //public List<MaterialVo4Unit> findById(Long id)throws Exception{
    //    List<MaterialVo4Unit> list =null;
    //    try{
    //        list=  materialMapperEx.findById(id);
    //    }catch(Exception e){
    //        JshException.readFail(logger, e);
    //    }
    //    return list;
    //}

    public void exportExcel(String categoryId, String materialParam, String color, String materialOther, String weight,
                                             String expiryNum, String enabled, String enableSerialNumber, String enableBatchNumber,
                                             String remark, HttpServletResponse response)throws Exception {
        //List<Long> idList = new ArrayList<>();
        //if(StringUtil.isNotEmpty(categoryId)){
        //    idList = getListByParentId(Long.parseLong(categoryId));
        //}
        ////查询商品主条码相关列表
        //List<MaterialVo4Unit> dataList = materialMapperEx.exportExcel(materialParam, color, materialOther, weight, expiryNum, enabled, enableSerialNumber,
        //        enableBatchNumber, remark, idList);
        ////查询商品副条码相关列表
        //Map<Long, MaterialExtend> otherMaterialMap = new HashMap<>();
        //List<MaterialExtend> otherDataList = materialMapperEx.getOtherMaterialList();
        //for(MaterialExtend me: otherDataList) {
        //    otherMaterialMap.put(me.getMaterialId(), me);
        //}
        //String nameStr = "名称*,规格,型号,颜色,类别,基础重量(kg),保质期(天),基本单位*,副单位,基本条码*,副条码,比例,多属性," +
        //        "采购价,零售价,销售价,最低售价,状态*,序列号,批号,仓位货架,制造商,自定义1,自定义2,自定义3,备注";
        //List<String> nameList = StringUtil.strToStringList(nameStr);
        ////仓库列表
        //List<Depot> depotList = depotService.getAllList();
        //if (nameList != null) {
        //    for(Depot depot: depotList) {
        //        nameList.add(depot.getName());
        //    }
        //}
        ////期初库存缓存
        //List<MaterialInitialStock> misList = materialInitialStockMapperEx.getListExceptZero();
        //Map<String, BigDecimal> misMap = new HashMap<>();
        //if (misList != null) {
        //    for (MaterialInitialStock mis : misList) {
        //        misMap.put(mis.getMaterialId() + "_" + mis.getDepotId(), mis.getNumber());
        //    }
        //}
        //String[] names = StringUtil.listToStringArray(nameList);
        //String title = "商品信息";
        //List<String[]> objects = new ArrayList<>();
        //if (null != dataList) {
        //    for (MaterialVo4Unit m : dataList) {
        //        String[] objs = new String[100];
        //        objs[0] = m.getName();
        //        objs[1] = m.getStandard();
        //        objs[2] = m.getModel();
        //        objs[5] = otherMaterialMap.get(m.getId()) == null ? "" : otherMaterialMap.get(m.getId()).getCommodityUnit();
        //        objs[7] = otherMaterialMap.get(m.getId()) == null ? "" : otherMaterialMap.get(m.getId()).getBarCode();
        //        objs[11] = m.getCommodityDecimal() == null ? "" : m.getCommodityDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        //        objs[12] = m.getWholesaleDecimal() == null ? "" : m.getWholesaleDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        //        objs[13] = m.getLowDecimal() == null ? "" : m.getLowDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        //        objs[14] = m.getRemark();
        //        //仓库期初库存
        //        int i = 26;
        //        for(Depot depot: depotList) {
        //            BigDecimal number = misMap.get(m.getId() + "_" + depot.getId());
        //            objs[i] = number == null ? "0" : number.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        //            i++;
        //        }
        //        objects.add(objs);
        //    }
        //}
        //File file = ExcelUtils.exportObjectsWithoutTitle(title, "*导入时本行内容请勿删除，切记！", names, title, objects);
        //ExcelUtils.downloadExcel(file, file.getName(), response);
    }

    ///**
    // * 缓存各个仓库的库存信息
    // * @param src
    // * @param depotCount
    // * @param depotMap
    // * @param i
    // * @return
    // * @throws Exception
    // */
    //private Map<Long, BigDecimal> getStockMapCache(Sheet src, int depotCount, Map<String, Long> depotMap, int i) throws Exception {
    //    Map<Long, BigDecimal> stockMap = new HashMap<>();
    //    for(int j = 1; j<= depotCount; j++) {
    //        int col = 25 + j;
    //        if(col < src.getColumns()){
    //            String depotName = ExcelUtils.getContent(src, 1, col); //获取仓库名称
    //            if(StringUtil.isNotEmpty(depotName)) {
    //                Long depotId = depotMap.get(depotName);
    //                if(depotId!=null && depotId!=0L){
    //                    String stockStr = ExcelUtils.getContent(src, i, col);
    //                    if(StringUtil.isNotEmpty(stockStr)) {
    //                        stockMap.put(depotId, parseBigDecimalEx(stockStr));
    //                    }
    //                }
    //            }
    //        }
    //    }
    //    return stockMap;
    //}


    ///**
    // * 根据条件返回产品列表
    // * @param name
    // * @param standard
    // * @param model
    // * @param unitId
    // * @return
    // */
    //private List<Material> getMaterialListByParam(String name, String standard, String model, Long unitId, String basicBarCode) throws Exception {
    //    List<Material> list = new ArrayList<>();
    //    MaterialExample example = new MaterialExample();
    //    MaterialExample.Criteria criteria = example.createCriteria();
    //    criteria.andNameEqualTo(name);
    //    if (StringUtil.isNotEmpty(model)) {
    //        criteria.andModelEqualTo(model);
    //    }
    //    if (unitId !=null) {
    //        criteria.andUnitIdEqualTo(unitId);
    //    }
    //    criteria.andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
    //    list = materialMapper.selectByExample(example);
    //    return list;
    //}

    //
    ///**
    // * 根据商品和仓库获取当前库存
    // * @param materialId
    // * @param depotId
    // * @return
    // */
    //public BigDecimal getCurrentStockByMaterialIdAndDepotId(Long materialId, Long depotId) {
    //    BigDecimal stock = BigDecimal.ZERO;
    //    MaterialCurrentStockExample example = new MaterialCurrentStockExample();
    //    example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
    //            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
    //    List<MaterialCurrentStock> list = materialCurrentStockMapper.selectByExample(example);
    //    if(list!=null && list.size()>0) {
    //        stock = list.get(0).getCurrentNumber();
    //    } else {
    //        stock = getInitStock(materialId,depotId);
    //    }
    //    return stock;
    //}

}
