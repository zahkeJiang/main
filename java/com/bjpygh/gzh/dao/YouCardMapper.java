package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.YouCard;
import com.bjpygh.gzh.bean.YouCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YouCardMapper {
    long countByExample(YouCardExample example);

    int deleteByExample(YouCardExample example);

    int deleteByPrimaryKey(Integer cardId);

    int insert(YouCard record);

    int insertSelective(YouCard record);

    List<YouCard> selectByExample(YouCardExample example);

    YouCard selectByPrimaryKey(Integer cardId);

    int updateByExampleSelective(@Param("record") YouCard record, @Param("example") YouCardExample example);

    int updateByExample(@Param("record") YouCard record, @Param("example") YouCardExample example);

    int updateByPrimaryKeySelective(YouCard record);

    int updateByPrimaryKey(YouCard record);
}