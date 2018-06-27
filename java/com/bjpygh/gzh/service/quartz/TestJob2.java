package com.bjpygh.gzh.service.quartz;

import com.bjpygh.gzh.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;

public class TestJob2 {

    @Autowired
    IntegralService integralService;

    public void execute() {
        System.out.println("updateAllSigned");
        //刷新所有签到用户
        integralService.updateAllSigned();

        System.out.println("updateContinuous");
        //将连续签到30天的标识清零
        integralService.updateContinuous();

    }
}
