package com.zr.dao;

import com.zr.po.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsDao extends JpaRepository<News,Long> , JpaSpecificationExecutor<News> {
    @Query("select function('date_format',n.updateTime,'%Y') as year from News n group by function('date_format',n.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select n from News n where function('date_format',n.updateTime,'%Y') = ?1")
    List<News> findByYear(String y);

    @Query("select n from News n where n.title like ?1 or n.content like ?1")
    Page<News> findByquery(String s, Pageable pageable);

    @Query("select n from News n where n.recommend=true")
    List<News> findTop(Pageable pageable);
}
