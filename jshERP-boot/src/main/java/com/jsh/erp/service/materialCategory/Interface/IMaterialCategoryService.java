package com.jsh.erp.service.materialCategory.Interface;

import java.util.List;
import java.util.Map;

import javax.naming.Name;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.NMaterialCategory;

public interface IMaterialCategoryService  extends IService<NMaterialCategory> {

    /**
     * 根据ids获取map<id,name>
     * @param ids
     * @return
     */
    Map<Long, String> getMapByIds(List<Long> ids);
}
