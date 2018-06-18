package com.training.service;

import com.alibaba.fastjson.JSONObject;
import com.training.common.*;
import com.training.dao.CardDao;
import com.training.dao.MemberCardDao;
import com.training.dao.MemberDao;
import com.training.dao.StaffDao;
import com.training.domain.Lesson;
import com.training.domain.Member;
import com.training.domain.Training;
import com.training.entity.*;
import com.training.domain.User;
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
    private CardDao cardDao;

    @Autowired
    private JwtUtil jwt;

    @Autowired
    private MemberCardDao memberCardDao;

    /**
     * 新增实体
     * @param member
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public ResponseEntity<String> add(MemberEntity member){
        User user = RequestContextHelper.getUser();
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
        List<MemberEntity> memberList = memberDao.find(query,page);
        List<Member> data = new ArrayList<>();
        for (MemberEntity trainingEntity : memberList){
            Member member = transferMember(trainingEntity);
            member.setCardType("私教次卡,团体月卡");
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

    private Member transferMember(MemberEntity memberEntity) {
        if(memberEntity==null){
            return null;
        }
        Member member = new Member();
        BeanUtils.copyProperties(memberEntity,member);
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

        // todo 发送手机验证码


        return ResponseUtil.success("发送验证码成功");
    }


    /**
     * 根据手机号码绑定会员
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    public ResponseEntity<String> bind(Member member) {
        JSONObject jo = new JSONObject();
        if(StringUtils.isEmpty(member.getCode()) || !member.getCode().equals("1234")){
            return ResponseUtil.exception("手机验证码错误!");
        }
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
                    memberEntity.setType("C");
                    memberEntity.setName(staffDB.getCustname());
                    memberEntity.setStoreId(staffDB.getStoreId());
                    staffDB.setOpenId(openId);
                    int n = staffDao.bind(staffDB);
                    logger.info("  bind  staffDao.bind  n = {} ",n);
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
        List<MemberCardEntity> validCardList = new ArrayList<>();
        for (MemberCardEntity memberCardEntity : cardList){


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
            if(memberCardEntity.getCount()<1){
                continue;
            }
            cardTypeSet.add(cardType);
            Lesson lesson = new Lesson();
            lesson.setType(cardType);
            MemberEntity coachEntity = memberDao.getById(memberCardEntity.getCoachId());
            lesson.setCoachId(memberCardEntity.getCoachId());
            lesson.setTitle("私教课");
            lesson.setCoachName(coachEntity.getName());
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
            logger.info("  lesson"+i+" = {} ",lesson );
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


//        int n = memberDao.update(memberUpdate);
//        if(n==1){
            return ResponseUtil.success("签到成功");
//        }
//        return ResponseUtil.exception("签到失败");
    }

}

