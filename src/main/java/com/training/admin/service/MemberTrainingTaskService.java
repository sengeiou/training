package com.training.admin.service;

import com.training.common.*;
import com.training.dao.*;
import com.training.domain.MemberCoupon;
import com.training.entity.*;
import com.training.service.MemberCardService;
import com.training.service.MemberCouponService;
import com.training.util.IDUtils;
import com.training.util.ut;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MemberTrainingTaskService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberCouponDao memberCouponDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MedalDao medalDao;

    @Autowired
    private MemberMedalDao memberMedalDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String updateTrainingHour() {
        logger.info(" MemberTrainingTaskService updateTrainingHour     ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT *  from member  ");
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            Map member = data.get(i);
            String memberId = member.get("member_id").toString();
            String sql = " select training_id from training where member_id = ? and `status` = 0 and card_type = 'PT' and lesson_date <=  ? and show_tag = 1 ";
            List trainingList =  jdbcTemplate.queryForList(sql,new Object[]{memberId,ut.currentDate(-1)});
//            if(CollectionUtils.isNotEmpty(trainingList)){
                System.out.println(" name = "+member.get("name")+"   hours = "+trainingList.size());
                jdbcTemplate.update(" update member set training_hours = ? where member_id = ? ",new Object[]{trainingList.size(),memberId});
                total++;
//            }
        }
        logger.info("total = "+total);
        return "updateTrainingHour执行成功";
    }

    public String updateMemberInfo() {
//        logger.info(" updateMemberInfo ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT * from member where coach_staff_id <> ''  ");
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
//            logger.info(" updateMemberInfo  i = {} ",i);
            Map member = data.get(i);
            String memberId = member.get("member_id").toString();
            String coach_staff_id = member.get("coach_staff_id").toString();
            StaffEntity staffEntity = staffDao.getById(coach_staff_id);
            if(staffEntity!=null){
                jdbcTemplate.update(" update member set store_id = ? where member_id = ? ",new Object[]{staffEntity.getStoreId(),memberId});
                total++;
            }
        }
