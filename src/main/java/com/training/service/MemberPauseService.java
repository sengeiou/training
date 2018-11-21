package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * member_pause 核心业务操作类
 * Created by huai23 on 2018-06-27 20:52:15.
 */ 
@Service
public class MemberPauseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberPauseDao memberPauseDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberCardDao memberCardDao;

    /**
     * 新增实体
     * @param memberPause
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public ResponseEntity<String> add(MemberPauseEntity memberPause){
        User user = RequestContextHelper.getUser();
        int n = memberPauseDao.add(memberPause);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public Page<MemberPauseEntity> find(MemberPauseQuery query , PageRequest page){
        List<MemberPauseEntity> memberPauseList = memberPauseDao.find(query,page);
        for (MemberPauseEntity memberPauseEntity : memberPauseList){
            String memberId = memberPauseEntity.getMemberId();
            String pauseStaffId = memberPauseEntity.getPauseStaffId();
            String restoreStaffId = memberPauseEntity.getRestoreStaffId();
            MemberEntity memberEntity = memberDao.getById(memberId);
            memberPauseEntity.setName(memberEntity.getName());
            memberPauseEntity.setPhone(memberEntity.getPhone());
            StoreEntity storeEntity = storeDao.getById(memberEntity.getStoreId());
            if(storeEntity!=null){
                memberPauseEntity.setStoreId(storeEntity.getStoreId());
                memberPauseEntity.setStoreName(storeEntity.getName());
            }else{
                memberPauseEntity.setStoreId("");
                memberPauseEntity.setStoreName("");
            }
            if(StringUtils.isNotEmpty(pauseStaffId)){
                StaffEntity pauseStaffEntity = staffDao.getById(pauseStaffId);
                memberPauseEntity.setPauseStaffName(pauseStaffEntity.getCustname());
            }else{
                memberPauseEntity.setPauseStaffName(" ");
            }

            if(StringUtils.isNotEmpty(restoreStaffId)){
                StaffEntity restoreStaffEntity = staffDao.getById(restoreStaffId);
                memberPauseEntity.setRestoreStaffName(restoreStaffEntity.getCustname());
            }else{
                memberPauseEntity.setRestoreStaffName(" ");
            }

            if(memberPauseEntity.getStatus()==1){
                memberPauseEntity.setShowStatus("停课中");
            }else if(memberPauseEntity.getStatus()==0){
                memberPauseEntity.setShowStatus("已复课");
            }

        }
        Long count = memberPauseDao.count(query);
        Page<MemberPauseEntity> returnPage = new Page<>();
        returnPage.setContent(memberPauseList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public Long count(MemberPauseQuery query){
        Long count = memberPauseDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public MemberPauseEntity getById(String id){
        MemberPauseEntity memberPauseDB = memberPauseDao.getById(id);
        return memberPauseDB;
    }

    /**
     * 根据实体更新
     * @param memberPause
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public  ResponseEntity<String> update(MemberPauseEntity memberPause){
        int n = memberPauseDao.update(memberPause);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-27 20:52:15.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberPauseDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

