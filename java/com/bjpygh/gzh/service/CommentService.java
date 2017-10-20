package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.Comment;
import com.bjpygh.gzh.bean.CommentExample;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.dao.CommentMapper;
import com.bjpygh.gzh.dao.UserMapper;
import com.bjpygh.gzh.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    public void putComment(Comment comment) {
        User user = userMapper.selectByPrimaryKey(comment.getUserId());
        comment.setHeadimageurl(user.getHeadimageurl());
        comment.setNickname(user.getNickname());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        comment.setCommentTime(formatter.format(new Date()));
        commentMapper.insertSelective(comment);
    }

    public List<Comment> getComment(String type) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(Integer.valueOf(type));

        return commentMapper.selectByExample(example);
    }

    public Status getGoodComment(String type) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(Integer.valueOf(type));
        List<Comment> comments = commentMapper.selectByExample(example);
        for (Comment c : comments){
            if (c.getStayStar()>3&&c.getSupportStar()>3&&c.getEnterStar()>3)
                return Status.success().add("comment",c);
        }
        return Status.fail(-20,"没有评论");
    }
}
