package com.zr.dao;


import com.zr.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {
    List<Comment> findByNewsIdAndParentCommentNull(Long newsId, Sort sort);
}
