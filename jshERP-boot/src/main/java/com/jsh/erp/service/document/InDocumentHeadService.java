package com.jsh.erp.service.document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.entities.InDocumentPrint;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.enumPackage.DocumentTypeEnum;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.enumPackage.PackageTypeEnum;
import com.jsh.erp.datasource.vo.DocumentItemPrintVO;
import com.jsh.erp.datasource.vo.DocumentPrintVO;
import com.jsh.erp.datasource.vo.InDocumentPrintVO;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.Interface.INMaterialService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.sequence.ISequenceService;
import com.jsh.erp.service.supplier.SupplierService;
import com.jsh.erp.service.user.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Primary
@Service(value = "inDocumentHeadService")
public class InDocumentHeadService extends AbsDocumentHeadService implements InitializingBean {
    @Autowired
    SupplierService supplierService;
    @Autowired
    DepotService depotService;
    @Autowired
    INMaterialService materialService;
    @Autowired
    ISequenceService sequenceService;
    @Autowired
    LogService logService;
    @Autowired
    UserService userService;

    public final static String SEQ_NAME = "document_number_seq";

    @Override
    public void afterPropertiesSet() throws Exception {
        DocumentStrategyFactory.register(DocumentTypeEnum.IN.getType(),this);
    }


    /**
     * 首字母缩写+客户号+日期+两位自增编号。
     * -获取当天最大的自增编号
     * @param supplierId
     * @param date
     * @return
     */
    @Override
    public String generatNumber(Long supplierId, LocalDate date){
        String dateStr = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Integer maxCurentVal = sequenceService.getMaxCurrentValue(SEQ_NAME,date);
        String maxStr = ""+maxCurentVal;
        if(maxCurentVal<10){
            maxStr = "0"+maxCurentVal;
        }
        return "IN"+supplierId+dateStr+maxStr;
    }

    /**
     * 打印
     * @param id
     * @return
     */
    @Override
    public DocumentPrintVO print(Long id) {
        InDocumentPrintVO inDocumentPrintVO =new InDocumentPrintVO();
        List<DocumentItem>  documentItems = documentItemService.getByHeadId(id);
        if(CollUtil.isEmpty(documentItems)){
            return inDocumentPrintVO;
        }
        List<InDocumentPrint> inDocumentPrints=new ArrayList<>();
        for(DocumentItem documentItem : documentItems){
            InDocumentPrint inDocumentPrint = new InDocumentPrint();
            inDocumentPrint.setModel(documentItem.getModel());
            inDocumentPrint.setOperNumber(documentItem.getOperNumber());
            inDocumentPrints.add(inDocumentPrint);
        }
        inDocumentPrintVO.setInDocumentPrints(inDocumentPrints);
        return inDocumentPrintVO;
    }
}
