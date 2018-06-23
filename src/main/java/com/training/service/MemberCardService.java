package com.training.service;

import com.training.dao.*;
import com.training.domain.MemberCard;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.ut;
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
    private CardDao cardDao;

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
    public Page<MemberCard> find(MemberCardQuery query , PageRequest page){
        List<MemberCardEntity> memberCardList = memberCardDao.find(query,page);
        List<MemberCard> content = new ArrayList<>();
        for(MemberCardEntity memberCardEntity : memberCardList){
            MemberCard memberCard = transfer(memberCardEntity);
            content.add(memberCard);
        }
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
        memberCard.setCardName(CardTypeEnum.getEnumByKey(memberCardEntity.getType()).getDesc());
        memberCard.setMemberName(memberEntity.getName());
        StoreEntity storeEntity = storeDao.getById(memberCard.getStoreId());
        if(storeEntity!=null){
            memberCard.setStoreName(storeEntity.getName());
        }else{
            memberCard.setStoreName("");
        }
        String cardType = "";
        if(CardTypeEnum.getEnumByKey(memberCard.getType())!=null){
            cardType = CardTypeEnum.getEnumByKey(memberCard.getType()).getDesc();
        }
        memberCard.setCardType(cardType);
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
}

