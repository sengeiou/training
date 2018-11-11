package com.training.service;

import com.alibaba.fastjson.JSONObject;
import com.training.dao.*;
import com.training.domain.*;
import com.training.entity.*;
import com.training.common.*;
import com.training.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private TrainingService trainingService;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CoachRestDao coachRestDao;

    @Autowired
    private StaffService staffService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private LessonSettingDao lessonSettingDao;

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        if("T".equals(query.getType())){
            lessonList = quertTeamSchedule(query);
        }
        if(query.getType().startsWith("S")){
            lessonList = quertSpecialSchedule(query);
        }
        if(StringUtils.isNotEmpty(query.getType()) && query.getType().startsWith("P")){

            String coachId = memberService.getCoachIdByMemberId(query.getMemberId());
            logger.info(" schedule  coachId = {} ",coachId);
            if(StringUtils.isEmpty(coachId)){
//                return ResponseUtil.exception("教练未绑定微信,暂时不能约课!请稍后再试");
                query.setCoachId(null);
            }

            if(StringUtils.isEmpty(query.getCoachId())){
//                return ResponseUtil.exception("教练信息缺失,查询课程时间表异常!");
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
//            return lessonList;
        }
        Member memberRequest = RequestContextHelper.getMember();
        if(StringUtils.isEmpty(query.getMemberId())){
            query.setMemberId(memberRequest.getMemberId());
        }

        MemberEntity memberEntity = memberDao.getById(query.getMemberId());

        String coachId = memberService.getCoachIdByMemberId(query.getMemberId());
        logger.info(" quertPersonalSchedule  coachId = {} ",coachId);
        query.setCoachId(coachId);

        logger.info(" quertPersonalSchedule  memberRequest = {} ",memberRequest);
        logger.info(" quertPersonalSchedule  query = {} ",query);
        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(query.getLessonDate());
        trainingQuery.setCoachId(coachId);
        trainingQuery.setStatus(0);
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);

        logger.info(" quertPersonalSchedule  trainingQuery1 = {} ",trainingQuery);
        trainingQuery.setCoachId(null);
        trainingQuery.setMemberId(query.getMemberId());
        logger.info(" quertPersonalSchedule  trainingQuery2 = {} ",trainingQuery);

        List<TrainingEntity> trainingEntityListMember = trainingDao.find(trainingQuery,page);
        logger.info(" quertPersonalSchedule  trainingEntityListMember.size() = {} ",trainingEntityListMember.size());

        for (TrainingEntity trainingEntity : trainingEntityListMember){
            if(!trainingEntity.getCoachId().equals(coachId)){
                trainingEntityList.add(trainingEntity);
            }
        }

        logger.info(" quertPersonalSchedule  trainingEntityList.size() = {} ",trainingEntityList.size());
