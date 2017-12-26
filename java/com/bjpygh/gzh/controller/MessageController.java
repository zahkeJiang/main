package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.entity.Status;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MessageController extends BaseController {

    //改变订单状态接口
    @ResponseBody
    @RequestMapping(value = "/getWxMessage", method = RequestMethod.GET)
    public String ChangeOrder(HttpServletRequest request){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        System.out.println("signature:" + signature);
        System.out.println("timestamp:" + timestamp);
        System.out.println("nonce:" + nonce);
        System.out.println("echostr:" + echostr);
        return echostr;
    }
}
