package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.ArmyOrder;
import com.bjpygh.gzh.bean.ArmyOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArmyOrderMapper {
    long countByExample(ArmyOrderExample example);

    int deleteByExample(ArmyOrderExample example);

    int deleteByPrimaryKey(Integer armyId);

    int insert(ArmyOrder record);

    int insertSelective(ArmyOrder record);

    List<ArmyOrder> selectByExample(ArmyOrderExample example);

    ArmyOrder selectByPrimaryKey(Integer armyId);

    int updateByExampleSelective(@Param("record") ArmyOrder record, @Param("example") ArmyOrderExample example);

    int updateByExample(@Param("example") ArmyOrderExample example);

    int updateByPrimaryKeySelective(ArmyOrder record);

    int updateByPrimaryKey(ArmyOrder record);
}