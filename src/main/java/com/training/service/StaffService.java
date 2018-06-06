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
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class StaffService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StaffDao staffDao;

    /**
     * 新增实体
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public ResponseEntity<String> add(StaffEntity staff){
        User user = RequestContextHelper.getUser();
        int n = staffDao.add(staff);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public Page<StaffEntity> find(StaffQuery query , PageRequest page){
        List<StaffEntity> staffList = staffDao.find(query,page);
        Long count = staffDao.count(query);
        Page<StaffEntity> returnPage = new Page<>();
        returnPage.setContent(staffList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public Long count(StaffQuery query){
        Long count = staffDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public StaffEntity getById(String id){
        StaffEntity staffDB = staffDao.getById(id);
        return staffDB;
    }

    /**
     * 根据实体更新
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public  ResponseEntity<String> update(StaffEntity staff){
        int n = staffDao.update(staff);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = staffDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    public StaffEntity getByUsername(String username){
        StaffEntity staffDB = staffDao.getByUsername(username);
        return staffDB;
    }


}

