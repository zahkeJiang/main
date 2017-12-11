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
        updateOrderStatus(comment,ordernumber,userid);
        return Status.success();
    }

    private void updateOrderStatus(Comment comment, String ordernumber,String userid) {
        if (comment.getType()==1){
            VillaOrder villaOrder = villaOrderService.getVillaOrderByNumber(ordernumber).get(0);
            if (villaOrder.getOrderStatus()!=7){
                comment.setUserId(Long.valueOf(userid));
                comment.setProjectName(villaOrder.getVillaName());
                commentService.putComment(comment);
                villaOrder.setOrderStatus(7);
                villaOrderService.updateOrder(villaOrder);
            }
        }else if (comment.getType()==2){
            DsOrder dsOrder = dsOrderService.getDsOrderByNumber(ordernumber).get(0);
            if (dsOrder.getOrderStatus()!=7){
                comment.setUserId(Long.valueOf(userid));
                comment.setProjectName(dsOrder.getDsName());
                commentService.putComment(comment);
                dsOrder.setOrderStatus((byte) 7);
                dsOrderService.updateOrder(dsOrder);
            }
        }else if (comment.getType()==3){
            ArmyOrder armyOrder = armyOrderService.getArmyOrderByNumber(ordernumber).get(0);
            if (armyOrder.getOrderStatus()!=7){
                comment.setUserId(Long.valueOf(userid));
                comment.setProjectName(armyOrder.getArmyName());
                commentService.putComment(comment);
                armyOrder.setOrderStatus(7);
                armyOrderService.updateOrder(armyOrder);
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getComment", method = RequestMethod.POST)
    public Status getComment(HttpServletRequest request,String projectName){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        List<Comment> comments = commentService.getComment(projectName);
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


    @ResponseBody
    @RequestMapping(value = "/getGoodComment", method = RequestMethod.POST)
    public Status getGoodComment(HttpServletRequest request,String projectName){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        return commentService.getGoodComment(projectName);
    }
}
