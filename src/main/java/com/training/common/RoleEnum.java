package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色：1-管理员,2-子账号
 * Created by huai on 2017/11/12.
 */
public enum RoleEnum {

    ADMIN("1","管理员"),
    USER("2","子账号");

    private String key;
    private String desc;

    public static final Map<String,RoleEnum> keyOfEnum = new HashMap<String, RoleEnum>();

    RoleEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static RoleEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        RoleEnum[] values =  RoleEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
