package com.zr.controller;

import com.zr.po.Comment;
import com.zr.po.User;
import com.zr.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public String save(Comment comment, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user==null){
            comment.setAdminComment(false);
        }else {
            comment.setAdminComment(true);
        }
        commentService.save(comment);
        Long newsId=comment.getNews().getId();
        return "redirect:/comments/"+newsId;
    }

    @RequestMapping("/comments/{newsId}")
    public String comments(@PathVariable Long newsId, Model model){
        List<Comment> comments=commentService.findCommentByNewsId(newsId);
        model.addAttribute("comments",comments);
        return "news :: commentList";

    }
}
