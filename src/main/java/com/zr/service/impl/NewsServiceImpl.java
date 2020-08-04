package com.zr.service.impl;

import com.zr.dao.NewsDao;
import com.zr.po.News;
import com.zr.po.NewsQuery;
import com.zr.po.Type;
import com.zr.service.NewsService;
import com.zr.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;

    @Override
    public Page<News> findByPageable(Pageable pageable) {
        return newsDao.findAll(pageable);
    }

    @Override
    public void input(News news) {
        if (news.getId()==null){
            news.setCreateTime(new Date());
            news.setUpdateTime(new Date());
            newsDao.save(news);
        }else {
            news.setUpdateTime(new Date());
            News n = newsDao.getOne(news.getId());
            BeanUtils.copyProperties(news,n, MyBeanUtils.getNullPropertyNames(news));
            newsDao.save(n);
        }

    }

    @Override
    public News findNewsById(Long id) {
        return newsDao.getOne(id);
    }

    @Override
    public Page<News> searchNews(Pageable pageable, NewsQuery newsQuery) {
        Page<News> news=newsDao.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                if(!StringUtils.isEmpty(newsQuery.getTitle())){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+newsQuery.getTitle()+"%"));
                }
                if(!StringUtils.isEmpty(newsQuery.getTypeId())){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),newsQuery.getTypeId()));
                }
                if(newsQuery.getRecommend()!=null){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),newsQuery.getRecommend()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
        return news;
    }

    @Override
    public Page<News> searchNews(Pageable pageable, Long id) {
        return newsDao.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join=root.join("tags");
                return criteriaBuilder.equal(join.get("id"),id);
            }
        }, pageable);
    }

    @Override
    public void deleteById(Long id) {
        newsDao.deleteById(id);
    }

    @Override
    public Long countNews() {
        return newsDao.count();
    }

    @Override
    public HashMap<String, List<News>> archiveNews() {
        LinkedHashMap<String, List<News>> map=new LinkedHashMap<>();
        List<String> years=newsDao.findGroupYear();
        for(String y:years){
            List<News> news=newsDao.findByYear(y);
            map.put(y,news);
        }
        return map;
    }

    @Override
    public Page<News> findNewsByQuery(String query, Pageable pageable) {
        return newsDao.findByquery("%"+query+"%",pageable);
    }

    @Override
    public List<News> findTop(int i) {
        Sort sort= Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, i, sort);
        return newsDao.findTop(pageable);
    }


}
