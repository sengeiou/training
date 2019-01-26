package com.training.service;

import com.training.dao.*;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.List;

/**
 * device 核心业务操作类
 * Created by huai23 on 2019-01-26 23:09:05.
 */ 
@Service
public class DeviceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceDao deviceDao;

    /**
     * 新增实体
     * @param device
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public ResponseEntity<String> add(DeviceEntity device){
        User user = RequestContextHelper.getUser();
        int n = deviceDao.add(device);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public Page<DeviceEntity> find(DeviceQuery query , PageRequest page){
        List<DeviceEntity> deviceList = deviceDao.find(query,page);
        Long count = deviceDao.count(query);
        Page<DeviceEntity> returnPage = new Page<>();
        returnPage.setContent(deviceList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public Long count(DeviceQuery query){
        Long count = deviceDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public DeviceEntity getById(String id){
        DeviceEntity deviceDB = deviceDao.getById(id);
        return deviceDB;
    }

    /**
     * 根据实体更新
     * @param device
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public  ResponseEntity<String> update(DeviceEntity device){
        int n = deviceDao.update(device);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = deviceDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

