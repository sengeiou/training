package com.training.admin.task;

import com.training.admin.service.BackupService;
import com.training.admin.service.CoachKpiUpdateService;
import com.training.admin.service.ManualService;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CoachTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CoachKpiUpdateService coachKpiUpdateService;

    @Autowired
    private ManualService manualService;

    /**
     * 更新教练KPI
     */
    @Scheduled(cron = "0 30 23 * * *")
    public void updateCoachStaff(){
        logger.info("start updateCoachStaff!  time = {} ", ut.currentTime());
        manualService.updateCoachStaff();
        logger.info("end updateCoachStaff!  time = {} ", ut.currentTime());
    }

    /**
     * 更新教练KPI
     */
    @Scheduled(cron = "0 55 23 * * *")
    public void updateCoachKpi(){
        logger.info("start updateMemberTrainingTimes!  time = {} ", ut.currentTime());
        coachKpiUpdateService.execute();
        logger.info("end updateMemberTrainingTimes!  time = {} ", ut.currentTime());
    }

}
