package com.training.admin.api;

import com.alibaba.fastjson.JSON;
import com.training.admin.service.ManualService;
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
        logger.info(" updateTrainingHour     ");
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(" SELECT *  from member  ");
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            Map member = data.get(i);
            String memberId = member.get("member_id").toString();
            String sql = " select training_id from training where member_id = ? and `status` = 0 and lesson_date <=  ? ";
            List trainingList =  jdbcTemplate.queryForList(sql,new Object[]{memberId,ut.currentDate(-1)});
            if(CollectionUtils.isNotEmpty(trainingList)){
                System.out.println(" name = "+member.get("name")+"   hours = "+trainingList.size());
                jdbcTemplate.update(" update member set training_hours = ? where member_id = ? ",new Object[]{trainingList.size(),memberId});
                total++;
            }
        }
        System.out.println("total = "+total);
        return "updateTrainingHour执行成功";
    }

    @GetMapping("createStaffMonth")
    public Object createStaffMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" createStaffMonth   ");
        String month = "201707";
        manualService.createStaffMonth(month);
        return "createStaffMonth执行成功";
    }


}
