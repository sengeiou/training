package com.training.service;

import com.training.dao.*;
import com.training.domain.StaffMedal;
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
 * staff_medal 核心业务操作类
 * Created by huai23 on 2018-07-22 23:28:30.
 */ 
@Service
public class StaffMedalService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StaffMedalDao staffMedalDao;

    @Autowired
    private MedalDao medalDao;

    /**
     * 新增实体
     * @param staffMedal
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public ResponseEntity<String> add(StaffMedalEntity staffMedal){

        StaffMedalEntity staffMedalEntity = staffMedalDao.getById(staffMedal.getStaffId(),staffMedal.getMedalId());
        if(staffMedalEntity!=null){
            return ResponseUtil.exception("该勋章已获得，不能重复颁发");
        }
        MedalEntity medalEntity = medalDao.getById(staffMedal.getMedalId());
        staffMedal.setContent(medalEntity.getContent());
        staffMedal.setAwardDate(ut.currentTime());
        int n = staffMedalDao.add(staffMedal);
        if(n==1){
            return ResponseUtil.success("颁发勋章成功");
        }
        return ResponseUtil.exception("颁发勋章失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public Page<StaffMedalEntity> find(StaffMedalQuery query , PageRequest page){
        query.setStatus(0);
        List<StaffMedalEntity> staffMedalList = staffMedalDao.find(query,page);
        Long count = staffMedalDao.count(query);
        Page<StaffMedalEntity> returnPage = new Page<>();
        returnPage.setContent(staffMedalList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public Long count(StaffMedalQuery query){
        Long count = staffMedalDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public StaffMedalEntity getById(String id,String medalId){
        StaffMedalEntity staffMedalDB = staffMedalDao.getById(id,medalId);
        return staffMedalDB;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-22 23:28:30.
     */
    public List<StaffMedal> getByStaffId(String id){
        List<StaffMedalEntity> staffMedalEntityList = staffMedalDao.getByStaffId(id);
        List<StaffMedal> staffMedals = new ArrayList();
        for (StaffMedalEntity staffMedalEntity:staffMedalEntityList){
            if(staffMedalEntity.getStatus()<0){
                continue;
            }
            StaffMedal staffMedal = new StaffMedal();
            BeanUtils.copyProperties(staffMedalEntity,staffMedal);
            MedalEntity medalEntity = medalDao.getById(staffMedalEntity.getMedalId());
            staffMedal.setMedalName(medalEntity.getName());
            staffMedals.add(staffMedal);
        }
        return staffMedals;
    }

    /**
     * 根据实体更新
     * @param staffMedal
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public  ResponseEntity<String> update(StaffMedalEntity staffMedal){
        int n = staffMedalDao.update(staffMedal);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-22 23:28:30.
     */ 
    public ResponseEntity<String> delete(String id,String medalId){
        int n = staffMedalDao.delete(id,medalId);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

