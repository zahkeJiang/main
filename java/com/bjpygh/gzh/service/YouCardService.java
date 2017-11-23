package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.YouCard;
import com.bjpygh.gzh.bean.YouCardExample;
import com.bjpygh.gzh.dao.YouCardMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class YouCardService {

    @Autowired
    YouCardMapper youCardMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Status applyCard(String userid, YouCard youCard) {
        YouCardExample example = new YouCardExample();
        YouCardExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userid));
        List<YouCard> youCards = youCardMapper.selectByExample(example);
        if (youCards.size()<1){
            youCard.setUserId(Long.valueOf(userid));
            youCard.setCreateTime(formatter.format(new Date()));
            youCard.setCardStatus((byte) 0);
            youCardMapper.insert(youCard);
            return Status.success();
        }else {
            return Status.fail(-20,"您已提交申请，勿重复提交");
        }
    }

    public Status isApply(String userid) {
        YouCardExample example = new YouCardExample();
        YouCardExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userid));
        List<YouCard> youCards = youCardMapper.selectByExample(example);
        if (youCards.size()<1){
            return Status.success();
        }else{
            return Status.fail(-20,"您已提交申请");
        }

    }
}
