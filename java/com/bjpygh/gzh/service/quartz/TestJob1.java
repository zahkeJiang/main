package com.bjpygh.gzh.service.quartz;

import com.bjpygh.gzh.bean.*;
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
    UserService userService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

    public void execute(){
        try{

            userService.updateAllSign();

        }catch(Exception ex){
//            ex.printStackTrace();
        }
    }
}
