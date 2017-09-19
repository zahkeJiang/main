package com.bjpygh.gzh.utils;

import java.util.HashMap;
import java.util.Map;

public class Lottery {

	double s;
	int price = 100;
	int num;
	public Map<String, Integer> getPrice(){
		s = Math.random();
//			if(s<=0.95){
//			 price = 200;
//		 }else if(s<=0.98){
//			 price = 300;
//		 }else if(s<=0.99){
//			 price = 400;
//		 }else if(s<=0.995){
//			 price = 500;
//		 }else if(s<=0.9994){
//			 price = 600;
//		 }else if(s<=0.9997){
//			 price = 700;
//		 }else if(s<=0.9999){
//			 price = 800;
//		 }else if(s<=1){
//			 price = 1000;
//		 }
		if(s<=0.25){
			 price = 200;
			 num=0;
		 }else if(s<=0.5){
			 price = 200;
			 num=4;
		 }else if(s<=0.75){
			 price = 200;
			 num=2;
		 }else if(s<=0.95){ //200  概率为0.95
			 price = 200;
			 num=6;
		 }else if(s<=0.98){ //300  概率为0.03
			 price = 300;
			 num=1;
		 }else if(s<=0.995){ //500  概率为0.015
			 price = 500;
			 num=3;
		 }else if(s<=0.9994){ //600  概率为0.0048
			 price = 600;
			 num=7;
		 }else if(s<=1){ 	  //1000  概率为0.0002
			 price = 1000;
			 num=5;
		 }
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("price", price);
		map.put("num", num);
		 return map;
	}
	
}
