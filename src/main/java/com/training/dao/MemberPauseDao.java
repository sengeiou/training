package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * member_pause 数据库操作类
 * Created by huai23 on 2018-06-27 20:52:15.
 */ 
@Service
public class MemberPauseDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberPauseRepository memberPauseRepository;

    /**
     * 新增实体
     * @param memberPause
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public int add(MemberPauseEntity memberPause){
        String pauseDate = memberPause.getPauseDate();
        if(StringUtils.isNotEmpty(pauseDate) && pauseDate.length()>10){
            memberPause.setPauseDate(pauseDate.substring(0,10));
        }
        int n = memberPauseRepository.add(memberPause);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public List<MemberPauseEntity> find(MemberPauseQuery query , PageRequest page){
        List<MemberPauseEntity> memberPauseList = memberPauseRepository.find(query,page);
        return memberPauseList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public Long count(MemberPauseQuery query){
        Long n = memberPauseRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public MemberPauseEntity getById(String id){
        MemberPauseEntity memberPauseDB = memberPauseRepository.getById(id);
        return memberPauseDB;
    }

    /**
     * 根据实体更新
     * @param memberPause
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public int update(MemberPauseEntity memberPause){
        int n = memberPauseRepository.update(memberPause);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public int delete(String id){
        int n = memberPauseRepository.delete(id);
        return n;
    }


}

