package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.bean.VillaOrderExample;
import com.bjpygh.gzh.bean.VillaPrice;
import com.bjpygh.gzh.dao.UserMapper;
import com.bjpygh.gzh.dao.VillaOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VillaOrderService {

    @Autowired
    VillaOrderMapper villaOrderMapper;

    @Autowired
    UserMapper userMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

    public String createVillaOrder(VillaOrder villaOrder) throws ParseException {
        /**
         * 计算订单价格
         */
        int sum=0;
        int villaNum = villaOrder.getVillaName().split(",").length;
        String[] dates = villaOrder.getDate().split(",");
        for (String date : dates){
            int villaPrice;
            Date d = formatter1.parse(date);
//            String[] ds = date.split("-");
//            Date d = new Date(Integer.parseInt(ds[0]),Integer.parseInt(ds[1])-1,Integer.parseInt(ds[2]));
            System.out.println(d);
            if (d.getDay()>0&&d.getDay()<5){
                villaPrice = 66;
                if (villaOrder.getPeopleNumber()>28*villaNum)
                    villaPrice *= villaOrder.getPeopleNumber();
                else
                    villaPrice = 1888*villaNum;
                sum += villaPrice;
            }else {
                villaPrice = 100;
                if (villaOrder.getPeopleNumber()>28*villaNum)
                    villaPrice *= villaOrder.getPeopleNumber();
                else
                    villaPrice = 2888*villaNum;
                sum += villaPrice;
            }
        }

        villaOrder.setVillaPrice(sum/2);

        //创建时间
        villaOrder.setCreateTime(formatter.format(new Date()));

        villaOrder.setOriginalPrice(sum);
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
        villaOrder.setNote("豪华别墅套餐");
        
        villaOrderMapper.insertSelective(villaOrder);
        return orderNumber;
    }

    public void updateOrderStatus(String ordernumber) {
        Map<String, String> map = new HashMap();
        map.put("orderNumber",ordernumber);
        map.put("orderStatus","6");
        villaOrderMapper.updateOrderStatus(map);
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
            String thisDate = format.format(newDate);
            List<VillaOrder> villaOrders = villaOrderMapper.getVillaOrderByDate(thisDate);
            if (villaOrders.size()>0){
                int num = 0;
                for (VillaOrder o : villaOrders){
                    num = o.getVillaName().split(",").length+num;
                }
                villaPrice.setNum(3-num+"");
            }else{
                //根据周几来设置价格
                if (newDate.getDay()>0&&newDate.getDay()<5){
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
        criteria.andPayTimeEqualTo(formatter.format(new Date()));
        criteria.andPayTypeEqualTo((byte) 0);
        villaOrderMapper.updateByExample(example);
    }

    public void updateOrder(VillaOrder villaOrder) {
        villaOrderMapper.updateByPrimaryKey(villaOrder);
    }

    public String getVillaPay(String[] date) throws ParseException {
        for (int i=0;i<date.length;i++){
            List<VillaOrder> villaOrders = villaOrderMapper.getVillaOrderByDate(date[i]);
            String name = "";
            for (VillaOrder order : villaOrders){
                if (name!=""){
                    name= name + ","+order.getVillaName();
                }else {
                    name= order.getVillaName();
                }
            }
           return name;
        }
        return "";
    }

    public boolean checkOrder(VillaOrder villaOrder) {
        String[] name = villaOrder.getVillaName().split(",");
        String[] date = villaOrder.getDate().split(",");
        for (String d : date){
            List<VillaOrder> villaOrders = villaOrderMapper.getVillaOrderByDate(d);
            for (int i=0;i<villaOrders.size();i++){
                String[] payedName = villaOrders.get(i).getVillaName().split(",");
                for (String p : payedName){
                    for (String n : name){
                        if (n.equals(p)){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public List<VillaOrder> getPay() {
        VillaOrderExample example = new VillaOrderExample();
        VillaOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStatusEqualTo(1);
        return villaOrderMapper.selectByExample(example);
    }
}
