package com.jsh.erp.service.document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.enumPackage.DocumentTypeEnum;
import com.jsh.erp.datasource.enumPackage.PackageTypeEnum;
import com.jsh.erp.datasource.vo.DocumentItemPrintVO;
import com.jsh.erp.datasource.vo.DocumentPrintVO;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.sequence.ISequenceService;
import com.jsh.erp.service.supplier.SupplierService;
import com.jsh.erp.service.user.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service(value = "transforDocumentHeadService")
public class TransforDocumentHeadService extends AbsDocumentHeadService implements InitializingBean {
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

    @Override
    public void afterPropertiesSet() throws Exception {
        DocumentStrategyFactory.register(DocumentTypeEnum.TRANSFOR.getType(),this);
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
        return "TF"+supplierId+dateStr+maxStr;
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
        //DocumentHead documentHead = this.getById(id);
        //if(ObjectUtil.isNull(documentHead)){
        //    return documentPrintVO;
        //}
        //
        //BeanUtil.copyProperties(documentHead,documentPrintVO);
        ////-填充单据信息
        //documentPrintVO.setTypeName(DocumentTypeEnum.getNameById(documentHead.getType()));
        //documentPrintVO.setPackageTypeName(PackageTypeEnum.getNameById(documentHead.getPackageType()));
        ////-填充用户信息
        //Long supplierId = documentHead.getSupplierId();
        //Supplier supplier = supplierService.getSupplier(supplierId);
        //if(ObjectUtil.isNotNull(supplier)){
        //    documentPrintVO.setSupplierName(supplier.getSupplier());
        //    documentPrintVO.setTelephone(supplier.getTelephone());
        //}
        ////根据单据id查询详情
        //List<DocumentItemPrintVO> documentItemPrintVOList =documentItemService.printByHeadId(id);
        ////汇总计算数量和立方数
        ////如果是半包，不计算体积
        //if(PackageTypeEnum.ALL.getType()==documentHead.getPackageType()){
        //    BigDecimal volumeCount = documentItemPrintVOList.stream().reduce(new BigDecimal(0),(total,documentItemPrintVO)-> total.add(documentItemPrintVO.getVolume()),BigDecimal::add);
        //    documentPrintVO.setVolumeCount(volumeCount);
        //}
        //Integer operNumberCount = documentItemPrintVOList.stream().reduce(0,(total,documentItemPrintVO)-> total+documentItemPrintVO.getOperNumber(),Integer::sum);
        //documentPrintVO.setNumberCount(operNumberCount);
        return documentPrintVO;
    }
}