//        for (TrainingEntity trainingEntity:trainingEntityList){
//            logger.info(" trainingEntity = {} ",trainingEntity);
//        }

        CoachRestQuery coachRestQuery = new CoachRestQuery();
        coachRestQuery.setCoachId(memberService.getCoachIdByMemberId(query.getMemberId()));

        List<CoachRestEntity> coachRestEntityList = coachRestDao.find(coachRestQuery,page);
        logger.info(" quertPersonalSchedule  coachRestEntityList.size() = {} ",coachRestEntityList.size());

        List<CoachRestEntity> filterCoachRestEntityList = new ArrayList<>();
        for (CoachRestEntity coachRestEntity:coachRestEntityList){
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.ONCE.getKey())){
                if(coachRestEntity.getRestDate().equals(query.getLessonDate())){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.DAY.getKey())){
                filterCoachRestEntityList.add(coachRestEntity);
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.WEEK.getKey())){
                int index1 = ut.indexOfWeek(coachRestEntity.getRestDate());
                int index2 = ut.indexOfWeek(query.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.MONTH.getKey())){
                int index1 = ut.indexOfMonth(coachRestEntity.getRestDate());
                int index2 = ut.indexOfMonth(query.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
        }

        LessonSettingQuery lessonSettingQuery = new LessonSettingQuery();
        lessonSettingQuery.setCoachId(memberEntity.getCoachStaffId());
        lessonSettingQuery.setStartDate(query.getLessonDate());
        lessonSettingQuery.setEndDate(query.getLessonDate());
        lessonSettingQuery.setStatus(0);
        List<LessonSettingEntity> lessonSettingList = lessonSettingDao.find(lessonSettingQuery,page);
        logger.info(" quertPersonalSchedule  lessonSettingList.size() = {} ",lessonSettingList.size());

        logger.info(" quertPersonalSchedule  filterCoachRestEntityList.size() = {} ",filterCoachRestEntityList.size());
        for (int i = 0; i < Const.times.size(); i++) {
            int startHour = Const.times.get(i);
            int endHour = startHour+100;
            Lesson lesson = new Lesson();
            lesson.setType("P");
            lesson.setStartHour(startHour);
            lesson.setEndHour(endHour);
            lesson.setCoachId(query.getCoachId());
            lesson.setLessonDate(query.getLessonDate());
            lesson.setLessonId(createLessonId(lesson));
            lesson.setMyOrder("0");
            lesson.setQuota(2);
            lesson.setTrainingId("");
            lesson.setTrainingList(new ArrayList<>());
            for (TrainingEntity trainingEntity:trainingEntityList){
//                logger.info(" ***********  getLessonDate = {} ,  getMemberId = {} ,  trainingEntity = {} ",trainingEntity.getLessonDate() , query.getMemberId() , trainingEntity.getMemberId());
//                logger.info(" ***********  trainingEntity.getStartHour() = {} ,  lesson.getStartHour() = {} ",trainingEntity.getStartHour() , lesson.getStartHour());
                if(trainingEntity.getStartHour().equals(lesson.getStartHour())){
                    lesson.setMemberCount(1);
                    if(trainingEntity.getCardType().equals(CardTypeEnum.PT.getKey())||trainingEntity.getCardType().equals(CardTypeEnum.TY.getKey())
                            ||trainingEntity.getCardType().equals(CardTypeEnum.TT.getKey())||trainingEntity.getCardType().equals(CardTypeEnum.TM.getKey())){
                        lesson.setQuota(0);
                    }
                    if(trainingEntity.getCardType().equals(CardTypeEnum.PM.getKey())){
                        lesson.setQuota(lesson.getQuota()-1);
                        lesson.setMemberCount(2);
                        if(!trainingEntity.getMemberId().equals(query.getMemberId())){
                            MemberEntity this_member = memberDao.getById(trainingEntity.getMemberId());
                            lesson.setMemberImage(this_member.getImage());
                        }
                    }
                    if(lesson.getQuota()<0){
                        lesson.setQuota(0);
                    }
                    if(trainingEntity.getMemberId().equals(query.getMemberId())){
                        lesson.setMyOrder("1");
                        lesson.getTrainingList().add(trainingService.transferTraining(trainingEntity));
                        lesson.setTrainingId(trainingEntity.getTrainingId());
                    }
                }
            }
            lesson.setStatus(0);  // 0 - 正常 ，   -1 - 不可约
            for (CoachRestEntity coachRestEntity:filterCoachRestEntityList){
                if(coachRestEntity.getStartHour() >= endHour || coachRestEntity.getEndHour() <= startHour){

                }else{
                    lesson.setStatus(-1);
                    break;
                }
            }
            for(LessonSettingEntity lessonSettingEntity: lessonSettingList){
                int index = ut.indexOfWeek(query.getLessonDate());
                String[] days = lessonSettingEntity.getWeekRepeat().split(",");
//                logger.info(" ***********   lessonSettingEntity   query.getLessonDate() = {},  index = {} ,  lesson.getStartHour() = {} ",query.getLessonDate(),index , JSON.toJSONString(days));
                if(days.length>=index){
                    if(days[index-1].equals("0")){
                        continue;
                    }
                }
                if(lessonSettingEntity.getStartHour() >= endHour || lessonSettingEntity.getEndHour() <= startHour){

                }else{
                    lesson.setStatus(-1);
                    break;
                }

            }

            lesson.setType(LessonTypeEnum.P.getKey());
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

        MemberEntity memberDB = memberDao.getById(memberRequest.getMemberId());
        if(memberDB==null){
            return lessonList;
        }
        if(StringUtils.isNotEmpty(memberDB.getCoachStaffId())){
            StaffEntity coachStaff = staffDao.getById(memberDB.getCoachStaffId());
            if(coachStaff!=null){
                query.setStoreId(coachStaff.getStoreId());
            }
        }else{
            if(StringUtils.isNotEmpty(memberDB.getStoreId())){
                query.setStoreId(memberDB.getStoreId());
            }
        }
        if(StringUtils.isEmpty(query.getStoreId())){
            return lessonList;
        }
        if(StringUtils.isEmpty(query.getLessonDate())){
            return lessonList;
        }

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
                int weekIndex = ut.indexOfWeek(query.getLessonDate());
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
            if(staffDB==null){
                continue;
            }else{
                lesson.setCoachName(staffDB.getCustname());
            }

            //如果教练当前所属的门店和课程设置的门店不匹配，跳过
            if(!staffDB.getStoreId().equals(lessonSettingEntity.getStoreId())){
//                continue;
            }

            StoreEntity storeEntity = storeDao.getById(lessonSettingEntity.getStoreId());
            lesson.setStoreId(storeEntity.getStoreId());
            lesson.setStoreName(storeEntity.getName());
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
        List<Lesson> lessonList = new ArrayList();
        Member memberRequest = RequestContextHelper.getMember();
        if(StringUtils.isEmpty(query.getMemberId())){
            query.setMemberId(memberRequest.getMemberId());
        }
        logger.info(" quertSpecialSchedule  memberRequest = {} ",memberRequest);
        logger.info(" quertSpecialSchedule  query = {} ",query);
        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(query.getLessonDate());
        String coachId = query.getCoachId();
        logger.info(" quertSpecialSchedule  coachId = {} ",coachId);
        trainingQuery.setCoachId(coachId);
        trainingQuery.setStatus(0);
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);
        logger.info(" quertSpecialSchedule  trainingEntityList.size() = {} ",trainingEntityList.size());
//        for (TrainingEntity trainingEntity:trainingEntityList){
//            logger.info(" trainingEntity = {} ",trainingEntity);
//        }

        logger.info(" quertPersonalSchedule  trainingQuery1 = {} ",trainingQuery);
        trainingQuery.setCoachId(null);
        trainingQuery.setMemberId(query.getMemberId());
        logger.info(" quertPersonalSchedule  trainingQuery2 = {} ",trainingQuery);

        List<TrainingEntity> trainingEntityListMember = trainingDao.find(trainingQuery,page);
        logger.info(" quertPersonalSchedule  trainingEntityListMember.size() = {} ",trainingEntityListMember.size());

        for (TrainingEntity trainingEntity : trainingEntityListMember){
            if(!trainingEntity.getCoachId().equals(coachId)){
                trainingEntityList.add(trainingEntity);
            }
        }

        CoachRestQuery coachRestQuery = new CoachRestQuery();
        coachRestQuery.setCoachId(memberService.getCoachIdByMemberId(query.getMemberId()));

        List<CoachRestEntity> coachRestEntityList = coachRestDao.find(coachRestQuery,page);
        logger.info(" quertSpecialSchedule  coachRestEntityList.size() = {} ",coachRestEntityList.size());

        List<CoachRestEntity> filterCoachRestEntityList = new ArrayList<>();
        for (CoachRestEntity coachRestEntity:coachRestEntityList){
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.ONCE.getKey())){
                if(coachRestEntity.getRestDate().equals(query.getLessonDate())){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.DAY.getKey())){
                filterCoachRestEntityList.add(coachRestEntity);
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.WEEK.getKey())){
                int index1 = ut.indexOfWeek(coachRestEntity.getRestDate());
                int index2 = ut.indexOfWeek(query.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.MONTH.getKey())){
                int index1 = ut.indexOfMonth(coachRestEntity.getRestDate());
                int index2 = ut.indexOfMonth(query.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
        }
        MemberEntity memberEntity = memberDao.getById(query.getMemberId());

        LessonSettingQuery lessonSettingQuery = new LessonSettingQuery();
        lessonSettingQuery.setCoachId(memberEntity.getCoachStaffId());
        lessonSettingQuery.setStartDate(query.getLessonDate());
        lessonSettingQuery.setEndDate(query.getLessonDate());
        lessonSettingQuery.setStatus(0);
        List<LessonSettingEntity> lessonSettingList = lessonSettingDao.find(lessonSettingQuery,page);
        logger.info(" quertSpecialSchedule  lessonSettingList.size() = {} ",lessonSettingList.size());

        logger.info(" quertSpecialSchedule  filterCoachRestEntityList.size() = {} ",filterCoachRestEntityList.size());
        for (int i = 0; i < Const.times.size(); i++) {
            int startHour = Const.times.get(i);
            int endHour = startHour+100;
            Lesson lesson = new Lesson();
            lesson.setType(query.getType());
            lesson.setStartHour(startHour);
            lesson.setEndHour(endHour);
            lesson.setCoachId(query.getCoachId());
            lesson.setLessonDate(query.getLessonDate());
            lesson.setLessonId(createLessonId(lesson));
            lesson.setMyOrder("0");
            lesson.setQuota(2);
            lesson.setTrainingId("");
            lesson.setTrainingList(new ArrayList<>());
            for (TrainingEntity trainingEntity:trainingEntityList){
//                logger.info(" ***********  getLessonDate = {} ,  getMemberId = {} ,  trainingEntity = {} ",trainingEntity.getLessonDate() , query.getMemberId() , trainingEntity.getMemberId());
//                logger.info(" ***********  trainingEntity.getStartHour() = {} ,  lesson.getStartHour() = {} ",trainingEntity.getStartHour() , lesson.getStartHour());
                if(trainingEntity.getStartHour().equals(lesson.getStartHour())){
                    if(trainingEntity.getCardType().equals(CardTypeEnum.PT.getKey())||trainingEntity.getCardType().equals(CardTypeEnum.TY.getKey())
                            ||trainingEntity.getCardType().equals(CardTypeEnum.TT.getKey())||trainingEntity.getCardType().equals(CardTypeEnum.TM.getKey())){
                        lesson.setQuota(0);
                    }
                    if(trainingEntity.getCardType().equals(CardTypeEnum.PM.getKey())){
                        lesson.setQuota(lesson.getQuota()-1);
                    }

                    if(lesson.getQuota()<0){
                        lesson.setQuota(0);
                    }
                    if(trainingEntity.getMemberId().equals(query.getMemberId())){
                        lesson.setMyOrder("1");
                        lesson.getTrainingList().add(trainingService.transferTraining(trainingEntity));
                        lesson.setTrainingId(trainingEntity.getTrainingId());
                    }
                }
            }
            lesson.setStatus(0);  // 0 - 正常 ，   -1 - 不可约
            for (CoachRestEntity coachRestEntity:filterCoachRestEntityList){
                if(coachRestEntity.getStartHour() >= endHour || coachRestEntity.getEndHour() <= startHour){

                }else{
                    lesson.setStatus(-1);
                    break;
                }
            }

            for(LessonSettingEntity lessonSettingEntity: lessonSettingList){
                int index = ut.indexOfWeek(query.getLessonDate());
                String[] days = lessonSettingEntity.getWeekRepeat().split(",");
//                logger.info(" ***********   lessonSettingEntity   query.getLessonDate() = {},  index = {} ,  lesson.getStartHour() = {} ",query.getLessonDate(),index , JSON.toJSONString(days));
                if(days.length>=index){
                    if(days[index-1].equals("0")){
                        continue;
                    }
                }
                if(lessonSettingEntity.getStartHour() >= endHour || lessonSettingEntity.getEndHour() <= startHour){

                }else{
                    lesson.setStatus(-1);
                    break;
                }

            }

            lesson.setType(LessonTypeEnum.P.getKey());
            lessonList.add(lesson);
        }

        for (Lesson lesson:lessonList){
//            logger.info(" lesson = {} ",lesson);
        }

        return lessonList;
    }

    public ResponseEntity<String> order(Lesson lesson) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" order  lesson = {}",lesson);
        if(StringUtils.isEmpty(lesson.getMemberId())){
            lesson.setMemberId(memberRequest.getMemberId());
        }

        MemberEntity memberEntity = memberDao.getById(lesson.getMemberId());
        if(memberEntity==null){
            return ResponseUtil.exception("约课异常!会员信息无效");
        }

        if(memberEntity.getStatus().equals(Integer.parseInt("9"))){
            return ResponseUtil.exception("您处于停课状态，不能约课");
        }

        int count = queryOrderLessonCount(lesson.getMemberId());
        if(count>=3){
            return ResponseUtil.exception("每个会员最多提前约三节课，你目前不能再约课了！");
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

        if(type.startsWith(LessonTypeEnum.S.getKey())){
            return orderSpecial(lesson);
        }

        return ResponseUtil.exception("约课失败!");
    }

    private int queryOrderLessonCount(String memberId) {
        int count = 0;
        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setStartDate(ut.currentDate());
        trainingQuery.setMemberId(memberId);
        trainingQuery.setStatus(0);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,pageRequest);
        for (TrainingEntity trainingEntity:trainingEntityList){
            if(trainingEntity.getLessonDate().equals(ut.currentDate())){
                int startHour = trainingEntity.getStartHour();
                int now = ut.currentHour();
                if(startHour>now){
                    count++;
                }
            }else {
                count++;
            }
        }
        return count;
    }

    private ResponseEntity<String> orderSpecial(Lesson lesson) {
        logger.info(" orderSpecial  lesson = {} ",lesson);
        String lessonId = lesson.getLessonId();
        String[] ids = lessonId.split(",");
        String type = ids[0];
        String lessonDate = ids[1];
        String startHour = ids[2];
        String endHour = ids[3];
        String coachId = ids[4];
        lesson.setStartHour(Integer.parseInt(startHour));
        lesson.setEndHour(Integer.parseInt(endHour));

        if(StringUtils.isEmpty(lesson.getCardNo())){
            return ResponseUtil.exception("约课失败!无课程卡ID!");
        }
        if(lesson.getLessonDate()==null){
            return ResponseUtil.exception("约课失败!约课日期异常");
        }
        if(lesson.getLessonDate().equals(ut.currentDate())){
            int currentHour = ut.currentHour();
            if(currentHour>2200||currentHour>=lesson.getStartHour()){
                return ResponseUtil.exception("约课失败!已过约课时间!");
            }
        }

        MemberCardEntity memberCardEntity = memberCardDao.getById(lesson.getCardNo());
        if(memberCardEntity==null){
            return ResponseUtil.exception("约课失败!无此课程卡!");
        }

        if(ut.passDayByDate(ut.currentDate(),memberCardEntity.getStartDate())>0){
            return ResponseUtil.exception("约课失败!课程卡未到使用期!");
        }

        if(ut.passDayByDate(ut.currentDate(),memberCardEntity.getEndDate())<0){
            return ResponseUtil.exception("约课失败!课程卡已过期!");
        }

        if(memberCardEntity.getCount()<=0){
            return ResponseUtil.exception("约课失败!课程卡已无可用次数!");
        }

        logger.info(" =======   lesson.getLessonDate() = {} ,memberCardEntity.getEndDate() = {} ",lesson.getLessonDate(),memberCardEntity.getEndDate());
        logger.info(" =======   passDayByDate = {} ",ut.passDayByDate(lesson.getLessonDate(),memberCardEntity.getEndDate()));

        if(ut.passDayByDate(lesson.getLessonDate(),memberCardEntity.getEndDate())<0){
            return ResponseUtil.exception("约课失败!预约日期已超过课卡有效期范围!");
        }

        CoachRestQuery coachRestQuery = new CoachRestQuery();
        coachRestQuery.setCoachId(coachId);

        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<CoachRestEntity> coachRestEntityList = coachRestDao.find(coachRestQuery,page);
        logger.info(" orderSpecial  coachRestEntityList.size() = {} ",coachRestEntityList.size());

        List<CoachRestEntity> filterCoachRestEntityList = new ArrayList<>();
        for (CoachRestEntity coachRestEntity:coachRestEntityList){
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.ONCE.getKey())){
                if(coachRestEntity.getRestDate().equals(lesson.getLessonDate())){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.DAY.getKey())){
                filterCoachRestEntityList.add(coachRestEntity);
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.WEEK.getKey())){
                int index1 = ut.indexOfWeek(coachRestEntity.getRestDate());
                int index2 = ut.indexOfWeek(lesson.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.MONTH.getKey())){
                int index1 = ut.indexOfMonth(coachRestEntity.getRestDate());
                int index2 = ut.indexOfMonth(lesson.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
        }
        logger.info(" orderSpecial  filterCoachRestEntityList.size() = {} ",filterCoachRestEntityList.size());

        for (CoachRestEntity coachRestEntity:filterCoachRestEntityList){
            if(coachRestEntity.getStartHour() >= lesson.getEndHour() || coachRestEntity.getEndHour() <= lesson.getStartHour() ){

            }else{
                return ResponseUtil.exception("约课失败!此时段课程可约时长不足一小时!");
            }
        }

        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(lesson.getLessonDate());
        trainingQuery.setCoachId(coachId);
//        trainingQuery.setMemberId(lesson.getMemberId());
//        trainingQuery.setType(LessonTypeEnum.P.getKey());
        trainingQuery.setStatus(0);

        List<TrainingEntity> trainingEntityListCoach = trainingDao.find(trainingQuery,page);
        logger.info(" orderSpecial  trainingEntityListCoach.size() = {} ",trainingEntityListCoach.size());


        logger.info(" quertPersonalSchedule  trainingQuery1 = {} ",trainingQuery);
        trainingQuery.setCoachId(null);
        trainingQuery.setMemberId(lesson.getMemberId());
        logger.info(" quertPersonalSchedule  trainingQuery2 = {} ",trainingQuery);
        List<TrainingEntity> trainingEntityListMember = trainingDao.find(trainingQuery,page);
        logger.info(" quertPersonalSchedule  trainingEntityListMember.size() = {} ",trainingEntityListMember.size());
        for (TrainingEntity trainingEntity : trainingEntityListMember){
            if(!trainingEntity.getCoachId().equals(coachId)){
                trainingEntityListCoach.add(trainingEntity);
            }
        }


        List<TrainingEntity> trainingEntityList = new ArrayList<>();
        for (TrainingEntity trainingEntity : trainingEntityListCoach){
            int startHourTraining = trainingEntity.getStartHour();
            int endHourTraining = trainingEntity.getEndHour();

            if(trainingEntity.getStartHour().equals( lesson.getStartHour() ) ){
                trainingEntityList.add(trainingEntity);
                continue;
            }
            if(startHourTraining >= lesson.getEndHour() || endHourTraining <= lesson.getStartHour() ){

            }else{
                return ResponseUtil.exception("约课失败!此时段课程可约时长不足一小时!不能预约!");
            }
        }

        for (TrainingEntity trainingEntity : trainingEntityList){
            if(trainingEntity.getMemberId().equals(lesson.getMemberId())){
                return ResponseUtil.exception("约课失败!您已预约此时段的课程!不能重复预约!");
            }
        }
        if(trainingEntityList.size()==1){
//            TrainingEntity trainingEntity = trainingEntityList.get(0);
//
//            MemberCardEntity memberCardOther = memberCardDao.getById(trainingEntity.getCardNo());
//            logger.info(" *****   memberCardOther = {} ",memberCardOther);
//            if(memberCardOther.getType().equals(CardTypeEnum.PT.getKey())){
                return ResponseUtil.exception("约课失败!该时段课程已约满!");
//            }else{
//                //如果已经预约的会员 是用月卡约的 ， 需要判断本次是否也是月卡  ，如果是月卡可以预约 ， 如果是次卡  不能预约
//                if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())){
//                    return ResponseUtil.exception("该时段课程因属于双学员课程，只能再由一个私教月卡会员预约!您为私教次卡不能预约!");
//                }
//            }
        }
        if(trainingEntityList.size()>1){
            return ResponseUtil.exception("该时段课程属于双学员课程，且已经约满，不能预约!");
        }
        TrainingEntity  trainingEntity = new TrainingEntity();
        trainingEntity.setTrainingId(IDUtils.getId());
        trainingEntity.setMemberId(lesson.getMemberId());
        if(coachId==null){
            return ResponseUtil.exception("约课失败!教练信息获取失败!");
        }

        MemberEntity memberEntity = memberDao.getById(lesson.getMemberId());
        StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());

        trainingEntity.setCoachId(coachId);
        trainingEntity.setStartHour(Integer.parseInt(startHour));
        trainingEntity.setEndHour(Integer.parseInt(endHour));
        trainingEntity.setLessonDate(lessonDate);
        trainingEntity.setLessonId(lessonId);
        trainingEntity.setType(type);
        trainingEntity.setTitle(CardTypeEnum.getEnumByKey(memberCardEntity.getType()).getDesc());
        trainingEntity.setStaffId(staffEntity.getStaffId());
        trainingEntity.setStoreId(staffEntity.getStoreId());
        trainingEntity.setCardNo(memberCardEntity.getCardNo());
        trainingEntity.setCardType(memberCardEntity.getType());
        int n = trainingDao.add(trainingEntity);
        if(n > 0){
            n = memberCardDao.reduceCount(memberCardEntity.getCardNo());
            try{
                smsUtil.sendLessonNotice(staffEntity.getPhone(),"【"+memberEntity.getName()+"】",lessonDate+" "+trainingEntity.getStartHour().toString().replaceAll("00",":00"),"特色课");
            }catch (Exception e){

            }
            return ResponseUtil.success("约课成功");
        }
        return ResponseUtil.exception("约课失败!该时段课程已约满!");
