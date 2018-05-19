package com.training.util;

import lombok.Data;

/**
 * Created by huai23 on 2017/10/19.
 */
@Data
public class CommonResponse {

    private Integer status;	//返回码
    private String msg;		//返回提示信息
    private Object data;	//返回数据

}
