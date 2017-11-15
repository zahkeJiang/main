package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.ArmyOrder;
import com.bjpygh.gzh.bean.ArmyOrderExample;
import com.bjpygh.gzh.bean.VillaPrice;
import com.bjpygh.gzh.dao.ArmyOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ArmyOrderService {

    @Autowired
    ArmyOrderMapper armyOrderMapper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

    public String createArmyOrder(ArmyOrder armyOrder) throws ParseException {
        /**
         * 计算订单价格
         */
        int sum=0;
        int armyPrice = 0;
        String[] dates = armyOrder.getDate().split(",");
        for (String date : dates){
            Date d = formatter1.parse(date);
//            String[] ds = date.split("-");
//            Date d = new Date(Integer.parseInt(ds[0]),Integer.parseInt(ds[1]),Integer.parseInt(ds[2]));
            int roomNumber = armyOrder.getRoomNumber();
            int noRoomNumber = armyOrder.getNoroomNumber();
            armyPrice = roomNumber*160+noRoomNumber*120+(armyOrder.getPeopleNumber()-roomNumber-noRoomNumber)*140;
            sum += armyPrice;
        }
        int insurance = 0;
        if (armyOrder.getInsurance()>0){
            insurance = 15*armyOrder.getInsurance()*dates.length;
        }
        int barbecue = 0;
        if (armyOrder.getBarbecue()==1){
            barbecue = 10*armyOrder.getPeopleNumber()*dates.length;
        }

        if (armyOrder.getFullAmount()==1)
            armyOrder.setArmyPrice(sum+insurance+barbecue);
        else
            armyOrder.setArmyPrice(sum/2+insurance+barbecue);

        //创建时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        armyOrder.setCreateTime(formatter.format(new Date()));

        armyOrder.setOriginalPrice(armyPrice);
        /**
         * 生成订单号
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
        armyOrder.setArmyName("作战之日");
        armyOrder.setImageurl("http://120.24.184.86/glxt/dsimage/army_order.jpg");
        //设置状态
        armyOrder.setOrderStatus(0);

        armyOrderMapper.insertSelective(armyOrder);
        return orderNumber;

    }

    public void updateOrderStatus(String ordernumber) {
        Map<String, String> map = new HashMap();
        map.put("orderNumber",ordernumber);
        map.put("orderStatus","6");
        armyOrderMapper.updateOrderStatus(map);
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
        criteria.andPayTimeEqualTo(formatter.format(new Date()));
        criteria.andPayTypeEqualTo((byte) 0);
        armyOrderMapper.selectByExample(example);
    }

    public void updateOrder(ArmyOrder armyOrder) {
        armyOrderMapper.updateByPrimaryKey(armyOrder);
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
            villaPrice.setPrice("120");
            villaPrices.add(villaPrice);
        }
        return villaPrices;
    }

    public List<ArmyOrder> getOrdersById(String userid) {
        ArmyOrderExample example = new ArmyOrderExample();
        ArmyOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Long.parseLong(userid));
        return armyOrderMapper.selectByExample(example);
    }

    public List<ArmyOrder> getNotPay() {
        ArmyOrderExample example = new ArmyOrderExample();
        ArmyOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStatusEqualTo(0);
        return armyOrderMapper.selectByExample(example);
    }

    public List<ArmyOrder> getPay() {
        ArmyOrderExample example = new ArmyOrderExample();
        ArmyOrderExample.Criteria criteria = example.createCriteria();
        criteria.andOrderStatusEqualTo(1);
        return armyOrderMapper.selectByExample(example);
    }
}
