package com.training.admin.task;

import com.training.admin.service.BackupService;
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
    BackupService BbackupService;

    /**
     * 更新学员的训练时长
     */
    @Scheduled(cron = "0 45 23 * * *")
//    @Scheduled(cron = "0 * * * * *")
    public void backupMember(){
        logger.info("start backupMember!  time = {} ", ut.currentTime());
        BbackupService.backupMember();
        logger.info("end backupMember!  time = {} ", ut.currentTime());
    }

    /**
     * 更新学员状态
     */
    @Scheduled(cron = "0 50 23 * * *")
//    @Scheduled(cron = "0 * * * * *")
    public void backupStaff(){
        logger.info("start backupStaff!  time = {} ", ut.currentTime());
        BbackupService.backupStaff();
        logger.info("end backupStaff!  time = {} ", ut.currentTime());
    }

}
