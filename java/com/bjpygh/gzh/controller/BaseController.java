package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.entity.Global;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.service.UserService;
import com.bjpygh.gzh.utils.OrderPush;
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
            String openid = null;
            String access_token = null;
            String userId = null;
            try{
//                map = OrderPush.getMap(request);
//                openid =map.get("openid");
//                access_token = map.get("access_token");
//                userId = userService.getUserIdByOpenid(openid);
//                if(userId == null){		//判断用户不存在
//                    //用户不存在则插入用户
//                    User user = OrderPush.getUser(OrderPush.getUserInfo(openid, access_token));
//                    userService.InsertUserFromWx(user);
//                    userId = userService.getUserIdByOpenid(openid);
//                }
//                //将用户信息放入缓存中
//                userMap.put("id",userId);
//                userMap.put("openid", openid);
//                userMap.put("access_token",access_token);
            userMap.put("id","1");
            userMap.put("openid","o9C-m0gWfR9WOs8DIDElxSUfDIUU");
                session.setAttribute("user", userMap );
                System.out.println("access_token="+userMap);
                return  userMap;
            }catch (Exception e){
                return null;
            }
        }else {
            return userMap;
        }
    }


    //从微信平台获取用户信息
//    private String getUserInfo(String openid,String access_token) {
//        String userInfo = getInfo(Global.WXURL+Global.WXURLUF+"?access_token="+access_token+"&openid="+openid+"&lang=zh_CN");
//        return userInfo;
//    }



}
