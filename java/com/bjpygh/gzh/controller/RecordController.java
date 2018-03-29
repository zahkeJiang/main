package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.controller.BaseController;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class RecordController extends BaseController {

    @Autowired
    RecordService recordService;

    /**
     * 积分收入记录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/inCoinRecord",method = RequestMethod.POST)
    public Status inCoinRecord(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return recordService.inCoinRecord(userid);
    }

    /**
     * 积分支出记录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/outCoinRecord",method = RequestMethod.POST)
    public Status outCoinRecord(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        return recordService.outCoinRecord(userid);
    }
}
