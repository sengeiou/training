package com.training.admin.task;

import com.training.admin.service.CreateCardService;
import com.training.admin.service.MemberTrainingTimeService;
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
    MemberTrainingTimeService memberTrainingTimeService;

    /**
     * 更新学员的训练时长
     */
//    @Scheduled(cron = "0 0/5 * * * *")
    public void updateMemberTrainingTimes(){
        logger.info("start updateMemberTrainingTimes!  time = {} ", ut.currentTime());
        memberTrainingTimeService.execute();
        logger.info("end updateMemberTrainingTimes!  time = {} ", ut.currentTime());
    }

}
