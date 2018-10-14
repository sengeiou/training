package com.training.admin.service;

import com.training.common.CardTypeEnum;
import com.training.common.PageRequest;
import com.training.dao.*;
import com.training.domain.MemberCard;
import com.training.entity.MemberCardEntity;
import com.training.entity.MemberCardQuery;
import com.training.entity.MemberEntity;
import com.training.entity.StaffEntity;
import com.training.service.MemberService;
import com.training.service.SysLogService;
import com.training.util.IDUtils;
import com.training.util.ResponseUtil;
import com.training.util.ut;
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
    private SysLogService sysLogService;

    @Autowired
    private MemberService memberService;

    public ResponseEntity<String> giveCard(MemberCard card) {
        logger.info(" AdminService  giveCard  card = {}",card);
        if(StringUtils.isEmpty(card.getStaffId())){
            return ResponseUtil.exception("获取教练员工ID异常");
        }
        if(StringUtils.isEmpty(card.getStartDate())||StringUtils.isEmpty(card.getEndDate())){
            return ResponseUtil.exception("会员卡有效期不能为空！");
        }
        if(null==card.getTotal()){
            return ResponseUtil.exception("会员卡可用次数不能为空！");
        }
        StaffEntity staffEntity = staffDao.getById(card.getStaffId());
        if(staffEntity==null){
            return ResponseUtil.exception("获取教练员工信息异常");
        }
        if(StringUtils.isEmpty(staffEntity.getOpenId())){
            return ResponseUtil.exception("教练"+staffEntity.getCustname()+"未绑定微信账号，不能发卡");
        }
        MemberEntity coachEntity = memberService.getByOpenId(staffEntity.getOpenId());
        if(coachEntity==null){
            return ResponseUtil.exception("获取教练信息异常");
        }

        MemberCardQuery query = new MemberCardQuery();
        query.setType(CardTypeEnum.TY.getKey());
        query.setMemberId(card.getMemberId());
        PageRequest page = new PageRequest();

        List cards = memberCardDao.find(query,page);
        if(cards.size()>0){
            return ResponseUtil.exception("每个会员只能发一次体验卡！");

        }


        MemberCardEntity memberCardEntity = new MemberCardEntity();
        memberCardEntity.setCardNo(IDUtils.getId());
        memberCardEntity.setCardId("");
        memberCardEntity.setCoachId(coachEntity.getMemberId());
        memberCardEntity.setStoreId(coachEntity.getStoreId());
        memberCardEntity.setMemberId(card.getMemberId());
        memberCardEntity.setType(CardTypeEnum.TY.getKey());
        memberCardEntity.setCount(card.getTotal());
        memberCardEntity.setTotal(card.getTotal());
        memberCardEntity.setDays(ut.passDay(card.getStartDate(),card.getEndDate()));
        memberCardEntity.setMoney("0");
        memberCardEntity.setStartDate(card.getStartDate());
        memberCardEntity.setEndDate(card.getEndDate());
        int n = memberCardDao.add(memberCardEntity);
        if(n > 0){
            return ResponseUtil.success("发卡成功");
        }
        return ResponseUtil.exception("发卡失败");
    }
}