//        return ResponseUtil.exception("约特色课失败!");
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

        if(lesson.getLessonDate().equals(ut.currentDate())){
            int currentHour = ut.currentHour();
            if(currentHour>2200||currentHour>=(lesson.getStartHour()-600)){
                return ResponseUtil.exception("约课失败!已过约课时间!");
            }
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
        for (TrainingEntity trainingEntity : trainingEntityList){
            if(trainingEntity.getMemberId().equals(lesson.getMemberId())){
                return ResponseUtil.exception("约课失败!您已预约此次课程!不能重复预约");
            }
        }


        logger.info(" quertPersonalSchedule  trainingQuery1 = {} ",trainingQuery);
        trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(lesson.getLessonDate());
        trainingQuery.setStatus(0);
        trainingQuery.setCoachId(null);
        trainingQuery.setMemberId(lesson.getMemberId());
        logger.info(" quertPersonalSchedule  trainingQuery2 = {} ",trainingQuery);
        List<TrainingEntity> trainingEntityListMember = trainingDao.find(trainingQuery,page);
        logger.info(" quertPersonalSchedule  trainingEntityListMember.size() = {} ",trainingEntityListMember.size());
        for (TrainingEntity trainingEntity : trainingEntityListMember){
            int startHourTraining = trainingEntity.getStartHour();
            int endHourTraining = trainingEntity.getEndHour();
            if(trainingEntity.getStartHour().equals( lesson.getStartHour() ) ){
                return ResponseUtil.exception("约课失败!您已预约此时段其他["+LessonTypeEnum.getEnumByKey(trainingEntity.getType()).getDesc()+"]课程!不能预约!");
            }
            if(startHourTraining >= lesson.getEndHour() || endHourTraining <= lesson.getStartHour() ){

            }else{
                return ResponseUtil.exception("约课失败!此时段与您预约的其他["+LessonTypeEnum.getEnumByKey(trainingEntity.getType()).getDesc()+"]课程时间有重叠，!不能预约!");
            }
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
        trainingEntity.setStaffId(staffEntity.getStaffId());
        trainingEntity.setStoreId(staffEntity.getStoreId());
        trainingEntity.setCardNo(memberCardEntity.getCardNo());
        trainingEntity.setCardType(memberCardEntity.getType());
        int n = trainingDao.add(trainingEntity);
        if(n > 0){
            n = memberCardDao.reduceCount(memberCardEntity.getCardNo());
            try{
                smsUtil.sendLessonNotice(staffEntity.getPhone(),"【"+memberEntity.getName()+"】",lesson.getLessonDate()+" "+trainingEntity.getStartHour().toString().replaceAll("00",":00"),"团体课");
            }catch (Exception e){

            }
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

        lesson.setStartHour(Integer.parseInt(startHour));
        lesson.setEndHour(Integer.parseInt(endHour));

        if(StringUtils.isEmpty(lesson.getCardNo())){
            return ResponseUtil.exception("约课失败!无课程卡ID!");
        }
        if(lesson.getLessonDate()==null){
            return ResponseUtil.exception("约课失败!约课日期异常");
        }
        if(lesson.getLessonDate().equals(ut.currentDate())){
            int currentHour = ut.currentHour();
            if(currentHour>2200||currentHour>=lesson.getStartHour()){
                return ResponseUtil.exception("约课失败!已过约课时间!");
            }
        }

        MemberCardEntity memberCardEntity = memberCardDao.getById(lesson.getCardNo());
        if(memberCardEntity==null){
            return ResponseUtil.exception("约课失败!无此课程卡!");
        }

        if(ut.passDayByDate(ut.currentDate(),memberCardEntity.getStartDate())>0){
            return ResponseUtil.exception("约课失败!课程卡未到使用期!");
        }

        if(ut.passDayByDate(ut.currentDate(),memberCardEntity.getEndDate())<0){
            return ResponseUtil.exception("约课失败!课程卡已过期!");
        }

        if(memberCardEntity.getCount()<=0){
            return ResponseUtil.exception("约课失败!课程卡已无可用次数!");
        }

        logger.info(" =======   lesson.getLessonDate() = {} ,memberCardEntity.getEndDate() = {} ",lesson.getLessonDate(),memberCardEntity.getEndDate());
        logger.info(" =======   passDayByDate = {} ",ut.passDayByDate(lesson.getLessonDate(),memberCardEntity.getEndDate()));

        if(ut.passDayByDate(lesson.getLessonDate(),memberCardEntity.getEndDate())<0){
            return ResponseUtil.exception("约课失败!预约日期已超过课卡有效期范围!");
        }

        if(memberCardEntity.getType().equals(CardTypeEnum.PM.getKey())) {
            TrainingQuery trainingQuery = new TrainingQuery();
            trainingQuery.setLessonDate(lesson.getLessonDate());
            trainingQuery.setCardNo(lesson.getCardNo());
            trainingQuery.setType(LessonTypeEnum.P.getKey());
            trainingQuery.setStatus(0);
            PageRequest page = new PageRequest();
            page.setPageSize(100);
            List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);
            if(trainingEntityList.size()>0){
                return ResponseUtil.exception("私教月卡每日限约课一次，您本日已经预约课程，不能再次预约!");
            }
        }

        if(StringUtils.isEmpty(coachId)||coachId.equals("123")){
            coachId = memberService.getCoachIdByMemberId(lesson.getMemberId());
        }

        CoachRestQuery coachRestQuery = new CoachRestQuery();
        coachRestQuery.setCoachId(memberService.getCoachIdByMemberId(lesson.getMemberId()));

        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<CoachRestEntity> coachRestEntityList = coachRestDao.find(coachRestQuery,page);
        logger.info(" quertPersonalSchedule  coachRestEntityList.size() = {} ",coachRestEntityList.size());

        List<CoachRestEntity> filterCoachRestEntityList = new ArrayList<>();
        for (CoachRestEntity coachRestEntity:coachRestEntityList){
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.ONCE.getKey())){
                if(coachRestEntity.getRestDate().equals(lesson.getLessonDate())){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.DAY.getKey())){
                filterCoachRestEntityList.add(coachRestEntity);
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.WEEK.getKey())){
                int index1 = ut.indexOfWeek(coachRestEntity.getRestDate());
                int index2 = ut.indexOfWeek(lesson.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.MONTH.getKey())){
                int index1 = ut.indexOfMonth(coachRestEntity.getRestDate());
                int index2 = ut.indexOfMonth(lesson.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
        }
        logger.info(" quertPersonalSchedule  filterCoachRestEntityList.size() = {} ",filterCoachRestEntityList.size());

        for (CoachRestEntity coachRestEntity:filterCoachRestEntityList){
            if(coachRestEntity.getStartHour() >= lesson.getEndHour() || coachRestEntity.getEndHour() <= lesson.getStartHour() ){

            }else{
                return ResponseUtil.exception("约课失败!此时段课程可约时长不足一小时!");
            }
        }

        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(lesson.getLessonDate());
//        trainingQuery.setLessonId(lessonId);
        trainingQuery.setCoachId(coachId);
//        trainingQuery.setMemberId(lesson.getMemberId());
//        trainingQuery.setType(LessonTypeEnum.P.getKey());
        trainingQuery.setStatus(0);

        List<TrainingEntity> trainingEntityListCoach = trainingDao.find(trainingQuery,page);
        logger.info(" orderPersonal  trainingEntityListCoach.size() = {} ",trainingEntityListCoach.size());


        logger.info(" quertPersonalSchedule  trainingQuery1 = {} ",trainingQuery);
        trainingQuery.setCoachId(null);
        trainingQuery.setMemberId(lesson.getMemberId());
        logger.info(" quertPersonalSchedule  trainingQuery2 = {} ",trainingQuery);
        List<TrainingEntity> trainingEntityListMember = trainingDao.find(trainingQuery,page);
        logger.info(" quertPersonalSchedule  trainingEntityListMember.size() = {} ",trainingEntityListMember.size());
        for (TrainingEntity trainingEntity : trainingEntityListMember){
            if(!trainingEntity.getCoachId().equals(coachId)){
                trainingEntityListCoach.add(trainingEntity);
            }
        }


        List<TrainingEntity> trainingEntityList = new ArrayList<>();
        for (TrainingEntity trainingEntity : trainingEntityListCoach){
            int startHourTraining = trainingEntity.getStartHour();
            int endHourTraining = trainingEntity.getEndHour();

            if(trainingEntity.getStartHour().equals( lesson.getStartHour() ) ){
                trainingEntityList.add(trainingEntity);
                continue;
            }
            if(startHourTraining >= lesson.getEndHour() || endHourTraining <= lesson.getStartHour() ){

            }else{
                return ResponseUtil.exception("约课失败!此时段课程可约时长不足一小时!不能预约!");
            }
        }

        for (TrainingEntity trainingEntity : trainingEntityList){
            if(trainingEntity.getMemberId().equals(lesson.getMemberId())){
                return ResponseUtil.exception("约课失败!您已预约此时段的课程!不能重复预约!");
            }
        }
        if(trainingEntityList.size()==1){
            TrainingEntity trainingEntity = trainingEntityList.get(0);

            MemberCardEntity memberCardOther = memberCardDao.getById(trainingEntity.getCardNo());
            logger.info(" *****   memberCardOther = {} ",memberCardOther);
            if(memberCardOther.getType().equals(CardTypeEnum.PT.getKey())){
                return ResponseUtil.exception("约课失败!该时段课程已约满!");
            }else{
                //如果已经预约的会员 是用月卡约的 ， 需要判断本次是否也是月卡  ，如果是月卡可以预约 ， 如果是次卡  不能预约
                if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())){
                    return ResponseUtil.exception("该时段课程因属于双学员课程，只能再由一个私教月卡会员预约!您为私教次卡不能预约!");
                }
            }
        }
        if(trainingEntityList.size()>1){
            return ResponseUtil.exception("该时段课程属于双学员课程，且已经约满，不能预约!");
        }
        TrainingEntity  trainingEntity = new TrainingEntity();
        trainingEntity.setTrainingId(IDUtils.getId());
        trainingEntity.setMemberId(lesson.getMemberId());


        if(coachId==null){
            return ResponseUtil.exception("约课失败!教练信息获取失败!");
        }

        MemberEntity memberEntity = memberDao.getById(lesson.getMemberId());
        StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());

        trainingEntity.setCoachId(coachId);
        trainingEntity.setStartHour(Integer.parseInt(startHour));
        trainingEntity.setEndHour(Integer.parseInt(endHour));
        trainingEntity.setLessonDate(lessonDate);
        trainingEntity.setLessonId(lessonId);
        trainingEntity.setType(type);
        trainingEntity.setTitle("私教课");
        trainingEntity.setStaffId(staffEntity.getStaffId());
        trainingEntity.setStoreId(staffEntity.getStoreId());
        trainingEntity.setCardNo(memberCardEntity.getCardNo());
        trainingEntity.setCardType(memberCardEntity.getType());
        int n = trainingDao.add(trainingEntity);
        if(n > 0){
            n = memberCardDao.reduceCount(memberCardEntity.getCardNo());
            try{
                smsUtil.sendLessonNotice(staffEntity.getPhone(),"【"+memberEntity.getName()+"】",lessonDate+" "+trainingEntity.getStartHour().toString().replaceAll("00",":00"),"私教课");
            }catch (Exception e){

            }
            return ResponseUtil.success("约课成功");
        }
        return ResponseUtil.exception("约课失败!该时段课程已约满!");
    }

    public ResponseEntity<String> cancel(Lesson lesson) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" cancel  memberRequest = {}",memberRequest);
        logger.info(" cancel  lesson = {}",lesson);
        Staff staffRequest = RequestContextHelper.getStaff();
        logger.info(" cancel  staffRequest = {}",staffRequest);

        if(StringUtils.isEmpty(lesson.getMemberId())){
            lesson.setMemberId(memberRequest.getMemberId());
        }


        MemberEntity memberEntity = memberDao.getById(lesson.getMemberId());

        boolean manageFlag = false;
        if(StringUtils.isNotEmpty(memberRequest.getStaffId())){
            manageFlag = true;
        }
        logger.info(" cancel  manageFlag = {}",manageFlag);

        TrainingEntity trainingEntity = trainingDao.getById(lesson.getTrainingId());
        if(trainingEntity==null){
            return ResponseUtil.exception("取消约课异常!");
        }

        //如果已经过期了
        if(ut.passDayByDate(ut.currentDate(),trainingEntity.getLessonDate())<0){
            if(manageFlag){
                if(StringUtils.isNotEmpty(trainingEntity.getSignTime())){
                    return ResponseUtil.exception("取消约课失败!该课程已签到，不能取消！签到时间："+trainingEntity.getSignTime());
                }
            }else{
                return ResponseUtil.exception("取消约课失败!该课程已过期");
            }

            if(!ut.currentFullMonth().equals(lesson.getLessonDate().substring(0,7))){
                return ResponseUtil.exception("取消约课失败!该课程不是当月课时,不能取消!");
            }
        }

        // 如果是课程当天
        if(ut.currentDate().equals(trainingEntity.getLessonDate())) {
            if(manageFlag){

            }else{
                if((trainingEntity.getStartHour()-ut.currentHour())<600){
                    return ResponseUtil.exception("取消约课失败!上课前6小时无法取消预约!如需取消请联系你的教练");
                }
            }
        }

        StaffEntity staffEntity = staffDao.getById(trainingEntity.getStaffId());

        if(trainingEntity.getMemberId().equals(lesson.getMemberId())){
            TrainingEntity trainingUpdate = new TrainingEntity();
            trainingUpdate.setTrainingId(trainingEntity.getTrainingId());
            trainingUpdate.setStatus(-1);
            trainingUpdate.setShowTag(-1);
            int n = trainingDao.update(trainingUpdate);
            if(n==1){
                n = memberCardDao.addCount(trainingEntity.getCardNo());
                try{
                    logger.info(" staff = {} , ",staffEntity);
                    logger.info(" trainingEntity = {} , ",trainingEntity);

                    smsUtil.sendCancelLessonNotice(staffEntity.getPhone(),"【"+memberEntity.getName()+"】",lesson.getLessonDate()+" "+trainingEntity.getStartHour().toString().replaceAll("00",":00"),trainingEntity.getTitle());
                }catch (Exception e){

                    e.printStackTrace();
                }
                return ResponseUtil.success("取消成功!");
            }
        }
        return ResponseUtil.exception("取消约课失败!");

    }


    public ResponseEntity<String> scheduleCoach(LessonQuery query) {
        Member coachRequest = RequestContextHelper.getMember();
        List<Lesson> lessonList = new ArrayList();

        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setLessonDate(query.getLessonDate());
        trainingQuery.setCoachId(coachRequest.getMemberId());
        trainingQuery.setStatus(0);
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);
        logger.info(" scheduleCoach  trainingEntityList.size() = {} ",trainingEntityList.size());
