package com.training.admin.api;

import com.training.domain.Member;
import com.training.entity.ContractEntity;
import com.training.entity.StaffEntity;
import com.training.entity.StoreEntity;
import com.training.service.ContractService;
import com.training.service.MemberService;
import com.training.service.StaffService;
import com.training.service.StoreService;
import com.training.util.DingtalkUtil;
import com.training.util.IDUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户 API控制器
 */
@RestController
@RequestMapping("/dingding")
public class DingDingRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreService storeService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ContractService contractService;

    @GetMapping("dept")
    public Object dept(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" DingDingRestController   dept  ");
        List<Map> deptList = DingtalkUtil.getDepts();
        for (int i = 0; i < deptList.size(); i++) {
            Map item = deptList.get(i);
            System.out.println("id: " + item.get("id").toString()+" , name: " + item.get("name").toString());
            StoreEntity store = new StoreEntity();
            store.setStoreId(item.get("id").toString());
            store.setDeptId(item.get("id").toString());
            store.setName(item.get("name").toString());
            store.setUserId("");
            storeService.add(store);
        }
        return "执行成功";
    }

    @GetMapping("staff")
    public Object staff(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" DingDingRestController   staff  ");
        List<Map> deptList = DingtalkUtil.getDepts();
        for (int i = 0; i < deptList.size(); i++) {
            Map dept = deptList.get(i);
            System.out.println("id: " + dept.get("id").toString()+" , name: " + dept.get("name").toString());

            String deptId = dept.get("id").toString();
            List<Map> staffList = DingtalkUtil.getStaffs(dept.get("id").toString());
            for (int j = 0; j < staffList.size(); j++) {
                Map item = staffList.get(j);
                System.out.println("userid: " + item.get("userid").toString());
                System.out.println("name: " + item.get("name").toString());

                String userid = item.get("userid").toString();
                StaffEntity staffDB = staffService.getByUsername(userid);
                if(staffDB!=null){
                    logger.info(item.get("name").toString()+"已存在，无需重复添加");
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
                staffEntity.setUsername(item.get("userid").toString());
                staffEntity.setCustname(item.get("name").toString());
                staffEntity.setPhone(mobile);
                staffEntity.setEmail(email);
                staffEntity.setFeature(extattr);
                staffEntity.setJob(position);
                staffService.add(staffEntity);
            }
        }
        return "执行成功";
    }

    @GetMapping("member")
    public Object member(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" DingDingRestController   member  ");
        String processCode = "PROC-EF6Y0XWVO2-RG59KJS2S58XL1O495ZN2-QABKW8MI-19"; // 新签合同（次卡私教）
//        String processCode = "PROC-FF6YQLE1N2-HESQB9ZYOW7FOIN97KIL1-84X7L0BJ-E"; // 新签合同（月卡私教）
        List<ContractEntity> contractEntityList = null;
        Long cursor = 1L;
        do{
            contractEntityList = DingtalkUtil.getContracts(processCode,cursor);
            for (int i = 0; i < contractEntityList.size(); i++) {
                ContractEntity contractEntity = contractEntityList.get(i);
                ContractEntity contractEntityDB = contractService.getById(contractEntity.getContractId());
                if(contractEntityDB!=null){
                    logger.info(contractEntityDB.getContractName().toString()+"已存在，无需重复添加");
                    continue;
                }
                contractService.add(contractEntity);
            }
            cursor++;
        }while(CollectionUtils.isNotEmpty(contractEntityList));
        return "执行成功 : cursor = "+cursor;
    }

}
