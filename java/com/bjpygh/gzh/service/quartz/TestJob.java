package com.bjpygh.gzh.service.quartz;

import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.service.DsOrderService;
import com.bjpygh.gzh.service.VillaOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestJob {

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    VillaOrderService villaOrderService;

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

            //检测订单是否已完成
            List<VillaOrder> pay = villaOrderService.getPay();
            for (VillaOrder v : pay){
                Date d2 = formatter.parse(v.getDate().split(",")[0]);
                if (time>d2.getTime()){
                    v.setOrderStatus(4);
                    villaOrderService.updateOrder(v);
                }
            }

            //检测已收到材料订单
            List<DsOrder> dsFinish = dsOrderService.getFinish();
            if (dsFinish.size()>0){
                for (DsOrder ds : dsFinish){
                    Date d = formatter.parse(ds.getGetTime());
                    if (time-d.getTime()>604800000L){
                        dsOrderService.updateOrderStatusFinish(ds.getOrderNumber());
                    }
                }
            }

        }catch(Exception ex){
//            ex.printStackTrace();
        }
    }
}
