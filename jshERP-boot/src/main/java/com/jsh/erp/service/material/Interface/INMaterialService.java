package com.jsh.erp.service.material.Interface;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.page.MaterialPage;

public interface INMaterialService extends IService<Material> {

    /**
     * 添加商品
     * @param material
     * @return
     */
    Boolean insertMaterial(Material material);
    /**
     * 更新商品
     * @param material
     * @return
     */
    Boolean updateMaterial(Material material);
    /**
     * 删除商品
     * @param id
     * @return
     */
    Boolean deleteMaterial(Long id);

    /**
     * 删除商品
     * @param ids
     * @return
     */
    Boolean batchDeleteMaterial(List<Long> ids);

    /**
     * 分页参数
     * @param materialPage
     * @return
     */
    Page<Material> getPage(MaterialPage materialPage);

    /**
     * 根据ids查询map<id,name>
     * @param ids
     * @return
     */
    Map<Long,String> getMayByIds(List<Long> ids);


    /**
     * 根据ids查询map<id,name>
     * @param ids
     * @return
     */
    Map<Long,Material> getEntityMayByIds(List<Long> ids);

}
