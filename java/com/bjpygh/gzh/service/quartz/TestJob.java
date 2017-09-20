package com.bjpygh.gzh.service.quartz;

import java.util.Date;

public class TestJob {
    Date date = new Date(2419200000L);
    public void execute(){
        try{
            //.......
            System.out.println("----.----");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
