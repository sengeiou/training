package com.training.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.training.common.SysLogEnum;
import com.training.dao.*;
import com.training.entity.*;
import com.training.service.MemberCardService;
import com.training.service.MemberService;
import com.training.service.SysLogService;
import com.training.util.IDUtils;
import com.training.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Date;
import java.util.Map;

/**
 * role API控制器
 * Created by huai23 on 2017-11-03 16:44:48.
 */ 
@Controller
public class CallbackController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SysLogDao sysLogDao;

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberBodyDao memberBodyDao;

    @Autowired
    private MeasurementDao measurementDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 支付回调接口
     * Created by huai23 on 2018-06-03 16:44:48.
     */
    @RequestMapping (value = "/measure/callback")
    public Object callback(@RequestBody String data, HttpServletRequest request, HttpServletResponse response){
        logger.info("  measure  callback  data = {} ",data);
        JSONObject result = JSON.parseObject(data);
        JSONObject detail = JSON.parseObject(result.getString("data"));
        String measurement_str = detail.getString("measurement");
        String composition = detail.getString("composition");
        String posture = detail.getString("posture");
        String girth = detail.getString("girth");
        System.out.println("measurement:"+measurement_str);
        System.out.println("composition:"+composition);
        System.out.println("posture:"+posture);
        System.out.println("girth:"+girth);
        if(composition==null){
            composition="";
        }
        if(posture==null){
            posture="";
        }
        if(girth==null){
            girth="";
        }

        JSONObject measurement = JSON.parseObject(measurement_str);
        System.out.println("measurement:"+measurement);
        String id = measurement.getString("id");
        String device_sn = measurement.getString("device_sn");
        String gender = measurement.getString("gender");
        String age = measurement.getString("age");
        String height = measurement.getString("height");
        String weight = measurement.getString("weight");
        String outline = measurement.getString("outline");
        String start_time = measurement.getString("start_time");
        String measure_date = start_time.substring(0,7);

        System.out.println("measure_date:"+measure_date);

        JSONObject outlineObj = JSON.parseObject(outline);
        String bmi = outlineObj.getString("bmi");

        String phone = measurement.getString("phone");
        System.out.println("id:"+id+" , phone="+phone);


        MemberEntity memberEntity = memberDao.getByPhone(phone);

        MeasurementEntity measurementEntity = measurementDao.getById(id);
        if(measurementEntity==null) {
            String bodyId = "";
            String memberId = "";
            if (memberEntity != null) {
                memberId = memberEntity.getMemberId();
                bodyId = IDUtils.getId();
                String coachId = "";
                StaffEntity staffEntity = staffDao.getById(memberEntity.getCoachStaffId());
                if (staffEntity != null && StringUtils.isNotEmpty(staffEntity.getOpenId())) {
                    MemberEntity staff = memberDao.getByOpenId(staffEntity.getOpenId());
                    if (staff != null) {
                        coachId = staff.getMemberId();
                    }
                }
                MemberBodyEntity memberBody = new MemberBodyEntity();
                memberBody.setBodyId(bodyId);
                memberBody.setCoachId(coachId);
                memberBody.setMemberId(memberId);
                memberBody.setBmi(bmi);
                memberBody.setHeight(height);
                memberBody.setWeight(weight);
                memberBody.setMeasurementId(id);
                int n = memberBodyDao.add(memberBody);

                try {
                    MemberEntity memberUpdate = new MemberEntity();
                    memberUpdate.setMemberId(memberId);
                    memberUpdate.setAge(Integer.parseInt(age));
                    memberUpdate.setHeight(Integer.parseInt(height));
                    int m = memberDao.update(memberUpdate);
                } catch (Exception e) {
                    logger.error(" updateMember age And height error memberId = {}, age = {},height= {}", memberId, age, height);
                }
            }
            String sql = "insert into measurement (measurement_id,body_id,member_id,device_sn,gender,age,height,weight,phone,outline,measurement,composition,posture,girth,measure_date,start_time,REMARK,created,modified) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now()) ";
            jdbcTemplate.update(sql, new Object[]{id, bodyId, memberId, device_sn, gender, age, height, weight, phone, outline, measurement.toJSONString(),composition,posture,girth, measure_date, start_time , "callback"});

        }

        return ResponseUtil.success("");
    }

}

