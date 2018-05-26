package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * card 核心业务操作类
 * Created by huai23 on 2018-05-26 13:53:45.
 */ 
@Service
public class CardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CardDao cardDao;

    /**
     * 新增实体
     * @param card
     * Created by huai23 on 2018-05-26 13:53:45.
     */ 
    public ResponseEntity<String> add(CardEntity card){
        User user = RequestContextHelper.getUser();
        int n = cardDao.add(card);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:53:45.
     */ 
    public Page<CardEntity> find(CardQuery query , PageRequest page){
        List<CardEntity> cardList = cardDao.find(query,page);
        Long count = cardDao.count(query);
        Page<CardEntity> returnPage = new Page<>();
        returnPage.setContent(cardList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:53:45.
     */ 
    public Long count(CardQuery query){
        Long count = cardDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:53:45.
     */ 
    public CardEntity getById(String id){
        CardEntity cardDB = cardDao.getById(id);
        return cardDB;
    }

    /**
     * 根据实体更新
     * @param card
     * Created by huai23 on 2018-05-26 13:53:45.
     */ 
    public  ResponseEntity<String> update(CardEntity card){
        int n = cardDao.update(card);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:53:45.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = cardDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

