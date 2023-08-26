package com.jsh.erp.service.document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.enumPackage.DocumentTypeEnum;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.enumPackage.PackageTypeEnum;
import com.jsh.erp.datasource.vo.DocumentPrintVO;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.sequence.ISequenceService;
import com.jsh.erp.service.supplier.SupplierService;
import com.jsh.erp.service.user.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InDocumentHeadService extends AbsDocumentHeadService implements InitializingBean {
    @Autowired
    SupplierService supplierService;
    @Autowired
    DepotService depotService;
    @Autowired
    MaterialService materialService;
    @Autowired
    ISequenceService sequenceService;
    @Autowired
    LogService logService;
    @Autowired
    UserService userService;

    public final static String SEQ_NAME = "document_number_seq";


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DocumentHead documentHead){
        this.checkParams(documentHead);
        //新增单子主体表
        //-获取编号
        String number = generatNumber(documentHead.getSupplierId(),documentHead.getCreateTime());
        documentHead.setNumber(number);
        //-获取当前登录人
        User user = userService.getCurrentUser();
        documentHead.setCreator(user.getUsername());
        this.save(documentHead);

        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_ADD);
        logService.insertLog(DocumentTypeEnum.getNameById(documentHead.getType()), sb.toString());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDocumentHead(DocumentHead documentHead){
        this.checkParams(documentHead);
        //新增单子主体表
        this.updateById(documentHead);

        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_EDIT);
        logService.insertLog(DocumentTypeEnum.getNameById(documentHead.getType()), sb.toString());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DocumentStrategyFactory.register(DocumentTypeEnum.IN.getType(),this);
    }


    private void checkParams(DocumentHead documentHead){
        ResultEnum.DOCUMENT_SUPPLIER_ERROR.isTrue(ObjectUtil.isNotNull(documentHead.getSupplierId()));
    }

    /**
     * 首字母缩写+客户号+日期+两位自增编号。
     * -获取当天最大的自增编号
     * @param supplierId
     * @param date
     * @return
     */
    private String generatNumber(Long supplierId, LocalDate date){
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Integer maxCurentVal = sequenceService.getMaxCurrentValue(SEQ_NAME,date);
        String maxStr = ""+maxCurentVal;
        if(maxCurentVal<10){
            maxStr = "0"+maxCurentVal;
        }
        return "IN-"+supplierId+dateStr+maxStr;
    }

    /**
     * 打印
     * @param id
     * @return
     */
    @Override
    public DocumentPrintVO print(Long id) {
        DocumentPrintVO documentPrintVO = new DocumentPrintVO();
        //根据id获取单据信息
        DocumentHead documentHead = this.getById(id);
        if(ObjectUtil.isNull(documentHead)){
            return documentPrintVO;
        }

        BeanUtil.copyProperties(documentHead,documentPrintVO);
        //-填充单据信息
        documentPrintVO.setTypeName(DocumentTypeEnum.getNameById(documentHead.getType()));
        documentPrintVO.setPackageTypeName(PackageTypeEnum.getNameById(documentHead.getPackageType()));
        //-填充用户信息
        Long supplierId = documentHead.getSupplierId();
        Supplier supplier = supplierService.getSupplier(supplierId);
        if(ObjectUtil.isNotNull(supplier)){
            documentPrintVO.setSupplierName(supplier.getSupplier());
            documentPrintVO.setTelephone(supplier.getTelephone());
        }
        //根据单据id查询详情

        //汇总计算数量和立方数
        return null;
    }
}
