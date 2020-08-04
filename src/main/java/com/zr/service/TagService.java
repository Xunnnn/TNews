package com.zr.service;

import com.zr.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Page<Tag> listTag(Pageable pageable);

    void delete(Long id);

    Tag findTagById(Long id);

    void input(Tag tag);

    List<Tag> listTag();

    List<Tag> findTagByTagId(String tagIds);

    String getTagIds(List<Tag> tags);

    List<Tag> findTop(int i);
}
