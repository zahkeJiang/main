package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.Comment;
import com.bjpygh.gzh.entity.Status;
import com.bjpygh.gzh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController extends BaseController{

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/putVillaComment", method = RequestMethod.POST)
    public Status putComment(Comment comment,HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        String userid = userMap.get("id");
        comment.setUserId(Long.valueOf(userid));
        commentService.putVillaComment(comment);
        return Status.success();
    }

    @ResponseBody
    @RequestMapping(value = "/getVillaComment", method = RequestMethod.POST)
    public Status getVillaComment(HttpServletRequest request){
        Map<String, String> userMap = checkWxUser(request);
        if(userMap == null){
            return Status.notInWx();
        }
        List<Comment> villaComment = commentService.getVillaComment();
        if (villaComment.size()>0){
            return Status.success().add("villaComment",villaComment);
        }else{
            return Status.fail(-20,"没有评论");
        }

    }

}
