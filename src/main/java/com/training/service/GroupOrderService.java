package com.training.service;

import com.aliyuncs.exceptions.ClientException;
import com.github.wxpay.sdk.WXPayUtil;
import com.training.dao.*;
import com.training.domain.GroupOrder;
import com.training.domain.StoreData;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import com.training.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * group_order 核心业务操作类
 * Created by huai23 on 2019-02-01 20:05:18.
 */ 
@Service
public class GroupOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderDao groupOrderDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private GroupOrderLogDao groupOrderLogDao;

    @Autowired
    private GroupBuyDao groupBuyDao;

    @Autowired
    private WechatUtils wechatUtils;

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增实体
     * @param groupOrder
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public ResponseEntity<String> addOrder(GroupOrderEntity groupOrder) throws Exception {
        String openId = groupOrder.getOpenId();
//        String validCode = groupOrder.getValidCode();
//        logger.info(" validCodeMap : {} " , Const.validCodeMap);
//        if(StringUtils.isEmpty(validCode)){
//            return ResponseUtil.exception("请输入手机验证码");
//        }
//        if(!validCode.equals("8209")) {
//            if(!Const.validCodeMap.containsKey(groupOrder.getPhone())){
//                return ResponseUtil.exception("验证码无效");
//            }
//            List<String> codeList = Const.validCodeMap.get(groupOrder.getPhone());
//            boolean errorFlag = true;
//            for (String code:codeList){
//                String[] codes = code.split("_");
//                logger.info(" code : {} " ,codes[0]);
//                logger.info(" time : {} " ,codes[1]);
//                long time = Long.parseLong(codes[1]);
//                long now = System.currentTimeMillis();
//                logger.info(" time : {} " ,time);
//                logger.info(" now : {} " ,now);
//                if(validCode.equals(codes[0])){
//                    errorFlag = false;
//                    break;
//                }
//            }
//            if(validCode.equals("8209")){
//                errorFlag = false;
//            }
//            if(errorFlag){
//                return ResponseUtil.exception("手机验证码错误!");
//            }
//        }

        if(!groupOrder.getPhone().startsWith("1") || groupOrder.getPhone().length() != 11){
            return ResponseUtil.exception("请输入正确的手机号码!");
        }

        GroupBuyEntity groupBuyEntity = groupBuyDao.getById(groupOrder.getBuyId());
        if(groupBuyEntity.getLimitation()==1){
            GroupOrderQuery query = new GroupOrderQuery();
            query.setBuyId(groupOrder.getBuyId());
            query.setPhone(groupOrder.getPhone());
            PageRequest page = new PageRequest();
            page.setPageSize(10000);
            List<GroupOrderEntity> groupOrderList = groupOrderDao.find(query,page);
            if(groupOrderList.size()>groupBuyEntity.getLimitation()){
                return ResponseUtil.exception("很抱歉，已超过限购数量，无法购买!");
            }
        }

        int n = groupOrderDao.add(groupOrder);

        if(n > 0){
            Long orderId = groupOrder.getOrderId();
            logger.info(" addOrder orderId = {} ",orderId);
            String out_trade_no = ""+orderId+"_"+System.currentTimeMillis();
            double fee = ut.doubled(groupOrder.getTotalFee());
            int total_fee = (int)(fee*100);

            if(groupOrder.getMicroTag()==1){
                Map<String, String> param = new HashMap<>();
                param.put("openId",openId);
                param.put("total_fee",""+total_fee);
                param.put("out_trade_no",out_trade_no);
                Map<String, String> dataMap = wechatUtils.payGroupOrder(param);
                dataMap.put("orderId",out_trade_no);
                return ResponseUtil.success("添加成功",dataMap);
            }

            String unifiedorderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            String mch_id = "1284812401";// 微信公众号
            String key = "DyCGX2iQOMt1S5spSWdB8wmya7aO3ACj";
            String device_info = "1000";
            String nonce_str = "abc123cba321";
            String timeStamp = ""+System.currentTimeMillis()/1000;
            Map<String,String> param = new HashMap();
            param.put("appid","wx07d9e50873fe1786"); // 微信公众号
            param.put("mch_id",mch_id);
            param.put("openid",openId);
            param.put("device_info",device_info);
            param.put("nonce_str",nonce_str);
            param.put("sign_type","MD5");
            param.put("body",openId);
            param.put("detail","detail123");
            param.put("attach",openId);
            param.put("out_trade_no",out_trade_no);
            param.put("fee_type","CNY");
            param.put("total_fee",""+total_fee);
//            param.put("total_fee","1");
            param.put("spbill_create_ip","47.104.252.220");
            param.put("notify_url","https://cloud.heyheroes.com/wechat/pay/group/callback");
            param.put("trade_type","JSAPI");
            String sign = WXPayUtil.generateSignature(param,key);
//            System.out.println("sign："+sign);
            param.put("sign",sign);
            String reqBody = WXPayUtil.mapToXml(param);
            System.out.println("reqBody："+reqBody);
            String data = com.training.util.HttpUtils.doPost(unifiedorderUrl,reqBody); // java的网络请求，这里是我自己封装的一个工具包，返回字符串
            System.out.println("请求结果："+data);
            System.out.println("请求结果："+new String(data.getBytes("gbk"),"UTF-8"));
            System.out.println("请求结果："+new String(data.getBytes("iso-8859-1"),"UTF-8"));
            System.out.println("请求结果："+new String(data.getBytes("iso-8859-1"),"gbk"));
            System.out.println("请求结果："+new String(data.getBytes("UTF-8"),"gbk"));
            Map<String, String> result = WXPayUtil.xmlToMap(data);
            System.out.println("timeStamp="+timeStamp);
            Map<String, String> signMap = new HashMap<>();
            String package1 = "prepay_id="+result.get("prepay_id");
            signMap.put("appId","wx07d9e50873fe1786");
            signMap.put("timeStamp",timeStamp);
            signMap.put("nonceStr",nonce_str);
            signMap.put("package",package1);
            signMap.put("signType","MD5");
            sign = WXPayUtil.generateSignature(signMap,key);
            signMap.put("paySign",sign);
//            Const.validCodeMap.remove(groupOrder.getPhone());
            return ResponseUtil.success("添加成功",signMap);
        }
        return ResponseUtil.exception("添加失败");
    }

    public ResponseEntity<String> add(GroupOrderEntity groupOrder) {
        User user = RequestContextHelper.getUser();
        String openId = groupOrder.getOpenId();
        int n = groupOrderDao.add(groupOrder);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public Page<GroupOrder> find(GroupOrderQuery query , PageRequest page){
        List<GroupOrderEntity> groupOrderList = groupOrderDao.find(query,page);
        List<GroupOrder> result = new ArrayList();
        for (GroupOrderEntity groupOrderEntity:groupOrderList){
            GroupOrder groupOrder = transferOrder(groupOrderEntity);
            result.add(groupOrder);
        }
        Long count = groupOrderDao.count(query);
        Page<GroupOrder> returnPage = new Page<>();
        returnPage.setContent(result);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    private GroupOrder transferOrder(GroupOrderEntity groupOrderEntity) {
        GroupOrder groupOrder = new GroupOrder();
        BeanUtils.copyProperties(groupOrderEntity,groupOrder);
        GroupBuyEntity groupBuyEntity = groupBuyDao.getById(groupOrderEntity.getBuyId());
        if(groupBuyEntity!=null){
            groupOrder.setTitle(groupBuyEntity.getTitle());
            groupOrder.setRemark(groupBuyEntity.getRemark());
        }
        StoreEntity storeEntity = storeDao.getById(groupOrder.getStoreId());
        if(storeEntity!=null){
            groupOrder.setStoreName(storeEntity.getName());
        }else{
            groupOrder.setStoreName(groupOrderEntity.getStoreId());
        }
        if(groupOrder.getStatus()==0){
            groupOrder.setShowStatus("待付款");
        }else if(groupOrder.getStatus()==2){
            groupOrder.setShowStatus("已付款待拼团");
        }else if(groupOrder.getStatus()==-1){
            groupOrder.setShowStatus("已失效");
        }else if(groupOrder.getStatus()==3){
            groupOrder.setShowStatus("已完成");
        }else{
            groupOrder.setShowStatus(""+groupOrder.getStatus());
        }

        GroupOrderLogQuery query = new GroupOrderLogQuery();
        query.setOrderId(groupOrderEntity.getOrderId().toString());
        PageRequest page = new PageRequest(1);
        List<GroupOrderLogEntity> groupOrderLogList = groupOrderLogDao.find(query,page);
        if(groupOrderLogList.size()>0){
            groupOrder.setLog(groupOrderLogList.get(0));
        }else{
            groupOrder.setLog(new GroupOrderLogEntity());
        }
        DateFormat df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        groupOrder.setCreateTime(df_time.format(groupOrderEntity.getCreated()));
        return groupOrder;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public Long count(GroupOrderQuery query){
        Long count = groupOrderDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public GroupOrder getById(String id){
        GroupOrderEntity groupOrderDB = groupOrderDao.getById(id);
        GroupOrder groupOrder = transferOrder(groupOrderDB);
        return groupOrder;
    }

    /**
     * 根据实体更新
     * @param groupOrder
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public  ResponseEntity<String> update(GroupOrderEntity groupOrder){
        int n = groupOrderDao.update(groupOrder);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = groupOrderDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }

    public void dealOrder() {
        GroupOrderQuery query = new GroupOrderQuery();
        query.setStatus(2);
        PageRequest page = new PageRequest(1000);
        List<GroupOrderEntity> groupOrderList = groupOrderDao.find(query,page);
        for (GroupOrderEntity groupOrderEntity : groupOrderList){
            long created = groupOrderEntity.getCreated().getTime();
            long now = System.currentTimeMillis();
            long seconds = now-created;
            logger.info(" dealOrder_orderId = {} , name = {} , phone = {} ,seconds = {}",groupOrderEntity.getOrderId(),groupOrderEntity.getName(),groupOrderEntity.getPhone(),seconds);
            if(seconds>= 15*60*1000){
                logger.info("   order  has expired  orderId = {} ,seconds = {}  ",groupOrderEntity.getOrderId(),seconds);
                jdbcTemplate.update(" update group_order set status = 3 where order_id = ? ",new Object[]{groupOrderEntity.getOrderId()});
                try {
                    smsUtil.sendOrderPintuanNotice(groupOrderEntity.getPhone(),groupOrderEntity.getOrderId().toString());
                } catch (ClientException e) {
                    logger.error(" sendOrderPintuanNoticeError , e = {} ",e.getMessage(),e);
                }
            }
        }

        GroupOrderQuery query2 = new GroupOrderQuery();
        query2.setStatus(0);
        PageRequest page2 = new PageRequest(1000);
        List<GroupOrderEntity> unPayList = groupOrderDao.find(query2,page2);
        for (GroupOrderEntity groupOrderEntity : unPayList){
            String day = new SimpleDateFormat("yyyy-MM-dd").format(groupOrderEntity.getCreated());
            String now = ut.currentDate();
            int passDays = ut.passDayByDate(day,now);
            logger.info(" dealOrder_orderId = {} , name = {} , phone = {} ,passDays = {}",groupOrderEntity.getOrderId(),groupOrderEntity.getName(),groupOrderEntity.getPhone(),passDays);
            if(passDays>1){
                logger.info("   order  has canceled  orderId = {} ,passDays = {}  ",groupOrderEntity.getOrderId(),passDays);
                jdbcTemplate.update(" insert into group_order_cancel select * from group_order  where order_id = ? ",new Object[]{groupOrderEntity.getOrderId()});
                jdbcTemplate.update(" delete from group_order where order_id = ? ",new Object[]{groupOrderEntity.getOrderId()});
            }
        }
    }

}

