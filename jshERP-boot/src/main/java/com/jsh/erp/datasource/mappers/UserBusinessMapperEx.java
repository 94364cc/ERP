package com.jsh.erp.datasource.mappers;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

/**
 * Description
 *
 * @Author: qiankunpingtai
 * @Date: 2019/3/29 15:09
 */
public interface UserBusinessMapperEx {

    int batchDeleteUserBusinessByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

}
