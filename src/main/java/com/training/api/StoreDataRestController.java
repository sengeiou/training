package com.training.api;

import com.alibaba.fastjson.JSONObject;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.domain.MarketReportData;
import com.training.domain.StoreData;
import com.training.entity.StoreDataQuery;
import com.training.entity.StoreEntity;
import com.training.entity.StoreQuery;
import com.training.service.StoreDataService;
import com.training.service.StoreService;
import com.training.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * store API控制器
 * Created by huai23 on 2018-05-26 13:43:38.
 */ 
@RestController
@RequestMapping("/api/manage/store")
public class StoreDataRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StoreDataService storeDataService;

    /**
     * 查询
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "querySaleMoney", method = RequestMethod.GET)
    public ResponseEntity<String> querySaleMoney(@ModelAttribute StoreDataQuery query, HttpServletRequest request, HttpServletResponse response){
        List<StoreData> storeDataList = storeDataService.querySaleMoney(query);
        JSONObject jo = new JSONObject();
        jo.put("storeDataList", storeDataList);
        return ResponseUtil.success(jo);
    }

    /**
     * 查询
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryIncome", method = RequestMethod.GET)
    public ResponseEntity<String> queryIncome(@ModelAttribute StoreDataQuery query, HttpServletRequest request, HttpServletResponse response){
        List<StoreData> storeDataList = storeDataService.queryIncome(query);
        JSONObject jo = new JSONObject();
        jo.put("storeDataList", storeDataList);
        return ResponseUtil.success(jo);
    }

    /**
     * 查询
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryChangeRate", method = RequestMethod.GET)
    public ResponseEntity<String> queryChangeRate(@ModelAttribute StoreDataQuery query, HttpServletRequest request, HttpServletResponse response){
        List<StoreData> storeDataList = storeDataService.queryChangeRate(query);
        JSONObject jo = new JSONObject();
        jo.put("storeDataList", storeDataList);
        return ResponseUtil.success(jo);
    }

    /**
     * 查询
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryMemberData", method = RequestMethod.GET)
    public ResponseEntity<String> queryMemberData(@ModelAttribute StoreDataQuery query, HttpServletRequest request, HttpServletResponse response){
        List<StoreData> storeDataList = storeDataService.queryMemberData(query);
        JSONObject jo = new JSONObject();
        jo.put("storeDataList", storeDataList);
        return ResponseUtil.success(jo);
    }

    /**
     * 查询
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryManagerKpi", method = RequestMethod.GET)
    public ResponseEntity<String> queryManagerKpi(@ModelAttribute StoreDataQuery query, HttpServletRequest request, HttpServletResponse response){
        List<StoreData> storeDataList = storeDataService.queryManagerKpi(query);
        JSONObject jo = new JSONObject();
        jo.put("storeDataList", storeDataList);
        return ResponseUtil.success(jo);
    }

    /**
     * 查询
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryCoachKpi", method = RequestMethod.GET)
    public ResponseEntity<String> queryCoachKpi(@ModelAttribute StoreDataQuery query, HttpServletRequest request, HttpServletResponse response){
        List<StoreData> storeDataList = storeDataService.queryCoachKpi(query);
        JSONObject jo = new JSONObject();
        jo.put("storeDataList", storeDataList);
        return ResponseUtil.success(jo);
    }

    /**
     * 查询
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryMarketingReport", method = RequestMethod.GET)
    public ResponseEntity<String> queryMarketingReport(@ModelAttribute StoreDataQuery query, HttpServletRequest request, HttpServletResponse response){
        List<MarketReportData> dataList = storeDataService.queryMarketingReport(query);
        JSONObject jo = new JSONObject();
        jo.put("data", dataList);
        return ResponseUtil.success(jo);
    }

}

