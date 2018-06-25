package com.training.service;

import com.training.dao.*;
import com.training.domain.Member;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * coach_rest 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:16.
 */ 
@Service
public class CoachRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CoachRestDao coachRestDao;

    @Autowired
    private TrainingDao trainingDao;

    /**
     * 新增实体
     * @param coachRest
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public ResponseEntity<String> add(CoachRestEntity coachRest){
        logger.info(" CoachRestService  add  coachRest = {} ",coachRest);

        Member coach = RequestContextHelper.getMember();
        logger.info(" CoachRestService  add  coach = {} ",coach);

        TrainingQuery query = new TrainingQuery();
        query.setCoachId(coachRest.getCoachId());
        query.setStartDate(ut.currentDate());
        query.setStatus(0);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(1000);
        List<TrainingEntity> trainingList =  trainingDao.find(query,pageRequest);
        logger.info(" =================    list  trainingList = {}",trainingList.size());
        for (TrainingEntity trainingEntity:trainingList){
            logger.info(" =================   trainingEntity = {} ",trainingEntity);
            if(fixRest(trainingEntity,coachRest)){
                return ResponseUtil.exception("已经有学员预约此时段的课程，不能设置休息");
            }
        }
        coachRest.setRestId(IDUtils.getId());
        int n = coachRestDao.add(coachRest);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    private boolean fixRest(TrainingEntity trainingEntity, CoachRestEntity coachRest) {
        if(coachRest.getType().equals(CoachRestTypeEnum.ONCE.getKey())){
            if(!coachRest.getRestDate().equals(trainingEntity.getLessonDate())){
                return false;
            }
        }
        if(coachRest.getType().equals(CoachRestTypeEnum.DAY.getKey())){

        }
        if(coachRest.getType().equals(CoachRestTypeEnum.WEEK.getKey())){
            int index1 = ut.indexOfWeek(coachRest.getRestDate());
            int index2 = ut.indexOfWeek(trainingEntity.getLessonDate());
            if(index1!=index2){
                return false;
            }
        }
        if(coachRest.getType().equals(CoachRestTypeEnum.MONTH.getKey())){
            int index1 = ut.indexOfMonth(coachRest.getRestDate());
            int index2 = ut.indexOfMonth(trainingEntity.getLessonDate());
            if(index1!=index2){
                return false;
            }
        }
        int startHour = trainingEntity.getStartHour();
        int endHour = trainingEntity.getEndHour();
        logger.info(" startHour = {} , endHour = {} ",startHour,endHour);
        logger.info(" coachRest.startHour = {} ,coachRest.endHour  = {} ",coachRest.getStartHour(),coachRest.getEndHour());
        if(coachRest.getStartHour() >= endHour || coachRest.getEndHour() <= startHour){
            logger.info(" ==========  无交集 ========= ");
        }else{
            logger.info(" ==========  有交集 ========= ");
            return true;
        }
        return  false;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public Page<CoachRestEntity> find(CoachRestQuery query , PageRequest page){
        List<CoachRestEntity> coachRestList = coachRestDao.find(query,page);
        Long count = coachRestDao.count(query);
        Page<CoachRestEntity> returnPage = new Page<>();
        returnPage.setContent(coachRestList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public Long count(CoachRestQuery query){
        Long count = coachRestDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public CoachRestEntity getById(String id){
        CoachRestEntity coachRestDB = coachRestDao.getById(id);
        return coachRestDB;
    }

    /**
     * 根据实体更新
     * @param coachRest
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public  ResponseEntity<String> update(CoachRestEntity coachRest){
        int n = coachRestDao.update(coachRest);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:55:16.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = coachRestDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

