package com.jsh.erp.datasource.mappers;

import java.time.LocalDate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.Sequence;
import org.apache.ibatis.annotations.Param;

public interface SequenceMapper extends BaseMapper<Sequence> {


    Sequence getMaxCurrentVal(@Param("seqName")String seqName,@Param("date")String date);
    Integer addMaxCurrentVal(@Param("id")Long id,@Param("newMaxCurrentVal")Integer newMaxCurrentVal,@Param("oldMaxCurrentVal")Integer oldMaxCurrentVal);
}