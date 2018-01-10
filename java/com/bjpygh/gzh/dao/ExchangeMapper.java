package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.Exchange;
import com.bjpygh.gzh.bean.ExchangeInfo;

import java.util.List;

public interface ExchangeMapper {
    List<ExchangeInfo> selectExchanges(Long userid);

    void insertExchange(Exchange exchange);
}
