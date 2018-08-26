package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * member_coupon 数据库操作类
 * Created by huai23 on 2018-06-30 11:02:43.
 */ 
@Mapper
public interface MemberCouponRepository {

    @Insert("<script> INSERT INTO member_coupon ( " +
                " <if test=\"memberCoupon.couponId != null\"> coupon_id, </if>" +
                " <if test=\"memberCoupon.memberId != null\"> member_id, </if>" +
                " <if test=\"memberCoupon.storeId != null\"> store_id, </if>" +
                " <if test=\"memberCoupon.type != null\"> type, </if>" +
                " <if test=\"memberCoupon.title != null\"> title, </if>" +
                " <if test=\"memberCoupon.discount != null\"> discount, </if>" +
                " <if test=\"memberCoupon.total != null\"> total, </if>" +
                " <if test=\"memberCoupon.reduction != null\"> reduction, </if>" +
                " <if test=\"memberCoupon.startDate != null\"> start_date, </if>" +
                " <if test=\"memberCoupon.endDate != null\"> end_date, </if>" +
                " <if test=\"memberCoupon.content != null\"> content, </if>" +
                " <if test=\"memberCoupon.origin != null\"> origin, </if>" +
                " <if test=\"memberCoupon.feature != null\"> feature, </if>" +
                " <if test=\"memberCoupon.remark != null\"> remark, </if>" +
                " <if test=\"memberCoupon.status != null\"> status, </if>" +
                " <if test=\"memberCoupon.useDate != null\"> use_date, </if>" +
                " <if test=\"memberCoupon.useStaffId != null\"> use_staff_id, </if>" +
                " <if test=\"memberCoupon.creator != null\"> creator, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"memberCoupon.couponId != null\"> #{memberCoupon.couponId}, </if>" +
                " <if test=\"memberCoupon.memberId != null\"> #{memberCoupon.memberId}, </if>" +
                " <if test=\"memberCoupon.storeId != null\"> #{memberCoupon.storeId}, </if>" +
                " <if test=\"memberCoupon.type != null\"> #{memberCoupon.type}, </if>" +
                " <if test=\"memberCoupon.title != null\"> #{memberCoupon.title}, </if>" +
                " <if test=\"memberCoupon.discount != null\"> #{memberCoupon.discount}, </if>" +
                " <if test=\"memberCoupon.total != null\"> #{memberCoupon.total}, </if>" +
                " <if test=\"memberCoupon.reduction != null\"> #{memberCoupon.reduction}, </if>" +
                " <if test=\"memberCoupon.startDate != null\"> #{memberCoupon.startDate}, </if>" +
                " <if test=\"memberCoupon.endDate != null\"> #{memberCoupon.endDate}, </if>" +
                " <if test=\"memberCoupon.content != null\"> #{memberCoupon.content}, </if>" +
                " <if test=\"memberCoupon.origin != null\"> #{memberCoupon.origin}, </if>" +
                " <if test=\"memberCoupon.feature != null\"> #{memberCoupon.feature}, </if>" +
                " <if test=\"memberCoupon.remark != null\"> #{memberCoupon.remark}, </if>" +
                " <if test=\"memberCoupon.status != null\"> #{memberCoupon.status}, </if>" +
                " <if test=\"memberCoupon.useDate != null\"> #{memberCoupon.useDate}, </if>" +
                " <if test=\"memberCoupon.useStaffId != null\"> #{memberCoupon.useStaffId}, </if>" +
                " <if test=\"memberCoupon.creator != null\"> #{memberCoupon.creator}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("memberCoupon") MemberCouponEntity memberCoupon);

    @Select("<script> SELECT coupon_id,member_id,store_id,type,title,discount,total,reduction,start_date,end_date,content,origin,feature,remark,status,use_date,use_staff_id,creator,created,modified " +
            " FROM member_coupon " +
            " WHERE 1 = 1 " +
            " <if test=\"query.couponId != null\"> AND coupon_id = #{query.couponId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.discount != null\"> AND discount = #{query.discount} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.reduction != null\"> AND reduction = #{query.reduction} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.origin != null\"> AND origin = #{query.origin} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.useStartDate != null\"> AND use_date &lt;= #{query.useStartDate} </if>" +
            " <if test=\"query.useEndDate != null\"> AND use_date &gt;= #{query.useEndDate} </if>" +
            " <if test=\"query.useStaffId != null\"> AND use_staff_id = #{query.useStaffId} </if>" +
            " <if test=\"query.useStaffName != null\"> AND use_staff_id in ( select staff_id from staff where custname like CONCAT('%',#{query.useStaffName},'%') )  </if>" +
            " <if test=\"query.creator != null\"> AND creator in ( select staff_id from staff where custname like CONCAT('%',#{query.creator},'%') )  </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d') &lt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d') &gt;= #{query.endDate} </if>" +
            " order by coupon_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberCouponEntity> find(@Param("query") MemberCouponQuery memberCoupon , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member_coupon " +
            " WHERE 1 = 1 " +
            " <if test=\"query.couponId != null\"> AND coupon_id = #{query.couponId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.discount != null\"> AND discount = #{query.discount} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.reduction != null\"> AND reduction = #{query.reduction} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.origin != null\"> AND origin = #{query.origin} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.useStartDate != null\"> AND use_date &lt;= #{query.useStartDate} </if>" +
            " <if test=\"query.useEndDate != null\"> AND use_date &gt;= #{query.useEndDate} </if>" +
            " <if test=\"query.useStaffId != null\"> AND use_staff_id = #{query.useStaffId} </if>" +
            " <if test=\"query.useStaffName != null\"> AND use_staff_id in ( select staff_id from staff where custname like CONCAT('%',#{query.useStaffName},'%') )  </if>" +
            " <if test=\"query.creator != null\"> AND creator in ( select staff_id from staff where custname like CONCAT('%',#{query.creator},'%') )  </if>" +
            " <if test=\"query.startDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d') &lt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND DATE_FORMAT(created,'%Y-%m-%d') &gt;= #{query.endDate} </if>" +
            "</script>")
    Long count(@Param("query") MemberCouponQuery memberCoupon);

    @Select("<script> SELECT coupon_id,member_id,store_id,type,title,discount,total,reduction,start_date,end_date,content,origin,feature,remark,status,use_date,use_staff_id,creator,created,modified " +
            " FROM member_coupon " +
            " WHERE coupon_id = #{id} " +
            "</script>")
    MemberCouponEntity getById(@Param("id") String id);

    @Update("<script> UPDATE member_coupon SET " +
                " <if test=\"memberCoupon.memberId != null\"> member_id = #{memberCoupon.memberId} , </if>" +
                " <if test=\"memberCoupon.storeId != null\"> store_id = #{memberCoupon.storeId} , </if>" +
                " <if test=\"memberCoupon.type != null\"> type = #{memberCoupon.type} , </if>" +
                " <if test=\"memberCoupon.title != null\"> title = #{memberCoupon.title} , </if>" +
                " <if test=\"memberCoupon.discount != null\"> discount = #{memberCoupon.discount} , </if>" +
                " <if test=\"memberCoupon.total != null\"> total = #{memberCoupon.total} , </if>" +
                " <if test=\"memberCoupon.reduction != null\"> reduction = #{memberCoupon.reduction} , </if>" +
                " <if test=\"memberCoupon.startDate != null\"> start_date = #{memberCoupon.startDate} , </if>" +
                " <if test=\"memberCoupon.endDate != null\"> end_date = #{memberCoupon.endDate} , </if>" +
                " <if test=\"memberCoupon.content != null\"> content = #{memberCoupon.content} , </if>" +
                " <if test=\"memberCoupon.feature != null\"> feature = #{memberCoupon.feature} , </if>" +
                " <if test=\"memberCoupon.remark != null\"> remark = #{memberCoupon.remark} , </if>" +
                " <if test=\"memberCoupon.status != null\"> status = #{memberCoupon.status} , </if>" +
                " <if test=\"memberCoupon.useDate != null\"> use_date = #{memberCoupon.useDate} , </if>" +
                " <if test=\"memberCoupon.useStaffId != null\"> use_staff_id = #{memberCoupon.useStaffId} , </if>" +
                " <if test=\"memberCoupon.creator != null\"> creator = #{memberCoupon.creator} , </if>" +
                " modified = now() " +
            " WHERE coupon_id = #{memberCoupon.couponId} " +
            "</script>")
    int update(@Param("memberCoupon") MemberCouponEntity memberCoupon);

    @Update("<script> DELETE  FROM member_coupon " +
            " WHERE coupon_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);

    @Update("<script> update member_coupon set  status = 1 , " +
            " <if test=\"memberCoupon.useDate != null\"> use_date = #{memberCoupon.useDate} , </if>" +
            " <if test=\"memberCoupon.useStaffId != null\"> use_staff_id = #{memberCoupon.useStaffId} , </if>" +
            " modified = now() " +
            " WHERE coupon_id = #{memberCoupon.couponId} " +
            "</script>")
    int useCoupon(@Param("memberCoupon") MemberCouponEntity memberCoupon);

    @Select("<script> SELECT coupon_id,member_id,store_id,type,title,discount,total,reduction,start_date,end_date,content,origin,feature,remark,status,use_date,use_staff_id,creator,created,modified " +
            " FROM member_coupon " +
            " WHERE member_id = #{memberId} " +
            "</script>")
    List<MemberCouponEntity> getByMemberId(@Param("memberId") String memberId);

}

