package com.training.repository;

import com.training.entity.*;
import org.apache.ibatis.annotations.*;
import com.training.common.PageRequest;

import java.util.List;

/**
 * wxpay_log 数据库操作类
 * Created by huai23 on 2019-01-13 20:31:57.
 */ 
@Mapper
public interface WxpayLogRepository {

    @Insert("<script> INSERT INTO wxpay_log ( " +
                " <if test=\"wxpayLog.transactionId != null\"> transaction_id, </if>" +
                " <if test=\"wxpayLog.openid != null\"> openid, </if>" +
                " <if test=\"wxpayLog.sign != null\"> sign, </if>" +
                " <if test=\"wxpayLog.outTradeNo != null\"> out_trade_no, </if>" +
                " <if test=\"wxpayLog.mchId != null\"> mch_id, </if>" +
                " <if test=\"wxpayLog.appid != null\"> appid, </if>" +
                " <if test=\"wxpayLog.deviceInfo != null\"> device_info, </if>" +
                " <if test=\"wxpayLog.feeType != null\"> fee_type, </if>" +
                " <if test=\"wxpayLog.cashFee != null\"> cash_fee, </if>" +
                " <if test=\"wxpayLog.totalFee != null\"> total_fee, </if>" +
                " <if test=\"wxpayLog.tradeType != null\"> trade_type, </if>" +
                " <if test=\"wxpayLog.attach != null\"> attach, </if>" +
                " <if test=\"wxpayLog.timeEnd != null\"> time_end, </if>" +
                " <if test=\"wxpayLog.resultCode != null\"> result_code, </if>" +
                " <if test=\"wxpayLog.returnCode != null\"> return_code, </if>" +
                " <if test=\"wxpayLog.taxRate != null\"> tax_rate, </if>" +
                " <if test=\"wxpayLog.type != null\"> TYPE, </if>" +
                " <if test=\"wxpayLog.orderId != null\"> order_id, </if>" +
                " <if test=\"wxpayLog.storeId != null\"> store_id, </if>" +
                " <if test=\"wxpayLog.memberId != null\"> member_id, </if>" +
                " <if test=\"wxpayLog.staffId != null\"> staff_id, </if>" +
                " <if test=\"wxpayLog.cardNo != null\"> card_no, </if>" +
                " <if test=\"wxpayLog.relId != null\"> rel_id, </if>" +
                " <if test=\"wxpayLog.content != null\"> content, </if>" +
                " <if test=\"wxpayLog.feature != null\"> feature, </if>" +
                " <if test=\"wxpayLog.remark != null\"> remark, </if>" +
                " <if test=\"wxpayLog.auditStaffId != null\"> audit_staff_id, </if>" +
                " <if test=\"wxpayLog.auditTime != null\"> audit_time, </if>" +
                " created , " +
                " modified " +
            " ) VALUES ( " +
                " <if test=\"wxpayLog.transactionId != null\"> #{wxpayLog.transactionId}, </if>" +
                " <if test=\"wxpayLog.openid != null\"> #{wxpayLog.openid}, </if>" +
                " <if test=\"wxpayLog.sign != null\"> #{wxpayLog.sign}, </if>" +
                " <if test=\"wxpayLog.outTradeNo != null\"> #{wxpayLog.outTradeNo}, </if>" +
                " <if test=\"wxpayLog.mchId != null\"> #{wxpayLog.mchId}, </if>" +
                " <if test=\"wxpayLog.appid != null\"> #{wxpayLog.appid}, </if>" +
                " <if test=\"wxpayLog.deviceInfo != null\"> #{wxpayLog.deviceInfo}, </if>" +
                " <if test=\"wxpayLog.feeType != null\"> #{wxpayLog.feeType}, </if>" +
                " <if test=\"wxpayLog.cashFee != null\"> #{wxpayLog.cashFee}, </if>" +
                " <if test=\"wxpayLog.totalFee != null\"> #{wxpayLog.totalFee}, </if>" +
                " <if test=\"wxpayLog.tradeType != null\"> #{wxpayLog.tradeType}, </if>" +
                " <if test=\"wxpayLog.attach != null\"> #{wxpayLog.attach}, </if>" +
                " <if test=\"wxpayLog.timeEnd != null\"> #{wxpayLog.timeEnd}, </if>" +
                " <if test=\"wxpayLog.resultCode != null\"> #{wxpayLog.resultCode}, </if>" +
                " <if test=\"wxpayLog.returnCode != null\"> #{wxpayLog.returnCode}, </if>" +
                " <if test=\"wxpayLog.taxRate != null\"> #{wxpayLog.taxRate}, </if>" +
                " <if test=\"wxpayLog.type != null\"> #{wxpayLog.type}, </if>" +
                " <if test=\"wxpayLog.orderId != null\"> #{wxpayLog.orderId}, </if>" +
                " <if test=\"wxpayLog.storeId != null\"> #{wxpayLog.storeId}, </if>" +
                " <if test=\"wxpayLog.memberId != null\"> #{wxpayLog.memberId}, </if>" +
                " <if test=\"wxpayLog.staffId != null\"> #{wxpayLog.staffId}, </if>" +
                " <if test=\"wxpayLog.cardNo != null\"> #{wxpayLog.cardNo}, </if>" +
                " <if test=\"wxpayLog.relId != null\"> #{wxpayLog.relId}, </if>" +
                " <if test=\"wxpayLog.content != null\"> #{wxpayLog.content}, </if>" +
                " <if test=\"wxpayLog.feature != null\"> #{wxpayLog.feature}, </if>" +
                " <if test=\"wxpayLog.remark != null\"> #{wxpayLog.remark}, </if>" +
                " <if test=\"wxpayLog.auditStaffId != null\"> #{wxpayLog.auditStaffId}, </if>" +
                " <if test=\"wxpayLog.auditTime != null\"> #{wxpayLog.auditTime}, </if>" +
                " now() , " +
                " now() " +
            " ) " +
            "</script>")
    int add(@Param("wxpayLog") WxpayLogEntity wxpayLog);

