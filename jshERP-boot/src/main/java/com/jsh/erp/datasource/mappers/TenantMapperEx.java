package com.jsh.erp.datasource.mappers;

import java.util.List;

import com.jsh.erp.datasource.entities.TenantEx;
import org.apache.ibatis.annotations.Param;

public interface TenantMapperEx {

    List<TenantEx> selectByConditionTenant(
            @Param("loginName") String loginName,
            @Param("type") String type,
            @Param("enabled") String enabled,
            @Param("remark") String remark,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByTenant(
            @Param("loginName") String loginName,
            @Param("type") String type,
            @Param("enabled") String enabled,
            @Param("remark") String remark);
}