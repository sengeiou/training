package com.training.service;

import com.training.dao.*;
import com.training.domain.Staff;
import com.training.entity.*;
import com.training.domain.User;
import com.training.common.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.training.util.ResponseUtil;
import com.training.util.RequestContextHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * store 核心业务操作类
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@Service
public class StoreService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 新增实体
     * @param store
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public ResponseEntity<String> add(StoreEntity store){
        User user = RequestContextHelper.getUser();
        int n = storeDao.add(store);
        if(n==1){
            return ResponseUtil.success("添加成功");
        }
        return ResponseUtil.exception("添加失败");
    }

    /**
     * 分页查询
     * @param query
     * @param page
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public Page<StoreEntity> find(StoreQuery query , PageRequest page){
        Staff staffRequest = RequestContextHelper.getStaff();
        StaffEntity staffDB = staffDao.getById(staffRequest.getStaffId());
        RoleEntity roleEntity = roleDao.getById(staffDB.getRoleId());
        if(StringUtils.isEmpty(query.getStoreId())){
            if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                query.setStoreId(roleEntity.getStoreData());
            }else{
                query.setStoreId("123456789");
            }
            if(staffDB.getUsername().equals("admin")){
                query.setStoreId(null);
            }
        }else{
            if(staffDB.getUsername().equals("admin")){

            }else{
                if(roleEntity!=null&&StringUtils.isNotEmpty(roleEntity.getStoreData())){
                    String[] stores = roleEntity.getStoreData().split(",");
                    Set<String> ids = new HashSet<>();
                    for (int i = 0; i < stores.length; i++) {
                        if(StringUtils.isNotEmpty(stores[i])){
                            ids.add(stores[i]);
                        }
                    }
                    if(ids.contains(query.getStoreId())){

                    }else{
                        query.setStoreId("123456789");
                    }
                }else{
                    query.setStoreId("123456789");
                }
            }
        }
        logger.info("  find  StoreQuery = {}",query);

        List<StoreEntity> storeList = storeDao.find(query,page);
        Long count = storeDao.count(query);
        Page<StoreEntity> returnPage = new Page<>();
        returnPage.setContent(storeList);
        returnPage.setPage(page.getPage());
        returnPage.setSize(page.getPageSize());
        returnPage.setTotalElements(count);
        return returnPage;
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public Long count(StoreQuery query){
        Long count = storeDao.count(query);
        return count;
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public StoreEntity getById(String id){
        StoreEntity storeDB = storeDao.getById(id);
        return storeDB;
    }

    /**
     * 根据实体更新
     * @param store
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public  ResponseEntity<String> update(StoreEntity store){
        int n = storeDao.update(store);
        if(n==1){
            return ResponseUtil.success("修改成功");
        }
        return ResponseUtil.exception("修改失败");
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:43:38.
     */ 
    public ResponseEntity<String> delete(String id){
        int n = storeDao.delete(id);
        if(n==1){
            return ResponseUtil.success("删除成功");
        }
        return ResponseUtil.exception("删除失败");
    }


}

