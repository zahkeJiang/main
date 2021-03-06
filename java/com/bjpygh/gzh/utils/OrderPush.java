package com.bjpygh.gzh.utils;

import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.entity.Global;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信消息推送
 */
public class OrderPush {

    static String access_token = null;

    String jsapi_ticket;

    public String CreateJsonObj(Map<String, String> map) {
        JSONObject first = new JSONObject();
        first.accumulate("value", map.get("first"));
        first.accumulate("color", "#173177");

        JSONObject orderID = new JSONObject();
        orderID.accumulate("value", map.get("orderID"));
        orderID.accumulate("color", "#173177");

        JSONObject orderMoneySum = new JSONObject();
        orderMoneySum.accumulate("value", map.get("orderMoneySum"));
        orderMoneySum.accumulate("color", "#173177");

        JSONObject remark = new JSONObject();
        remark.accumulate("value", map.get("remark"));
        remark.accumulate("color", "#173177");
        JSONObject ja = new JSONObject();

        ja.accumulate("first", first);
        ja.accumulate("orderID", orderID);
        ja.accumulate("orderMoneySum", orderMoneySum);
        ja.accumulate("remark", remark);

        JSONObject obj = new JSONObject();
        obj.accumulate("touser", map.get("openid"));
        obj.accumulate("template_id", "Oxsy4JnPMy3F2KvuEjKrS5awVXMK8zFir3pblGAaqow");
        obj.accumulate("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + Global.appID + "&redirect_uri=" + Global.URL + "/schedule.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        obj.accumulate("data", ja);

        String result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token,
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode") != 0) {
            access_token = getAccesstoken();
            result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token,
                    obj.toString());
        }
        return result;
    }

    public String PayJsonObj(Map<String, String> map) {
        JSONObject first = new JSONObject();
        first.accumulate("value", map.get("first"));
        first.accumulate("color", "#173177");

        JSONObject keyword1 = new JSONObject();
        keyword1.accumulate("value", map.get("keyword1"));
        keyword1.accumulate("color", "#173177");

        JSONObject keyword2 = new JSONObject();
        keyword2.accumulate("value", map.get("keyword2"));
        keyword2.accumulate("color", "#173177");

        JSONObject remark = new JSONObject();
        remark.accumulate("value", map.get("remark"));
        remark.accumulate("color", "#173177");
        JSONObject ja = new JSONObject();

        ja.accumulate("first", first);
        ja.accumulate("keyword1", keyword1);
        ja.accumulate("keyword2", keyword2);
        ja.accumulate("remark", remark);

        JSONObject obj = new JSONObject();
        obj.accumulate("touser", map.get("openid"));
        obj.accumulate("template_id", "uPIYP31nOWg4WIaxwCwQE3aPXTHLNL5yD_YgkKer-gY");
//        obj.accumulate("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx74d8d40a83387a3e&redirect_uri=http://gzpt.bjpygh.com/schedule.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        obj.accumulate("data", ja);

        String result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token,
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode") != 0) {
            access_token = getAccesstoken();
            result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token,
                    obj.toString());
        }
        return result;
    }

    public String PayForAV(Map<String, String> map) {
        JSONObject first = new JSONObject();
        first.accumulate("value", map.get("first"));
        first.accumulate("color", "#173177");

        JSONObject keyword1 = new JSONObject();
        keyword1.accumulate("value", map.get("keyword1"));
        keyword1.accumulate("color", "#173177");

        JSONObject keyword2 = new JSONObject();
        keyword2.accumulate("value", map.get("keyword2"));
        keyword2.accumulate("color", "#173177");

        JSONObject keyword3 = new JSONObject();
        keyword2.accumulate("value", map.get("keyword3"));
        keyword2.accumulate("color", "#173177");

        JSONObject remark = new JSONObject();
        remark.accumulate("value", map.get("remark"));
        remark.accumulate("color", "#173177");
        JSONObject ja = new JSONObject();

        ja.accumulate("first", first);
        ja.accumulate("keyword1", keyword1);
        ja.accumulate("keyword2", keyword2);
        ja.accumulate("keyword3", keyword3);
        ja.accumulate("remark", remark);

        JSONObject obj = new JSONObject();
        obj.accumulate("touser", map.get("openid"));
        obj.accumulate("template_id", "uPIYP31nOWg4WIaxwCwQE3aPXTHLNL5yD_YgkKer-gY");
//        obj.accumulate("url","https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx74d8d40a83387a3e&redirect_uri=http://gzpt.bjpygh.com/schedule.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        obj.accumulate("data", ja);

        String result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token,
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode") != 0) {
            access_token = getAccesstoken();
            result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token,
                    obj.toString());
        }
        return result;
    }

    public String FinishJsonObj(Map<String, String> map) {
        JSONObject first = new JSONObject();
        first.accumulate("value", map.get("first"));
        first.accumulate("color", "#173177");

        JSONObject keyword1 = new JSONObject();
        keyword1.accumulate("value", map.get("keyword1"));
        keyword1.accumulate("color", "#173177");

        JSONObject keyword2 = new JSONObject();
        keyword2.accumulate("value", map.get("keyword2"));
        keyword2.accumulate("color", "#173177");

        JSONObject remark = new JSONObject();
        remark.accumulate("value", map.get("remark"));
        remark.accumulate("color", "#173177");
        JSONObject ja = new JSONObject();

        ja.accumulate("first", first);
        ja.accumulate("keyword1", keyword1);
        ja.accumulate("keyword2", keyword2);
        ja.accumulate("remark", remark);

        JSONObject obj = new JSONObject();
        obj.accumulate("touser", map.get("openid"));
        obj.accumulate("template_id", "doWGLX8X7dgUVFif8FHgsLl5j_aRDR2rYtTy6kEt6Dc\n");
        obj.accumulate("url", "http://gzpt.bjpygh.com/orderInformation.html?ordernumber=" + map.get("keyword1"));
        obj.accumulate("data", ja);

        String result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token,
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode") != 0) {
            access_token = getAccesstoken();
            result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token,
                    obj.toString());
        }
        return result;
    }


    public static String getAccesstoken() {
        JSONObject jsonObject = JSONObject.fromObject(
                Http.sendGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Global.appID + "&secret=" + Global.appsecret));
        access_token = jsonObject.getString("access_token");
        return access_token;
    }

    public String getJsapiTicket(String passed) {
        if (passed.equals("1")) {
            JSONObject jsonObject = JSONObject.fromObject(Http.sendGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi"));
            if (jsonObject.getInt("errcode") != 0) {
                access_token = getAccesstoken();
                jsonObject = JSONObject.fromObject(Http.sendGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi"));
                return jsonObject.getString("ticket");
            } else {
                return jsonObject.getString("ticket");
            }
        } else {
            return jsapi_ticket;
        }
    }

    public static String getAccess_token() {
        if (access_token != null) {
            return access_token;
        } else {
            return getAccesstoken();
        }
    }


    //从微信平台获取用户信息
    public static String getUserInfo(String openid, String access_token) {
        System.out.println("1111111111111111111111111111");
        String userInfo = getInfo(Global.WXURL + Global.WXURLUF + "?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN");
        return userInfo;
    }

    //get请求方式访问数据
    public static String getInfo(String url) {
        URL httpUrl;
        String s = null;
        try {
            httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            s = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    //将返回的用户信息解析成User对象
    public static User getUser(String userInfo) {
        User user = new User();
        JSONObject user1 = JSONObject.fromObject(userInfo);
        user.setCity(user1.getString("city"));
        user.setOpenid(user1.getString("openid"));
        user.setCountry(user1.getString("country"));
        user.setNickname(user1.getString("nickname"));
        user.setProvince(user1.getString("province"));
        user.setSex(Integer.parseInt(user1.getString("sex")));
        if (Integer.parseInt(user1.getString("sex")) == 2) {
            user.setHeadimageurl("http://120.24.184.86/glxt/dsimage/girl.jpg");
        } else {
            user.setHeadimageurl("http://120.24.184.86/glxt/dsimage/boy.jpg");
        }

        return user;
    }

    //获取用户的openID，以及access_token。
    public static Map<String, String> getMap(HttpServletRequest request) {
        String code = request.getParameter("code");
        System.out.println("code:" + code);
        String sb1 = OrderPush.getInfo(Global.WXURL + Global.WXURLAT +
                "?appid=" + Global.appID + "&secret=" + Global.appsecret +
                "&code=" + code + "&grant_type=authorization_code");
        System.out.println("sb1:" + sb1);
        JSONObject tmp = JSONObject.fromObject(sb1);
        String access_token = null;
        String openid = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            access_token = tmp.getString("access_token");
            openid = tmp.getString("openid");
        } catch (Exception e) {
            return map;
        }
        map.put("access_token", access_token);
        map.put("openid", openid);
        return map;
    }
}
