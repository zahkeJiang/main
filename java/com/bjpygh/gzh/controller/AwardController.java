package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.Address;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AwardController extends BaseController{

    @Autowired
    AwardService awardService;

    //获取奖品列表
    @ResponseBody
    @RequestMapping(value = "/getAwards", method = RequestMethod.POST)
    public Status getAwards(String filter, HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return Status.success().add("awards",awardService.getAwards(userid,filter));
    }

    //获取奖品列表
    @ResponseBody
    @RequestMapping(value = "/getAward", method = RequestMethod.POST)
    public Status getAward(String awardId, HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return Status.success().add("award",awardService.getAward(userid, awardId));
    }

    //添加地址
    @ResponseBody
    @RequestMapping(value = "/insertAddress", method = RequestMethod.POST)
    public Status insertAddress(Address address, HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return awardService.insertAddress(userid, address);
    }

    //添加地址
    @ResponseBody
    @RequestMapping(value = "/selectAddresses", method = RequestMethod.POST)
    public Status selectAddresses(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return awardService.selectAddresses(userid);
    }

    //添加地址
    @ResponseBody
    @RequestMapping(value = "/selectAddress", method = RequestMethod.POST)
    public Status selectAddress(String addressId, HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return Status.success().add("addresses",awardService.selectAddress(addressId));
    }

    //添加地址
    @ResponseBody
    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public Status updateAddress(Address address, HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return awardService.updateAddress(address);
    }

    //添加地址
    @ResponseBody
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public Status deleteAddress(String addressId, HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return awardService.deleteAddress(addressId);
    }

    @RequestMapping(value = "/jiangpin.action", method = RequestMethod.GET)
    public String applyCard(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "prizesCenter";
    }
}
