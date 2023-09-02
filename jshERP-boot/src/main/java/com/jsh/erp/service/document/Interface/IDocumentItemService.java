package com.jsh.erp.service.document.Interface;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.dto.DocumentItemAddDto;
import com.jsh.erp.datasource.dto.DocumentItemUpdateDto;
import com.jsh.erp.datasource.entities.DocumentHead;
import com.jsh.erp.datasource.entities.DocumentItem;
import com.jsh.erp.datasource.entities.DocumentItemFlow;
import com.jsh.erp.datasource.vo.DocumentItemPrintVO;
import com.jsh.erp.utils.Constants;
import org.springframework.web.bind.annotation.RequestParam;

public interface IDocumentItemService extends IService<DocumentItem> {

    /**
     * 新增单据详情
     * @param documentItemAddDto
     */
    void add(DocumentItemAddDto documentItemAddDto);

    /**
     * 新更新单据详情
     * @param documentItemUpdateDto
     */
    void update(DocumentItemUpdateDto documentItemUpdateDto);

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
    List<DocumentItem> getByHeadId(Long headId);


    /**
     * 根据主体id获取详情列表
     * @param headId
     */
    List<DocumentItemPrintVO> printByHeadId(Long headId);


    /**
     * 根据商品id获取流水
     * @param materialId
     */
    Page<DocumentItemFlow> getFlowByMaterialId(Integer pageSize,
                                               Integer currentPage,
                                               String number,
                                               String beginTime,
                                               String endTime,
                                               Long materialId);

}
