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
        if(StringUtils.isEmpty(query.getStaffName())){
            query.setStaffName(null);
        }

        List<KpiStaffMonthEntity> kpiStaffMonthList = kpiStaffMonthDao.find(query,page);
        logger.info(" find   kpiStaffMonthList = {}  ",kpiStaffMonthList.size());

        List<KpiStaffMonth> kpiList = new ArrayList();
        for(KpiStaffMonthEntity kpiStaffMonthEntity :kpiStaffMonthList){
            KpiStaffMonth kpiStaffMonth = convertKpiStaffMonth(kpiStaffMonthEntity);
            if(kpiStaffMonth==null){
                continue;
            }
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

    public KpiStaffMonth convertKpiStaffMonth(KpiStaffMonthEntity kpiStaffMonthEntity) {
        logger.info(" convertKpiStaffMonth   kpiStaffMonthEntity = {} ",kpiStaffMonthEntity);

        double kpiScore = 0;
        if(kpiStaffMonthEntity==null){
            return null;
        }
        KpiStaffMonth kpiStaffMonth = new KpiStaffMonth();
        BeanUtils.copyProperties(kpiStaffMonthEntity,kpiStaffMonth);
        StaffEntity staffEntity = staffDao.getById(kpiStaffMonth.getStaffId());
        if(staffEntity==null){
            return null;
        }
        StoreEntity storeEntity = storeDao.getById(kpiStaffMonthEntity.getStoreId());
        if(storeEntity!=null){
            kpiStaffMonth.setStoreName(storeEntity.getName());
        }else{
            kpiStaffMonth.setStoreName("-");
        }

        logger.info(" kpiStaffMonth.getStaffId() = {},staffEntity = {} ",kpiStaffMonth.getStaffId(),staffEntity);

        KpiTemplateEntity kpiTemplateEntity = kpiTemplateDao.getById(staffEntity.getTemplateId());
        if(kpiTemplateEntity!=null){
            kpiStaffMonth.setTemplateName(kpiTemplateEntity.getTitle());
            kpiStaffMonth.setKpiTemplateQuotaList(new ArrayList<>());
            KpiTemplateQuotaQuery query = new KpiTemplateQuotaQuery();
            query.setTemplateId(staffEntity.getTemplateId());
            PageRequest page = new PageRequest();
            page.setPageSize(100);
            List<KpiTemplateQuotaEntity> kpiTemplateQuotaEntityList = kpiTemplateQuotaDao.find(query,page);
            int i = 0;
            for (KpiTemplateQuotaEntity kpiTemplateQuotaEntity : kpiTemplateQuotaEntityList){
                KpiTemplateQuota kpiTemplateQuota = new KpiTemplateQuota();
                BeanUtils.copyProperties(kpiTemplateQuotaEntity,kpiTemplateQuota);
                kpiStaffMonth.getKpiTemplateQuotaList().add(kpiTemplateQuota);
            }
        }else{
            kpiStaffMonth.setTemplateName("-");
        }
        logger.info(" convertKpiStaffMonth kpiScore : {}   ",kpiScore);
        kpiStaffMonth.setStar("0");
        if(StringUtils.isNotEmpty(kpiStaffMonthEntity.getParam1())){
            kpiStaffMonth.setStar(kpiStaffMonthEntity.getParam1());
        }
        kpiStaffMonth.setRestDays(kpiStaffMonth.getParam2());
        kpiStaffMonth.setExtraXks(kpiStaffMonth.getParam3());
        kpiStaffMonth.setExtraJks(kpiStaffMonth.getParam4());
        kpiStaffMonth.setExtraScore(kpiStaffMonth.getParam5());

        return kpiStaffMonth;
    }

    public KpiStaffMonth convertKpiStaffMonthDetail(KpiStaffMonthEntity kpiStaffMonthEntity) {
        double kpiScore = 0;
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

        logger.info(" kpiStaffMonth.getStaffId() = {},staffEntity = {} ",kpiStaffMonth.getStaffId(),staffEntity);

        KpiTemplateEntity kpiTemplateEntity = kpiTemplateDao.getById(staffEntity.getTemplateId());
        if(kpiTemplateEntity!=null){
            kpiStaffMonth.setTemplateName(kpiTemplateEntity.getTitle());
            kpiStaffMonth.setKpiTemplateQuotaList(new ArrayList<>());
            KpiTemplateQuotaQuery query = new KpiTemplateQuotaQuery();
            query.setTemplateId(staffEntity.getTemplateId());
            PageRequest page = new PageRequest();
            page.setPageSize(100);
            List<KpiTemplateQuotaEntity> kpiTemplateQuotaEntityList = kpiTemplateQuotaDao.find(query,page);
            int i = 0;
            for (KpiTemplateQuotaEntity kpiTemplateQuotaEntity : kpiTemplateQuotaEntityList){
                KpiTemplateQuota kpiTemplateQuota = new KpiTemplateQuota();
                BeanUtils.copyProperties(kpiTemplateQuotaEntity,kpiTemplateQuota);
                kpiStaffMonth.getKpiTemplateQuotaList().add(kpiTemplateQuota);
            }
        }else{
            kpiStaffMonth.setTemplateName("-");
        }
        logger.info(" convertKpiStaffMonth kpiScore : {}   ",kpiScore);
        return kpiStaffMonth;
    }

    public KpiStaffMonth calculateKpiStaffMonth(KpiStaffMonthEntity kpiStaffMonthEntity) {
        double kpiScore = 0;
        if(kpiStaffMonthEntity==null){
            return null;
        }
        KpiStaffMonth kpiStaffMonth = new KpiStaffMonth();
        BeanUtils.copyProperties(kpiStaffMonthEntity,kpiStaffMonth);

        StaffEntity staffEntity = staffDao.getById(kpiStaffMonth.getStaffId());

        String templateId = "";
        String storeId = "";
        if(staffEntity==null){
            storeId = kpiStaffMonthEntity.getStaffId();
            templateId = "15342960149761174910b207e4dc9b41f3eca88a0f041";
        }else{
            templateId = staffEntity.getTemplateId();
            storeId = staffEntity.getStoreId();
        }
        StoreEntity storeEntity = storeDao.getById(storeId);
        if(storeEntity!=null){
            kpiStaffMonth.setStoreName(storeEntity.getName());
        }else{
            kpiStaffMonth.setStoreName("-");
        }

        KpiTemplateEntity kpiTemplateEntity = kpiTemplateDao.getById(templateId);
        if(kpiTemplateEntity!=null){
            kpiStaffMonth.setTemplateName(kpiTemplateEntity.getTitle());
            kpiStaffMonth.setKpiTemplateQuotaList(new ArrayList<>());
            KpiTemplateQuotaQuery query = new KpiTemplateQuotaQuery();
            query.setTemplateId(templateId);
            PageRequest page = new PageRequest();
            page.setPageSize(100);
            List<KpiTemplateQuotaEntity> kpiTemplateQuotaEntityList = kpiTemplateQuotaDao.find(query,page);
            int i = 0;
            for (KpiTemplateQuotaEntity kpiTemplateQuotaEntity : kpiTemplateQuotaEntityList){
                KpiTemplateQuota kpiTemplateQuota = new KpiTemplateQuota();
                BeanUtils.copyProperties(kpiTemplateQuotaEntity,kpiTemplateQuota);
                kpiScore = kpiScore + calculateQuota(kpiTemplateQuota,kpiStaffMonth);
                kpiStaffMonth.getKpiTemplateQuotaList().add(kpiTemplateQuota);
            }

        }else{
            kpiStaffMonth.setTemplateName("-");
        }
        if(StringUtils.isNotEmpty(kpiStaffMonth.getExtraScore())){
            double extraScore = Double.parseDouble(kpiStaffMonth.getExtraScore());
            logger.info(" calculateKpiStaffMonth kpiScore = {} , extraScore = {}   ",kpiScore,extraScore);
            kpiScore = kpiScore + extraScore;
            if(kpiScore<0){
                kpiScore = 0;
            }
        }
        logger.info(" calculateKpiStaffMonth final kpiScore : {}   ",kpiScore);
        kpiStaffMonth.setKpiScore(ut.getDoubleString(kpiScore));
        return kpiStaffMonth;
    }

    private double calculateQuota(KpiTemplateQuota kpiTemplateQuota, KpiStaffMonth kpiStaffMonth) {
        logger.info(" calculateQuota kpiTemplateQuota : {} ,kpiStaffMonth = {} ",kpiTemplateQuota,kpiStaffMonth);
        double kpiScore = 0;
        if(KpiQuotaEnum.k1.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore(kpiStaffMonth.getTnkh());
            try{
                int tnkh = Integer.parseInt(kpiStaffMonth.getTnkh());
                kpiTemplateQuota.setFinishRate(""+tnkh+"%");
                double d = 1.0;
                double score = d*tnkh*kpiTemplateQuota.getWeight()/100;
                kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                kpiScore = score;
            }catch (Exception e){
//                logger.error(" calculateQuota ERRPR : {} ",e.getMessage(),e);
            }
        }else if(KpiQuotaEnum.k2.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");
            if(StringUtils.isNotEmpty(kpiStaffMonth.getHyd())){
                double hyd = Double.parseDouble(kpiStaffMonth.getHyd());
                kpiTemplateQuota.setFinishRate(""+hyd+"%");

                List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
                for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
//                    logger.info(" kpiQuotaStandard = {} ",kpiQuotaStandard);

                    int min = 0;
                    int max = 999999;
                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
                        max = Integer.parseInt(kpiQuotaStandard.getMax());
                    }
                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
                        min = Integer.parseInt(kpiQuotaStandard.getMin());
                    }
                    if(hyd>=min&&hyd<max){
                        int value = Integer.parseInt(kpiQuotaStandard.getScore());
                        logger.info(" value = {} ",value);
                        kpiTemplateQuota.setScore(""+value);
                        double score = (double)value*kpiTemplateQuota.getWeight()/100;
                        kpiScore = score;
                        kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                        break;
                    }
                }

            }
        }else if(KpiQuotaEnum.k3.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");
            if(StringUtils.isNotEmpty(kpiStaffMonth.getHydp())){
                int hydp = Integer.parseInt(kpiStaffMonth.getHydp());
                kpiTemplateQuota.setFinishRate(""+hydp+"%");
                double score = (double)hydp*kpiTemplateQuota.getWeight()/100;
                logger.info(" score = {} ",score);
                kpiScore = score;
                kpiTemplateQuota.setScore(""+hydp+".00");
                kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
            }
        }else if(KpiQuotaEnum.k4.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("0%");
            kpiTemplateQuota.setKpiScore("0");
            kpiTemplateQuota.setScore("0");

            String month = kpiStaffMonth.getMonth().substring(0,4)+"-"+kpiStaffMonth.getMonth().substring(4,6);
            System.out.println(month);
            String month1 = ut.currentFullMonth(month,-1).replace("-","");
            String month2= ut.currentFullMonth(month,-2).replace("-","");
            System.out.println(month1);
            System.out.println(month2);

            KpiStaffMonthEntity kpiStaffMonthEntity1 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStaffId(),month1);
            KpiStaffMonthEntity kpiStaffMonthEntity2 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStaffId(),month2);
