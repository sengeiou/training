package com.training.api;

import com.training.config.ConstData;
import com.training.domain.Staff;
import com.training.service.*;
import com.training.entity.*;
import com.training.common.*;
import com.training.util.ExportUtil;
import com.training.util.IDUtils;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
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
 * staff API控制器
 * Created by huai23 on 2018-05-26 13:55:30.
 */ 
@RestController
@RequestMapping("/api/staff")
public class StaffRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StaffService staffService;

    /**
     * 新增实体
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody StaffEntity staff,HttpServletRequest request, HttpServletResponse response){
        logger.info(" staffRestController  add  staff = {}",staff);
        return staffService.add(staff);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute StaffQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        logger.info(" StaffRestController  find  name = {} ,  query = {}",name,query);
        if(StringUtils.isNotEmpty(name)){
            query.setCustname(name);
        }
        Page<Staff> page = staffService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "findForSelectList", method = RequestMethod.GET)
    public ResponseEntity<String> findForSelectList(@ModelAttribute StaffQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info(" findForSelectList   query = {}",query);
        Page<Staff> page = staffService.findForSelectList(query,pageRequest);
        return ResponseUtil.success(page);
    }


    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "staffMedalList", method = RequestMethod.GET)
    public ResponseEntity<String> staffMedalList(@ModelAttribute StaffQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        logger.info(" StaffRestController  staffMedalList  name = {} ,  query = {}",name,query);
        if(StringUtils.isNotEmpty(name)){
            query.setCustname(name);
        }
        Page<Staff> page = staffService.staffMedalList(query,pageRequest);
        return ResponseUtil.success(page);
    }


    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute StaffQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = staffService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        Staff staffDB = staffService.getById(id);
        if(staffDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(staffDB);
    }

    /**
     * 根据实体更新
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody StaffEntity staff,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  staff = {}",staff);
        return staffService.update(staff);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return staffService.delete(id);
    }


    /**
     * 根据实体更新
     * @param staff
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "resetPwd", method = RequestMethod.POST)
    public ResponseEntity<String> resetPwd(@RequestBody StaffEntity staff,HttpServletRequest request, HttpServletResponse response){
        logger.info("  resetPwd  staff = {}",staff);
        return staffService.resetPwd(staff);
    }


    @RequestMapping(value = "exportStaff")
    public ResponseEntity<String> exportStaff(@ModelAttribute StaffQuery query , HttpServletRequest request, HttpServletResponse response) {
        logger.info(" exportStaff   query = {}",query);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        String name = request.getParameter("name");
        logger.info(" StaffRestController  exportStaff  name = {} ,  query = {}",name,query);
        if(StringUtils.isNotEmpty(name)){
            query.setCustname(name);
        }
        Page<Staff> page = staffService.find(query,pageRequest);

        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);
        String[] headers = { "姓名", "手机号","门店","角色","后台角色", "会员数","KPI模板","当月KPI"};
        String fileName = "staff_info-"+System.currentTimeMillis()+".xls";
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
            for (Staff staff : page.getContent()){
                if(staff==null){
                    continue;
                }
                String[] row = new String[8];
                row[0] = staff.getCustname();
                row[1] = staff.getPhone();
                row[2] = staff.getStoreName();
                row[3] = staff.getJob();
                row[4] = staff.getRoleName();
                row[5] = ""+staff.getMemberCount();
                row[6] = staff.getTemplateName();
                row[7] = staff.getKpi();
                dataList.add(row);
            }
            String sheetName = "员工"+ ut.currentDate();
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
        return ResponseUtil.success("导出员工信息成功！",result);
    }


    /**
     * 离职
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "leave/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> leave(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  leave  id = {}",id);
        return staffService.leave(id);
    }

    /**
     * 复职
     * @param id
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "entry/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> entry(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  entry  id = {}",id);
        return staffService.entry(id);
    }

}

