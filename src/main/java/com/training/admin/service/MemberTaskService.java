package com.training.admin.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.training.common.*;
import com.training.dao.*;
import com.training.entity.*;
import com.training.service.MemberCardService;
import com.training.service.MemberCouponService;
import com.training.service.SmsLogService;
import com.training.util.IDUtils;
import com.training.util.SmsUtil;
import com.training.util.ut;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MemberTaskService {

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
    private StoreDao storeDao;

    @Autowired
    private MemberMedalDao memberMedalDao;

    @Autowired
    SmsLogService smsLogService;

    @Autowired
    SmsLogDao smsLogDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void sendTrainingNotice() {
        logger.info(" MemberTaskService sendTrainingNotice   start  ");
        String sql = " select training_id from training where member_id = ? and `status` = 0 and lesson_date >=  ? ";
        String card_sql = " select card_no, member_id , type , start_date,end_date ,count, total  from member_card where member_id = ? and type not  in ('TY') and `start_date` <= ? and end_date >  ? ";
        String sql_log = " select * from sms_log where member_id = ? and type = ? and send_time >= ? ";

        // 1. 先找出所有有效会员
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT member_id ,name,phone, coach_staff_id from member where status = 1  ");
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            Map member = data.get(i);
            try {
                String memberId = member.get("member_id").toString();
                String name = member.get("name").toString();
                String phone = member.get("phone").toString();
                String staffId = member.get("coach_staff_id").toString();
                StaffEntity staffEntity = staffDao.getById(staffId);
                StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());

                // 2. 查询从十天前算起都没有上课和约课记录的用户
                List trainingList =  jdbcTemplate.queryForList(sql,new Object[]{memberId,ut.currentDate(-10)});
                if(CollectionUtils.isEmpty(trainingList)){

                    // 3. 查询用户有没有可用的课卡，体验卡除外
                    List cardList =  jdbcTemplate.queryForList(card_sql,new Object[]{memberId,ut.currentDate(-10),ut.currentDate()});
                    System.out.println(" memberId = "+memberId+" , name = "+member.get("name")+" , cardList = "+cardList.size());
                    if(cardList.size()>0 && total < 1000){
                        List logs = jdbcTemplate.queryForList(sql_log,new Object[]{memberId,SmsLogEnum.TRAINING_NOTICE.getKey(),ut.currentDate(-12)+" 00:00:00"});
                        if(logs.size()>0){
                            logger.info(" sendTrainingNotice  hasSend , member =  {} , log = {}  ",member , logs.get(0));
                            continue;
                        }

                        SmsUtil.sendTrainingNoticeToMember(phone,"10");
                        Thread.sleep(20);

                        SmsLogEntity smsLog= new SmsLogEntity();
                        smsLog.setLogId(IDUtils.getId());
                        smsLog.setCardNo("");
                        smsLog.setType(SmsLogEnum.TRAINING_NOTICE.getKey());
                        smsLog.setMemberId(memberId);
                        smsLog.setStoreId(storeEntity.getStoreId());
                        smsLog.setStaffId(staffEntity.getStaffId());
                        smsLog.setSendTime(ut.currentTime());
                        int n = smsLogDao.add(smsLog);

                        SmsUtil.sendTrainingNoticeToCoach(staffEntity.getPhone(),storeEntity.getName().replaceAll("店",""),name,"10");
                        Thread.sleep(20);

                        List<StaffEntity> managers = staffDao.getManagerByStoreId(staffEntity.getStoreId());
                        for (StaffEntity manager : managers){
                            if(StringUtils.isNotEmpty(manager.getPhone())){
                                try {
                                    SmsUtil.sendTrainingNoticeToCoach(manager.getPhone(),storeEntity.getName().replaceAll("店",""),name,"10");
                                    Thread.sleep(20);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        total++;
                    }
                }
            }catch (Exception e){
                logger.error(" sendTrainingNotice  ERROR , member =  {} ",member);
            }
        }
        logger.info("total = "+total);
        logger.info(" MemberTaskService sendTrainingNotice   end  ");

    }

    public void sendCardEndNotice() {
        logger.info(" MemberTaskService sendCardEndNotice   start  ");
        String card_sql = " select card_no, member_id , type , start_date,end_date ,count, total  from member_card " +
                " where type  in ('PT','TT','ST2','ST3','ST4','ST5','ST6') and  count > 0 and `start_date` <= ? and end_date >=  ? and end_date <=  ?  order by member_id ";
        List<Map<String,Object>> cardList =  jdbcTemplate.queryForList(card_sql,new Object[]{ut.currentDate(),ut.currentDate(),ut.currentDate(15)});
        int total = 0;
        String sql = " select * from sms_log where card_no = ? and type = ? ";

        for (int i = 0; i < cardList.size(); i++) {
            Map card = cardList.get(i);
            try {
                String memberId = card.get("member_id").toString();
                String cardNo = card.get("card_no").toString();
                String type = card.get("type").toString();
                String start_date = card.get("start_date").toString();
                String end_date = card.get("end_date").toString();
                String count = card.get("count").toString();
                String totalCard = card.get("total").toString();
                MemberEntity memberEntity = memberDao.getById(memberId);
                if(memberEntity.getStatus()==1){
                    List data = jdbcTemplate.queryForList(sql,new Object[]{cardNo,SmsLogEnum.CARD_END.getKey()});
                    if(data.size()>0){
                        logger.info(" sendCardEndNotice  hasSend , card =  {} , log = {}  ",card , data.get(0));
                        continue;
                    }
                    System.out.println(" memberId = "+memberId+" , cardNo = "+cardNo+" , type = "+type+" , start_date = "+start_date+" , end_date = "+end_date+" , count = "+count+" , totalCard = "+totalCard);
                    String staffId = memberEntity.getCoachStaffId();
                    StaffEntity staffEntity = staffDao.getById(staffId);
                    StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());

                    SmsUtil.sendCardEndNoticeToMember(memberEntity.getPhone(),cardNo);
                    Thread.sleep(20);

                    SmsLogEntity smsLog= new SmsLogEntity();
                    smsLog.setLogId(IDUtils.getId());
                    smsLog.setCardNo(cardNo);
                    smsLog.setType(SmsLogEnum.CARD_END.getKey());
                    smsLog.setMemberId(memberEntity.getMemberId());
                    smsLog.setStoreId(storeEntity.getStoreId());
                    smsLog.setStaffId(staffEntity.getStaffId());
                    smsLog.setSendTime(ut.currentTime());
                    int n = smsLogDao.add(smsLog);

                    SmsUtil.sendCardEndNoticeToCoach(staffEntity.getPhone(),storeEntity.getName().replaceAll("店",""),memberEntity.getName());
                    Thread.sleep(20);

                    List<StaffEntity> managers = staffDao.getManagerByStoreId(staffEntity.getStoreId());
                    for (StaffEntity manager : managers){
                        if(StringUtils.isNotEmpty(manager.getPhone())){
                            try {
                                SmsUtil.sendCardEndNoticeToCoach(manager.getPhone(),storeEntity.getName().replaceAll("店",""),memberEntity.getName());
                                Thread.sleep(20);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    total++;
                }
            }catch (Exception e){
                logger.error(" sendCardEndNotice  ERROR , card =  {} ",card);
            }

        }
        logger.info("total = "+total);
        logger.info(" MemberTaskService sendCardEndNotice   end  ");
    }

}
