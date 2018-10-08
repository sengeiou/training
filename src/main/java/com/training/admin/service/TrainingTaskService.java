package com.training.admin.service;

import com.training.dao.*;
import com.training.service.CardService;
import com.training.service.MemberService;
import com.training.service.SysLogService;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class TrainingTaskService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private CardService cardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void updateShowTag() {
        String sql = " select * from  training where show_tag = 0  ";
        String update_sql = " update training set show_tag = ? ,  modified = now()  where  training_id =  ? ";

        List data = jdbcTemplate.queryForList(sql,new Object[]{});
        logger.info("  updateShowTag data.size()  = {}",data.size());
        for (int i = 0; i < data.size(); i++) {
            Map training = (Map) data.get(i);
            try{
                logger.info("  updateShowTag i = {}",i);
                int showTag = 0;
                String trainingId = training.get("training_id").toString();
                Integer status = Integer.parseInt(training.get("status").toString());
                Integer startHour = Integer.parseInt(training.get("start_hour").toString());
                Integer endHour = Integer.parseInt(training.get("end_hour").toString());
                String lessonDate = training.get("lesson_date").toString();
                String signTime = "";
                if(training.get("sign_time")!=null){
                    signTime = training.get("sign_time").toString();
                }
                if(status==-1){
                    showTag = -1;
                }
                if(ut.passDayByDate(lessonDate,ut.currentDate())<0){
                    continue;
                }
                if(ut.passDayByDate(lessonDate,ut.currentDate())>0){
                    if(!StringUtils.isEmpty(signTime)){
                        showTag = 1;
                    }else{
                        showTag = 2;
                    }
                }
                if(ut.passDayByDate(lessonDate,ut.currentDate())==0){
                    System.out.println("lessonDate="+lessonDate+" , ut.currentDate()="+ut.currentDate());
                    int time = ut.currentHour();
                    if(time>endHour){
                        if(!StringUtils.isEmpty(signTime)){
                            showTag = 1;
                        }else{
                            showTag = 2;
                        }
                    }
                }
                if(showTag!=0){
                    jdbcTemplate.update(update_sql,new Object[]{showTag,trainingId});
                }
            }catch (Exception e){
                logger.error("  updateShowTag  ERROR  training_id = {}",training.get("training_id"),e);
            }
        }
    }
}

