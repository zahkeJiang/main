package com.bjpygh.gzh.service.quartz;

import com.bjpygh.gzh.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TestJob1 {

    @Autowired
    IntegralService integralService;

    public void execute() {
        try {
            //自然月一号：清除连续签到标识
            if (new Date().getDate() == 1) {
                integralService.updateDateOne();
            }

            System.out.println("updateAllUnSigned");
            //所有昨日没签到的用户，将连续签到标识清零
            integralService.updateAllUnSigned();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
