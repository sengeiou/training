package com.training.dao;

import com.training.repository.*;
import com.training.entity.*;
import com.training.common.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * device 数据库操作类
 * Created by huai23 on 2019-01-26 23:09:05.
 */ 
@Service
public class DeviceDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * 新增实体
     * @param device
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public int add(DeviceEntity device){
        int n = deviceRepository.add(device);
        return n;
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public List<DeviceEntity> find(DeviceQuery query , PageRequest page){
        List<DeviceEntity> deviceList = deviceRepository.find(query,page);
        return deviceList;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public Long count(DeviceQuery query){
        Long n = deviceRepository.count(query);
        return n;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public DeviceEntity getById(String id){
        DeviceEntity deviceDB = deviceRepository.getById(id);
        return deviceDB;
    }

    /**
     * 根据实体更新
     * @param device
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public int update(DeviceEntity device){
        int n = deviceRepository.update(device);
        return n;
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-01-26 23:09:05.
     */ 
    public int delete(String id){
        int n = deviceRepository.delete(id);
        return n;
    }


}

