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
 * member_medal 核心业务操作类
 * Created by huai23 on 2018-05-26 13:54:40.
 */ 
@Service
public class MemberMedalService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMedalDao memberMedalDao;

    /**
     * 新增实体
     * @param memberMedal
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public ResponseEntity<String> add(MemberMedalEntity memberMedal){
        User user = RequestContextHelper.getUser();
        int n = memberMedalDao.add(memberMedal);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public Page<MemberMedalEntity> find(MemberMedalQuery query , PageRequest page){
        List<MemberMedalEntity> memberMedalList = memberMedalDao.find(query,page);
        Long count = memberMedalDao.count(query);
        Page<MemberMedalEntity> returnPage = new Page<>();
        returnPage.setContent(memberMedalList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public Long count(MemberMedalQuery query){
        Long count = memberMedalDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public MemberMedalEntity getById(String id){
        MemberMedalEntity memberMedalDB = memberMedalDao.getById(id);
        return memberMedalDB;
    }

    /**
     * 根据实体更新
     * @param memberMedal
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public  ResponseEntity<String> update(MemberMedalEntity memberMedal){
        int n = memberMedalDao.update(memberMedal);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:40.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberMedalDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

