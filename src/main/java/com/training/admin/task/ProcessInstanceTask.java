package com.training.admin.task;

import com.training.admin.service.ProcessInstanceService;
import com.training.common.ProcessCodeEnum;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstanceTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessInstanceService processInstanceService;

    @Scheduled(cron = "0 0/10 * * * *")
    public void getProcessInstance(){
//        logger.info("start getProcessInstance scheduled!  time = {} ", ut.currentTime());
        for (ProcessCodeEnum processCodeEnum : ProcessCodeEnum.values()){
            processInstanceService.getConcract(processCodeEnum);
        }
//        logger.info("end getProcessInstance scheduled!");
    }

}
