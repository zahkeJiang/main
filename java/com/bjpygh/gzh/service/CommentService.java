package com.bjpygh.gzh.service;

import com.bjpygh.gzh.bean.Comment;
import com.bjpygh.gzh.bean.CommentExample;
import com.bjpygh.gzh.bean.User;
import com.bjpygh.gzh.dao.CommentMapper;
import com.bjpygh.gzh.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    public void putVillaComment(Comment comment) {
        User user = userMapper.selectByPrimaryKey(comment.getUserId());
        comment.setHeadimageurl(user.getHeadimageurl());
        comment.setNickname(user.getNickname());
        comment.setType(1);

        commentMapper.insertSelective(comment);
    }

    public List<Comment> getVillaComment() {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(1);

        return commentMapper.selectByExample(example);
    }
}
