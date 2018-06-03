package com.training.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.training.common.Const;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.StaffDao;
import com.training.domain.Staff;
import com.training.domain.User;
import com.training.entity.StaffEntity;
import com.training.entity.StaffQuery;
import com.training.util.JwtUtil;
import com.training.util.RequestContextHelper;
import com.training.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class LoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private JwtUtil jwt;

    public Object doLogin(Staff staff) {
        if(StringUtils.isEmpty(staff.getUsername())||StringUtils.isEmpty(staff.getPassword())){
            return ResponseUtil.exception("用户名或密码不能为空！");
        }
        StaffEntity staffEntity = staffDao.getByUsername(staff.getUsername());
        if(staffEntity==null){
            return ResponseUtil.exception("用户名不存在！");
        }
        if(!staffEntity.getPassword().equals(staff.getPassword())){
            return ResponseUtil.exception("密码错误！");
        }
        JSONObject jo = new JSONObject();
        String subject = JwtUtil.generalAdminSubject(staffEntity);
        try {
            String token = jwt.createJWT(Const.JWT_ID, subject, Const.JWT_TTL);
            jo.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.success("登录成功",jo);
    }
}

