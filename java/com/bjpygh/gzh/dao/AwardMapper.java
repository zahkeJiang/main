package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.Award;

import java.util.List;

public interface AwardMapper {
    List<Award> getAwards();

    Award getAward(Long awardId);

    void updateAwardAmount(Award award);
}
