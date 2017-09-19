package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.IntegralRecord;
import com.bjpygh.gzh.bean.IntegralRecordExample;
import com.bjpygh.gzh.dao.IntegralRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    @Autowired
    IntegralRecordMapper integralRecordMapper;


    public void insertRecord(IntegralRecord record) {
        integralRecordMapper.insertSelective(record);
    }

    public List<IntegralRecord> getRecordById(String userid) {
        IntegralRecordExample example = new IntegralRecordExample();
        IntegralRecordExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.parseLong(userid));
        return integralRecordMapper.selectByExample(example);
    }
}
