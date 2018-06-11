package com.training.admin.task;

import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstanceTaskService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "* * * * * *")
    public void getProcessInstance(){
        logger.info("start getProcessInstance scheduled!  time = {} ", ut.currentTime());
        try {
            Thread.sleep(2100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("end getProcessInstance scheduled!");
    }

}
