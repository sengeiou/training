package com.training.admin.task;

import com.training.admin.service.BackupService;
import com.training.admin.service.KpiStaffDetailAdminService;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackupTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BackupService backupService;

    @Autowired
    private KpiStaffDetailAdminService kpiStaffDetailAdminService;

    @Scheduled(cron = "0 50 23 * * *")
    public void backupMemberAndStaff(){
        String month = ut.currentFullMonth();
        String day = ut.currentDate();
        try {
            logger.info("start backupMember!  month = {} , time = {} ",month, ut.currentTime());
            backupService.backupMember();
            logger.info("end backupMember!  time = {} ", ut.currentTime());
        }catch (Exception e){

        }
        try {
            logger.info("start backupMemberCard!  time = {} ", ut.currentTime());
            backupService.backupMemberCard();
            logger.info("end backupMemberCard!  time = {} ", ut.currentTime());
        }catch (Exception e){

        }
        try {
            kpiStaffDetailAdminService.dealJk(day);
        }catch (Exception e){

        }
        try {
            kpiStaffDetailAdminService.dealXk(day);
        }catch (Exception e){

        }
        try {
            logger.info("start backupStaff!  time = {} ", ut.currentTime());
            backupService.backupStaff();
            logger.info("end backupStaff!  time = {} ", ut.currentTime());
        }catch (Exception e){

        }
    }

}
