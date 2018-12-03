package com.training.service;

import com.training.admin.service.CalculateKpiService;
import com.training.dao.*;
import com.training.domain.*;
import com.training.entity.*;
import com.training.common.*;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class StaffService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RoleService roleService;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StaffMedalDao staffMedalDao;

    @Autowired
    private MedalDao medalDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private KpiTemplateDao kpiTemplateDao;

    @Autowired
    private CalculateKpiService calculateKpiService;

    @Autowired
    private KpiStaffMonthService kpiStaffMonthService;

    /**
     * 新增实体
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public ResponseEntity<String> add(StaffEntity staff){
        int n = staffDao.add(staff);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public Page<Staff> find(StaffQuery query , PageRequest page){
        Staff staffRequest = RequestContextHelper.getStaff();
        StaffEntity staffDB = staffDao.getById(staffRequest.getStaffId());
        RoleEntity roleEntity = roleDao.getById(staffDB.getRoleId());
        if(StringUtils.isEmpty(query.getStoreId())){
            if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                query.setStoreId(roleEntity.getStoreData());
            }else{
                query.setStoreId("123456789");
            }
            if(staffDB.getUsername().equals("admin")){
                query.setStoreId(null);
            }
        }else{
            if(staffDB.getUsername().equals("admin")){

            }else{
                if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                    String[] stores = roleEntity.getStoreData().split(",");
                    Set<String> ids = new HashSet<>();
                    for (int i = 0; i < stores.length; i++) {
                        if(StringUtils.isNotEmpty(stores[i])){
                            ids.add(stores[i]);
                        }
                    }
                    if(ids.contains(query.getStoreId())){

                    }else{
                        query.setStoreId("123456789");
                    }
                }else{
                    query.setStoreId("123456789");
                }
            }
        }
        logger.info("  find  StaffQuery = {}",query);
        List<Staff> staffs = new ArrayList();
        List<StaffEntity> staffList = staffDao.find(query,page);
        for (StaffEntity staffEntity : staffList) {
            Staff staff = convertEntityToStaff(staffEntity);
            staff.setStar(2);
            staffs.add(staff);
        }
        Long count = staffDao.count(query);
        Page<Staff> returnPage = new Page<>();
        returnPage.setContent(staffs);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }


    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    public Page<Staff> findForSelectList(StaffQuery query , PageRequest page){
        query = new StaffQuery();
        Staff staffRequest = RequestContextHelper.getStaff();
        StaffEntity staffDB = staffDao.getById(staffRequest.getStaffId());
        RoleEntity roleEntity = roleDao.getById(staffDB.getRoleId());

        if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
            query.setStoreId(roleEntity.getStoreData());
        }else{
            query.setStoreId("123456789");
        }
        if(staffDB.getUsername().equals("admin")){
            query.setStoreId(null);
        }

        List<StaffEntity> staffList = staffDao.find(query,page);
        List<Staff> staffs = new ArrayList();
        for (StaffEntity staffEntity : staffList) {
            Staff staff = new Staff();
            staff.setStaffId(staffEntity.getStaffId());
            staff.setCustname(staffEntity.getCustname());
            staffs.add(staff);
        }
        Long count = staffDao.count(query);
        Page<Staff> returnPage = new Page<>();
        returnPage.setContent(staffs);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 分页查询员工勋章列表
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    public Page<Staff> staffMedalList(StaffQuery query , PageRequest page){
        Staff staffRequest = RequestContextHelper.getStaff();
        StaffEntity staffDB = staffDao.getById(staffRequest.getStaffId());
        RoleEntity roleEntity = roleDao.getById(staffDB.getRoleId());
        if(StringUtils.isEmpty(query.getStoreId())){
            if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                query.setStoreId(roleEntity.getStoreData());
            }else{
                query.setStoreId("123456789");
            }
            if(staffDB.getUsername().equals("admin")){
                query.setStoreId(null);
            }
        }else{
            if(staffDB.getUsername().equals("admin")){

            }else{
                if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                    String[] stores = roleEntity.getStoreData().split(",");
                    Set<String> ids = new HashSet<>();
                    for (int i = 0; i < stores.length; i++) {
                        if(StringUtils.isNotEmpty(stores[i])){
                            ids.add(stores[i]);
                        }
                    }
                    if(ids.contains(query.getStoreId())){

                    }else{
                        query.setStoreId("123456789");
                    }
                }else{
                    query.setStoreId("123456789");
                }
            }
        }
        logger.info("  staffMedalList  StaffQuery = {}",query);
        List<Staff> staffs = new ArrayList();
        List<StaffEntity> staffList = staffDao.find(query,page);
        for (StaffEntity staffEntity : staffList) {
            Staff staff = convertEntityToStaff(staffEntity);
            List<StaffMedal> staffMedalList = staffMedalDao.queryStaffMedalList(staffEntity.getStaffId());
            staff.setStaffMedalList(staffMedalList);
            staffs.add(staff);
        }
        Long count = staffDao.count(query);
        Page<Staff> returnPage = new Page<>();
        returnPage.setContent(staffs);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    private Staff convertEntityToStaff(StaffEntity staffEntity) {
        Staff staff = new Staff();
        BeanUtils.copyProperties(staffEntity,staff);
        Role role = roleService.getById(staff.getRoleId());
        staff.setRole(role);
        String roleName = "无";
        if(role!=null){
            roleName = role.getRoleName();
        }
        staff.setRoleName(roleName);

        StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
        if(storeEntity!=null){
            staff.setStoreName(storeEntity.getName());
        }else{
            staff.setStoreName("无");
        }

        if(StringUtils.isNotEmpty(staff.getTemplateId())){
            KpiTemplateEntity kpiTemplateEntity = kpiTemplateDao.getById(staff.getTemplateId());
            staff.setTemplateName(kpiTemplateEntity.getTitle());
        }else {
            staff.setTemplateName("-");
        }
        int count = calculateKpiService.queryTotalMemberCountByDay(staff.getStaffId(), ut.currentDate(-1));
        staff.setMemberCount(count);
        staff.setKpi("0");
        KpiStaffMonth kpiStaffMonth = kpiStaffMonthService.getByIdAndMonth(staffEntity.getStaffId(),ut.currentKpiMonth());
        if(kpiStaffMonth!=null){
            staff.setKpi(kpiStaffMonth.getKpiScore());
        }

        return staff;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public Long count(StaffQuery query){
        Long count = staffDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public Staff getById(String id){
        StaffEntity staffDB = staffDao.getById(id);
        Staff staff = convertEntityToStaff(staffDB);
        return staff;
    }

    /**
     * 根据实体更新
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public  ResponseEntity<String> update(StaffEntity staff){
        int n = staffDao.update(staff);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = staffDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    public StaffEntity getByUsername(String username){
        StaffEntity staffDB = staffDao.getByUsername(username);
        return staffDB;
    }


    public StaffEntity getByPhone(String phone){
        StaffEntity staffDB = staffDao.getByPhone(phone);
        return staffDB;
    }

    /**
     * 新增实体
     * @param staff
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public int bind(StaffEntity staff){
        int n = staffDao.bind(staff);
        return n;
    }

    /**
     * 根据实体更新
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    public  ResponseEntity<String> updatePwd(Staff staff){
        Staff staffRequest = RequestContextHelper.getStaff();
        logger.info("  updatePwd  staffRequest = {}",staffRequest);
        logger.info("  updatePwd  staff = {}",staff);
        if(staffRequest==null||staff==null){
            return ResponseUtil.exception("修改异常");
        }
        if(StringUtils.isEmpty(staff.getPassword())){
            return ResponseUtil.exception("原密码不能为空");
        }
        if(StringUtils.isEmpty(staff.getNewPassword())){
            return ResponseUtil.exception("新密码不能为空");
        }
        StaffEntity staffDB = staffDao.getById(staffRequest.getStaffId());
        if(staffDB==null){
            return ResponseUtil.exception("修改异常");
        }
        if(!staffDB.getPassword().equals(staff.getPassword())){
            return ResponseUtil.exception("原密码错误");
        }
        StaffEntity staffUpdate = new StaffEntity();
        staffUpdate.setStaffId(staffRequest.getStaffId());
        staffUpdate.setPassword(staff.getNewPassword());
        int n = staffDao.update(staffUpdate);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    public ResponseEntity<String> getLoginStaffInfo() {
        Staff staffRequest = RequestContextHelper.getStaff();
        if(staffRequest==null){
            return ResponseUtil.exception("查询失败");
        }
        Staff staff = this.getById(staffRequest.getStaffId());


        return ResponseUtil.success(staff);
    }

    public ResponseEntity<String> resetPwd(StaffEntity staff) {
        String pwd = "123456";
        StaffEntity staffUpdate = new StaffEntity();
        staffUpdate.setStaffId(staff.getStaffId());
        staffUpdate.setPassword(pwd);
        int n = staffDao.update(staffUpdate);
        if(n==1){
            return ResponseUtil.success("密码重置成功，新密码:"+pwd+"，请及时登陆修改");
        }
        return ResponseUtil.exception("密码重置失败");
    }

    public ResponseEntity<String> leave(String id) {
        StaffEntity staffEntity = staffDao.getById(id);
        List<MemberEntity> memberEntityList = memberDao.getMemberByStaffId(id);
        if(memberEntityList.size()>0){
            return ResponseUtil.exception("教练名下还有"+memberEntityList.size()+"个会员，不能进行离职操作");
        }
        int n = staffDao.leave(id);
        if(n==1){
            if(StringUtils.isNotEmpty(staffEntity.getOpenId())){
                MemberEntity memberEntity = memberDao.getByOpenId(staffEntity.getOpenId());
                if(memberEntity!=null){
                    memberDao.logoffByStaff(memberEntity.getMemberId());
                    staffDao.logoffByStaff(memberEntity.getOpenId());
                }
            }
            return ResponseUtil.success("离职成功");
        }
        return ResponseUtil.exception("离职失败");
    }

    public ResponseEntity<String> entry(String id) {
        int n = staffDao.entry(id);
        if(n==1){
            return ResponseUtil.success("复职成功");
        }
        return ResponseUtil.exception("复职失败");
    }

}

