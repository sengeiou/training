package com.training.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员卡类型
 * Created by huai on 2017/11/12.
 */
public enum OriginEnum {

    DZDP(0,"大众点评"),
    MT(1,"美团"),
    WX(2,"微信"),
    WXPYQ(3,"微信朋友圈"),
    PEOPLE(4,"大众"),
    COACH(5,"教练推广"),
    COMING(6,"上门来访"),
    ZJS(7,"转介绍"),
    ZMQN(8,"周末去哪儿"),
    HDB(9,"互动吧"),
    ALISPORT(10,"阿里体育"),
    EXCEL(11,"EXCEL导入"),
    CONTRACT(12,"合同生成"),
    AUTO(13,"自动生成"),
    OTHER(14,"其他");

    private Integer key;
    private String desc;

    public static final Map<Integer,OriginEnum> keyOfEnum = new HashMap<Integer, OriginEnum>();

    OriginEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static OriginEnum getEnumByKey(Integer key){
        if(key == null || keyOfEnum == null){
            return  null;
        }
        return keyOfEnum.get(key);
    }
    static {
        OriginEnum[] values =  OriginEnum.values();
        for (int i = 0,length = values.length; i < length; i++) {
            keyOfEnum.put(values[i].getKey(),values[i]);
        }
    }
}