//        logger.info("total = "+total);
        return "updateMemberInfo执行成功";
    }

    public String updateTrainingInfo() {
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT * from training where staff_id = '' or store_id = '' ");
        logger.info(" MemberTrainingTaskService updateTrainingInfo data.size =  "+data.size());
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            Map training = data.get(i);
            String trainingId = training.get("training_id").toString();
            String coach_id = training.get("coach_id").toString();
            MemberEntity coach = memberDao.getById(coach_id);
            StaffEntity staffEntity = staffDao.getByOpenId(coach.getOpenId());
            if(staffEntity!=null){
                jdbcTemplate.update(" update training set store_id = ? , staff_id = ?  where training_id = ? ",new Object[]{staffEntity.getStoreId(),staffEntity.getStaffId(),trainingId});
                total++;
            }
        }
        logger.info("total = "+total);
        return "updateTrainingInfo执行成功";
    }

    public String updateMemberMedal() {
        logger.info(" MemberTrainingTaskService updateMemberMedal     ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT member_id,name,training_hours from member   ");
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            try {
                Map member = data.get(i);
                logger.info(" member = {} ",member);
                String memberId = member.get("member_id").toString();
                int trainingHours = Integer.parseInt(member.get("training_hours").toString());
                List<MemberMedalEntity> memberMedalList = memberMedalDao.getByMemberId(memberId);
                Set<String> medals = new HashSet();
                for (MemberMedalEntity memberMedalEntity :memberMedalList){
                    medals.add(memberMedalEntity.getMedalId());
                }
                TrainingQuery query = new TrainingQuery();
                query.setMemberId(memberId);
                query.setCardType(CardTypeEnum.PT.getKey());
                query.setStartDate(ut.currentDate(-90));
                query.setEndDate(ut.currentDate());
                query.setStatus(0);
                query.setShowTag(""+TrainingShowTagEnum.finish.getKey());
                PageRequest pageRequest = new PageRequest();
                pageRequest.setPageSize(1000);
                List<TrainingEntity> trainingList =  trainingDao.find(query,pageRequest);

                Set<String> couponOrigins = new HashSet();
                List<MemberCouponEntity> memberCouponEntityList = memberCouponDao.getByMemberId(memberId);
                for (MemberCouponEntity memberCouponEntity :memberCouponEntityList){
                    couponOrigins.add(memberCouponEntity.getOrigin());
                }

                int cq1 = 0;
                int cq2 = 0;
                int cq3 = 0;
                for (TrainingEntity trainingEntity :trainingList){
                    if(StringUtils.isEmpty(trainingEntity.getSignTime())){
                        continue;
                    }
                    if(trainingEntity.getType().equals(LessonTypeEnum.P.getKey())||trainingEntity.getType().startsWith(LessonTypeEnum.S.getKey())){
                        if(ut.passDayByDate(ut.currentDate(-30),trainingEntity.getLessonDate())>=0){
                            cq1++;
                        }
                        if(ut.passDayByDate(ut.currentDate(-60),trainingEntity.getLessonDate())>=0){
                            cq2++;
                        }
                        if(ut.passDayByDate(ut.currentDate(-90),trainingEntity.getLessonDate())>=0){
                            cq3++;
                        }
                    }
                }

                if(medals.contains(MemberMedalEnum.CQ1.getKey())){
                    if(!couponOrigins.contains(MemberMedalEnum.CQ1.getKey())){
                        attendance1(memberId);
                    }
                }
                if(!medals.contains(MemberMedalEnum.CQ1.getKey()) && cq1>=MemberMedalEnum.CQ1.getCount()){
                    int n = addMemberMedal(memberId,MemberMedalEnum.CQ1.getKey());
                    attendance1(memberId);
                }

                if(medals.contains(MemberMedalEnum.CQ2.getKey())){
                    if(!couponOrigins.contains(MemberMedalEnum.CQ2.getKey())){
                        attendance2(memberId);
                    }
                }
                if(!medals.contains(MemberMedalEnum.CQ2.getKey()) && cq2>=MemberMedalEnum.CQ2.getCount()){
                    int n = addMemberMedal(memberId,MemberMedalEnum.CQ2.getKey());
                    attendance2(memberId);
                }

                if(medals.contains(MemberMedalEnum.CQ3.getKey())){
                    if(!couponOrigins.contains(MemberMedalEnum.CQ3.getKey())){
                        attendance3(memberId);
                    }
                }
                if(!medals.contains(MemberMedalEnum.CQ3.getKey()) && cq3>=MemberMedalEnum.CQ3.getCount()){
                    int n = addMemberMedal(memberId,MemberMedalEnum.CQ3.getKey());
                    attendance3(memberId);
                }

                if(medals.contains(MemberMedalEnum.SJ1.getKey())){
                    if(!couponOrigins.contains(MemberMedalEnum.SJ1.getKey())){
                        driverLevel1(memberId);
                    }
                }
                if(!medals.contains(MemberMedalEnum.SJ1.getKey()) && trainingHours>=MemberMedalEnum.SJ1.getCount()){
                    int n = addMemberMedal(memberId,MemberMedalEnum.SJ1.getKey());
                    driverLevel1(memberId);
                }

                if(medals.contains(MemberMedalEnum.SJ2.getKey())){
                    if(!couponOrigins.contains(MemberMedalEnum.SJ2.getKey())){
                        driverLevel2(memberId);
                    }
                }
                if(!medals.contains(MemberMedalEnum.SJ2.getKey()) && trainingHours>=MemberMedalEnum.SJ2.getCount()){
                    int n = addMemberMedal(memberId,MemberMedalEnum.SJ2.getKey());
                    driverLevel2(memberId);
                }

                if(medals.contains(MemberMedalEnum.SJ3.getKey())){
                    if(!couponOrigins.contains(MemberMedalEnum.SJ3.getKey())){
                        driverLevel3(memberId);
                    }
                }
                if(!medals.contains(MemberMedalEnum.SJ3.getKey()) && trainingHours>=MemberMedalEnum.SJ3.getCount()){
                    int n = addMemberMedal(memberId,MemberMedalEnum.SJ3.getKey());
                    driverLevel3(memberId);
                }

                if(medals.contains(MemberMedalEnum.OX.getKey())){
                    if(!couponOrigins.contains(MemberMedalEnum.OX.getKey())){
                        heyHeros(memberId);
                    }
                }

                total++;
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        }
        logger.info("total = "+total);
        return "updateMemberMedal执行成功 , total = "+total;
    }

    private void heyHeros(String memberId) {
        MemberCouponEntity memberCoupon = new MemberCouponEntity();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setType(CouponTypeEnum.DZ.getKey());
        memberCoupon.setTitle("6.8折折扣券");
        memberCoupon.setDiscount(68);
        memberCoupon.setStartDate(ut.currentDate());
        memberCoupon.setEndDate(ut.currentDate(1000));
        memberCoupon.setOrigin(MemberMedalEnum.OX.getKey());
        memberCoupon.setContent(MemberMedalEnum.OX.getDesc());
        memberCouponService.addOne(memberCoupon);
    }

    private void attendance1(String memberId) {
        MemberCouponEntity memberCoupon = new MemberCouponEntity();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setType(CouponTypeEnum.MZ.getKey());
        memberCoupon.setTitle("300元代金券");
        memberCoupon.setTotal(300);
        memberCoupon.setReduction(300);
        memberCoupon.setStartDate(ut.currentDate());
        memberCoupon.setEndDate(ut.currentDate(1000));
        memberCoupon.setOrigin(MemberMedalEnum.CQ1.getKey());
        memberCoupon.setContent(MemberMedalEnum.CQ1.getDesc());
        memberCouponService.addOne(memberCoupon);
    }

    private void attendance2(String memberId) {
        MemberCouponEntity memberCoupon = new MemberCouponEntity();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setType(CouponTypeEnum.MZ.getKey());
        memberCoupon.setTitle("300元代金券");
        memberCoupon.setTotal(300);
        memberCoupon.setReduction(300);
        memberCoupon.setStartDate(ut.currentDate());
        memberCoupon.setEndDate(ut.currentDate(1000));
        memberCoupon.setOrigin(MemberMedalEnum.CQ2.getKey());
        memberCoupon.setContent(MemberMedalEnum.CQ2.getDesc());
        memberCouponService.addOne(memberCoupon);
    }

    private void attendance3(String memberId) {
        MemberCouponEntity memberCoupon = new MemberCouponEntity();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setType(CouponTypeEnum.MZ.getKey());
        memberCoupon.setTitle("300元代金券");
        memberCoupon.setTotal(300);
        memberCoupon.setReduction(300);
        memberCoupon.setStartDate(ut.currentDate());
        memberCoupon.setEndDate(ut.currentDate(1000));
        memberCoupon.setOrigin(MemberMedalEnum.CQ3.getKey());
        memberCoupon.setContent(MemberMedalEnum.CQ3.getDesc());
        memberCouponService.addOne(memberCoupon);
    }

    private void driverLevel1(String memberId) {
        MemberCouponEntity memberCoupon = new MemberCouponEntity();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setType(CouponTypeEnum.DZ.getKey());
        memberCoupon.setTitle("9.5折折扣券");
        memberCoupon.setDiscount(95);
        memberCoupon.setStartDate(ut.currentDate());
        memberCoupon.setEndDate(ut.currentDate(1000));
        memberCoupon.setOrigin(MemberMedalEnum.SJ1.getKey());
        memberCoupon.setContent(MemberMedalEnum.SJ1.getDesc());
        memberCouponService.addOne(memberCoupon);
    }

    private void driverLevel2(String memberId) {
        MemberCouponEntity memberCoupon = new MemberCouponEntity();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setType(CouponTypeEnum.DZ.getKey());
        memberCoupon.setTitle("9.0折折扣券");
        memberCoupon.setDiscount(90);
        memberCoupon.setStartDate(ut.currentDate());
        memberCoupon.setEndDate(ut.currentDate(1000));
        memberCoupon.setOrigin(MemberMedalEnum.SJ2.getKey());
        memberCoupon.setContent(MemberMedalEnum.SJ2.getDesc());
        memberCouponService.addOne(memberCoupon);
    }

    private void driverLevel3(String memberId) {
        MemberCouponEntity memberCoupon = new MemberCouponEntity();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setType(CouponTypeEnum.DZ.getKey());
        memberCoupon.setTitle("8.5折折扣券");
        memberCoupon.setDiscount(85);
        memberCoupon.setStartDate(ut.currentDate());
        memberCoupon.setEndDate(ut.currentDate(1000));
        memberCoupon.setOrigin(MemberMedalEnum.SJ3.getKey());
        memberCoupon.setContent(MemberMedalEnum.SJ3.getDesc());
        memberCouponService.addOne(memberCoupon);
    }


    private int addMemberMedal(String memberId,String medalId) {
        MemberMedalEntity memberMedal = new MemberMedalEntity();
        memberMedal.setMemberId(memberId);
        memberMedal.setMedalId(medalId);
        MedalEntity medalEntity = medalDao.getById(memberMedal.getMedalId());
        memberMedal.setAwardDate(ut.currentTime());
        memberMedal.setContent(medalEntity.getContent());
        int n = memberMedalDao.add(memberMedal);
        return n;
    }

    public String updateMemberStatus() {
        logger.info(" MemberTrainingTaskService updateMemberStatus     ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT * from member where status not in (9)  ");
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            Map member = data.get(i);
            logger.info(" updateMemberStatus   index = {} , name = {} , phone = {}   ",i,member.get("name"),member.get("phone"));
            int status = Integer.parseInt(member.get("status").toString());
            String memberId = member.get("member_id").toString();
            List<Map<String,Object>> cards = jdbcTemplate.queryForList(" SELECT * from member_card where member_id = ? and type not in ('TY')  ",new Object[]{memberId});
            if(cards.size()==0){
                if(status!=0){
                    status = 0;
                    jdbcTemplate.update(" update member set status = ? where  member_id = ?  ",new Object[]{status,memberId});
                }
            }else{
                boolean isValid = false;
                for (int j = 0; j < cards.size(); j++) {
                    Map card = cards.get(j);
                    int count = Integer.parseInt(card.get("count").toString());
                    String cardNo = card.get("card_no").toString();
                    String type = card.get("type").toString();
                    String startDate = card.get("start_date").toString();
                    String endDate = card.get("end_date").toString();

                    if(ut.passDayByDate(startDate,ut.currentDate())<0){
                        continue;
                    }

                    if(type.equals("TM")||type.equals("PM")){
                        if(ut.passDayByDate(ut.currentDate(),endDate)>=0 ){
                            isValid = true;
                            break;
                        }

                        String sql = "select 1 from training where card_no = ? and lesson_date > ? and status >= 0 ";
                        List lessons = jdbcTemplate.queryForList(sql,new Object[]{cardNo,ut.currentDate()});
                        if(lessons.size()>0){
                            isValid = true;
                            break;
                        }

                    }else{
                        if(ut.passDayByDate(ut.currentDate(),endDate)>=0 && count >0){
                            isValid = true;
                            break;
                        }

                        String sql = "select 1 from training where card_no = ? and lesson_date > ? and status >= 0 ";
                        List lessons = jdbcTemplate.queryForList(sql,new Object[]{cardNo,ut.currentDate()});
                        if(lessons.size()>0){
                            isValid = true;
                            break;
                        }

                    }
                }
                if(isValid){
                    status = 1;
                }else {
                    status = 2;
                }
                jdbcTemplate.update(" update member set status = ? where  member_id = ?  ",new Object[]{status,memberId});
                total++;
            }
        }
        logger.info(" updateMemberStatus执行成功    total = {}  ",total);
        return "updateMemberStatus执行成功";


    }

    public String updateMemberCoupon() {
        logger.info(" MemberTrainingTaskService updateMemberCoupon     ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT * from member_coupon where status = 0  ");
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            Map memberCoupon = data.get(i);
            try{
                String endDate = memberCoupon.get("end_date").toString();
                logger.info(" updateMemberCoupon   index = {} , coupon_id = {} , end_date = {}   ",i,memberCoupon.get("coupon_id"),endDate);
                if(ut.passDayByDate(endDate,ut.currentDate())>0){
                    jdbcTemplate.update(" update member_coupon set status = -1 where  coupon_id = ?  ",new Object[]{memberCoupon.get("coupon_id")});
                    total++;
                }
            }catch (Exception e){
                logger.error(" updateMemberCoupon  ERROR  index = {} , memberCoupon = {}  ",i,memberCoupon,e);
            }
        }
        logger.info(" updateMemberCoupon执行成功    total = {}  ",total);
        return "updateMemberCoupon执行成功";
    }

    public Object updateMemberCardStatus() {
        logger.info(" updateMemberCardStatus  执行开始  ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT * from member_card where status in (0,1,-1) ");
        List<Map<String,Object>> data2 =  jdbcTemplate.queryForList(" SELECT * from member_card where status in (2) and count > 0 ",new Object[]{});
        data.addAll(data2);
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            Map card = data.get(i);
            try{
                String cardNo = card.get("card_no").toString();
                Integer status = Integer.parseInt(card.get("status").toString());
                String startDate = card.get("start_date").toString();
                String endDate = card.get("end_date").toString();
                int count = Integer.parseInt(card.get("count").toString());
//                logger.info(" updateMemberCardStatus   index = {} , card_no = {} , end_date = {}   ",i,card.get("card_no"),endDate);
                if(ut.passDayByDate(ut.currentDate(),startDate)>0){
                    if(status!=0){
                        jdbcTemplate.update(" update member_card set status = 0 where  card_no = ?  ",new Object[]{cardNo});
                    }
                }else if(ut.passDayByDate(endDate,ut.currentDate())>0){
                    if(status!=2){
                        jdbcTemplate.update(" update member_card set status = 2 where  card_no = ?  ",new Object[]{cardNo});
                    }
                }else if(count==0){
                    if(status!=2){
                        jdbcTemplate.update(" update member_card set status = 2 where  card_no = ?  ",new Object[]{cardNo});
                    }
                }else {
                    if(status!=1){
                        jdbcTemplate.update(" update member_card set status = 1 where  card_no = ?  ",new Object[]{cardNo});
                    }
                }
                total++;
            }catch (Exception e){
                logger.error(" updateMemberCoupon  ERROR  index = {} , card = {}  ",i,card,e);
            }
        }
        logger.info(" updateMemberCardStatus执行成功    data.size() = {}  ",data.size());
        logger.info(" updateMemberCardStatus执行成功    data2.size() = {}  ",data2.size());
        logger.info(" updateMemberCardStatus执行成功    total = {}  ",total);
        return "updateMemberCardStatus执行成功";
    }
}
