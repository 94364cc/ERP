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
     * 新增单子
     * @return
     */
    DocumentHeadVO getDocumentHeadById(Long id);

}
