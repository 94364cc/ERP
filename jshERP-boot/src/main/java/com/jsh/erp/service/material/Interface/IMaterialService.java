package com.jsh.erp.service.material.Interface;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.Material;

public interface IMaterialService extends IService<Material> {

    /**
     * 添加商品
     * @param material
     * @return
     */
    Boolean insertMaterial(Material material);

}
