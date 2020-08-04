package com.zr.controller;

import com.zr.po.*;
import com.zr.service.NewsService;
import com.zr.service.TagService;
import com.zr.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    @RequestMapping
    public String list(@PageableDefault(size = 5,sort={"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                       Model model){
        Page<News> page= newsService.findByPageable(pageable);
        model.addAttribute("page",page);
        model.addAttribute("types",typeService.listType());
        return "admin/news";
    }

    @RequestMapping("input/{id}")
    public String toInput(@PathVariable Long id,Model model){
        if(id==-1){
            model.addAttribute("news",new News());
        }else {
            News news= newsService.findNewsById(id);
            String tagIds=tagService.getTagIds(news.getTags());
            news.setTagIds(tagIds);
            model.addAttribute("news",news);
        }
        List<Type> types=typeService.listType();
        model.addAttribute("types",types);
        model.addAttribute("tags",tagService.listTag());

        return "admin/news-input";
    }
    @RequestMapping("input")
    public String input(News news, HttpSession session){
        User user= (User) session.getAttribute("user");
        news.setUser(user);
        List<Tag> tags=tagService.findTagByTagId(news.getTagIds());
        news.setTags(tags);
        newsService.input(news);
        return "redirect:/admin/news";
    }

    @RequestMapping("search")
    public String search(@PageableDefault(size = 5,sort={"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                         NewsQuery newsQuery,
                         Model model){
      Page<News> page= newsService.searchNews(pageable,newsQuery);
      model.addAttribute("page",page);
        return "admin/news :: newsList";
    }

    @RequestMapping("delete/{id}")
    public String delete(@PathVariable Long id){
        newsService.deleteById(id);
        return "redirect:/admin/news";
    }

}
