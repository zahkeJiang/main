package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.CoinRecord;

import java.util.List;

public interface CoinRecordMapper {

    void insertCoinRecord(CoinRecord coinRecord);

    List<CoinRecord> inCoinRecord(Long userId);

    List<CoinRecord> outCoinRecord(Long userId);
}
