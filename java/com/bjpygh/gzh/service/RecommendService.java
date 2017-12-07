package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.Recommend;
import com.bjpygh.gzh.dao.RecommendMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendService {

    @Autowired
    RecommendMapper recommendMapper;

    public Status setRecommend(Recommend recommend) {

        Recommend recommend1 = recommendMapper.selectByPrimaryKey(recommend.getRecommend());
        if (recommend1 != null){
            recommendMapper.updateByPrimaryKey(recommend);
        }else {
            recommendMapper.insert(recommend);
        }

        return Status.success();
    }

    public Recommend getRecommend(String userid) {
        return recommendMapper.selectByPrimaryKey(Long.valueOf(userid));
    }
}
