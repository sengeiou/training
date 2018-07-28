package com.training.service;

import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.dao.RoleDao;
import com.training.dao.StaffDao;
import com.training.dao.StoreDao;
import com.training.domain.Staff;
import com.training.domain.StoreData;
import com.training.domain.User;
import com.training.entity.*;
import com.training.util.RequestContextHelper;
import com.training.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * store 核心业务操作类
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@Service
public class StoreDataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private RoleDao roleDao;


    public List<StoreData> querySaleMoney(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();
        StoreData sjkxq = new StoreData();
        sjkxq.setLabel("私教课新签");
        sjkxq.setCount("2");
        sjkxq.setLesson("10");
        sjkxq.setMoney("100");
        storeDataList.add(sjkxq);

        StoreData sjkxk = new StoreData();
        sjkxk.setLabel("私教课续课");
        sjkxk.setCount("2");
        sjkxk.setLesson("10");
        sjkxk.setMoney("100");
        storeDataList.add(sjkxk);

        StoreData sjkzjs = new StoreData();
        sjkzjs.setLabel("私教课转介绍");
        sjkzjs.setCount("2");
        sjkzjs.setLesson("10");
        sjkzjs.setMoney("100");
        storeDataList.add(sjkzjs);

        StoreData ttk = new StoreData();
        ttk.setLabel("团体课");
        ttk.setCount("2");
        ttk.setLesson("10");
        ttk.setMoney("100");
        storeDataList.add(ttk);

        StoreData tsk = new StoreData();
        tsk.setLabel("特色课");
        tsk.setCount("2");
        tsk.setLesson("10");
        tsk.setMoney("100");
        storeDataList.add(tsk);

        return storeDataList;
    }

    public List<StoreData> queryIncome(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();

        StoreData hksr = new StoreData();
        hksr.setLabel("耗课收入");
        hksr.setValue("3000");
        storeDataList.add(hksr);

        StoreData sksr = new StoreData();
        sksr.setLabel("死课收入");
        sksr.setValue("500");
        storeDataList.add(sksr);

        StoreData yqsr = new StoreData();
        yqsr.setLabel("延期收入");
        yqsr.setValue("100");
        storeDataList.add(yqsr);

        return storeDataList;
    }

    public List<StoreData> queryChangeRate(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();

        StoreData xzyxhys = new StoreData();
        xzyxhys.setLabel("新增意向会员数");
        xzyxhys.setValue("100");
        storeDataList.add(xzyxhys);

        StoreData ddrs = new StoreData();
        ddrs.setLabel("到店人数");
        ddrs.setValue("80");
        storeDataList.add(ddrs);

        StoreData sksr = new StoreData();
        sksr.setLabel("到店率");
        sksr.setValue("80%");
        storeDataList.add(sksr);

        StoreData cjrs = new StoreData();
        cjrs.setLabel("成交人数");
        cjrs.setValue("70");
        storeDataList.add(cjrs);

        StoreData cjl = new StoreData();
        cjl.setLabel("成交率");
        cjl.setValue("70%");
        storeDataList.add(cjl);

        return storeDataList;
    }

    public List<StoreData> queryMemberData(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();

        StoreData yxhy = new StoreData();
        yxhy.setLabel("有效会员");
        yxhy.setValue("70");
        storeDataList.add(yxhy);

        StoreData tkrs = new StoreData();
        tkrs.setLabel("停课会员");
        tkrs.setValue("10");
        storeDataList.add(tkrs);

        StoreData hyd = new StoreData();
        hyd.setLabel("会员活跃度");
        hyd.setValue("60");
        storeDataList.add(hyd);

        StoreData jkrs = new StoreData();
        jkrs.setLabel("结课人数");
        jkrs.setValue("50");
        storeDataList.add(jkrs);

        StoreData xkrs = new StoreData();
        xkrs.setLabel("续课人数");
        xkrs.setValue("20");
        storeDataList.add(xkrs);

        StoreData xkl = new StoreData();
        xkl.setLabel("续课率");
        xkl.setValue("40%");
        storeDataList.add(xkl);

        return storeDataList;
    }

    public List<StoreData> queryManagerKpi(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();

        StoreData manager = new StoreData();
        manager.setStaffName("张三");
        manager.setValue("102");
        storeDataList.add(manager);

        return storeDataList;
    }

    public List<StoreData> queryCoachKpi(StoreDataQuery query) {
        List<StoreData> storeDataList= new ArrayList();
        for (int i = 0; i < 15; i++) {
            StoreData coach = new StoreData();
            coach.setStaffName("教练-"+i);
            coach.setValue(""+ (90+i));
            storeDataList.add(coach);
        }
        return storeDataList;
    }
}

