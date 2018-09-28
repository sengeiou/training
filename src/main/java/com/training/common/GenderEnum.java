package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 联系人属性
 * Created by huai on 2017/11/12.
 */
public enum GenderEnum {

    male(0,"男"),
    female(1,"女");

    private Integer key;
    private String desc;

    public static final Map<Integer,GenderEnum> keyOfEnum = new HashMap();

    GenderEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static GenderEnum getEnumByKey(Integer key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        GenderEnum[] values =  GenderEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
