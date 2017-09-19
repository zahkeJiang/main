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
        map.put("ordernumber",ordernumber);
        map.put("orderstatus","4");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("gettime",formatter.format(new Date()));
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
}
