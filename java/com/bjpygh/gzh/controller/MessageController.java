package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.ConcernService;
import com.bjpygh.gzh.service.QrCodeService;
import com.bjpygh.gzh.utils.Http;
import com.bjpygh.gzh.utils.OrderPush;
import com.bjpygh.gzh.utils.XMLToMap;
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

    @Autowired
    ConcernService concernService;
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
    @ResponseBody
    @RequestMapping(value = "/getWxMessage", method = RequestMethod.POST)
    public String getWxMessage(HttpServletRequest request){
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
        System.out.println("map"+map);

        if (map.get("MsgType").equals("event")){
            if (map.get("Event").equals("CLICK")){
                if (map.get("EventKey").equals("support_hotline")){//咨询电话
                    String text = "座机：010-59822296 \n" +
                            "小漂：18813069524 \n" +
                            "      18811758773";
                    sendToUser(map,text);
                }
            }else if (map.get("Event").equals("subscribe")){//关注
                String text = "ʜɪ!我是小漂Ꙭ，既然关注了我那就是我的人啦\n"+
                        "为了纪念这一天相遇，我特意为你准备了爱心大礼包，快看看吧\n" +
                        "①<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx74d8d40a83387a3e&redirect_uri=http://gzpt.bjpygh.com/coupon.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">点击抽奖</a> \n"+
                        "②<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx74d8d40a83387a3e&redirect_uri=http://gzpt.bjpygh.com/tuanjian.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">团建白菜价</a> \n" +
                        "另外，【留言】还是要有的，万一被小漂Ꙭ撩了呢哈哈~\n" +
                        "                ᴀʟʟ ғᴏʀ ʏᴏᴜ";
                sendToUser(map,text);
                if (map.get("EventKey") != null){//扫码关注
                    qrCodeService.updateCode(map);
                    concernService.insertConcern(map);
                }
            }else if (map.get("Event").equals("unsubscribe")){//取关
                if (concernService.selectOpenid(map).size() > 0){
                    qrCodeService.changeCode(map);
                    concernService.deleteConcern(map);
                }
            }
        }
        return "success";
    }

    private void sendToUser(Map<String, String> map, String text) {
        JSONObject content = new JSONObject();
        content.element("content",text);
        JSONObject obj = new JSONObject();
        obj.element("touser",map.get("FromUserName"));
        obj.element("msgtype","text");
        obj.element("text",content);
        String result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + OrderPush.getAccess_token(),
                obj.toString());
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject.getInt("errcode") != 0){
            OrderPush.getAccesstoken();
            result = Http.sendPost("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + OrderPush.getAccess_token(),
                    obj.toString());
        }
        System.out.println("resultFromHttps="+result);
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

        return Status.success().add("ticket",qrCodeService.checkTicket(userid));
    }

    //获取关注信息
    @ResponseBody
    @RequestMapping(value = "/getConcern", method = RequestMethod.GET)
    public Status getConcern(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        Long userid = Long.valueOf(userMap.get("id"));

        return Status.success().add("ticket",qrCodeService.getConcern(userid));
    }
}
