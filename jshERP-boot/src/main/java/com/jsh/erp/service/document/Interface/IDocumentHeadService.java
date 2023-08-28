package com.jsh.erp.service.document.Interface;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.DocumentHeadVo4Body;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.page.DocumentHeadPage;
import com.jsh.erp.datasource.page.MaterialPage;
import com.jsh.erp.datasource.vo.DocumentHeadPageVO;
import com.jsh.erp.datasource.vo.DocumentHeadVO;
import com.jsh.erp.datasource.vo.DocumentPrintVO;

public interface IDocumentHeadService extends IService<DocumentHead> {

    /**
     * 分页
     * @return
     */
    Page<DocumentHeadPageVO> getPage(DocumentHeadPage documentHeadPage) throws Exception;

    /**
     * 新增单子
     * @return
     */
    void add(DocumentHead documentHead);

    /**
     * 更新单子
     * @return
     */
    void updateDocumentHead(DocumentHead documentHead);

    /**
     * 删除单子
     * @return
     */
    void deleteDocumentHead(Long id);

    /**
     * 根据id获取详情
     * @return
     */
    DocumentHeadVO getDocumentHeadById(Long id);



    /**
     * 打印详情
     * @return
     */
    DocumentPrintVO print(Long id);


    /**
     * 根据单据详情查询客户
     * @return
     */
    Long getSupplierIdByItemId(Long itemId);

    /**
     * 根据单据详情查询客户
     * @return
     */
    DocumentHead getByNumber(String number);

}
