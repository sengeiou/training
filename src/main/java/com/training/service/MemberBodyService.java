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
 * member_body 核心业务操作类
 * Created by huai23 on 2018-05-26 13:54:03.
 */ 
@Service
public class MemberBodyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberBodyDao memberBodyDao;

    /**
     * 新增实体
     * @param memberBody
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public ResponseEntity<String> add(MemberBodyEntity memberBody){
        User user = RequestContextHelper.getUser();
        memberBody.setBodyId(IDUtils.getId());
        int n = memberBodyDao.add(memberBody);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public Page<MemberBodyEntity> find(MemberBodyQuery query , PageRequest page){
        logger.info("  MemberBody find  MemberQuery = {} , page = {} ",query,page);
        List<MemberBodyEntity> memberBodyList = memberBodyDao.find(query,page);
        Long count = memberBodyDao.count(query);
        Page<MemberBodyEntity> returnPage = new Page<>();
        returnPage.setContent(memberBodyList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public Long count(MemberBodyQuery query){
        Long count = memberBodyDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public MemberBodyEntity getById(String id){
        MemberBodyEntity memberBodyDB = memberBodyDao.getById(id);
        return memberBodyDB;
    }

    /**
     * 根据实体更新
     * @param memberBody
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public  ResponseEntity<String> update(MemberBodyEntity memberBody){
        int n = memberBodyDao.update(memberBody);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:03.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberBodyDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

