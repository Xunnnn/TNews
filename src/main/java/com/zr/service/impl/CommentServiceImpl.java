package com.zr.service.impl;

import com.zr.dao.CommentDao;
import com.zr.po.Comment;
import com.zr.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public void save(Comment comment) {
        if(comment.getParentComment().getId()==-1){
            comment.setParentComment(null);
        }
        commentDao.save(comment);
    }

    @Override
    public List<Comment> findCommentByNewsId(Long newsId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments=commentDao.findByNewsIdAndParentCommentNull(newsId,sort);
        return comments;
    }
}
