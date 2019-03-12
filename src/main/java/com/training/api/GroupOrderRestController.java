package com.training.api;

import com.training.config.ConstData;
import com.training.domain.GroupOrder;
import com.training.service.*;
import com.training.entity.*;
import com.training.common.*;
import com.training.util.ExportUtil;
import com.training.util.IDUtils;
import com.training.util.ut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.training.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * group_order API控制器
 * Created by huai23 on 2019-02-01 20:05:18.
 */ 
@RestController
@RequestMapping("/api/groupOrder")
public class GroupOrderRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroupOrderService groupOrderService;

    /**
     * 新增实体
     * @param groupOrder
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    @RequestMapping (value = "addOrder", method = RequestMethod.POST)
    public ResponseEntity<String> addOrder(@ModelAttribute GroupOrderEntity groupOrder,HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" group_orderRestController  addOrder  groupOrder = {}",groupOrder);
        return groupOrderService.addOrder(groupOrder);
    }

    /**
     * 新增实体
     * @param groupOrder
     * Created by huai23 on 2019-02-01 20:05:18.
     */
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody GroupOrderEntity groupOrder,HttpServletRequest request, HttpServletResponse response){
        logger.info(" group_orderRestController  add  groupOrder = {}",groupOrder);
        return groupOrderService.add(groupOrder);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute GroupOrderQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<GroupOrder> page = groupOrderService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute GroupOrderQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = groupOrderService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        GroupOrder groupOrder = groupOrderService.getById(id);
        if(groupOrder==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(groupOrder);
    }

    /**
     * 根据实体更新
     * @param groupOrder
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody GroupOrderEntity groupOrder,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  groupOrder = {}",groupOrder);
        return groupOrderService.update(groupOrder);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2019-02-01 20:05:18.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return groupOrderService.delete(id);
    }

    @RequestMapping(value = "export")
    public ResponseEntity<String> export(@ModelAttribute GroupOrderQuery query , HttpServletRequest request, HttpServletResponse response) {
        logger.info(" export   query = {}",query);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        Page<GroupOrder> page = groupOrderService.find(query,pageRequest);
        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);
        String[] headers = { "订单号", "团单标题", "姓名", "手机号","性别","支付金额","下单时间", "支付时间","状态"};
        String fileName = "card-"+System.currentTimeMillis()+".xls";
        File targetFile = new File(path+"/"+ fileName);
        File pathf = new File(path);
        logger.info(" pathf.getPath() = {} " , pathf.getPath());
        logger.info(" targetFile.getPath() = {} " , targetFile.getPath());
        if(!pathf.exists()){
            pathf.mkdir();
        }
        if(!targetFile.exists()){
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info(" targetFile.exists() = {} " , targetFile.exists());
        try {
            List<String[]> dataList = new ArrayList<>();
            for (GroupOrder groupOrder : page.getContent()){
                if(groupOrder==null){
                    continue;
                }
                String[] row = new String[9];
                row[0] = groupOrder.getOrderId().toString();
                row[1] = groupOrder.getTitle();
                row[2] = groupOrder.getMemberName();
                row[3] = groupOrder.getPhone();
                row[4] = GenderEnum.getEnumByKey(groupOrder.getGender()).getDesc();
                row[5] = ""+groupOrder.getTotalFee();
                row[6] = groupOrder.getCreateTime();
                row[7] = groupOrder.getPayTime();
                row[8] = groupOrder.getShowStatus();
                dataList.add(row);
            }
            String sheetName = "拼团订单"+ ut.currentDate();
            ExportUtil.writeExcel(sheetName, headers, dataList, new FileOutputStream(targetFile));
            logger.info("filename = {}",targetFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map result = new HashMap();
        String id = IDUtils.getId();
        ConstData.data.put(id,fileName);
        String url = "/api/export/file/"+id;
        result.put("url",url);
        return ResponseUtil.success("导出拼团订单信息成功！",result);
    }

}

