package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.Exchange;
import com.bjpygh.gzh.bean.ExchangeInfo;

import java.util.HashMap;
import java.util.List;

public interface ExchangeMapper {
    List<ExchangeInfo> selectExchanges(Long userid);

    void insertExchange(Exchange exchange);

    ExchangeInfo selectExchange(HashMap<String, String> map);

    Long selectExchangeId(String exchangeNumber);
}