    @Select("<script> SELECT pk_id,transaction_id,openid,sign,out_trade_no,mch_id,appid,device_info,fee_type,cash_fee,total_fee,trade_type,attach,time_end,result_code,return_code,tax_rate,TYPE,order_id,store_id,member_id,staff_id,card_no,rel_id,content,feature,remark,audit_staff_id,audit_time,created,modified " +
            " FROM wxpay_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.transactionId != null\"> AND transaction_id = #{query.transactionId} </if>" +
            " <if test=\"query.openid != null\"> AND openid = #{query.openid} </if>" +
            " <if test=\"query.sign != null\"> AND sign = #{query.sign} </if>" +
            " <if test=\"query.outTradeNo != null\"> AND out_trade_no = #{query.outTradeNo} </if>" +
            " <if test=\"query.mchId != null\"> AND mch_id = #{query.mchId} </if>" +
            " <if test=\"query.appid != null\"> AND appid = #{query.appid} </if>" +
            " <if test=\"query.deviceInfo != null\"> AND device_info = #{query.deviceInfo} </if>" +
            " <if test=\"query.feeType != null\"> AND fee_type = #{query.feeType} </if>" +
            " <if test=\"query.cashFee != null\"> AND cash_fee = #{query.cashFee} </if>" +
            " <if test=\"query.totalFee != null\"> AND total_fee = #{query.totalFee} </if>" +
            " <if test=\"query.tradeType != null\"> AND trade_type = #{query.tradeType} </if>" +
            " <if test=\"query.attach != null\"> AND attach = #{query.attach} </if>" +
            " <if test=\"query.timeEnd != null\"> AND time_end = #{query.timeEnd} </if>" +
            " <if test=\"query.resultCode != null\"> AND result_code = #{query.resultCode} </if>" +
            " <if test=\"query.returnCode != null\"> AND return_code = #{query.returnCode} </if>" +
            " <if test=\"query.taxRate != null\"> AND tax_rate = #{query.taxRate} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.orderId != null\"> AND order_id = #{query.orderId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.relId != null\"> AND rel_id = #{query.relId} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.auditStaffId != null\"> AND audit_staff_id = #{query.auditStaffId} </if>" +
            " <if test=\"query.auditTime != null\"> AND audit_time = #{query.auditTime} </if>" +
            " LIMIT #{page.offset} , #{page.pageSize} " +
            "</script>")
    List<WxpayLogEntity> find(@Param("query") WxpayLogQuery wxpayLog , @Param("page") PageRequest page);

