package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * member_coupon 数据库操作类
 * Created by huai23 on 2018-06-30 10:02:47.
 */ 
@Service
public class MemberCouponDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    /**
     * 新增实体
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public int add(MemberCouponEntity memberCoupon){
        int n = memberCouponRepository.add(memberCoupon);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public List<MemberCouponEntity> find(MemberCouponQuery query , PageRequest page){
        List<MemberCouponEntity> memberCouponList = memberCouponRepository.find(query,page);
        return memberCouponList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public Long count(MemberCouponQuery query){
        Long n = memberCouponRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public MemberCouponEntity getById(String id){
        MemberCouponEntity memberCouponDB = memberCouponRepository.getById(id);
        return memberCouponDB;
    }

    /**
     * 根据实体更新
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public int update(MemberCouponEntity memberCoupon){
        int n = memberCouponRepository.update(memberCoupon);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public int delete(String id){
        int n = memberCouponRepository.delete(id);
        return n;
    }


}

