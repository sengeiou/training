package com.training.service;

import com.training.dao.*;
import com.training.domain.MemberMedal;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.ut;
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
 * member_medal 核心业务操作类
 * Created by huai23 on 2018-07-24 22:31:46.
 */ 
@Service
public class MemberMedalService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMedalDao memberMedalDao;

    @Autowired
    private MedalDao medalDao;

    /**
     * 新增实体
     * @param memberMedal
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    public ResponseEntity<String> add(MemberMedalEntity memberMedal){
        MemberMedalEntity memberMedalEntity = memberMedalDao.getById(memberMedal.getMemberId(),memberMedal.getMedalId());
        if(memberMedalEntity!=null){
            return ResponseUtil.exception("该成就已获得，不能重复颁发");
        }
        MedalEntity medalEntity = medalDao.getById(memberMedal.getMedalId());
        memberMedal.setAwardDate(ut.currentTime());
        memberMedal.setContent(medalEntity.getContent());
        int n = memberMedalDao.add(memberMedal);
        if(n==1){
            return ResponseUtil.success("添加会员成就成功");
        }
        return ResponseUtil.exception("添加会员成就失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    public Page<MemberMedalEntity> find(MemberMedalQuery query , PageRequest page){
        List<MemberMedalEntity> memberMedalList = memberMedalDao.find(query,page);
        Long count = memberMedalDao.count(query);
        Page<MemberMedalEntity> returnPage = new Page<>();
        returnPage.setContent(memberMedalList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    public Long count(MemberMedalQuery query){
        Long count = memberMedalDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    public MemberMedalEntity getById(String id,String medalId){
        MemberMedalEntity memberMedalDB = memberMedalDao.getById(id,medalId);
        return memberMedalDB;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-24 22:31:46.
     */
    public List<MemberMedal> getByMemberId(String id){
        List<MemberMedalEntity> memberMedalList = memberMedalDao.getByMemberId(id);
        List<MemberMedal> memberMedals = new ArrayList();

        int level_cq = 0;
        int level_tn = 0;
        MemberMedal memberMedalTN = null;
        MemberMedal memberMedalCQ = null;

        for (MemberMedalEntity memberMedalEntity:memberMedalList){
            MemberMedal memberMedal = new MemberMedal();
            BeanUtils.copyProperties(memberMedalEntity,memberMedal);
            MedalEntity medalEntity = medalDao.getById(memberMedalEntity.getMedalId());
            memberMedal.setMedalName(medalEntity.getName());
            memberMedal.setLevel(medalEntity.getLevel());
            if(medalEntity.getMedalId().startsWith("SJ")){
                int level = memberMedal.getLevel();
                if(level>level_tn){
                    memberMedalTN = memberMedal;
                    level_tn = level;
                }
                continue;
            }
            if(medalEntity.getMedalId().startsWith("CQ")){
                int level = memberMedal.getLevel();
                if(level>level_cq){
                    memberMedalCQ = memberMedal;
                    level_cq = level;
                }
                continue;
            }
            memberMedals.add(memberMedal);
        }
        if(memberMedalTN!=null){
            memberMedals.add(memberMedalTN);
        }
        if(memberMedalCQ!=null){
            memberMedals.add(memberMedalCQ);
        }
        return memberMedals;
    }

    /**
     * 根据实体更新
     * @param memberMedal
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    public  ResponseEntity<String> update(MemberMedalEntity memberMedal){
        int n = memberMedalDao.update(memberMedal);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-24 22:31:46.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberMedalDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

