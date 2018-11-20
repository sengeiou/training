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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.*;

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

    @Autowired
    private MemberMedalDao memberMedalDao;

    @Autowired
    private MedalDao medalDao;

    @Autowired
    private StaffMedalDao staffMedalDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增实体
     * @param member
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public ResponseEntity<String> add(MemberEntity member){
        if(StringUtils.isNotEmpty(member.getPhone())){
            MemberEntity memberDB = this.getByPhone(member.getPhone());
            if(memberDB!=null){
                String coachStaffId = memberDB.getCoachStaffId();
                String storeName = "无教练";
                if(StringUtils.isNotEmpty(coachStaffId)){
                    StaffEntity staffEntity = staffDao.getById(coachStaffId);
                    if(staffEntity!=null){
                        StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                        if(storeEntity!=null){
                            storeName = storeEntity.getName();
                        }
                    }
                }
                return ResponseUtil.exception("添加失败,手机号码已存在 ：会员姓名："+memberDB.getName()+" ,电话："+member.getPhone()+"，所属门店："+storeName);
            }
        }
        member.setMemberId(IDUtils.getId());
        int n = memberDao.add(member);
        if(n==1){

            MemberEntity memberEntity = memberDao.getById(member.getMemberId());
            if(!StringUtils.isEmpty(member.getCoachStaffId())){
                StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                try {
                    smsUtil.sendAddMemberNotice(staffEntity.getPhone(), memberEntity.getName(),memberEntity.getPhone());
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                List<StaffEntity> managers = staffDao.getManagerByStoreId(staffEntity.getStoreId());
                for (StaffEntity manager : managers){
                    if(StringUtils.isNotEmpty(manager.getPhone()) && !manager.getPhone().equals(staffEntity.getPhone())){
                        try {
                            smsUtil.sendAddMemberNotice(manager.getPhone(), memberEntity.getName(),memberEntity.getPhone());
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

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
        logger.info("  find  MemberQuery111 = {} , page = {} ",query,page);

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
            query.setIsStoreQuery(1);
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

        logger.info("  find  MemberQuery222 = {} , page = {} ",query,page);
        List<MemberEntity> memberList = memberDao.find(query,page);
        List<Member> data = new ArrayList<>();
        for (MemberEntity memberEntity : memberList){
            Member member = transferMember(memberEntity);

            MemberCardQuery memberCardQuery = new MemberCardQuery();
            memberCardQuery.setType(CardTypeEnum.TY.getKey());
            memberCardQuery.setMemberId(memberEntity.getMemberId());
            PageRequest pageRequest = new PageRequest();
            List cards = memberCardDao.find(memberCardQuery,pageRequest);
            if(cards.size()>0){
                member.setHasTY(cards.size());
            }
            data.add(member);
        }
        Long count = memberDao.count(query);
        logger.info("  find  MemberQuery222  count = {} , data.size() = {} ",count,data.size());
        Page<Member> returnPage = new Page<>();
        returnPage.setContent(data);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }


    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public Page<Member> memberMedalList(MemberQuery query , PageRequest page){
        logger.info("  memberMedalList  MemberQuery = {}",query);

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
        for (MemberEntity memberEntity : memberList){
            Member member = transferMember(memberEntity);

            MemberCardQuery memberCardQuery = new MemberCardQuery();
            memberCardQuery.setType(CardTypeEnum.TY.getKey());
            memberCardQuery.setMemberId(memberEntity.getMemberId());
            PageRequest pageRequest = new PageRequest();
            List cards = memberCardDao.find(memberCardQuery,pageRequest);
            if(cards.size()>0){
                member.setHasTY(cards.size());
            }
            List<MemberMedalEntity> memberMedalEntityList = memberMedalDao.getByMemberId(memberEntity.getMemberId());
            List memberMedals = new ArrayList();

            int level_cq = 0;
            int level_tn = 0;
            MemberMedal memberMedalTN = null;
            MemberMedal memberMedalCQ = null;

            for (MemberMedalEntity memberMedalEntity:memberMedalEntityList){
                MemberMedal memberMedal = new MemberMedal();
                BeanUtils.copyProperties(memberMedalEntity,memberMedal);
                MedalEntity medalEntity = medalDao.getById(memberMedalEntity.getMedalId());
                memberMedal.setMedalName(medalEntity.getName());

                memberMedal.setLevel(medalEntity.getLevel());
                if(medalEntity.getMedalId().startsWith("SJ")){
                    int level = memberMedal.getLevel();
                    if(level>level_tn){
                        memberMedalTN = memberMedal;
                        level_tn = level;
                    }
                    continue;
                }
                if(medalEntity.getMedalId().startsWith("CQ")){
                    int level = memberMedal.getLevel();
                    if(level>level_cq){
                        memberMedalCQ = memberMedal;
                        level_cq = level;
                    }
                    continue;
                }

                memberMedals.add(memberMedal);
            }

            if(memberMedalTN!=null){
                memberMedals.add(memberMedalTN);
            }
            if(memberMedalCQ!=null){
                memberMedals.add(memberMedalCQ);
            }

            member.setMemberMedalList(memberMedals);
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
            if(memberDB!=null && !member.getMemberId().equals(memberDB.getMemberId())){
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
     * 根据ID闭环
     * @param id
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public ResponseEntity<String> close(String id){
        MemberEntity memberEntity = memberDao.getById(id);
        if(memberEntity.getStatus()!=0){
            return ResponseUtil.exception("只有意向会员才能转成闭环状态");
        }
        int n = memberDao.close(id);
        if(n==1){
            return ResponseUtil.success("闭环成功");
        }
        return ResponseUtil.exception("闭环操作失败");
    }

    /**
     * 根据ID恢复为意向
     * @param id
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public ResponseEntity<String> restore(String id){
        MemberEntity memberEntity = memberDao.getById(id);
        if(memberEntity.getStatus()!=-1){
            return ResponseUtil.exception("只有闭环会员才能恢复成意向状态");
        }
        int n = memberDao.restore(id);
        if(n==1){
            return ResponseUtil.success("恢复成功");
        }
        return ResponseUtil.exception("恢复操作失败");
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
            smsUtil.sendCode(member.getPhone(),code);
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
        }else {
            StaffEntity staffDB = staffDao.getByPhone(memberEntity.getPhone());
            if(staffDB!=null&&StringUtils.isNotEmpty(staffDB.getOpenId())){
                logger.info("  bindIsCoach 发现是教练 memberEntity.getPhone() = {} ",memberEntity.getPhone());
                staffDB.setOpenId(openId);
                int n = staffDao.bind(staffDB);
                MemberEntity memberUpdate = new MemberEntity();
                memberUpdate.setMemberId(memberEntity.getMemberId());
                memberUpdate.setType("C");
                memberUpdate.setName(staffDB.getCustname());
                memberUpdate.setStoreId(staffDB.getStoreId());
                memberDao.update(memberUpdate);
                logger.info("  bind  staffDao.bind  n = {} ",n);
                memberEntity = memberDao.getById(memberEntity.getMemberId());
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
            if(StringUtils.isNotEmpty(staff.getImage())){
                coach.setImage(staff.getImage());
            }
            lesson.setCoachImage(coach.getImage());
            lesson.setCoachName(staff.getCustname());

            List<StaffMedal> staffMedalList = staffMedalDao.queryStaffMedalList(staff.getStaffId());
            lesson.setStaffMedalList(staffMedalList);

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
            String cardType = memberCardEntity.getType();
            if(!memberCardEntity.getType().startsWith("S")){
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
            lesson.setTitle(CardTypeEnum.getEnumByKey(cardType).getDesc());
            lesson.setCoachName(coachEntity.getName());
            StaffEntity staff = staffDao.getByPhone(coachEntity.getPhone());
            logger.info(" ============ staff = {}",staff);
            if(StringUtils.isNotEmpty(staff.getImage())){
                coachEntity.setImage(staff.getImage());
            }
            lesson.setCoachImage(coachEntity.getImage());

            List<StaffMedal> staffMedalList = staffMedalDao.queryStaffMedalList(staff.getStaffId());
            lesson.setStaffMedalList(staffMedalList);

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
        String[] strs = training.getTrainingId().split("_");
        String time = strs[1];
        Long now = System.currentTimeMillis();
        logger.info("  signIn  time = {} , now = {} , now-time = {}  ", time,now,(now-Long.parseLong(time)));
        if(now-Long.parseLong(time)>15000){
            logger.error("  signIn  二维码已失效,请重新获取 ");
            return ResponseUtil.exception("二维码已失效,请重新获取");
        }
        TrainingEntity trainingEntity = trainingDao.getById(strs[0]);
        if(trainingEntity==null){
            return ResponseUtil.exception("签到异常");
        }
        if(!trainingEntity.getMemberId().equals(memberRequest.getMemberId())){
            logger.error("  signIn  只有学员本人的微信才能扫码签到 ");
            return ResponseUtil.exception("只有学员本人的微信才能扫码签到");
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

        MemberEntity memberEntity = memberDao.getById(member.getMemberId());

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



        MemberCardQuery queryCard = new MemberCardQuery();
        queryCard.setMemberId(member.getMemberId());
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100);
        List<MemberCardEntity> memberCardEntityList = memberCardDao.find(queryCard,pageRequest);
        logger.info("  changeCoach  memberCardEntityList.size() = {}",memberCardEntityList.size());

        boolean hasRealCard = false;
        for (MemberCardEntity memberCardEntity:memberCardEntityList){
            if(!memberCardEntity.getType().equals(CardTypeEnum.TY.getKey())){
                hasRealCard = true;
                break;
            }
        }
        logger.info("  changeCoach  hasRealCard = {}",hasRealCard);

        if(hasRealCard){
            SysLogQuery query = new SysLogQuery();
            query.setStartDate(ut.firstDayOfMonth());
            query.setEndDate(ut.lastDayOfMonth());
            query.setType(SysLogEnum.CC.getKey());
            query.setId1(member.getMemberId());

            logger.info(" query = {} ",query);
            List<SysLogEntity> sysLogEntities = sysLogDao.find(query,new PageRequest());
            logger.info(" sysLogEntities.size() = {} ",sysLogEntities.size());
            if(sysLogEntities.size()>0){
                SysLogEntity sysLogEntity = sysLogEntities.get(0);
//                return ResponseUtil.exception("更换教练失败,本月已经于"+ut.df_time.format(sysLogEntity.getCreated())+"更换过教练");
            }
        }

        StaffEntity staffEntity = staffDao.getById(member.getStaffId());
        logger.info("  changeCoach  staffEntity = {}", staffEntity);
        MemberEntity memberUpdate = new MemberEntity();
        memberUpdate.setMemberId(member.getMemberId());
        memberUpdate.setStoreId(staffEntity.getStoreId());
        memberUpdate.setCoachStaffId(staffEntity.getStaffId());
        logger.info("  changeCoach  memberUpdate = {}",memberUpdate);
        int n = memberDao.update(memberUpdate);
        if(n==1){
            SysLogEntity sysLogEntity = new SysLogEntity();
            sysLogEntity.setLogId(IDUtils.getId());
            sysLogEntity.setType(SysLogEnum.CC.getKey());
            sysLogEntity.setLevel(2);
            sysLogEntity.setId1(member.getMemberId());
            sysLogEntity.setId2(memberEntity.getCoachStaffId());
            sysLogEntity.setLogText(memberEntity.getCoachStaffId()+","+staffEntity.getStaffId());
            sysLogEntity.setContent("");
            sysLogEntity.setCreated(new Date());
            sysLogService.add(sysLogEntity);

            if(StringUtils.isEmpty(memberEntity.getCoachStaffId()) || (StringUtils.isNotEmpty(memberEntity.getStoreId())&&!memberEntity.getStoreId().equals(staffEntity.getStoreId())) ){
                try {
                    smsUtil.sendAddMemberNotice(staffEntity.getPhone(), memberEntity.getName(),memberEntity.getPhone());
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                List<StaffEntity> managers = staffDao.getManagerByStoreId(staffEntity.getStoreId());
                for (StaffEntity manager : managers){
                    if(StringUtils.isNotEmpty(manager.getPhone()) && !manager.getPhone().equals(staffEntity.getPhone())){
                        try {
                            smsUtil.sendAddMemberNotice(manager.getPhone(), memberEntity.getName(),memberEntity.getPhone());
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

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
        for (MemberEntity memberEntity : memberList){
            if(memberEntity.getStatus()==-1){
                continue;
            }
            Member member = transferMember(memberEntity);
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
        if(StringUtils.isEmpty(lesson.getMemberId())){
            logger.info("  getCanUseCardList  getMemberId = {}", lesson.getMemberId());
            return ResponseUtil.success(cards);
        }
        MemberCardQuery query = new MemberCardQuery();
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

            if(lesson.getType().startsWith(LessonTypeEnum.S.getKey())){
                if(memberCardEntity.getType().equals(lesson.getType())){
                    if(memberCardEntity.getCount()>0){
                        validCardList.add(memberCardEntity);
                    }
                }
            }

        }

        logger.info("  getCanUseCardList   validCardList.size() = {}", validCardList.size());

        for (MemberCardEntity memberCardEntity : validCardList){
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
        MemberEntity memberEntity = memberDao.getById(member.getMemberId());
        if(memberEntity==null){
            return ResponseUtil.exception("停课学员未知异常");
        }
        if(memberEntity.getStatus()==9){
            return ResponseUtil.exception("该学员已处于停课状态！");
        }

        TrainingQuery query = new TrainingQuery();
        query.setMemberId(memberEntity.getMemberId());
        query.setStartDate(ut.currentDate());
        query.setStatus(0);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100);
        List<TrainingEntity> trainingList =  trainingDao.find(query,pageRequest);
        logger.info(" =================    list  trainingList = {}",trainingList.size());
        for (TrainingEntity trainingEntity:trainingList){
            if(trainingEntity.getLessonDate().equals(ut.currentDate())){
                int time = ut.currentHour();
                if(time<trainingEntity.getStartHour()){
                    return ResponseUtil.exception("该学员已经预约今日课程，不能停课！");
                }
            }else{
                return ResponseUtil.exception("该学员已经预约今日之后的课程，不能停课！");
            }
        }

        memberEntity.setMemberId(member.getMemberId());
        memberEntity.setStatus(9);   //  暂停

        MemberPauseEntity memberPauseEntity = new MemberPauseEntity();
        memberPauseEntity.setMemberId(member.getMemberId());
        memberPauseEntity.setPauseDate(ut.currentTime());
        memberPauseEntity.setPauseStaffId(staffRequest.getStaffId());
        int n = memberPauseDao.add(memberPauseEntity);
        if(n==1){
            n = memberDao.update(memberEntity);
            try {
                memberEntity = memberDao.getById(member.getMemberId());
                StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                smsUtil.sendPauseMemberNoticeToMember(memberEntity.getPhone());
                smsUtil.sendPauseMemberNoticeToCoach(staffEntity.getPhone(),memberEntity.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseUtil.success("停课成功");
        }
        return ResponseUtil.exception("停课失败");
    }

    @Transactional
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
        MemberPauseQuery query = new MemberPauseQuery();
        query.setMemberId(member.getMemberId());
        query.setStatus(1);
        PageRequest page = new PageRequest();
        List<MemberPauseEntity> memberPauseEntities = memberPauseDao.find(query,page);
        int days = 0;
        if(memberPauseEntities.size()>0){
            MemberPauseEntity memberPauseEntity = memberPauseEntities.get(0);
            days = ut.passDayByDate(memberPauseEntity.getPauseDate(),ut.currentDate());
            memberPauseEntity.setRestoreDate(ut.currentDate());
            memberPauseEntity.setRestoreStaffId(staffRequest.getStaffId());
            memberPauseEntity.setStatus(0);
            int n = memberPauseDao.update(memberPauseEntity);
            if(n<1){
                return ResponseUtil.exception("复课异常，更新停课日志失败");
            }
            logger.info("  restoreMember  days = {}", days);
            if(days>0){
                MemberCardQuery memberCardQuery = new MemberCardQuery();
                memberCardQuery.setMemberId(member.getMemberId());
                PageRequest pageRequest = new PageRequest();
                pageRequest.setPageSize(9999);
                List<MemberCardEntity> cards = memberCardDao.find(memberCardQuery,pageRequest);
                for (MemberCardEntity memberCardEntity :cards) {
                    if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())
                            ||memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())
                            ||memberCardEntity.getType().equals(CardTypeEnum.TY.getKey())){
                        if(memberCardEntity.getCount()==0){
                            continue;
                        }
                    }
                    if(ut.passDayByDate(memberCardEntity.getEndDate(),memberPauseEntity.getPauseDate())<=0){
                        String newEndDate = ut.currentDate(memberCardEntity.getEndDate(),days);
                        logger.info("  restoreMember  newEndDate = {} , memberCardEntity = {} ", newEndDate,memberCardEntity);
                        MemberCardEntity memberCardEntityUpdate = new MemberCardEntity();
                        memberCardEntityUpdate.setEndDate(newEndDate);
                        memberCardEntityUpdate.setCardNo(memberCardEntity.getCardNo());
                        memberCardDao.update(memberCardEntityUpdate);
                    }
                }
            }

        }else{
            return ResponseUtil.exception("复课异常，无停课日志");
        }
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(member.getMemberId());
        memberEntity.setStatus(1);
        int n = memberDao.update(memberEntity);
        if(n==1){
            try {
                memberEntity = memberDao.getById(member.getMemberId());
                StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                smsUtil.sendRestoreMemberNoticeToMember(memberEntity.getPhone());
                smsUtil.sendRestoreMemberNoticeToCoach(staffEntity.getPhone(),memberEntity.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseUtil.success("复课成功");
        }
        return ResponseUtil.exception("复课失败");
    }

    @Transactional
    public ResponseEntity<String> pauseMemberBySelf(String memberId) {
        logger.info("  pauseMemberBySelf  member = {}", memberId);
        if(StringUtils.isEmpty(memberId)){
            return ResponseUtil.exception("停课参数异常");
        }
        MemberEntity memberEntity = memberDao.getById(memberId);
        if(memberEntity==null){
            return ResponseUtil.exception("停课会员未知异常");
        }
        if(memberEntity.getStatus()==9){
            return ResponseUtil.exception("您已已处于停课状态！");
        }

        MemberCardQuery query = new MemberCardQuery();
        query.setMemberId(memberId);
        query.setStartDate(ut.currentDate());
        query.setEndDate(ut.currentDate());
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<MemberCardEntity> cardList = memberCardDao.find(query,page);
        logger.info("  canPauseBySelf   cardList.size() = {}", cardList.size());
        boolean canPause = false;
        String cardNo = "";
        for (MemberCardEntity memberCardEntity : cardList){
            if(!memberCardEntity.getType().equals(CardTypeEnum.PM.getKey())){
                continue;
            }
            int count = 0;
            if(memberCardEntity.getDays()<90){
                continue;
            }else if(memberCardEntity.getDays()>=90&&memberCardEntity.getDays()<180){
                count = 1;
            }else if(memberCardEntity.getDays()>=180&&memberCardEntity.getDays()<360){
                count = 2;
            }else if(memberCardEntity.getDays()>=360){
                count = 3;
            }
            List times = jdbcTemplate.queryForList("select * from member_pause where card_no = ?  ",new Object[]{memberCardEntity.getCardNo()});
            if(count>times.size()){
                canPause = true;
                cardNo = memberCardEntity.getCardNo();
                break;
            }
        }
        if(!canPause){
            return ResponseUtil.success("无权限自助停课");
        }

        TrainingQuery trainingQuery = new TrainingQuery();
        trainingQuery.setMemberId(memberEntity.getMemberId());
        trainingQuery.setStartDate(ut.currentDate());
        trainingQuery.setStatus(0);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100);
        List<TrainingEntity> trainingList =  trainingDao.find(trainingQuery,pageRequest);
        logger.info(" =================    list  trainingList = {}",trainingList.size());
        for (TrainingEntity trainingEntity:trainingList){
            if(trainingEntity.getLessonDate().equals(ut.currentDate())){
                int time = ut.currentHour();
                if(time<trainingEntity.getStartHour()){
                    return ResponseUtil.exception("您已经预约今日课程，不能停课！");
                }
            }else{
                return ResponseUtil.exception("您已经预约今日之后的课程，不能停课！请先取消约课!");
            }
        }


        MemberEntity memberUpdate = new MemberEntity();
        memberUpdate.setMemberId(memberId);
        memberUpdate.setStatus(9);   //  暂停

        MemberPauseEntity memberPauseEntity = new MemberPauseEntity();
        memberPauseEntity.setMemberId(memberId);
        memberPauseEntity.setCardNo(cardNo);
        memberPauseEntity.setPauseDate(ut.currentTime());
        memberPauseEntity.setPauseStaffId("");
        memberPauseEntity.setStatus(1);
        int n = memberPauseDao.add(memberPauseEntity);
        if(n==1){
            n = memberDao.update(memberUpdate);
            jdbcTemplate.update(" update member_card set status = 9 where member_id = ? and status in ( 0 , 1 ) ",new Object[]{memberId});
            try {
                StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                smsUtil.sendPauseMemberNoticeToMember(memberEntity.getPhone());
                smsUtil.sendPauseMemberNoticeToCoach(staffEntity.getPhone(),memberEntity.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseUtil.success("停课成功");
        }
        return ResponseUtil.exception("停课失败");
    }

    @Transactional
    public ResponseEntity<String> restoreMemberBySelf(String memberId) {
        MemberEntity memberEntity = memberDao.getById(memberId);
        if(memberEntity==null){
            return ResponseUtil.exception("用户无效");
        }
        MemberPauseQuery query = new MemberPauseQuery();
        query.setMemberId(memberId);
        query.setStatus(1);
        PageRequest page = new PageRequest();
        List<MemberPauseEntity> memberPauseEntities = memberPauseDao.find(query,page);
        boolean canRestore = false;
        for (MemberPauseEntity memberPauseEntity : memberPauseEntities){
            if(StringUtils.isEmpty(memberPauseEntity.getCardNo())){
                continue;
            }
            MemberCardEntity card = memberCardDao.getById(memberPauseEntity.getCardNo());
            if(card==null){
                continue;
            }
            if(!card.getType().equals(CardTypeEnum.PM.getKey())){
                continue;
            }
            canRestore = true;
            memberPauseEntity.setRestoreDate(ut.currentDate());
            memberPauseEntity.setRestoreStaffId("");
            memberPauseEntity.setStatus(0);
            int n = memberPauseDao.update(memberPauseEntity);
            int days = ut.passDayByDate(memberPauseEntity.getPauseDate(),ut.currentDate());
            if(n>0){
                canRestore = true;
            }
            if(n>0 && days>0){
                MemberCardQuery memberCardQuery = new MemberCardQuery();
                memberCardQuery.setMemberId(memberId);
                PageRequest pageRequest = new PageRequest();
                pageRequest.setPageSize(9999);
                List<MemberCardEntity> cards = memberCardDao.find(memberCardQuery,pageRequest);
                for (MemberCardEntity memberCardEntity :cards) {
                    if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())
                            ||memberCardEntity.getType().equals(CardTypeEnum.TT.getKey())
                            ||memberCardEntity.getType().equals(CardTypeEnum.TY.getKey())){
                        if(memberCardEntity.getCount()==0){
                            continue;
                        }
                    }
                    if(ut.passDayByDate(memberCardEntity.getEndDate(),memberPauseEntity.getPauseDate())<=0){
                        String newEndDate = ut.currentDate(memberCardEntity.getEndDate(),days);
                        logger.info("  restoreMember  newEndDate = {} , memberCardEntity = {} ", newEndDate,memberCardEntity);
                        MemberCardEntity memberCardEntityUpdate = new MemberCardEntity();
                        memberCardEntityUpdate.setEndDate(newEndDate);
                        memberCardEntityUpdate.setCardNo(memberCardEntity.getCardNo());
                        memberCardDao.update(memberCardEntityUpdate);
                    }
                }
            }
        }
        if(canRestore){
            MemberEntity memberUpdate = new MemberEntity();
            memberUpdate.setMemberId(memberId);
            memberUpdate.setStatus(1);
            int n = memberDao.update(memberUpdate);
            jdbcTemplate.update(" update member_card set status = 1 where member_id = ? and status = 9 ",new Object[]{memberId});
            if(n==1){
                try {
                    StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                    smsUtil.sendRestoreMemberNoticeToMember(memberEntity.getPhone());
                    smsUtil.sendRestoreMemberNoticeToCoach(staffEntity.getPhone(),memberEntity.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ResponseUtil.success("复课成功");
            }
        }
        return ResponseUtil.exception("复课失败");
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


    public ResponseEntity<String> logoff() {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info("  logoff  memberRequest = {}",memberRequest);

        MemberEntity memberEntity = memberDao.getById(memberRequest.getMemberId());
        if(memberEntity==null){
            return ResponseUtil.exception("注销失败，用户非法");
        }

        if(memberEntity.getType().equals("C")){
            return ResponseUtil.exception("教练不能在此注销，请联系管理员");
        }

        memberDao.logoff(memberEntity.getMemberId());
        return ResponseUtil.success("微信注销成功");
    }

    public ResponseEntity<String> logoffByStaff() {
        Member memberRequest = RequestContextHelper.getMember();
        logger.info("  logoffByStaff  memberRequest = {}",memberRequest);
        MemberEntity memberEntity = memberDao.getById(memberRequest.getMemberId());
        if(memberEntity==null){
            return ResponseUtil.exception("注销失败，用户非法");
        }

        if(memberEntity.getType().equals("M")){
            return ResponseUtil.exception("会员不能在此注销，请联系管理员");
        }

        memberDao.logoffByStaff(memberEntity.getMemberId());
        staffDao.logoffByStaff(memberEntity.getOpenId());
        return ResponseUtil.success("教练微信注销成功");
    }

    public ResponseEntity<String> canPauseBySelf(String memberId) {
        MemberEntity memberEntity = memberDao.getById(memberId);
        if(memberEntity==null){
            return ResponseUtil.exception("用户无效");
        }
        if(memberEntity.getStatus().equals(MemberStatusEnum.PAUSE.getKey())){
            return ResponseUtil.success("0");
        }
        MemberCardQuery query = new MemberCardQuery();
        query.setMemberId(memberId);
        query.setStartDate(ut.currentDate());
        query.setEndDate(ut.currentDate());
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<MemberCardEntity> cardList = memberCardDao.find(query,page);
        logger.info("  canPauseBySelf   cardList.size() = {}", cardList.size());
        boolean canPause = false;
        for (MemberCardEntity memberCardEntity : cardList){
            if(!memberCardEntity.getType().equals(CardTypeEnum.PM.getKey())){
                continue;
            }
            int count = 0;
            if(memberCardEntity.getDays()<90){
                continue;
            }else if(memberCardEntity.getDays()>=90&&memberCardEntity.getDays()<180){
                count = 1;
            }else if(memberCardEntity.getDays()>=180&&memberCardEntity.getDays()<360){
                count = 2;
            }else if(memberCardEntity.getDays()>=360){
                count = 3;
            }
            List times = jdbcTemplate.queryForList("select * from member_pause where card_no = ?  ",new Object[]{memberCardEntity.getCardNo()});
            if(count>times.size()){
                canPause = true;
                break;
            }
        }
        if(canPause){
            return ResponseUtil.success("1");
        }
        return ResponseUtil.success("0");

    }

    public ResponseEntity<String> canRestoreBySelf(String memberId) {
        MemberEntity memberEntity = memberDao.getById(memberId);
        if(memberEntity==null){
            return ResponseUtil.exception("用户无效");
        }
        if(!memberEntity.getStatus().equals(MemberStatusEnum.PAUSE.getKey())){
            return ResponseUtil.success("0");
        }
        MemberPauseQuery query = new MemberPauseQuery();
        query.setMemberId(memberId);
        query.setStatus(1);
        PageRequest page = new PageRequest();
        List<MemberPauseEntity> memberPauseEntities = memberPauseDao.find(query,page);
        boolean canRestore = false;
        for (MemberPauseEntity memberPauseEntity : memberPauseEntities){
            if(StringUtils.isNotEmpty(memberPauseEntity.getCardNo())){
                MemberCardEntity memberCardEntity = memberCardDao.getById(memberPauseEntity.getCardNo());
                if(memberCardEntity!=null && memberCardEntity.getType().equals(CardTypeEnum.PM.getKey())){
                    canRestore = true;
                    break;
                }
            }
        }
        if(canRestore){
            return ResponseUtil.success("1");
        }
        return ResponseUtil.success("0");
    }


}

