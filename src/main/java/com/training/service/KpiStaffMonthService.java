package com.training.service;

import com.training.dao.*;
import com.training.domain.KpiStaffMonth;
import com.training.domain.KpiTemplateQuota;
import com.training.entity.*;
import com.training.domain.User;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * kpi_staff_month 核心业务操作类
 * Created by huai23 on 2018-07-13 23:24:52.
 */ 
@Service
public class KpiStaffMonthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KpiStaffMonthDao kpiStaffMonthDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private KpiTemplateDao kpiTemplateDao;

    @Autowired
    private KpiTemplateQuotaDao kpiTemplateQuotaDao;


    /**
     * 新增实体
     * @param kpiStaffMonth
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public ResponseEntity<String> add(KpiStaffMonthEntity kpiStaffMonth){
        User user = RequestContextHelper.getUser();
        int n = kpiStaffMonthDao.add(kpiStaffMonth);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-13 23:24:52.
     */ 
    public Page<KpiStaffMonth> find(KpiStaffMonthQuery query , PageRequest page){
        logger.info(" find   query = {}  ",query);
        List<KpiStaffMonthEntity> kpiStaffMonthList = kpiStaffMonthDao.find(query,page);
        logger.info(" find   kpiStaffMonthList = {}  ",kpiStaffMonthList.size());

        List<KpiStaffMonth> kpiList = new ArrayList();
        for(KpiStaffMonthEntity kpiStaffMonthEntity :kpiStaffMonthList){
            KpiStaffMonth kpiStaffMonth = convertKpiStaffMonth(kpiStaffMonthEntity);
            kpiList.add(kpiStaffMonth);
        }
        Long count = kpiStaffMonthDao.count(query);
        Page<KpiStaffMonth> returnPage = new Page<>();
        returnPage.setContent(kpiList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-07-13 23:24:52.
     */
    public Page<KpiStaffMonth> findByCoach(KpiStaffMonthQuery query , PageRequest page){
        page.setPageSize(100);
        query.setStartMonth(ut.currentFullMonth(-6).replaceAll("-",""));
        query.setEndMonth(ut.currentFullMonth().replaceAll("-",""));
        List<KpiStaffMonthEntity> kpiStaffMonthList = kpiStaffMonthDao.find(query,page);
        logger.info("  findByCoach  query = {}",query);
        List<KpiStaffMonth> kpiList = new ArrayList();
        for(KpiStaffMonthEntity kpiStaffMonthEntity :kpiStaffMonthList){
            KpiStaffMonth kpiStaffMonth = convertKpiStaffMonth(kpiStaffMonthEntity);
            kpiList.add(kpiStaffMonth);
        }
        Page<KpiStaffMonth> returnPage = new Page<>();
        returnPage.setContent(kpiList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(kpiList.size());
        return returnPage;
    }

    private KpiStaffMonth convertKpiStaffMonth(KpiStaffMonthEntity kpiStaffMonthEntity) {
        if(kpiStaffMonthEntity==null){
            return null;
        }
        KpiStaffMonth kpiStaffMonth = new KpiStaffMonth();
        BeanUtils.copyProperties(kpiStaffMonthEntity,kpiStaffMonth);

        StaffEntity staffEntity = staffDao.getById(kpiStaffMonth.getStaffId());

        StoreEntity storeEntity = storeDao.getById(kpiStaffMonthEntity.getStoreId());
        if(storeEntity!=null){
            kpiStaffMonth.setStoreName(storeEntity.getName());
        }else{
            kpiStaffMonth.setStoreName("-");
        }

        KpiTemplateEntity kpiTemplateEntity = kpiTemplateDao.getById(staffEntity.getTemplateId());
        if(kpiTemplateEntity!=null){
            kpiStaffMonth.setTemplateName(kpiTemplateEntity.getTitle());

            kpiStaffMonth.setKpiTemplateQuotaList(new ArrayList<>());

            KpiTemplateQuotaQuery query = new KpiTemplateQuotaQuery();
            query.setTemplateId(kpiStaffMonthEntity.getTemplateId());
            PageRequest page = new PageRequest();
            page.setPageSize(100);
            List<KpiTemplateQuotaEntity> kpiTemplateQuotaEntityList = kpiTemplateQuotaDao.find(query,page);
            int i = 0;
            for (KpiTemplateQuotaEntity kpiTemplateQuotaEntity : kpiTemplateQuotaEntityList){
                KpiTemplateQuota kpiTemplateQuota = new KpiTemplateQuota();
                BeanUtils.copyProperties(kpiTemplateQuotaEntity,kpiTemplateQuota);
                kpiTemplateQuota.setFinishRate("123"+i);
                kpiTemplateQuota.setScore("9"+i);
                kpiTemplateQuota.setKpiScore(""+i);
                kpiStaffMonth.getKpiTemplateQuotaList().add(kpiTemplateQuota);
            }

        }else{
            kpiStaffMonth.setTemplateName("-");
        }
        return kpiStaffMonth;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    public Long count(KpiStaffMonthQuery query){
        Long count = kpiStaffMonthDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    public KpiStaffMonth getByIdAndMonth(String id,String month){
        logger.info(" getByIdAndMonth , id = {} , month = {}  ",id,month);
        KpiStaffMonthEntity kpiStaffMonthDB = kpiStaffMonthDao.getByIdAndMonth(id,month);
        KpiStaffMonth kpiStaffMonth = convertKpiStaffMonth(kpiStaffMonthDB);
        return kpiStaffMonth;
    }

    /**
     * 根据实体更新
     * @param kpiStaffMonth
     * Created by huai23 on 2018-07-13 23:24:53.
     */
    @Transactional
    public  ResponseEntity<String> update(KpiStaffMonthEntity kpiStaffMonth){
        logger.info(" KpiStaffMonthService  update , kpiStaffMonth = {} ",kpiStaffMonth);
        String templateId = kpiStaffMonth.getTemplateId();
        if(StringUtils.isNotEmpty(templateId)){
            StaffEntity staffEntity = new StaffEntity();
            staffEntity.setStaffId(kpiStaffMonth.getStaffId());
            staffEntity.setTemplateId(kpiStaffMonth.getTemplateId());
            staffDao.update(staffEntity);
        }
        kpiStaffMonth.setTemplateId(null);
        int n = kpiStaffMonthDao.update(kpiStaffMonth);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-07-13 23:24:53.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = kpiStaffMonthDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

