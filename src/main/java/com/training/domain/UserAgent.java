package com.training.domain;

import lombok.Data;

@Data
public class UserAgent {

    private String browserType;//浏览器类型
    private String browserVersion;//浏览器版本
    private String platformType;//平台类型
    private String platformSeries;//平台系列
    private String platformVersion;//平台版本
    private Boolean isWeixin;//是否微信

    public UserAgent(){}

    public UserAgent(String browserType, String browserVersion,
                     String platformType, String platformSeries, String platformVersion){
        this.browserType = browserType;
        this.browserVersion = browserVersion;
        this.platformType = platformType;
        this.platformSeries = platformSeries;
        this.platformVersion = platformVersion;
    }

}
