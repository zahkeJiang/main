package com.bjpygh.gzh.service.quartz;

import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.dao.IntegralMapper;
import com.bjpygh.gzh.service.*;
import com.bjpygh.gzh.utils.OrderPush;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJob1 {

    @Autowired
    IntegralService integralService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

    public void execute(){
        try{
            //自然月一号：清除连续签到标识
            if (new Date().getDay() == 1){
                integralService.updateDateOne();
            }

            //所有昨日没签到的用户，将连续签到标识清零
            integralService.updateAllUnSigned();

            //刷新所有签到用户
            integralService.updateAllSigned();

            //将连续签到30天的标识清零
            integralService.updateContinuous();

        }catch(Exception ex){
//            ex.printStackTrace();
        }
    }
}
