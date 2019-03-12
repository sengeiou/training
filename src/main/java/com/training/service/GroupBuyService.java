package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * group_buy 核心业务操作类
 * Created by huai23 on 2019-01-30 22:53:15.
 */ 
@Service
public class GroupBuyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupBuyDao groupBuyDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增实体
     * @param groupBuy
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public ResponseEntity<String> add(GroupBuyEntity groupBuy){
        logger.info("content:{}",groupBuy.getContent().length());
        if(groupBuy.getContent()!=null&&groupBuy.getContent().length()>400000){
            return ResponseUtil.exception("图片太大，请更换小尺寸图片，图片大小限制在300k以内");
        }
        logger.info(" group_buyRestController  add  groupBuy = {}",groupBuy);
        String id = IDUtils.getId();
        groupBuy.setBuyId(id);
        groupBuy.setShareUrl("http://cloud.heyheroes.com/od/"+id+"?from=singlemessage&isappinstalled=0");
        int n = groupBuyDao.add(groupBuy);
        if(n==1){
            if(groupBuy.getMainTag()!=null&&groupBuy.getMainTag().equals("1")){
                jdbcTemplate.update("update group_buy set main_tag = '0' where buy_id <> '"+id+"' ");
            }
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public Page<GroupBuyEntity> find(GroupBuyQuery query , PageRequest page){
        List<GroupBuyEntity> groupBuyList = groupBuyDao.find(query,page);
        Long count = groupBuyDao.count(query);
        Page<GroupBuyEntity> returnPage = new Page<>();
        returnPage.setContent(groupBuyList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public Long count(GroupBuyQuery query){
        Long count = groupBuyDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public GroupBuyEntity getById(String id){
        GroupBuyEntity groupBuyDB = groupBuyDao.getById(id);
        return groupBuyDB;
    }

    /**
     * 根据实体更新
     * @param groupBuy
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public  ResponseEntity<String> update(GroupBuyEntity groupBuy){
        logger.info("content:{}",groupBuy.getContent().length());
        if(groupBuy.getContent()!=null&&groupBuy.getContent().length()>400000){
            return ResponseUtil.exception("图片太大，请更换小尺寸图片，图片大小限制在300k以内");
        }
        int n = groupBuyDao.update(groupBuy);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-30 22:53:15.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = groupBuyDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

