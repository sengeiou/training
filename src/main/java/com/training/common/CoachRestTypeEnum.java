package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum CoachRestTypeEnum {

    ONCE("0","仅一次"),
    DAY("1","每日重复"),
    WEEK("2","每周重复"),
    MONTH("3","每月重复"),
    ;

    private String key;
    private String desc;

    public static final Map<String,CoachRestTypeEnum> keyOfEnum = new HashMap<String, CoachRestTypeEnum>();

    CoachRestTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static CoachRestTypeEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        CoachRestTypeEnum[] values =  CoachRestTypeEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
