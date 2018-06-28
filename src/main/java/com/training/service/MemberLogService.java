package com.training.service;

import com.training.dao.*;
import com.training.domain.Staff;
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
 * member_log 核心业务操作类
 * Created by huai23 on 2018-06-09 09:16:23.
 */ 
@Service
public class MemberLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberLogDao memberLogDao;

    @Autowired
    private StaffDao staffDao;

    /**
     * 新增实体
     * @param memberLog
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public ResponseEntity<String> add(MemberLogEntity memberLog){
        Staff staff = RequestContextHelper.getStaff();
        memberLog.setLogId(IDUtils.getId());
        memberLog.setStaffId(staff.getStaffId());
        int n = memberLogDao.add(memberLog);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public Page<MemberLogEntity> find(MemberLogQuery query , PageRequest page){
        List<MemberLogEntity> memberLogList = memberLogDao.find(query,page);
        for (MemberLogEntity memberLog:memberLogList){
            StaffEntity staffEntity = staffDao.getById(memberLog.getStaffId());
            if(staffEntity!=null){
                memberLog.setStaffName(staffEntity.getCustname());
            }
        }
        Long count = memberLogDao.count(query);
        Page<MemberLogEntity> returnPage = new Page<>();
        returnPage.setContent(memberLogList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public Long count(MemberLogQuery query){
        Long count = memberLogDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public MemberLogEntity getById(String id){
        MemberLogEntity memberLogDB = memberLogDao.getById(id);
        return memberLogDB;
    }

    /**
     * 根据实体更新
     * @param memberLog
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public  ResponseEntity<String> update(MemberLogEntity memberLog){
        int n = memberLogDao.update(memberLog);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-09 09:16:23.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberLogDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

