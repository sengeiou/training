package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum HeroListTypeEnum {

    LC("LC","课程数量"),
    XK("XK","续课金额"),
    HYD("HYD","会员活跃度"),
    ;

    private String key;
    private String desc;

    public static final Map<String,HeroListTypeEnum> keyOfEnum = new HashMap<String, HeroListTypeEnum>();

    HeroListTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static HeroListTypeEnum getEnumByKey(Integer key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        HeroListTypeEnum[] values =  HeroListTypeEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
