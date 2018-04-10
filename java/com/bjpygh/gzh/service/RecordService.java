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
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    CoinRecordMapper coinRecordMapper;

    public Status inCoinRecord(String userid) {
        List<CoinRecord> coinRecords = coinRecordMapper.inCoinRecord(Long.valueOf(userid));
        List<CoinRecord> coinRecords1 = new ArrayList<CoinRecord>();
        if (coinRecords.size()>0){
            for (CoinRecord c : coinRecords){
                c.setCreateTime(c.getCreateTime().split(" ")[0]);
                coinRecords1.add(c);
            }
            return Status.success().add("coinRecord", coinRecords1);
        }else {
            return Status.fail(-10,"没有记录");
        }

    }

    public Status outCoinRecord(String userid) {
        List<CoinRecord> coinRecords = coinRecordMapper.outCoinRecord(Long.valueOf(userid));
        List<CoinRecord> coinRecords1 = new ArrayList<CoinRecord>();
        if (coinRecords.size()>0){
            for (CoinRecord c : coinRecords){
                c.setCreateTime(c.getCreateTime().split(" ")[0]);
                c.setGeneralValue(Math.abs(c.getGeneralValue()));
                c.setCoinValue(Math.abs(c.getCoinValue()));
                coinRecords1.add(c);
            }
            return Status.success().add("coinRecord", coinRecords1);
        }else {
            return Status.fail(-10,"没有记录");
        }
    }
}
