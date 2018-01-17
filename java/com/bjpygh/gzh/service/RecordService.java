package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.CoinRecord;
import com.bjpygh.gzh.bean.IntegralRecord;
import com.bjpygh.gzh.bean.IntegralRecordExample;
import com.bjpygh.gzh.dao.CoinRecordMapper;
import com.bjpygh.gzh.dao.IntegralMapper;
import com.bjpygh.gzh.dao.IntegralRecordMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class RecordService {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    CoinRecordMapper coinRecordMapper;

    public Status inCoinRecord(String userid) {
        CoinRecord coinRecord = coinRecordMapper.inCoinRecord(Long.valueOf(userid));
        if (coinRecord != null){
            coinRecord.setCreateTime(coinRecord.getCreateTime().split(" ")[0]);
            return Status.success().add("coinRecord", coinRecord);
        }else {
            return Status.fail(-10,"没有记录");
        }

    }

    public Status outCoinRecord(String userid) {
        CoinRecord coinRecord = coinRecordMapper.outCoinRecord(Long.valueOf(userid));
        if (coinRecord != null){
            coinRecord.setCreateTime(coinRecord.getCreateTime().split(" ")[0]);
            return Status.success().add("coinRecord",coinRecord);
        }else {
            return Status.fail(-10,"没有记录");
        }

    }
}
