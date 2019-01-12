package com.training.service;

import com.training.dao.*;
import com.training.domain.KpiStaffDetail;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * kpi_staff_detail 核心业务操作类
 * Created by huai23 on 2018-11-18 12:13:37.
 */ 
@Service
public class KpiStaffDetailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiStaffDetailDao kpiStaffDetailDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增实体
     * @param kpiStaffDetail
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    public ResponseEntity<String> add(KpiStaffDetailEntity kpiStaffDetail){
        User user = RequestContextHelper.getUser();
        int n = kpiStaffDetailDao.add(kpiStaffDetail);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    public Page<KpiStaffDetailEntity> find(KpiStaffDetailQuery query , PageRequest page){
        List<KpiStaffDetailEntity> kpiStaffDetailList = kpiStaffDetailDao.find(query,page);
        Long count = kpiStaffDetailDao.count(query);
        Page<KpiStaffDetailEntity> returnPage = new Page<>();
        returnPage.setContent(kpiStaffDetailList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    public Long count(KpiStaffDetailQuery query){
        Long count = kpiStaffDetailDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    public KpiStaffDetailEntity getById(String id){
        KpiStaffDetailEntity kpiStaffDetailDB = kpiStaffDetailDao.getById(id);
        return kpiStaffDetailDB;
    }

    /**
     * 根据实体更新
     * @param kpiStaffDetail
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    public  ResponseEntity<String> update(KpiStaffDetailEntity kpiStaffDetail){
        int n = kpiStaffDetailDao.update(kpiStaffDetail);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-11-18 12:13:37.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = kpiStaffDetailDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    public List<KpiStaffDetail> queryXkAndJkByMonth(KpiStaffDetailQuery query){
        PageRequest page = new PageRequest();
        page.setPageSize(1000);
        List<KpiStaffDetail> data = new ArrayList<>();
        List<KpiStaffDetailEntity> kpiStaffDetailList = kpiStaffDetailDao.queryXkAndJkByMonth(query);
        for (KpiStaffDetailEntity kpiStaffDetailEntity:kpiStaffDetailList){
            KpiStaffDetail kpiStaffDetail = transfer(kpiStaffDetailEntity);
            data.add(kpiStaffDetail);
        }
        return data;
    }

    private KpiStaffDetail transfer(KpiStaffDetailEntity kpiStaffDetailEntity) {
        KpiStaffDetail kpiStaffDetail = new KpiStaffDetail();
        BeanUtils.copyProperties(kpiStaffDetailEntity,kpiStaffDetail);
        MemberEntity memberEntity = memberService.getById(kpiStaffDetailEntity.getMemberId());
        if(memberEntity!=null){
            kpiStaffDetail.setMemberName(memberEntity.getName());
            kpiStaffDetail.setPhone(memberEntity.getPhone());
        }
        if(kpiStaffDetailEntity.getType().equals("XK")){
            kpiStaffDetail.setShowType("续课");
        } else if(kpiStaffDetailEntity.getType().equals("JK")){
            kpiStaffDetail.setShowType("结课");
        } else{
            kpiStaffDetail.setShowType(kpiStaffDetailEntity.getType());
        }
        return kpiStaffDetail;
    }

    public List queryValidMemberCountByMonth(KpiStaffDetailQuery query){
        String m = query.getMonth().substring(5,7);
        String tableName = "member_his_"+m;
        List data = new ArrayList();
        String sql = "select backup_date day , count(member_id) total from "+tableName+" where coach_staff_id = ? GROUP BY backup_date ";
        List result = jdbcTemplate.queryForList(sql,new Object[]{query.getStaffId()});

        return result;
    }

}

