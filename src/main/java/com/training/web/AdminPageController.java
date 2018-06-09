package com.training.web;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.training.entity.SysLogEntity;
import com.training.service.SysLogService;
import com.training.util.IDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Date;
import java.util.Map;

/**
 * role API控制器
 * Created by huai23 on 2017-11-03 16:44:48.
 */ 
@Controller
public class AdminPageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogService sysLogService;

    /**
     * 首页
     * Created by huai23 on 2017-11-03 16:44:48.
     */
    @RequestMapping (value = "/admin")
    public String root(HttpServletRequest request, HttpServletResponse response){
        logger.info("  AdminPageController  admin  ");
        return "admin";
    }

}

