package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.DsInformation;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.DsInfoService;
import com.bjpygh.gzh.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class PackageController extends BaseController{

    @Autowired
    PackageService packageService;

    @Autowired
    DsInfoService dsInfoService;

    //根据套餐id获取套餐信息
    @ResponseBody
    @RequestMapping(value = "/queryPackage.action", method = RequestMethod.POST)
    public Status QueryPackage(HttpServletRequest request,String packageid){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        return packageService.getPackageById(packageid);
    }

    //获取驾校套餐列表接口，为空时返回驾校信息
    @ResponseBody
    @RequestMapping(value = "/sdp.action", method = RequestMethod.POST)
    public Status getPackage(HttpServletRequest request,String dsname){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        return packageService.getPackageList(dsname);
    }

    //获取驾校列表
    @ResponseBody
    @RequestMapping(value = "/sdi.action", method = RequestMethod.POST)
    public Status getDsInfoList(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        List<DsInformation> dspInfolist = dsInfoService.selectDsInfoList();
        return Status.success().add("dspInfolist",dspInfolist);
    }

    //驾考首页
    @RequestMapping(value = "/sdi.action", method = RequestMethod.GET)
    public String toDsInfoList(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return "error";
        }
        return "index";
    }

}
