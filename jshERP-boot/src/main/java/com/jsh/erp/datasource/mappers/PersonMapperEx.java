package com.jsh.erp.datasource.mappers;

import java.util.List;

import com.jsh.erp.datasource.entities.Person;
import org.apache.ibatis.annotations.Param;

public interface PersonMapperEx {

    List<Person> selectByConditionPerson(
            @Param("name") String name,
            @Param("type") String type,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByPerson(
            @Param("name") String name,
            @Param("type") String type);

    int batchDeletePersonByIds(@Param("ids") String ids[]);
}