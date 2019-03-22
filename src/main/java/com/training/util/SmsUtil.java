package com.training.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Service
public class SmsUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${sms.send}")
    public boolean sendTag;

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    static final String accessKeyId = "LTAI3S8lfxPGqzDm";
    static final String accessKeySecret = "eje76N1240vOyP6ory77jFjzKOe8ax";

    private IAcsClient acsClient;

    @PostConstruct
    public void init()  throws ClientException{
        logger.info(">>>>>>>>>  SmsUtil.init start <<<<<<<<<<<");
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        acsClient = new DefaultAcsClient(profile);
        logger.info(">>>>>>>>>  SmsUtil.init end <<<<<<<<<<<");
    }

    public SendSmsResponse sendCode(String phone, String code) throws ClientException {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("HeyHeroes健身");
        request.setTemplateCode("SMS_137660191");
        request.setTemplateParam("{\"code\":\""+code+"\"}");
        request.setOutId("huai23");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        logger.info("sendSmsResponse = {} ",JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public SendSmsResponse sendLessonNotice(String phone, String name, String date, String lessonType) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        if(StringUtils.isEmpty(lessonType)){
            return null;
        }

        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_146806695");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"name\":\""+name+"\",\"date\":\""+date+"\",\"lesson_type\":\""+lessonType+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }

    public SendSmsResponse sendCancelLessonNotice(String phone, String name, String date, String lessonType) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        if(StringUtils.isEmpty(lessonType)){
            return null;
        }

        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_146803532");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"name\":\""+name+"\",\"date\":\""+date+"\",\"lesson_type\":\""+lessonType+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }

    public SendSmsResponse sendAddMemberNotice(String phone, String name, String memberPhone) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }

        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_146806697");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"name\":\""+name+"\",\"phone\":\""+memberPhone+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }

    public SendSmsResponse sendChangeCoachNotice(String phone, String store,String coach, String name) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }

        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_138078472");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"store\":\""+store+"\",\"coach\":\""+coach+"\",\"name\":\""+name+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }

    public SendSmsResponse sendAddMemberMsgNotice(String phone, String store,String coach, String name) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }

        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_138063660");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"store\":\""+store+"\",\"coach\":\""+coach+"\",\"name\":\""+name+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }

    public SendSmsResponse sendTrainingNoticeToCoach(String phone, String store,String name, String day) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }

        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_138078471");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"store\":\""+store+"\",\"name\":\""+name+"\",\"day\":\""+day+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }

    public SendSmsResponse sendTrainingNoticeToMember(String phone,String day) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(day)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_157276515");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"day\":\""+day+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }

    public SendSmsResponse sendCardEndNoticeToCoach(String phone,String store , String name) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(store)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_138078469");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"store\":\""+store+"\",\"name\":\""+name+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }

    public SendSmsResponse sendCardEndNoticeToMember(String phone,String card) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_138068544");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"card\":\""+card+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = sendSms(request);
        return sendSmsResponse;
    }


    /**
     * 停课通知_会员
     * @param phone
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendPauseMemberNoticeToMember(String phone) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("HeyHeroes健身");
        request.setTemplateCode("SMS_150742384");
        request.setOutId("huai23");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        logger.info("sendSmsResponse = {} ",JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    /**
     * 停课通知_教练
     * @param phone
     * @param memberName
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendPauseMemberNoticeToCoach(String phone,String memberName) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("HeyHeroes健身");
        request.setTemplateCode("SMS_150737505");
        request.setTemplateParam("{\"name\":\""+memberName+"\"}");
        request.setOutId("huai23");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        logger.info("sendSmsResponse = {} ",JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    /**
     * 复课通知_会员
     * @param phone
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendRestoreMemberNoticeToMember(String phone) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("HeyHeroes健身");
        request.setTemplateCode("SMS_155455789");
        request.setOutId("huai23");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        logger.info("sendSmsResponse = {} ",JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    /**
     * 复课通知_教练
     * @param phone
     * @param memberName
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendRestoreMemberNoticeToCoach(String phone,String memberName) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("HeyHeroes健身");
        request.setTemplateCode("SMS_150737509");
        request.setTemplateParam("{\"name\":\""+memberName+"\"}");
        request.setOutId("huai23");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        logger.info("sendSmsResponse = {} ",JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    /**
     * 购买成功通知
     * @param phone
     * @param orderId
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendOrderBuyNotice(String phone,String orderId) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("HeyHeroes健身");
        request.setTemplateCode("SMS_158547766");
        request.setTemplateCode("SMS_161380529");
        request.setTemplateParam("{\"order\":\""+orderId+"\"}");
        request.setOutId("huai23");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        logger.info("sendSmsResponse = {} ",JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    /**
     * 拼团成功通知
     * @param phone
     * @param orderId
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendOrderPintuanNotice(String phone,String orderId) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName("HeyHeroes健身");
        request.setTemplateCode("SMS_158547767");
        request.setTemplateCode("SMS_161380528");
        request.setTemplateParam("{\"order\":\""+orderId+"\"}");
        request.setOutId("huai23");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        logger.info("sendSmsResponse = {} ",JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    private SendSmsResponse sendSms(SendSmsRequest request) throws ClientException {
        SendSmsResponse sendSmsResponse = null;
        if(sendTag){
            sendSmsResponse = acsClient.getAcsResponse(request);
        }else{
            sendSmsResponse = createResponse();
        }
        logger.info("sendSmsResponse = {} ",JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    private SendSmsResponse createResponse() {
        SendSmsResponse sendSmsResponse = new SendSmsResponse();
        sendSmsResponse.setBizId("");
        sendSmsResponse.setCode("");
        sendSmsResponse.setMessage("");
        sendSmsResponse.setRequestId("");
        return sendSmsResponse;
    }

    public static void main(String[] args) throws Exception {
//        SmsUtil.sendLessonNotice("18618191128","【张三】","2018-09-30 20:30","团体课");
//        SmsUtil.sendCancelLessonNotice("18618191128","【张三】","2018-09-30 20:30","团体课");
//        SmsUtil.sendAddMemberNotice("13426474340","【 刘德华】","13988888888");
//        SmsUtil.sendChangeCoachNotice("18618191128","紫竹桥","【李四】","【张三】");
//        SmsUtil.sendAddMemberMsgNotice("18618191128","紫竹桥","【李四】","【张三】");

//        SmsUtil.sendTrainingNoticeToCoach("18618191128","紫竹桥","【李四】","36");
//        SmsUtil.sendTrainingNoticeToMember("18618191128","50");

//        SmsUtil.sendCardEndNoticeToCoach("13810248890","测试门","【二狗子】");
//        SmsUtil.sendCardEndNoticeToMember("18618191128","私教次卡");

        System.out.println(new SmsUtil().sendTag);

    }

}
