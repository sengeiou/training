package com.training.domain;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Lesson {

    private Long pkId;

    private String lessonId;

    private String storeId;

    private String title;

    private String type;

    private String specialType;

    private String coachId;

    private String coachName;

    private String coachImage;

    private String cardNo;

    private String lessonDate;

    private Integer startHour;

    private Integer endHour;

    private Integer quota;

    private Integer minCount;

    private Integer maxCount;

    private Integer memberCount;

    private String trainingData;

    private String feature;

    private String myOrder;

    private String remark;

    private Integer status;

    private Date created;

    private Date modified;

    private List<Training> trainingList;

    private String trainingId;

    private String memberId;

    // 1v2的课程
    private String memberImage;

    private List<StaffMedal> staffMedalList;

}
