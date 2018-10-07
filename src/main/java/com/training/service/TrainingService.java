package com.training.service;

import com.training.dao.*;
import com.training.domain.Member;
import com.training.domain.Training;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.QrCodeUtils;
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
import java.util.List;

/**
 * training 核心业务操作类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Service
public class TrainingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StoreDao storeDao;
    /**
     * 新增实体
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public ResponseEntity<String> add(TrainingEntity training){
        int n = trainingDao.add(training);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public Page<Training> find(TrainingQuery query , PageRequest page){
        logger.info(" find   query = {} , page = {} ",query,page);
        List<TrainingEntity> trainingList = trainingDao.find(query,page);
        List<Training> data = new ArrayList<>();
        for (TrainingEntity trainingEntity : trainingList){
            Training training = transferTraining(trainingEntity);
            data.add(training);
        }
        Long count = trainingDao.count(query);
        Page<Training> returnPage = new Page<>();
        returnPage.setContent(data);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public Long count(TrainingQuery query){
        Long count = trainingDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public Training getById(String id){
        TrainingEntity trainingDB = trainingDao.getById(id);
        Training training = transferTraining(trainingDB);
//        String text = "https://trainingbj.huai23.com/sign_in?id="+training.getTrainingId()+"_"+System.currentTimeMillis();
//        String qrcode = QrCodeUtils.getBase64Str(text);
//        logger.info("  qrcode   indexOf = {} ",  qrcode.indexOf("\\r\\n"));
//        training.setQrCode(qrcode);
        return training;
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public  ResponseEntity<String> update(TrainingEntity training){
        int n = trainingDao.update(training);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = trainingDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    public ResponseEntity<String> list(TrainingQuery query) {
        Member memberToken = RequestContextHelper.getMember();
        if(StringUtils.isEmpty(query.getStartDate())||StringUtils.isEmpty(query.getEndDate())){
            return ResponseUtil.success("请输入起始日期和结束日期");
        }
        if(StringUtils.isNotEmpty(query.getMemberId())&&StringUtils.isEmpty(query.getType())){
//            return ResponseUtil.success("参数错误type is null");
        }
        query.setMemberId(memberToken.getMemberId());
        query.setStatus(0);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(1000);
        List<TrainingEntity> trainingList =  trainingDao.find(query,pageRequest);
        logger.info(" =================    list  trainingList = {}",trainingList.size());
        for (TrainingEntity trainingEntity:trainingList){
            logger.info(" =================   trainingEntity = {} ",trainingEntity);
        }
        return ResponseUtil.success(trainingList);
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    public Page<Training> trainingList(TrainingQuery query , PageRequest page){
        List<TrainingEntity> trainingList = trainingDao.find(query,page);
        List<Training> data = new ArrayList<>();
        for (TrainingEntity trainingEntity : trainingList){
            Training training = transferTraining(trainingEntity);
            data.add(training);
        }
        Long count = trainingDao.count(query);
        Page<Training> returnPage = new Page<>();
        returnPage.setContent(data);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    public Training transferTraining(TrainingEntity trainingEntity) {
        if(trainingEntity==null){
            return null;
        }
        Training training = new Training();
        BeanUtils.copyProperties(trainingEntity,training);
        StoreEntity storeEntity = storeDao.getById(trainingEntity.getStoreId());
        training.setStoreName("未知场馆");
        if(storeEntity!=null){
            training.setStoreName(storeEntity.getName());
        }
        MemberEntity memberEntity = memberService.getById(trainingEntity.getMemberId());
        Member member = new Member();
        if(memberEntity!=null){
            BeanUtils.copyProperties(memberEntity,member);
        }
        training.setMember(member);
        return training;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    public Page<Training> findByStaff(TrainingQuery query , PageRequest page){
        logger.info("findByStaff  query = {} , page = {} ", query,page);
        query.setStatus(0);
        List<TrainingEntity> trainingList = trainingDao.find(query,page);
        List<Training> data = new ArrayList<>();
        for (TrainingEntity trainingEntity : trainingList){
            Training training = transferTraining(trainingEntity);
            data.add(training);
        }
        Long count = trainingDao.count(query);
        Page<Training> returnPage = new Page<>();
        returnPage.setContent(data);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }


    public ResponseEntity<String> qrCode(TrainingQuery query) {
        Member memberToken = RequestContextHelper.getMember();
        logger.info(" qrCode  query = {} ",query);
        TrainingEntity trainingEntity = trainingDao.getById(query.getTrainingId());
        if(trainingEntity==null){
            return ResponseUtil.exception("获取二维码异常");
        }
        if(!ut.currentDate().equals(trainingEntity.getLessonDate())){
            return ResponseUtil.exception("不是训练课程日，不能签到");
        }

        int currentHour = ut.currentHour();

        int startHour = trainingEntity.getStartHour();
        if(startHour%100==0){
            startHour = startHour - 70;
        }else{
            startHour = startHour - 30;
        }
        if(currentHour<startHour){
            return ResponseUtil.exception("上课前半小时内允许签到，时间未到不能签到");
        }
        if(currentHour>trainingEntity.getEndHour()) {
            return ResponseUtil.exception("已过下课时间，不能签到");
        }
        logger.info(" =================   trainingEntity = {} ",trainingEntity);
        String text = "https://trainingbj.huai23.com/sign_in?id="+trainingEntity.getTrainingId()+"_"+System.currentTimeMillis();
        String qrcode = QrCodeUtils.getBase64Str(text);
        return ResponseUtil.success("获取成功",qrcode);
    }

}

