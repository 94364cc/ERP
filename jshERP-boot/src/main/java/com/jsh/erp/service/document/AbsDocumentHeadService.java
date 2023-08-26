package com.jsh.erp.service.document;

import java.util.List;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.datasource.enumPackage.DocumentTypeEnum;
import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.enumPackage.PackageTypeEnum;
import com.jsh.erp.datasource.mappers.DocumentHeadMapper;
import com.jsh.erp.datasource.page.DocumentHeadPage;
import com.jsh.erp.datasource.vo.DocumentHeadPageVO;
import com.jsh.erp.datasource.vo.DocumentHeadVO;
import com.jsh.erp.datasource.vo.DocumentPrintVO;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.document.Interface.IDocumentHeadService;
import com.jsh.erp.service.document.Interface.IDocumentItemService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbsDocumentHeadService extends ServiceImpl<DocumentHeadMapper, DocumentHead> implements IDocumentHeadService {
    @Autowired
    SupplierService supplierService;
    @Autowired
    IDocumentItemService documentItemService;
    @Autowired
    DepotItemService depotItemService;
    @Autowired
    MaterialService materialService;

    /**
     * 分页接口
     * @return
     */
    @Override
    public Page<DocumentHeadPageVO> getPage(DocumentHeadPage documentHeadPage) throws Exception {
        Page<DocumentHeadPageVO> documentHeadVoPage = new Page<>(documentHeadPage.getCurrent(),documentHeadPage.getSize());
        Page<DocumentHead> page =  this.page(documentHeadPage,Wrappers.<DocumentHead>lambdaQuery()
            .eq(ObjectUtil.isNotNull(documentHeadPage.getSupplierId()),DocumentHead::getSupplierId,documentHeadPage.getSupplierId())
            .eq(ObjectUtil.isNotNull(documentHeadPage.getNumber()),DocumentHead::getNumber,documentHeadPage.getNumber())
            .ge(ObjectUtil.isNotNull(documentHeadPage.getBeginDate()),DocumentHead::getCreateTime,documentHeadPage.getBeginDate())
            .le(ObjectUtil.isNotNull(documentHeadPage.getEndDate()),DocumentHead::getCreateTime,documentHeadPage.getEndDate())
        );

        BeanUtil.copyProperties(page,documentHeadPage);
        List<DocumentHead> materialList = page.getRecords();
        if(CollUtil.isEmpty(materialList)){
            return documentHeadVoPage;
        }
        List<DocumentHeadPageVO> pageVOS = CollUtil.newArrayList();

        for(DocumentHead documentHead : materialList){
            DocumentHeadPageVO documentHeadPageVO = new DocumentHeadPageVO();
            BeanUtil.copyProperties(documentHead,documentHeadPageVO);
            //转义客户名称
            Supplier supplier = supplierService.getSupplier(documentHead.getSupplierId());
            if(ObjectUtil.isNotNull(supplier)){
                documentHeadPageVO.setSupplierName(supplier.getSupplier());
            }
            pageVOS.add(documentHeadPageVO);
        }
        documentHeadVoPage.setRecords(pageVOS);
        return documentHeadVoPage;
    }

    /**
     * 删除单子
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDocumentHead(Long id) {
        //删除详情单子
        documentItemService.deleteByHeadId(id);
        //删除主体单子
        this.deleteDocumentHead(id);
    }

    @Override
    public DocumentHeadVO getDocumentHeadById(Long id) {
        DocumentHead documentHead = this.getById(id);
        DocumentHeadVO documentHeadVO = new DocumentHeadVO();
        BeanUtil.copyProperties(documentHead,documentHeadVO);
        documentHeadVO.setTypeName(DocumentTypeEnum.getNameById(documentHead.getType()));
        documentHeadVO.setPackageTypeName(PackageTypeEnum.getNameById(documentHead.getType()));
        //转义客户名称
        Supplier supplier = supplierService.getSupplier(documentHead.getSupplierId());
        if(ObjectUtil.isNotNull(supplier)){
            documentHeadVO.setSupplierName(supplier.getSupplier());
        }
        return documentHeadVO;
    }


}
