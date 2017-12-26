package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.QrCodeService;
import com.bjpygh.gzh.utils.Http;
import com.bjpygh.gzh.utils.OrderPush;
import com.bjpygh.gzh.utils.XMLToMap;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MessageController extends BaseController {


    @Autowired
    QrCodeService qrCodeService;
    //改变订单状态接口
    @ResponseBody
    @RequestMapping(value = "/getWxMessage", method = RequestMethod.GET)
    public String ChangeOrder(HttpServletRequest request){
//        String signature = request.getParameter("signature");
//        String timestamp = request.getParameter("timestamp");
//        String nonce = request.getParameter("nonce");
//        String echostr = request.getParameter("echostr");
//        System.out.println("signature:" + signature);
//        System.out.println("timestamp:" + timestamp);
//        System.out.println("nonce:" + nonce);
//        System.out.println("echostr:" + echostr);
        String inputLine;
        String notityXml = "";

        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        XMLToMap x= new XMLToMap();
        Map<String, String> map = x.getXML(notityXml);
        System.out.println(map);
        return "success";
    }

    //改变订单状态接口
    @ResponseBody
    @RequestMapping(value = "/getQrcode", method = RequestMethod.GET)
    public Status getQrcode(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        int userid = Integer.parseInt(userMap.get("id"));
        ;

        return Status.success().add("ticket",qrCodeService.getTicket(userid));
    }
}