    @Select("<script> SELECT COUNT(1) FROM wxpay_log " +
            " WHERE 1 = 1 " +
            " <if test=\"query.transactionId != null\"> AND transaction_id = #{query.transactionId} </if>" +
            " <if test=\"query.openid != null\"> AND openid = #{query.openid} </if>" +
            " <if test=\"query.sign != null\"> AND sign = #{query.sign} </if>" +
            " <if test=\"query.outTradeNo != null\"> AND out_trade_no = #{query.outTradeNo} </if>" +
            " <if test=\"query.mchId != null\"> AND mch_id = #{query.mchId} </if>" +
            " <if test=\"query.appid != null\"> AND appid = #{query.appid} </if>" +
            " <if test=\"query.deviceInfo != null\"> AND device_info = #{query.deviceInfo} </if>" +
            " <if test=\"query.feeType != null\"> AND fee_type = #{query.feeType} </if>" +
            " <if test=\"query.cashFee != null\"> AND cash_fee = #{query.cashFee} </if>" +
            " <if test=\"query.totalFee != null\"> AND total_fee = #{query.totalFee} </if>" +
            " <if test=\"query.tradeType != null\"> AND trade_type = #{query.tradeType} </if>" +
            " <if test=\"query.attach != null\"> AND attach = #{query.attach} </if>" +
            " <if test=\"query.timeEnd != null\"> AND time_end = #{query.timeEnd} </if>" +
            " <if test=\"query.resultCode != null\"> AND result_code = #{query.resultCode} </if>" +
            " <if test=\"query.returnCode != null\"> AND return_code = #{query.returnCode} </if>" +
            " <if test=\"query.taxRate != null\"> AND tax_rate = #{query.taxRate} </if>" +
            " <if test=\"query.type != null\"> AND TYPE = #{query.type} </if>" +
            " <if test=\"query.orderId != null\"> AND order_id = #{query.orderId} </if>" +
            " <if test=\"query.storeId != null\"> AND store_id = #{query.storeId} </if>" +
            " <if test=\"query.memberId != null\"> AND member_id = #{query.memberId} </if>" +
            " <if test=\"query.staffId != null\"> AND staff_id = #{query.staffId} </if>" +
            " <if test=\"query.cardNo != null\"> AND card_no = #{query.cardNo} </if>" +
            " <if test=\"query.relId != null\"> AND rel_id = #{query.relId} </if>" +
            " <if test=\"query.content != null\"> AND content = #{query.content} </if>" +
            " <if test=\"query.feature != null\"> AND feature = #{query.feature} </if>" +
            " <if test=\"query.remark != null\"> AND remark = #{query.remark} </if>" +
            " <if test=\"query.auditStaffId != null\"> AND audit_staff_id = #{query.auditStaffId} </if>" +
            " <if test=\"query.auditTime != null\"> AND audit_time = #{query.auditTime} </if>" +
            "</script>")
    Long count(@Param("query") WxpayLogQuery wxpayLog);

    @Select("<script> SELECT pk_id,transaction_id,openid,sign,out_trade_no,mch_id,appid,device_info,fee_type,cash_fee,total_fee,trade_type,attach,time_end,result_code,return_code,tax_rate,TYPE,order_id,store_id,member_id,staff_id,card_no,rel_id,content,feature,remark,audit_staff_id,audit_time,created,modified " +
            " FROM wxpay_log " +
            " WHERE transaction_id = #{id} " +
            "</script>")
    WxpayLogEntity getById(@Param("id") String id);

