package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.Address;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.bean.UserExample;
import com.bjpygh.gzh.dao.UserMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    //通过openid获取userid
    public String getUserIdByOpenid(String openid) {
        String userId = userMapper.getUserId(openid);
        return userId;
    }

    //插入用户信息
    public void InsertUserFromWx(User user) {
        userMapper.insertSelective(user);
    }

    //更新用户信息
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    //获取用户个人资料
    public User getUserById(String userid) {
        Long id = Long.parseLong(userid);
        return userMapper.selectByPrimaryKey(id);
    }

    //检测手机是否绑定
    public boolean checkBond(String phonenumber) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneNumberEqualTo(phonenumber);
        List<User> users = userMapper.selectByExample(example);
        if (users.size()>0){
            return false;
        }else{
            return true;
        }
    }

    public List<User> getUserByOpenid(String openid) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andOpenidEqualTo(openid);
        return userMapper.selectByExample(example);
    }

    public void changeUserPoints(Map<String, String> uMap) {
        userMapper.changeUserPoints(uMap);
    }

    public void bondRealName(Map<String, String> map) {
        userMapper.bondRealName(map);
    }

    public boolean isBondUser(String userid) {
        User user = userMapper.selectByPrimaryKey(Long.parseLong(userid));
        if (user.getPhoneNumber()!=null){
            return true;
        }else {
            return false;
        }
    }

    public Status getUserCoin(String userid) {
        return Status.success()
                .add("userCoin",userMapper.getUserCoin(Integer.valueOf(userid)));
    }

    public Status updateUserSign(String userid) {
        userMapper.updateUserSign(Long.valueOf(userid));
        return Status.success();
    }

    public void updateAllSign() {
        userMapper.updateAllSign();
    }

    public Status setDefaultAddress(String userid, String addressId) {
        User user = new User();
        user.setUserId(Long.valueOf(userid));
        user.setAddressId(Long.valueOf(addressId));
        userMapper.setDefaultAddress(user);
        return Status.success();
    }

    public Status selectDefaultAddress(String userid) {
        Address address = userMapper.selectDefaultAddress(Long.valueOf(userid));
        return Status.success().add("address",address);
    }
}
