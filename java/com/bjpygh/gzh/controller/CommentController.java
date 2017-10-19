package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.*;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.ArmyOrderService;
import com.bjpygh.gzh.service.CommentService;
import com.bjpygh.gzh.service.DsOrderService;
import com.bjpygh.gzh.service.VillaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController extends BaseController{

    @Autowired
    CommentService commentService;

    @Autowired
    DsOrderService dsOrderService;

    @Autowired
    VillaOrderService villaOrderService;

    @Autowired
    ArmyOrderService armyOrderService;

    @ResponseBody
    @RequestMapping(value = "/putComment", method = RequestMethod.POST)
    public Status putComment(Comment comment,String ordernumber,HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        comment.setUserId(Long.valueOf(userid));
        commentService.putComment(comment);
        updateOrderStatus(comment.getType(),ordernumber);
        return Status.success();
    }

    private void updateOrderStatus(Integer type, String ordernumber) {
        if (type==1){
            VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);
            villaOrder.setOrderStatus(7);
            villaOrderService.updateOrder(villaOrder);
        }else if (type==2){
            DsOrder dsOrder = dsOrderService.getDsOrderByNumber(ordernumber).get(0);
            dsOrder.setOrderStatus((byte) 7);
            dsOrderService.updateOrder(dsOrder);
        }else if (type==3){
            ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(ordernumber).get(0);
            armyOrder.setOrderStatus(7);
            armyOrderService.updateOrder(armyOrder);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getComment", method = RequestMethod.POST)
    public Status getVillaComment(HttpServletRequest request,String type){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        List<Comment> comments = commentService.getComment(type);
        if (comments.size()>0){
            Collections.sort(comments,new Comparator<Comment>(){
                public int compare(Comment arg1, Comment arg0) {
                    return arg0.getCommentTime().compareTo(arg1.getCommentTime());
                }
            });
            return Status.success().add("Comment",comments);
        }else{
            return Status.fail(-20,"没有评论");
        }

    }

}
