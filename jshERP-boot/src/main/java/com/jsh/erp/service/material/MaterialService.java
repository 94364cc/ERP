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
import com.jsh.erp.datasource.mappers.DepotItemMapperEx;
import com.jsh.erp.datasource.mappers.MaterialCategoryMapperEx;
import com.jsh.erp.datasource.mappers.MaterialCurrentStockMapper;
import com.jsh.erp.datasource.mappers.MaterialCurrentStockMapperEx;
import com.jsh.erp.datasource.mappers.MaterialInitialStockMapper;
import com.jsh.erp.datasource.mappers.MaterialInitialStockMapperEx;
import com.jsh.erp.datasource.mappers.MaterialMapper;
import com.jsh.erp.datasource.mappers.MaterialMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.materialCategory.MaterialCategoryService;
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
    private MaterialMapperEx materialMapperEx;
    @Resource
    private MaterialCategoryMapperEx materialCategoryMapperEx;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;
    @Resource
    private DepotItemMapperEx depotItemMapperEx;
    @Resource
    private UnitService unitService;
    @Resource
    private MaterialInitialStockMapper materialInitialStockMapper;
    @Resource
    private MaterialCurrentStockMapper materialCurrentStockMapper;
    @Resource
    private MaterialCurrentStockMapperEx materialCurrentStockMapperEx;
    @Resource
    private DepotService depotService;

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

    public Long countMaterial(String materialParam, String color, String materialOther, String weight, String expiryNum,
                              String enableSerialNumber, String enableBatchNumber, String position, String enabled,
                              String remark, String categoryId,String mpList)throws Exception {
        Long result =null;
        try{
            List<Long> idList = new ArrayList<>();
            if(StringUtil.isNotEmpty(categoryId)){
                idList = getListByParentId(Long.parseLong(categoryId));
            }
            result= materialMapperEx.countsByMaterial(materialParam, color, materialOther, weight, expiryNum,
                    enableSerialNumber, enableBatchNumber, position, enabled, remark, idList, mpList);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertMaterial(JSONObject obj, HttpServletRequest request)throws Exception {
        Material m = JSONObject.parseObject(obj.toJSONString(), Material.class);
        try{
            materialMapperEx.insertSelectiveEx(m);
            logService.insertLog("商品",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(m.getName()).toString(), request);
            return 1;
        }catch (BusinessRunTimeException ex) {
            throw new BusinessRunTimeException(ex.getCode(), ex.getMessage());
        }
        catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateMaterial(JSONObject obj, HttpServletRequest request) throws Exception{
        Material material = JSONObject.parseObject(obj.toJSONString(), Material.class);
        try{
            materialMapper.updateByPrimaryKeySelective(material);
            logService.insertLog("商品",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(material.getName()).toString(), request);
            return 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteMaterial(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterial(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialByIds(String ids) throws Exception{
        String [] idArray=ids.split(",");
        //校验单据子表	jsh_depot_item
        List<DepotItem> depotItemList =null;
        try{
            depotItemList=  depotItemMapperEx.getDepotItemListListByMaterialIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(depotItemList!=null&&depotItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,MaterialIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Material> list = getMaterialListByIds(ids);
        for(Material material: list){
            sb.append("[").append(material.getName()).append("]");
        }
        logService.insertLog("商品", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        //校验通过执行删除操作
        try{
            //逻辑删除商品
            materialMapperEx.batchDeleteMaterialByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
            return 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
            return 0;
        }
    }

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

    public int checkIsExist(Long id, String name, String model, String standard,Long unitId)throws Exception {
        return materialMapperEx.checkIsExist(id, name, model, standard, unitId);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String ids)throws Exception {
        logService.insertLog("商品",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> materialIds = StringUtil.strToLongList(ids);
        Material material = new Material();
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdIn(materialIds);
        int result =0;
        try{
            result=  materialMapper.updateByExampleSelective(material, example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public Unit findUnit(Long mId)throws Exception{
        Unit unit = new Unit();
        try{
            List<Unit> list = materialMapperEx.findUnitList(mId);
            if(list!=null && list.size()>0) {
                unit = list.get(0);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return unit;
    }

    public List<MaterialVo4Unit> findById(Long id)throws Exception{
        List<MaterialVo4Unit> list =null;
        try{
            list=  materialMapperEx.findById(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialVo4Unit> findByIdWithBarCode(Long meId)throws Exception{
        List<MaterialVo4Unit> list =null;
        try{
            list=  materialMapperEx.findByIdWithBarCode(meId);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Long> getListByParentId(Long parentId) {
        List<Long> idList = new ArrayList<Long>();
        List<MaterialCategory> list = materialCategoryMapperEx.getListByParentId(parentId);
        idList.add(parentId);
        if(list!=null && list.size()>0) {
            getIdListByParentId(idList, parentId);
        }
        return idList;
    }

    public List<Long> getIdListByParentId(List<Long> idList, Long parentId){
        List<MaterialCategory> list = materialCategoryMapperEx.getListByParentId(parentId);
        if(list!=null && list.size()>0) {
            for(MaterialCategory mc : list){
                idList.add(mc.getId());
                getIdListByParentId(idList, mc.getId());
            }
        }
        return idList;
    }


    public List<MaterialVo4Unit> findBySelectWithBarCode(Long categoryId, String q, String enableSerialNumber,
                                                         String enableBatchNumber, Integer offset, Integer rows)throws Exception{
        List<MaterialVo4Unit> list =null;
        try{
            List<Long> idList = new ArrayList<>();
            if(categoryId!=null){
                Long parentId = categoryId;
                idList = getListByParentId(parentId);
            }
            if(StringUtil.isNotEmpty(q)) {
                q = q.replace("'", "");
                q = q.trim();
            }
            list=  materialMapperEx.findBySelectWithBarCode(idList, q, enableSerialNumber, enableBatchNumber, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findBySelectWithBarCodeCount(Long categoryId, String q, String enableSerialNumber,
                                            String enableBatchNumber)throws Exception{
        int result=0;
        try{
            List<Long> idList = new ArrayList<>();
            if(categoryId!=null){
                Long parentId = categoryId;
                idList = getListByParentId(parentId);
            }
            if(StringUtil.isNotEmpty(q)) {
                q = q.replace("'", "");
            }
            result = materialMapperEx.findBySelectWithBarCodeCount(idList, q, enableSerialNumber, enableBatchNumber);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

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

    /**
     * 缓存各个仓库的库存信息
     * @param src
     * @param depotCount
     * @param depotMap
     * @param i
     * @return
     * @throws Exception
     */
    private Map<Long, BigDecimal> getStockMapCache(Sheet src, int depotCount, Map<String, Long> depotMap, int i) throws Exception {
        Map<Long, BigDecimal> stockMap = new HashMap<>();
        for(int j = 1; j<= depotCount; j++) {
            int col = 25 + j;
            if(col < src.getColumns()){
                String depotName = ExcelUtils.getContent(src, 1, col); //获取仓库名称
                if(StringUtil.isNotEmpty(depotName)) {
                    Long depotId = depotMap.get(depotName);
                    if(depotId!=null && depotId!=0L){
                        String stockStr = ExcelUtils.getContent(src, i, col);
                        if(StringUtil.isNotEmpty(stockStr)) {
                            stockMap.put(depotId, parseBigDecimalEx(stockStr));
                        }
                    }
                }
            }
        }
        return stockMap;
    }


    /**
     * 根据条件返回产品列表
     * @param name
     * @param standard
     * @param model
     * @param unitId
     * @return
     */
    private List<Material> getMaterialListByParam(String name, String standard, String model, Long unitId, String basicBarCode) throws Exception {
        List<Material> list = new ArrayList<>();
        MaterialExample example = new MaterialExample();
        MaterialExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        if (StringUtil.isNotEmpty(model)) {
            criteria.andModelEqualTo(model);
        }
        if (unitId !=null) {
            criteria.andUnitIdEqualTo(unitId);
        }
        criteria.andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        list = materialMapper.selectByExample(example);
        return list;
    }

    public BigDecimal parseBigDecimalEx(String str) throws Exception{
        if(!StringUtil.isEmpty(str)) {
            return  new BigDecimal(str);
        } else {
            return null;
        }
    }

    public BigDecimal parsePrice(String price, String ratio) throws Exception{
        if(StringUtil.isEmpty(price) || StringUtil.isEmpty(ratio)) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal pr=new BigDecimal(price);
            BigDecimal r=new BigDecimal(ratio);
            return pr.multiply(r);
        }
    }

    /**
     * 根据商品获取初始库存-多仓库
     * @param depotList
     * @param materialId
     * @return
     */
    public BigDecimal getInitStockByMidAndDepotList(List<Long> depotList, Long materialId) {
        BigDecimal stock = BigDecimal.ZERO;
        MaterialInitialStockExample example = new MaterialInitialStockExample();
        if(depotList!=null && depotList.size()>0) {
            example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdIn(depotList)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        } else {
            example.createCriteria().andMaterialIdEqualTo(materialId)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        }
        List<MaterialInitialStock> list = materialInitialStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            for(MaterialInitialStock ms: list) {
                if(ms!=null) {
                    stock = stock.add(ms.getNumber());
                }
            }
        }
        return stock;
    }

    /**
     * 根据商品和仓库获取初始库存
     * @param materialId
     * @param depotId
     * @return
     */
    public BigDecimal getInitStock(Long materialId, Long depotId) {
        BigDecimal stock = BigDecimal.ZERO;
        MaterialInitialStockExample example = new MaterialInitialStockExample();
        example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialInitialStock> list = materialInitialStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            stock = list.get(0).getNumber();
        }
        return stock;
    }

    /**
     * 根据商品和仓库获取当前库存
     * @param materialId
     * @param depotId
     * @return
     */
    public BigDecimal getCurrentStockByMaterialIdAndDepotId(Long materialId, Long depotId) {
        BigDecimal stock = BigDecimal.ZERO;
        MaterialCurrentStockExample example = new MaterialCurrentStockExample();
        example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialCurrentStock> list = materialCurrentStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            stock = list.get(0).getCurrentNumber();
        } else {
            stock = getInitStock(materialId,depotId);
        }
        return stock;
    }

    /**
     * 根据商品列表获取当前库存Map
     * @param list
     * @return
     */
    public Map<Long,BigDecimal> getCurrentStockMapByMaterialList(List<MaterialVo4Unit> list) {
        Map<Long,BigDecimal> map = new HashMap<>();
        List<Long> materialIdList = new ArrayList<>();
        for(MaterialVo4Unit materialVo4Unit: list) {
            materialIdList.add(materialVo4Unit.getId());
        }
        List<MaterialCurrentStock> mcsList = materialCurrentStockMapperEx.getCurrentStockMapByIdList(materialIdList);
        for(MaterialCurrentStock materialCurrentStock: mcsList) {
            map.put(materialCurrentStock.getMaterialId(), materialCurrentStock.getCurrentNumber());
        }
        return map;
    }

    /**
     * 根据商品和仓库获取安全库存信息
     * @param materialId
     * @param depotId
     * @return
     */
    public MaterialInitialStock getSafeStock(Long materialId, Long depotId) {
        MaterialInitialStock materialInitialStock = new MaterialInitialStock();
        MaterialInitialStockExample example = new MaterialInitialStockExample();
        example.createCriteria().andMaterialIdEqualTo(materialId).andDepotIdEqualTo(depotId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialInitialStock> list = materialInitialStockMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            materialInitialStock = list.get(0);
        }
        return materialInitialStock;
    }

    public List<MaterialVo4Unit> getMaterialByMeId(Long meId) {
        List<MaterialVo4Unit> result = new ArrayList<MaterialVo4Unit>();
        try{
            if(meId!=null) {
                result= materialMapperEx.getMaterialByMeId(meId);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<String> getMaterialNameList() {
        return materialMapperEx.getMaterialNameList();
    }

    public List<MaterialVo4Unit> getMaterialByBarCode(String barCode) {
        String [] barCodeArray=barCode.split(",");
        return materialMapperEx.getMaterialByBarCode(barCodeArray);
    }

    public List<MaterialInitialStockWithMaterial> getInitialStockWithMaterial(List<Long> depotList) {
        return materialMapperEx.getInitialStockWithMaterial(depotList);
    }

    //public List<MaterialVo4Unit> getListWithStock(List<Long> depotList, List<Long> idList, String position, String materialParam, Integer zeroStock,
    //                                              String column, String order, Integer offset, Integer rows) throws Exception {
    //    Map<Long, BigDecimal> initialStockMap = new HashMap<>();
    //    List<MaterialInitialStockWithMaterial> initialStockList = getInitialStockWithMaterial(depotList);
    //    for (MaterialInitialStockWithMaterial mism: initialStockList) {
    //        initialStockMap.put(mism.getMaterialId(), mism.getNumber());
    //    }
    //    List<MaterialVo4Unit> dataList = materialMapperEx.getListWithStock(depotList, idList, position, materialParam, zeroStock, column, order, offset, rows);
    //    return dataList;
    //}

}
