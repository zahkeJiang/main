package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.Concern;
import com.bjpygh.gzh.bean.Integral;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.entity.Global;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.ConcernService;
import com.bjpygh.gzh.service.IntegralService;
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
import java.util.HashMap;
import java.util.Map;

@Controller
public class MessageController extends BaseController {


    @Autowired
    QrCodeService qrCodeService;

    @Autowired
    ConcernService concernService;

    @Autowired
    IntegralService integralService;

    //
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
                        "①<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Global.appID+"&redirect_uri="+Global.URL+"/coupon.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">点击抽奖</a> \n"+
                        "②<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Global.appID+"&redirect_uri="+Global.URL+"/tuanjian.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">团建白菜价</a> \n" +
                        "另外，【留言】还是要有的，万一被小漂Ꙭ撩了呢哈哈~\n" +
                        "                ᴀʟʟ ғᴏʀ ʏᴏᴜ";
                sendToUser(map,text);
                if (map.get("EventKey") != null){//扫码关注
                    //获取关注者的openid
                    String openid = map.get("FromUserName");
                    //判断是否关注过
                    Concern concern1 = concernService.selectConcernByOpenId(openid);
                    if (concern1 != null){ //关注过
                        //更新关注时间
                        concernService.updateTimeAndCancel(openid);
                        //前一个推广用户净关注数量加一,取关量减一
                        qrCodeService.updateQrConcern(concern1.getUserId());
                    }else {
                        //添加新关注用户
                        concernService.insertConcern(map);
                        qrCodeService.updateNewConcern(map);//新推广者的关注量变化
                        //奖励一金币
                        String userId = map.get("EventKey").split("_")[1];
                        integralService.addOneCoin(userId);
                        //此处做推送
                        User user = userService.getUserById(userId);
                        String pushInfo = "温馨提示： \n" +
                                "成功推荐一名用户关注 \n";
                        Map<String, String> openId = new HashMap<String, String>();
                        openId.put("FromUserName", user.getOpenid());
                        sendToUser(openId,pushInfo);
                    }

                }else {
                    concernService.insertConcern(map);
                }
            }else if (map.get("Event").equals("unsubscribe")){//取关
                String openid = map.get("FromUserName");
                concernService.cancelConcern(openid);  //更新关注状态为取关
                //更新推荐人的关注量
                String userId = map.get("EventKey").split("_")[1];
                qrCodeService.updateNewQrCode(userId);
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
