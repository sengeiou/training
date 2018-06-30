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
 * member_coupon 核心业务操作类
 * Created by huai23 on 2018-06-30 10:02:47.
 */ 
@Service
public class MemberCouponService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCouponDao memberCouponDao;

    /**
     * 新增实体
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public ResponseEntity<String> add(MemberCouponEntity memberCoupon){
        User user = RequestContextHelper.getUser();
        int n = memberCouponDao.add(memberCoupon);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public Page<MemberCouponEntity> find(MemberCouponQuery query , PageRequest page){
        List<MemberCouponEntity> memberCouponList = memberCouponDao.find(query,page);
        Long count = memberCouponDao.count(query);
        Page<MemberCouponEntity> returnPage = new Page<>();
        returnPage.setContent(memberCouponList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public Long count(MemberCouponQuery query){
        Long count = memberCouponDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public MemberCouponEntity getById(String id){
        MemberCouponEntity memberCouponDB = memberCouponDao.getById(id);
        return memberCouponDB;
    }

    /**
     * 根据实体更新
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public  ResponseEntity<String> update(MemberCouponEntity memberCoupon){
        int n = memberCouponDao.update(memberCoupon);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberCouponDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

