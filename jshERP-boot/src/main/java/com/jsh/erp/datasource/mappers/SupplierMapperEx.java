package com.jsh.erp.datasource.mappers;

import java.util.Date;
import java.util.List;

import com.jsh.erp.datasource.entities.Supplier;
import org.apache.ibatis.annotations.Param;

public interface SupplierMapperEx {

    List<Supplier> selectByConditionSupplier(
            @Param("supplier") String supplier,
            @Param("type") String type,
            @Param("phonenum") String phonenum,
            @Param("telephone") String telephone,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsBySupplier(
            @Param("supplier") String supplier,
            @Param("type") String type,
            @Param("phonenum") String phonenum,
            @Param("telephone") String telephone,
            @Param("creatorArray") String[] creatorArray);

    List<Supplier> findByAll(
            @Param("supplier") String supplier,
            @Param("type") String type,
            @Param("phonenum") String phonenum,
            @Param("telephone") String telephone);

    int batchDeleteSupplierByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    Supplier getSupplierByNameAndType(
            @Param("supplier") String supplier,
            @Param("type") String type);
}