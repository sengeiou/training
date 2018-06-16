package com.training.service;

import com.alibaba.fastjson.JSONObject;
import com.training.dao.*;
import com.training.domain.Lesson;
import com.training.domain.Member;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
import org.apache.commons.lang.StringUtils;
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
 * lesson 核心业务操作类
 * Created by huai23 on 2018-05-26 17:02:19.
 */ 
@Service
public class LessonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private TrainingService trainingService;

    /**
     * 新增实体
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public ResponseEntity<String> add(LessonEntity lesson){
        User user = RequestContextHelper.getUser();
        int n = lessonDao.add(lesson);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public Page<LessonEntity> find(LessonQuery query , PageRequest page){
        List<LessonEntity> lessonList = lessonDao.find(query,page);
        Long count = lessonDao.count(query);
        Page<LessonEntity> returnPage = new Page<>();
        returnPage.setContent(lessonList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public Long count(LessonQuery query){
        Long count = lessonDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public LessonEntity getById(String id){
        LessonEntity lessonDB = lessonDao.getById(id);
        return lessonDB;
    }

    /**
     * 根据实体更新
     * @param lesson
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public  ResponseEntity<String> update(LessonEntity lesson){
        int n = lessonDao.update(lesson);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:02:19.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = lessonDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    public ResponseEntity<String> schedule(LessonQuery query) {
        if(StringUtils.isEmpty(query.getCoachId())){
            return ResponseUtil.exception("教练信息缺失,查询课程时间表异常!");
        }
        List<Lesson> lessonList = new ArrayList();
        if(query.getType().equals("T")){
            lessonList = quertTeamSchedule(query);
        }
        if(query.getType().startsWith("S")){
            lessonList = quertSpecialSchedule(query);
        }
        if(query.getType().startsWith("P")){
            lessonList = quertPersonalSchedule(query);
        }
        JSONObject jo = new JSONObject();
        jo.put("lessonList", lessonList);
        return ResponseUtil.success("查询课程时间表成功",lessonList);
    }

    private List<Lesson> quertPersonalSchedule(LessonQuery query) {
        List<Lesson> lessonList = new ArrayList();
        if(StringUtils.isEmpty(query.getCoachId())){
            return lessonList;
        }
        Member memberRequest = RequestContextHelper.getMember();
        if(StringUtils.isEmpty(query.getMemberId())){
            query.setMemberId(memberRequest.getMemberId());
        }
        logger.info(" quertPersonalSchedule  memberRequest = {} ",memberRequest);
        logger.info(" quertPersonalSchedule  query = {} ",query);
        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(query.getLessonDate());
        trainingQuery.setCoachId(query.getCoachId());
        trainingQuery.setStatus(0);
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);
        logger.info(" quertPersonalSchedule  trainingEntityList.size() = {} ",trainingEntityList.size());
//        for (TrainingEntity trainingEntity:trainingEntityList){
//            logger.info(" trainingEntity = {} ",trainingEntity);
//        }
        for (int i = 9; i < 22; i++) {
            Lesson lesson = new Lesson();
            lesson.setType("P");
            lesson.setStartHour(i);
            lesson.setEndHour(i+1);
            lesson.setCoachId(query.getCoachId());
            lesson.setLessonDate(query.getLessonDate());
            lesson.setLessonId(createLessonId(lesson));
            lesson.setMyOrder("0");
            lesson.setQuota(1);
            lesson.setTrainingId("");
            lesson.setTrainingList(new ArrayList<>());
            for (TrainingEntity trainingEntity:trainingEntityList){
                logger.info(" ***********  getLessonDate = {} ,  getMemberId = {} ,  trainingEntity = {} ",trainingEntity.getLessonDate() , query.getMemberId() , trainingEntity.getMemberId());
                if(trainingEntity.getStartHour()==lesson.getStartHour()){
                    lesson.setQuota(0);
                    if(trainingEntity.getMemberId().equals(query.getMemberId())){
                        lesson.setMyOrder("1");
                        lesson.getTrainingList().add(trainingService.transferTraining(trainingEntity));
                        lesson.setTrainingId(trainingEntity.getTrainingId());
                    }
                }
            }
            lesson.setStatus(0);
//            if(i%3==0){
//                lesson.setStatus(-1);
//            }
            lessonList.add(lesson);
        }

        for (Lesson lesson:lessonList){
//            logger.info(" lesson = {} ",lesson);
        }

        return lessonList;
    }

    private String createLessonId(Lesson lesson) {
//        logger.info(" lesson = {} ",lesson);
        String lessonId = ""+lesson.getType()+","+lesson.getLessonDate()+","+lesson.getStartHour()+","+lesson.getEndHour()+","+lesson.getCoachId();
//        logger.info(" lessonId = {} ",lessonId);
        return lessonId;
    }

    public List<Lesson> quertTeamSchedule(LessonQuery query) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" quertTeamSchedule  memberRequest = {}",memberRequest);
        List<Lesson> lessonList = new ArrayList();
        for (int i = 0; i < 0; i++) {
            Lesson lesson = new Lesson();
            lesson.setLessonId(System.currentTimeMillis()+"");
            lesson.setTitle("肌肉训练+"+i);
            lesson.setCoachName("詹姆斯");
            lesson.setStartHour(11+i);
            lesson.setEndHour(12+1);
            lesson.setCoachId("1");
            lesson.setLessonDate(query.getLessonDate());
            lesson.setQuota(4);
            lesson.setMaxCount(8);
            lesson.setMinCount(3);
            lessonList.add(lesson);
        }
       return lessonList;
    }

    public List<Lesson> quertSpecialSchedule(LessonQuery query) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" quertSpecialSchedule  memberRequest = {}",memberRequest);
        List<Lesson> lessonList = new ArrayList();
        for (int i = 9; i < 22; i++) {
            Lesson lesson = new Lesson();
            lesson.setTitle("肌肉训练课"+query.getType());
            lesson.setLessonId(System.currentTimeMillis()+"");
            lesson.setStartHour(i);
            lesson.setEndHour(i+1);
            lesson.setCoachId("1");
            lesson.setLessonDate(query.getLessonDate());
            if(i%5==0){
                lesson.setQuota(0);
            }else{
                lesson.setQuota(1);
            }
            if(i%3==0){
                lesson.setStatus(-1);
            }else{
                lesson.setStatus(0);
            }
            lessonList.add(lesson);
        }
        return lessonList;
    }

    public ResponseEntity<String> order(Lesson lesson) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" order  lesson = {}",lesson);
        if(StringUtils.isEmpty(lesson.getMemberId())){
            lesson.setMemberId(memberRequest.getMemberId());
        }
        String lessonId = lesson.getLessonId();
        String[] ids = lessonId.split(",");
        String type = ids[0];
        String lessonDate = ids[1];
        String startHour = ids[2];
        String endHour = ids[3];
        String coachId = ids[4];

        MemberCardEntity memberCardEntity = memberCardService.getCurrentUseCard(lesson.getMemberId(),type);
        if(memberCardEntity==null){
            return ResponseUtil.exception("约课失败!无可用课程卡!请先购卡");
        }
        TrainingEntity  trainingEntity = new TrainingEntity();
        trainingEntity.setTrainingId(IDUtils.getId());
        trainingEntity.setMemberId(lesson.getMemberId());
        trainingEntity.setCoachId(coachId);
        trainingEntity.setStartHour(Integer.parseInt(startHour));
        trainingEntity.setEndHour(Integer.parseInt(endHour));
        trainingEntity.setLessonDate(lessonDate);
        trainingEntity.setLessonId(lessonId);
        trainingEntity.setType(type);
        trainingEntity.setTitle("私教课");
        trainingEntity.setStoreId(memberCardEntity.getStoreId());
        trainingEntity.setCardNo(memberCardEntity.getCardNo());
        trainingEntity.setCardType(memberCardEntity.getType());
        int n = trainingDao.add(trainingEntity);
        if(n > 0){
            return ResponseUtil.success("约课成功");
        }
        return ResponseUtil.exception("约课失败!该时段课程已约满!");
    }

    public ResponseEntity<String> cancel(Lesson lesson) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" cancel  memberRequest = {}",memberRequest);
        logger.info(" cancel  memberRequest = {}",lesson);

        if(StringUtils.isEmpty(lesson.getMemberId())){
            lesson.setMemberId(memberRequest.getMemberId());
        }

        TrainingEntity trainingEntity = trainingDao.getById(lesson.getTrainingId());
        if(trainingEntity==null){
            return ResponseUtil.exception("取消约课异常!");
        }

        if(trainingEntity.getMemberId().equals(lesson.getMemberId())){
            TrainingEntity trainingUpdate = new TrainingEntity();
            trainingUpdate.setTrainingId(trainingEntity.getTrainingId());
            trainingUpdate.setStatus(-1);
            int n = trainingDao.update(trainingUpdate);
            if(n==1){
                return ResponseUtil.success("取消成功!");
            }
        }
        return ResponseUtil.exception("取消约课失败!");

    }



}