//            System.out.println(" xks-0 = "+kpiStaffMonth.getXks());
//            System.out.println(" xks-1 = "+kpiStaffMonthEntity1.getXks());
//            System.out.println(" xks-2 = "+kpiStaffMonthEntity2.getXks());
//            System.out.println(" jks-0 = "+kpiStaffMonth.getJks());
//            System.out.println(" jks-1 = "+kpiStaffMonthEntity1.getJks());
//            System.out.println(" jks-2 = "+kpiStaffMonthEntity2.getJks());
            int xks = 0;
            int jks = 0;
            if(kpiStaffMonth!=null&&StringUtils.isNotEmpty(kpiStaffMonth.getXks())){
                xks = xks + Integer.parseInt(kpiStaffMonth.getXks());
            }
            if(kpiStaffMonthEntity1!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getXks())){
                xks = xks + Integer.parseInt(kpiStaffMonthEntity1.getXks());
            }
            if(kpiStaffMonthEntity2!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getXks())){
                xks = xks + Integer.parseInt(kpiStaffMonthEntity2.getXks());
            }
            if(kpiStaffMonth!=null&&StringUtils.isNotEmpty(kpiStaffMonth.getJks())){
                jks = jks + Integer.parseInt(kpiStaffMonth.getJks());
            }
            if(kpiStaffMonthEntity1!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getJks())){
                jks = jks + Integer.parseInt(kpiStaffMonthEntity1.getJks());
            }
            if(kpiStaffMonthEntity2!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getJks())){
                jks = jks + Integer.parseInt(kpiStaffMonthEntity2.getJks());
            }
            int xkl = 100;
            if(jks>0){
                xkl = 100*xks/jks;
            }
            kpiTemplateQuota.setFinishRate(""+xkl+"%");
            List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
            for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
