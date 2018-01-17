package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.CoinRecord;

public interface CoinRecordMapper {

    void insertCoinRecord(CoinRecord coinRecord);

    CoinRecord inCoinRecord(Long userid);

    CoinRecord outCoinRecord(Long userid);
}
