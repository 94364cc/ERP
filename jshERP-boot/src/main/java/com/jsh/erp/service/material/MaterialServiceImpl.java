package com.jsh.erp.service.material;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.entities.NMaterialCategory;
import com.jsh.erp.datasource.mappers.MaterialMapper;
import com.jsh.erp.datasource.page.MaterialPage;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.material.Interface.IMaterialService;
import com.jsh.erp.service.materialCategory.Interface.IMaterialCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

    private final static String LOGNAME = "商品";

    @Autowired
    LogService logService;

    @Autowired
    IMaterialCategoryService materialCategoryService;
    /**
     * 添加商品
     * @param material
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean insertMaterial(Material material) {
        validRepeat(material);
        this.save(material);
        logService.insertLog(LOGNAME,
            new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(material.getName()).toString());
        return true;
    }


    /**
     * 更新商品
     * @param material
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateMaterial(Material material) {
        validRepeat(material);
        this.updateById(material);
        logService.insertLog(LOGNAME,
            new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(material.getName()).toString());
        return true;
    }


    /**
     * 删除商品
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteMaterial(Long id) {
        return this.batchDeleteMaterial(Arrays.asList(id));
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean batchDeleteMaterial(List<Long> ids) {
        if(CollUtil.isEmpty(ids)){
            return true;
        }

        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Material> list = this.listByIds(ids);
        for(Material material: list){
            sb.append("[").append(material.getName()).append("]");
        }
        logService.insertLog(LOGNAME, sb.toString());

        //删除
        this.removeByIds(ids);
        return true;
    }

    /**
     * 分页
     * @param materialPage
     * @return
     */
    @Override
    public Page<Material> getPage(MaterialPage materialPage) {
        Page<Material> page =  this.page(materialPage,Wrappers.<Material>lambdaQuery()
            .eq(StrUtil.isNotBlank(materialPage.getName()),Material::getName,materialPage.getName())
            .eq(StrUtil.isNotBlank(materialPage.getModel()),Material::getName,materialPage.getModel())
        );

        List<Material> materialList = page.getRecords();
        if(CollUtil.isEmpty(materialList)){
            return page;
        }
        List<Long> categoryIds = materialList.stream().map(Material::getCategoryId).collect(Collectors.toList());
        Map<Long,String> categoryMap = materialCategoryService.getMapByIds(categoryIds);
        for(Material material : materialList){
            material.setCategoryName(categoryMap.get(material.getCategoryId()));
        }
        return page;
    }

    /**
     * 校验数据唯一
     * @param material
     */
    private void validRepeat(Material material) {
        boolean repeatFlag = false;
        List<Material> organs = this.list(Wrappers.<Material>query().lambda()
            .eq(Material::getName, material.getName())
            .eq(Material::getModel, material.getModel())
        );
        if (CollUtil.isNotEmpty(organs)) {
            // 编辑
            if (Optional.ofNullable(material.getId()).isPresent()) {
                // 编辑同一条记录允许名称重复
                repeatFlag = !(organs.size() == 1 && Objects.equals(organs.get(0).getId(), material.getId()));
            } else {
                //新增场景
                repeatFlag = true;
            }
        }
        ResultEnum.MATERIAL_SAVE_ERROR.isFalse(repeatFlag);
    }
}
