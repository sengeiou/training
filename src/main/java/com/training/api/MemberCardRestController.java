package com.training.api;

import com.training.config.ConstData;
import com.training.domain.MemberCard;
import com.training.domain.Training;
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
 * member_card API控制器
 * Created by huai23 on 2018-05-26 13:53:17.
 */ 
@RestController
@RequestMapping("/api/memberCard")
public class MemberCardRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberCardService memberCardService;

    /**
     * 新增实体
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberCardEntity memberCard,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_cardRestController  add  memberCard = {}",memberCard);
        return memberCardService.add(memberCard);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberCardQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_cardRestController  find  query = {}",query);
        Page<MemberCard> page = memberCardService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    @RequestMapping (value = "findPro", method = RequestMethod.GET)
    public ResponseEntity<String> findPro(@ModelAttribute MemberCardQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info(" member_cardRestController  findPro  query = {}",query);
        Page<MemberCard> page = memberCardService.findPro(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberCardQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberCardService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberCard memberCard = memberCardService.getById(id);
        if(memberCard==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberCard);
    }

    /**
     * 根据实体更新
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberCardEntity memberCard,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  memberCard = {}",memberCard);
        return memberCardService.update(memberCard);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:53:17.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberCardService.delete(id);
    }


    /**
     * 根据ID删除
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    @RequestMapping (value = "freeDelay", method = RequestMethod.POST)
    public ResponseEntity<String> freeDelay(@RequestBody MemberCard memberCard,HttpServletRequest request, HttpServletResponse response){
        logger.info("  freeDelay  memberCard = {}",memberCard);
        return memberCardService.freeDelay(memberCard);
    }


    /**
     * 根据ID删除
     * @param memberCard
     * Created by huai23 on 2018-05-26 13:53:17.
     */
    @RequestMapping (value = "advanceCard", method = RequestMethod.POST)
    public ResponseEntity<String> advanceCard(@RequestBody MemberCard memberCard,HttpServletRequest request, HttpServletResponse response){
        logger.info("  advanceCard  memberCard = {}",memberCard);
        return memberCardService.advanceCard(memberCard);
    }


    @RequestMapping(value = "exportCard")
    public ResponseEntity<String> exportCard(@ModelAttribute MemberCardQuery query , HttpServletRequest request, HttpServletResponse response) {
        logger.info(" exportCard   query = {}",query);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        Page<MemberCard> page = memberCardService.findPro(query,pageRequest);
        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);
        String[] headers = { "所属门店", "卡号", "会员姓名", "会员电话","卡片名称","次数","生效时间", "失效时间","健身教练","销售教练","开卡门店","使用门店","剩余次数","剩余金额"};
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
            for (MemberCard memberCard : page.getContent()){
                if(memberCard==null){
                    continue;
                }
                String[] row = new String[14];
                row[0] = memberCard.getMemberStoreName();
                row[1] = memberCard.getCardNo();
                row[2] = memberCard.getMemberName();
                row[3] = memberCard.getPhone();
                row[4] = CardTypeEnum.getEnumByKey(memberCard.getType()).getDesc();
                row[5] = ""+memberCard.getTotal();
                row[6] = memberCard.getStartDate();
                row[7] = memberCard.getEndDate();
                row[8] = memberCard.getCoachName();
                row[9] = memberCard.getSaleStaffName();
                row[10] = memberCard.getStoreName();
                row[11] = memberCard.getMemberStoreName();
                row[12] = ""+memberCard.getCount();
                row[13] = ""+memberCard.getRealFee();
                dataList.add(row);
            }
            String sheetName = "课卡"+ ut.currentDate();
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
        return ResponseUtil.success("导出会员卡信息成功！",result);
    }

}

