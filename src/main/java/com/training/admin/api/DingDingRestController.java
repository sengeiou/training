package com.training.admin.api;

import com.alibaba.fastjson.JSON;
import com.training.common.CardTypeEnum;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.ContractManualDao;
import com.training.domain.Member;
import com.training.entity.*;
import com.training.service.*;
import com.training.util.DingtalkUtil;
import com.training.util.ExcelUtil;
import com.training.util.IDUtils;
import com.training.util.ut;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
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
    private JdbcTemplate jdbcTemplate;

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
        List<Map<String,Object>> deptList =  jdbcTemplate.queryForList("select * from store ");
        for (int i = 0; i < deptList.size(); i++) {
            Map dept = deptList.get(i);
            System.out.println("dept_id: " + dept.get("dept_id").toString()+" , name: " + dept.get("name").toString());

            String deptId = dept.get("dept_id").toString();
            List<Map> staffList = DingtalkUtil.getStaffs(dept.get("dept_id").toString());
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
//        String processCode = "PROC-EF6Y0XWVO2-RG59KJS2S58XL1O495ZN2-QABKW8MI-19"; // 新签合同（次卡私教）
        String processCode = "PROC-FF6YQLE1N2-HESQB9ZYOW7FOIN97KIL1-84X7L0BJ-E"; // 新签合同（月卡私教）
        List<ContractEntity> contractEntityList = null;
        Long cursor = 1L;
        Long start = ut.df_day.parse("2010-01-01").getTime();
        do{
            contractEntityList = DingtalkUtil.getContracts(start,System.currentTimeMillis(),processCode,cursor);
            for (int i = 0; i < contractEntityList.size(); i++) {
                ContractEntity contractEntity = contractEntityList.get(i);
                ContractEntity contractEntityDB = contractService.getById(contractEntity.getProcessInstanceId());
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

    @GetMapping("member2")
    public Object member2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" DingDingRestController   member2  ");

        ContractQuery query = new ContractQuery();
//        PageRequest page = new
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(50);
        Page<ContractEntity> page = contractService.find(query,pageRequest);
        for (ContractEntity contractEntity : page.getContent()){
            MemberEntity memberEntityDB = memberService.getByPhone(contractEntity.getPhone());
            if(memberEntityDB!=null){
                logger.info(" ============   "+contractEntity.getPhone() +" 已存在 ");
                continue;
            }
            MemberEntity member = new MemberEntity();
            member.setMemberId(IDUtils.getId());
            member.setType("M");
            member.setName(contractEntity.getMemberName());
            member.setPhone(contractEntity.getPhone());
            member.setSalesman(contractEntity.getSalesman());
            memberService.add(member);
        }
        return "member2执行成功";
    }


    @GetMapping("contract_manual")
    public Object contract_manual(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" DingDingRestController   contract_manual  ");

        File file = new File("d:/file/honglian2.xls");
        List<List<String>> data = ExcelUtil.readExcel(file);
        int i = 0;
        for (List<String> item : data){
            System.out.println(JSON.toJSONString(item));
            if(i==0){
                i++;
                continue;
            }
            ContractManualEntity contractManualEntity = new ContractManualEntity();
            contractManualEntity.setContractId(item.get(0));
            if(StringUtils.isEmpty(contractManualEntity.getContractId())){
                contractManualEntity.setContractId(IDUtils.getId());
            }
            contractManualEntity.setCardNo(item.get(1));

            contractManualEntity.setMemberName(item.get(3));
            contractManualEntity.setPhone(item.get(4));
            contractManualEntity.setCardType(item.get(5));
            contractManualEntity.setStoreName(item.get(6));
            contractManualEntity.setSalesman(item.get(7));
            contractManualEntity.setSalesmanPhone(item.get(8));
            contractManualEntity.setCoach(item.get(9));
            contractManualEntity.setCoachPhone(item.get(10));
            contractManualEntity.setTotal(item.get(11));
            contractManualEntity.setMoney(item.get(12));
            contractManualEntity.setType(item.get(13));
            contractManualEntity.setPayType(item.get(14));
            contractManualEntity.setPrice(item.get(15));
            contractManualEntity.setDealFlag(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String date1 = sdf.format(HSSFDateUtil.getJavaDate(Double.parseDouble(item.get(15))));
//            String date2 = sdf.format(HSSFDateUtil.getJavaDate(Double.parseDouble(item.get(16))));
            String date1 = item.get(16);
            String date2 = item.get(17);
            contractManualEntity.setStartDate(date1);
            contractManualEntity.setEndDate(date2);
            contractManualEntity.setRealFee(item.get(18));
            contractManualEntity.setCount(item.get(19));
            contractManualEntity.setStatus(item.get(20));
            contractManualEntity.setPauseDate(item.size()>21?item.get(21):"");
            contractManualEntity.setDeadDate(item.size()>22?item.get(22):"");

            int n = contractManualDao.add(contractManualEntity);


            i++;
        }
        System.out.println(" count = "+i);

        return "contract_manual执行成功";
    }


    @GetMapping("contract_manual2")
    public Object contract_manual2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" DingDingRestController   contract_manual2  ");
        ContractManualQuery query = new ContractManualQuery();
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(500);
        Page<ContractManualEntity> page = contractManualService.find(query,pageRequest);
        for (ContractManualEntity contractManualEntity : page.getContent()){
            MemberEntity memberEntityDB = memberService.getByPhone(contractManualEntity.getPhone());
            if(memberEntityDB!=null){
                logger.info(" ============   "+contractManualEntity.getPhone() +" 已存在 ");
                continue;
            }
            MemberEntity member = new MemberEntity();
            member.setMemberId(IDUtils.getId());
            member.setType("M");
            member.setName(contractManualEntity.getMemberName());
            member.setPhone(contractManualEntity.getPhone());
            member.setSalesman(contractManualEntity.getSalesman());
            member.setOrigin("EXCEL导入");
            StaffEntity staffEntity = staffService.getByPhone(contractManualEntity.getCoachPhone());
            if(staffEntity!=null){
                member.setCoachStaffId(staffEntity.getStaffId());
            }
            memberService.add(member);
        }
        return "contract_manual2执行成功";
    }

    @GetMapping("contract_manual3")
    public Object contract_manual3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" DingDingRestController   contract_manual3  ");
        ContractManualQuery query = new ContractManualQuery();
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(500);
        Page<ContractManualEntity> page = contractManualService.find(query,pageRequest);
        int n = 0;
        for (ContractManualEntity contractManualEntity : page.getContent()){
            MemberEntity memberEntityDB = memberService.getByPhone(contractManualEntity.getPhone());
            if(memberEntityDB==null){
                logger.info(" ============   "+contractManualEntity.getPhone() +" 不存在 ");
                continue;
            }
            String coachId = "123";
            String cardId = "1";
            if(contractManualEntity.getStatus().indexOf("有效")>=0||contractManualEntity.getStatus().indexOf("停")>=0){
                int total = Integer.parseInt(contractManualEntity.getTotal());
                int count = Integer.parseInt(contractManualEntity.getCount());
                if(count==0){
                    continue;
                }
                MemberCardEntity memberCardEntity = new MemberCardEntity();
                memberCardEntity.setCardNo(IDUtils.getId());
                memberCardEntity.setCardId(cardId);
                memberCardEntity.setCoachId(coachId);
                memberCardEntity.setContractId(contractManualEntity.getContractId());
                memberCardEntity.setMemberId(memberEntityDB.getMemberId());
                if(contractManualEntity.getCardType().indexOf("私教")>=0) {
                    memberCardEntity.setType(CardTypeEnum.PT.getKey());
                }
                if(contractManualEntity.getCardType().indexOf("赠课")>=0) {
                    memberCardEntity.setType(CardTypeEnum.TY.getKey());
                }
                memberCardEntity.setCount(count);
                memberCardEntity.setTotal(total);
                memberCardEntity.setDays(ut.passDayByDate(contractManualEntity.getStartDate(),contractManualEntity.getEndDate()));
                memberCardEntity.setMoney(contractManualEntity.getMoney());
                memberCardEntity.setStartDate(contractManualEntity.getStartDate());
                memberCardEntity.setEndDate(contractManualEntity.getEndDate());
                memberCardService.add(memberCardEntity);
                n++;

                ContractManualEntity contractManualEntityUpdate = new ContractManualEntity();
                contractManualEntityUpdate.setContractId(contractManualEntity.getContractId());
                contractManualEntityUpdate.setDealFlag(1);
                contractManualService.update(contractManualEntityUpdate);

            }
        }
        logger.info("  n = {}" , n);
        return "contract_manual3执行成功";
    }

    @GetMapping("contract_manual4")
    public Object contract_manual4(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" DingDingRestController   contract_manual4  ");
        ContractManualQuery query = new ContractManualQuery();
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(5000);
        Page<ContractManualEntity> page = contractManualService.find(query,pageRequest);
        int n = 0;
        for (ContractManualEntity contractManualEntity : page.getContent()){
            MemberEntity memberEntityDB = memberService.getByPhone(contractManualEntity.getPhone());
            if(memberEntityDB==null){
                logger.info(" ============   "+contractManualEntity.getPhone() +" 不存在 ");
                continue;
            }
            if(contractManualEntity.getStatus().indexOf("停")>=0){
                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setMemberId(memberEntityDB.getMemberId());
                memberEntity.setStatus(9);   //  暂停
                logger.info(" ============  getPauseDate = " , contractManualEntity.getPauseDate());
                n++;
            }
        }
        logger.info("  n = {}" , n);
        return "contract_manual4执行成功";
    }

}
