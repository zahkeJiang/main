package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.Concern;
import com.bjpygh.gzh.bean.ConcernExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConcernMapper {
    long countByExample(ConcernExample example);

    int deleteByExample(ConcernExample example);

    int deleteByPrimaryKey(Long concernId);

    int insert(ConcernExample record);

    int insertSelective(Concern record);

    List<Concern> selectByExample(ConcernExample example);

    Concern selectByPrimaryKey(Long concernId);

    int updateByExampleSelective(@Param("record") Concern record, @Param("example") ConcernExample example);

    int updateByExample(@Param("example") ConcernExample example);

    int updateByPrimaryKeySelective(Concern record);

    int updateByPrimaryKey(Concern record);

    List<Concern> selectConcerned();
}