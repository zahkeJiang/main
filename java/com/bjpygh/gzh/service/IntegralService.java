package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.CoinRecord;
import com.bjpygh.gzh.bean.Integral;
import com.bjpygh.gzh.bean.UserCoin;
import com.bjpygh.gzh.dao.CoinRecordMapper;
import com.bjpygh.gzh.dao.IntegralMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class IntegralService {

    @Autowired
    IntegralMapper integralMapper;

    @Autowired
    CoinRecordMapper coinRecordMapper;

    public Status selectUserCoin(String userid) {
        UserCoin userCoin = integralMapper.selectUserCoin(Long.valueOf(userid));
        if (userCoin == null){
            integralMapper.insertUserCoin(Long.valueOf(userid));
            userCoin = integralMapper.selectUserCoin(Long.valueOf(userid));
        }
        return Status.success().add("userCoin",
                userCoin);
    }

    public Status updateUserSign(String userid) {
        Integral integral = integralMapper.selectIntegral(Long.valueOf(userid));
        if (integral.getIsSign() == 0){
            Double generalCoin = integral.getGeneralCoin();
            if (integral.getContinuous() >= 15){
                integral.setGeneralCoin(integral.getGeneralCoin()+15);
                generalCoin = Double.valueOf(15);
            }else {
                integral.setGeneralCoin(integral.getGeneralCoin()+integral.getContinuous()+1);
                generalCoin = Double.valueOf(integral.getContinuous()+1);
            }
            integral.setIsSign(1);
            integral.setContinuous(integral.getContinuous()+1);
            integralMapper.updateUserSign(integral);
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("generalCoin", generalCoin.intValue());
            map.put("continuous",integral.getContinuous());
            //添加记录
            CoinRecord coinRecord = new CoinRecord();
            coinRecord.setUserId(Long.valueOf(userid));
            coinRecord.setGeneralValue(generalCoin.intValue());
            coinRecord.setNote("签到奖励");
            coinRecordMapper.insertCoinRecord(coinRecord);
            return Status.success().add("map",map);
        }else{
         return Status.fail(-10,"已完成签到");
        }
    }

    public void updateAllSigned() {
        integralMapper.updateAllSigned();
    }

    public void updateAllUnSigned() {
        integralMapper.updateAllUnSigned();
    }
}
