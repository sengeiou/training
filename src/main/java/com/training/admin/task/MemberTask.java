package com.training.admin.task;

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

    /**
     * 更新学员的训练时长
     */
    @Scheduled(cron = "0 1 0 * * *")
    public void updateMemberTrainingTimes(){
        logger.info("start updateTrainingHour!  time = {} ", ut.currentTime());
        memberTrainingTaskService.updateTrainingHour();
        logger.info("end updateTrainingHour!  time = {} ", ut.currentTime());
    }

}
