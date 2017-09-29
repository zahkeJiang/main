package com.bjpygh.gzh.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args){
//        Date date = new Date(2017,8,29);
//        System.out.println(date.getDay());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse("2017-09-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
//29 6 5
//28 5 4
//27 4 3
//26 3 2
//25 2 1
//24 1 7
//23 0 6