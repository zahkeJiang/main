package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class IntegralController extends BaseController {

    @Autowired
    IntegralService integralService;

    /**
     * 获取积分
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectUserCoin",method = RequestMethod.POST)
    public Status selectUserCoin(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return integralService.selectUserCoin(userid);
    }

    /**
     * 用户签到接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUserSign",method = RequestMethod.POST)
    public Status updateUserSign(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return integralService.updateUserSign(userid);
    }
}
