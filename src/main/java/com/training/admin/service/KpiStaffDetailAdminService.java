package com.training.admin.service;

import com.alibaba.fastjson.JSON;
import com.training.common.CardTypeEnum;
import com.training.dao.*;
import com.training.entity.KpiStaffDetailEntity;
import com.training.entity.MemberCardEntity;
import com.training.entity.MemberEntity;
import com.training.entity.StaffEntity;
import com.training.service.MemberCardService;
import com.training.util.ExcelUtil;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KpiStaffDetailAdminService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiStaffDetailDao kpiStaffDetailDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private CalculateKpiService calculateKpiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int dealXk(String month) {
        logger.info(" =======   KpiStaffDetailService  dealXk  month = {} ",month);
        String sql = "select * from contract where card_type in ('PT','PM') and sign_date >= ? and sign_date <= ? ";
        int xks = 0;
        int xqs = 0;
        int zjs = 0;

        String startDate = month+"-01";
        String endDate = month+"-31";
        List data = jdbcTemplate.queryForList(sql,new Object[]{startDate,endDate});
        logger.info(" dealXk   startDate = {} , endDate = {} , data.size = {} ",startDate,endDate,data.size());
        for (int i = 0; i < data.size(); i++) {
            Map contract = (Map)data.get(i);
            try {
                String contractId = contract.get("contract_id").toString();
                String type = contract.get("type").toString();
                if(type.indexOf("续课")>=0){
                    logger.info(" getXks"+xks+"  contract = {} ",contract);
                    if(contract.get("card_no")!=null && contract.get("sale_staff_id")!=null && contract.get("member_id")!=null && contract.get("store_id")!=null){
                        MemberEntity memberEntity = memberDao.getById(contract.get("member_id").toString());
                        String cardNo = contract.get("card_no").toString();
                        KpiStaffDetailEntity kpiStaffDetailEntity = new KpiStaffDetailEntity();
                        kpiStaffDetailEntity.setMonth(month);
                        kpiStaffDetailEntity.setCardNo(cardNo);
                        kpiStaffDetailEntity.setContractId(contractId);
                        kpiStaffDetailEntity.setType("XK");
                        kpiStaffDetailEntity.setCardType(contract.get("card_type").toString());
                        kpiStaffDetailEntity.setMemberId(contract.get("member_id").toString());
                        kpiStaffDetailEntity.setStoreId(contract.get("store_id").toString());
                        kpiStaffDetailEntity.setStaffId(contract.get("sale_staff_id").toString());
                        int n = kpiStaffDetailDao.add(kpiStaffDetailEntity);
                        if(n>0){
                            xks++;
                            MemberCardEntity memberCardEntity = memberCardDao.getById(cardNo);
                            if(memberCardEntity==null){
                                continue;
                            }
                            String signDate = contract.get("sign_date").toString();
                            String sql_card = "select * from member_card where member_id = ? and card_no <> ? and type = ? and end_date >= ? ";
                            List cards = jdbcTemplate.queryForList(sql_card,new Object[]{memberCardEntity.getMemberId(),cardNo,memberCardEntity.getType(),signDate});
                            for (int j = 0; j < cards.size(); j++) {
                                Map card = (Map)cards.get(j);
                                try {
                                    KpiStaffDetailEntity kpiStaffDetailEntityJk = new KpiStaffDetailEntity();
                                    kpiStaffDetailEntityJk.setMonth(month);
                                    kpiStaffDetailEntityJk.setCardNo(card.get("card_no").toString());
                                    kpiStaffDetailEntityJk.setContractId("");
                                    kpiStaffDetailEntityJk.setType("JK");
                                    kpiStaffDetailEntityJk.setCardType(card.get("type").toString());
                                    kpiStaffDetailEntityJk.setMemberId(memberEntity.getMemberId());
                                    kpiStaffDetailEntityJk.setStoreId(memberEntity.getStoreId());
                                    kpiStaffDetailEntityJk.setStaffId(memberEntity.getCoachStaffId());
                                    kpiStaffDetailEntityJk.setRemark("预结课续课合同："+contractId);
                                    kpiStaffDetailDao.add(kpiStaffDetailEntityJk);
                                }catch (Exception e){

                                }
                            }
                        }
                    }
                }

                if(type.indexOf("新会员")>=0){
                    if(contract.get("card_no")!=null && contract.get("sale_staff_id")!=null && contract.get("member_id")!=null && contract.get("store_id")!=null){
                        logger.info(" xqs = "+xqs+"  contract = {} ",contract);
                        String cardNo = contract.get("card_no").toString();
                        KpiStaffDetailEntity kpiStaffDetailEntity = new KpiStaffDetailEntity();
                        kpiStaffDetailEntity.setMonth(month);
                        kpiStaffDetailEntity.setCardNo(cardNo);
                        kpiStaffDetailEntity.setContractId(contractId);
                        kpiStaffDetailEntity.setType("XQ");
                        kpiStaffDetailEntity.setCardType(contract.get("card_type").toString());
                        kpiStaffDetailEntity.setMemberId(contract.get("member_id").toString());
                        kpiStaffDetailEntity.setStoreId(contract.get("store_id").toString());
                        kpiStaffDetailEntity.setStaffId(contract.get("sale_staff_id").toString());
                        int n = kpiStaffDetailDao.add(kpiStaffDetailEntity);
                        if(n>0){
                            xqs++;
                        }
                    }
                }

                if(type.indexOf("转介绍")>=0){
                    logger.info(" zjs = "+zjs+"  contract = {} ",contract);
                    if(contract.get("card_no")!=null && contract.get("sale_staff_id")!=null && contract.get("member_id")!=null && contract.get("store_id")!=null){
                        String cardNo = contract.get("card_no").toString();
                        KpiStaffDetailEntity kpiStaffDetailEntity = new KpiStaffDetailEntity();
                        kpiStaffDetailEntity.setMonth(month);
                        kpiStaffDetailEntity.setCardNo(cardNo);
                        kpiStaffDetailEntity.setContractId(contractId);
                        kpiStaffDetailEntity.setType("ZJS");
                        kpiStaffDetailEntity.setCardType(contract.get("card_type").toString());
                        kpiStaffDetailEntity.setMemberId(contract.get("member_id").toString());
                        kpiStaffDetailEntity.setStoreId(contract.get("store_id").toString());
                        kpiStaffDetailEntity.setStaffId(contract.get("sale_staff_id").toString());
                        int n = kpiStaffDetailDao.add(kpiStaffDetailEntity);
                        if(n>0){
                            zjs++;
                        }
                    }
                }

            }catch (Exception e){
                logger.error("  dealXkERROR : message = {} , contract = {} ",e.getMessage(),contract,e);
            }
        }
        logger.info(" getXks = {} ,xqs = {} , zjs = {} , month = {} , data.size = {} ",xks,xqs,zjs,month,data.size());
        return xks;
    }

    public int dealJk(String month) {
        logger.info(" =======   KpiStaffDetailService  dealJk  month = {} ",month);
        String startDate = month+"-01";
        String endDate = month+"-31";
        if(ut.currentDate().indexOf(month)==0){
            if(ut.passDayByDate(ut.currentDate(),endDate)>0){
                endDate = ut.currentDate();
            }
        }
        int jks = 0;
        String sql = "select * from member_card where type in ('PT','PM') and start_date <= ?  and end_date >= ? and created <= ? ";
        List cards = jdbcTemplate.queryForList(sql ,new Object[]{endDate,ut.currentDate(startDate,-35),endDate+" 23:59:59"});
        for (int i = 0; i < cards.size(); i++) {
            Map memberCard = (Map)cards.get(i);
            try {
                String memberId = memberCard.get("member_id").toString();
                String cardNo = memberCard.get("card_no").toString();
                String type = memberCard.get("type").toString();
                Integer count = Integer.parseInt(memberCard.get("count").toString());
                String startDateCard = memberCard.get("start_date").toString();
                String endDateCard = memberCard.get("end_date").toString();
                MemberEntity memberEntity = memberDao.getById(memberId);
                String staffId = memberEntity.getCoachStaffId();
                String remark = "";
                boolean isJk = false;
                boolean isSk = false;
                if(type.equals(CardTypeEnum.PM.getKey())){
                    if(ut.passDayByDate(endDateCard,endDate)>0){
                        isJk = true;
                        isSk = true;
                        remark = "死课";
                    }
                }
                if(type.equals(CardTypeEnum.PT.getKey())){
                    int passDays = ut.passDayByDate(endDateCard,endDate);
                    if(count>0){
                        if(passDays<=30){
                            continue;
                        }
                        isJk = true;
                        isSk = true;
                        remark = "死课";
                    }else{
                        List trainings = jdbcTemplate.queryForList("select * from training where card_no = ? order by lesson_date desc " ,new Object[]{cardNo});
                        if(trainings.size()==0){
                            isJk = true;
                            remark = "结课";
                        }else{
                            Map training = (Map)trainings.get(0);
                            String lessonDate = training.get("lesson_date").toString();
                            if(ut.passDayByDate(endDate,lessonDate)>0){
                                continue;
                            }else{
                                isJk = true;
                                staffId = training.get("staff_id").toString();
                                remark = "结课";
                            }
                        }
                    }
                }
                if(isJk){
                    KpiStaffDetailEntity kpiStaffDetailEntity = new KpiStaffDetailEntity();
                    kpiStaffDetailEntity.setMonth(month);
                    kpiStaffDetailEntity.setCardNo(cardNo);
                    kpiStaffDetailEntity.setContractId("");
                    kpiStaffDetailEntity.setType("JK");
                    kpiStaffDetailEntity.setCardType(type);
                    kpiStaffDetailEntity.setMemberId(memberId);
                    kpiStaffDetailEntity.setStoreId(memberEntity.getStoreId());
                    kpiStaffDetailEntity.setStaffId(staffId);
                    kpiStaffDetailEntity.setRemark(remark);
                    int n = kpiStaffDetailDao.add(kpiStaffDetailEntity);
                    if(n>0){
                        jks++;
                    }
                }
                if(isSk){
                    KpiStaffDetailEntity kpiStaffDetailEntity = new KpiStaffDetailEntity();
                    kpiStaffDetailEntity.setMonth(month);
                    kpiStaffDetailEntity.setCardNo(cardNo);
                    kpiStaffDetailEntity.setContractId("");
                    kpiStaffDetailEntity.setType("SK");
                    kpiStaffDetailEntity.setCardType(type);
                    kpiStaffDetailEntity.setMemberId(memberId);
                    kpiStaffDetailEntity.setStoreId(memberEntity.getStoreId());
                    kpiStaffDetailEntity.setStaffId(staffId);
                    kpiStaffDetailEntity.setRemark(JSON.toJSONString(memberCard));
                    int n = kpiStaffDetailDao.add(kpiStaffDetailEntity);
                }

            }catch (Exception e){
                logger.error("  dealJkERROR : memberCard = {} ",memberCard);
            }
        }
        logger.info(" =======   KpiStaffDetailService  dealJk  startDate = {} , endDate = {} ",startDate,endDate);
        logger.info(" dealJk  ,jks = {} , month = {} , cards.size = {} ",jks,month,cards.size());
        return jks;
    }


    public String updateKpiAndStar() {
        List<List<String>> data =ExcelUtil.readExcel(new File("c://kpi-4.xls"));
        List error = new ArrayList();
        String sql = "update kpi_staff_month set param1 = ? , kpi_score = ? where staff_id = ? and month = ? ";
        int i = 0;
        for (List<String> row:data){
            if(i==0){
                i++;
                continue;
            }
//            logger.info(" row = {} ",row);
            String phone = row.get(2);

            String star6 = row.get(5);
            String kpi6 = row.get(6);

            String star7 = row.get(7);
            String kpi7 = row.get(8);

            String star8 = row.get(9);
            String kpi8 = row.get(10);

            String star9 = row.get(11);
            String kpi9 = row.get(12);

            String star10 = row.get(13);
            String kpi10 = row.get(14);

            StaffEntity staffEntity = staffDao.getByPhone(phone);
            if(staffEntity==null){
                error.add(phone);
            }
            logger.info(" phone = {} , staffEntity = {} ",phone,staffEntity);
            logger.info(" month - 201806 : star = {} , kpi = {} ",star6,kpi6);
            try{
                Integer star = Integer.parseInt(star6);
                Double kpi = Double.parseDouble(kpi6);
                jdbcTemplate.update(sql,new Object[]{star6,kpi6,staffEntity.getStaffId(),"201806"});
                jdbcTemplate.update(" update staff set star = ? where staff_id = ?  ",new Object[]{star6,staffEntity.getStaffId()});

            }catch (Exception e){

            }
            logger.info(" month - 201807 : star = {} , kpi = {} ",star7,kpi7);
            try{
                Integer star = Integer.parseInt(star7);
                Double kpi = Double.parseDouble(kpi7);
                jdbcTemplate.update(sql,new Object[]{star7,kpi7,staffEntity.getStaffId(),"201807"});
                jdbcTemplate.update(" update staff set star = ? where staff_id = ?  ",new Object[]{star7,staffEntity.getStaffId()});

            }catch (Exception e){

            }
            logger.info(" month - 201808 : star = {} , kpi = {} ",star8,kpi8);
            try{
                Integer star = Integer.parseInt(star8);
                Double kpi = Double.parseDouble(kpi8);
                jdbcTemplate.update(sql,new Object[]{star8,kpi8,staffEntity.getStaffId(),"201808"});
                jdbcTemplate.update(" update staff set star = ? where staff_id = ?  ",new Object[]{star8,staffEntity.getStaffId()});

            }catch (Exception e){

            }
            logger.info(" month - 201809 : star = {} , kpi = {} ",star9,kpi9);
            try{
                Integer star = Integer.parseInt(star9);
                Double kpi = Double.parseDouble(kpi9);
                jdbcTemplate.update(sql,new Object[]{star9,kpi9,staffEntity.getStaffId(),"201809"});
                jdbcTemplate.update(" update staff set star = ? where staff_id = ?  ",new Object[]{star9,staffEntity.getStaffId()});
            }catch (Exception e){

            }
            logger.info(" month - 201810 : star = {} , kpi = {} ",star10,kpi10);
            try{
                Integer star = Integer.parseInt(star10);
                Double kpi = Double.parseDouble(kpi10);
                jdbcTemplate.update(sql,new Object[]{star10,kpi10,staffEntity.getStaffId(),"201810"});
                jdbcTemplate.update(" update staff set star = ? where staff_id = ?  ",new Object[]{star10,staffEntity.getStaffId()});

            }catch (Exception e){

            }
            i++;
        }
        logger.info(" data = {} ",data.size());
        logger.info(" error = {} ",error.size());
        return "SUCCESS";
    }

}
