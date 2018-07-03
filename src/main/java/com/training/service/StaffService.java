package com.training.service;

import com.training.dao.*;
import com.training.domain.Role;
import com.training.domain.Staff;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
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
    private RoleDao roleDao;

    /**
     * 新增实体
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    public ResponseEntity<String> add(StaffEntity staff){
        User user = RequestContextHelper.getUser();
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
        staff.setMemberCount(10);
        staff.setKpi(98);
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
}