//        for (TrainingEntity trainingEntity:trainingEntityList){
//            logger.info(" trainingEntity = {} ",trainingEntity);
//        }

        CoachRestQuery coachRestQuery = new CoachRestQuery();
        coachRestQuery.setCoachId(coachRequest.getMemberId());
        List<CoachRestEntity> coachRestEntityList = coachRestDao.find(coachRestQuery,page);
        logger.info(" scheduleCoach  coachRestEntityList.size() = {} ",coachRestEntityList.size());
        List<CoachRestEntity> filterCoachRestEntityList = new ArrayList<>();
        for (CoachRestEntity coachRestEntity:coachRestEntityList){
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.ONCE.getKey())){
                if(coachRestEntity.getRestDate().equals(query.getLessonDate())){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.DAY.getKey())){
                filterCoachRestEntityList.add(coachRestEntity);
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.WEEK.getKey())){
                int index1 = ut.indexOfWeek(coachRestEntity.getRestDate());
                int index2 = ut.indexOfWeek(query.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
            if(coachRestEntity.getType().equals(CoachRestTypeEnum.MONTH.getKey())){
                int index1 = ut.indexOfMonth(coachRestEntity.getRestDate());
                int index2 = ut.indexOfMonth(query.getLessonDate());
                if(index1==index2){
                    filterCoachRestEntityList.add(coachRestEntity);
                }
            }
        }
        logger.info(" scheduleCoach  filterCoachRestEntityList.size() = {} ",filterCoachRestEntityList.size());

        for (int i = 0; i < Const.times.size(); i++) {
            int startHour = Const.times.get(i);
            int endHour = startHour+100;
            Lesson lesson = new Lesson();
            lesson.setType("P");
            lesson.setStartHour(startHour);
            lesson.setEndHour(endHour);
            lesson.setCoachId(query.getCoachId());
            lesson.setLessonDate(query.getLessonDate());
            lesson.setLessonId(createLessonId(lesson));
            lesson.setMyOrder("0");
            lesson.setQuota(2);
            lesson.setTrainingId("");
            lesson.setTrainingList(new ArrayList<>());
            for (TrainingEntity trainingEntity:trainingEntityList){
//                logger.info(" ***********  getLessonDate = {} ,  getMemberId = {} ,  trainingEntity = {} ",trainingEntity.getLessonDate() , query.getMemberId() , trainingEntity.getMemberId());
//                logger.info(" ***********  trainingEntity.getStartHour() = {} ,  lesson.getStartHour() = {} ",trainingEntity.getStartHour() , lesson.getStartHour());
                if(trainingEntity.getStartHour().equals(lesson.getStartHour())){
                    lesson.getTrainingList().add(trainingService.transferTraining(trainingEntity));
                }
            }
            lesson.setStatus(0);  // 0 - 正常 ，   -1 - 不可约
            for (CoachRestEntity coachRestEntity:filterCoachRestEntityList){
                if( startHour >= coachRestEntity.getEndHour()  || startHour < coachRestEntity.getStartHour() ){

                }else{
                    lesson.setTitle(coachRestEntity.getTitle());
                    lesson.setStatus(-1);
                    break;
                }
            }
            lesson.setType(LessonTypeEnum.P.getKey());
            lessonList.add(lesson);
        }

        for (Lesson lesson:lessonList){
//            logger.info(" lesson = {} ",lesson);
        }
        JSONObject jo = new JSONObject();
        jo.put("lessonList", lessonList);
        return ResponseUtil.success("查询教练课程表成功",lessonList);
    }

}

