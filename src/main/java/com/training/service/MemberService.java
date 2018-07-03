package com.training.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.training.common.*;
import com.training.dao.*;
import com.training.domain.*;
import com.training.entity.*;
import com.training.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * member 核心业务操作类
 * Created by huai23 on 2018-05-26 13:33:17.
 */ 
@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private JwtUtil jwt;

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private MemberPauseDao memberPauseDao;

    @Autowired
    private MemberLogDao memberLogDao;

    /**
     * 新增实体
     * @param member
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public ResponseEntity<String> add(MemberEntity member){
        if(StringUtils.isNotEmpty(member.getPhone())){
            MemberEntity memberDB = this.getByPhone(member.getPhone());
            if(memberDB!=null){
                return ResponseUtil.exception("添加失败,手机号码已存在 ： "+member.getPhone());
            }
        }
        member.setMemberId(IDUtils.getId());
        int n = memberDao.add(member);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public Page<Member> find(MemberQuery query , PageRequest page){
        logger.info("  find  MemberQuery = {}",query);

        Staff staffRequest = RequestContextHelper.getStaff();
        StaffEntity staffDB = staffDao.getById(staffRequest.getStaffId());
        RoleEntity roleEntity = roleDao.getById(staffDB.getRoleId());
        if(StringUtils.isEmpty(query.getStoreId())){
            if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                query.setStoreId(roleEntity.getStoreData());
            }else{
                query.setStoreId("123456789");
            }
            if(staffDB.getUsername().equals("admin")){
                query.setStoreId(null);
            }
        }else{
            if(staffDB.getUsername().equals("admin")){

            }else{
                if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                    String[] stores = roleEntity.getStoreData().split(",");
                    Set<String> ids = new HashSet<>();
                    for (int i = 0; i < stores.length; i++) {
                        if(StringUtils.isNotEmpty(stores[i])){
                            ids.add(stores[i]);
                        }
                    }
                    if(ids.contains(query.getStoreId())){

                    }else{
                        query.setStoreId("123456789");
                    }
                }else{
                    query.setStoreId("123456789");
                }
            }
        }

        logger.info("  find  MemberQuery = {}",query);
        List<MemberEntity> memberList = memberDao.find(query,page);
        List<Member> data = new ArrayList<>();
        for (MemberEntity trainingEntity : memberList){
            Member member = transferMember(trainingEntity);
            data.add(member);
        }
        Long count = memberDao.count(query);
        Page<Member> returnPage = new Page<>();
        returnPage.setContent(data);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    public Member transferMember(MemberEntity memberEntity) {
        if(memberEntity==null){
            return null;
        }
        Member member = new Member();
        BeanUtils.copyProperties(memberEntity,member);
        StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
        if(staffEntity==null){
            member.setCoachName("暂无教练");
        }else{
            member.setCoachName(staffEntity.getCustname());
        }
        member.setCardType("私教次卡");

        MemberLogQuery memberLogQuery = new MemberLogQuery();
        memberLogQuery.setMemberId(memberEntity.getMemberId());
        PageRequest page = new PageRequest();
        page.setPageSize(1);

        List<MemberLogEntity> memberLogEntityList = memberLogDao.find(memberLogQuery,page);
        if(memberLogEntityList.size()>0){
            member.setMemberLog(memberLogEntityList.get(0));
        }else{
            member.setMemberLog(new MemberLogEntity());
        }
        return member;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public Long count(MemberQuery query){
        Long count = memberDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public MemberEntity getById(String id){
        MemberEntity memberDB = memberDao.getById(id);
        return memberDB;
    }

    /**
     * 根据ID查询实体
     * @param openId
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public MemberEntity getByOpenId(String openId){
        if(StringUtils.isEmpty(openId)){
            return null;
        }
        MemberEntity memberDB = memberDao.getByOpenId(openId);
        return memberDB;
    }

    /**
     * 根据ID查询实体
     * @param phone
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public MemberEntity getByPhone(String phone){
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        MemberEntity memberDB = memberDao.getByPhone(phone);
        return memberDB;
    }

    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public  ResponseEntity<String> update(MemberEntity member){
        if(StringUtils.isNotEmpty(member.getPhone())){
            MemberEntity memberDB = this.getByPhone(member.getPhone());
            if(memberDB!=null){
                return ResponseUtil.exception("修改失败,手机号码已存在 ： "+member.getPhone());
            }
        }
        int n = memberDao.update(member);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    /**
     * 根据手机号码发送验证码
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    public ResponseEntity<String> sendCode(Member member) {
        Member memberToken = RequestContextHelper.getMember();
        logger.info(" memberRestController  sendCode  getOpenId = {}",memberToken.getOpenId());
        logger.info(" memberRestController  sendCode  getPhone = {}",member.getPhone());
        if(StringUtils.isEmpty(member.getPhone())||member.getPhone().length()!=11){
            return ResponseUtil.exception("手机号码输入有误!");
        }
        String code = IDUtils.getVerifyCode();
//        code = "1234";
        Const.validCodeMap.put(member.getPhone(),code+"_"+System.currentTimeMillis()+"_"+ut.currentTime());
        try {
            SmsUtil.sendCode(member.getPhone(),code);
            logger.info(" memberRestController  sendCode  getPhone = {} , code = {} ",member.getPhone(),code);
            return ResponseUtil.success("发送验证码成功");
        } catch (ClientException e) {
            e.printStackTrace();
            return ResponseUtil.exception("发送验证码失败");
        }
    }


    /**
     * 根据手机号码绑定会员
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    public ResponseEntity<String> bind(Member member) {
        JSONObject jo = new JSONObject();

        logger.info(" bind    member : {} " , member);

        logger.info(" validCodeMap : {} " , Const.validCodeMap);

        if(StringUtils.isEmpty(member.getCode())){
            return ResponseUtil.exception("请输入手机验证码");
        }
        if(!Const.validCodeMap.containsKey(member.getPhone())){
            return ResponseUtil.exception("验证码无效");
        }
        String[] codes = Const.validCodeMap.get(member.getPhone()).split("_");
        logger.info(" code : {} " ,codes[0]);
        logger.info(" time : {} " ,codes[1]);

        long time = Long.parseLong(codes[1]);
        long now = System.currentTimeMillis();

        logger.info(" time : {} " ,time);
        logger.info(" now : {} " ,now);

        if(!member.getCode().equals(codes[0])){
            return ResponseUtil.exception("手机验证码错误!");
        }

        Const.validCodeMap.remove(member.getPhone());

        Member memberRequest = RequestContextHelper.getMember();
        String openId = memberRequest.getOpenId();
        logger.info("  bind  getOpenId = {} , getPhone = {} , code = {} ",openId,member.getPhone(),member.getCode());
        MemberEntity memberEntity = getByOpenId(openId);
        if(memberEntity==null){
            memberEntity = getByPhone(member.getPhone());
            if(memberEntity==null){
                memberEntity = new MemberEntity();
                memberEntity.setMemberId(IDUtils.getId());
                memberEntity.setName(member.getPhone());
                memberEntity.setPhone(member.getPhone());
                memberEntity.setOpenId(openId);
                memberEntity.setType("M");
                StaffEntity staffDB = staffDao.getByPhone(member.getPhone());
                if( staffDB!=null && StringUtils.isEmpty(staffDB.getOpenId())){
                    if("教练".equals(staffDB.getJob())||"店长".equals(staffDB.getJob())||"CEO".equals(staffDB.getJob())){
                        memberEntity.setType("C");
                        memberEntity.setName(staffDB.getCustname());
                        memberEntity.setStoreId(staffDB.getStoreId());
                        staffDB.setOpenId(openId);
                        int n = staffDao.bind(staffDB);
                        logger.info("  bind  staffDao.bind  n = {} ",n);
                    }
                }
                memberEntity.setOrigin("自动生成");
                int n = memberDao.add(memberEntity);
                if(n!=1){
                    return ResponseUtil.exception("创建新会员失败!");
                }
                memberEntity = this.getById(memberEntity.getMemberId());
            }else{
                memberEntity.setOpenId(openId);
                int n = memberDao.bind(memberEntity);
            }
        }
        Member memberResult = new Member();
        memberResult = new Member();
        memberResult.setMemberId(memberEntity.getMemberId());
        memberResult.setType(memberEntity.getType());
        jo.put("member", memberEntity);
        String subject = JwtUtil.generalSubject(memberRequest.getOpenId(),memberResult);
        try {
            String token = jwt.createJWT(Const.JWT_ID, subject, Const.JWT_TTL);
            jo.put("token", token);
            logger.info("  bind  token = {} ",token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.success(jo);
    }

    public ResponseEntity<String> getValidLessonType(String memberId) {
        if(StringUtils.isEmpty(memberId)){
            return ResponseUtil.exception("会员信息查询异常");
        }

        MemberCardQuery query = new MemberCardQuery();
        query.setMemberId(memberId);
        query.setStatus(0);
        query.setStartDate(ut.currentDate());
        query.setEndDate(ut.currentDate());
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<MemberCardEntity> cardList = memberCardDao.find(query,page);

        logger.info("  getValidLessonType  cardList.size() = {} ",cardList.size());


        List<MemberCardEntity> validCardList = new ArrayList<>();
        for (MemberCardEntity memberCardEntity : cardList){
            if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())||memberCardEntity.getType().equals(CardTypeEnum.TY.getKey())){
                if(memberCardEntity.getCount()<=0){
//                    if(memberCardEntity.getCount()<=0 && ut.passDayByDate(ut.currentDate(),memberCardEntity.getEndDate())<0){

//                        continue;
                }
            }
            validCardList.add(memberCardEntity);
        }
        if(CollectionUtils.isEmpty(validCardList)){
            return ResponseUtil.exception("无可用课时,请先购卡!");
        }

        Set<String> cardTypeSet = new HashSet();
        List<Lesson>  types = new ArrayList<>();
        for (MemberCardEntity memberCardEntity : validCardList){
            String cardType = memberCardEntity.getType().substring(0,1);
            // 体验卡等同于私教次卡
            if(memberCardEntity.getType().equals(CardTypeEnum.TY.getKey())){
                cardType = "P";
            }
            if(!cardType.equals("P")){
                continue;
            }
            if(cardTypeSet.contains(cardType)){
                continue;
            }
            if(memberCardEntity.getCount()<=0 && ut.passDayByDate(ut.currentDate(),memberCardEntity.getEndDate())<0) {
                continue;
            }
            if(memberCardEntity.getCount()<1){
//                continue;
            }
            cardTypeSet.add(cardType);
            Lesson lesson = new Lesson();
            lesson.setType(cardType);
            MemberEntity memberEntity = memberDao.getById(memberId);
            StaffEntity staff = staffDao.getById(memberEntity.getCoachStaffId());
            logger.info(" ============ staff = {}",staff);

            if(staff == null){
                return ResponseUtil.exception("还未给您分配教练，请稍后再试");
            }

            String coachId = getCoachIdByMemberId(query.getMemberId());
            logger.info(" ============ coachId = {}",coachId);

            if(coachId == null){
                return ResponseUtil.exception("教练暂时未绑定微信，请联系客服或稍后再试");
            }
            MemberEntity coach = memberDao.getById(coachId);
            lesson.setCoachId(memberCardEntity.getCoachId());
            lesson.setTitle("私教课");
            lesson.setCoachImage(coach.getImage());
            lesson.setCoachName(staff.getCustname());
            types.add(lesson);
        }

        for (MemberCardEntity memberCardEntity : validCardList){
            String cardType = memberCardEntity.getType().substring(0,1);
            // 体验卡等同于团体次卡
            if(memberCardEntity.getType().equals(CardTypeEnum.TY.getKey())){
                cardType = "T";
            }
            if(!cardType.equals("T")){
                continue;
            }
            if(cardTypeSet.contains(cardType)){
                continue;
            }
            cardTypeSet.add(cardType);
            Lesson lesson = new Lesson();
            lesson.setType(cardType);
            lesson.setCardNo(memberCardEntity.getCardNo());
//            CardEntity cardEntity = cardDao.getById(memberCardEntity.getCardId());
//            MemberEntity coachEntity = memberDao.getById(memberCardEntity.getCoachId());
            lesson.setCoachId("");
            lesson.setTitle("团体课");
            lesson.setCoachName("");
            types.add(lesson);
        }

        for (MemberCardEntity memberCardEntity : validCardList){
            String cardType = memberCardEntity.getType().substring(0,1);
            if(!cardType.equals("S")){
                continue;
            }
            if(cardTypeSet.contains(cardType)){
                continue;
            }
            cardTypeSet.add(cardType);
            Lesson lesson = new Lesson();
            lesson.setType(cardType);
            MemberEntity coachEntity = memberDao.getById(memberCardEntity.getCoachId());
            lesson.setCoachId(memberCardEntity.getCoachId());
            lesson.setTitle("特色课");
            lesson.setCoachName(coachEntity.getName());
            types.add(lesson);
        }

        for (int i = 0; i < types.size(); i++) {
            Lesson lesson = types.get(i);
//            logger.info("  lesson"+i+" = {} ",lesson );
        }

        return ResponseUtil.success(types);
    }


    /**
     * 将微信用户设置成教练
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @Transactional
    public ResponseEntity<String> bindCoach(Member member) {
        if(member==null||StringUtils.isEmpty(member.getMemberId())||StringUtils.isEmpty(member.getStaffId())) {
            return ResponseUtil.exception("参数错误!");
        }
        logger.info("  bindCoach  getMemberId = {} , getStaffId = {} ",member.getMemberId(),member.getStaffId());
        MemberEntity memberEntity = this.getById(member.getMemberId());
        if(StringUtils.isEmpty(memberEntity.getOpenId())){
            return ResponseUtil.exception("该会员不是微信用户，不能设置成教练!");
        }
        StaffEntity staffEntity = staffDao.getById(member.getStaffId());
        if(memberEntity==null||staffEntity==null) {
            return ResponseUtil.exception("设置教练异常!");
        }

        if(StringUtils.isNotEmpty(staffEntity.getOpenId())) {
            MemberEntity memberNow = memberDao.getByOpenId(staffEntity.getOpenId());
//            return ResponseUtil.exception("设置教练异常!该员工已经绑定微信用户:"+memberNow.getName());
        }

        staffEntity.setOpenId(memberEntity.getOpenId());
        int n = staffDao.bind(staffEntity);
        logger.info("  bindCoach  staffDao.bind  n = {} ",n);
        MemberEntity memberUpdate = new MemberEntity();
        memberUpdate.setMemberId(memberEntity.getMemberId());
        memberUpdate.setPhone(staffEntity.getPhone());
        memberUpdate.setName(staffEntity.getCustname());
        memberUpdate.setStoreId(staffEntity.getStoreId());
        memberUpdate.setType("C");
        n = memberDao.update(memberUpdate);
        logger.info("  bindCoach  memberDao.update  n = {} ",n);
        if(n>0){
            return ResponseUtil.success("设置教练成功");
        }
        return ResponseUtil.exception("设置教练失败!");
    }

    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public  ResponseEntity<String> modify(MemberEntity member){
        Member memberRequest = RequestContextHelper.getMember();
        logger.info("  modify  memberRequest = {}",memberRequest);
        logger.info("  modify  member = {}",member);
        if(memberRequest==null||StringUtils.isEmpty(memberRequest.getMemberId())){
            return ResponseUtil.exception("修改异常");
        }
        boolean flag = false;
        MemberEntity memberUpdate = new MemberEntity();
        memberUpdate.setMemberId(memberRequest.getMemberId());
        if(member.getHeight()!=null){
            memberUpdate.setHeight(member.getHeight());
            flag = true;
        }
        if(member.getAge()!=null){
            memberUpdate.setAge(member.getAge());
            flag = true;
        }
        if(member.getGender()!=null){
            memberUpdate.setGender(member.getGender());
            flag = true;
        }
        logger.info("  modify  memberUpdate = {}",memberUpdate);
        if(flag){
            int n = memberDao.update(memberUpdate);
            if(n==1){
                return ResponseUtil.success("修改成功");
            }
        }
        return ResponseUtil.success("修改完成");
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public  ResponseEntity<String> signIn(Training training) {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info("  signIn  memberRequest = {}", memberRequest);
        logger.info("  signIn  training = {}", training);
        TrainingEntity trainingEntity = trainingDao.getById(training.getTrainingId());
        if(trainingEntity==null){
            return ResponseUtil.exception("签到异常");
        }
        int n = trainingDao.signIn(trainingEntity);
        if(n==1){
            return ResponseUtil.success("签到成功");
        }
        return ResponseUtil.exception("签到失败");
    }

    public ResponseEntity<String> changeCoach(Member member) {
        Staff staffRequest = RequestContextHelper.getStaff();
        logger.info("  changeCoach  staffRequest = {}", staffRequest);
        logger.info("  changeCoach  member = {}", member);

        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setStartDate(ut.currentDate());
        trainingQuery.setMemberId(member.getMemberId());
        trainingQuery.setStatus(0);
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<TrainingEntity> trainingEntityList = trainingDao.find(trainingQuery,page);
        logger.info(" changeCoach  trainingEntityList.size() = {} ",trainingEntityList.size());
        for (TrainingEntity trainingEntity:trainingEntityList){
            logger.info(" trainingEntity = {} ",trainingEntity);
            if(trainingEntity.getLessonDate().equals(ut.currentDate())){
                if(trainingEntity.getStartHour()>ut.currentHour()){
                    return ResponseUtil.exception("更换教练失败,请先取消当前的所有预约课程");
                }
            }else{
                return ResponseUtil.exception("更换教练失败,请先取消现有教练的预约课程");
            }
        }
        StaffEntity staffEntity = staffDao.getById(member.getStaffId());
        logger.info("  changeCoach  staffEntity = {}", staffEntity);
        MemberEntity memberUpdate = new MemberEntity();
        memberUpdate.setMemberId(member.getMemberId());
        memberUpdate.setCoachStaffId(staffEntity.getStaffId());
        logger.info("  changeCoach  memberUpdate = {}",memberUpdate);
        int n = memberDao.update(memberUpdate);
        if(n==1){
            return ResponseUtil.success("更换教练成功");
        }
        return ResponseUtil.exception("更换教练失败");
    }

    public Page<Member> memberList(MemberQuery query) {
        Member memberRequest = RequestContextHelper.getMember();
        MemberEntity memberDB = memberDao.getById(memberRequest.getMemberId());
        StaffEntity staffEntity = staffDao.getByPhone(memberDB.getPhone());
        if(staffEntity==null){
            query.setCoachStaffId(IDUtils.getId());
        }else{
            query.setCoachStaffId(staffEntity.getStaffId());
        }
        query.setType("M");
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<MemberEntity> memberList = memberDao.find(query,page);
        List<Member> data = new ArrayList<>();
        for (MemberEntity trainingEntity : memberList){
            Member member = transferMember(trainingEntity);
            data.add(member);
        }

        logger.info("  memberList  data.size() = {}", data.size());

        Long count = memberDao.count(query);
        Page<Member> returnPage = new Page<>();
        returnPage.setContent(data);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    public ResponseEntity<String> getCanUseCardList(Lesson lesson) {
        logger.info("  getCanUseCardList  lesson = {}", lesson);
        List cards = new ArrayList();
        MemberCardQuery query = new MemberCardQuery();
        if(StringUtils.isEmpty(lesson.getMemberId())){
            logger.info("  getCanUseCardList  getMemberId = {}", lesson.getMemberId());
            return ResponseUtil.success(cards);
        }
        query.setMemberId(lesson.getMemberId());
        query.setStatus(0);
        query.setStartDate(ut.currentDate());
        query.setEndDate(ut.currentDate());
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<MemberCardEntity> cardList = memberCardDao.find(query,page);
        logger.info("  getCanUseCardList   cardList.size() = {}", cardList.size());

        List<MemberCardEntity> validCardList = new ArrayList<>();
        for (MemberCardEntity memberCardEntity : cardList){
            if(LessonTypeEnum.P.getKey().equals(lesson.getType())){
                if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())
                        ||memberCardEntity.getType().equals(CardTypeEnum.TY.getKey())){

                    if(memberCardEntity.getCount()>0){
                        validCardList.add(memberCardEntity);
                    }
                }

                if(memberCardEntity.getType().equals(CardTypeEnum.PM.getKey())){
                    validCardList.add(memberCardEntity);
                }

            }

            if(LessonTypeEnum.T.getKey().equals(lesson.getType())){
                if(memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())
                        ||memberCardEntity.getType().equals(CardTypeEnum.TY.getKey())){

                    if(memberCardEntity.getCount()>0){
                        validCardList.add(memberCardEntity);
                    }
                }

                if(memberCardEntity.getType().equals(CardTypeEnum.TM.getKey())){
                    validCardList.add(memberCardEntity);
                }
            }

        }

        logger.info("  getCanUseCardList   cardList.size() = {}", cardList.size());

        for (MemberCardEntity memberCardEntity : cardList){
            MemberCard card = memberCardService.transfer(memberCardEntity);
            cards.add(card);
        }

        return ResponseUtil.success(cards);

    }

    public String getCoachIdByMemberId(String memberId) {
        MemberEntity memberEntity = this.getById(memberId);
        if(memberEntity==null){
            return null;
        }
        StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
        if(staffEntity==null){
            return null;
        }
        MemberEntity coach = this.getByOpenId(staffEntity.getOpenId());
        if(coach==null){
            return null;
        }
        return coach.getMemberId();
    }

    public String getCoachIdBStaffId(String staffId) {
        if(StringUtils.isEmpty(staffId)){
            return null;
        }
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(staffEntity==null){
            return null;
        }
        MemberEntity coach = this.getByOpenId(staffEntity.getOpenId());
        if(coach==null){
            return null;
        }
        return coach.getMemberId();
    }

    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public  ResponseEntity<String> updateImage(MemberEntity member){
        Member memberRequest = RequestContextHelper.getMember();
//        logger.info("  updateImage  memberRequest = {}",memberRequest);
//        logger.info("  updateImage  member = {}",member);
        if(memberRequest==null||StringUtils.isEmpty(memberRequest.getMemberId())){
            return ResponseUtil.exception("修改异常");
        }
        boolean flag = false;
        MemberEntity memberUpdate = new MemberEntity();
        memberUpdate.setMemberId(memberRequest.getMemberId());
        if(StringUtils.isNotEmpty(member.getNickname())){
//            memberUpdate.setNickname(member.getNickname());
//            flag = true;
        }
        if(StringUtils.isNotEmpty(member.getImage())){
            memberUpdate.setImage(member.getImage());
            flag = true;
        }
//        logger.info("  updateImage  memberUpdate = {}",memberUpdate);
        if(flag){
            int n = memberDao.update(memberUpdate);
            if(n==1){
                return ResponseUtil.success("修改成功");
            }
        }
        return ResponseUtil.success("修改完成");
    }

    @Transactional
    public ResponseEntity<String> pauseMember(Member member) {
        Staff staffRequest = RequestContextHelper.getStaff();
        logger.info("  pauseMember  staffRequest = {}", staffRequest);
        logger.info("  pauseMember  member = {}", member);
        if(staffRequest==null){
            return ResponseUtil.exception("停课异常");
        }
        if(member==null||StringUtils.isEmpty(member.getMemberId())){
            return ResponseUtil.exception("停课参数异常");
        }

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(member.getMemberId());
        memberEntity.setStatus(9);   //  暂停

        MemberPauseEntity memberPauseEntity = new MemberPauseEntity();
        memberPauseEntity.setMemberId(member.getMemberId());
        memberPauseEntity.setPauseDate(ut.currentTime());
        memberPauseEntity.setPauseStaffId(staffRequest.getStaffId());
        int n = memberPauseDao.add(memberPauseEntity);
        if(n==1){
            n = memberDao.update(memberEntity);
            return ResponseUtil.success("停课成功");
        }
        return ResponseUtil.exception("停课失败");
    }


    public ResponseEntity<String> restoreMember(Member member) {
        Staff staffRequest = RequestContextHelper.getStaff();
        logger.info("  restoreMember  staffRequest = {}", staffRequest);
        logger.info("  restoreMember  member = {}", member);
        if(staffRequest==null){
            return ResponseUtil.exception("恢复异常");
        }
        if(member==null||StringUtils.isEmpty(member.getMemberId())){
            return ResponseUtil.exception("恢复参数异常");
        }

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(member.getMemberId());
        memberEntity.setStatus(0);
        int n = memberDao.update(memberEntity);
        if(n==1){
            return ResponseUtil.success("恢复成功");
        }
        return ResponseUtil.exception("恢复失败");
    }

    public ResponseEntity<String> changeRole(Staff staff) {
        Staff staffRequest = RequestContextHelper.getStaff();
        logger.info("  restoreMember  staffRequest = {}", staffRequest);
        logger.info("  restoreMember  staff = {}", staff);

        StaffEntity staffEntity = staffDao.getById(staff.getStaffId());
        if(staffEntity==null||StringUtils.isEmpty(staff.getRoleId())){
            return ResponseUtil.exception("设置角色异常");

        }
        StaffEntity staffUpdate =  new StaffEntity();
        staffUpdate.setRoleId(staff.getRoleId());
        staffUpdate.setStaffId(staff.getStaffId());
        int n = staffDao.update(staffUpdate);
        if(n>0){
            return ResponseUtil.success("设置角色成功");
        }
        return ResponseUtil.exception("设置角色失败");

    }


}

