package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.CoinRecord;
import com.bjpygh.gzh.bean.Integral;
import com.bjpygh.gzh.bean.UserCoin;
import com.bjpygh.gzh.dao.CoinRecordMapper;
import com.bjpygh.gzh.dao.IntegralMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                int generalCoin;
                //添加记录
                CoinRecord coinRecord = new CoinRecord();
                coinRecord.setUserId(Long.valueOf(userid));

                if (integral.getContinuous() == 6){  //判断是否第七天签到
                    generalCoin = integral.getSignContinuous() + 1 + 5;  //设置今日签到奖励数额
                    //第七天额外奖励5银币
                    integral.setGeneralCoin(integral.getGeneralCoin() + generalCoin); //更新银币的数额
                    integral.setContinuous(integral.getContinuous()+1);    //更新连续签到天数
                    integral.setSignContinuous(integral.getSignContinuous()+1);    //更新连续签到天数

                    //添加记录
                    coinRecord.setGeneralValue(5);
                    coinRecord.setNote("连续7天签到奖励");
                    coinRecordMapper.insertCoinRecord(coinRecord);
                }else if(integral.getContinuous() == 2) {
                    generalCoin = integral.getSignContinuous() + 1 + 2;  //设置今日签到奖励数额
                    //第三天额外奖励2银币
                    integral.setGeneralCoin(integral.getGeneralCoin() + generalCoin); //更新银币的数额
                    integral.setContinuous(integral.getContinuous()+1);    //更新连续签到天数
                    integral.setSignContinuous(integral.getSignContinuous()+1);    //更新连续签到天数

                    //添加记录
                    coinRecord.setGeneralValue(2);
                    coinRecord.setNote("连续3天签到奖励");
                    coinRecordMapper.insertCoinRecord(coinRecord);
                }else if (integral.getContinuous() == 14){  //判断是否第十五天签到
                    generalCoin = integral.getSignContinuous() + 1 + 10;  //设置今日签到奖励数额
                    //第七天额外奖励10银币
                    integral.setGeneralCoin(integral.getGeneralCoin() + generalCoin); //更新银币的数额
                    integral.setContinuous(integral.getContinuous()+1);    //更新连续签到天数
                    integral.setSignContinuous(integral.getSignContinuous()+1);    //更新连续签到天数

                    //添加记录
                    coinRecord.setGeneralValue(10);
                    coinRecord.setNote("连续15天签到奖励");
                    coinRecordMapper.insertCoinRecord(coinRecord);
                }else {     //其他非特殊情况，自然签到
                    generalCoin = integral.getSignContinuous() + 1;  //设置今日签到奖励数额
                    integral.setGeneralCoin(integral.getGeneralCoin() + generalCoin); //更新银币的数额
                    integral.setContinuous(integral.getContinuous()+1);    //更新连续签到天数
                    integral.setSignContinuous(integral.getSignContinuous()+1);    //更新连续签到天数
                }
                // 更新累计签到信息
                integral.setSignTimes(integral.getSignTimes() + 1);
                integral.setContinuousTimes(integral.getContinuousTimes() + 1);
                // 设置签到完成
                integral.setIsSign(1);
                // 更新数据
                integralMapper.updateUserSign(integral);
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("generalCoin", generalCoin);
                map.put("continuous",integral.getContinuous());
                //添加记录
                coinRecord.setGeneralValue(integral.getSignContinuous());
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

    public void addOneCoin(String userId) {
        Integral integral = integralMapper.selectIntegral(Long.valueOf(userId));
        integral.setCoin(integral.getCoin() + 1);
        integralMapper.updateInegral(integral);


        //添加获取金币记录
        CoinRecord coinRecord = new CoinRecord();
        coinRecord.setCoinValue(1);
        coinRecord.setUserId(Long.valueOf(userId));
        coinRecord.setNote("推荐新用户关注成功");

        coinRecordMapper.insertCoinRecord(coinRecord);
    }

    public void addTenCoin(Long userId) {
        Integral integral = integralMapper.selectIntegral(Long.valueOf(userId));
        integral.setCoin(integral.getCoin() + 10);
        integralMapper.updateInegral(integral);

        //添加获取金币记录
        CoinRecord coinRecord = new CoinRecord();
        coinRecord.setCoinValue(10);
        coinRecord.setUserId(Long.valueOf(userId));
        coinRecord.setNote("推荐关注已达连续30天");

        coinRecordMapper.insertCoinRecord(coinRecord);
    }

    public void addFiveCoin(Long userId) {
        Integral integral = integralMapper.selectIntegral(Long.valueOf(userId));
        integral.setCoin(integral.getCoin() + 5);
        integralMapper.updateInegral(integral);

        //添加获取金币记录
        CoinRecord coinRecord = new CoinRecord();
        coinRecord.setCoinValue(5);
        coinRecord.setUserId(Long.valueOf(userId));
        coinRecord.setNote("推荐关注已达连续15天");

        coinRecordMapper.insertCoinRecord(coinRecord);
    }

    public void addTwoCoin(Long userId) {
        Integral integral = integralMapper.selectIntegral(Long.valueOf(userId));
        integral.setCoin(integral.getCoin() + 2);
        integralMapper.updateInegral(integral);

        //添加获取金币记录
        CoinRecord coinRecord = new CoinRecord();
        coinRecord.setCoinValue(2);
        coinRecord.setUserId(Long.valueOf(userId));
        coinRecord.setNote("推荐关注已达连续7天");

        coinRecordMapper.insertCoinRecord(coinRecord);
    }

    public void updateContinuous() {
        integralMapper.updateContinuous();
    }

    public void updateDateOne() {
        integralMapper.updateAllSignContinuous();
    }
}