//                    logger.info(" kpiQuotaStandard = {} ",kpiQuotaStandard);
                int min = 0;
                int max = 999999;
                if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
                    max = Integer.parseInt(kpiQuotaStandard.getMax());
                }
                if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
                    min = Integer.parseInt(kpiQuotaStandard.getMin());
                }
                if(xkl>=min&&xkl<max){
                    int value = Integer.parseInt(kpiQuotaStandard.getScore());
                    logger.info(" value = {} ",value);
                    kpiTemplateQuota.setScore(""+value);
                    double score = (double)value*kpiTemplateQuota.getWeight()/100;
                    kpiScore = score;
                    kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                    break;
                }
            }
        }else if(KpiQuotaEnum.k5.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("0");
            kpiTemplateQuota.setKpiScore("0");
            kpiTemplateQuota.setScore("0");

            String month = kpiStaffMonth.getMonth().substring(0,4)+"-"+kpiStaffMonth.getMonth().substring(4,6);
            System.out.println(month);
            String month1 = ut.currentFullMonth(month,-1).replace("-","");
            String month2= ut.currentFullMonth(month,-2).replace("-","");
            System.out.println(month1);
            System.out.println(month2);

            KpiStaffMonthEntity kpiStaffMonthEntity1 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStaffId(),month1);
            KpiStaffMonthEntity kpiStaffMonthEntity2 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStaffId(),month2);

