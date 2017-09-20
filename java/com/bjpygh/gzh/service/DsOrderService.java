package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.bean.DsOrderExample;
import com.bjpygh.gzh.dao.DsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DsOrderService {

    @Autowired
    DsOrderMapper dsOrderMapper;

    public void ChageOrderStatus(String ordernumber) {
        Map<String, String> map = new HashMap();
        map.put("orderNumber",ordernumber);
        map.put("orderStatus","4");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("getTime",formatter.format(new Date()));
        dsOrderMapper.changeStatus(map);
    }



    public List<DsOrder> getOrdersById(String userid) {
        DsOrderExample example = new DsOrderExample();
        DsOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.parseLong(userid));
        return dsOrderMapper.selectByExample(example);
    }

    public void insertOrder(DsOrder dsOrder) {
        dsOrderMapper.insertSelective(dsOrder);
    }

    public void updateOrderByStatus(DsOrder dsOrder) {
        dsOrderMapper.updateOrderByStatus(dsOrder);
    }

    public List<DsOrder> getDsOrderByNumber(String out_trade_no) {
        DsOrderExample example = new DsOrderExample();
        DsOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNumberEqualTo(out_trade_no);
        return dsOrderMapper.selectByExample(example);
    }

    public void updateOrder(DsOrder dsOrder) {
        dsOrderMapper.updateOrder(dsOrder);
    }

    public void deleteOrderByNum(String ordernumber) {
        DsOrderExample example = new DsOrderExample();
        DsOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNumberEqualTo(ordernumber);
        dsOrderMapper.deleteByExample(example);
    }

    public List<DsOrder> getNotPay() {
        DsOrderExample example = new DsOrderExample();
        DsOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStatusEqualTo("0");
        return dsOrderMapper.selectByExample(example);
    }

    public void updateOrderStatus(String ordernumber) {
        Map<String, String> map = new HashMap();
        map.put("orderNumber",ordernumber);
        map.put("orderStatus","6");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("refundTime",formatter.format(new Date()));
        dsOrderMapper.updateOrderStatus(map);
    }
}
