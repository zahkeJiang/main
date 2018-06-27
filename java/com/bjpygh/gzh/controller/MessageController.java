package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.Concern;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.bean.UserCoin;
import com.bjpygh.gzh.entity.Global;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.ConcernService;
import com.bjpygh.gzh.service.IntegralService;
import com.bjpygh.gzh.service.QrCodeService;
import com.bjpygh.gzh.service.UserService;
import com.bjpygh.gzh.utils.Http;
import com.bjpygh.gzh.utils.OrderPush;
import com.bjpygh.gzh.utils.XMLToMap;
import net.sf.json.JSONArray;
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

    @Autowired
    UserService userService;

    int index = 0;

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
                            "           13070108698";
                    sendToUser(map,text);
                }
            }else if (map.get("Event").equals("subscribe")){//关注
                String text = "终于等到你！所有的相见恨晚，都是恰逢其时。来了，就别走。\n"+
                        "看看我给你准备的大礼包里面都有啥[机智]\n" +
                        "1.<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Global.appID+"&redirect_uri="+Global.URL+"/coupon.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">团建白菜价</a> \n"+
                        "2.<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Global.appID+"&redirect_uri="+Global.URL+"/tuanjian.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">漂洋过海VIP驾考补贴</a> \n" +
                        "3.回复“签到”，了解我们的“分享有礼活动” \n" +
                        "5.回复“预约咨询”，勾搭漂洋过海CEO（一只90后帅气学长），预约“构建自我-职业生涯规划”、“职场菁英训练营”、“企业家CEO创业畅谈沙龙”等优质咨询课程~\n" +
                        "6.回复“欢乐谷”，参加漂洋过海端午节欢乐谷特惠活动，只要99元，日场夜场任你玩儿！\n" +
                        "7.回复“小黄车”，和漂洋过海一起竞猜世界杯，瓜分百万免费骑行[机智]";
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
        }else if (map.get("MsgType").equals("text")){
            if (map.get("Content").equals("签到")){
                String userId = userService.getUserIdByOpenid(map.get("FromUserName"));
                UserCoin userCoin = integralService.selectUserCoinByUserId(userId);
                String text = "";
                if (userCoin.getIsSign() == 0){
                    Status status = integralService.updateUserSign(userId);
                    Map<String, Object> data = status.getData();
                    HashMap<String, Integer> map1 = (HashMap<String, Integer>) data.get("map");
                    Integer generalCoin = map1.get("generalCoin");
                    double v = userCoin.getGeneralCoin() + generalCoin;
                    text = "叮叮～今日份儿的签到打卡get！\n" +
                            generalCoin + "枚银币已收入囊中～\n" +
                            "累积获得" + v + "枚银币！\n" +
                            "<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Global.appID+"&redirect_uri="+Global.URL+"/jiangpin.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">戳我去奖品中心</a> \n";
                }else {
                    text = "您已完成签到";
                }
                sendToUser(map,text);
            }else if (map.get("Content").equals("王卡")) {
                String text = "恭喜抢到优惠名额！\n" +
                        "#漂洋过海×腾讯大王卡#  \n" +
                        "0元购卡另赠120元话费\n" +
                        "<a href=\"https://jinshuju.net/f/6VsMEK?from=groupmessage&isappinstalled=0\">点击独家专属链接挑选靓号</a>";
                sendToUser(map, text);
            }else if (map.get("Content").equals("大王卡")){
                String text = "恭喜抢到优惠名额！\n" +
                        "#漂洋过海×腾讯大王卡#  \n" +
                        "0元购卡另赠120元话费\n" +
                        "<a href=\"https://jinshuju.net/f/6VsMEK?from=groupmessage&isappinstalled=0\">点击独家专属链接挑选靓号</a>";
                sendToUser(map, text);
            }else if (map.get("Content").equals("腾讯大王卡")){
                String text = "恭喜抢到优惠名额！\n" +
                        "#漂洋过海×腾讯大王卡#  \n" +
                        "0元购卡另赠120元话费\n" +
                        "<a href=\"https://jinshuju.net/f/6VsMEK?from=groupmessage&isappinstalled=0\">点击独家专属链接挑选靓号</a>";
                sendToUser(map, text);
            }else if (map.get("Content").equals("联通大王卡")){
                String text = "恭喜抢到优惠名额！\n" +
                        "#漂洋过海×腾讯大王卡#  \n" +
                        "0元购卡另赠120元话费\n" +
                        "<a href=\"https://jinshuju.net/f/6VsMEK?from=groupmessage&isappinstalled=0\">点击独家专属链接挑选靓号</a>";
                sendToUser(map, text);
            }else if (map.get("Content").equals("欢乐谷")){
                JSONObject a = new JSONObject();
                a.element("title", "欢乐谷端午门票只需99元另外赠送大礼！！！");
                a.element("description", "欢乐谷第四期甜品王国全面上线，13大项目酷爽嗨萌！家庭过山车、蛋糕秋千、尖峰时刻、甜心飞饼、飞跃牛奶河、皇宫影院......");
                a.element("url","https://mp.weixin.qq.com/s/QcEkwJ9tef-FmxdCT-ZSHQ");
                a.element("picurl", "https://mmbiz.qlogo.cn/mmbiz_jpg/G9kiclPrCRqJBtjReDEXpMEYSteh61oEo0oLQ9DAPibcicRxpWtIiapNYkRm1WicDxib1anicshlL9ODWAiaLfUMoZkJ0w/0?wx_fmt=jpeg");
                sendToUserWithPic(map, a);
            }else if (map.get("Content").equals("别墅")){
                String text = "报名别墅轰趴流程：\n" +
                        "1.添加小漂微信：pygh0318或13070108698咨询详情（备注：别墅+姓名）\n" +
                        "2.活动收款支付宝：13260386563 杨金秋 （转账务必备注：别墅+姓名）\n" +
                        "小漂爱泥萌么么哒~";
                sendToUser(map,text);
            }else if (map.get("Content").equals("香山报名")){
                String text = "香山真人CS活动报名流程如下：\n" +
                        "1、报名小编微信：pygh0318\n" +
                        "（验证备注：香山+活动日期+名字） \n" +
                        "    13070108698\n" +
                        "2、活动收款支付宝： 13260386563 杨金秋\n" +
                        "（转账务必备注：香山+姓名）";
                sendToUser(map,text);
            }else if (map.get("Content").equals("抽奖")){
                String text = "<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx74d8d40a83387a3e&redirect_uri=http://gzpt.bjpygh.com/coupon.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">Follow me</a>";
                sendToUser(map,text);
            }else if (map.get("Content").equals("527")){
                String text = "小伙伴你好呀！欢迎参加漂洋过海527活动~添加微信13070108698，验证备注：姓名+学校+527，即可参加，有一个可爱的小姐姐在等你哦[嘿哈]";
                sendToUser(map,text);
            }else if (map.get("Content").equals("小黄车")){
                index ++;
                String text = "＃漂洋过海＃陪你竞猜世界杯，瓜分百万免费骑行\n" +
                        "<a href='https://ofo-campaign.ofo.com/wcactivity/index.html#/?channel=15289526091161&platform=1'>点击立即领取</a>";
                sendToUser(map,text);
            }else if (map.get("Content").equals("毕业照约拍")){
                String text = "请添加工作小编微信: 13070108698 了解详情并说出你的需求，验证消息请备注“姓名＋学校”";
                sendToUser(map,text);
            }else if (map.get("Content").equals("预约咨询")){
                String text = "Hi～我是CEO的助理，一只可爱的学姐～预约CEO请加微信: 13070108698，了解课程详情～";
                sendToUser(map,text);
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

    private void sendToUserWithPic(Map<String, String> map, JSONObject a) {
        JSONArray articles = new JSONArray();
        articles.element(a);
        JSONObject arti = new JSONObject();
        arti.element("articles", articles);
        JSONObject obj = new JSONObject();
        obj.accumulate("touser",map.get("FromUserName"));
        obj.accumulate("msgtype","news");
        obj.accumulate("news",arti);
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

//    //获取关注信息
//    @ResponseBody
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public Status test(HttpServletRequest request){
//        List<String> strings = userService.selectAllOpenid();
//
//        for (int i=0;i<strings.size();i++){
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("FromUserName", strings.get(i));
//            concernService.insertConcern(map);
//        }
//
//        return Status.success();
//    }
}
