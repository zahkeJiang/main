package com.bjpygh.gzh.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 微信消息推送
 */
public class OrderPush {

    public String CreateJsonObj(Map<String, String> map){
        JSONObject first = new JSONObject();
        first.accumulate("value",map.get("first"));
        first.accumulate("color","#173177");

        JSONObject orderID = new JSONObject();
        orderID.accumulate("value",map.get("orderID"));
        orderID.accumulate("color","#173177");

        JSONObject orderMoneySum = new JSONObject();
        orderMoneySum.accumulate("value",map.get("orderMoneySum"));
        orderMoneySum.accumulate("color","#173177");

        JSONObject remark = new JSONObject();
        remark.accumulate("value",map.get("remark")+"\n 详情");
        remark.accumulate("color","#173177");
        JSONObject ja = new JSONObject();

        ja.accumulate("first",first);
        ja.accumulate("orderID",orderID);
        ja.accumulate("orderMoneySum",orderMoneySum);
        ja.accumulate("remark",remark);

        JSONObject obj = new JSONObject();
        obj.accumulate("touser",map.get("openid"));
        obj.accumulate("template_id","Oxsy4JnPMy3F2KvuEjKrS5awVXMK8zFir3pblGAaqow");
        obj.accumulate("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx74d8d40a83387a3e&redirect_uri=http://gzpt.bjpygh.com/schedule.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        obj.accumulate("data",ja);

        return sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=BjjtKkvXXzTQN44eU0Qm4nK7gmWxjYyKW3g54sJMIcL_6OcIS0NsNSK7BcZdW7rxcK3p_e-zDFRt9CoUcOJFm755ptHBB98P9UT9ZWTyz5sYDQhADALQD",
                obj.toString());
    }

    public String PayJsonObj(Map<String, String> map){
        JSONObject first = new JSONObject();
        first.accumulate("value",map.get("first"));
        first.accumulate("color","#173177");

        JSONObject keyword1 = new JSONObject();
        keyword1.accumulate("value",map.get("keyword1"));
        keyword1.accumulate("color","#173177");

        JSONObject keyword2 = new JSONObject();
        keyword2.accumulate("value",map.get("keyword2"));
        keyword2.accumulate("color","#173177");

        JSONObject remark = new JSONObject();
        remark.accumulate("value",map.get("remark"));
        remark.accumulate("color","#173177");
        JSONObject ja = new JSONObject();

        ja.accumulate("first",first);
        ja.accumulate("keyword1",keyword1);
        ja.accumulate("keyword2",keyword2);
        ja.accumulate("remark",remark);

        JSONObject obj = new JSONObject();
        obj.accumulate("touser",map.get("openid"));
        obj.accumulate("template_id","uPIYP31nOWg4WIaxwCwQE3aPXTHLNL5yD_YgkKer-gY");
//        obj.accumulate("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx74d8d40a83387a3e&redirect_uri=http://gzpt.bjpygh.com/schedule.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        obj.accumulate("data",ja);

        return sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=BjjtKkvXXzTQN44eU0Qm4nK7gmWxjYyKW3g54sJMIcL_6OcIS0NsNSK7BcZdW7rxcK3p_e-zDFRt9CoUcOJFm755ptHBB98P9UT9ZWTyz5sYDQhADALQD",
                obj.toString());
    }

    public String sendPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
