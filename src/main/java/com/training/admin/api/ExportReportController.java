package com.training.admin.api;

import com.training.admin.service.*;
import com.training.dao.ContractManualDao;
import com.training.entity.ContractEntity;
import com.training.entity.MemberEntity;
import com.training.service.*;
import com.training.util.SmsUtil;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户 API控制器
 */
@RestController
@RequestMapping("/exportReport")
public class ExportReportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExportFileService exportFileService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("trainingExcel")
    public Object trainingExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" trainingExcel   ");
        String startDate = "2019-03-01";
        String endDate = "2019-03-31";
        exportFileService.trainingExcel(startDate,endDate);
        return "trainingExcel执行成功";
    }

    @GetMapping("exportAllMembers")
    public Object exportAllMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" exportAllMembers   ");
        exportFileService.exportAllMembers();
        return "exportAllMembers";
    }

    @GetMapping("exportDeadLessonMoney")
    public Object exportDeadLessonMoney(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" exportDeadLessonMoney  start ");
        String startDate = "2018-10-01";
        String endDate = "2018-10-31";
        exportFileService.exportDeadLessonMoney(startDate,endDate);
        return "exportDeadLessonMoney end ";
    }


    @GetMapping("exportStaffMember")
    public Object exportStaffMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" exportStaffMember  start ");
        String date = "2018-10-31";
        exportFileService.exportStaffMember(date);
        return "exportStaffMember end ";
    }

    @GetMapping("monthCardExcel")
    public Object monthCardExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" monthCardExcel   ");
        String startDate = "2019-03-01";
        String endDate = "2019-03-31";
        exportFileService.monthCardExcel(startDate,endDate);
        return "monthCardExcel执行成功";
    }

    @GetMapping("contractSale")
    public Object contractSale(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" contractSale   ");
        String startDate = "2019-01-01";
        String endDate = "2019-01-31";
        exportFileService.contractSale(startDate,endDate);
        return "contractSale执行成功";
    }

    /**
     * 死课结课明细表
     */
    @GetMapping("deadAndEndCard")
    public Object deadAndEndCard(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" deadAndEndCard   ");
        String startDate = "2019-03-01";
        String endDate = "2019-03-31";
        exportFileService.deadAndEndCard(startDate,endDate);
        return "deadAndEndCard执行成功";
    }

    /**
     * 死课明细表
     */
    @GetMapping("deadCard")
    public Object deadCard(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" deadCard   ");
        String startDate = "2018-11-01";
        String endDate = "2018-11-30";
        exportFileService.deadCard(startDate,endDate);
        return "deadCard执行成功";
    }

    /**
     * kpi报表
     */
    @GetMapping("staffKpi")
    public Object staffKpi(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" staffKpi   ");
        String month = "201902";
        exportFileService.staffKpi(month);
        exportFileService.managerKpi(month);
        return "staffKpi";
    }

    /**
     * 有效会员明细表
     */
    @GetMapping("staffMemberDetailByDay")
    public Object staffMemberDetailByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" staffMemberDetailByDay   ");
        String startDate = "2019-01-01";
        String endDate = "2019-01-31";
        String month = "2019-01";
        exportFileService.staffMemberDetailByDay(month);
        return "staffMemberDetailByDay";
    }

}
