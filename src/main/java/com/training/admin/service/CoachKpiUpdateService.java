package com.training.admin.service;

import com.training.dao.ContractDao;
import com.training.dao.MemberDao;
import com.training.dao.StaffDao;
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
public class CoachKpiUpdateService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    public void execute() {
        logger.info(" =======   CoachKpiUpdateService  execute start  ");
        String month = ut.currentFullMonth().replace("-","");
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
        logger.info(" =======   CoachKpiUpdateService  execute end  ");
    }

}
