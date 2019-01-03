package com.training.admin.task;

import com.training.admin.service.TrainingTaskService;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TrainingTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrainingTaskService trainingTaskService;

    /**
     * 更新训练状态
     */
    @Scheduled(cron = "0 2,32 * * * *")
    public void updateShowTag(){
        logger.info("start updateShowTag!  time = {} ", ut.currentTime());
        trainingTaskService.updateShowTag();
        logger.info("end updateShowTag!  time = {} ", ut.currentTime());
    }


}
