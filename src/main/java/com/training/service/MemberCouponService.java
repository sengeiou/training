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
    private MemberDao memberDao;

    @Autowired
    private MemberCouponDao memberCouponDao;

    @Autowired
    private StaffDao staffDao;

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
            memberCoupon.setCreator(staff.getStaffId());
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
            MemberEntity memberEntity = memberDao.getByPhone(memberId);
            if(memberEntity==null){
                continue;
            }
            memberId = memberEntity.getMemberId();
            Integer couponId = 0;
            MemberCouponEntity memberCouponEntity = null;
            do{
                couponId = IDUtils.getCouponId();
                memberCouponEntity = memberCouponDao.getById(""+couponId);
            }while(memberCouponEntity != null);
            memberCoupon.setCouponId(couponId);
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
     * 新增实体
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */
    public ResponseEntity<String> addOne(MemberCouponEntity memberCoupon){
        if(memberCoupon==null|| StringUtils.isEmpty(memberCoupon.getMemberId())|| StringUtils.isEmpty(memberCoupon.getStartDate())|| StringUtils.isEmpty(memberCoupon.getEndDate())){
            return ResponseUtil.exception("发放优惠券参数异常");
        }
        Staff staff = RequestContextHelper.getStaff();
        if(staff!=null){
            memberCoupon.setCreator(staff.getStaffId());
        }
        memberCoupon.setCouponId(getCouponId());
        if(StringUtils.isEmpty(memberCoupon.getOrigin())){
            memberCoupon.setOrigin("auto");
        }
        int n = memberCouponDao.add(memberCoupon);
        if(n>0){
            return ResponseUtil.success("发放成功");
        }
        return ResponseUtil.exception("发放失败");
    }


    public Integer getCouponId(){
        Integer couponId = 0;
        MemberCouponEntity memberCouponEntity = null;
        do{
            couponId = IDUtils.getCouponId();
            memberCouponEntity = memberCouponDao.getById(""+couponId);
        }while(memberCouponEntity != null);
        return couponId;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-06-30 10:02:47.
     */
    public Page<MemberCoupon> find(MemberCouponQuery query , PageRequest page){
        logger.info(" MemberCouponService query  = {}  ",query);
        List<MemberCouponEntity> memberCouponList = memberCouponDao.find(query,page);
        List<MemberCoupon> memberCoupons = new ArrayList<>();
        for (MemberCouponEntity memberCouponEntity : memberCouponList){
            MemberCoupon memberCoupon = transferCoupon(memberCouponEntity);
            logger.info(" memberCoupon  = {}  ",memberCoupon);
            memberCoupons.add(memberCoupon);
        }
        Long count = memberCouponDao.count(query);
        Page<MemberCoupon> returnPage = new Page<>();
        returnPage.setContent(memberCoupons);
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
        if(StringUtils.isNotEmpty(memberCoupon.getUseStaffId())){
            StaffEntity staffEntity = staffDao.getById(memberCoupon.getUseStaffId());
            memberCoupon.setUseStaffName(staffEntity.getCustname());
        }else{
            memberCoupon.setUseStaffName(" ");
        }
        if(StringUtils.isEmpty(memberCoupon.getUseDate())){
            memberCoupon.setUseDate(" ");
        }
        if(StringUtils.isNotEmpty(memberCoupon.getCreator())){
            StaffEntity staffEntity = staffDao.getById(memberCoupon.getCreator());
            memberCoupon.setCreator(staffEntity.getCustname());
        }else{
            memberCoupon.setCreator("-");
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
        MemberCouponEntity memberCouponEntity = memberCouponDao.getById(""+memberCoupon.getCouponId());
        if(ut.passDayByDate(memberCouponEntity.getStartDate(),ut.currentDate())<0){
            return ResponseUtil.exception("优惠券未生效，请稍后使用");
        }
        if(ut.passDayByDate(memberCouponEntity.getEndDate(),ut.currentDate())>0){
            return ResponseUtil.exception("优惠券已过期，无法使用");
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
        query.setStatus(0);
        List<MemberCouponEntity> memberCouponList = memberCouponDao.find(query,page);
        query.setStatus(1);
        List<MemberCouponEntity> usedMemberCouponList = memberCouponDao.find(query,page);
        memberCouponList.addAll(usedMemberCouponList);
        query.setStatus(-1);
        List<MemberCouponEntity> passMemberCouponList = memberCouponDao.find(query,page);
        memberCouponList.addAll(passMemberCouponList);
        return ResponseUtil.success(memberCouponList);
    }

    public Page<MemberCoupon> queryUseLog(MemberCouponQuery query, PageRequest page) {
        logger.info(" MemberCouponService memberCouponQuery  = {}  ",query);
        query.setStartDate(null);
        query.setEndDate(null);
        if(StringUtils.isNotEmpty(query.getUseStartDate())){
            query.setUseStartDate(query.getUseStartDate()+" 00:00:00");
        }
        if(StringUtils.isNotEmpty(query.getUseEndDate())){
            query.setUseEndDate(query.getUseEndDate()+" 23:59:59");
        }
        query.setStatus(1);
        List<MemberCouponEntity> memberCouponList = memberCouponDao.find(query,page);
        List<MemberCoupon> memberCoupons = new ArrayList<>();
        for (MemberCouponEntity memberCouponEntity : memberCouponList){
            MemberCoupon memberCoupon = transferCoupon(memberCouponEntity);
            logger.info(" memberCoupon  = {}  ",memberCoupon);
            memberCoupons.add(memberCoupon);
        }
        Long count = memberCouponDao.count(query);
        Page<MemberCoupon> returnPage = new Page<>();
        returnPage.setContent(memberCoupons);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

}

