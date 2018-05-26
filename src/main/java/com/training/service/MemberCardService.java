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
 * member_card 核心业务操作类
 * Created by huai23 on 2018-05-26 13:53:17.
 */ 
@Service
public class MemberCardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCardDao memberCardDao;

    /**
     * 新增实体
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public ResponseEntity<String> add(MemberCardEntity memberCard){
        User user = RequestContextHelper.getUser();
        int n = memberCardDao.add(memberCard);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public Page<MemberCardEntity> find(MemberCardQuery query , PageRequest page){
        List<MemberCardEntity> memberCardList = memberCardDao.find(query,page);
        Long count = memberCardDao.count(query);
        Page<MemberCardEntity> returnPage = new Page<>();
        returnPage.setContent(memberCardList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public Long count(MemberCardQuery query){
        Long count = memberCardDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public MemberCardEntity getById(String id){
        MemberCardEntity memberCardDB = memberCardDao.getById(id);
        return memberCardDB;
    }

    /**
     * 根据实体更新
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public  ResponseEntity<String> update(MemberCardEntity memberCard){
        int n = memberCardDao.update(memberCard);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberCardDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

