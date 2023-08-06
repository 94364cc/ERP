package com.jsh.erp.datasource.mappers;

import java.util.List;

import com.jsh.erp.datasource.entities.Material;
import org.apache.ibatis.annotations.Param;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/1/22 14:54
 */
public interface MaterialMapperEx {

    List<Material> getMaterialListByUnitIds(@Param("unitIds") String[] unitIds);
    List<Material> getMaterialListByCategoryIds(@Param("categoryIds") String[] categoryIds);


}
