package com.training.common;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class Const {


    public static final String DOMAIN = "http://localhost:8080";

    public static ConcurrentHashMap<String,String> validCodeMap = new ConcurrentHashMap();

    public static ConcurrentSkipListSet<String> openIds = new ConcurrentSkipListSet();

    public static List<Integer> times;

    {
        times = new ArrayList();
        times.add(600);
        times.add(630);
        times.add(700);
        times.add(730);
        times.add(800);
        times.add(830);
        times.add(900);
        times.add(930);
        times.add(1000);
        times.add(1030);
        times.add(1100);
        times.add(1130);
        times.add(1200);
        times.add(1230);
        times.add(1300);
        times.add(1330);
        times.add(1400);
        times.add(1430);
        times.add(1500);
        times.add(1530);
        times.add(1600);
        times.add(1630);
        times.add(1700);
        times.add(1730);
        times.add(1800);
        times.add(1830);
        times.add(1900);
        times.add(1930);
        times.add(2000);
        times.add(2030);
        times.add(2100);
        times.add(2130);
        times.add(2200);
    }

    /**
     * 数据请求返回码
     */
    public static final int RESCODE_SUCCESS = 200;				//成功
    public static final int RESCODE_EXCEPTION = 500;			//请求抛出异常
    public static final int RESCODE_NOLOGIN = 1003;				//未登陆状态
    public static final int RESCODE_NOEXIST = 1004;				//查询结果为空
    public static final int RESCODE_NOAUTH = 1005;				//无操作权限

    /**
     * jwt
     */
    public static final String JWT_ID = "jwt";
    public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
    public static final int JWT_TTL = 30*24*60*60*1000;  //millisecond
//    public static final int JWT_REFRESH_INTERVAL = 55*60*1000;  //millisecond
    public static final int JWT_REFRESH_INTERVAL = 30*24*60*60*1000;  //millisecond
    public static final int JWT_REFRESH_TTL = 30*24*60*60*1000;  //millisecond

}
