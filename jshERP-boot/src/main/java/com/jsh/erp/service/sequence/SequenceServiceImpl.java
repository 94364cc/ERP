package com.jsh.erp.service.sequence;

import java.time.LocalDate;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsh.erp.datasource.entities.Sequence;
import com.jsh.erp.datasource.mappers.SequenceMapper;
import com.jsh.erp.exception.ResultEnum;
import org.springframework.stereotype.Service;

@Service
public class SequenceServiceImpl extends ServiceImpl<SequenceMapper, Sequence> implements ISequenceService {



    /**
     * 获取日期当天最大值
     * @param seqName
     * @param date
     * @return
     */
    @Override
    public Integer getMaxCurrentValue(String seqName, LocalDate date) {
        Sequence sequence = this.baseMapper.getMaxCurrentVal(seqName,date.toString());
        Integer newCurrentVal=0;
        if(ObjectUtil.isNull(sequence)){
            newCurrentVal = newCurrentVal+1;
            //新增一条当天的记录
            Sequence addSeqence = new Sequence();
            addSeqence.setSeqName(seqName);
            addSeqence.setDate(date);
            addSeqence.setCurrentVal(newCurrentVal);
            this.save(addSeqence);
        }else {
            //加1
            newCurrentVal = sequence.getCurrentVal()+ sequence.getIncrementVal();
            Integer result = this.baseMapper.addMaxCurrentVal(sequence.getId(),newCurrentVal,sequence.getCurrentVal());
            Integer number = 0;
            //乐观锁更新，如果失败，重试5次
            while(result<0 && number<=5){
                number +=1;
                this.baseMapper.addMaxCurrentVal(sequence.getId(),newCurrentVal,sequence.getCurrentVal());
            }
            ResultEnum.DOCUMENT_SEQENCE_ERROR.isTrue(result>0);
        }

        return newCurrentVal;
    }
}
