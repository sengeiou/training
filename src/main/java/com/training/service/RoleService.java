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
 * role 核心业务操作类
 * Created by huai23 on 2018-06-27 15:28:01.
 */ 
@Service
public class RoleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleDao roleDao;

    /**
     * 新增实体
     * @param role
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public ResponseEntity<String> add(RoleEntity role){
        User user = RequestContextHelper.getUser();
        int n = roleDao.add(role);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public Page<RoleEntity> find(RoleQuery query , PageRequest page){
        List<RoleEntity> roleList = roleDao.find(query,page);
        Long count = roleDao.count(query);
        Page<RoleEntity> returnPage = new Page<>();
        returnPage.setContent(roleList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public Long count(RoleQuery query){
        Long count = roleDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public RoleEntity getById(String id){
        RoleEntity roleDB = roleDao.getById(id);
        return roleDB;
    }

    /**
     * 根据实体更新
     * @param role
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public  ResponseEntity<String> update(RoleEntity role){
        int n = roleDao.update(role);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-27 15:28:01.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = roleDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

