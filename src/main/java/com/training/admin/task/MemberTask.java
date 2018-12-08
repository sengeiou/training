package com.training.admin.task;

import com.training.admin.service.MemberTaskService;
import com.training.admin.service.MemberTrainingTaskService;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MemberTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberTrainingTaskService memberTrainingTaskService;

    @Autowired
    MemberTaskService memberTaskService;

    /**
     * 更新学员的训练时长
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void updateMemberTrainingTimes(){
        logger.info("start updateTrainingHour!  time = {} ", ut.currentTime());
        memberTrainingTaskService.updateTrainingHour();
        logger.info("end updateTrainingHour!  time = {} ", ut.currentTime());
    }

    /**
     * 更新学员状态
     */
    @Scheduled(cron = "0 10 2 * * *")
    public void updateMemberStatus(){
        logger.info("start updateMemberStatus!  time = {} ", ut.currentTime());
        memberTrainingTaskService.updateMemberStatus();
        logger.info("end updateMemberStatus!  time = {} ", ut.currentTime());
    }

    /**
     * 更新学员勋章
     */
    @Scheduled(cron = "0 20 2 * * *")
    public void updateMemberMedal(){
        logger.info("start updateMemberMedal!  time = {} ", ut.currentTime());
        memberTrainingTaskService.updateMemberMedal();
        logger.info("end updateMemberMedal!  time = {} ", ut.currentTime());
    }

    /**
     * 更新学员勋章
     */
    @Scheduled(cron = "0 40 2 * * *")
    public void updateMemberCoupon(){
        logger.info("start updateMemberCoupon!  time = {} ", ut.currentTime());
        memberTrainingTaskService.updateMemberCoupon();
        logger.info("end updateMemberCoupon!  time = {} ", ut.currentTime());
    }

    /**
     * 出勤提醒
     */
    @Scheduled(cron = "0 0 10 * * *")
    public void sendTrainingNotice(){
        logger.info("start sendTrainingNotice!  time = {} ", ut.currentTime());
        memberTaskService.sendTrainingNotice();
        logger.info("end sendTrainingNotice!  time = {} ", ut.currentTime());
    }

    /**
     * 卡到期提醒
     */
    @Scheduled(cron = "0 5 10 * * *")
    public void sendCardEndNotice(){
        logger.info("start sendCardEndNotice!  time = {} ", ut.currentTime());
        memberTaskService.sendCardEndNotice();
        logger.info("end sendCardEndNotice!  time = {} ", ut.currentTime());
    }

    /**
     * 自动停课月卡会员自动复课
     */
    @Scheduled(cron = "0 30 8 * * *")
    public void restorePauseMembers(){
        logger.info("start restorePauseMembers!  time = {} ", ut.currentTime());
        memberTaskService.restorePauseMembers(ut.currentDate());
        logger.info("end restorePauseMembers!  time = {} ", ut.currentTime());
    }


}
