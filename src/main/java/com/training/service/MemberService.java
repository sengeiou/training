package com.training.service;

import com.alibaba.fastjson.JSONObject;
import com.training.common.*;
import com.training.dao.MemberDao;
import com.training.domain.Lesson;
import com.training.domain.Member;
import com.training.domain.Training;
import com.training.entity.MemberEntity;
import com.training.entity.MemberQuery;
import com.training.domain.User;
import com.training.util.IDUtils;
import com.training.util.JwtUtil;
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
 * member 核心业务操作类
 * Created by huai23 on 2018-05-26 13:33:17.
 */ 
@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private JwtUtil jwt;

    /**
     * 新增实体
     * @param member
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public ResponseEntity<String> add(MemberEntity member){
        User user = RequestContextHelper.getUser();
        member.setMemberId(IDUtils.getId());
        int n = memberDao.add(member);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public Page<Member> find(MemberQuery query , PageRequest page){
        List<MemberEntity> memberList = memberDao.find(query,page);
        List<Member> data = new ArrayList<>();
        for (MemberEntity trainingEntity : memberList){
            Member member = transferMember(trainingEntity);
            member.setCardType("私教次卡,团体月卡");
            data.add(member);
        }
        Long count = memberDao.count(query);
        Page<Member> returnPage = new Page<>();
        returnPage.setContent(data);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    private Member transferMember(MemberEntity memberEntity) {
        if(memberEntity==null){
            return null;
        }
        Member member = new Member();
        BeanUtils.copyProperties(memberEntity,member);
        return member;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public Long count(MemberQuery query){
        Long count = memberDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public MemberEntity getById(String id){
        MemberEntity memberDB = memberDao.getById(id);
        return memberDB;
    }

    /**
     * 根据ID查询实体
     * @param openId
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public MemberEntity getByOpenId(String openId){
        if(StringUtils.isEmpty(openId)){
            return null;
        }
        MemberEntity memberDB = memberDao.getByOpenId(openId);
        return memberDB;
    }

    /**
     * 根据ID查询实体
     * @param phone
     * Created by huai23 on 2018-05-26 13:33:17.
     */
    public MemberEntity getByPhone(String phone){
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        MemberEntity memberDB = memberDao.getByPhone(phone);
        return memberDB;
    }

    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public  ResponseEntity<String> update(MemberEntity member){
        int n = memberDao.update(member);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:33:17.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    /**
     * 根据手机号码发送验证码
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    public ResponseEntity<String> sendCode(Member member) {
        Member memberToken = RequestContextHelper.getMember();
        logger.info(" memberRestController  sendCode  getOpenId = {}",memberToken.getOpenId());
        logger.info(" memberRestController  sendCode  getPhone = {}",member.getPhone());
        if(StringUtils.isEmpty(member.getPhone())||member.getPhone().length()!=11){
            return ResponseUtil.exception("手机号码输入有误!");
        }

        return ResponseUtil.success("发送验证码成功");
    }


    /**
     * 根据手机号码绑定会员
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    public ResponseEntity<String> bind(Member member) {
        JSONObject jo = new JSONObject();
        if(StringUtils.isEmpty(member.getCode()) || !member.getCode().equals("1234")){
            return ResponseUtil.exception("手机验证码错误!");
        }
        Member memberRequest = RequestContextHelper.getMember();
        String openId = memberRequest.getOpenId();
        logger.info("  bind  getOpenId = {} , getPhone = {} , code = {} ",openId,member.getPhone(),member.getCode());
        MemberEntity memberEntity = getByOpenId(openId);
        if(memberEntity==null){
            memberEntity = getByPhone(member.getPhone());
            if(memberEntity==null){
                memberEntity = new MemberEntity();
                memberEntity.setMemberId(IDUtils.getId());
                memberEntity.setName(member.getPhone());
                memberEntity.setPhone(member.getPhone());
                memberEntity.setOpenId(openId);
                memberEntity.setType("M");
                memberEntity.setOrigin("自动生成");
                int n = memberDao.add(memberEntity);
                if(n!=1){
                    return ResponseUtil.exception("创建新会员失败!");
                }
                memberEntity = this.getById(memberEntity.getMemberId());
            }else{
                memberEntity.setOpenId(openId);
                int n = memberDao.bind(memberEntity);
            }
        }
        Member memberResult = new Member();
        memberResult = new Member();
        memberResult.setMemberId(memberEntity.getMemberId());
        memberResult.setType(memberEntity.getType());
        jo.put("member", memberEntity);
        String subject = JwtUtil.generalSubject(memberRequest.getOpenId(),memberResult);
        try {
            String token = jwt.createJWT(Const.JWT_ID, subject, Const.JWT_TTL);
            jo.put("token", token);
            logger.info("  bind  token = {} ",token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.success(jo);
//        return ResponseUtil.exception("手机验证码错误!");
    }

    public ResponseEntity<String> getValidLessonType(String memberId) {
        if(StringUtils.isEmpty(memberId)){
            return ResponseUtil.exception("会员信息查询异常");
        }
        if(!memberId.equals("1")){
            return ResponseUtil.exception("无可用课时,请先购卡!");
        }
        List<Lesson>  types = new ArrayList<>();
        Lesson p = new Lesson();
        p.setType("P");
        p.setCoachId("1");
        p.setTitle("私教课");
        p.setCoachName("张三");

        types.add(p);

        Lesson t = new Lesson();
        t.setType("T");
        t.setCoachId("1");
        t.setTitle("团体课");
        t.setCoachName("张三");

        types.add(t);

        Lesson s1 = new Lesson();
        s1.setType("S1");
        s1.setCoachId("1");
        s1.setTitle("肌肉强化");
        s1.setCoachName("张三");
        types.add(s1);

        Lesson s2 = new Lesson();
        s2.setType("S2");
        s2.setCoachId("1");
        s2.setTitle("瘦身训练");
        s2.setCoachName("张三");

        types.add(s2);

        Lesson s3 = new Lesson();
        s3.setType("S3");
        s3.setCoachId("1");
        s3.setTitle("产后恢复");
        s3.setCoachName("张三");

        types.add(s3);

        return ResponseUtil.success(types);
    }

}

