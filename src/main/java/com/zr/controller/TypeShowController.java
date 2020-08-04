package com.zr.controller;

import com.zr.po.News;
import com.zr.po.NewsQuery;
import com.zr.po.Type;
import com.zr.service.NewsService;
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

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private NewsService newsService;

    @RequestMapping("/types/{id}")
    public String types(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Type> types = typeService.findTop(7);
        if(id==-1){
            id=types.get(0).getId();
        }
        NewsQuery newsQuery=new NewsQuery();
        newsQuery.setTypeId(id.toString());
        Page<News> page = newsService.searchNews(pageable, newsQuery);
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        return "types";
    }

}
