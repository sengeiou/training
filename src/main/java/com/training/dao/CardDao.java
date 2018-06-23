package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * card 数据库操作类
 * Created by huai23 on 2018-06-06 18:46:25.
 */ 
@Service
public class CardDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CardRepository cardRepository;

    /**
     * 新增实体
     * @param card
     * Created by huai23 on 2018-06-06 18:46:25.
     */ 
    public int add(CardEntity card){
        int n = cardRepository.add(card);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-06 18:46:25.
     */ 
    public List<CardEntity> find(CardQuery query , PageRequest page){
        query.setStatus(1);
        List<CardEntity> cardList = cardRepository.find(query,page);
        return cardList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-06 18:46:25.
     */ 
    public Long count(CardQuery query){
        query.setStatus(1);
        Long n = cardRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-06 18:46:25.
     */ 
    public CardEntity getById(String id){
        CardEntity cardDB = cardRepository.getById(id);
        return cardDB;
    }

    /**
     * 根据实体更新
     * @param card
     * Created by huai23 on 2018-06-06 18:46:25.
     */ 
    public int update(CardEntity card){
        int n = cardRepository.update(card);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-06 18:46:25.
     */ 
    public int delete(String id){
        int n = cardRepository.delete(id);
        return n;
    }


}

