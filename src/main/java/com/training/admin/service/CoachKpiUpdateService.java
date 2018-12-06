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
    private CoachStaffKpiService coachStaffKpiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void execute() {
        logger.info(" =======   CoachKpiUpdateService  execute start  ");
        String month = ut.currentFullMonth().replace("-","");
        List<Map<String,Object>> coachs =  jdbcTemplate.queryForList(" SELECT staff_id from staff where job in ('教练','店长') and status >= 0 ");
        for (int i = 0; i < coachs.size(); i++){
            Map staff = coachs.get(i);
            String staffId = staff.get("staff_id").toString();
            coachStaffKpiService.calculateStaffKpi(staffId,month);
        }
        List<Map<String,Object>> stores =  jdbcTemplate.queryForList(" SELECT store_id from store where store_id not in ('0') ");
        for (int i = 0; i < stores.size(); i++){
            Map store = stores.get(i);
            String store_id = store.get("store_id").toString();
            coachStaffKpiService.calculateStoreKpi(store_id,month);
        }
        logger.info(" =======   CoachKpiUpdateService  execute end  ");
    }

}
