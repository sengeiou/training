package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 课程类型
 * Created by huai on 2017/11/12.
 */
public enum SysLogEnum {

    PAY("PAY","支付"),
    CC("CC","换教练"),
    YQ("YQ","付费延期"),
    ;

    private String key;
    private String desc;

    public static final Map<String,SysLogEnum> keyOfEnum = new HashMap<String, SysLogEnum>();

    SysLogEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static SysLogEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        SysLogEnum[] values =  SysLogEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
