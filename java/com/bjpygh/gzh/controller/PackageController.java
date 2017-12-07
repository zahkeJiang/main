package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.DsInformation;
import com.bjpygh.gzh.bean.DsPackage;
import com.bjpygh.gzh.bean.Recommend;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.DsInfoService;
import com.bjpygh.gzh.service.PackageService;
import com.bjpygh.gzh.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class PackageController extends BaseController {

    @Autowired
    PackageService packageService;

    @Autowired
    DsInfoService dsInfoService;

    @Autowired
    RecommendService recommendService;

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
    public Status getPackage(HttpServletRequest request,DsPackage dsPackage){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        return packageService.getPackageList(dsPackage);
    }

    //获取驾校首页名称
    @ResponseBody
    @RequestMapping(value = "/sdi.action", method = RequestMethod.POST)
    public Status getDsInfoList(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        List<Map<String, String>> dsNames = dsInfoService.selectDsNames();
        return Status.success().add("dsNames",dsNames);
    }

    //获取驾校信息
    @ResponseBody
    @RequestMapping(value = "/getDsInfo", method = RequestMethod.POST)
    public Status getDsInfo(HttpServletRequest request,String dsName){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        DsInformation dsInfo = dsInfoService.selectDsInfo(dsName);
        return Status.success().add("dsNames",dsInfo);
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

    //提交推荐数据接口
    @ResponseBody
    @RequestMapping(value = "/Recommend", method = RequestMethod.POST)
    public Status setRecommend(HttpServletRequest request,@RequestBody(required = false)Recommend recommend){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }

        if (recommend != null){
            recommend.setRecommend(Long.valueOf(userMap.get("id")));
            recommendService.setRecommend(recommend);

            List<DsPackage> packageByRecommend = packageService.getPackageByRecommend(recommend);
            return  Status.success().add("packages",packageByRecommend);
        }else {
            recommend = recommendService.getRecommend(userMap.get("id"));
            if (recommend != null){
                List<DsPackage> packageByRecommend = packageService.getPackageByRecommend(recommend);
                return  Status.success().add("packages",packageByRecommend);
            }else {
                return Status.fail(-20,"没有推荐记录");
            }
        }

    }

//    //获取推荐记录接口
//    @ResponseBody
//    @RequestMapping(value = "/getRecommend", method = RequestMethod.POST)
//    public Status getRecommend(HttpServletRequest request){
//        Map<String, String> userMap = checkWxUser(request);
//        if(userMap == null){
//            return Status.notInWx();
//        }
//
//        Recommend recommend = recommendService.getRecommend(userMap.get("id"));
//        if (recommend != null){
//            List<DsPackage> packageByRecommend = packageService.getPackageByRecommend(recommend);
//            if (packageByRecommend.size()>0){
//                return  Status.success().add("packages",packageByRecommend);
//            }else {
//                return  Status.fail(-30,"根据您的选择，没有匹配到推荐");
//            }
//
//        }else {
//            return Status.fail(-20,"没有推荐记录");
//        }
//    }


}
