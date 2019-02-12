package com.training.service;

import com.google.common.collect.Lists;
import com.training.dao.*;
import com.training.domain.Member;
import com.training.domain.MemberCard;
import com.training.domain.Staff;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * member_card 核心业务操作类
 * Created by huai23 on 2018-05-26 13:53:17.
 */ 
@Service
public class MemberCardService {

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
    private RoleDao roleDao;

    @Autowired
    private ContractDao contractDao;

    /**
     * 新增实体
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public ResponseEntity<String> add(MemberCardEntity memberCard){
        int n = memberCardDao.add(memberCard);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public Page<MemberCard> findPro(MemberCardQuery query , PageRequest page){
        Staff staffRequest = RequestContextHelper.getStaff();
        logger.info("  findPro  staffRequest = {} ", staffRequest);
        logger.info("  findPro  MemberCardQuery1 = {} ", query);

        StaffEntity staffDB = staffDao.getById(staffRequest.getStaffId());
        RoleEntity roleEntity = roleDao.getById(staffDB.getRoleId());

        if(StringUtils.isEmpty(query.getStoreId())){
            if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                query.setStoreId(roleEntity.getStoreData());
            }else{
                if(staffDB.getUsername().equals("admin")){

                }else {
                    query.setStoreId("123456789");
                }
            }
        }else{
            if(staffDB.getUsername().equals("admin")){

            }else {
                if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                    String[] storeIds = roleEntity.getStoreData().split(",");
                    List ids = Arrays.asList(storeIds);

                    if(ids.contains(query.getStoreId())){

                    }else{
                        query.setStoreId("123456789");
                    }

                }else {
                    query.setStoreId("123456789");
                }
            }

        }

        logger.info("  findPro  MemberCardQuery2 = {} ", query);
        List<MemberCardEntity> memberCardList = memberCardDao.findPro(query,page);
        List<MemberCard> content = new ArrayList<>();
        for(MemberCardEntity memberCardEntity : memberCardList){
            MemberCard memberCard = transfer(memberCardEntity);
            content.add(memberCard);
        }
        Long count = memberCardDao.countPro(query);
        Page<MemberCard> returnPage = new Page<>();
        returnPage.setContent(content);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    public Page<MemberCard> find(MemberCardQuery query , PageRequest page){
        List<MemberCardEntity> memberCardList = memberCardDao.find(query,page);
        List<MemberCard> content = new ArrayList<>();
        for(MemberCardEntity memberCardEntity : memberCardList){
            MemberCard memberCard = transfer(memberCardEntity);
            content.add(memberCard);
        }
        logger.info(" member_cardRestControllerfind  content = {}",content.size());

        Long count = memberCardDao.count(query);
        Page<MemberCard> returnPage = new Page<>();
        returnPage.setContent(content);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    public MemberCard transfer(MemberCardEntity memberCardEntity) {
        if(memberCardEntity==null){
            return null;
        }
//        CardEntity cardEntity = cardDao.getById(memberCardEntity.getCardId());
        MemberCard memberCard = new MemberCard();
        BeanUtils.copyProperties(memberCardEntity,memberCard);
        MemberEntity memberEntity = memberDao.getById(memberCardEntity.getMemberId());
        if(memberEntity==null){
            logger.error("  MemberCard  transfer  memberEntity = null, memberCardEntity = ", memberCardEntity);
            return null;
        }
        memberCard.setCardName(CardTypeEnum.getEnumByKey(memberCardEntity.getType()).getDesc());
        memberCard.setMemberName(memberEntity.getName());
        memberCard.setPhone(memberEntity.getPhone());

        StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
        if(staffEntity!=null){
            memberCard.setCoachName(staffEntity.getCustname());
        }

        StoreEntity storeEntity = storeDao.getById(memberCard.getStoreId());
        if(storeEntity!=null){
            memberCard.setStoreName(storeEntity.getName());
        }else{
            memberCard.setStoreName("");
        }

        StoreEntity memberStoreEntity = storeDao.getById(memberEntity.getStoreId());
        if(memberStoreEntity!=null){
            memberCard.setMemberStoreId(memberStoreEntity.getStoreId());
            memberCard.setMemberStoreName(memberStoreEntity.getName());
        }else{
            memberCard.setMemberStoreId("");
            memberCard.setMemberStoreName("");
        }

        String cardType = "";
        if(CardTypeEnum.getEnumByKey(memberCard.getType())!=null){
            cardType = CardTypeEnum.getEnumByKey(memberCard.getType()).getDesc();
        }


        double realFee = 0;
        double money = 0;
        try{
            money = Double.parseDouble(memberCardEntity.getMoney());
        }catch (Exception e){

        }
        if(memberCardEntity.getTotal()>0){
            realFee = money*memberCardEntity.getCount()/memberCardEntity.getTotal();
        }

        if(memberCard.getType().equals(CardTypeEnum.PM.getKey())||memberCard.getType().equals(CardTypeEnum.TM.getKey())) {
            int days = memberCardEntity.getDays();
            int total = ut.passDayByDate(memberCardEntity.getStartDate(),memberCardEntity.getEndDate())+1;
            int count = ut.passDayByDate(ut.currentDate(),memberCardEntity.getEndDate())+1;
            memberCard.setTotal(total);
            memberCard.setCount(count);
            memberCard.setDays(total);
            if(days>0){
                realFee = money*count/days;
            }
        }
        memberCard.setRealFee(ut.getDoubleString(realFee));
        memberCard.setCardType(cardType);
        memberCard.setCanDelay(0);
        memberCard.setDelayFee("0");
        memberCard.setDelayDays(60);
        if(memberCard.getType().equals(CardTypeEnum.PT.getKey())||memberCard.getType().equals(CardTypeEnum.TT.getKey())){
            if(ut.passDayByDate(memberCard.getEndDate(),ut.currentDate(7)) > 0 && ut.passDayByDate(memberCard.getEndDate(),ut.currentDate(-30)) <= 0){
                if(memberCard.getDelay()==0){
                    memberCard.setCanDelay(1);
                    memberCard.setDelayFee("0");
                }else if(memberCard.getDelay()>0){
                    if(StringUtils.isNotEmpty(memberCard.getMoney())){
                        if(money>0){
                            int count = memberCard.getCount();
                            int total = memberCard.getTotal();
                            double delayMoney = count*money/(total*10);
                            memberCard.setDelayFee(ut.getDoubleString(delayMoney));
                            memberCard.setCanDelay(2);
                        }
                    }
                }
            }
        }else{

        }
        if(StringUtils.isNotEmpty(memberCardEntity.getContractId())){
            List<ContractEntity> contractEntitieList = contractDao.getByContractId(memberCardEntity.getContractId());
            if(contractEntitieList.size()>0){
//                memberCard.setCoachName(contractEntitieList.get(0).getCoach());
                memberCard.setSaleStaffName(contractEntitieList.get(0).getSalesman());
            }else{
                memberCard.setCoachName(" ");
                memberCard.setSaleStaffName(" ");
            }
        }

        memberCard.setShowStatus(CardStatusEnum.getEnumByKey(memberCardEntity.getStatus()).getDesc());
        return memberCard;
    }


    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public Long count(MemberCardQuery query){
        Long count = memberCardDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public MemberCard getById(String id){
        MemberCardEntity memberCardDB = memberCardDao.getById(id);
        MemberCard memberCard = transfer(memberCardDB);
        return memberCard;
    }

    /**
     * 根据实体更新
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public  ResponseEntity<String> update(MemberCardEntity memberCard){
        int n = memberCardDao.update(memberCard);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberCardDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    /**
     * 分页查询
     * @param memberId
     * @param type
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    public MemberCardEntity getCurrentUseCard(String memberId , String type){
        MemberCardQuery query = new MemberCardQuery();
        query.setMemberId(memberId);
        PageRequest page = new PageRequest();
        page.setPageSize(100);
        List<MemberCardEntity> memberCardList = memberCardDao.find(query,page);
        MemberCardEntity memberCardEntity = null;
        if(type.equals("P")){
            for (MemberCardEntity memberCard:memberCardList){
                if(memberCard.getType().startsWith("P")||memberCard.getType().equals("TY")){
                    if(memberCard.getCount()>0){
                        memberCardEntity = memberCard;
                        break;
                    }
                }
            }
        }
        if(type.equals("T")){
            for (MemberCardEntity memberCard:memberCardList){
                if(memberCard.getType().startsWith("T")&&!memberCard.getType().equals("TY")){
                    int passDays = ut.passDayByDate(ut.currentDate(),memberCard.getEndDate());
                    if( passDays > 0){
                        memberCardEntity = memberCard;
                        break;
                    }
                }
            }
        }
        return memberCardEntity;
    }

    /**
     * 根据ID删除
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    public ResponseEntity<String> freeDelay(MemberCard memberCard){
        Member member = RequestContextHelper.getMember();
        logger.info(" =================    freeDelay  member = {}",member);
        logger.info(" =================    freeDelay  memberCard = {}",memberCard);
        if(StringUtils.isEmpty(memberCard.getCardNo())){
            return ResponseUtil.exception("卡号不能为空");
        }
        MemberCardEntity memberCardDB = memberCardDao.getById(memberCard.getCardNo());
        if(memberCardDB==null){
            return ResponseUtil.exception("无效卡号");
        }
        if(memberCardDB.getType().equals(CardTypeEnum.PT.getKey())||memberCardDB.getType().equals(CardTypeEnum.TT.getKey())){

        }else{
            return ResponseUtil.exception("此种课卡不能延期");
        }
        if(ut.passDayByDate(memberCardDB.getEndDate(),ut.currentDate(7)) > 0 && ut.passDayByDate(memberCardDB.getEndDate(),ut.currentDate(-30)) <= 0){

        }else{
            return ResponseUtil.exception("课卡到期前一周才可延期");
        }
        if(memberCardDB.getDelay()>0){
            return ResponseUtil.exception("课卡已经延期过,不能再次免费延期");
        }
        memberCardDB.setEndDate(ut.currentDate(memberCardDB.getEndDate(),60));
        int n = memberCardDao.freeDelay(memberCardDB);
        if(n>0){
            return ResponseUtil.success("免费延期成功");
        }
        return ResponseUtil.exception("免费延期失败");
    }

    /**
     * 根据cardNo延期
     * @param cardNo
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    public ResponseEntity<String> payDelay(String cardNo){
        Member member = RequestContextHelper.getMember();
        logger.info(" =================    payDelay  cardNo = {}",cardNo);
        if(StringUtils.isEmpty(cardNo)){
            return ResponseUtil.exception("卡号不能为空");
        }
        MemberCardEntity memberCardDB = memberCardDao.getById(cardNo);
        if(memberCardDB==null){
            return ResponseUtil.exception("无效卡号");
        }
        memberCardDB.setEndDate(ut.currentDate(memberCardDB.getEndDate(),60));
        int n = memberCardDao.payDelay(memberCardDB);
        if(n>0){
            return ResponseUtil.success("免费延期成功");
        }
        return ResponseUtil.exception("免费延期失败");
    }


    /**
     * 根据ID提前开卡
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    public ResponseEntity<String> advanceCard(MemberCard memberCard){
        Member member = RequestContextHelper.getMember();
        logger.info(" =================    advanceCard  member = {}",member);
        logger.info(" =================    advanceCard  memberCard = {}",memberCard);
        if(StringUtils.isEmpty(memberCard.getCardNo())){
            return ResponseUtil.exception("卡号不能为空");
        }
        MemberCardEntity memberCardDB = memberCardDao.getById(memberCard.getCardNo());
        if(memberCardDB==null){
            return ResponseUtil.exception("无效卡号");
        }
        int passdays = ut.passDayByDate(ut.currentDate(),memberCardDB.getStartDate());
        if(passdays<=0){
            return ResponseUtil.exception("此卡已过生效日期，无需提前开卡");
        }
        String endDate = ut.currentDate(memberCardDB.getEndDate(),(0-passdays));
        memberCardDB.setStartDate(ut.currentDate());
        memberCardDB.setEndDate(endDate);
        int n = memberCardDao.advanceCard(memberCardDB);
        if(n>0){
            return ResponseUtil.success("提前开卡成功");
        }
        return ResponseUtil.exception("提前开卡失败");
    }

}

