package com.training.admin.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.training.common.*;
import com.training.dao.*;
import com.training.entity.*;
import com.training.service.MemberCardService;
import com.training.service.MemberCouponService;
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
    private JdbcTemplate jdbcTemplate;

    public void sendTrainingNotice() {
        logger.info(" MemberTaskService sendTrainingNotice   start  ");
        String sql = " select training_id from training where member_id = ? and `status` = 0 and lesson_date <  ? ";
        String card_sql = " select card_no, member_id , type , start_date,end_date ,count, total  from member_card where member_id = ? and type not  in ('TY','ST1','ST2','ST3','ST4','ST5','ST6') and `start_date` <= ? and end_date >=  ? ";

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

                List trainingList =  jdbcTemplate.queryForList(sql,new Object[]{memberId,ut.currentDate(-10)});
                if(CollectionUtils.isEmpty(trainingList)){
                    List cardList =  jdbcTemplate.queryForList(card_sql,new Object[]{memberId,ut.currentDate(),ut.currentDate()});
                    System.out.println(" memberId = "+memberId+" , name = "+member.get("name")+" , cardList = "+cardList.size());
                    if(cardList.size()>0 && total < 3){
                        SmsUtil.sendTrainingNoticeToCoach(staffEntity.getPhone(),storeEntity.getName().replaceAll("店",""),name,"10");
                        Thread.sleep(20);
                        SmsUtil.sendTrainingNoticeToMember(phone,"10");
                        Thread.sleep(20);
                        List<StaffEntity> managers = staffDao.getManagerByStoreId(staffEntity.getStoreId());
                        for (StaffEntity manager : managers){
                            if(StringUtils.isNotEmpty(manager.getPhone())){
                                try {
                                    SmsUtil.sendTrainingNoticeToCoach(manager.getPhone(),storeEntity.getName().replaceAll("店",""),name,"10");
                                    Thread.sleep(20);
                                } catch (ClientException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    total++;
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


        logger.info(" MemberTaskService sendCardEndNotice   end  ");
    }

}
