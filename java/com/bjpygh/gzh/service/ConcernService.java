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
        ConcernExample example = new ConcernExample();
        ConcernExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(map.get("EventKey")));
        criteria.andOpenidEqualTo(map.get("FromUserName"));
        criteria.andCreateTimeEqualTo(formatter.format(new Date()));
        concernMapper.insert(example);
    }

    public void deleteConcern(Map<String, String> map) {
        ConcernExample example = new ConcernExample();
        ConcernExample.Criteria criteria = example.createCriteria();
        criteria.andOpenidEqualTo(map.get("FromUserName"));
        int i = concernMapper.deleteByExample(example);
    }

    public List<Concern> selectOpenid(Map<String, String> map) {
        ConcernExample example = new ConcernExample();
        ConcernExample.Criteria criteria = example.createCriteria();
        criteria.andOpenidEqualTo(map.get("FromUserName"));
        criteria.andConcernedEqualTo(false);
        return concernMapper.selectByExample(example);
    }

    public List<Concern> getConcerned() {
        return concernMapper.selectConcerned();
    }

    public void updateConcerned(Concern c) {
        concernMapper.updateByPrimaryKey(c);
    }
}
