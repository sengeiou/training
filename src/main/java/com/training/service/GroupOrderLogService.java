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
 * group_order_log 核心业务操作类
 * Created by huai23 on 2019-01-30 23:16:13.
 */ 
@Service
public class GroupOrderLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderLogDao groupOrderLogDao;

    /**
     * 新增实体
     * @param groupOrderLog
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public ResponseEntity<String> add(GroupOrderLogEntity groupOrderLog){
        User user = RequestContextHelper.getUser();
        int n = groupOrderLogDao.add(groupOrderLog);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public Page<GroupOrderLogEntity> find(GroupOrderLogQuery query , PageRequest page){
        List<GroupOrderLogEntity> groupOrderLogList = groupOrderLogDao.find(query,page);
        Long count = groupOrderLogDao.count(query);
        Page<GroupOrderLogEntity> returnPage = new Page<>();
        returnPage.setContent(groupOrderLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public Long count(GroupOrderLogQuery query){
        Long count = groupOrderLogDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public GroupOrderLogEntity getById(String id){
        GroupOrderLogEntity groupOrderLogDB = groupOrderLogDao.getById(id);
        return groupOrderLogDB;
    }

    /**
     * 根据实体更新
     * @param groupOrderLog
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public  ResponseEntity<String> update(GroupOrderLogEntity groupOrderLog){
        int n = groupOrderLogDao.update(groupOrderLog);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-30 23:16:13.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = groupOrderLogDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

