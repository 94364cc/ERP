package com.jsh.erp.service.material;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.mappers.MaterialMapper;
import com.jsh.erp.exception.ResultEnum;
import com.jsh.erp.service.material.Interface.IMaterialService;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

    /**
     * 添加商品
     * @param material
     * @return
     */
    @Override
    public Boolean insertMaterial(Material material) {
        validRepeat(material);
        return this.save(material);
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
