package com.training.service;

import com.alibaba.fastjson.JSONObject;
import com.training.dao.*;
import com.training.domain.Lesson;
import com.training.domain.Member;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
import com.training.util.ut;
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

    @Autowired
    private LessonSettingDao lessonSettingDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberDao memberDao;

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
        List<Lesson> lessonList = new ArrayList();
        if(query.getType().equals("T")){
            lessonList = quertTeamSchedule(query);
        }
//        if(query.getType().startsWith("S")){
//            lessonList = quertSpecialSchedule(query);
//        }
        if(query.getType().startsWith("P")){
            if(StringUtils.isEmpty(query.getCoachId())){
                return ResponseUtil.exception("教练信息缺失,查询课程时间表异常!");
            }
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
            lesson.setStartHour(i*100);
            lesson.setEndHour((i+1)*100);
            lesson.setCoachId(query.getCoachId());
            lesson.setLessonDate(query.getLessonDate());
            lesson.setLessonId(createLessonId(lesson));
            lesson.setMyOrder("0");
            lesson.setQuota(1);
            lesson.setTrainingId("");
            lesson.setTrainingList(new ArrayList<>());
            for (TrainingEntity trainingEntity:trainingEntityList){
                logger.info(" ***********  getLessonDate = {} ,  getMemberId = {} ,  trainingEntity = {} ",trainingEntity.getLessonDate() , query.getMemberId() , trainingEntity.getMemberId());
                logger.info(" ***********  trainingEntity.getStartHour() = {} ,  lesson.getStartHour() = {} ",trainingEntity.getStartHour() , lesson.getStartHour());
                if(trainingEntity.getStartHour().equals(lesson.getStartHour())){
                    lesson.setQuota(0);
                    if(trainingEntity.getMemberId().equals(query.getMemberId())){
                        lesson.setMyOrder("1");
                        lesson.getTrainingList().add(trainingService.transferTraining(trainingEntity));
                        lesson.setTrainingId(trainingEntity.getTrainingId());
                    }
                }
            }
            lesson.setStatus(0);
            lesson.setType(LessonTypeEnum.P.getKey());

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
        List<Lesson> lessonList = new ArrayList();
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" quertTeamSchedule  memberRequest = {} ",memberRequest);
        logger.info(" quertTeamSchedule  query = {} ",query);
        if(StringUtils.isEmpty(query.getCardNo())){
            return lessonList;
        }
        if(StringUtils.isEmpty(query.getMemberId())){
            query.setMemberId(memberRequest.getMemberId());
        }
        MemberCardEntity memberCardDB = memberCardDao.getById(query.getCardNo());
        if(memberCardDB==null){
            return lessonList;
        }
        query.setStoreId(memberCardDB.getStoreId());

        LessonSettingQuery lessonSettingQuery = new LessonSettingQuery();
        lessonSettingQuery.setStoreId(query.getStoreId());
        lessonSettingQuery.setStartDate(query.getLessonDate());
        lessonSettingQuery.setEndDate(query.getLessonDate());
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<LessonSettingEntity> lessonSettingList = lessonSettingDao.find(lessonSettingQuery,page);
        logger.info(" quertTeamSchedule  lessonSettingList.size() = {} ",lessonSettingList.size());
        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(query.getLessonDate());
        trainingQuery.setStoreId(query.getStoreId());
        trainingQuery.setType(LessonTypeEnum.T.getKey());
        trainingQuery.setStatus(0);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);
        logger.info(" quertTeamSchedule  trainingEntityList.size() = {} ",trainingEntityList.size());

        for (LessonSettingEntity lessonSettingEntity : lessonSettingList) {
            if(StringUtils.isNotEmpty(lessonSettingEntity.getWeekRepeat())){
                String[] weekDays = lessonSettingEntity.getWeekRepeat().split(",");
                int weekIndex = ut.daysOfWeek(query.getLessonDate());
                if(weekDays.length<weekIndex){
                    continue;
                }
                if(!weekDays[weekIndex-1].equals("1")){
                    continue;
                }
            }
            logger.info(" quertTeamSchedule  lessonSettingEntity = {} ",lessonSettingEntity);

            Lesson lesson = new Lesson();
            lesson.setLessonId(lessonSettingEntity.getLessonId());
            lesson.setTitle(lessonSettingEntity.getTitle());

            StaffEntity staffDB = staffDao.getById(lessonSettingEntity.getCoachId());
            if(staffDB!=null){
                lesson.setCoachName(staffDB.getCustname());
            }else{
                lesson.setCoachName("暂无");
            }
            lesson.setStartHour(lessonSettingEntity.getStartHour());
            lesson.setEndHour(lessonSettingEntity.getEndHour());
            lesson.setCoachId(lessonSettingEntity.getCoachId());
            lesson.setLessonDate(query.getLessonDate());
            lesson.setMaxCount(lessonSettingEntity.getQuotaMax());
            lesson.setMinCount(lessonSettingEntity.getQuotaMin());
            lesson.setMyOrder("0");
            lesson.setTrainingList(new ArrayList<>());
            int count = 0;
            for (TrainingEntity trainingEntity : trainingEntityList){
                if(trainingEntity.getLessonId().equals(lessonSettingEntity.getLessonId())){
                    count++;
                    if(trainingEntity.getMemberId().equals(query.getMemberId())){
                        lesson.setMyOrder("1");
                        lesson.getTrainingList().add(trainingService.transferTraining(trainingEntity));
                        lesson.setTrainingId(trainingEntity.getTrainingId());
                    }
                }

            }

            logger.info(" quertTeamSchedule  count = {} , lessonSettingEntity.getQuotaMax() = {} ",count,lessonSettingEntity.getQuotaMax());

            int quota = (lessonSettingEntity.getQuotaMax()<=count)?0:(lessonSettingEntity.getQuotaMax()-count);
            lesson.setQuota(quota);
            lesson.setType(LessonTypeEnum.T.getKey());
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
        String type = lesson.getType();
        if(StringUtils.isEmpty(type)){
            return ResponseUtil.exception("约课异常!");
        }
        if(type.equals(LessonTypeEnum.P.getKey())){
            return orderPersonal(lesson);
        }
        if(type.equals(LessonTypeEnum.T.getKey())){
            return orderTeam(lesson);
        }
        return ResponseUtil.exception("约课失败!");
    }

    private ResponseEntity<String> orderTeam(Lesson lesson) {
        String lessonId = lesson.getLessonId();

        LessonSettingEntity lessonSettingEntity = lessonSettingDao.getById(lessonId);
        if(lessonSettingEntity==null){
            return ResponseUtil.exception("约课异常!");
        }

        MemberCardEntity memberCardEntity = memberCardService.getCurrentUseCard(lesson.getMemberId(),lesson.getType());
        if(memberCardEntity==null){
            return ResponseUtil.exception("约课失败!无可用课程卡!请先购卡");
        }

        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(lesson.getLessonDate());
        trainingQuery.setLessonId(lessonId);
        trainingQuery.setType(LessonTypeEnum.T.getKey());
        trainingQuery.setStatus(0);
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);
        logger.info(" orderTeam  trainingEntityList.size() = {} , lessonSettingEntity.getQuotaMax() = {} ",trainingEntityList.size(),lessonSettingEntity.getQuotaMax());
        if(trainingEntityList.size()>=lessonSettingEntity.getQuotaMax()){
            return ResponseUtil.exception("约课失败!该课程已约满!");
        }
        TrainingEntity  trainingEntity = new TrainingEntity();
        trainingEntity.setTrainingId(IDUtils.getId());
        trainingEntity.setMemberId(lesson.getMemberId());

        StaffEntity staffEntity = staffDao.getById(lessonSettingEntity.getCoachId());

        if(StringUtils.isEmpty(staffEntity.getOpenId())){
            return ResponseUtil.exception("约课失败!教练设置异常!");
        }
        MemberEntity memberEntity = memberDao.getByOpenId(staffEntity.getOpenId());
        if(memberEntity==null){
            return ResponseUtil.exception("约课失败!教练设置异常");
        }
        trainingEntity.setCoachId(memberEntity.getMemberId());
        trainingEntity.setStartHour(lessonSettingEntity.getStartHour());
        trainingEntity.setEndHour(lessonSettingEntity.getEndHour());
        trainingEntity.setLessonDate(lesson.getLessonDate());
        trainingEntity.setLessonId(lessonId);
        trainingEntity.setType(lesson.getType());
        trainingEntity.setTitle(lessonSettingEntity.getTitle());
        trainingEntity.setStoreId(lessonSettingEntity.getStoreId());
        trainingEntity.setCardNo(memberCardEntity.getCardNo());
        trainingEntity.setCardType(memberCardEntity.getType());
        int n = trainingDao.add(trainingEntity);
        if(n > 0){
            return ResponseUtil.success("约课成功");
        }
        return ResponseUtil.exception("约课失败!");

    }

    private ResponseEntity<String> orderPersonal(Lesson lesson) {
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


        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(lesson.getLessonDate());
        trainingQuery.setLessonId(lessonId);
        trainingQuery.setMemberId(lesson.getMemberId());
        trainingQuery.setType(LessonTypeEnum.P.getKey());
        trainingQuery.setStatus(0);
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);
        logger.info(" orderPersonal  trainingEntityList.size() = {} ",trainingEntityList.size());
        if(trainingEntityList.size()>0){
            return ResponseUtil.exception("约课失败!您已预约此时段的课程!不能重复预约!");
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

