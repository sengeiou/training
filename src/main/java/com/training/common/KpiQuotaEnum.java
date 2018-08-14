package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum KpiQuotaEnum {

    k1("k1","个人体能考核"),
    k2("k2","会员活跃度"),
    k3("k3","会员点评"),
    k4("k4","会员续课率"),
    k5("k5","会员转介绍"),
    k6("k6","教练专业考核"),
    k7("k7","教练投诉"),
    ;

    private String key;
    private String desc;

    public static final Map<String,KpiQuotaEnum> keyOfEnum = new HashMap<String, KpiQuotaEnum>();

    KpiQuotaEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static KpiQuotaEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        KpiQuotaEnum[] values =  KpiQuotaEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
