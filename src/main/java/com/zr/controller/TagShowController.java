package com.zr.controller;

import com.zr.po.News;
import com.zr.po.Tag;
import com.zr.service.NewsService;
import com.zr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private NewsService newsService;

    @RequestMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        List<Tag> tags = tagService.findTop(7);
        if(id==-1){
            id=tags.get(0).getId();
        }
        Page<News> page= newsService.searchNews(pageable,id);
        model.addAttribute("page",page);
        model.addAttribute("tags",tags);
        return "tags";


    }
}
