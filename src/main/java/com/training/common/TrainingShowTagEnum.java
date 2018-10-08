package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 联系人属性
 * Created by huai on 2017/11/12.
 */
public enum TrainingShowTagEnum {

    cancel(-1,"已取消"),
    order(0,"预约中"),
    finish(1,"已完成"),
    overdue(2,"已过期");

    private Integer key;
    private String desc;

    public static final Map<Integer,TrainingShowTagEnum> keyOfEnum = new HashMap();

    TrainingShowTagEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static TrainingShowTagEnum getEnumByKey(Integer key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }

    static {
        TrainingShowTagEnum[] values =  TrainingShowTagEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
