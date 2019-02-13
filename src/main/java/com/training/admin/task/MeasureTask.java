package com.training.admin.task;

import com.training.admin.service.MeasureService;
import com.training.admin.service.TrainingTaskService;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MeasureTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MeasureService measureService;


    /**
     * 更新训练状态
     */
    @Scheduled(cron = "0 30 1,23 * * *")
    public void queryAll(){
        logger.info("start MeasureTask!  time = {} ", ut.currentTime());
        measureService.queryAll();
        logger.info("end MeasureTask!  time = {} ", ut.currentTime());
    }


}
