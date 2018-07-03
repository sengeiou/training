package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * member 数据库操作类
 * Created by huai23 on 2018-06-06 20:15:26.
 */ 
@Mapper
public interface MemberRepository {

    @Insert("<script> INSERT INTO member ( " +
                " <if test=\"member.memberId != null\"> member_id, </if>" +
                " <if test=\"member.storeId != null\"> store_id, </if>" +
                " <if test=\"member.type != null\"> type, </if>" +
                " <if test=\"member.name != null\"> name, </if>" +
                " <if test=\"member.email != null\"> email, </if>" +
                " <if test=\"member.phone != null\"> phone, </if>" +
                " <if test=\"member.nickname != null\"> nickname, </if>" +
                " <if test=\"member.image != null\"> image, </if>" +
                " <if test=\"member.age != null\"> age, </if>" +
                " <if test=\"member.gender != null\"> gender, </if>" +
                " <if test=\"member.height != null\"> height, </if>" +
                " <if test=\"member.idCard != null\"> id_card, </if>" +
                " <if test=\"member.address != null\"> address, </if>" +
                " <if test=\"member.coachStaffId != null\"> coach_staff_id, </if>" +
                " <if test=\"member.cardNo != null\"> card_no, </if>" +
                " <if test=\"member.trainingHours != null\"> training_hours, </if>" +
                " <if test=\"member.openId != null\"> open_id, </if>" +
                " <if test=\"member.unionId != null\"> union_id, </if>" +
                " <if test=\"member.feature != null\"> feature, </if>" +
                " <if test=\"member.origin != null\"> origin, </if>" +
                " <if test=\"member.salesman != null\"> salesman, </if>" +
                " <if test=\"member.remark != null\"> remark, </if>" +
                " <if test=\"member.status != null\"> status, </if>" +
                " <if test=\"member.creater != null\"> creater, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"member.memberId != null\"> #{member.memberId}, </if>" +
                " <if test=\"member.storeId != null\"> #{member.storeId}, </if>" +
                " <if test=\"member.type != null\"> #{member.type}, </if>" +
                " <if test=\"member.name != null\"> #{member.name}, </if>" +
                " <if test=\"member.email != null\"> #{member.email}, </if>" +
                " <if test=\"member.phone != null\"> #{member.phone}, </if>" +
                " <if test=\"member.nickname != null\"> #{member.nickname}, </if>" +
                " <if test=\"member.image != null\"> #{member.image}, </if>" +
                " <if test=\"member.age != null\"> #{member.age}, </if>" +
                " <if test=\"member.gender != null\"> #{member.gender}, </if>" +
                " <if test=\"member.height != null\"> #{member.height}, </if>" +
                " <if test=\"member.idCard != null\"> #{member.idCard}, </if>" +
                " <if test=\"member.address != null\"> #{member.address}, </if>" +
                " <if test=\"member.coachStaffId != null\"> #{member.coachStaffId}, </if>" +
                " <if test=\"member.cardNo != null\"> #{member.cardNo}, </if>" +
                " <if test=\"member.trainingHours != null\"> #{member.trainingHours}, </if>" +
                " <if test=\"member.openId != null\"> #{member.openId}, </if>" +
                " <if test=\"member.unionId != null\"> #{member.unionId}, </if>" +
                " <if test=\"member.feature != null\"> #{member.feature}, </if>" +
                " <if test=\"member.origin != null\"> #{member.origin}, </if>" +
                " <if test=\"member.salesman != null\"> #{member.salesman}, </if>" +
                " <if test=\"member.remark != null\"> #{member.remark}, </if>" +
                " <if test=\"member.status != null\"> #{member.status}, </if>" +
                " <if test=\"member.creater != null\"> #{member.creater}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("member") MemberEntity member);

    @Select("<script> SELECT pk_id,member_id,store_id,type,name,email,phone,nickname,image,age,gender,height,id_card,address,coach_staff_id,card_no,training_hours,open_id,union_id,feature,origin,salesman,remark,status,creater,created,modified " +
            " FROM member " +
            " WHERE 1 = 1 " +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.storeId != null\"> AND coach_staff_id in ( select staff_id from staff where store_id in ( #{query.storeId} ) ) </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.name != null\"> AND name like CONCAT('%',#{query.name},'%')    </if>" +
            " <if test=\"query.email != null\"> AND email = #{query.email} </if>" +
            " <if test=\"query.phone != null\"> AND phone like CONCAT('%',#{query.phone},'%')  </if>" +
            " <if test=\"query.nickname != null\"> AND nickname = #{query.nickname} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.age != null\"> AND age = #{query.age} </if>" +
            " <if test=\"query.gender != null\"> AND gender = #{query.gender} </if>" +
            " <if test=\"query.height != null\"> AND height = #{query.height} </if>" +
            " <if test=\"query.idCard != null\"> AND id_card = #{query.idCard} </if>" +
            " <if test=\"query.address != null\"> AND address = #{query.address} </if>" +
            " <if test=\"query.coachStaffId != null\"> AND coach_staff_id = #{query.coachStaffId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.trainingHours != null\"> AND training_hours = #{query.trainingHours} </if>" +
            " <if test=\"query.openId != null\"> AND open_id = #{query.openId} </if>" +
            " <if test=\"query.unionId != null\"> AND union_id = #{query.unionId} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.origin != null\"> AND origin = #{query.origin} </if>" +
            " <if test=\"query.salesman != null\"> AND salesman like CONCAT('%',#{query.salesman},'%')  </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            " order by pk_id desc LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<MemberEntity> find(@Param("query") MemberQuery member , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM member " +
            " WHERE 1 = 1 " +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.storeId != null\"> AND coach_staff_id in ( select staff_id from staff where store_id in ( #{query.storeId} ) ) </if>" +
            " <if test=\"query.type != null\"> AND type = #{query.type} </if>" +
            " <if test=\"query.name != null\"> AND name like CONCAT('%',#{query.name},'%')  </if>" +
            " <if test=\"query.email != null\"> AND email = #{query.email} </if>" +
            " <if test=\"query.phone != null\"> AND phone like CONCAT('%',#{query.phone},'%')  </if>" +
            " <if test=\"query.nickname != null\"> AND nickname = #{query.nickname} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.age != null\"> AND age = #{query.age} </if>" +
            " <if test=\"query.gender != null\"> AND gender = #{query.gender} </if>" +
            " <if test=\"query.height != null\"> AND height = #{query.height} </if>" +
            " <if test=\"query.idCard != null\"> AND id_card = #{query.idCard} </if>" +
            " <if test=\"query.address != null\"> AND address = #{query.address} </if>" +
            " <if test=\"query.coachStaffId != null\"> AND coach_staff_id = #{query.coachStaffId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.trainingHours != null\"> AND training_hours = #{query.trainingHours} </if>" +
            " <if test=\"query.openId != null\"> AND open_id = #{query.openId} </if>" +
            " <if test=\"query.unionId != null\"> AND union_id = #{query.unionId} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.origin != null\"> AND origin = #{query.origin} </if>" +
            " <if test=\"query.salesman != null\"> AND salesman like CONCAT('%',#{query.salesman},'%')  </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.creater != null\"> AND creater = #{query.creater} </if>" +
            "</script>")
    Long count(@Param("query") MemberQuery member);

    @Select("<script> SELECT pk_id,member_id,store_id,type,name,email,phone,nickname,image,age,gender,height,id_card,address,coach_staff_id,card_no,training_hours,open_id,union_id,feature,origin,salesman,remark,status,creater,created,modified " +
            " FROM member " +
            " WHERE member_id = #{id} " +
            "</script>")
    MemberEntity getById(@Param("id") String id);

    @Select("<script> SELECT pk_id,member_id,store_id,type,name,email,phone,nickname,image,age,gender,height,id_card,address,coach_staff_id,card_no,training_hours,open_id,union_id,feature,origin,remark,status,creater,created,modified " +
            " FROM member " +
            " WHERE open_id = #{openId} " +
            "</script>")
    MemberEntity getByOpenId(@Param("openId") String openId);

    @Select("<script> SELECT pk_id,member_id,store_id,type,name,email,phone,nickname,image,age,gender,height,id_card,address,coach_staff_id,card_no,training_hours,open_id,union_id,feature,origin,remark,status,creater,created,modified " +
            " FROM member " +
            " WHERE phone = #{phone} limit 0 , 1  " +
            "</script>")
    MemberEntity getByPhone(@Param("phone") String phone);

    @Update("<script> UPDATE member SET " +
                " <if test=\"member.memberId != null\"> member_id = #{member.memberId} , </if>" +
                " <if test=\"member.storeId != null\"> store_id = #{member.storeId} , </if>" +
                " <if test=\"member.type != null\"> type = #{member.type} , </if>" +
                " <if test=\"member.name != null\"> name = #{member.name} , </if>" +
                " <if test=\"member.email != null\"> email = #{member.email} , </if>" +
                " <if test=\"member.phone != null\"> phone = #{member.phone} , </if>" +
                " <if test=\"member.nickname != null\"> nickname = #{member.nickname} , </if>" +
                " <if test=\"member.image != null\"> image = #{member.image} , </if>" +
                " <if test=\"member.age != null\"> age = #{member.age} , </if>" +
                " <if test=\"member.gender != null\"> gender = #{member.gender} , </if>" +
                " <if test=\"member.height != null\"> height = #{member.height} , </if>" +
                " <if test=\"member.idCard != null\"> id_card = #{member.idCard} , </if>" +
                " <if test=\"member.address != null\"> address = #{member.address} , </if>" +
                " <if test=\"member.coachStaffId != null\"> coach_staff_id = #{member.coachStaffId}, </if>" +
                " <if test=\"member.cardNo != null\"> card_no = #{member.cardNo} , </if>" +
                " <if test=\"member.trainingHours != null\"> training_hours = #{member.trainingHours} , </if>" +
                " <if test=\"member.openId != null\"> open_id = #{member.openId} , </if>" +
                " <if test=\"member.unionId != null\"> union_id = #{member.unionId} , </if>" +
                " <if test=\"member.feature != null\"> feature = #{member.feature} , </if>" +
                " <if test=\"member.origin != null\"> origin = #{member.origin} , </if>" +
                " <if test=\"member.salesman != null\"> salesman = #{member.salesman} , </if>" +
                " <if test=\"member.remark != null\"> remark = #{member.remark} , </if>" +
                " <if test=\"member.status != null\"> status = #{member.status} , </if>" +
                " <if test=\"member.creater != null\"> creater = #{member.creater} , </if>" +
                " modified = now() " +
            " WHERE member_id = #{member.memberId} " +
            "</script>")
    int update(@Param("member") MemberEntity member);

    @Update("<script> DELETE  FROM member " +
            " WHERE member_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);

    @Update("<script> UPDATE member SET open_id = #{member.openId} , modified = now() WHERE member_id = #{member.memberId}  </script>")
    int bind(@Param("member") MemberEntity member);

}

