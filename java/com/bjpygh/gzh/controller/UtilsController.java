package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.DsOrder;
import com.bjpygh.gzh.bean.IntegralRecord;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.bean.UserCoupon;
import com.bjpygh.gzh.entity.Note;
import com.bjpygh.gzh.entity.Sms;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.CouponService;
import com.bjpygh.gzh.service.DsOrderService;
import com.bjpygh.gzh.service.RecordService;
import com.bjpygh.gzh.service.UserService;
import com.bjpygh.gzh.utils.JuheSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UtilsController extends BaseController {

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;

    @Autowired
    CouponService couponService;

    //发送短信验证码接口
    @ResponseBody
    @RequestMapping(value = "/sms.action", method = RequestMethod.POST)
    public Status SmsCode(Sms sms){
        if (JuheSMS.getRequest2(sms.getMobile(), sms.getTpl_id(), sms.getTpl_value())){
            return Status.success();
        }else{
            return Status.fail(-20,"处理失败");
        }
    }

    //报名备注填写接口
    @ResponseBody
    @RequestMapping(value = "/note.action", method = RequestMethod.POST)
    public Status Note(HttpServletRequest request, Note n){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        String realname = n.getRealname();
        String address = n.getAddress();
        String note = n.getNote();

        Map<String, String> map = new HashMap<String, String>();
        List<DsOrder> dsOrder = dsOrderService.getOrdersById(userid);
        for(DsOrder dso : dsOrder){
            if(dso!=null&&dso.getOrderStatus()!=5&&dso.getOrderStatus()!=6){
                return Status.fail(-20,"已支付过订单");
            }
        }

        map.put("userId", userid);
        map.put("realName", realname);
        map.put("address", address);
        map.put("reamark", note);

        userService.bondRealName(map);
        return Status.success();
    }

    //优惠券码验证
    @ResponseBody
    @RequestMapping(value = "/actcode.action", method = RequestMethod.POST)
    public Status ActiveCode(HttpSession session, String code){
        Map<String, String> userMap = (Map<String, String>) session.getAttribute("user");
        if(code!=null&&code.equals("PYGH521")){
            userMap.put("active", "true");
            session.removeAttribute("user");
            session.setAttribute("user", userMap );
            return Status.success();
        }else{
            userMap.put("active", "false");
            session.removeAttribute("user");
            session.setAttribute("user", userMap );
            return Status.fail(-20,"验证失败");
        }
    }

    //会员激活接口
    @ResponseBody
    @RequestMapping(value = "/activation.action", method = RequestMethod.POST)
    public Status Activation(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        User user = userService.getUserById(userid);
        if(user.getMemberPoints()<15){
            return Status.fail(-20,"积分不足");
        }
        Map<String, String> pointMap = new HashMap<String, String>();
        pointMap.put("userId", userid);
        pointMap.put("memberPoints", user.getMemberPoints()-15+"");
        pointMap.put("integral", user.getIntegral()+"");
        userService.changeUserPoints(pointMap);

        //插入消费记录
        IntegralRecord record = new IntegralRecord();
        record.setValue("-15");
        record.setNote("驾校优惠券激活");
        record.setUserId(user.getUserId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        record.setTime(formatter.format(new Date()));
//        recordService.insertRecord(record);

        UserCoupon userCoupon = couponService.getDsCoupon(userid);
        Map<String, String> statusMap = new HashMap<String, String>();
        statusMap.put("userId",""+userCoupon.getUserId());
        statusMap.put("couponStatus", "2");
        couponService.updataCouponStatus(statusMap);

        return Status.success();
    }

//    //查询积分消费记录接口
//    @ResponseBody
//    @RequestMapping(value = "/selectRecord.action", method = RequestMethod.POST)
//    public Status selectRecord(HttpServletRequest request){
//        Map<String, String> userMap = checkWxUser(request);
//        if(userMap == null){
//            return Status.notInWx();
//        }
//        String userid = userMap.get("id");
//        List<IntegralRecord> records = recordService.getRecordById(userid);
//        if(records.size()>0){
//            return Status.success().add("records",records);
//        }else{
//            return Status.fail(-30,"没有记录");
//        }
//    }
}
