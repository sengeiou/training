package com.training.admin.api;

import com.alibaba.fastjson.JSON;
import com.training.admin.service.CalculateKpiService;
import com.training.admin.service.ManualService;
import com.training.admin.service.MemberTrainingTaskService;
import com.training.common.CardTypeEnum;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.ContractManualDao;
import com.training.entity.*;
import com.training.service.*;
import com.training.util.DingtalkUtil;
import com.training.util.ExcelUtil;
import com.training.util.IDUtils;
import com.training.util.ut;
import org.apache.commons.collections.CollectionUtils;
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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 用户 API控制器
 */
@RestController
@RequestMapping("/manual")
public class ManualRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreService storeService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CardService cardService;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractManualService contractManualService;

    @Autowired
    private ContractManualDao contractManualDao;


    @Autowired
    private ManualService manualService;

    @Autowired
    private CalculateKpiService calculateKpiService;

    @Autowired
    MemberTrainingTaskService memberTrainingTaskService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("updateCoachStaffId")
    public Object updateCoachStaffId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" updateCoachStaffId     ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT a.staff_id , a.custname ,a.phone coach_phone,b.member_name , b.phone  from staff a , contract_manual b  where a.phone = b.coach_phone\n ");
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            Map contract = data.get(i);
            MemberEntity memberEntity = memberService.getByPhone(contract.get("phone").toString());
            if(memberEntity==null){
                System.out.println(" ===========   contract  不存在  : " + contract);
            }else{
                if(StringUtils.isEmpty(memberEntity.getCoachStaffId())){
                    System.out.println(" **************   getCoachStaffId  不存在  : contract = " + contract + " , memberEntity = " + memberEntity );
//                    jdbcTemplate.update(" update member set coach_staff_id = ? where member_id = ? ",new Object[]{contract.get("staff_id"),memberEntity.getMemberId()});
                    total++;
                }
            }
        }
        System.out.println("total = "+total);
        return "updateCoachStaffId执行成功";
    }

    @GetMapping("updateTrainingHour")
    public Object updateTrainingHour(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return memberTrainingTaskService.updateTrainingHour();
    }

    @GetMapping("updateMemberStatus")
    public Object updateMemberStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return memberTrainingTaskService.updateMemberStatus();
    }

    @GetMapping("updateMemberMedal")
    public Object updateMemberMedal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return memberTrainingTaskService.updateMemberMedal();
    }

    @GetMapping("createStaffMonth")
    public Object createStaffMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" createStaffMonth   ");
        String month = "201808";
        manualService.createStaffMonth(month);
        return "createStaffMonth执行成功";
    }

    @GetMapping("kpi")
    public Object calculateKpi(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" calculateKpi   ");
//        String staffId = "15301384842221555143d88014ac5a5634d21fb41b64b";
        String month = "201807";
        List<Map<String,Object>> coachs =  jdbcTemplate.queryForList(" SELECT staff_id from staff where job = '教练' ");

        for (int i = 0; i < coachs.size(); i++){
            Map staff = coachs.get(i);
            String staffId = staff.get("staff_id").toString();
            calculateKpiService.calculateStaffKpi(staffId,month);
        }

        List<Map<String,Object>> managers =  jdbcTemplate.queryForList(" SELECT staff_id from staff where job = '店长' ");
        for (int i = 0; i < managers.size(); i++){
            Map staff = managers.get(i);
            String staffId = staff.get("staff_id").toString();
            calculateKpiService.calculateStaffKpi(staffId,month);
        }

        logger.info(" coachs.size() = "+coachs.size());
        logger.info(" managers.size() = "+managers.size());
        return "calculateKpi执行成功";
    }

    @GetMapping("trainingExcel")
    public Object trainingExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" trainingExcel   ");
        String startDate = "2018-07-01";
        String endDate = "2018-07-31";
        manualService.trainingExcel(startDate,endDate);
        return "trainingExcel执行成功";
    }

    @GetMapping("createStoreOpen")
    public Object createStoreOpen(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" createStoreOpen   ");
        String year = "2018";
        List<Map<String,Object>> stores =  jdbcTemplate.queryForList(" SELECT store_id from store ");
        for (int i = 0; i < stores.size(); i++){
            Map store = stores.get(i);
            String storeId = store.get("store_id").toString();
            manualService.createStoreOpen(storeId,year);
        }
        return "createStoreOpen 执行成功";
    }

}
