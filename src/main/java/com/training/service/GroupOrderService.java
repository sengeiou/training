package com.training.service;

import com.training.dao.*;
import com.training.domain.GroupOrder;
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
 * group_order 核心业务操作类
 * Created by huai23 on 2019-02-01 20:05:18.
 */ 
@Service
public class GroupOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderDao groupOrderDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private GroupOrderLogDao groupOrderLogDao;

    /**
     * 新增实体
     * @param groupOrder
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public ResponseEntity<String> add(GroupOrderEntity groupOrder){
        User user = RequestContextHelper.getUser();
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
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public Page<GroupOrder> find(GroupOrderQuery query , PageRequest page){
        List<GroupOrderEntity> groupOrderList = groupOrderDao.find(query,page);
        List<GroupOrder> result = new ArrayList();
        for (GroupOrderEntity groupOrderEntity:groupOrderList){
            GroupOrder groupOrder = transferOrder(groupOrderEntity);
            result.add(groupOrder);
        }
        Long count = groupOrderDao.count(query);
        Page<GroupOrder> returnPage = new Page<>();
        returnPage.setContent(result);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    private GroupOrder transferOrder(GroupOrderEntity groupOrderEntity) {
        GroupOrder groupOrder = new GroupOrder();
        BeanUtils.copyProperties(groupOrderEntity,groupOrder);


        return groupOrder;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public Long count(GroupOrderQuery query){
        Long count = groupOrderDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public GroupOrder getById(String id){
        GroupOrderEntity groupOrderDB = groupOrderDao.getById(id);
        GroupOrder groupOrder = transferOrder(groupOrderDB);
        return groupOrder;
    }

    /**
     * 根据实体更新
     * @param groupOrder
     * Created by huai23 on 2019-02-01 20:05:18.
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
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = groupOrderDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

