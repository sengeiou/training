package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum MemberMedalEnum {

    CQ1("CQ1","出勤达人Lv1",8),
    CQ2("CQ2","出勤达人Lv2",16),
    CQ3("CQ3","出勤达人Lv3",24),
    SJ1("SJ1","健身老司机Lv1",36),
    SJ2("SJ2","健身老司机Lv2",64),
    SJ3("SJ3","健身老司机Lv3",108),
    OX("OX","Hey帮偶像",0),
    ;

    private String key;
    private String desc;
    private int count;

    public static final Map<String,MemberMedalEnum> keyOfEnum = new HashMap<String, MemberMedalEnum>();

    MemberMedalEnum(String key, String desc, int count) {
        this.key = key;
        this.desc = desc;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public int getCount() {
        return count;
    }

    public static MemberMedalEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        MemberMedalEnum[] values =  MemberMedalEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