//            System.out.println(" zjs-0 = "+kpiStaffMonth.getZjs());
//            System.out.println(" zjs-1 = "+kpiStaffMonthEntity1.getZjs());
//            System.out.println(" zjs-2 = "+kpiStaffMonthEntity2.getZjs());
            double zjs0 = 0;
            double zjs1 = 0;
            double zjs2 = 0;
            if(kpiStaffMonth!=null&&StringUtils.isNotEmpty(kpiStaffMonth.getZjs())){
                zjs0 = Double.parseDouble(kpiStaffMonth.getZjs());
            }
            if(kpiStaffMonthEntity1!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getZjs())){
                zjs1 = Double.parseDouble(kpiStaffMonthEntity1.getZjs());
            }
            if(kpiStaffMonthEntity2!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getZjs())){
                zjs2 = Double.parseDouble(kpiStaffMonthEntity2.getZjs());
            }
            if(StringUtils.isNotEmpty(kpiStaffMonth.getZjs())){
                double zjs = zjs0 + zjs1 + zjs2;
                kpiTemplateQuota.setFinishRate(ut.getDoubleString(zjs));
                List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
                for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
//                    logger.info(" kpiQuotaStandard = {} ",kpiQuotaStandard);

                    double min = 0;
                    double max = 999999;
                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
                        max = Integer.parseInt(kpiQuotaStandard.getMax());
                    }
                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
                        min = Integer.parseInt(kpiQuotaStandard.getMin());
                    }
                    if(zjs>=min&&zjs<max){
                        double value = Double.parseDouble(kpiQuotaStandard.getScore());
                        logger.info(" value = {} ",value);
                        kpiTemplateQuota.setScore(ut.getDoubleString(value));
                        double score = value*kpiTemplateQuota.getWeight()/100;
                        kpiScore = score;
                        kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                        break;
                    }
                }
            }
        }else if(KpiQuotaEnum.k6.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");
            if(StringUtils.isNotEmpty(kpiStaffMonth.getZykh())){
                int score =0;
                if("0".equals(kpiStaffMonth.getZykh())){
                    kpiTemplateQuota.setFinishRate("不通过");
                    score = -5;
                }else{
                    kpiTemplateQuota.setFinishRate("通过");
                }
                kpiTemplateQuota.setScore(""+score+".00");
                kpiTemplateQuota.setKpiScore(""+score+".00");
                kpiScore = score;
            }
        }else if(KpiQuotaEnum.k7.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");
            if(StringUtils.isNotEmpty(kpiStaffMonth.getTss())){
                kpiTemplateQuota.setFinishRate(kpiStaffMonth.getTss());
                kpiTemplateQuota.setScore("-"+kpiStaffMonth.getTss());
                int score = -10*Integer.parseInt(kpiStaffMonth.getTss());
                kpiScore = score;
                kpiTemplateQuota.setKpiScore(""+score+".00");
            }
        }else if(KpiQuotaEnum.k8.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");

            KpiStaffMonthEntity kpiStaffMonthEntity0 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStoreId(),kpiStaffMonth.getMonth());
            int qdzye = 0;
            if(kpiStaffMonthEntity0!=null && StringUtils.isNotEmpty(kpiStaffMonthEntity0.getQdzye())){
                qdzye = Integer.parseInt(kpiStaffMonthEntity0.getQdzye());
            }

            int xswcl = 100;
            if(StringUtils.isNotEmpty(kpiStaffMonth.getXsmb())){
                int xsmb = Integer.parseInt(kpiStaffMonth.getXsmb());
                if(xsmb>0){
                    xswcl = qdzye*100/xsmb;
                }
            }

            kpiTemplateQuota.setFinishRate(xswcl+"%");
            List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
            for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
                int min = 0;
                int max = 999999;
                if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
                    max = Integer.parseInt(kpiQuotaStandard.getMax());
                }
                if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
                    min = Integer.parseInt(kpiQuotaStandard.getMin());
                }
                if(xswcl>=min&&xswcl<max){
                    int value = Integer.parseInt(kpiQuotaStandard.getScore());
                    logger.info(" value = {} ",value);
                    kpiTemplateQuota.setScore(""+value);
                    double score = (double)value*kpiTemplateQuota.getWeight()/100;
                    kpiScore = score;
                    kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                    break;
                }
            }

        }else if(KpiQuotaEnum.k9.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");

            String month = kpiStaffMonth.getMonth().substring(0,4)+"-"+kpiStaffMonth.getMonth().substring(4,6);
            System.out.println(month);
            String month1 = ut.currentFullMonth(month,-1).replace("-","");
            String month2= ut.currentFullMonth(month,-2).replace("-","");
            System.out.println(month1);
            System.out.println(month2);

            KpiStaffMonthEntity kpiStaffMonthEntity0 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStoreId(),kpiStaffMonth.getMonth());
            KpiStaffMonthEntity kpiStaffMonthEntity1 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStoreId(),month1);
            KpiStaffMonthEntity kpiStaffMonthEntity2 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStoreId(),month2);

