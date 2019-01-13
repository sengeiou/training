package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum AuditTypeEnum {

    SUCCESS("1","通过"),
    CANCEL("2","取消"),
    ;

    private String key;
    private String desc;

    public static final Map<String,AuditTypeEnum> keyOfEnum = new HashMap<String, AuditTypeEnum>();

    AuditTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static AuditTypeEnum getEnumByKey(Integer key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        AuditTypeEnum[] values =  AuditTypeEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
