package com.training.service;

import com.training.dao.*;
import com.training.domain.Member;
import com.training.domain.MemberCoupon;
import com.training.domain.Staff;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.IDUtils;
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
 * member_coupon 核心业务操作类
 * Created by huai23 on 2018-06-30 10:02:47.
 */ 
@Service
public class MemberCouponService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCouponDao memberCouponDao;

    /**
     * 新增实体
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public ResponseEntity<String> add(MemberCouponEntity memberCoupon){
        if(memberCoupon==null|| StringUtils.isEmpty(memberCoupon.getMemberId())|| StringUtils.isEmpty(memberCoupon.getStartDate())|| StringUtils.isEmpty(memberCoupon.getEndDate())){
            return ResponseUtil.exception("发放优惠券异常");
        }
        Staff staff = RequestContextHelper.getStaff();
        if(staff!=null){
            memberCoupon.setUseStaffId(staff.getStaffId());
        }
        memberCoupon.setOrigin("manual");
        int total = 0;
        List<String> array = new ArrayList();
        String[] ids = memberCoupon.getMemberId().split(",");
        for (int i = 0; i < ids.length; i++) {
            if(StringUtils.isNotEmpty(ids[i])){
                array.add(ids[i]);
            }
        }
        for (String memberId : array){
            Integer couponId = 0;
            MemberCouponEntity memberCouponEntity = null;
            int times = 0;
            do{
                couponId = IDUtils.getCouponId();
                memberCouponEntity = memberCouponDao.getById(""+couponId);
                times++;
            }while(memberCouponEntity != null && times<100);
            memberCoupon.setMemberId(memberId);
            int n = memberCouponDao.add(memberCoupon);
            total = total + n;
        }
        if(total>0){
            return ResponseUtil.success("发放成功,共发放"+total+"张优惠券");
        }
        return ResponseUtil.exception("发放失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public Page<MemberCouponEntity> find(MemberCouponQuery query , PageRequest page){
        List<MemberCouponEntity> memberCouponList = memberCouponDao.find(query,page);
        Long count = memberCouponDao.count(query);
        Page<MemberCouponEntity> returnPage = new Page<>();
        returnPage.setContent(memberCouponList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public Long count(MemberCouponQuery query){
        Long count = memberCouponDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public MemberCoupon getById(String id){
        MemberCouponEntity memberCouponDB = memberCouponDao.getById(id);
        MemberCoupon memberCoupon = transferCoupon(memberCouponDB);
        return memberCoupon;
    }

    private MemberCoupon transferCoupon(MemberCouponEntity memberCouponDB) {
        if(memberCouponDB==null){
            return null;
        }
        MemberCoupon memberCoupon = new MemberCoupon();
        BeanUtils.copyProperties(memberCouponDB,memberCoupon);
        MemberEntity memberEntity = memberService.getById(memberCouponDB.getMemberId());
        Member member = memberService.transferMember(memberEntity);
        memberCoupon.setMember(member);
        if(member!=null){
            memberCoupon.setMemberName(member.getName());
        }
        return memberCoupon;
    }

    /**
     * 根据实体更新
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public  ResponseEntity<String> update(MemberCouponEntity memberCoupon){
        int n = memberCouponDao.update(memberCoupon);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = memberCouponDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    /**
     * 核销优惠券
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */
    public ResponseEntity<String> useCoupon(MemberCouponEntity memberCoupon){
        if(memberCoupon==null||memberCoupon.getCouponId()==null){
            return ResponseUtil.exception("核销优惠券异常");
        }
        Staff staff = RequestContextHelper.getStaff();
        if(staff!=null){
            memberCoupon.setUseStaffId(staff.getStaffId());
        }
        memberCoupon.setUseDate(ut.currentTime());
        int n = memberCouponDao.useCoupon(memberCoupon);
        if(n==1){
            return ResponseUtil.success("核销成功");
        }
        return ResponseUtil.exception("核销失败");
    }

    public ResponseEntity<String> couponList(MemberCouponQuery query, PageRequest page) {
        logger.info("  couponList  query = {} , pageRequest = {} ",query,page);
        page.setPageSize(1000);
        List<MemberCouponEntity> memberCouponList = memberCouponDao.find(query,page);
        return ResponseUtil.success(memberCouponList);
    }

}
