package com.training.api;

import com.training.config.ConstData;
import com.training.domain.*;
import com.training.service.*;
import com.training.entity.*;
import com.training.common.*;
import com.training.util.ExportUtil;
import com.training.util.IDUtils;
import com.training.util.RequestContextHelper;
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
 * training API控制器
 * Created by huai23 on 2018-05-26 17:09:14.
 */ 
@RestController
@RequestMapping("/api/training")
public class TrainingRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrainingService trainingService;

    /**
     * 新增实体
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody TrainingEntity training,HttpServletRequest request, HttpServletResponse response){
        logger.info(" trainingRestController  add  training = {}",training);
        return trainingService.add(training);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute TrainingQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        query.setStatus(0);
        Page<Training> page = trainingService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute TrainingQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = trainingService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        Training training = trainingService.getById(id);
        if(training==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(training);
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody TrainingEntity training,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  training = {}",training);
        return trainingService.update(training);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 17:09:14.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return trainingService.delete(id);
    }

    /**
     * 查询训练课程
     * @param query
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "list", method = RequestMethod.GET)
    public ResponseEntity<String> list(@ModelAttribute TrainingQuery query ,HttpServletRequest request, HttpServletResponse response){
        logger.info(" list  query = {}",query);
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" list  memberRequest = {}",memberRequest);
        return trainingService.list(query);
    }


    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "findByStaff", method = RequestMethod.GET)
    public ResponseEntity<String> findByStaff(@ModelAttribute TrainingQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info(" TrainingRestController.findByStaff pageRequest = {} ",pageRequest);
        Page<Training> page = trainingService.findByStaff(query,pageRequest);
        logger.info(" TrainingRestController.findByStaff getContent().size() = {} ",page.getContent().size());
        return ResponseUtil.success(page);
    }


    @RequestMapping(value = "exportTrainingByStaff")
    public ResponseEntity<String> exportTrainingByStaff(@ModelAttribute TrainingQuery query , HttpServletRequest request, HttpServletResponse response) {
        logger.info(" exportTrainingByStaff   query = {}",query);
        query.setStaffId(query.getCoachId());
        query.setCoachId(null);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        Page<Training> page = trainingService.findByStaff(query,pageRequest);
        logger.info(" exportTrainingByStaff.findByStaff getContent().size() = {} ",page.getContent().size());
        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);
        String[] headers = { "上课日期", "上课时间","学员名称","会员手机号","课程名称","课卡类型","教练姓名","教练手机号", "状态"};
        String fileName = "training_by_staff-"+System.currentTimeMillis()+".xls";
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
            for (Training training : page.getContent()){
                if(training==null){
                    continue;
                }
                String[] row = new String[9];
                row[0] = training.getLessonDate();
                row[1] = training.getStartHour() + " - " +training.getEndHour();
                row[2] = training.getMember().getName();
                row[3] = training.getMember().getPhone();
                row[4] = training.getTitle();
                row[5] = CardTypeEnum.getEnumByKey(training.getCardType()).getDesc();
                row[6] = training.getCoachName();
                row[7] = training.getStaff().getPhone();
                row[8] = TrainingShowTagEnum.getEnumByKey(training.getShowTag()).getDesc();
                dataList.add(row);
            }
            String sheetName = "课程";
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
        return ResponseUtil.success("导出课程成功！",result);
    }


    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 17:09:14.
     */
    @RequestMapping (value = "querySignLog", method = RequestMethod.GET)
    public ResponseEntity<String> querySignLog(@ModelAttribute TrainingQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info(" TrainingRestController.querySignLog pageRequest = {} ",pageRequest);
        Page<SignLog> page = trainingService.querySignLog(query,pageRequest);
        logger.info(" TrainingRestController.querySignLog getContent().size() = {} ",page.getContent().size());
        return ResponseUtil.success(page);
    }


}

