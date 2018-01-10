package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.bean.UserCoin;
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

    void bondRealName(Map<String, String> map);

    void changeUserPoints(Map<String, String> uMap);

    UserCoin getUserCoin(Integer integer);

    void updateUserSign(Long userid);

    void updateAllSign();

    String getUserId(String openid);

    void updateUserCoin(User user);
}