//            System.out.println(" xks-0 = "+kpiStaffMonthEntity0.getXks());
//            System.out.println(" xks-1 = "+kpiStaffMonthEntity1.getXks());
//            System.out.println(" xks-2 = "+kpiStaffMonthEntity2.getXks());
//            System.out.println(" jks-0 = "+kpiStaffMonthEntity0.getJks());
//            System.out.println(" jks-1 = "+kpiStaffMonthEntity1.getJks());
//            System.out.println(" jks-2 = "+kpiStaffMonthEntity2.getJks());
            int xks = 0;
            int jks = 0;
            if(kpiStaffMonthEntity0!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity0.getXks())){
                xks = xks + Integer.parseInt(kpiStaffMonthEntity0.getXks());
            }
            if(kpiStaffMonthEntity1!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getXks())){
                xks = xks + Integer.parseInt(kpiStaffMonthEntity1.getXks());
            }
            if(kpiStaffMonthEntity2!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getXks())){
                xks = xks + Integer.parseInt(kpiStaffMonthEntity2.getXks());
            }
            if(kpiStaffMonthEntity0!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity0.getJks())){
                jks = jks + Integer.parseInt(kpiStaffMonthEntity0.getJks());
            }
            if(kpiStaffMonthEntity1!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getJks())){
                jks = jks + Integer.parseInt(kpiStaffMonthEntity1.getJks());
            }
            if(kpiStaffMonthEntity2!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getJks())){
                jks = jks + Integer.parseInt(kpiStaffMonthEntity2.getJks());
            }
            int qdxkl = 100;
            if(jks>0){
                qdxkl = 100*xks/jks;
            }

            kpiTemplateQuota.setFinishRate(qdxkl+"%");
            List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
            for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
                int min = 0;
                int max = 999999;
                if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
                    max = Integer.parseInt(kpiQuotaStandard.getMax());
                }
                if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
                    min = Integer.parseInt(kpiQuotaStandard.getMin());
                }
                if(qdxkl>=min&&qdxkl<max){
                    int value = Integer.parseInt(kpiQuotaStandard.getScore());
                    logger.info(" value = {} ",value);
                    kpiTemplateQuota.setScore(""+value);
                    double score = (double)value*kpiTemplateQuota.getWeight()/100;
                    kpiScore = score;
                    kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                    break;
                }
            }

        }else if(KpiQuotaEnum.k10.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");

            String month = kpiStaffMonth.getMonth().substring(0,4)+"-"+kpiStaffMonth.getMonth().substring(4,6);
            System.out.println(month);
            String month1 = ut.currentFullMonth(month,-1).replace("-","");
            String month2= ut.currentFullMonth(month,-2).replace("-","");
            System.out.println(month1);
            System.out.println(month2);

            KpiStaffMonthEntity kpiStaffMonthEntity0 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStoreId(),kpiStaffMonth.getMonth());
            KpiStaffMonthEntity kpiStaffMonthEntity1 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStoreId(),month1);
            KpiStaffMonthEntity kpiStaffMonthEntity2 = kpiStaffMonthDao.getByIdAndMonth(kpiStaffMonth.getStoreId(),month2);

