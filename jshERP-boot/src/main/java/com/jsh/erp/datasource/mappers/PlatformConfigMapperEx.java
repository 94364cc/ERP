package com.jsh.erp.datasource.mappers;

import java.util.List;

import com.jsh.erp.datasource.entities.PlatformConfig;
import org.apache.ibatis.annotations.Param;

public interface PlatformConfigMapperEx {

    List<PlatformConfig> selectByConditionPlatformConfig(
            @Param("platformKey") String platformKey,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByPlatformConfig(
            @Param("platformKey") String platformKey);

}