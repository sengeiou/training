package com.training.util;

import com.swetake.util.Qrcode;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

@Service
public class QrCodeUtils {

    public static String getBase64Str(String text) {
        System.out.println(" getBase64Str text = "+text);
        String base64Img = "";
        try {
            Qrcode testQrcode =new Qrcode();
            testQrcode.setQrcodeErrorCorrect('M');
            testQrcode.setQrcodeEncodeMode('B');
            testQrcode.setQrcodeVersion(7);
            byte[] d = text.getBytes("utf-8");
            System.out.println(" getBase64Str d.length = "+d.length);

            BufferedImage image = new BufferedImage(98, 98, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D g = image.createGraphics();
            g.setBackground(Color.WHITE);
            g.clearRect(0, 0, 98, 98);
            g.setColor(Color.BLACK);
            if (d.length>0 && d.length <120){
                boolean[][] s = testQrcode.calQrcode(d);
                for (int i=0;i<s.length;i++){
                    for (int j=0;j<s.length;j++){
                        if (s[j][i]) {
                            g.fillRect(j*2+3,i*2+3,2,2);
                        }
                    }
                }
            }
            g.dispose();
            image.flush();
//         bufferImage->base64
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            base64Img = encoder.encode(outputStream.toByteArray());
//            System.out.println(" base64Img="+base64Img);
//            String html = "<img src= \"data:image/png;base64," + base64Img + "\" width=\"400\" height=\"400\"/>";
//            System.out.println(" html = "+html);
//            ImageIO.write(image,"jpg",new File("d:/qrcode.jpg"));  //将其保存在C:/imageSort/targetPIC/下
        }catch (Exception e){
            e.printStackTrace();
        }
        return base64Img;
    }

    public static void main(String[] args) {
        getBase64Str("https://trainingbj.huai23.com/sign_in?id=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

}
