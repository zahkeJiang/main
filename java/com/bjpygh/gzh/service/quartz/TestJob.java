package com.bjpygh.gzh.service.quartz;

import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.service.DsOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestJob {

    @Autowired
    DsOrderService dsOrderService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void execute(){
        try{
            //检测订单是否过期
            long time = new Date().getTime();
            List<DsOrder> orders = dsOrderService.getNotPay();
            if (orders.size()>0){
                for (DsOrder dso : orders){
                    Date d = formatter.parse(dso.getCreateTime());
                    if (time-d.getTime()>86400000L){
                        dsOrderService.updateOrderStatus(dso.getOrderNumber());
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
