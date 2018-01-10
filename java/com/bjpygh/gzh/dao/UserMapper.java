package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.Address;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.bean.UserExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    String getUserId(String openid);

    void changeUserPoints(Map<String, String> uMap);

    void bondRealName(Map<String, String> map);

    Object getUserCoin(Integer integer);

    void updateUserSign(Long aLong);

    void updateAllSign();

    void setDefaultAddress(User user);

    Address selectDefaultAddress(Long userid);

    void updateUserCoin(User user);
}