package com.training.util;

import org.springframework.stereotype.Service;

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

}
