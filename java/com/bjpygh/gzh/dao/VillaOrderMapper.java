package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.bean.VillaOrderExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface VillaOrderMapper {
    long countByExample(VillaOrderExample example);

    int deleteByExample(VillaOrderExample example);

    int deleteByPrimaryKey(Integer villaId);

    int insert(VillaOrder record);

    int insertSelective(VillaOrder record);

    List<VillaOrder> selectByExample(VillaOrderExample example);

    VillaOrder selectByPrimaryKey(Integer villaId);

    int updateByExampleSelective(@Param("record") VillaOrder record, @Param("example") VillaOrderExample example);

    int updateByExample(@Param("example") VillaOrderExample example);

    int updateByPrimaryKeySelective(VillaOrder record);

    int updateByPrimaryKey(VillaOrder record);

    void updateOrderStatus(Map<String, String> map);

    List<VillaOrder> getVillaOrderByDate(String thisDate);
}