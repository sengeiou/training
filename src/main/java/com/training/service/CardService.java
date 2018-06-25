package com.training.service;

import com.training.dao.*;
import com.training.domain.Card;
import com.training.domain.MemberCard;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * card 核心业务操作类
 * Created by huai23 on 2018-06-06 18:46:26.
 */ 
@Service
public class CardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CardDao cardDao;

    /**
     * 新增实体
     * @param card
     * Created by huai23 on 2018-06-06 18:46:26.
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
     * Created by huai23 on 2018-06-06 18:46:26.
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
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-06 18:46:26.
     */
    public Page<Card> list(CardQuery query , PageRequest page){
        logger.info(" card.list    query : {} " , query);

        List<CardEntity> cardList = cardDao.find(query,page);

        logger.info(" card.list    cardList.size() : {} " , cardList.size());

        List<Card> content = new ArrayList<>();
        for(CardEntity cardEntity : cardList){
            Card card = transfer(cardEntity);
            content.add(card);
        }
        Long count = cardDao.count(query);
        Page<Card> returnPage = new Page<>();
        returnPage.setContent(content);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    private Card transfer(CardEntity cardEntity) {
        if(cardEntity==null){
            return null;
        }
        Card card = new Card();
        BeanUtils.copyProperties(cardEntity,card);
        String type = "-";
        if(CardTypeEnum.getEnumByKey(card.getType())!=null){
            type = CardTypeEnum.getEnumByKey(card.getType()).getDesc();
        }
        card.setTypeName(type);
        return card;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    public Long count(CardQuery query){
        Long count = cardDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    public CardEntity getById(String id){
        CardEntity cardDB = cardDao.getById(id);
        return cardDB;
    }

    /**
     * 根据实体更新
     * @param card
     * Created by huai23 on 2018-06-06 18:46:26.
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
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = cardDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

