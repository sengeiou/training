package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.training.common.CardTypeEnum;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.Member;
import com.training.entity.*;
import com.training.service.KpiStaffMonthService;
import com.training.service.MemberService;
import com.training.service.StaffService;
import com.training.service.SysLogService;
import com.training.util.DingtalkUtil;
import com.training.util.ExcelUtil;
import com.training.util.IDUtils;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class ExportFileService {

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
    private SysLogService sysLogService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private KpiStaffMonthService kpiStaffMonthService;

    @Autowired
    private KpiStaffMonthDao kpiStaffMonthDao;

    @Autowired
    private KpiTemplateDao kpiTemplateDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void trainingExcel(String startDate, String endDate) {
        List data = jdbcTemplate.queryForList("select * from training where  lesson_date >= ? and lesson_date <= ? and status >= 0 " +
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
        titleRow.add("上课人数");
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
                String lesson_id = ((Map) training).get("lesson_id").toString();
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
                row.add(storeEntity.getName());
                row.add("");
                row.add(lesson_date);
                row.add(start_hour);
                row.add(end_hour);
                row.add(title);
                row.add(staffEntity.getCustname());
                row.add(member.getName());

                double price =  Double.parseDouble(memberCardEntity.getMoney())/memberCardEntity.getTotal();
                if("PM".equals(card_type)||"TM".equals(card_type)){
                    int days = ut.passDayByDate(memberCardEntity.getStartDate(),memberCardEntity.getEndDate())+1;
//                    int leftDays = ut.passDayByDate(ut.currentDate(),memberCardEntity.getEndDate());
                    if(days>0){
                        price = Double.parseDouble(memberCardEntity.getMoney())/days;
                    }
                    memberCardEntity.setTotal(days);
                    List lessons = jdbcTemplate.queryForList("select 1 from training where lesson_id = ? and status >=0 ",new Object[]{lesson_id});
                    row.add(""+lessons.size());
                }else{
                    row.add("1");
                }
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
        ExcelUtil.writeExcel(excelData,"C://product/training201811.xls");
    }


    public void monthCardExcel(String startDate, String endDate) {
        List data = jdbcTemplate.queryForList("select * from member_card where type in ('PM' ) and end_date >= ? and created <= ? " +
//                " and card_no = 10916 " +
                " order by card_no desc ",new Object[]{ startDate, endDate+" 23:59:59"});
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
        titleRow.add("耗课天数");
        titleRow.add("停课天数");
        titleRow.add("耗课总金额");

        System.out.println(data.size());
        excelData.add(titleRow);
        for(Object card : data){
            try {
                List<String> row = new ArrayList();
                String memberId = ((Map) card).get("member_id").toString();
                String card_no = ((Map) card).get("card_no").toString();
                String type = ((Map) card).get("type").toString();
                String start_date = ((Map) card).get("start_date").toString();
                String end_date = ((Map) card).get("end_date").toString();

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

                int monthDays = ut.passDayByDate(startDate,endDate)+1;
                double money = 0;
                int monthDays2 = ut.passDayByDate(startDate,end_date)+1;
                if(monthDays>monthDays2){
                    monthDays = monthDays2;
                }

                int pauseDays = getPauseDaysByMonth(memberId,startDate,endDate);

                memberCardEntity.getMoney();
                double price =  Double.parseDouble(memberCardEntity.getMoney())/memberCardEntity.getTotal();
                int days = memberCardEntity.getDays();
                if(days>0){
                    price = Double.parseDouble(memberCardEntity.getMoney())/days;
                    if(monthDays-pauseDays>0){
                        money = price*(monthDays-pauseDays);
                    }
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
                row.add(""+monthDays);
                row.add(""+pauseDays);
                row.add(ut.getDoubleString(money));

                excelData.add(row);
            }catch (Exception e){
                logger.error(" training = "+ JSON.toJSONString(card),e);
            }
        }
        ExcelUtil.writeExcel(excelData,"C://product/monthCard"+System.currentTimeMillis()+".xls");
    }

    private int getPauseDaysByMonth(String memberId, String startDate, String endDate) {
        String start = startDate;
        String end = endDate;
        int pauseDays = 0;
        String sql = " select * from member_pause where member_id = ? and status = 0 and restore_date > ?";
        List data = jdbcTemplate.queryForList(sql,new Object[]{ memberId, start});
        for (int i = 0; i < data.size(); i++) {
            Map pause = (Map)data.get(i);
            String pause_date = pause.get("pause_date").toString();
            if(pause_date.indexOf(":")>0){
                pause_date = pause_date.substring(0,10);
            }
            String restore_date = pause.get("restore_date").toString();
            if(ut.passDayByDate(startDate,pause_date)>0){
                startDate = pause_date;
            }
            if(ut.passDayByDate(endDate,restore_date)<0){
                endDate = restore_date;
            }
            int days = ut.passDayByDate(startDate,endDate);
            pauseDays = pauseDays + days;
        }

        startDate = start;
        endDate = end;

        sql = " select * from member_pause where member_id = ? and status = 1 and pause_date < ?";
        data = jdbcTemplate.queryForList(sql,new Object[]{ memberId, end});
        for (int i = 0; i < data.size(); i++) {
            Map pause = (Map)data.get(i);
            String pause_date = pause.get("pause_date").toString();
            if(pause_date.indexOf(":")>0){
                pause_date = pause_date.substring(0,10);
            }
            String restore_date = pause.get("restore_date").toString();
            if(ut.passDayByDate(startDate,pause_date)>0){
                startDate = pause_date;
            }
            if(ut.passDayByDate(endDate,restore_date)<0){
                endDate = restore_date;
            }
            int days = ut.passDayByDate(startDate,endDate);
            pauseDays = pauseDays + days;
        }
        return pauseDays;
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
        ExcelUtil.writeExcel(excelData,"C://deadlesson_"+endDate.replaceAll("-","")+".xls");

    }

    public void exportStaffMember(String date) {
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("教练姓名");
        titleRow.add("日期");
        titleRow.add("意向会员数");
        titleRow.add("有效会员数");
        titleRow.add("结课会员数");
        titleRow.add("停课会员数");
        titleRow.add("总会员数");
        excelData.add(titleRow);
        String sql = " SELECT * from member_his_10 where backup_date = ? ";
        List<Map<String,Object>> members =  jdbcTemplate.queryForList(sql,new Object[]{date});

        String sql_srtaff = " SELECT  a.* , b.name store_name from staff a, store b where a.store_id = b.store_id  order by a.store_id  ";
        List<Map<String,Object>> staffs =  jdbcTemplate.queryForList(sql_srtaff,new Object[]{});
        for (int i = 0; i < staffs.size(); i++) {
            Map staff = staffs.get(i);
            int count_0 = 0;
            int count_1 = 0;
            int count_2 = 0;
            int count_9 = 0;
            int total = 0;
            for (int j = 0; j < members.size(); j++) {
                Map member = members.get(j);
                if(member.get("coach_staff_id")==null){
                    continue;
                }
                String staffId = member.get("coach_staff_id").toString();
                if(staff.get("staff_id").toString().equals(staffId)){
                    total++;
                    String status = member.get("status").toString();
                    if(status.equals("0")){
                        count_0++;
                    }
                    if(status.equals("1")){
                        count_1++;
                    }
                    if(status.equals("2")){
                        count_2++;
                    }
                    if(status.equals("9")){
                        count_9++;
                    }
                }
            }
            List<String> row = new ArrayList();
            row.add(staff.get("store_name").toString());
            row.add(staff.get("custname").toString());
            row.add(date);
            row.add(""+count_0);
            row.add(""+count_1);
            row.add(""+count_2);
            row.add(""+count_9);
            row.add(""+total);
            excelData.add(row);
        }
        logger.info(" exportDeadLessonMoney     excelData = {} ",excelData.size());
        ExcelUtil.writeExcel(excelData,"C://staff_member_"+date.replaceAll("-","")+".xls");
    }

    public void contractSale(String startDate, String endDate) {
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("合同编号");
        titleRow.add("门店");
        titleRow.add("会员姓名");
        titleRow.add("会员电话");
        titleRow.add("销售人员姓名");
        titleRow.add("课程类型");
        titleRow.add("课程数量");
        titleRow.add("课程金额");
        titleRow.add("签约日期");
        excelData.add(titleRow);
        String sql = " SELECT * from contract where sign_date >= ? and sign_date <= ? and type in ('新会员','续课','转介绍') order by store_id  ";
        List<Map<String,Object>> contractList =  jdbcTemplate.queryForList(sql,new Object[]{startDate,endDate});
        for (int i = 0; i < contractList.size(); i++) {
            Map contract = contractList.get(i);
            String storeId = contract.get("store_id").toString();
            String storeName = "";
            if(StringUtils.isNotEmpty(storeId)){
                StoreEntity storeEntity = storeDao.getById(storeId);
                if(storeEntity!=null){
                    storeName = storeEntity.getName();
                }
            }
            List<String> row = new ArrayList();
            row.add(contract.get("contract_id").toString());
            row.add(storeName);
            row.add(contract.get("member_name").toString());
            row.add(contract.get("phone").toString());
            row.add(contract.get("salesman").toString());
            row.add(contract.get("type").toString());
            row.add(contract.get("total").toString());
            row.add(contract.get("money").toString());
            row.add(contract.get("sign_date").toString());
            excelData.add(row);
        }
        logger.info(" contractSale     excelData = {} ",excelData.size());
        ExcelUtil.writeExcel(excelData,"C://product/contractSale_"+endDate.replaceAll("-","")+".xls");
    }

    /**
     * 死课结课明细表
     */
    public void deadAndEndCard(String startDate, String endDate) {
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("姓名");
        titleRow.add("电话");
        titleRow.add("卡号");
        titleRow.add("类型");
        titleRow.add("教练");
        titleRow.add("购课节数");
        titleRow.add("购课金额");
        titleRow.add("开始日期");
        titleRow.add("结束日期");
        titleRow.add("剩余节数");
        titleRow.add("剩余金额");
        titleRow.add("备注");
        excelData.add(titleRow);
        String sql = " SELECT * from kpi_staff_detail where day >= ? and day <= ? and type in ('JK') order by store_id ";
        List<Map<String,Object>> detailList =  jdbcTemplate.queryForList(sql,new Object[]{startDate,endDate});
        for (int i = 0; i < detailList.size(); i++) {
            Map detail = detailList.get(i);
            String cardNo = detail.get("card_no").toString();
            String memberId = detail.get("member_id").toString();
            String storeId = detail.get("store_id").toString();
            String staffId = detail.get("staff_id").toString();

            String storeName = "";
            if(StringUtils.isNotEmpty(storeId)){
                StoreEntity storeEntity = storeDao.getById(storeId);
                if(storeEntity!=null){
                    storeName = storeEntity.getName();
                }
            }
            MemberEntity memberEntity = memberDao.getById(memberId);
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            StaffEntity staffEntity = staffDao.getById(staffId);
            List<String> row = new ArrayList();
            row.add(storeName);
            row.add(memberEntity.getName());
            row.add(memberEntity.getPhone());
            row.add(cardNo);
            row.add(CardTypeEnum.getEnumByKey(memberCardEntity.getType()).getDesc());
            row.add(staffEntity.getCustname());
            row.add(memberCardEntity.getTotal().toString());
            row.add(memberCardEntity.getMoney());
            row.add(memberCardEntity.getStartDate());
            row.add(memberCardEntity.getEndDate());
            row.add(memberCardEntity.getCount().toString());
            if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())){
                double price =  Double.parseDouble(memberCardEntity.getMoney())/memberCardEntity.getTotal();
                double money = price*memberCardEntity.getCount();
                row.add(ut.getDoubleString(money));
            }else{
                row.add("-");
            }
            row.add(detail.get("remark").toString());
            excelData.add(row);
        }
        logger.info(" contractSale     excelData = {} ",excelData.size());
        ExcelUtil.writeExcel(excelData,"C://product/结课死课报表_"+endDate.replaceAll("-","")+".xls");
    }

    /**
     * 死课结课明细表
     */
    public void deadCard(String startDate, String endDate) {
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("姓名");
        titleRow.add("电话");
        titleRow.add("卡号");
        titleRow.add("类型");
        titleRow.add("教练");
        titleRow.add("购课节数");
        titleRow.add("购课金额");
        titleRow.add("开始日期");
        titleRow.add("结束日期");
        titleRow.add("剩余节数");
        titleRow.add("剩余金额");
        titleRow.add("备注");
        excelData.add(titleRow);
        String sql = " SELECT * from kpi_staff_detail where day >= ? and day <= ? and type in ('SK') order by store_id ";
        List<Map<String,Object>> detailList =  jdbcTemplate.queryForList(sql,new Object[]{startDate,endDate});
        for (int i = 0; i < detailList.size(); i++) {
            Map detail = detailList.get(i);
            String cardNo = detail.get("card_no").toString();
            String memberId = detail.get("member_id").toString();
            String storeId = detail.get("store_id").toString();
            String staffId = detail.get("staff_id").toString();

            String storeName = "";
            if(StringUtils.isNotEmpty(storeId)){
                StoreEntity storeEntity = storeDao.getById(storeId);
                if(storeEntity!=null){
                    storeName = storeEntity.getName();
                }
            }
            MemberEntity memberEntity = memberDao.getById(memberId);
            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
            StaffEntity staffEntity = staffDao.getById(staffId);
            List<String> row = new ArrayList();
            row.add(storeName);
            row.add(memberEntity.getName());
            row.add(memberEntity.getPhone());
            row.add(cardNo);
            row.add(CardTypeEnum.getEnumByKey(memberCardEntity.getType()).getDesc());
            row.add(staffEntity.getCustname());
            row.add(memberCardEntity.getTotal().toString());
            row.add(memberCardEntity.getMoney());
            row.add(memberCardEntity.getStartDate());
            row.add(memberCardEntity.getEndDate());
            row.add(memberCardEntity.getCount().toString());
            if(memberCardEntity.getType().equals(CardTypeEnum.PT.getKey())){
                double price =  Double.parseDouble(memberCardEntity.getMoney())/memberCardEntity.getTotal();
                double money = price*memberCardEntity.getCount();
                row.add(ut.getDoubleString(money));
            }else{
                row.add("-");
            }
            row.add(detail.get("remark").toString());
            excelData.add(row);
        }
        logger.info(" deadCard     excelData = {} ",excelData.size());
        ExcelUtil.writeExcel(excelData,"C://product/死课报表_"+endDate.replaceAll("-","")+".xls");
    }

    public void staffKpi(String month) {
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("教练姓名");
        titleRow.add("KPI模板");
        titleRow.add("KPI得分");
        titleRow.add("KPI额外加减分");
        titleRow.add("KPI最终得分");
        titleRow.add("星级");
        titleRow.add("体能考核");
        titleRow.add("专业考核分数");
        titleRow.add("投诉数");
        titleRow.add("转介绍数");
        titleRow.add("会员点评得分");
        titleRow.add("目标销售额");
        titleRow.add("非营业天数");
        titleRow.add("私教课数");
        titleRow.add("续课数");
        titleRow.add("结课数");
        titleRow.add("附加续课数");
        titleRow.add("附加结课数");
        titleRow.add("星级变动说明");

        excelData.add(titleRow);

        String sql = " SELECT * from kpi_staff_month where month = ? and staff_name <> '全店' order by store_id ";
        List<Map<String,Object>> detailList =  jdbcTemplate.queryForList(sql,new Object[]{month});
        for (int i = 0; i < detailList.size(); i++) {
            Map detail = detailList.get(i);
            String staffName = detail.get("staff_name").toString();
            String templateName = "";
            String staffId = detail.get("staff_id").toString();
            String storeId = detail.get("store_id").toString();

            StaffEntity staffEntity = staffDao.getById(staffId);
            if(staffEntity.getJob().equals("店长")){
                continue;
            }
            KpiTemplateEntity kpiTemplateEntity = kpiTemplateDao.getById(staffEntity.getTemplateId());
            logger.info(" staffKpi template_id = {}  kpiTemplateEntity = {} ",staffEntity.getTemplateId(),kpiTemplateEntity);

            if(kpiTemplateEntity!=null){
                templateName = kpiTemplateEntity.getTitle();
            }
            String storeName = "";
            if (StringUtils.isNotEmpty(storeId)) {
                StoreEntity storeEntity = storeDao.getById(storeId);
                if (storeEntity != null) {
                    storeName = storeEntity.getName();
                }
            }
            double score = 0;
            if(detail.get("kpi_score")!=null&&StringUtils.isNotEmpty(detail.get("kpi_score").toString())){
                score = Double.parseDouble(detail.get("kpi_score").toString());
                if(detail.get("param5")!=null&&StringUtils.isNotEmpty(detail.get("param5").toString())){
                    score = score + Double.parseDouble(detail.get("param5").toString());
                }
            }
            List<String> row = new ArrayList();
            row.add(storeName);
            row.add(staffName);
            row.add(templateName);
            row.add(detail.get("kpi_score").toString());
            row.add(detail.get("param5").toString());
            row.add(ut.getDoubleString(score));
            row.add(detail.get("param1").toString());
            row.add(detail.get("tnkh").toString());
            row.add(detail.get("zykh").toString());
            row.add(detail.get("tss").toString());
            row.add(detail.get("zjs").toString());
            row.add(detail.get("hydp").toString());
            row.add(detail.get("xsmb").toString());
            row.add(detail.get("param2").toString());
            row.add(detail.get("sjks").toString());
            row.add(detail.get("xks").toString());
            row.add(detail.get("jks").toString());
            row.add(detail.get("param3").toString());
            row.add(detail.get("param4").toString());
            row.add(detail.get("param8").toString());

            excelData.add(row);
        }
        logger.info(" staffKpi     excelData = {} ",excelData.size());
        ExcelUtil.writeExcel(excelData,"C://product/教练KPI统计明细_"+month+".xls");
    }

    public void staffMemberDetailByDay(String month) {
        String y = month.substring(0,4);
        String m = month.substring(5,7);
        String tableName = "member_his_"+m;

        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("教练姓名");
        titleRow.add("会员姓名");
        titleRow.add("会员电话");
        titleRow.add("状态");
        titleRow.add("日期");
        excelData.add(titleRow);
        String sql = " SELECT d.name store_name,b.custname,c.name member_name, c.phone , a.status , a.backup_date from "+tableName+" a, staff b , member c , store d " +
                " where a.coach_staff_id > 0 and a.status = 1 " +
                " and a.store_id = d.store_id and a.coach_staff_id = b.staff_id and a.member_id = c.member_id " +
                " order by a.backup_date , a.coach_staff_id  ";
        List<Map<String,Object>> detailList =  jdbcTemplate.queryForList(sql,new Object[]{});
        logger.info(" staffMemberDetailByDay     detailList = {} ",detailList.size());

        for (int i = 0; i < detailList.size(); i++) {
            Map detail = detailList.get(i);
            List<String> row = new ArrayList();
            row.add(detail.get("store_name").toString());
            row.add(detail.get("custname").toString());
            row.add(detail.get("member_name").toString());
            row.add(detail.get("phone").toString());
            row.add("有效");
            row.add(detail.get("backup_date").toString());
            excelData.add(row);
        }

        logger.info(" staffMemberDetailByDay     excelData = {} ",excelData.size());
        ExcelUtil.writeExcel(excelData,"C://product/有效会员明细表_"+month+".xls");
    }

    public void managerKpi(String month) {
        List<List<String>> excelData = new ArrayList<>();
        List<String> titleRow = new ArrayList();
        titleRow.add("门店");
        titleRow.add("教练姓名");
        titleRow.add("KPI模板");
        titleRow.add("KPI得分");
        titleRow.add("KPI额外加减分");
        titleRow.add("KPI最终得分");
        titleRow.add("星级");
        titleRow.add("体能考核");
        titleRow.add("专业考核分数");
        titleRow.add("投诉数");
        titleRow.add("转介绍数");
        titleRow.add("会员点评得分");
        titleRow.add("非营业天数");
        titleRow.add("私教课数");
        titleRow.add("续课数");
        titleRow.add("结课数");
        titleRow.add("附加续课数");
        titleRow.add("附加结课数");

        titleRow.add("目标销售额");
        titleRow.add("个人销售额");
        titleRow.add("全店销售额");
        titleRow.add("全店销售完成率");
        titleRow.add("全店续课数");
        titleRow.add("全店结课数");
        titleRow.add("全店续课率");
        titleRow.add("全店季度转介绍数");
        titleRow.add("全店私教课数");
        titleRow.add("全店有效会员数");
        titleRow.add("全店活跃度");
        titleRow.add("全店钉钉成交数");
        titleRow.add("全店体侧数");
        titleRow.add("全店体侧转化率");


        excelData.add(titleRow);

        String sql = " SELECT * from kpi_staff_month where month = ? and staff_name <> '全店' order by store_id ";
        List<Map<String,Object>> detailList =  jdbcTemplate.queryForList(sql,new Object[]{month});
        for (int i = 0; i < detailList.size(); i++) {
            Map detail = detailList.get(i);
            String staffName = detail.get("staff_name").toString();
            String templateName = "";
            String staffId = detail.get("staff_id").toString();
            String storeId = detail.get("store_id").toString();

            StaffEntity staffEntity = staffDao.getById(staffId);
            if(!staffEntity.getJob().equals("店长")){
                continue;
            }

            KpiTemplateEntity kpiTemplateEntity = kpiTemplateDao.getById(staffEntity.getTemplateId());
            logger.info(" staffKpi template_id = {}  kpiTemplateEntity = {} ",staffEntity.getTemplateId(),kpiTemplateEntity);

            if(kpiTemplateEntity!=null){
                templateName = kpiTemplateEntity.getTitle();
            }
            String storeName = "";
            if (StringUtils.isNotEmpty(storeId)) {
                StoreEntity storeEntity = storeDao.getById(storeId);
                if (storeEntity != null) {
                    storeName = storeEntity.getName();
                }
            }
            double score = 0;
            if(detail.get("kpi_score")!=null&&StringUtils.isNotEmpty(detail.get("kpi_score").toString())){
                score = Double.parseDouble(detail.get("kpi_score").toString());
                if(detail.get("param5")!=null&&StringUtils.isNotEmpty(detail.get("param5").toString())){
                    score = score + Double.parseDouble(detail.get("param5").toString());
                }
            }
            List<String> row = new ArrayList();
            row.add(storeName);
            row.add(staffName);
            row.add(templateName);
            row.add(detail.get("kpi_score").toString());
            row.add(detail.get("param5").toString());
            row.add(ut.getDoubleString(score));
            row.add(detail.get("param1").toString());
            row.add(detail.get("tnkh").toString());
            row.add(detail.get("zykh").toString());
            row.add(detail.get("tss").toString());
            row.add(detail.get("zjs").toString());
            row.add(detail.get("hydp").toString());
            row.add(detail.get("param2").toString());
            row.add(detail.get("sjks").toString());
            row.add(detail.get("xks").toString());
            row.add(detail.get("jks").toString());
            row.add(detail.get("param3").toString());
            row.add(detail.get("param4").toString());

            row.add(detail.get("xsmb").toString());
            row.add(detail.get("zye").toString());
            row.add(detail.get("qdzye").toString());
            row.add(detail.get("xswcl").toString());

            row.add(detail.get("qdxks").toString());
            row.add(detail.get("qdjks").toString());
            row.add(detail.get("qdxkl").toString());
            row.add(detail.get("qdzjs").toString());
            row.add(detail.get("qdsjks").toString());
            row.add(detail.get("qdyxhys").toString());
            row.add(detail.get("qdhyd").toString());
            row.add(detail.get("qdcjs").toString());
            row.add(detail.get("qdtcs").toString());
            row.add(detail.get("tczhl").toString());

            excelData.add(row);
        }
        logger.info(" staffKpi     excelData = {} ",excelData.size());
        ExcelUtil.writeExcel(excelData,"C://product/店长KPI统计明细_"+month+".xls");
    }
}

