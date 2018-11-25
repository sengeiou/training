package com.training.api;

import com.alibaba.fastjson.JSONObject;
import com.training.util.IDUtils;
import com.training.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件 API控制器
 */
@RestController
@RequestMapping("/api/upload")
public class UploadFileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "image")
    public ResponseEntity<String> uploadImage(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        logger.info(" uploadImage  start File.separator = {}",File.separator);
        String path = request.getSession().getServletContext().getRealPath("/upload/image");
        String fileName = file.getOriginalFilename();
        logger.info(" fileName = {} ",fileName);
        logger.info(" path = {} ",path);
        fileName = IDUtils.getId()+"_"+fileName;

        File dic = new File(path);
        if(!dic.exists()){
            dic.mkdirs();
        }

        File targetFile = new File(path+"/"+fileName);
        File pathf = new File(path);
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
        try {
            file.transferTo(targetFile);
            logger.info(" targetFile.getPath() = {} ",targetFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jo = new JSONObject();
        jo.put("msg", "上传成功");
        jo.put("url", request.getContextPath()+"/upload/image/"+fileName);
        return ResponseUtil.success(jo);
    }

    @RequestMapping(value = "staffImage")
    public ResponseEntity<String> uploadStaffImage(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        logger.info(" staffImage  start File.separator = {}",File.separator);
        String path = request.getSession().getServletContext().getRealPath("/upload/staff");
        String fileName = file.getOriginalFilename();
        logger.info(" fileName = {} ",fileName);
        logger.info(" path = {} ",path);
        fileName = IDUtils.getId()+"_"+fileName;

        File dic = new File(path);
        if(!dic.exists()){
            dic.mkdirs();
        }
        File targetFile = new File(path+"/"+fileName);
        File pathf = new File(path);
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
        try {
            file.transferTo(targetFile);
            logger.info(" targetFile.getPath() = {} ",targetFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jo = new JSONObject();
        jo.put("msg", "上传成功");
        jo.put("url", request.getContextPath()+"/upload/staff/"+fileName);
        return ResponseUtil.success(jo);
    }

    @RequestMapping(value = "teamLessonImage")
    public ResponseEntity<String> uploadTeamLessonImage(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        logger.info(" uploadTeamLessonImage  start File.separator = {}",File.separator);
        String path = request.getSession().getServletContext().getRealPath("/upload/team");
        File filePath = new File(path);
        if(!filePath.exists()){
            filePath.mkdir();
        }
        String fileName = file.getOriginalFilename();
        logger.info(" fileName = {} ",fileName);
        logger.info(" path = {} ",path);
        fileName = IDUtils.getId()+"_"+fileName;

        File dic = new File(path);
        if(!dic.exists()){
            dic.mkdirs();
        }
        File targetFile = new File(path+"/"+fileName);
        File pathf = new File(path);
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
        try {
            file.transferTo(targetFile);
            logger.info(" targetFile.getPath() = {} ",targetFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jo = new JSONObject();
        jo.put("msg", "上传成功");
        jo.put("url", request.getContextPath()+"/upload/team/"+fileName);
        return ResponseUtil.success(jo);
    }

    @RequestMapping(value = "rotationChart")
    public ResponseEntity<String> uploadRotationChart(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
        logger.info(" uploadRotationChart  start File.separator = {}",File.separator);
        String path = request.getSession().getServletContext().getRealPath("/upload/rotation");
        File filePath = new File(path);
        if(!filePath.exists()){
            filePath.mkdir();
        }
        String fileName = file.getOriginalFilename();
        logger.info(" fileName = {} ",fileName);
        logger.info(" path = {} ",path);
        fileName = IDUtils.getId()+"_"+fileName;

        File dic = new File(path);
        if(!dic.exists()){
            dic.mkdirs();
        }
        File targetFile = new File(path+"/"+fileName);
        File pathf = new File(path);
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
        try {
            file.transferTo(targetFile);
            logger.info(" targetFile.getPath() = {} ",targetFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jo = new JSONObject();
        jo.put("msg", "上传成功");
        jo.put("url", request.getContextPath()+"/upload/rotation/"+fileName);
        return ResponseUtil.success(jo);
    }

}
