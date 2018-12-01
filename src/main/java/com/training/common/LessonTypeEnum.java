package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 课程类型
 * Created by huai on 2017/11/12.
 */
public enum LessonTypeEnum {

    P("P","私教课"),
    T("T","团体课"),
    S("S","特色课"),
    ST1("ST1","格斗健身"),
    ST2("ST2","极速塑形"),
    ST3("ST3","孕产课程"),
    ;

    private String key;
    private String desc;

    public static final Map<String,LessonTypeEnum> keyOfEnum = new HashMap<String, LessonTypeEnum>();

    LessonTypeEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static LessonTypeEnum getEnumByKey(String key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        LessonTypeEnum[] values =  LessonTypeEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
