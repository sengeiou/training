package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * member_card 数据库操作类
 * Created by huai23 on 2018-05-26 13:53:17.
 */ 
@Mapper
public interface MemberCardRepository {

    @Insert("<script> INSERT INTO member_card ( " +
                " <if test=\"memberCard.cardNo != null\"> card_no, </if>" +
                " <if test=\"memberCard.cardId != null\"> card_id, </if>" +
                " <if test=\"memberCard.memberId != null\"> member_id, </if>" +
                " <if test=\"memberCard.coachId != null\"> coach_id, </if>" +
                " <if test=\"memberCard.storeId != null\"> store_id, </if>" +
                " <if test=\"memberCard.type != null\"> type, </if>" +
                " <if test=\"memberCard.money != null\"> money, </if>" +
                " <if test=\"memberCard.count != null\"> count, </if>" +
                " <if test=\"memberCard.total != null\"> total, </if>" +
                " <if test=\"memberCard.days != null\"> days, </if>" +
                " <if test=\"memberCard.startDate != null\"> start_date, </if>" +
                " <if test=\"memberCard.endDate != null\"> end_date, </if>" +
                " <if test=\"memberCard.delay != null\"> delay, </if>" +
                " <if test=\"memberCard.feature != null\"> feature, </if>" +
                " <if test=\"memberCard.remark != null\"> remark, </if>" +
                " <if test=\"memberCard.auditId != null\"> audit_id, </if>" +
                " <if test=\"memberCard.contractId != null\"> contract_id, </if>" +
                " <if test=\"memberCard.status != null\"> status, </if>" +
                " <if test=\"memberCard.creater != null\"> creater, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"memberCard.cardNo != null\"> #{memberCard.cardNo}, </if>" +
                " <if test=\"memberCard.cardId != null\"> #{memberCard.cardId}, </if>" +
                " <if test=\"memberCard.memberId != null\"> #{memberCard.memberId}, </if>" +
                " <if test=\"memberCard.coachId != null\"> #{memberCard.coachId}, </if>" +
                " <if test=\"memberCard.storeId != null\"> #{memberCard.storeId}, </if>" +
                " <if test=\"memberCard.type != null\"> #{memberCard.type}, </if>" +
                " <if test=\"memberCard.money != null\"> #{memberCard.money}, </if>" +
                " <if test=\"memberCard.count != null\"> #{memberCard.count}, </if>" +
                " <if test=\"memberCard.total != null\"> #{memberCard.total}, </if>" +
                " <if test=\"memberCard.days != null\"> #{memberCard.days}, </if>" +
                " <if test=\"memberCard.startDate != null\"> #{memberCard.startDate}, </if>" +
                " <if test=\"memberCard.endDate != null\"> #{memberCard.endDate}, </if>" +
                " <if test=\"memberCard.delay != null\"> #{memberCard.delay}, </if>" +
                " <if test=\"memberCard.feature != null\"> #{memberCard.feature}, </if>" +
                " <if test=\"memberCard.remark != null\"> #{memberCard.remark}, </if>" +
                " <if test=\"memberCard.auditId != null\"> #{memberCard.auditId}, </if>" +
                " <if test=\"memberCard.contractId != null\"> #{memberCard.contractId}, </if>" +
                " <if test=\"memberCard.status != null\"> #{memberCard.status}, </if>" +
                " <if test=\"memberCard.creater != null\"> #{memberCard.creater}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("memberCard") MemberCardEntity memberCard);

    @Select("<script> SELECT card_no,card_id,member_id,coach_id,store_id,type,money,count,total,days,start_date,end_date,delay,feature,remark,audit_id,contract_id,status,creater,created,modified " +
            " FROM member_card " +
            " WHERE 1 = 1 " +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.cardId != null\"> AND card_id = #{query.cardId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.money != null\"> AND money = #{query.money} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.days != null\"> AND days = #{query.days} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date &lt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date &gt;= #{query.endDate} </if>" +
            " <if test=\"query.delay != null\"> AND delay = #{query.delay} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.auditId != null\"> AND audit_id = #{query.auditId} </if>" +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            " order by card_no asc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberCardEntity> find(@Param("query") MemberCardQuery memberCard , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member_card " +
            " WHERE 1 = 1 " +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.cardId != null\"> AND card_id = #{query.cardId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.money != null\"> AND money = #{query.money} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.days != null\"> AND days = #{query.days} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date &lt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date &gt;= #{query.endDate} </if>" +
            " <if test=\"query.delay != null\"> AND delay = #{query.delay} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.auditId != null\"> AND audit_id = #{query.auditId} </if>" +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            "</script>")
    Long count(@Param("query") MemberCardQuery memberCard);

    @Select("<script> SELECT card_no,card_id,member_id,coach_id,store_id,type,money,count,total,days,start_date,end_date,delay,feature,remark,audit_id,contract_id,status,creater,created,modified " +
            " FROM member_card " +
            " WHERE card_no = #{id} " +
            "</script>")
    MemberCardEntity getById(@Param("id") String id);

    @Update("<script> UPDATE member_card SET " +
                " <if test=\"memberCard.cardId != null\"> card_id = #{memberCard.cardId} , </if>" +
                " <if test=\"memberCard.memberId != null\"> member_id = #{memberCard.memberId} , </if>" +
                " <if test=\"memberCard.coachId != null\"> coach_id = #{memberCard.coachId} , </if>" +
                " <if test=\"memberCard.storeId != null\"> store_id = #{memberCard.storeId} , </if>" +
                " <if test=\"memberCard.type != null\"> type = #{memberCard.type} , </if>" +
                " <if test=\"memberCard.money != null\"> money = #{memberCard.money} , </if>" +
                " <if test=\"memberCard.count != null\"> count = #{memberCard.count} , </if>" +
                " <if test=\"memberCard.total != null\"> total = #{memberCard.total} , </if>" +
                " <if test=\"memberCard.days != null\"> days = #{memberCard.days} , </if>" +
                " <if test=\"memberCard.startDate != null\"> start_date = #{memberCard.startDate} , </if>" +
                " <if test=\"memberCard.endDate != null\"> end_date = #{memberCard.endDate} , </if>" +
                " <if test=\"memberCard.delay != null\"> delay = #{memberCard.delay} , </if>" +
                " <if test=\"memberCard.feature != null\"> feature = #{memberCard.feature} , </if>" +
                " <if test=\"memberCard.remark != null\"> remark = #{memberCard.remark} , </if>" +
                " <if test=\"memberCard.auditId != null\"> audit_id = #{memberCard.auditId} , </if>" +
                " <if test=\"memberCard.contractId != null\"> contract_id = #{memberCard.contractId} , </if>" +
                " <if test=\"memberCard.status != null\"> status = #{memberCard.status} , </if>" +
                " <if test=\"memberCard.creater != null\"> creater = #{memberCard.creater} , </if>" +
                " modified = now() " +
            " WHERE card_no = #{memberCard.cardNo} " +
            "</script>")
    int update(@Param("memberCard") MemberCardEntity memberCard);

    @Update("<script> DELETE  FROM member_card " +
            " WHERE card_no = #{id} " +
            "</script>")
    int delete(@Param("id") String id);

    @Update("<script> UPDATE member_card SET count = #{memberCard.count} ,modified = now()  WHERE card_no = #{memberCard.cardNo} " +
            "</script>")
    int updateCount(@Param("memberCard") MemberCardEntity memberCard);

    @Update("<script> UPDATE member_card SET delay = delay + 1 , end_date = #{memberCard.endDate} ,modified = now()  WHERE card_no = #{memberCard.cardNo} " +
            "</script>")
    int delay(@Param("memberCard") MemberCardEntity memberCard);

    @Select("<script> SELECT card_no,card_id,member_id,coach_id,store_id,type,money,count,total,days,start_date,end_date,delay,feature,remark,audit_id,contract_id,status,creater,created,modified " +
            " FROM member_card " +
            " WHERE 1 = 1 " +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.cardId != null\"> AND card_id = #{query.cardId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.name != null || query.phone != null\"> AND member_id in ( select member_id from member where 1 = 1  " +
            "   <if test=\"query.name != null\"> AND name like CONCAT('%',#{query.name},'%')  </if>" +
            "   <if test=\"query.phone != null\"> AND phone like CONCAT('%',#{query.phone},'%')  </if>" +
            " ) </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.storeId != null\"> AND member_id  in ( SELECT a.member_id from member a , staff b where a.coach_staff_id = b.staff_id and b.store_id in ( ${query.storeId} )  )  </if>" +
            " <if test=\"query.saleStaffId != null\"> AND sale_staff_id = #{query.saleStaffId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.money != null\"> AND money = #{query.money} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.days != null\"> AND days = #{query.days} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date &lt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date &gt;= #{query.endDate} </if>" +
            " <if test=\"query.delay != null\"> AND delay = #{query.delay} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.auditId != null\"> AND audit_id = #{query.auditId} </if>" +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            " order by card_no desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberCardEntity> findPro(@Param("query") MemberCardQuery memberCard , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member_card " +
            " WHERE 1 = 1 " +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.cardId != null\"> AND card_id = #{query.cardId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.name != null || query.phone != null\"> AND member_id in ( select member_id from member where 1 = 1  " +
            "   <if test=\"query.name != null\"> AND name like CONCAT('%',#{query.name},'%')  </if>" +
            "   <if test=\"query.phone != null\"> AND phone like CONCAT('%',#{query.phone},'%')  </if>" +
            " ) </if>" +
            " <if test=\"query.coachId != null\"> AND coach_id = #{query.coachId} </if>" +
            " <if test=\"query.storeId != null\"> AND member_id  in ( SELECT a.member_id from member a , staff b where a.coach_staff_id = b.staff_id and b.store_id in ( ${query.storeId} )  )  </if>" +
            " <if test=\"query.saleStaffId != null\"> AND sale_staff_id = #{query.saleStaffId} </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.money != null\"> AND money = #{query.money} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.total != null\"> AND total = #{query.total} </if>" +
            " <if test=\"query.days != null\"> AND days = #{query.days} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date &lt;= #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date &gt;= #{query.endDate} </if>" +
            " <if test=\"query.delay != null\"> AND delay = #{query.delay} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.auditId != null\"> AND audit_id = #{query.auditId} </if>" +
            " <if test=\"query.contractId != null\"> AND contract_id = #{query.contractId} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            "</script>")
    Long countPro(@Param("query") MemberCardQuery memberCard);


    @Update("<script> UPDATE member_card SET start_date = #{memberCard.startDate} , end_date = #{memberCard.endDate} ,modified = now()  WHERE card_no = #{memberCard.cardNo} " +
            "</script>")
    int advanceCard(@Param("memberCard") MemberCardEntity memberCard);

    @Select("<script> SELECT card_no,card_id,member_id,coach_id,store_id,type,money,count,total,days,start_date,end_date,delay,feature,remark,audit_id,contract_id,status,creater,created,modified " +
            " FROM member_card " +
            " WHERE contract_id = #{contractId} " +
            "</script>")
    List<MemberCardEntity> getByContractId(@Param("contractId") String contractId);

}

