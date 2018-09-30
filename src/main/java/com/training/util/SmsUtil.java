package com.training.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.training.domain.Email;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SmsUtil {

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    static final String accessKeyId = "LTAI3S8lfxPGqzDm";
    static final String accessKeySecret = "eje76N1240vOyP6ory77jFjzKOe8ax";

    public static SendSmsResponse sendCode(String phone, String code) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_137660191");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\""+code+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendLessonNotice(String phone, String name, String date, String lessonType) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        if(StringUtils.isEmpty(lessonType)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
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
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendCancelLessonNotice(String phone, String name, String date, String lessonType) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        if(StringUtils.isEmpty(lessonType)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
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
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendAddMemberNotice(String phone, String name, String memberPhone) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
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
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendChangeCoachNotice(String phone, String store,String coach, String name) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
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
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendAddMemberMsgNotice(String phone, String store,String coach, String name) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
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
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendTrainingNoticeToCoach(String phone, String store,String name, String day) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
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
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendTrainingNoticeToMember(String phone,String day) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(day)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("HeyHeroes健身");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_138068546");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"day\":\""+day+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("huai23");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendCardEndNoticeToCoach(String phone,String store , String name) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if(StringUtils.isEmpty(store)){
            return null;
        }
        if(StringUtils.isEmpty(name)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
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
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static SendSmsResponse sendCardEndNoticeToMember(String phone,String card) throws ClientException {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
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
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("sendSmsResponse = "+JSON.toJSON(sendSmsResponse));
        return sendSmsResponse;
    }

    public static void main(String[] args) throws Exception {
//        SmsUtil.sendLessonNotice("18618191128","【张三】","2018-09-30 20:30","团体课");
//        SmsUtil.sendCancelLessonNotice("18618191128","【张三】","2018-09-30 20:30","团体课");
//        SmsUtil.sendAddMemberNotice("18618191128","【张三】","13812348899");
//        SmsUtil.sendChangeCoachNotice("18618191128","紫竹桥","【李四】","【张三】");
//        SmsUtil.sendAddMemberMsgNotice("18618191128","紫竹桥","【李四】","【张三】");

//        SmsUtil.sendTrainingNoticeToCoach("18618191128","紫竹桥","【李四】","36");
//        SmsUtil.sendTrainingNoticeToMember("18618191128","50");

        SmsUtil.sendCardEndNoticeToCoach("18618191128","望京","【王五】");
//        SmsUtil.sendCardEndNoticeToMember("18618191128","私教次卡");


    }

}
