package com.bjpygh.gzh.service.quartz;

import com.bjpygh.gzh.bean.ArmyOrder;
import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.bean.VillaOrder;
import com.bjpygh.gzh.service.ArmyOrderService;
import com.bjpygh.gzh.service.DsOrderService;
import com.bjpygh.gzh.service.UserService;
import com.bjpygh.gzh.service.VillaOrderService;
import com.bjpygh.gzh.utils.OrderPush;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJob {

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    VillaOrderService villaOrderService;

    @Autowired
    ArmyOrderService armyOrderService;

    @Autowired
    UserService userService;

    OrderPush orderPush = new OrderPush();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

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
            List<VillaOrder> vNotPays = villaOrderService.getNotPay();
            if (vNotPays.size()>0){
                for (VillaOrder v : vNotPays){
                    Date d = formatter.parse(v.getCreateTime());
                    if (time-d.getTime()>86400000L){
                        villaOrderService.updateOrderStatus(v.getOrderNumber());
                    }
                }
            }
            List<ArmyOrder> aNotPays = armyOrderService.getNotPay();
            if (aNotPays.size()>0){
                for (ArmyOrder a : aNotPays){
                    Date d = formatter.parse(a.getCreateTime());
                    if (time-d.getTime()>86400000L){
                        armyOrderService.updateOrderStatus(a.getOrderNumber());
                    }
                }
            }

            //检测订单是否已完成
            List<VillaOrder> pay = villaOrderService.getPay();
            for (VillaOrder v : pay){
                Date d2 = formatter1.parse(v.getDate().split(",")[0]);
                if (time>d2.getTime()){
                    v.setOrderStatus(4);
                    villaOrderService.updateOrder(v);
                    User userById = userService.getUserById(String.valueOf(v.getUserId()));
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("first","住的还舒服吗？满意的话，请帮小漂作出评价吧。");
                    map.put("keyword1",v.getOrderNumber());
                    map.put("keyword2","漂洋过海小别墅");
                    map.put("remark","期待和您下次一起玩儿啊~");
                    map.put("openid",userById.getOpenid());
                    orderPush.FinishJsonObj(map);
                }
            }
            List<ArmyOrder> aPays = armyOrderService.getPay();
            for (ArmyOrder a : aPays){
                Date d2 = formatter1.parse(a.getDate().split(",")[0]);
                if (time>d2.getTime()){
                    a.setOrderStatus(4);
                    armyOrderService.updateOrder(a);
                    User userById = userService.getUserById(String.valueOf(a.getUserId()));
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("first","玩的尽兴吗？满意的话，请帮小漂作出评价吧。");
                    map.put("keyword1",a.getOrderNumber());
                    map.put("keyword2","作战之日");
                    map.put("remark","期待和您再次并肩作战~");
                    map.put("openid",userById.getOpenid());
                    orderPush.FinishJsonObj(map);
                }
            }

            //检测已收到材料订单
            List<DsOrder> dsFinish = dsOrderService.getFinish();
            if (dsFinish.size()>0){
                for (DsOrder ds : dsFinish){
                    Date d = formatter.parse(ds.getGetTime());
                    if (time-d.getTime()>604800000L){
                        dsOrderService.updateOrderStatusFinish(ds.getOrderNumber());
                        User userById = userService.getUserById(String.valueOf(ds.getUserId()));
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("first","撒花！小漂已为您完成驾考报名。若您满意我们的服务，就请帮小漂作出评价吧。");
                        map.put("keyword1",ds.getOrderNumber());
                        map.put("keyword2","驾校报名");
                        map.put("remark","点击详情,您可以对订单进行评价。在后续的学习过程中，有问题也可以和小漂沟通哦。");
                        map.put("openid",userById.getOpenid());
                        orderPush.FinishJsonObj(map);
                    }
                }
            }

        }catch(Exception ex){
//            ex.printStackTrace();
        }
    }
}
