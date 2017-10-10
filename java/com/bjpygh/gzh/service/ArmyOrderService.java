package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.ArmyOrder;
import com.bjpygh.gzh.bean.ArmyOrderExample;
import com.bjpygh.gzh.dao.ArmyOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ArmyOrderService {

    @Autowired
    ArmyOrderMapper armyOrderMapper;

    public String createArmyOrder(ArmyOrder armyOrder) {
        /**
         * 计算订单价格
         */
        int armyPrice = 0;
        String[] dates = armyOrder.getDate().split(",");
        for (String date : dates){
            String[] ds = date.split("-");
            Date d = new Date(Integer.parseInt(ds[0]),Integer.parseInt(ds[1]),Integer.parseInt(ds[2]));
            if (d.getDay()>1){
                armyPrice +=66;
            }else {
                armyPrice +=100;
            }
        }
        armyPrice *= armyOrder.getPeopleNumber();
        armyOrder.setArmyPrice(armyPrice);

        //创建时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        armyOrder.setCreateTime(formatter.format(new Date()));

        armyOrder.setOriginalPrice(armyPrice);
        /**
         * 生成订单
         */
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String orderNumber ="APYGH" + year + month + date + hour + minute + second + armyOrder.getUserId();

        armyOrder.setOrderNumber(orderNumber);
        //设置状态
        armyOrder.setOrderStatus(0);

        armyOrderMapper.insertSelective(armyOrder);
        return orderNumber;

    }

    public void updateOrderStatus(String ordernumber) {
        ArmyOrderExample example = new ArmyOrderExample();
        ArmyOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNumberEqualTo(ordernumber);
        criteria.andOrderStatusEqualTo(0);
        armyOrderMapper.updateByExample(example);
    }

    public List<ArmyOrder> getArmyOrder(String userid) {
        ArmyOrderExample example = new ArmyOrderExample();
        ArmyOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userid));
        return armyOrderMapper.selectByExample(example);
    }

    public List<ArmyOrder> getArmyOrderByNumber(String ordernumber) {
        ArmyOrderExample example = new ArmyOrderExample();
        ArmyOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNumberEqualTo(ordernumber);

        return armyOrderMapper.selectByExample(example);
    }

    public void ChangeArmyStatusByNumber(String out_trade_no) {
        ArmyOrderExample example = new ArmyOrderExample();
        ArmyOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNumberEqualTo(out_trade_no);
        criteria.andOrderStatusEqualTo(1);
        armyOrderMapper.selectByExample(example);
    }

    public void updateOrder(ArmyOrder armyOrder) {
        armyOrderMapper.updateByPrimaryKey(armyOrder);
    }
}
