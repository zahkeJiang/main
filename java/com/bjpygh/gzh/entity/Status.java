package com.bjpygh.gzh.entity;

import java.util.HashMap;
import java.util.Map;

public class Status {

    //状态码
	private int status;

	private String msg;

    //用户要返回给浏览器的数据
    private Map<String, Object> data = new HashMap<String, Object>();


    public static Status success(){
        Status result = new Status();
        result.setStatus(0);
        result.setMsg("处理成功");
        return result;
    }

    public static Status fail(int status,String msg){
        Status result = new Status();
        result.setStatus(status);
        result.setMsg(msg);
        return result;
    }

    public static Status notInWx(){
        Status result = new Status();
        result.setStatus(-10);
        result.setMsg("请在微信端登录");
        return result;
    }

    public Status add(String key,Object value){
        this.getData().put(key,value);
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
