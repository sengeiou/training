package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum CardTypeEnum {

    PT("PT","私教次卡"),
    PM("PM","私教月卡"),
    TT("TT","团体次卡"),
    TM("TM","团体月卡"),
    TY("TY","体验卡"),
    ;

    private String key;
    private String desc;

    public static final Map<String,CardTypeEnum> keyOfEnum = new HashMap<String, CardTypeEnum>();

    CardTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static CardTypeEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        CardTypeEnum[] values =  CardTypeEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
