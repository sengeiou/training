package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.training.common.CardTypeEnum;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.KpiStaffMonth;
import com.training.domain.Member;
import com.training.domain.MemberCard;
import com.training.domain.StoreData;
import com.training.entity.*;
import com.training.service.*;
import com.training.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class ManualService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StaffService staffService;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private CardService cardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private KpiStaffMonthService kpiStaffMonthService;

    @Autowired
    private KpiStaffMonthDao kpiStaffMonthDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createStaffMonth(String month) {
        StaffQuery query = new StaffQuery();
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<StaffEntity> staffList = staffDao.find(query,page);
        for (StaffEntity staffEntity : staffList){
            KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
            if(kpiStaffMonthEntity==null){
                createSingleStaffMonth(staffEntity.getStaffId(),month);
            }
        }
    }

    public int createSingleStaffMonth(String staffId,String month) {
        int n = 0;
        StaffEntity staffEntity = staffDao.getById(staffId);
        if(staffEntity==null){
            return n;
        }
        KpiStaffMonthEntity kpiStaffMonthEntity = kpiStaffMonthDao.getByIdAndMonth(staffEntity.getStaffId(),month);
        if(kpiStaffMonthEntity==null){
            kpiStaffMonthEntity = new KpiStaffMonthEntity();
            kpiStaffMonthEntity.setStaffId(staffEntity.getStaffId());
            kpiStaffMonthEntity.setStaffName(staffEntity.getCustname());
            kpiStaffMonthEntity.setStoreId(staffEntity.getStoreId());
            kpiStaffMonthEntity.setYear(month.substring(0,4));
            kpiStaffMonthEntity.setMonth(month);
            kpiStaffMonthEntity.setKpiScore("0");
            kpiStaffMonthEntity.setType("");
            if("教练".equals(staffEntity.getJob())){
                kpiStaffMonthEntity.setType("JL");
            }
            if("店长".equals(staffEntity.getJob())){
                kpiStaffMonthEntity.setType("DZ");
            }
            n = kpiStaffMonthDao.add(kpiStaffMonthEntity);
        }
        return n;
    }


    public void trainingExcel(String startDate, String endDate) {
        List data = jdbcTemplate.queryForList("select * from training where card_type in ('PM' ) and lesson_date >= ? and lesson_date <= ? and status >= 0 " +
                " order by staff_id , lesson_date ",new Object[]{startDate,endDate});
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("场地");
        titleRow.add("课程日期");
        titleRow.add("课程开始时间");
        titleRow.add("课程结束时间");
        titleRow.add("课程名");
        titleRow.add("教练");
        titleRow.add("会员");
        titleRow.add("预约人数");
        titleRow.add("会员卡名称");
        titleRow.add("耗课数量");
        titleRow.add("会员卡号");
        titleRow.add("有效期开始");
        titleRow.add("有效期结束");
        titleRow.add("单价");
        titleRow.add("合同金额");
        titleRow.add("购买课程节数/天数");
        titleRow.add("预约状态");
        titleRow.add("签到时间");
        System.out.println(data.size());
        excelData.add(titleRow);
        for(Object training : data){
            try {
                List<String> row = new ArrayList();
                training = (Map)training;
                String memberId = ((Map) training).get("member_id").toString();
                String staff_id = ((Map) training).get("staff_id").toString();
                String lesson_date = ((Map) training).get("lesson_date").toString();
                String start_hour = ((Map) training).get("start_hour").toString();
                String end_hour = ((Map) training).get("end_hour").toString();
                String card_no = ((Map) training).get("card_no").toString();
                String title = ((Map) training).get("title").toString();
                String type = ((Map) training).get("type").toString();
                String card_type = ((Map) training).get("card_type").toString();
                String sign_time = ((Map) training).get("sign_time").toString();

                MemberEntity member = memberDao.getById(memberId);
                if(member==null){
                    logger.error(" member==null ,  "+ JSON.toJSONString(training));
                    continue;
                }
                StaffEntity staffEntity = staffDao.getById(staff_id);
                if(staffEntity==null){
                    logger.error(" staffEntity==null ,  "+ JSON.toJSONString(training));
                    continue;
                }

                StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                MemberCardEntity memberCardEntity = memberCardDao.getById(card_no);

                if(memberCardEntity==null){
                    logger.error(" memberCardEntity==null ,  "+ JSON.toJSONString(training));
                    continue;
                }

                memberCardEntity.getMoney();
                double price =  Double.parseDouble(memberCardEntity.getMoney())/memberCardEntity.getTotal();
                if("PM".equals(card_type)||"TM".equals(card_type)){
                    int days = ut.passDayByDate(memberCardEntity.getStartDate(),memberCardEntity.getEndDate())+1;
//                    int leftDays = ut.passDayByDate(ut.currentDate(),memberCardEntity.getEndDate());
                    if(days>0){
                        price = Double.parseDouble(memberCardEntity.getMoney())/days;
                    }
                    memberCardEntity.setTotal(days);
                }
                row.add(storeEntity.getName());
                row.add("");
                row.add(lesson_date);
                row.add(start_hour);
                row.add(end_hour);
                row.add(title);
                row.add(staffEntity.getCustname());
                row.add(member.getName());
                row.add("1");
                row.add(CardTypeEnum.getEnumByKey(card_type).getDesc());
                row.add("1");
                row.add(memberCardEntity.getCardNo());
                row.add(memberCardEntity.getStartDate());
                row.add(memberCardEntity.getEndDate());
                row.add(ut.getDoubleString(price));
                row.add(memberCardEntity.getMoney());
                row.add(""+memberCardEntity.getTotal());
                row.add("已完成");
                row.add(sign_time);
                excelData.add(row);
            }catch (Exception e){
                logger.error(" training = "+ JSON.toJSONString(training),e);
            }
        }
        ExcelUtil.writeExcel(excelData,"C://training201809.xls");
    }


    public void monthCardExcel(String startDate, String endDate) {
        List data = jdbcTemplate.queryForList("select * from member_card where type in ('PM' )  order by card_no desc ",new Object[]{ });
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("会员卡号");
        titleRow.add("卡类型");
        titleRow.add("会员姓名");
        titleRow.add("会员电话");
        titleRow.add("有效期开始");
        titleRow.add("有效期结束");
        titleRow.add("单价");
        titleRow.add("合同金额");
        titleRow.add("购买课程节数/天数");
        System.out.println(data.size());
        excelData.add(titleRow);
        for(Object card : data){
            try {
                List<String> row = new ArrayList();
                String memberId = ((Map) card).get("member_id").toString();
                String card_no = ((Map) card).get("card_no").toString();
                String type = ((Map) card).get("type").toString();
                MemberEntity member = memberDao.getById(memberId);
                if(member==null){
                    logger.error(" member==null ,  "+ JSON.toJSONString(card));
                    continue;
                }
                StaffEntity staffEntity = staffDao.getById(member.getCoachStaffId());
                if(staffEntity==null){
                    logger.error(" staffEntity==null ,  "+ JSON.toJSONString(member));
                    continue;
                }

                StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                MemberCardEntity memberCardEntity = memberCardDao.getById(card_no);

                if(memberCardEntity==null){
                    logger.error(" memberCardEntity==null ,  "+ JSON.toJSONString(card));
                    continue;
                }

                memberCardEntity.getMoney();
                double price =  Double.parseDouble(memberCardEntity.getMoney())/memberCardEntity.getTotal();
                int days = ut.passDayByDate(memberCardEntity.getStartDate(),memberCardEntity.getEndDate())+1;
                if(days>0){
                    price = Double.parseDouble(memberCardEntity.getMoney())/days;
                }
                memberCardEntity.setTotal(days);
                row.add(storeEntity.getName());
                row.add(memberCardEntity.getCardNo());
                row.add(CardTypeEnum.getEnumByKey(type).getDesc());
                row.add(member.getName());
                row.add(member.getPhone());
                row.add(memberCardEntity.getStartDate());
                row.add(memberCardEntity.getEndDate());
                row.add(ut.getDoubleString(price));
                row.add(memberCardEntity.getMoney());
                row.add(""+memberCardEntity.getTotal());
                excelData.add(row);
            }catch (Exception e){
                logger.error(" training = "+ JSON.toJSONString(card),e);
            }
        }
        ExcelUtil.writeExcel(excelData,"C://monthCard"+System.currentTimeMillis()+".xls");
    }

    public int createStoreOpen(String storeId,String year) {
        StoreEntity storeEntity = storeDao.getById(storeId);
        if(storeEntity==null){
            return 0;
        }
        List data = jdbcTemplate.queryForList("select * from store_open where store_id = ? and  year = ?",new Object[]{storeId,year});
        if(data.size()>0){
            return 0;
        }
        String sql = " insert into store_open (store_id, year,created,modified) values (?,?,now(),now()) ";
        int n = jdbcTemplate.update(sql,new Object[]{storeId,year});
        return n;

    }

    public void updateCoachStaff() {
        logger.info(" updateCoachStaff  ");
        List<Map<String,Object>> deptList =  jdbcTemplate.queryForList("select * from store  ");
        for (int i = 0; i < deptList.size(); i++) {
            try {
                Map dept = deptList.get(i);
                String deptId = dept.get("dept_id").toString();
                System.out.println("dept_id: " + dept.get("dept_id").toString()+" , name: " + dept.get("name").toString());
                List<Map> staffList = DingtalkUtil.getStaffs(dept.get("dept_id").toString());
                for (int j = 0; j < staffList.size(); j++) {
                    try{
                        Map item = staffList.get(j);
                        System.out.println("userid: " + item.get("userid").toString());
                        System.out.println("name: " + item.get("name").toString());
                        String userid = item.get("userid").toString();
                        StaffEntity staffDB = staffService.getByPhone(item.get("mobile").toString());
                        if(staffDB!=null){
                            logger.info(item.get("name").toString()+"已存在，无需重复添加");
                            StaffEntity staffUpdate = new StaffEntity();
                            staffUpdate.setStaffId(staffDB.getStaffId());
                            staffUpdate.setCustname(item.get("name").toString());
                            staffUpdate.setStoreId(deptId);
                            if(item.containsKey("position")){
                                staffUpdate.setJob(item.get("position").toString());
                            }
                            staffService.update(staffUpdate);
                            continue;
                        }
                        String position = "";
                        if(item.containsKey("position")){
                            position = item.get("position").toString();
                        }
                        String mobile = "";
                        if(item.containsKey("mobile")){
                            mobile = item.get("mobile").toString();
                        }
                        String email = "";
                        if(item.containsKey("email")){
                            email = item.get("email").toString();
                        }
                        String extattr = "";
                        if(item.containsKey("extattr")){
                            extattr = item.get("extattr").toString();
                        }
                        StaffEntity staffEntity = new StaffEntity();
                        staffEntity.setStaffId(IDUtils.getId());
                        staffEntity.setRelId(item.get("userid").toString());
                        staffEntity.setStoreId(deptId);
                        staffEntity.setUsername(item.get("mobile").toString());
                        staffEntity.setCustname(item.get("name").toString());
                        staffEntity.setPhone(mobile);
                        staffEntity.setEmail(email);
                        staffEntity.setFeature(extattr);
                        staffEntity.setJob(position);
                        staffService.add(staffEntity);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){

            }
        }
    }

    public void exportAllMembers() {
        MemberQuery query = new MemberQuery();
        query.setType("M");
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(9999);
        List<MemberEntity> memberEntities = memberDao.find(query,pageRequest);
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("姓名");
        titleRow.add("电话");
        titleRow.add("教练");
        titleRow.add("来源");
        titleRow.add("状态");
        System.out.println(memberEntities.size());
        excelData.add(titleRow);
        for(MemberEntity memberEntity : memberEntities){
            try {
                List<String> row = new ArrayList();
                Member member = memberService.transferMember(memberEntity);
                String staffName = "";
                String storeName = "";
                if(StringUtils.isNotEmpty(member.getCoachStaffId())){
                    StaffEntity staffEntity = staffDao.getById(member.getCoachStaffId());
                    staffName = staffEntity.getCustname();
                    if(staffEntity!=null){
                        StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                        storeName = storeEntity.getName();
                    }
                }
                row.add(storeName);
                row.add(member.getName());
                row.add(member.getPhone());
                row.add(staffName);
                row.add(member.getOrigin());
                row.add(member.getShowStatus());
                excelData.add(row);
            }catch (Exception e){
                logger.error(" training = "+ JSON.toJSONString(memberEntity),e);
            }
        }
        ExcelUtil.writeExcel(excelData,"C://member_"+ut.currentDate()+".xls");
    }

    public void exportDeadLessonMoney(String startDate, String endDate) {
        logger.info(" exportDeadLessonMoney   startDate = {} ",startDate);
        logger.info(" exportDeadLessonMoney   endDate = {} ",endDate);


        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("姓名");
        titleRow.add("电话");
        titleRow.add("卡号");
        titleRow.add("开始日期");
        titleRow.add("结束日期");
        titleRow.add("购课节数");
        titleRow.add("购课金额");
        titleRow.add("死课节数");
        titleRow.add("死课收入金额");
        excelData.add(titleRow);

        String sql = " SELECT * from member where status <> 9 and created <= ? ";
        List<Map<String,Object>> members =  jdbcTemplate.queryForList(sql,new Object[]{endDate+" 23:59:59"});
        logger.info(" exportDeadLessonMoney  members = {} ",members.size());
        List deadList = new ArrayList();
        for (int i = 0; i < members.size(); i++) {
            Map member = members.get(i);
            String coach_staff_id = member.get("coach_staff_id").toString();
            String staffName = "";
            String storeName = "";
            if(StringUtils.isNotEmpty(coach_staff_id)){
                StaffEntity staffEntity = staffDao.getById(coach_staff_id);
                staffName = staffEntity.getCustname();
                if(staffEntity!=null){
                    StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                    storeName = storeEntity.getName();
                }
            }
            String sql_card = "select * from member_card where member_id = ? and type in ('PT') and count > 0 and end_date >= ? and end_date < ?  ";
            if(ut.passDay(ut.currentDate(),endDate)>0){
                endDate = ut.currentDate();
            }
            List cards = jdbcTemplate.queryForList(sql_card,new Object[]{member.get("member_id").toString(),ut.currentDate(endDate,-60),ut.currentDate(endDate,-30)});
//            logger.info(" exportDeadLessonMoney cards = {} ",cards.size());
            for (int j = 0; j < cards.size(); j++) {
                Map card = (Map)cards.get(j);
                double money_sk = 0;
                int total = 0;
                int left_count = 0;
                double money = 0;
                try{
                    total = Integer.parseInt(card.get("total").toString());
                    left_count = Integer.parseInt(card.get("count").toString());
                }catch (Exception e){

                }
                try{
                    money = Double.parseDouble(card.get("money").toString());
                }catch (Exception e){

                }
                if(total>0){
                    money_sk = money*left_count/total;
                }
                card.put("storeName",storeName);
                card.put("staffName",staffName);
                card.put("memberName",member.get("name"));
                card.put("phone",member.get("phone"));
                card.put("money_sk",ut.getDoubleString(money_sk));
                System.out.println("card :"+card);
                List<String> row = new ArrayList();
                row.add(storeName);
                row.add(member.get("name").toString());
                row.add(member.get("phone").toString());
                row.add(card.get("card_no").toString());
                row.add(card.get("start_date").toString());
                row.add(card.get("end_date").toString());
                row.add(card.get("total").toString());
                row.add(card.get("money").toString());
                row.add(card.get("count").toString());
                row.add(ut.getDoubleString(money_sk));
                excelData.add(row);
                deadList.add(card);
            }
        }
        logger.info(" exportDeadLessonMoney     deadList = {} ",deadList.size());
        ExcelUtil.writeExcel(excelData,"C://deadlesson_"+ut.currentDate()+".xls");

    }

}

