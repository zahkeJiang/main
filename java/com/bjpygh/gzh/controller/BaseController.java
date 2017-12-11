package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.entity.Global;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {

    @Autowired
    UserService userService;

    //判断用户信息是否存入数据库，未存入则存入数据库,并返回userMap
    public Map<String, String> checkWxUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String, String> userMap = (Map<String, String>) session.getAttribute("user");
        Map<String, String> map = new HashMap<String, String>();
        if(userMap == null){
            userMap = new HashMap<String, String>();
//            map = getMap(request);
//            String openid =map.get("openid");
//            String access_token = map.get("access_token");
//            String userId = userService.getUserIdByOpenid(openid);
//            if(userId == null){		//判断用户不存在
//                //用户不存在则插入用户
//                User user = getUser(getUserInfo(openid, access_token));
//                userService.InsertUserFromWx(user);
//                userId = userService.getUserIdByOpenid(openid);
//            }
//          //将用户信息放入缓存中
//            userMap.put("id",userId);
//            userMap.put("openid", openid);
//            userMap.put("access_token",access_token);
            userMap.put("id","1");
            userMap.put("openid","o9C-m0gWfR9WOs8DIDElxSUfDIUU");
            session.setAttribute("user", userMap );
        }
        System.out.println("access_token="+userMap);
        return  userMap;
    }


    //从微信平台获取用户信息
    private String getUserInfo(String openid,String access_token) {
        String userInfo = getInfo(Global.WXURL+Global.WXURLUF+"?access_token="+access_token+"&openid="+openid+"&lang=zh_CN");
        return userInfo;
    }

    //将返回的用户信息解析成User对象
    private User getUser(String userInfo) {
        User user = new User();
        JSONObject user1 = JSONObject.fromObject(userInfo);
        user.setCity(user1.getString("city"));
        user.setOpenid(user1.getString("openid"));
        user.setCountry(user1.getString("country"));
        user.setNickname(user1.getString("nickname"));
        user.setProvince(user1.getString("province"));
        user.setSex(Integer.parseInt(user1.getString("sex")));
        if (Integer.parseInt(user1.getString("sex"))==2){
            user.setHeadimageurl("http://120.24.184.86/glxt/dsimage/girl.jpg");
        }else {
            user.setHeadimageurl("http://120.24.184.86/glxt/dsimage/boy.jpg");
        }

        return user;
    }

    //获取用户的openID，以及access_token。
    private Map<String, String> getMap(HttpServletRequest request) {
        String code = request.getParameter("code");

        String sb1 = getInfo(Global.WXURL+Global.WXURLAT+"?appid=wx74d8d40a83387a3e&secret=0f84386999305a8cd8464fc32efb01f3&code="+code+"&grant_type=authorization_code");
        JSONObject tmp = JSONObject.fromObject(sb1);
        String access_token = tmp.getString("access_token");
        String openid = tmp.getString("openid");

        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", access_token);
        map.put("openid", openid);
        return map;
    }

    //get请求方式访问数据
    private String getInfo(String url){
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
            while((str = reader.readLine())!=null){
                sb.append(str);
            }
            s = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
