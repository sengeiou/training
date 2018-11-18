package com.training.admin.service;

import com.training.dao.MemberCardDao;
import com.training.dao.MemberDao;
import com.training.dao.StaffDao;
import com.training.dao.StoreDao;
import com.training.entity.MemberCardEntity;
import com.training.entity.MemberEntity;
import com.training.entity.StaffEntity;
import com.training.service.MemberService;
import com.training.service.SysLogService;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class ContractAdminService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCardDao memberCardDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String updateContractInfo() {
        String sql = "select * from contract where card_type in ('PT','PM','TT','ST1','ST2','ST3','ST4','ST5','ST6') ";
        List<Map<String,Object>> data =  jdbcTemplate.queryForList(sql);
        for (int i = 0; i < data.size(); i++){
            logger.info(" updateContractInfo  i = {} ",i);
            Map contract = data.get(i);
            String pk_id = contract.get("pk_id").toString();
            String contractId = contract.get("contract_id").toString();
            String phone = contract.get("phone").toString();
            String salesman = contract.get("salesman").toString();
            String coach = contract.get("coach").toString();

            salesman = salesman.replaceAll("（","(").replaceAll("）",")");
            if(salesman.indexOf("(")>0){
                salesman = salesman.substring(0,salesman.indexOf("("));
            }

            coach = coach.replaceAll("（","(").replaceAll("）",")");
            if(coach.indexOf("(")>0){
                coach = coach.substring(0,coach.indexOf("("));
            }

            if(contract.get("member_id")==null || StringUtils.isEmpty(contract.get("member_id").toString())
                    || contract.get("store_id")==null || StringUtils.isEmpty(contract.get("store_id").toString())){
                MemberEntity memberEntity = memberDao.getByPhone(phone);
                if(memberEntity!=null){
                    jdbcTemplate.update(" update contract set member_id = ? , store_id = ? where pk_id = ? ",new Object[]{memberEntity.getMemberId(),memberEntity.getStoreId(),pk_id});
                    contract.put("member_id",memberEntity.getMemberId());
                }
            }

            if(contract.get("sale_staff_id")==null || StringUtils.isEmpty(contract.get("sale_staff_id").toString())){
                StaffEntity saleStaff = staffDao.getByCustname(salesman);
                if(saleStaff!=null){
                    jdbcTemplate.update(" update contract set sale_staff_id = ?  where pk_id = ? ",new Object[]{saleStaff.getStaffId(),pk_id});
                }
            }

            if(contract.get("coach_staff_id")==null || StringUtils.isEmpty(contract.get("coach_staff_id").toString())){
                StaffEntity coachStaff = staffDao.getByCustname(coach);
                if(coachStaff!=null){
                    jdbcTemplate.update(" update contract set coach_staff_id = ?  where pk_id = ? ",new Object[]{coachStaff.getStaffId(),pk_id});
                }
            }

            if(contract.get("card_no")==null || StringUtils.isEmpty(contract.get("card_no").toString())){
                MemberCardEntity memberCardEntity = memberCardDao.getByContractId(contractId);
                if(memberCardEntity!=null){
                    jdbcTemplate.update(" update contract set card_no = ?  where pk_id = ? ",new Object[]{memberCardEntity.getCardNo(),pk_id});
                } else{
                    if(contract.get("member_id")!=null){
                        String memberId = contract.get("member_id").toString();
                        List<Map<String,Object>>  cards = jdbcTemplate.queryForList("select card_no,start_date,end_date,DATE_FORMAT(created,'%Y-%m-%d') created from member_card where member_id = ? and contract_id = '' ",new Object[]{memberId});
                        for (int j = 0; j < cards.size(); j++) {
                            Map card = cards.get(j);
                            String cardNo = card.get("card_no").toString();
                            String startDate = card.get("start_date").toString();
                            String endDate = card.get("end_date").toString();
                            String created = card.get("created").toString();
                            if(startDate.equals(contract.get("start_date")) && endDate.equals(contract.get("end_date"))){
                                jdbcTemplate.update(" update contract set card_no = ?  where pk_id = ? ",new Object[]{cardNo,pk_id});
                                jdbcTemplate.update(" update member_card set contract_id = ?  where card_no = ? ",new Object[]{contractId,cardNo});
                            }else if(startDate.equals(contract.get("start_date")) && created.equals(contract.get("sign_date"))){
                                jdbcTemplate.update(" update contract set card_no = ?  where pk_id = ? ",new Object[]{cardNo,pk_id});
                                jdbcTemplate.update(" update member_card set contract_id = ?  where card_no = ? ",new Object[]{contractId,cardNo});
                            }
                        }
                    }
                }
            }
            jdbcTemplate.update(" update contract set deal_flag = 1  where pk_id = ? ",new Object[]{pk_id});
        }
        return "";
    }


}

