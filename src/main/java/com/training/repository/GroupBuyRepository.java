package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * group_buy 数据库操作类
 * Created by huai23 on 2019-01-30 23:03:41.
 */ 
@Mapper
public interface GroupBuyRepository {

    @Insert("<script> INSERT INTO group_buy ( " +
                " <if test=\"groupBuy.buyId != null\"> buy_id, </if>" +
                " <if test=\"groupBuy.title != null\"> title, </if>" +
                " <if test=\"groupBuy.image != null\"> image, </if>" +
                " <if test=\"groupBuy.shareTitle != null\"> share_title, </if>" +
                " <if test=\"groupBuy.shareDesc != null\"> share_desc, </if>" +
                " <if test=\"groupBuy.shareImage != null\"> share_image, </if>" +
                " <if test=\"groupBuy.shareUrl != null\"> share_url, </if>" +
                " <if test=\"groupBuy.count != null\"> count, </if>" +
                " <if test=\"groupBuy.initCount != null\"> init_count, </if>" +
                " <if test=\"groupBuy.saleCount != null\"> sale_count, </if>" +
                " <if test=\"groupBuy.autoComplete != null\"> auto_complete, </if>" +
                " <if test=\"groupBuy.price != null\"> price, </if>" +
                " <if test=\"groupBuy.groupPrice != null\"> group_price, </if>" +
                " <if test=\"groupBuy.startDate != null\"> start_date, </if>" +
                " <if test=\"groupBuy.endDate != null\"> end_date, </if>" +
                " <if test=\"groupBuy.limitation != null\"> limitation, </if>" +
                " <if test=\"groupBuy.content != null\"> content, </if>" +
                " <if test=\"groupBuy.mainTag != null\"> main_tag, </if>" +
                " <if test=\"groupBuy.status != null\"> status, </if>" +
                " <if test=\"groupBuy.viewCount != null\"> view_count, </if>" +
                " <if test=\"groupBuy.buyCount != null\"> buy_count, </if>" +
                " <if test=\"groupBuy.feature != null\"> feature, </if>" +
                " <if test=\"groupBuy.remark != null\"> remark, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"groupBuy.buyId != null\"> #{groupBuy.buyId}, </if>" +
                " <if test=\"groupBuy.title != null\"> #{groupBuy.title}, </if>" +
                " <if test=\"groupBuy.image != null\"> #{groupBuy.image}, </if>" +
                " <if test=\"groupBuy.shareTitle != null\"> #{groupBuy.shareTitle}, </if>" +
                " <if test=\"groupBuy.shareDesc != null\"> #{groupBuy.shareDesc}, </if>" +
                " <if test=\"groupBuy.shareImage != null\"> #{groupBuy.shareImage}, </if>" +
                " <if test=\"groupBuy.shareUrl != null\"> #{groupBuy.shareUrl}, </if>" +
                " <if test=\"groupBuy.count != null\"> #{groupBuy.count}, </if>" +
                " <if test=\"groupBuy.initCount != null\"> #{groupBuy.initCount}, </if>" +
                " <if test=\"groupBuy.saleCount != null\"> #{groupBuy.saleCount}, </if>" +
                " <if test=\"groupBuy.autoComplete != null\"> #{groupBuy.autoComplete}, </if>" +
                " <if test=\"groupBuy.price != null\"> #{groupBuy.price}, </if>" +
                " <if test=\"groupBuy.groupPrice != null\"> #{groupBuy.groupPrice}, </if>" +
                " <if test=\"groupBuy.startDate != null\"> #{groupBuy.startDate}, </if>" +
                " <if test=\"groupBuy.endDate != null\"> #{groupBuy.endDate}, </if>" +
                " <if test=\"groupBuy.limitation != null\"> #{groupBuy.limitation}, </if>" +
                " <if test=\"groupBuy.content != null\"> #{groupBuy.content}, </if>" +
                " <if test=\"groupBuy.mainTag != null\"> #{groupBuy.mainTag}, </if>" +
                " <if test=\"groupBuy.status != null\"> #{groupBuy.status}, </if>" +
                " <if test=\"groupBuy.viewCount != null\"> #{groupBuy.viewCount}, </if>" +
                " <if test=\"groupBuy.buyCount != null\"> #{groupBuy.buyCount}, </if>" +
                " <if test=\"groupBuy.feature != null\"> #{groupBuy.feature}, </if>" +
                " <if test=\"groupBuy.remark != null\"> #{groupBuy.remark}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("groupBuy") GroupBuyEntity groupBuy);

    @Select("<script> SELECT pk_id,buy_id,title,image,share_title,share_desc,share_image,share_url,count,init_count,sale_count,auto_complete,price,group_price,start_date,end_date,limitation,content,main_tag,status,view_count,buy_count,feature,remark,created,modified " +
            " FROM group_buy " +
            " WHERE 1 = 1 " +
            " <if test=\"query.buyId != null\"> AND buy_id = #{query.buyId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.shareTitle != null\"> AND share_title = #{query.shareTitle} </if>" +
            " <if test=\"query.shareDesc != null\"> AND share_desc = #{query.shareDesc} </if>" +
            " <if test=\"query.shareImage != null\"> AND share_image = #{query.shareImage} </if>" +
            " <if test=\"query.shareUrl != null\"> AND share_url = #{query.shareUrl} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.initCount != null\"> AND init_count = #{query.initCount} </if>" +
            " <if test=\"query.saleCount != null\"> AND sale_count = #{query.saleCount} </if>" +
            " <if test=\"query.autoComplete != null\"> AND auto_complete = #{query.autoComplete} </if>" +
            " <if test=\"query.price != null\"> AND price = #{query.price} </if>" +
            " <if test=\"query.groupPrice != null\"> AND group_price = #{query.groupPrice} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date = #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date = #{query.endDate} </if>" +
            " <if test=\"query.limitation != null\"> AND limitation = #{query.limitation} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.mainTag != null\"> AND main_tag = #{query.mainTag} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.viewCount != null\"> AND view_count = #{query.viewCount} </if>" +
            " <if test=\"query.buyCount != null\"> AND buy_count = #{query.buyCount} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<GroupBuyEntity> find(@Param("query") GroupBuyQuery groupBuy , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM group_buy " +
            " WHERE 1 = 1 " +
            " <if test=\"query.buyId != null\"> AND buy_id = #{query.buyId} </if>" +
            " <if test=\"query.title != null\"> AND title = #{query.title} </if>" +
            " <if test=\"query.image != null\"> AND image = #{query.image} </if>" +
            " <if test=\"query.shareTitle != null\"> AND share_title = #{query.shareTitle} </if>" +
            " <if test=\"query.shareDesc != null\"> AND share_desc = #{query.shareDesc} </if>" +
            " <if test=\"query.shareImage != null\"> AND share_image = #{query.shareImage} </if>" +
            " <if test=\"query.shareUrl != null\"> AND share_url = #{query.shareUrl} </if>" +
            " <if test=\"query.count != null\"> AND count = #{query.count} </if>" +
            " <if test=\"query.initCount != null\"> AND init_count = #{query.initCount} </if>" +
            " <if test=\"query.saleCount != null\"> AND sale_count = #{query.saleCount} </if>" +
            " <if test=\"query.autoComplete != null\"> AND auto_complete = #{query.autoComplete} </if>" +
            " <if test=\"query.price != null\"> AND price = #{query.price} </if>" +
            " <if test=\"query.groupPrice != null\"> AND group_price = #{query.groupPrice} </if>" +
            " <if test=\"query.startDate != null\"> AND start_date = #{query.startDate} </if>" +
            " <if test=\"query.endDate != null\"> AND end_date = #{query.endDate} </if>" +
            " <if test=\"query.limitation != null\"> AND limitation = #{query.limitation} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.mainTag != null\"> AND main_tag = #{query.mainTag} </if>" +
            " <if test=\"query.status != null\"> AND status = #{query.status} </if>" +
            " <if test=\"query.viewCount != null\"> AND view_count = #{query.viewCount} </if>" +
            " <if test=\"query.buyCount != null\"> AND buy_count = #{query.buyCount} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            "</script>")
    Long count(@Param("query") GroupBuyQuery groupBuy);

    @Select("<script> SELECT pk_id,buy_id,title,image,share_title,share_desc,share_image,share_url,count,init_count,sale_count,auto_complete,price,group_price,start_date,end_date,limitation,content,main_tag,status,view_count,buy_count,feature,remark,created,modified " +
            " FROM group_buy " +
            " WHERE buy_id = #{id} " +
            "</script>")
    GroupBuyEntity getById(@Param("id") String id);

    @Update("<script> UPDATE group_buy SET " +
                " <if test=\"groupBuy.buyId != null\"> buy_id = #{groupBuy.buyId} , </if>" +
                " <if test=\"groupBuy.title != null\"> title = #{groupBuy.title} , </if>" +
                " <if test=\"groupBuy.image != null\"> image = #{groupBuy.image} , </if>" +
                " <if test=\"groupBuy.shareTitle != null\"> share_title = #{groupBuy.shareTitle} , </if>" +
                " <if test=\"groupBuy.shareDesc != null\"> share_desc = #{groupBuy.shareDesc} , </if>" +
                " <if test=\"groupBuy.shareImage != null\"> share_image = #{groupBuy.shareImage} , </if>" +
                " <if test=\"groupBuy.shareUrl != null\"> share_url = #{groupBuy.shareUrl} , </if>" +
                " <if test=\"groupBuy.count != null\"> count = #{groupBuy.count} , </if>" +
                " <if test=\"groupBuy.initCount != null\"> init_count = #{groupBuy.initCount} , </if>" +
                " <if test=\"groupBuy.saleCount != null\"> sale_count = #{groupBuy.saleCount} , </if>" +
                " <if test=\"groupBuy.autoComplete != null\"> auto_complete = #{groupBuy.autoComplete} , </if>" +
                " <if test=\"groupBuy.price != null\"> price = #{groupBuy.price} , </if>" +
                " <if test=\"groupBuy.groupPrice != null\"> group_price = #{groupBuy.groupPrice} , </if>" +
                " <if test=\"groupBuy.startDate != null\"> start_date = #{groupBuy.startDate} , </if>" +
                " <if test=\"groupBuy.endDate != null\"> end_date = #{groupBuy.endDate} , </if>" +
                " <if test=\"groupBuy.limitation != null\"> limitation = #{groupBuy.limitation} , </if>" +
                " <if test=\"groupBuy.content != null\"> content = #{groupBuy.content} , </if>" +
                " <if test=\"groupBuy.mainTag != null\"> main_tag = #{groupBuy.mainTag} , </if>" +
                " <if test=\"groupBuy.status != null\"> status = #{groupBuy.status} , </if>" +
                " <if test=\"groupBuy.viewCount != null\"> view_count = #{groupBuy.viewCount} , </if>" +
                " <if test=\"groupBuy.buyCount != null\"> buy_count = #{groupBuy.buyCount} , </if>" +
                " <if test=\"groupBuy.feature != null\"> feature = #{groupBuy.feature} , </if>" +
                " <if test=\"groupBuy.remark != null\"> remark = #{groupBuy.remark} , </if>" +
                " modified = now() " +
            " WHERE buy_id = #{groupBuy.buyId} " +
            "</script>")
    int update(@Param("groupBuy") GroupBuyEntity groupBuy);

    @Update("<script> DELETE  FROM group_buy " +
            " WHERE buy_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}

