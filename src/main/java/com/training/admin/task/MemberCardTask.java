package com.training.admin.task;

import com.training.admin.service.CreateCardService;
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

    @Scheduled(cron = "0 0/50 * * * *")
    public void createCard(){
        logger.info("start getProcessInstance scheduled!  time = {} ", ut.currentTime());
        createCardService.createCard();
        logger.info("end getProcessInstance scheduled!  time = {} ", ut.currentTime());
    }

}
