package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡审批类型
 * Created by huai on 2017/11/12.
 */
public enum ProcessCodeEnum {

    PT("PT","PROC-EF6Y0XWVO2-RG59KJS2S58XL1O495ZN2-QABKW8MI-19","私教次卡"),
    PM("PM","PROC-FF6YQLE1N2-HESQB9ZYOW7FOIN97KIL1-84X7L0BJ-E","私教月卡"),
    TT("TT","","团体次卡"),
    TM("TM","","团体月卡"),
    ;

    private String key;
    private String code;
    private String desc;

    public static final Map<String,ProcessCodeEnum> keyOfEnum = new HashMap<String, ProcessCodeEnum>();

    ProcessCodeEnum(String key, String code, String desc) {
        this.key = key;
        this.code = code;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ProcessCodeEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        ProcessCodeEnum[] values =  ProcessCodeEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
