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
 * measurement 核心业务操作类
 * Created by huai23 on 2019-01-22 21:54:18.
 */ 
@Service
public class MeasurementService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MeasurementDao measurementDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 新增实体
     * @param measurement
     * Created by huai23 on 2019-01-22 21:54:18.
     */ 
    public ResponseEntity<String> add(MeasurementEntity measurement){
        User user = RequestContextHelper.getUser();
        int n = measurementDao.add(measurement);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-22 21:54:18.
     */ 
    public Page<MeasurementEntity> find(MeasurementQuery query , PageRequest page){
        List<MeasurementEntity> measurementList = measurementDao.find(query,page);
        Long count = measurementDao.count(query);
        Page<MeasurementEntity> returnPage = new Page<>();
        returnPage.setContent(measurementList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-22 21:54:18.
     */ 
    public Long count(MeasurementQuery query){
        Long count = measurementDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-22 21:54:18.
     */ 
    public MeasurementEntity getById(String id){
        MeasurementEntity measurementDB = measurementDao.getById(id);
        if(measurementDB!=null&&measurementDB.getMemberId()!=null){
            MemberEntity memberEntity = memberDao.getById(measurementDB.getMemberId());
            if(memberEntity!=null){
                measurementDB.setMemberImage(memberEntity.getImage());
            }else{
                measurementDB.setMemberImage("");
            }
        }
        return measurementDB;
    }

    /**
     * 根据实体更新
     * @param measurement
     * Created by huai23 on 2019-01-22 21:54:18.
     */ 
    public  ResponseEntity<String> update(MeasurementEntity measurement){
        int n = measurementDao.update(measurement);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-22 21:54:18.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = measurementDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

