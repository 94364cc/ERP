package com.jsh.erp.service.sequence;

import java.time.LocalDate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsh.erp.datasource.entities.Sequence;

/**
 * Description
 *
 * @Author: jishenghua
 * @Date: 2021/3/16 16:33
 */
public interface ISequenceService extends IService<Sequence> {

    /**
     * 获取日期当天最大值
     * @param seqName
     * @param localDate
     * @return
     */
    Integer getMaxCurrentValue(String seqName, LocalDate localDate);
}
