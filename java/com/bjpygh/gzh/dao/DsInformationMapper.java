package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.DsInformation;
import com.bjpygh.gzh.bean.DsInformationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsInformationMapper {
    long countByExample(DsInformationExample example);

    int deleteByExample(DsInformationExample example);

    int deleteByPrimaryKey(String dsName);

    int insert(DsInformation record);

    int insertSelective(DsInformation record);

    List<DsInformation> selectByExample(DsInformationExample example);

    DsInformation selectByPrimaryKey(String dsName);

    int updateByExampleSelective(@Param("record") DsInformation record, @Param("example") DsInformationExample example);

    int updateByExample(@Param("record") DsInformation record, @Param("example") DsInformationExample example);

    int updateByPrimaryKeySelective(DsInformation record);

    int updateByPrimaryKey(DsInformation record);
}