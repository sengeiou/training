package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum CardStatusEnum {

    INIT(0,"未启用"),
    USING(1,"使用中"),
    END(2,"已失效"),
    PAUSE(9,"停课中")
    ;

    private Integer key;
    private String desc;

    public static final Map<Integer,CardStatusEnum> keyOfEnum = new HashMap<Integer, CardStatusEnum>();

    CardStatusEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static CardStatusEnum getEnumByKey(Integer key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        CardStatusEnum[] values =  CardStatusEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
