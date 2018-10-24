package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信类型
 * Created by huai on 2017/11/12.
 */
public enum SmsLogEnum {

    CARD_END("CARD_END","卡到期提醒"),
    TRAINING_NOTICE("TRAINING_NOTICE","上课提醒"),
    ;

    private String key;
    private String desc;

    public static final Map<String,SmsLogEnum> keyOfEnum = new HashMap<String, SmsLogEnum>();

    SmsLogEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static SmsLogEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        SmsLogEnum[] values =  SmsLogEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
