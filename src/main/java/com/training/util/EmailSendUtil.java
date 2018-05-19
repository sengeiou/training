package com.training.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.training.domain.Email;
import org.springframework.stereotype.Service;

@Service
public class EmailSendUtil {

    public EmailSendUtil(){

    }

    public static void send(Email email, String toEmail) {
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIBPPrJvTqcQGX", "Qs0uZUJJLvjeNTK2wLnZMHlHPqnmfF");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIZle3i6SgjDxY", "H0CcGRGMHXpeB4fRRc6TQmfqIY6DeP");

        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
//            request.setAccountName("admin@huai23.com");
            request.setAccountName("qingyou@qingyouzi.cn");
            request.setFromAlias("青柚研学旅行管理系统");
            request.setAddressType(1);
            request.setTagName("notice");
            request.setReplyToAddress(true);
            request.setToAddress(toEmail);
            request.setSubject(email.getTitle());
            request.setHtmlBody(email.getContent());
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            System.out.println(httpResponse.getRequestId());
            System.out.println(httpResponse.getEnvId());
//            Thread.sleep(4000);
            System.out.println("  *************     后台发送了一封邮件   toEmail = " + toEmail);

        }
        catch (ServerException e) {
            e.printStackTrace();
        }
        catch (ClientException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Email email = new Email();
        email.setTitle("测试邮件");
        email.setContent("测试邮件内容：123456");
        send(email,"hi@qingyouzi.cn");
    }

}
