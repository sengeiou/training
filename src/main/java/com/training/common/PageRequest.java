package com.training.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求
 * Created by huai23 on 2016/8/30.
 */
@Data
public class PageRequest implements Serializable {

    private int page = 1;
    private int pageSize = 10;

    public PageRequest(){
    }

    public PageRequest(int pageNum,int pageSize){
        this.page = pageNum;
        this.pageSize = pageSize;
    }

    public PageRequest(int pageSize){
        this.pageSize = pageSize;
    }

    public long getOffset() {
        return (long) (page - 1) * pageSize;
    }

    public int getPageNum(){
        return this.page;
    }

    public void setPageNum(int pageNum){
        this.page = pageNum;
    }

    public int getNumPerPage(){
        return this.pageSize;
    }

    public void setNumPerPage(int numPerPage){
        this.pageSize = numPerPage;
    }

}
