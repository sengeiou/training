package com.training.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.training.common.Const;
import com.training.dao.*;
import com.training.domain.MemberCard;
import com.training.domain.Staff;
import com.training.entity.StaffEntity;
import com.training.util.JwtUtil;
import com.training.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * staff 核心业务操作类
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@Service
public class AdminService {

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
    private CardDao cardDao;

    public ResponseEntity<String> giveCard(MemberCard card) {
        logger.info(" AdminService  giveCard  card = {}",card);



        return ResponseUtil.success("发卡成功");
    }
}

