package com.jsh.erp.service.material.Interface;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.Material;

public interface IMaterialService extends IService<Material> {

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
}
