package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * member_card 数据库操作类
 * Created by huai23 on 2018-05-26 13:53:17.
 */ 
@Service
public class MemberCardDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCardRepository memberCardRepository;

    /**
     * 新增实体
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public int add(MemberCardEntity memberCard){
        memberCard.setCardNo(null);
        int n = memberCardRepository.add(memberCard);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public List<MemberCardEntity> find(MemberCardQuery query , PageRequest page){
        List<MemberCardEntity> memberCardList = memberCardRepository.find(query,page);
        return memberCardList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public Long count(MemberCardQuery query){
        Long n = memberCardRepository.count(query);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    public List<MemberCardEntity> findPro(MemberCardQuery query , PageRequest page){
        List<MemberCardEntity> memberCardList = memberCardRepository.findPro(query,page);
        return memberCardList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    public Long countPro(MemberCardQuery query){
        Long n = memberCardRepository.countPro(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public MemberCardEntity getById(String id){
        MemberCardEntity memberCardDB = memberCardRepository.getById(id);
        return memberCardDB;
    }

    /**
     * 根据实体更新
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public int update(MemberCardEntity memberCard){
        int n = memberCardRepository.update(memberCard);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    public int delete(String id){
        int n = memberCardRepository.delete(id);
        return n;
    }

    public int reduceCount(String cardNo) {
        MemberCardEntity memberCardDB = memberCardRepository.getById(cardNo);
        int count = memberCardDB.getCount()-1;
        if(count<0){
            return 0;
        }
        int n = memberCardRepository.updateCount(count,memberCardDB.getCount(),cardNo);
        return n;
    }

    public int addCount(Integer newCount ,Integer oldCount ,String cardNo) {
        int n = memberCardRepository.updateCount(newCount,oldCount,cardNo);
        return n;
    }

    public int freeDelay(MemberCardEntity card) {
        int n = memberCardRepository.delay(card);
        return n;
    }

    public int payDelay(MemberCardEntity card) {
        int n = memberCardRepository.delay(card);
        return n;
    }

    public int advanceCard(MemberCardEntity card) {
        int n = memberCardRepository.advanceCard(card);
        return n;
    }

    public MemberCardEntity getByContractId(String contractId) {
        if(StringUtils.isEmpty(contractId)){
            return null;
        }
        List<MemberCardEntity> memberCardDBs = memberCardRepository.getByContractId(contractId);
        if(CollectionUtils.isNotEmpty(memberCardDBs)){
            return memberCardDBs.get(0);
        }
        return null;
    }
}

