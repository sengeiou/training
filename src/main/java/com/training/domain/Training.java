package com.training.domain;

import com.training.util.ut;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * training 实体类
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@Data
public class Training {

    private Long pkId;

    private String trainingId;

    private String lessonId;

    private String title;

    private String storeId;

    private String storeName;

    private String type;

    private String memberId;

    private String coachId;

    private String cardNo;

    private String cardType;

    private String lessonDate;

    private Integer startHour;

    private Integer endHour;

    private String trainingData;

    private String signTime;

    private String feature;

    private String remark;

    private Integer status;

    private String showStatus;

    private Date created;

    private Date modified;

    private Member member;

    private String qrCode;

    public String getShowStatus(){
        if(null==this.status||StringUtils.isEmpty(this.lessonDate)||null==this.startHour||null==this.endHour){
            return "";
        }
        if(status==-1){
            return "已取消";
        }
        if(ut.passDayByDate(this.lessonDate,ut.currentDate())<0){
            return "预约中";
        }
        if(ut.passDayByDate(this.lessonDate,ut.currentDate())>0){
            if(!StringUtils.isEmpty(signTime)){
                return "已完成";
            }else{
                return "已过期";
            }
        }
        if(ut.passDayByDate(this.lessonDate,ut.currentDate())==0){
            System.out.println("this.lessonDate="+this.lessonDate+" , ut.currentDate()="+ut.currentDate());
            int time = ut.currentHour();
            if(time>this.endHour){
                if(!StringUtils.isEmpty(signTime)){
                    return "已完成";
                }else{
                    return "已过期";
                }
            }else{
                return "预约中";

            }
        }
        return "-";
    }

}

