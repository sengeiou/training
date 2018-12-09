package com.training.admin.api;

import com.training.admin.service.*;
import com.training.dao.ContractManualDao;
import com.training.entity.*;
import com.training.service.*;
import com.training.util.*;
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
    private MemberCardService memberCardService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractManualService contractManualService;

    @Autowired
    private ContractManualDao contractManualDao;

    @Autowired
    private TrainingTaskService trainingTaskService;

    @Autowired
    private ManualService manualService;

    @Autowired
    private MemberTrainingTaskService memberTrainingTaskService;

    @Autowired
    private CreateCardService createCardService;

    @Autowired
    private MemberTaskService memberTaskService;

    @Autowired
    private ContractAdminService contractAdminService;

    @Autowired
    private KpiStaffDetailAdminService kpiStaffDetailAdminService;

    @Autowired
    private ReportOnceService reportOnceService;

    @Autowired
    private ReportMonthService reportMonthService;

    @Autowired
    private ReportStaffService reportStaffService;

    @Autowired
    private BackupService backupService;

    @Autowired
    private CoachStaffStarService coachStaffStarService;

    @Autowired
    private CoachStaffKpiService coachStaffKpiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SmsUtil smsUtil;

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

    @GetMapping("updateMemberCoupon")
    public Object updateMemberCoupon(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return memberTrainingTaskService.updateMemberCoupon();
    }

    @GetMapping("updateMemberInfo")
    public Object updateMemberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return memberTrainingTaskService.updateMemberInfo();
    }

    @GetMapping("updateTrainingInfo")
    public Object updateTrainingInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return memberTrainingTaskService.updateTrainingInfo();
    }

    @GetMapping("updateContractInfo")
    public Object updateContractInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return contractAdminService.updateContractInfo();
    }

    @GetMapping("updateMemberCardStatus")
    public Object updateMemberCardStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return memberTrainingTaskService.updateMemberCardStatus();
    }

    @GetMapping("createStaffMonth")
    public Object createStaffMonth(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" createStaffMonth   ");
        String month = "201810";
        manualService.createStaffMonth(month);
        return "createStaffMonth执行成功";
    }

    @GetMapping("kpione")
    public Object calculateOneKpi(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" calculateOneKpi   ");
        String staffId = "15324796409527304d2f3fbb940798d55e826b496ed41";
        String month = "201811";
        coachStaffKpiService.calculateStaffKpi(staffId,month);
        return "calculateOneKpi执行成功";
    }

    @GetMapping("kpi")
    public Object calculateKpi(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" =======   calculateKpi  execute start  ");
        String month = "201811";
        List<Map<String,Object>> coachs =  jdbcTemplate.queryForList(" SELECT staff_id from staff where job in ('教练','店长') ");
        for (int i = 0; i < coachs.size(); i++){
            Map staff = coachs.get(i);
            String staffId = staff.get("staff_id").toString();
            try {
                coachStaffKpiService.calculateStaffKpi(staffId,month);
            }catch (Exception e){
                logger.error(" ============== calculateKpiError  staffId:{} ",staffId);
                e.printStackTrace();
            }
        }
        List<Map<String,Object>> stores =  jdbcTemplate.queryForList(" SELECT store_id from store where store_id not in ('0') ");
        for (int i = 0; i < stores.size(); i++){
            Map store = stores.get(i);
            String store_id = store.get("store_id").toString();
            try {
                coachStaffKpiService.calculateStoreKpi(store_id,month);
            }catch (Exception e){
                logger.error(" ============== calculateKpiError  store_id:{} ",store_id);
                e.printStackTrace();
            }
        }
        logger.info(" =======   calculateKpi  execute end  ");
        return "calculateKpi执行成功";
    }

    @GetMapping("star")
    public Object calculateStar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" =======   calculateStar  execute start  ");
        String month = "201811";
        List<Map<String,Object>> coachs =  jdbcTemplate.queryForList(" SELECT staff_id from staff where job in ('教练') ");
        for (int i = 0; i < coachs.size(); i++){
            Map staff = coachs.get(i);
            String staffId = staff.get("staff_id").toString();
            coachStaffStarService.calculateStaffStar(staffId,month);
        }
        logger.info(" =======   calculateStar  execute end  ");
        return "calculateStar执行成功";
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

    @GetMapping("backupMembers")
    public Object backupMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" backupMembers  start ");
        String backupDate = "2018-10-02";
        String tableName = "member_his_10";
        System.out.println(" backupMember  tableName = "+tableName);
        String sql = " INSERT into "+tableName+"  select null, '"+backupDate+"',a.member_id,IFNULL(b.store_id,''),a.phone,a.coach_staff_id,a.training_hours,a.open_id,a.status ,a.created,a.modified from member a LEFT JOIN staff b on a.coach_staff_id = b.staff_id and  a.created <= '2010-10-02 23:59:59'  ";
        try{
            int n = jdbcTemplate.update(sql);
            System.out.println(" backupMember  n = "+n);
        }catch (Exception e){
            System.out.println(" backupMember  ERROR : "+e.getMessage());
//            logger.error(" backupMember  ERROR : {}" , e.getMessage(),e);
        }
        return "backupMembers end ";
    }

    @GetMapping("backupMemberCard")
    public Object backupMemberCard(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" backupMembers  start ");
        backupService.backupMemberCard();
        return "backupMembers end ";
    }

    @GetMapping("updateShowTag")
    public Object updateShowTag(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" updateShowTag   ");
        String startDate = "2018-09-01";
        String endDate = "2018-09-30";
        trainingTaskService.updateShowTag();
        return "updateShowTag";
    }


    @GetMapping("dealContract")
    public Object dealContract(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" dealContract start  ");
        String id = "f383ac47-14ac-40ae-b7d8-f5ae1e4a5c92";
        String memberId = "15391682440744e73a7d6d7c54f1dbaa8bb5bcf0d423f";
        ContractEntity contractEntity = contractService.getById(id);
        MemberEntity memberDB = memberService.getById(memberId);
        createCardService.createPT(contractEntity,memberDB);
        return "dealContract end";
    }

    /**
     * 出勤提醒
     */
    @GetMapping("sendTrainingNotice")
    public void sendTrainingNotice(){
        logger.info("start sendTrainingNotice!  time = {} ", ut.currentTime());
        memberTaskService.sendTrainingNotice();
        logger.info("end sendTrainingNotice!  time = {} ", ut.currentTime());
    }

    /**
     * 卡到期提醒
     */
    @GetMapping("sendCardEndNotice")
    public void sendCardEndNotice(){
        logger.info("start sendCardEndNotice!  time = {} ", ut.currentTime());
        memberTaskService.sendCardEndNotice();
        logger.info("end sendCardEndNotice!  time = {} ", ut.currentTime());
    }

    /**
     * 卡到期提醒
     */
    @GetMapping("deleteMonthMedal")
    public void deleteMonthMedal(){
        logger.info("start deleteMonthMedal!  time = {} ", ut.currentTime());
        List<Map<String,Object>> medals =  jdbcTemplate.queryForList(" SELECT * from member_medal where medal_id <> 'OX' ");
        int error = 0;
        int success = 0;
        for (int i = 0; i < medals.size(); i++){
            Map medal = medals.get(i);
            String pk_id = medal.get("pk_id").toString();
            String memberId = medal.get("member_id").toString();
            List cards = jdbcTemplate.queryForList(" SELECT * from member_card where member_id = ?  and type = 'PT' ",new Object[]{memberId});
            if(cards.size()==0){
                error++;
                int n = jdbcTemplate.update("DELETE from member_medal where pk_id = ? ",new Object[]{pk_id});
                if(n==1){
                    success++;
                }
            }
        }
        logger.info(" error = {} ", error);
        logger.info(" success = {} ", success);
        logger.info("end deleteMonthMedal!  time = {} ", ut.currentTime());
    }

    /**
     * 卡到期提醒
     */
    @GetMapping("deleteMonthMedal2")
    public void deleteMonthMedal2(){
        logger.info("start deleteMonthMedal2!  time = {} ", ut.currentTime());
        List<Map<String,Object>> coupons =  jdbcTemplate.queryForList(" SELECT * from member_coupon where origin like '%CQ%' or origin like '%SJ%' ");
        int error = 0;
        int canDelete = 0;
        int success = 0;
        for (int i = 0; i < coupons.size(); i++){
            Map coupon = coupons.get(i);
            String coupon_id = coupon.get("coupon_id").toString();
            String memberId = coupon.get("member_id").toString();
            String status = coupon.get("status").toString();
            List cards = jdbcTemplate.queryForList(" SELECT * from member_card where member_id = ?  and type = 'PT' ",new Object[]{memberId});
            if(cards.size()==0){
                error++;
                logger.info(" memberId = {} ", memberId);
                if(status.equals("0")){
                    canDelete++;
                    int n = jdbcTemplate.update("DELETE from member_coupon where coupon_id = ? ",new Object[]{coupon_id});
                    if(n==1){
                        success++;
                    }
                }
            }
        }
        logger.info(" error = {} ", error);
        logger.info(" canDelete = {} ", canDelete);
        logger.info(" success = {} ", success);
        logger.info("end deleteMonthMedal2!  time = {} ", ut.currentTime());
    }

    /**
     * sendTest
     */
    @GetMapping("sendTest")
    public void sendTest(){
        logger.info("start sendTest!  time = {} ", ut.currentTime());
        logger.info(" smsUtil.sendTag = {}",smsUtil.sendTag);
        logger.info("end sendTest!  time = {} ", ut.currentTime());
    }

    @GetMapping("dealJkAndXk")
    public void dealJkAndXk(){
        logger.info("start dealJkAndXk!  time = {} ", ut.currentTime());
        String start = "2018-06-01";
        String end = "2018-06-30";
        end = "2018-12-07";
        int days = ut.passDayByDate(start,end)+1;
        for (int i = 0; i < days; i++) {
            String day = ut.currentDate(start,i);
            logger.info(" dealJkAndXk !  day = {} ",day);
            kpiStaffDetailAdminService.dealJk(day);
            kpiStaffDetailAdminService.dealXk(day);
        }
        logger.info("end dealJkAndXk!  time = {} ", ut.currentTime());
    }

    @GetMapping("dealTk")
    public void dealTk(){
        logger.info("start dealTk!  time = {} ", ut.currentTime());
        String start = "2018-10-02";
        String end = "2018-06-30";
        end = "2018-12-08";
        int days = ut.passDayByDate(start,end)+1;
        for (int i = 0; i < days; i++) {
            String day = ut.currentDate(start,i);
            logger.info(" dealTk !  day = {} ",day);
            kpiStaffDetailAdminService.dealTk(day);
        }
        logger.info("end dealTk!  time = {} ", ut.currentTime());
    }

    @GetMapping("updateKpiAndStar")
    public Object updateKpiAndStar(){
        logger.info("start updateKpiAndStar!  time = {} ", ut.currentTime());
        String msg = kpiStaffDetailAdminService.updateKpiAndStar();
        logger.info("end updateKpiAndStar!  time = {} ", ut.currentTime());
        return msg;
    }

    @GetMapping("calculateStoreFinanceOnceReport")
    public Object calculateStoreFinanceOnceReport(){
        logger.info("start calculateStoreFinanceOnceReport!  time = {} ", ut.currentTime());
        String storeId = "31978073";
        String today = "2018-11-30";
        today = ut.currentDate();
        List<Map<String,Object>> stores =  jdbcTemplate.queryForList(" SELECT store_id from store where store_id not in ('0') ");
        String msg = "";
        for (int i = 0; i < stores.size(); i++){
            Map store = stores.get(i);
            storeId = store.get("store_id").toString();
            msg = reportOnceService.calculateStoreFinanceOnceReport(storeId,today);
        }

        logger.info("end calculateStoreFinanceOnceReport!  time = {} ", ut.currentTime());
        return msg;
    }

    @GetMapping("calculateStoreFinanceMonthReport")
    public Object calculateStoreFinanceMonthReport(){
        logger.info("start calculateStoreFinanceMonthReport!  time = {} ", ut.currentTime());
        String storeId = "31978073";
        String today = "2018-11-30";
        today = ut.currentDate();
        List<Map<String,Object>> stores =  jdbcTemplate.queryForList(" SELECT store_id from store where store_id not in ('0') ");
        String msg = "";
        for (int i = 0; i < stores.size(); i++){
            Map store = stores.get(i);
            storeId = store.get("store_id").toString();
            msg = reportMonthService.calculateStoreFinanceMonthReport(storeId,today);
        }

        logger.info("end calculateStoreFinanceMonthReport!  time = {} ", ut.currentTime());
        return msg;
    }

    @GetMapping("calculateStaffFinanceReport")
    public Object calculateStaffFinanceReport(){
        logger.info("start calculateStaffFinanceReport!  time = {} ", ut.currentTime());
        String staffId = "1530715402419e703a209dd8d4e79892f7e0b8952344d";
        String today = "2018-11-30";
        today = ut.currentDate();
        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(" SELECT staff_id from staff  ");
        String msg = "";
        for (int i = 0; i < staffs.size(); i++){
            logger.info(" calculateStaffFinanceReport!  index = {} ", i);
            Map staff = staffs.get(i);
            staffId = staff.get("staff_id").toString();
            msg = reportStaffService.calculateStaffFinanceReport(staffId,today);
        }

        logger.info("end calculateStaffFinanceReport!  time = {} ", ut.currentTime());
        return msg;
    }

    @GetMapping("updateStaffStar")
    public Object updateStaffStar(){
        logger.info("start updateStaffStar!  time = {} ", ut.currentTime());
        String staffId = "1530715402419e703a209dd8d4e79892f7e0b8952344d";
        String month = "2018-11";
        String msg = coachStaffStarService.calculateStaffStar(staffId,month);
        logger.info("end updateStaffStar!  time = {} ", ut.currentTime());
        return msg;
    }


    @GetMapping("restorePauseMembers")
    public Object restorePauseMembers(){
        logger.info("start restorePauseMembers!  time = {} ", ut.currentTime());
        String day = ut.currentDate();
        String msg = memberTaskService.restorePauseMembers(day);
        logger.info("end restorePauseMembers!  time = {} ", ut.currentTime());
        return msg;
    }

}
