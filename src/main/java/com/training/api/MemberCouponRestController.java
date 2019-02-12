package com.training.api;

import com.training.config.ConstData;
import com.training.domain.MemberCard;
import com.training.domain.MemberCoupon;
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
 * member_coupon API控制器
 * Created by huai23 on 2018-06-30 10:02:47.
 */ 
@RestController
@RequestMapping("/api/manage/memberCoupon")
public class MemberCouponRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCouponService memberCouponService;

    /**
     * 新增实体
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberCouponEntity memberCoupon,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_couponRestController  add  memberCoupon = {}",memberCoupon);
        return memberCouponService.add(memberCoupon);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberCouponQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<MemberCoupon> page = memberCouponService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberCouponQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberCouponService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberCoupon memberCoupon = memberCouponService.getById(id);
        if(memberCoupon==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberCoupon);
    }

    /**
     * 根据实体更新
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberCouponEntity memberCoupon,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  memberCoupon = {}",memberCoupon);
        return memberCouponService.update(memberCoupon);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-30 10:02:47.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberCouponService.delete(id);
    }

    /**
     * 核销优惠券
     * @param memberCoupon
     * Created by huai23 on 2018-06-30 10:02:47.
     */
    @RequestMapping (value = "useCoupon", method = RequestMethod.POST)
    public ResponseEntity<String> useCoupon(@RequestBody MemberCouponEntity memberCoupon,HttpServletRequest request, HttpServletResponse response){
        logger.info("  useCoupon  memberCoupon = {}",memberCoupon);
        return memberCouponService.useCoupon(memberCoupon);
    }

    /**
     * 分页查询优惠券核销记录
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-30 10:02:47.
     */
    @RequestMapping (value = "queryUseLog", method = RequestMethod.GET)
    public ResponseEntity<String> queryUseLog(@ModelAttribute MemberCouponQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info("  queryUseLog  MemberCouponQuery = {}",query);
        Page<MemberCoupon> page = memberCouponService.queryUseLog(query,pageRequest);
        return ResponseUtil.success(page);
    }


    /**
     * 导出优惠券核销记录
     * @param query
     * Created by huai23 on 2018-06-30 10:02:47.
     */
    @RequestMapping (value = "exportUseLog", method = RequestMethod.GET)
    public ResponseEntity<String> exportUseLog(@ModelAttribute MemberCouponQuery query ,HttpServletRequest request, HttpServletResponse response){
        logger.info("  exportUseLog  MemberCouponQuery = {}",query);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        Page<MemberCoupon> page = memberCouponService.queryUseLog(query,pageRequest);
        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);
        String[] headers = { "编号", "名称", "类型", "描述","创建人","核销人","核销时间"};
        String fileName = "CouponUseLog-"+System.currentTimeMillis()+".xls";
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
            for (MemberCoupon memberCoupon : page.getContent()){
                if(memberCoupon==null){
                    continue;
                }
                String type = "满减";
                if(memberCoupon.getType().equals("DZ")){
                    type = "打折";
                }
                String[] row = new String[12];
                row[0] = memberCoupon.getCouponId().toString();
                row[1] = memberCoupon.getTitle();
                row[2] = type;
                row[3] = memberCoupon.getContent();
                row[4] = memberCoupon.getCreator();
                row[5] = memberCoupon.getUseStaffName();
                row[6] = memberCoupon.getUseDate();
                dataList.add(row);
            }
            String sheetName = "优惠券核销记录";
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
        return ResponseUtil.success("导出优惠券核销记录信息成功！",result);
    }


}

