package com.training.util;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class IDUtils {

    public static String getId() {
        return getId("");
    }

    public static String getId(String seq_name) {
        UUID uuid = UUID.randomUUID();
        String id = System.currentTimeMillis() + uuid.toString().replaceAll("-","");
        return id;
    }

    public static String getNewPwd() {
        return "yanxue"+((int)((Math.random()*9+1)*100000));
    }

    public static String getVerifyCode() {
        int max = 10000;
        int min = 1000;
        int n = new Random().nextInt(max - min) + min;
        return ""+n;
    }

    public static Integer getCouponId() {
        int max = 100000000;
        int min = 10000000;
        int n = new Random().nextInt(max - min) + min;
        return n;
    }

    public static void main(String[] args) {
        System.out.println(getCouponId());
    }

}
