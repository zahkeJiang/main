package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.Concern;
import com.bjpygh.gzh.bean.ConcernExample;
import com.bjpygh.gzh.dao.ConcernMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ConcernService {

    @Autowired
    ConcernMapper concernMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void insertConcern(Map<String, String> map) {
        Concern concern = new Concern();
        if (map.get("EventKey") == null){
            concern.setUserId(0L);
        }else {
            concern.setUserId(Long.valueOf(map.get("EventKey").split("_")[1]));
        }
        concern.setOpenid(map.get("FromUserName"));
        concern.setCreateTime(formatter.format(new Date()));
        concernMapper.insertConcern(concern);
    }

    public void deleteConcern(Map<String, String> map) {
        ConcernExample example = new ConcernExample();
        ConcernExample.Criteria criteria = example.createCriteria();
        criteria.andOpenidEqualTo(map.get("FromUserName"));
        criteria.andIsDeleteEqualTo(true);
        int i = concernMapper.updateByExample(example);
    }

    public List<Concern> selectOpenid(Map<String, String> map) {
        ConcernExample example = new ConcernExample();
        ConcernExample.Criteria criteria = example.createCriteria();
        criteria.andOpenidEqualTo(map.get("FromUserName"));
//        criteria.andConcernedEqualTo(false);
        return concernMapper.selectByExample(example);
    }


    public List<Concern> getConcernedThirty() {
        return concernMapper.getConcernedThirty();
    }

    public void updateConcerned(Concern c) {
        concernMapper.updateByPrimaryKey(c);
    }

    public void updateExist(Map<String, String> map) {
        ConcernExample example = new ConcernExample();
        ConcernExample.Criteria criteria = example.createCriteria();
        criteria.andOpenidEqualTo(map.get("FromUserName"));
        criteria.andUserIdEqualTo(Long.valueOf(map.get("EventKey").split("_")[1]));
        criteria.andIsDeleteEqualTo(false);
        int i = concernMapper.updateByExample(example);
    }

    public Concern selectConcernByOpenId(String openid) {
        Concern concern = concernMapper.selectConcernByOpenId(openid);
        return concern;
    }

    public void updateTimeAndCancel(String openid) {
        concernMapper.updateTimeAndCancel(openid);
    }

    public void cancelConcern(String openid) {
        concernMapper.cancelConcern(openid);
    }

    public List<Concern> getConcernedSeven() {
        return concernMapper.getConcernedSeven();
    }

    public List<Concern> getConcernedFifteen() {
        return concernMapper.getConcernedFifteen();
    }
}
