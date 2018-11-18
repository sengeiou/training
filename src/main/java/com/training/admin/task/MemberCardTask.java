package com.training.admin.task;

import com.training.admin.service.CreateCardService;
import com.training.admin.service.MemberTrainingTaskService;
import com.training.admin.service.ProcessInstanceService;
import com.training.common.ProcessCodeEnum;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MemberCardTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CreateCardService createCardService;

    @Autowired
    private MemberTrainingTaskService memberTrainingTaskService;

    @Scheduled(cron = "0 30 * * * *")
    public void createCard(){
        logger.info("start createCard scheduled!  time = {} ", ut.currentTime());
        createCardService.createCard();
        logger.info("end createCard scheduled!  time = {} ", ut.currentTime());
    }

    @Scheduled(cron = "0 15,45 * * * *")
    public void updateMemberCardStatus(){
        logger.info("start updateMemberCardStatus scheduled!  time = {} ", ut.currentTime());
        memberTrainingTaskService.updateMemberCardStatus();
        logger.info("end updateMemberCardStatus scheduled!  time = {} ", ut.currentTime());
    }

}
