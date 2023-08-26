package com.jsh.erp.service.document.Interface;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.DocumentItem;

public interface IDocumentItemService extends IService<DocumentItem> {

    /**
     * 新增单据详情
     * @param documentItem
     */
    void add(DocumentItem documentItem);

    /**
     * 新更新单据详情
     * @param documentItem
     */
    void update(DocumentItem documentItem);

    /**
     * 新更新单据详情
     * @param id
     */
    void delete(Long id);

    /**
     * 新更新单据详情
     * @param headId
     */
    void deleteByHeadId(Long headId);


    /**
     * 根据主体id获取详情列表
     * @param headId
     */
    List<DocumentItem> getByHeadId(Long headId) throws Exception;


    /**
     * 根据主体id获取数量
     * @param headId
     */
    Integer countByHeadId(Long headId);
}
