package com.training.test;

import java.io.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;

/**
 * Created by huai23 on 2018/5/14.
 */
public class UploadPicTest {

    /**
     * 不显示的使用HttpPost就会默认提交请求的方式为post,
     * 传递的参数为以key:value的形式来传递参数
     * @param httpUrl
     * @param imagebyte 可以传递图片等文件,以字节数组的形式传递
     */
    @SuppressWarnings({ "deprecation" })
    public static String httpPost(String httpUrl,byte[] imagebyte){
        HttpPost httpPost = new HttpPost(httpUrl);
        ByteArrayBody image = new ByteArrayBody(imagebyte,ContentType.APPLICATION_JSON,"image.jpg");//传递图片的时候可以通过此处上传image.jpg随便给出即可
        String picName = "1.jpg";
        StringBody name = new StringBody(picName,ContentType.APPLICATION_JSON);
        StringBody filename = new StringBody(picName,ContentType.APPLICATION_JSON);
        MultipartEntityBuilder me = MultipartEntityBuilder.create();
        me.addPart("file", image)//image参数为在服务端获取的key通过image这个参数可以获取到传递的字节流,这里不一定就是image,你的服务端使用什么这里就对应给出什么参数即可
                .addPart("name",name )
                .addPart("filename", filename);

        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        DefaultHttpClient client = new DefaultHttpClient();
        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        registry.register(new Scheme("https", socketFactory, 443));
        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
        DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

//        DefaultHttpClient client= new DefaultHttpClient();
        HttpEntity reqEntity = me.build();
        httpPost.setEntity(reqEntity);
        HttpResponse responseRes = null;
        try {
            responseRes=httpClient.execute(httpPost);
            int status = responseRes.getStatusLine().getStatusCode();
            System.out.println(" status = "+status);

            String resultStr =null;
            if (status == 200) {
                byte[] content;
                try {
                    content = getContent(responseRes);
                    resultStr = new String(content,"utf-8");
                    System.out.println("httpPost返回的结果==:"+resultStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(resultStr !=null){
                return resultStr;
            }else{
                return "";
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.close();
        }
        return "";
    }


    private static byte[] getContent(HttpResponse response)
            throws IOException {
        InputStream result = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = resEntity.getContent();
                int len = 0;
                while ((len = result.read()) != -1) {
                    out.write(len);
                }
                return out.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("getContent异常", e);
        } finally {
            out.close();
            if (result != null) {
                result.close();
            }
        }
        return null;
    }

    public String start() throws ClientProtocolException, IOException{
        // 1. 创建上传需要的元素类型
        // 1.1 装载本地上传图片的文件
        File imageFile = new File("C:/Intel/test.jpg");
        FileBody imageFileBody = new FileBody(imageFile);
        // 1.2 装载经过base64编码的图片的数据
        String imageBase64Data = "vnweovinsldkjosdfgvndlgkdfgjsdfdfg";
        ByteArrayBody byteArrayBody = null;
        if(StringUtils.isNotEmpty(imageBase64Data)){
            byte[] byteImage = Base64.decodeBase64(imageBase64Data);
            byteArrayBody = new ByteArrayBody(byteImage,"image_name");
        }
        // 1.3 装载上传字符串的对象
        StringBody name = new StringBody("admin",ContentType.TEXT_PLAIN);
        System.out.println("装载数据完成");
        // 2. 将所有需要上传元素打包成HttpEntity对象
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("name", name)
                .addPart("image1",imageFileBody)
                .addPart("image2",byteArrayBody).build();
        System.out.println("打包数据完成");
        // 3. 创建HttpPost对象，用于包含信息发送post消息
        HttpPost httpPost = new HttpPost("http://www.cctv.com");
        httpPost.setEntity(reqEntity);
        System.out.println("创建post请求并装载好打包数据");
        // 4. 创建HttpClient对象，传入httpPost执行发送网络请求的动作
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println("发送post请求并获取结果");
        // 5. 获取返回的实体内容对象并解析内容
        HttpEntity resultEntity = response.getEntity();
        String responseMessage = "";
        try{
            System.out.println("开始解析结果");
            if(resultEntity!=null){
                InputStream is = resultEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                responseMessage = sb.toString();
                System.out.println("解析完成，解析内容为"+ responseMessage);
            }
            EntityUtils.consume(resultEntity);
        }finally{
            if (null != response){
                response.close();
            }
        }
        return responseMessage;
    }

    public static void main(String[] args) throws Exception{
//        UploadPicTest ht = new UploadPicTest();
//        ht.start();

        File file = new File("C://1.jpg");
        InputStream in = new FileInputStream(file);
        byte[] strBuffer = new byte[(int)file.length()];
        in.read(strBuffer, 0, (int)file.length());
        String result = httpPost("https://trainingbj.huai23.com/api/upload/image",strBuffer);

        System.out.println(result);
    }

}
