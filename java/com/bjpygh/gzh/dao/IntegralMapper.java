package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.Integral;
import com.bjpygh.gzh.bean.UserCoin;

public interface IntegralMapper {
    UserCoin selectUserCoin(Long userId);

    void updateUserSign(Integral integral);

    void insertUserCoin(Long userId);

    Integral selectIntegral(Long userId);

    void updateAllSigned();

    void updateAllUnSigned();

    void inCoinRecord(String userid);

    void updateInegral(Integral integral);
}
