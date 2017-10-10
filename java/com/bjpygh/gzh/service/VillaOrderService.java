package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.bean.VillaOrderExample;
import com.bjpygh.gzh.bean.VillaPrice;
import com.bjpygh.gzh.dao.UserMapper;
import com.bjpygh.gzh.dao.VillaOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VillaOrderService {

    @Autowired
    VillaOrderMapper villaOrderMapper;

    @Autowired
    UserMapper userMapper;

    public String createVillaOrder(VillaOrder villaOrder) {
        /**
         * 计算订单价格
         */
        int villaPrice = 0;
        int villaNum = villaOrder.getVillaName().split(",").length;
        String[] dates = villaOrder.getDate().split(",");
        for (String date : dates){
            String[] ds = date.split("-");
            Date d = new Date(Integer.parseInt(ds[0]),Integer.parseInt(ds[1]),Integer.parseInt(ds[2]));
            if (d.getDay()>1){
                villaPrice +=66;
            }else {
                villaPrice +=100;
            }
        }
        villaPrice *= villaNum;
        villaPrice *= villaOrder.getPeopleNumber();
        villaOrder.setVillaPrice(villaPrice);

        //创建时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        villaOrder.setCreateTime(formatter.format(new Date()));

        villaOrder.setOriginalPrice(villaPrice);
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
        String orderNumber ="VPYGH" + year + month + date + hour + minute + second + villaOrder.getUserId();

        villaOrder.setOrderNumber(orderNumber);
        //设置状态
        villaOrder.setOrderStatus(0);
        
        villaOrderMapper.insertSelective(villaOrder);
        return orderNumber;
    }

    public void updateOrderStatus(String ordernumber) {
        VillaOrderExample example = new VillaOrderExample();
        VillaOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNumberEqualTo(ordernumber);
        criteria.andOrderStatusEqualTo(6);
        villaOrderMapper.updateByExample(example);
    }

    public List<VillaOrder> getVillaOrder(String userid) {
        VillaOrderExample example = new VillaOrderExample();
        VillaOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userid));
        List<VillaOrder> villaOrders = villaOrderMapper.selectByExample(example);

        return villaOrders;
    }

    public List<VillaPrice> getPriceList(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //拿到年份和月份
        int year = date.getYear();
        int month = date.getMonth();

        //将date转换为calendar，并获取这月最后一天
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH,0);
        String last = format.format(cal.getTime());
        //生成价格列表作为返回数据
        List<VillaPrice> villaPrices = new ArrayList<VillaPrice>();
        for (int i=0;i<Integer.parseInt(last.split("-")[2]);i++){
            Date newDate = new Date(year,month,i+1);

            VillaPrice villaPrice = new VillaPrice();
            villaPrice.setDate(i+1+"");

            //从数据库获取本日的报名数
            VillaOrderExample example = new VillaOrderExample();
            VillaOrderExample.Criteria criteria = example.createCriteria();
            String thisDate = format.format(newDate);
            criteria.andDateLike(thisDate);
            List<VillaOrder> villaOrders = villaOrderMapper.selectByExample(example);
            if (villaOrders.size()>0){
                villaPrice.setNum(villaOrders.size()+"");
            }else{
                //根据周几来设置价格
                if (newDate.getDay()>1){
                    villaPrice.setPrice("66");
                }else {
                    villaPrice.setPrice("100");
                }
            }
            villaPrices.add(villaPrice);
        }
        return villaPrices;
    }

    public List<VillaOrder> getVillaOrderByNumber(String ordernumber) {
        VillaOrderExample example = new VillaOrderExample();
        VillaOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNumberEqualTo(ordernumber);

        return villaOrderMapper.selectByExample(example);
    }

    public void ChangeVillaStatusByNumber(String out_trade_no) {
        VillaOrderExample example = new VillaOrderExample();
        VillaOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNumberEqualTo(out_trade_no);
        criteria.andOrderStatusEqualTo(1);
        villaOrderMapper.updateByExample(example);
    }

    public void updateOrder(VillaOrder villaOrder) {
        villaOrderMapper.updateByPrimaryKey(villaOrder);
    }
}
