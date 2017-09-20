package com.bjpygh.gzh.dao;

import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.bean.DsOrderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DsOrderMapper {
    long countByExample(DsOrderExample example);

    int deleteByExample(DsOrderExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(DsOrder record);

    int insertSelective(DsOrder record);

    List<DsOrder> selectByExample(DsOrderExample example);

    DsOrder selectByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") DsOrder record, @Param("example") DsOrderExample example);

    int updateByExample(@Param("record") DsOrder record, @Param("example") DsOrderExample example);

    int updateByPrimaryKeySelective(DsOrder record);

    int updateByPrimaryKey(DsOrder record);

    void changeStatus(Map<String, String> map);

    void updateOrderByStatus(DsOrder dsorder);

    void updateOrder(DsOrder dsorder);

    void updateOrderStatus(Map<String, String> map);
}