package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.dao.*;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.utils.CodeUtil;
import com.bjpygh.gzh.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    ExchangeMapper exchangeMapper;

    @Autowired
    CoinRecordMapper coinRecordMapper;

    @Autowired
    IntegralMapper integralMapper;

    @Autowired
    AwardMapper awardMapper;

    public List<ExchangeInfo> selectExchanges(String userid) {
        return exchangeMapper.selectExchanges(Long.valueOf(userid));
    }


    //兑换商品
    public Status insertExchanges(String userid, Exchange exchange) {
        Award award = awardMapper.getAward(Long.valueOf(exchange.getAwardId()));
        if (award.getAmount()>0){
            Integral integral = integralMapper.selectIntegral(Long.valueOf(userid));
            if (integral.getGeneralCoin()>=award.getGeneralCoin()&&integral.getCoin()>=award.getCoin()){
                //生成兑换编号
                String exchangeNumber = DateUtils.getSystemTimeInMM()+ CodeUtil.getRandom(4);
                exchange.setExchangeNumber(exchangeNumber);
                //创建商品消费记录
                exchange.setStatus(0);
                exchangeMapper.insertExchange(exchange);
                //扣除积分
                integral.setGeneralCoin(integral.getGeneralCoin()-award.getGeneralCoin());
                integral.setCoin(integral.getCoin()-award.getCoin());
                integralMapper.updateInegral(integral);
                //商品数量-1
                award.setAmount(award.getAmount()-1);
                awardMapper.updateAwardAmount(award);
                //添加积分消费记录
                CoinRecord coinRecord = new CoinRecord();
                coinRecord.setNote("奖品兑换");
                coinRecord.setGeneralValue((int) (0-award.getGeneralCoin()));
                coinRecord.setCoinValue((int) (0-award.getCoin()));
                coinRecord.setUserId(Long.valueOf(userid));
                coinRecordMapper.insertCoinRecord(coinRecord);

                Long aLong = exchangeMapper.selectExchangeId(exchangeNumber);
                return Status.success().add("exchangeId",aLong);
            }else{
                return Status.fail(-20,"余额不足");
            }
        }else {
            return Status.fail(-10,"商品已经被抢光啦");
        }
    }

    public Status selectExchange(Exchange exchange, String userid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("exchangeId", String.valueOf(exchange.getExchangeId()));
        map.put("userId", userid);
        ExchangeInfo exchangeInfo = exchangeMapper.selectExchange(map);
        return Status.success().add("exchange",exchangeInfo);
    }
}
