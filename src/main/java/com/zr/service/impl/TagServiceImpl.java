package com.zr.service.impl;

import com.zr.dao.TagDao;
import com.zr.po.Tag;
import com.zr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        tagDao.deleteById(id);
    }

    @Override
    public Tag findTagById(Long id) {
        return tagDao.getOne(id);
    }

    @Override
    public void input(Tag tag) {
        tagDao.save(tag);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> findTagByTagId(String tagIds) {
        //1,2,3
        List<Long> ids=new ArrayList<>();
        if(!StringUtils.isEmpty(tagIds)){
            String[] strings = tagIds.split(",");
            for (String s:strings){
                if(!StringUtils.isEmpty(s)){
                    ids.add(new Long(s));
                }
            }
        }
        return tagDao.findAllById(ids);
    }

    @Override
    public String getTagIds(List<Tag> tags) {
        //1 2 3 ===>1,2,3
        StringBuffer ids=new StringBuffer();
        if(!tags.isEmpty()){
            boolean flag=false;
            for(Tag t:tags){
                if(flag){
                    ids.append(",");
                    ids.append(t.getId());
                }else {
                    ids.append(t.getId());
                    flag=true;
                }
            }
        }
        return ids.toString();
    }

    @Override
    public List<Tag> findTop(int i) {
        Sort sort=Sort.by(Sort.Direction.DESC,"newsList.size");
        Pageable pageable = PageRequest.of(0, i, sort);
        return tagDao.findTop(pageable);
    }
}
