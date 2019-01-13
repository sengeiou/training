package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * hero_list 数据库操作类
 * Created by huai23 on 2019-01-13 20:32:10.
 */ 
@Service
public class HeroListDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HeroListRepository heroListRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增实体
     * @param heroList
     * Created by huai23 on 2019-01-13 20:32:10.
     */ 
    public int add(HeroListEntity heroList){
        int n = heroListRepository.add(heroList);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-13 20:32:10.
     */ 
    public List<HeroListEntity> find(HeroListQuery query , PageRequest page){
        List<HeroListEntity> heroListList = heroListRepository.find(query,page);
        return heroListList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-13 20:32:10.
     */ 
    public Long count(HeroListQuery query){
        Long n = heroListRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-13 20:32:10.
     */ 
    public HeroListEntity getById(String id){
        HeroListEntity heroListDB = heroListRepository.getById(id);
        return heroListDB;
    }

    /**
     * 根据实体更新
     * @param heroList
     * Created by huai23 on 2019-01-13 20:32:10.
     */ 
    public int update(HeroListEntity heroList){
        int n = heroListRepository.update(heroList);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-13 20:32:10.
     */ 
    public int delete(String id){
        int n = heroListRepository.delete(id);
        return n;
    }

    public String getLastDay(String month) {
        String sql = "select max(hero_date) day from hero_list where hero_date <= ? ";
        List data = jdbcTemplate.queryForList(sql,new Object[]{month+"-31",});
        if(data.size()>0){
            Map item = (Map)data.get(0);
            return item.get("day").toString();
        }
        return null;
    }

}

