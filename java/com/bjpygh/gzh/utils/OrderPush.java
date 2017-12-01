package com.bjpygh.gzh.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 微信消息推送
 */
public class OrderPush {

    String access_token;

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
        remark.accumulate("value",map.get("remark"));
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

        String result = sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode") != 0){
            access_token = getAccesstoken();
            result = sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,
                    obj.toString());
        }
        return result;
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

        String result = sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode")!=0){
            access_token = getAccesstoken();
            result = sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,
                    obj.toString());
        }
        return result;
    }

    public String PayForAV(Map<String, String> map){
        JSONObject first = new JSONObject();
        first.accumulate("value",map.get("first"));
        first.accumulate("color","#173177");

        JSONObject keyword1 = new JSONObject();
        keyword1.accumulate("value",map.get("keyword1"));
        keyword1.accumulate("color","#173177");

        JSONObject keyword2 = new JSONObject();
        keyword2.accumulate("value",map.get("keyword2"));
        keyword2.accumulate("color","#173177");

        JSONObject keyword3 = new JSONObject();
        keyword2.accumulate("value",map.get("keyword3"));
        keyword2.accumulate("color","#173177");

        JSONObject remark = new JSONObject();
        remark.accumulate("value",map.get("remark"));
        remark.accumulate("color","#173177");
        JSONObject ja = new JSONObject();

        ja.accumulate("first",first);
        ja.accumulate("keyword1",keyword1);
        ja.accumulate("keyword2",keyword2);
        ja.accumulate("keyword3",keyword3);
        ja.accumulate("remark",remark);

        JSONObject obj = new JSONObject();
        obj.accumulate("touser",map.get("openid"));
        obj.accumulate("template_id","uPIYP31nOWg4WIaxwCwQE3aPXTHLNL5yD_YgkKer-gY");
//        obj.accumulate("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx74d8d40a83387a3e&redirect_uri=http://gzpt.bjpygh.com/schedule.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        obj.accumulate("data",ja);

        String result = sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode")!=0){
            access_token = getAccesstoken();
            result = sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,
                    obj.toString());
        }
        return result;
    }

    public String FinishJsonObj(Map<String, String> map){
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
        obj.accumulate("template_id","doWGLX8X7dgUVFif8FHgsLl5j_aRDR2rYtTy6kEt6Dc\n");
        obj.accumulate("url","http://gzpt.bjpygh.com/orderInformation.html?ordernumber="+map.get("keyword1"));
        obj.accumulate("data",ja);

        String result = sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode") != 0){
            access_token = getAccesstoken();
            result = sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token,
                    obj.toString());
        }
        return result;
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

    public String getAccesstoken(){
        JSONObject jsonObject = JSONObject.fromObject(sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx74d8d40a83387a3e&secret=0f84386999305a8cd8464fc32efb01f3"));
        return jsonObject.getString("access_token");
    }

    public static String sendGet(String urlNameString) {
        String result = "";
        BufferedReader in = null;
        try {
//            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