    @Update("<script> UPDATE wxpay_log SET " +
                " <if test=\"wxpayLog.transactionId != null\"> transaction_id = #{wxpayLog.transactionId} , </if>" +
                " <if test=\"wxpayLog.openid != null\"> openid = #{wxpayLog.openid} , </if>" +
                " <if test=\"wxpayLog.sign != null\"> sign = #{wxpayLog.sign} , </if>" +
                " <if test=\"wxpayLog.outTradeNo != null\"> out_trade_no = #{wxpayLog.outTradeNo} , </if>" +
                " <if test=\"wxpayLog.mchId != null\"> mch_id = #{wxpayLog.mchId} , </if>" +
                " <if test=\"wxpayLog.appid != null\"> appid = #{wxpayLog.appid} , </if>" +
                " <if test=\"wxpayLog.deviceInfo != null\"> device_info = #{wxpayLog.deviceInfo} , </if>" +
                " <if test=\"wxpayLog.feeType != null\"> fee_type = #{wxpayLog.feeType} , </if>" +
                " <if test=\"wxpayLog.cashFee != null\"> cash_fee = #{wxpayLog.cashFee} , </if>" +
                " <if test=\"wxpayLog.totalFee != null\"> total_fee = #{wxpayLog.totalFee} , </if>" +
                " <if test=\"wxpayLog.tradeType != null\"> trade_type = #{wxpayLog.tradeType} , </if>" +
                " <if test=\"wxpayLog.attach != null\"> attach = #{wxpayLog.attach} , </if>" +
                " <if test=\"wxpayLog.timeEnd != null\"> time_end = #{wxpayLog.timeEnd} , </if>" +
                " <if test=\"wxpayLog.resultCode != null\"> result_code = #{wxpayLog.resultCode} , </if>" +
                " <if test=\"wxpayLog.returnCode != null\"> return_code = #{wxpayLog.returnCode} , </if>" +
                " <if test=\"wxpayLog.taxRate != null\"> tax_rate = #{wxpayLog.taxRate} , </if>" +
                " <if test=\"wxpayLog.type != null\"> TYPE = #{wxpayLog.type} , </if>" +
                " <if test=\"wxpayLog.orderId != null\"> order_id = #{wxpayLog.orderId} , </if>" +
                " <if test=\"wxpayLog.storeId != null\"> store_id = #{wxpayLog.storeId} , </if>" +
                " <if test=\"wxpayLog.memberId != null\"> member_id = #{wxpayLog.memberId} , </if>" +
                " <if test=\"wxpayLog.staffId != null\"> staff_id = #{wxpayLog.staffId} , </if>" +
                " <if test=\"wxpayLog.cardNo != null\"> card_no = #{wxpayLog.cardNo} , </if>" +
                " <if test=\"wxpayLog.relId != null\"> rel_id = #{wxpayLog.relId} , </if>" +
                " <if test=\"wxpayLog.content != null\"> content = #{wxpayLog.content} , </if>" +
                " <if test=\"wxpayLog.feature != null\"> feature = #{wxpayLog.feature} , </if>" +
                " <if test=\"wxpayLog.remark != null\"> remark = #{wxpayLog.remark} , </if>" +
                " <if test=\"wxpayLog.auditStaffId != null\"> audit_staff_id = #{wxpayLog.auditStaffId} , </if>" +
                " <if test=\"wxpayLog.auditTime != null\"> audit_time = #{wxpayLog.auditTime} , </if>" +
                " modified = now() " +
            " WHERE transaction_id = #{wxpayLog.transactionId} " +
            "</script>")
    int update(@Param("wxpayLog") WxpayLogEntity wxpayLog);

    @Update("<script> DELETE  FROM wxpay_log " +
            " WHERE transaction_id = #{id} " +
            "</script>")
    int delete(@Param("id") String id);


}

