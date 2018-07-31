package com.training.admin.service;

import com.training.common.*;
import com.training.dao.*;
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
            String sql = " select training_id from training where member_id = ? and `status` = 0 and lesson_date <=  ? ";
            List trainingList =  jdbcTemplate.queryForList(sql,new Object[]{memberId,ut.currentDate(-1)});
            if(CollectionUtils.isNotEmpty(trainingList)){
                System.out.println(" name = "+member.get("name")+"   hours = "+trainingList.size());
                jdbcTemplate.update(" update member set training_hours = ? where member_id = ? ",new Object[]{trainingList.size(),memberId});
                total++;
            }
        }
        logger.info("total = "+total);
        return "updateTrainingHour执行成功";
    }

    public String updateMemberMedal() {
        logger.info(" MemberTrainingTaskService updateMemberMedal     ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT member_id,name,training_hours from member where training_hours >= 8   ");
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
                query.setStartDate(ut.currentDate(-90));
                query.setEndDate(ut.currentDate());
                query.setStatus(0);
                PageRequest pageRequest = new PageRequest();
                pageRequest.setPageSize(1000);
                List<TrainingEntity> trainingList =  trainingDao.find(query,pageRequest);
                int cq1 = 0;
                int cq2 = 0;
                int cq3 = 0;
                for (TrainingEntity trainingEntity :trainingList){
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

                if(!medals.contains(MemberMedalEnum.CQ1.getKey()) && cq1>=MemberMedalEnum.CQ1.getCount()){
                    attendance1(memberId);
                }

                if(!medals.contains(MemberMedalEnum.CQ2.getKey()) && cq2>=MemberMedalEnum.CQ2.getCount()){
                    attendance2(memberId);
                }

                if(!medals.contains(MemberMedalEnum.CQ3.getKey()) && cq3>=MemberMedalEnum.CQ3.getCount()){
                    attendance3(memberId);
                }

                if(!medals.contains(MemberMedalEnum.SJ1.getKey()) && trainingHours>=MemberMedalEnum.SJ1.getCount()){
                    driverLevel1(memberId);
                }
                if(!medals.contains(MemberMedalEnum.SJ2.getKey()) && trainingHours>=MemberMedalEnum.SJ2.getCount()){
                    driverLevel2(memberId);
                }
                if(!medals.contains(MemberMedalEnum.SJ3.getKey()) && trainingHours>=MemberMedalEnum.SJ3.getCount()){
                    driverLevel3(memberId);
                }
                total++;
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        }
        logger.info("total = "+total);
        return "updateMemberMedal执行成功 , total = "+total;
    }

    private void attendance1(String memberId) {
        int n = addMemberMedal(memberId,MemberMedalEnum.CQ1.getKey());
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
        int n = addMemberMedal(memberId,MemberMedalEnum.CQ2.getKey());
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
        int n = addMemberMedal(memberId,MemberMedalEnum.CQ3.getKey());
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
        int n = addMemberMedal(memberId,MemberMedalEnum.SJ1.getKey());
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
        int n = addMemberMedal(memberId,MemberMedalEnum.SJ2.getKey());
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
        int n = addMemberMedal(memberId,MemberMedalEnum.SJ3.getKey());
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


}
