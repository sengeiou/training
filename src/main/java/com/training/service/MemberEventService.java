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
 * member_event 核心业务操作类
 * Created by huai23 on 2018-07-31 07:36:32.
 */ 
@Service
public class MemberEventService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberEventDao memberEventDao;

    /**
     * 新增实体
     * @param memberEvent
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public ResponseEntity<String> add(MemberEventEntity memberEvent){
        User user = RequestContextHelper.getUser();
        int n = memberEventDao.add(memberEvent);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public Page<MemberEventEntity> find(MemberEventQuery query , PageRequest page){
        List<MemberEventEntity> memberEventList = memberEventDao.find(query,page);
        Long count = memberEventDao.count(query);
        Page<MemberEventEntity> returnPage = new Page<>();
        returnPage.setContent(memberEventList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public Long count(MemberEventQuery query){
        Long count = memberEventDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public MemberEventEntity getById(String id){
        MemberEventEntity memberEventDB = memberEventDao.getById(id);
        return memberEventDB;
    }

    /**
     * 根据实体更新
     * @param memberEvent
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public  ResponseEntity<String> update(MemberEventEntity memberEvent){
        int n = memberEventDao.update(memberEvent);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-31 07:36:32.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberEventDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

