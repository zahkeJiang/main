package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.AttentionExample;
import com.bjpygh.gzh.dao.AttentionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AttentionService {

    @Autowired
    AttentionMapper attentionMapper;

    public void insertAtentionService(Map<String, String> map) {
        AttentionExample example = new AttentionExample();
        AttentionExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(map.get("EventKey")));
        criteria.andOpenIdEqualTo(map.get("FromUserName"));
        attentionMapper.insert(example);
    }
}
