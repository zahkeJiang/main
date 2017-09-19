package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.IntegralRecord;
import com.bjpygh.gzh.bean.IntegralRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IntegralRecordMapper {
    long countByExample(IntegralRecordExample example);

    int deleteByExample(IntegralRecordExample example);

    int deleteByPrimaryKey(Integer recordId);

    int insert(IntegralRecord record);

    int insertSelective(IntegralRecord record);

    List<IntegralRecord> selectByExample(IntegralRecordExample example);

    IntegralRecord selectByPrimaryKey(Integer recordId);

    int updateByExampleSelective(@Param("record") IntegralRecord record, @Param("example") IntegralRecordExample example);

    int updateByExample(@Param("record") IntegralRecord record, @Param("example") IntegralRecordExample example);

    int updateByPrimaryKeySelective(IntegralRecord record);

    int updateByPrimaryKey(IntegralRecord record);
}