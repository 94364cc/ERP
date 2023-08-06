package com.jsh.erp.service.materialCategory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.Name;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.entities.NMaterialCategory;
import com.jsh.erp.datasource.mappers.NMaterialCategoryMapper;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.materialCategory.Interface.IMaterialCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialCategoryServiceImpl extends ServiceImpl<NMaterialCategoryMapper, NMaterialCategory> implements
    IMaterialCategoryService {

    private final static String LOGNAME = "商品分类";

    @Autowired
    LogService logService;

    /**
     * 根据ids获取map<id,name>
     * @return
     */
    @Override
    public Map<Long, String> getMapByIds(List<Long> ids) {
        List<NMaterialCategory> materialCategories = this.listByIds(ids);
        return materialCategories.stream().collect(Collectors.toMap(NMaterialCategory::getId,NMaterialCategory::getName));
    }
}
