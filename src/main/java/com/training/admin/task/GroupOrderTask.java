package com.training.admin.task;

import com.training.service.GroupOrderService;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GroupOrderTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderService groupOrderService;

    /**
     * 更新团单状态
     */
    @Scheduled(cron = "0 * * * * *")
    public void dealOrder(){
        logger.info("start dealOrder!  time = {} ", ut.currentTime());
        groupOrderService.dealOrder();
        logger.info("end dealOrder!  time = {} ", ut.currentTime());
    }

}
