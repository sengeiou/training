package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * group_order 核心业务操作类
 * Created by huai23 on 2019-01-30 22:53:43.
 */ 
@Service
public class GroupOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderDao groupOrderDao;

    /**
     * 新增实体
     * @param groupOrder
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public ResponseEntity<String> add(GroupOrderEntity groupOrder){
        User user = RequestContextHelper.getUser();
        groupOrder.setOrderId(IDUtils.getId());
        int n = groupOrderDao.add(groupOrder);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public Page<GroupOrderEntity> find(GroupOrderQuery query , PageRequest page){
        List<GroupOrderEntity> groupOrderList = groupOrderDao.find(query,page);
        Long count = groupOrderDao.count(query);
        Page<GroupOrderEntity> returnPage = new Page<>();
        returnPage.setContent(groupOrderList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public Long count(GroupOrderQuery query){
        Long count = groupOrderDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public GroupOrderEntity getById(String id){
        GroupOrderEntity groupOrderDB = groupOrderDao.getById(id);
        return groupOrderDB;
    }

    /**
     * 根据实体更新
     * @param groupOrder
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public  ResponseEntity<String> update(GroupOrderEntity groupOrder){
        int n = groupOrderDao.update(groupOrder);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-30 22:53:43.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = groupOrderDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

