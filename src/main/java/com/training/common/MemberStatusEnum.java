package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum MemberStatusEnum {

    INIT(0,"意向"),
    NORMAL(1,"有效"),
    PAUSE(9,"停课"),
    EXPIRE(2,"结课"),
    CLOSE(-1,"闭环");

    private Integer key;
    private String desc;

    public static final Map<Integer,MemberStatusEnum> keyOfEnum = new HashMap<Integer, MemberStatusEnum>();

    MemberStatusEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static MemberStatusEnum getEnumByKey(Integer key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }
    static {
        MemberStatusEnum[] values =  MemberStatusEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
