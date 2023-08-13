package com.jsh.erp.datasource.mappers;

import java.util.Date;
import java.util.List;

import com.jsh.erp.datasource.entities.SystemConfig;
import org.apache.ibatis.annotations.Param;

public interface SystemConfigMapperEx {

    List<SystemConfig> selectByConditionSystemConfig(
            @Param("companyName") String companyName,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsBySystemConfig(
            @Param("companyName") String companyName);

    int batchDeleteSystemConfigByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);
}