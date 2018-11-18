package com.training.admin.service;

import com.training.common.CardTypeEnum;
import com.training.dao.ContractDao;
import com.training.dao.KpiStaffDetailDao;
import com.training.dao.MemberDao;
import com.training.dao.StaffDao;
import com.training.entity.KpiStaffDetailEntity;
import com.training.entity.MemberEntity;
import com.training.service.MemberCardService;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
    private MemberCardService memberCardService;

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
        List cards = jdbcTemplate.queryForList(sql ,new Object[]{endDate,startDate,endDate+" 23:59:59"});
        for (int i = 0; i < cards.size(); i++) {
            Map memberCard = (Map)cards.get(i);
            try {
                String memberId = memberCard.get("member_id").toString();
                String cardNo = memberCard.get("card_no").toString();
                String type = memberCard.get("type").toString();
                Integer count = Integer.parseInt(memberCard.get("count").toString());
                String startDateCard = memberCard.get("start_date").toString();
                String endDateCard = memberCard.get("end_date").toString();
                boolean isJk = false;
                if(type.equals(CardTypeEnum.PM.getKey())){
                    if(ut.passDayByDate(endDateCard,endDate)>0){
                        isJk = true;
                    }
                }
                if(type.equals(CardTypeEnum.PT.getKey())){
                    if(count>0){
                        continue;
                    }
                    List trainings = jdbcTemplate.queryForList("select * from training where card_no = ? and lesson_date > ? " ,new Object[]{cardNo,endDate});
                    if(trainings.size()==0){
                        isJk = true;
                    }
                }
                if(isJk){
                    MemberEntity memberEntity = memberDao.getById(memberId);
                    KpiStaffDetailEntity kpiStaffDetailEntity = new KpiStaffDetailEntity();
                    kpiStaffDetailEntity.setMonth(month);
                    kpiStaffDetailEntity.setCardNo(cardNo);
                    kpiStaffDetailEntity.setContractId("");
                    kpiStaffDetailEntity.setType("JK");
                    kpiStaffDetailEntity.setCardType(type);
                    kpiStaffDetailEntity.setMemberId(memberId);
                    kpiStaffDetailEntity.setStoreId(memberEntity.getStoreId());
                    kpiStaffDetailEntity.setStaffId(memberEntity.getCoachStaffId());
                    int n = kpiStaffDetailDao.add(kpiStaffDetailEntity);
                    if(n>0){
                        jks++;
                    }
                }
            }catch (Exception e){
                logger.error("  dealJkERROR : memberCard = {} ",memberCard);
            }
        }
        logger.info(" =======   KpiStaffDetailService  dealJk  startDate = {} , endDate = {} ",startDate,endDate);
        logger.info(" dealJk  ,jks = {} , month = {} , cards.size = {} ",jks,month,cards.size());
        return jks;
    }


}
