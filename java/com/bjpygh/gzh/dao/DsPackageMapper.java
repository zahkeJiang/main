package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.DsPackage;
import com.bjpygh.gzh.bean.DsPackageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsPackageMapper {
    long countByExample(DsPackageExample example);

    int deleteByExample(DsPackageExample example);

    int deleteByPrimaryKey(Integer packageid);

    int insert(DsPackage record);

    int insertSelective(DsPackage record);

    List<DsPackage> selectByExample(DsPackageExample example);

    DsPackage selectByPrimaryKey(Integer packageid);

    int updateByExampleSelective(@Param("record") DsPackage record, @Param("example") DsPackageExample example);

    int updateByExample(@Param("record") DsPackage record, @Param("example") DsPackageExample example);

    int updateByPrimaryKeySelective(DsPackage record);

    int updateByPrimaryKey(DsPackage record);
}