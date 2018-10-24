package com.training.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.training.dao.*;
import com.training.domain.Member;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * feedback 核心业务操作类
 * Created by huai23 on 2018-05-26 13:54:54.
 */ 
@Service
public class FeedbackService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private StoreDao storeDao;

    /**
     * 新增实体
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public ResponseEntity<String> add(FeedbackEntity feedback){
        if(StringUtils.isEmpty(feedback.getContent())){
            return ResponseUtil.exception("请输入反馈内容");
        }
        feedback.setContent(EmojiUtils.filterEmoji(feedback.getContent(),"[emoji]"));
        Member member = RequestContextHelper.getMember();
        MemberEntity memberEntity = memberDao.getById(member.getMemberId());
        feedback.setFeedbackId(IDUtils.getId());
        feedback.setMemberId(memberEntity.getMemberId());
        feedback.setType("feedback");
        String title = memberEntity.getName()+"的反馈";
        feedback.setTitle(title);
        int n = feedbackDao.add(feedback);
        if(n==1){
            if(!StringUtils.isEmpty(memberEntity.getCoachStaffId())){
                StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                List<StaffEntity> managers = staffDao.getManagerByStoreId(staffEntity.getStoreId());
                for (StaffEntity manager : managers){
                    manager.setPhone("18618191128");
                    if(StringUtils.isNotEmpty(manager.getPhone())){
                        try {
                            if(feedback.getType().equals(FeedbackTypeEnum.change_coach.getKey())){
                                SendSmsResponse sendSmsResponse = SmsUtil.sendChangeCoachNotice(manager.getPhone(), storeEntity.getName(),staffEntity.getCustname(),memberEntity.getName());
                                logger.info(" sendChangeCoachNotice   success sendSmsResponse = {} ",sendSmsResponse);
                            }else if(feedback.getType().equals(FeedbackTypeEnum.feedback.getKey())){
                                SendSmsResponse sendSmsResponse = SmsUtil.sendAddMemberMsgNotice(manager.getPhone(), storeEntity.getName(),staffEntity.getCustname(),memberEntity.getName());
                                logger.info(" sendChangeCoachNotice   success sendAddMemberMsgNotice = {} ",sendSmsResponse);
                            }
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 新增实体
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */
    public ResponseEntity<String> changeCoach(FeedbackEntity feedback){
        Member member = RequestContextHelper.getMember();
        MemberEntity memberEntity = memberDao.getById(member.getMemberId());
        logger.info(" changeCoach   memberEntity= {}",memberEntity);
        feedback.setFeedbackId(IDUtils.getId());
        feedback.setMemberId(memberEntity.getMemberId());
        feedback.setType("change_coach");
        feedback.setTitle(memberEntity.getName()+"更换教练申请");
        feedback.setContent(memberEntity.getName()+"于"+ ut.currentDate()+"请求更换教练");
        int n = feedbackDao.add(feedback);
        if(n==1){
            if(!StringUtils.isEmpty(memberEntity.getCoachStaffId())){
                StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
                List<StaffEntity> managers = staffDao.getManagerByStoreId(staffEntity.getStoreId());
                logger.info(" changeCoach   managers = {} ",managers.size());
                for (StaffEntity manager : managers){
                    manager.setPhone("18618191128");
                    if(StringUtils.isNotEmpty(manager.getPhone())){
                        try {
                            if(feedback.getType().equals(FeedbackTypeEnum.change_coach.getKey())){
                                SendSmsResponse sendSmsResponse = SmsUtil.sendChangeCoachNotice(manager.getPhone(), storeEntity.getName(),staffEntity.getCustname(),memberEntity.getName());
                                logger.info(" sendChangeCoachNotice   success sendSmsResponse = {} ",sendSmsResponse);
                            }else if(feedback.getType().equals(FeedbackTypeEnum.feedback.getKey())){
                                SendSmsResponse sendSmsResponse = SmsUtil.sendAddMemberMsgNotice(manager.getPhone(), storeEntity.getName(),staffEntity.getCustname(),memberEntity.getName());
                                logger.info(" sendChangeCoachNotice   success sendAddMemberMsgNotice = {} ",sendSmsResponse);
                            }
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return ResponseUtil.success("您的请求已经收到，稍后会有客服与您联系");
        }
        return ResponseUtil.exception("请求失败，请稍后再试");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public Page<FeedbackEntity> find(FeedbackQuery query , PageRequest page){
        logger.info(" =================   FeedbackService  find  query111 = {}",query);
        if(StringUtils.isEmpty(query.getTitle())){
            query.setTitle(null);
        }
        if(StringUtils.isEmpty(query.getStoreId())){
            query.setStoreId(null);
        }
        if(StringUtils.isEmpty(query.getPhone())){
            query.setPhone(null);
        }
        if(StringUtils.isNotEmpty(query.getType())){
            if(query.getType().indexOf("changecoach")>=0){
                query.setType("change_coach");
            }
        }
        logger.info(" =================   FeedbackService  find  query222 = {}",query);
        List<FeedbackEntity> feedbackList = feedbackDao.find(query,page);
        for (FeedbackEntity feedbackDB:feedbackList){
            convert(feedbackDB);
        }
        Long count = feedbackDao.count(query);
        Page<FeedbackEntity> returnPage = new Page<>();
        returnPage.setContent(feedbackList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public Long count(FeedbackQuery query){
        Long count = feedbackDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public FeedbackEntity getById(String id){
        FeedbackEntity feedbackDB = feedbackDao.getById(id);
        convert(feedbackDB);
        return feedbackDB;
    }

    private void convert(FeedbackEntity feedbackDB) {
        if(feedbackDB==null||StringUtils.isEmpty(feedbackDB.getMemberId())){
            return;
        }
        MemberEntity memberEntity = memberDao.getById(feedbackDB.getMemberId());
        if(memberEntity==null){
            return;
        }
        feedbackDB.setMemberName(memberEntity.getName());
        feedbackDB.setPhone(memberEntity.getPhone());
        if(StringUtils.isNotEmpty(memberEntity.getCoachStaffId())){
            StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
            if(staffEntity==null){
                return;
            }

            StoreEntity storeEntity = storeDao.getById(staffEntity.getStoreId());
            if(storeEntity==null){
                return;
            }
            feedbackDB.setStoreId(storeEntity.getStoreId());
            feedbackDB.setStoreName(storeEntity.getName());
        }


    }

    /**
     * 根据实体更新
     * @param feedback
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public  ResponseEntity<String> update(FeedbackEntity feedback){
        logger.info("  FeedbackService   update  feedback = {}",feedback);
        int n = feedbackDao.update(feedback);
        logger.info("  FeedbackService   update  n = {}",n);

        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:54:54.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = feedbackDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

