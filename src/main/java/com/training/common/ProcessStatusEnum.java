package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 审批状态
 * Created by huai on 2017/11/12.
 */
public enum ProcessStatusEnum {

    NEW("NEW","刚创建"),
    RUNNING("RUNNING","运行中"),
    TERMINATED("TERMINATED","被终止"),
    COMPLETED("COMPLETED","完成"),
    CANCELED("CANCELED","取消"),
    ;

    private String key;
    private String desc;

    public static final Map<String,ProcessStatusEnum> keyOfEnum = new HashMap<String, ProcessStatusEnum>();

    ProcessStatusEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static ProcessStatusEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        ProcessStatusEnum[] values =  ProcessStatusEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