//            System.out.println(" zjs-0 = "+kpiStaffMonthEntity0.getZjs());
//            System.out.println(" zjs-1 = "+kpiStaffMonthEntity1.getZjs());
//            System.out.println(" zjs-2 = "+kpiStaffMonthEntity2.getZjs());
            double zjs0 = 0;
            double zjs1 = 0;
            double zjs2 = 0;
            if(kpiStaffMonthEntity0!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity0.getQdzjs())){
                zjs0 = Double.parseDouble(kpiStaffMonthEntity0.getQdzjs());
            }
            if(kpiStaffMonthEntity1!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity1.getQdzjs())){
                zjs1 = Double.parseDouble(kpiStaffMonthEntity1.getQdzjs());
            }
            if(kpiStaffMonthEntity2!=null&&StringUtils.isNotEmpty(kpiStaffMonthEntity2.getQdzjs())){
                zjs2 = Double.parseDouble(kpiStaffMonthEntity2.getQdzjs());
            }

            System.out.println(" zjs-0 = "+zjs0);
            System.out.println(" zjs-1 = "+zjs1);
            System.out.println(" zjs-2 = "+zjs2);

            double qdzjs = (zjs0 + zjs1 + zjs2)/3;

            System.out.println(" qdzjs = "+qdzjs);


            kpiTemplateQuota.setFinishRate(ut.getDoubleString(qdzjs));
            List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
            for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
                double min = 0;
                double max = 999999;
                if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
                    max = Double.parseDouble(kpiQuotaStandard.getMax());
                }
                if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
                    min = Double.parseDouble(kpiQuotaStandard.getMin());
                }
                if(qdzjs>=min&&qdzjs<max){
                    int value = Integer.parseInt(kpiQuotaStandard.getScore());
                    logger.info(" value = {} ",value);
                    kpiTemplateQuota.setScore(""+value);
                    double score = (double)value*kpiTemplateQuota.getWeight()/100;
                    kpiScore = score;
                    kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                    break;
                }
            }

        }else if(KpiQuotaEnum.k11.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("0");
            kpiTemplateQuota.setKpiScore("0");
            kpiTemplateQuota.setScore("0");
//            if(StringUtils.isNotEmpty(kpiStaffMonth.getQdhydp())){
//                int qdhydp = Integer.parseInt(kpiStaffMonth.getQdhydp());
//                kpiTemplateQuota.setFinishRate(qdhydp+"%");
//                List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
//                for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
//                    int min = 0;
//                    int max = 999999;
//                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
//                        max = Integer.parseInt(kpiQuotaStandard.getMax());
//                    }
//                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
//                        min = Integer.parseInt(kpiQuotaStandard.getMin());
//                    }
//                    if(qdhydp>=min&&qdhydp<max){
//                        int value = Integer.parseInt(kpiQuotaStandard.getScore());
//                        logger.info(" value = {} ",value);
//                        kpiTemplateQuota.setScore(""+value);
//                        double score = (double)value*kpiTemplateQuota.getWeight()/100;
//                        kpiScore = score;
//                        kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
//                        break;
//                    }
//                }
//            }
        }else if(KpiQuotaEnum.k12.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");
            if(StringUtils.isNotEmpty(kpiStaffMonth.getTczhl())){
                int tczhl = Integer.parseInt(kpiStaffMonth.getTczhl());
                kpiTemplateQuota.setFinishRate(tczhl+"%");
                List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
                for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
                    int min = 0;
                    int max = 999999;
                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
                        max = Integer.parseInt(kpiQuotaStandard.getMax());
                    }
                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
                        min = Integer.parseInt(kpiQuotaStandard.getMin());
                    }
                    if(tczhl>=min&&tczhl<max){
                        int value = Integer.parseInt(kpiQuotaStandard.getScore());
                        logger.info(" value = {} ",value);
                        kpiTemplateQuota.setScore(""+value);
                        double score = (double)value*kpiTemplateQuota.getWeight()/100;
                        kpiScore = score;
                        kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                        break;
                    }
                }
            }
        }else if(KpiQuotaEnum.k13.getKey().equals(kpiTemplateQuota.getQuotaId())){
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setKpiScore("-");
            kpiTemplateQuota.setScore("-");
            if(StringUtils.isNotEmpty(kpiStaffMonth.getQdhyd())){
                double qdhyd = Double.parseDouble(kpiStaffMonth.getQdhyd());
                kpiTemplateQuota.setFinishRate(qdhyd+"%");
                List<KpiQuotaStandard> kpiQuotaStandardList = kpiTemplateQuota.getStandardList();
                for (KpiQuotaStandard kpiQuotaStandard:kpiQuotaStandardList){
                    int min = 0;
                    int max = 999999;
                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMax())){
                        max = Integer.parseInt(kpiQuotaStandard.getMax());
                    }
                    if(StringUtils.isNotEmpty(kpiQuotaStandard.getMin())){
                        min = Integer.parseInt(kpiQuotaStandard.getMin());
                    }
                    if(qdhyd>=min&&qdhyd<max){
                        int value = Integer.parseInt(kpiQuotaStandard.getScore());
                        logger.info(" value = {} ",value);
                        kpiTemplateQuota.setScore(""+value);
                        double score = (double)value*kpiTemplateQuota.getWeight()/100;
                        kpiScore = score;
                        kpiTemplateQuota.setKpiScore(ut.getDoubleString(score));
                        break;
                    }
                }
            }
        }else{
            kpiTemplateQuota.setFinishRate("-");
            kpiTemplateQuota.setScore("-");
            kpiTemplateQuota.setKpiScore("-");
        }
        return kpiScore;
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
        logger.info(" getByIdAndMonth , kpiStaffMonthDB = {}  ",kpiStaffMonthDB);
        KpiStaffMonth kpiStaffMonth = convertKpiStaffMonth(kpiStaffMonthDB);
        return kpiStaffMonth;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-07-13 23:24:53.
     */
    public KpiStaffMonth getStaffKpiDetail(String id,String month){
        logger.info(" getByIdAndMonth , id = {} , month = {}  ",id,month);
        KpiStaffMonthEntity kpiStaffMonthDB = kpiStaffMonthDao.getByIdAndMonth(id,month);
        KpiStaffMonth kpiStaffMonth = calculateKpiStaffMonth(kpiStaffMonthDB);
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
//        String templateId = kpiStaffMonth.getTemplateId();
//        if(StringUtils.isNotEmpty(templateId)){
//            StaffEntity staffEntity = new StaffEntity();
//            staffEntity.setStaffId(kpiStaffMonth.getStaffId());
//            staffEntity.setTemplateId(kpiStaffMonth.getTemplateId());
//            staffDao.update(staffEntity);
//        }
        kpiStaffMonth.setTemplateId(null);
        kpiStaffMonth.setParam2(kpiStaffMonth.getRestDays());
        kpiStaffMonth.setParam3(kpiStaffMonth.getExtraXks());
        kpiStaffMonth.setParam4(kpiStaffMonth.getExtraJks());
        kpiStaffMonth.setParam5(kpiStaffMonth.getExtraScore());
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

