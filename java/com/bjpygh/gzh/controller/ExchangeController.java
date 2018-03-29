package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.Exchange;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ExchangeController extends BaseController {

    @Autowired
    ExchangeService exchangeService;

    //获取兑换记录列表
    @ResponseBody
    @RequestMapping(value = "/selectExchanges", method = RequestMethod.POST)
    public Status selectExchanges(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return Status.success().add("exchanges",exchangeService.selectExchanges(userid));
    }

    //通过ID获取单个兑换记录
    @ResponseBody
    @RequestMapping(value = "/selectExchange", method = RequestMethod.POST)
    public Status selectExchange(HttpServletRequest request,Exchange exchange){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return exchangeService.selectExchange(exchange, userid);
    }

    //奖品兑换接口
    @ResponseBody
    @RequestMapping(value = "/insertExchanges", method = RequestMethod.POST)
    public Status insertExchanges(Exchange exchange, HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return exchangeService.insertExchanges(userid, exchange);
    }
}
