package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.Award;
import com.bjpygh.gzh.bean.Exchange;
import com.bjpygh.gzh.bean.ExchangeInfo;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.dao.AwardMapper;
import com.bjpygh.gzh.dao.ExchangeMapper;
import com.bjpygh.gzh.dao.UserMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    ExchangeMapper exchangeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AwardMapper awardMapper;

    public List<ExchangeInfo> selectExchanges(String userid) {
        return exchangeMapper.selectExchanges(Long.valueOf(userid));
    }

    public Status insertExchanges(String userid, Exchange exchange) {
        Award award = awardMapper.getAward(Long.valueOf(exchange.getAwardId()));
        if (award.getAmount()>0){
            User user = userMapper.selectByPrimaryKey(Long.valueOf(userid));
            if (user.getGeneralCoin()>award.getGeneralCoin()){
                exchange.setStatus(0);
                exchangeMapper.insertExchange(exchange);
                user.setGeneralCoin(user.getGeneralCoin()-award.getGeneralCoin());
                userMapper.updateUserCoin(user);
                award.setAmount(award.getAmount()-1);
                awardMapper.updateAwardAmount(award);
            }
        }
        return null;
    }
}
