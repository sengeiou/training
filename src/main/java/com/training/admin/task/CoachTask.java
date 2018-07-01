package com.training.admin.task;

import com.training.admin.service.CoachKpiUpdateService;
import com.training.admin.service.MemberTrainingTimeService;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CoachKpiUpdateService coachKpiUpdateService;

    /**
     * 更新教练KPI
     */
//    @Scheduled(cron = "0 0/5 * * * *")
    public void updateCoachKpi(){
        logger.info("start updateMemberTrainingTimes!  time = {} ", ut.currentTime());
        coachKpiUpdateService.execute();
        logger.info("end updateMemberTrainingTimes!  time = {} ", ut.currentTime());
    }

}
