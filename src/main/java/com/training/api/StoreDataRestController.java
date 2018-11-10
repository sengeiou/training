package com.training.api;

import com.alibaba.fastjson.JSONObject;
import com.training.common.GenderEnum;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.config.ConstData;
import com.training.domain.MarketReportData;
import com.training.domain.Member;
import com.training.domain.StoreData;
import com.training.entity.MemberQuery;
import com.training.entity.StoreDataQuery;
import com.training.entity.StoreEntity;
import com.training.entity.StoreQuery;
import com.training.service.StoreDataService;
import com.training.service.StoreService;
import com.training.util.IDUtils;
import com.training.util.ResponseUtil;
import com.training.util.ut;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        logger.info("  queryMarketingReport  query = {}",query);
        List<MarketReportData> dataList = storeDataService.queryMarketingReport(query);
        JSONObject jo = new JSONObject();
        jo.put("data", dataList);
        logger.info("  queryMarketingReport  dataList = {}",dataList);
        return ResponseUtil.success(jo);
    }


    @RequestMapping(value = "exportMarketingReport")
    public ResponseEntity<String> exportMarketingReport(@ModelAttribute StoreDataQuery query , HttpServletRequest request, HttpServletResponse response) {
        logger.info(" exportMarketingReport  start query = {}",query);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        List<MarketReportData> dataList = storeDataService.queryMarketingReport(query);
        logger.info(" exportMarketingReport  dataList.size() = {} ",dataList.size());
        String path = request.getSession().getServletContext().getRealPath("/export/report");
        logger.info(" path = {} ",path);
        String[] headers = { "门店", "来源","新增数量","到店数量","成交数量", "成交金额"};
        String fileName = "MarketingReport-"+System.currentTimeMillis()+".xls";
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
            exportMarketingReportExcel("member", headers,dataList, new FileOutputStream(targetFile));
            logger.info("filename = {}",targetFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map result = new HashMap();
        String id = IDUtils.getId();
        ConstData.data.put(id,fileName);
        String url = "/api/export/file/"+id;
        result.put("url",url);
        return ResponseUtil.success("导出营销报表成功！",result);
    }

    public void exportMarketingReportExcel(String title, String[] headers, List<MarketReportData> dataset, OutputStream out) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        for (short i = 0; i < dataset.size(); i++)
        {
            try
            {
                HSSFRow rowContact = sheet.createRow(i+1);
                MarketReportData marketReportData = dataset.get(i);
                HSSFCell cell0 = rowContact.createCell(0);
                cell0.setCellStyle(style2);
                cell0.setCellValue(new HSSFRichTextString(marketReportData.getStoreName()));

                HSSFCell cell1 = rowContact.createCell(1);
                cell1.setCellStyle(style2);
                cell1.setCellValue(new HSSFRichTextString(marketReportData.getOrigin()));

                HSSFCell cell2 = rowContact.createCell(2);
                cell2.setCellStyle(style2);
                if(marketReportData.getNewCount()!=null){
                    cell2.setCellValue(marketReportData.getNewCount());
                }else{
                    cell2.setCellValue(new HSSFRichTextString(" "));
                }
                HSSFCell cell3 = rowContact.createCell(3);
                cell3.setCellStyle(style2);
                if(marketReportData.getArriveCount()==null){
                    cell3.setCellValue(new HSSFRichTextString(" "));
                }else{
                    cell3.setCellValue(new HSSFRichTextString(""+marketReportData.getArriveCount()));
                }

                HSSFCell cell4 = rowContact.createCell(4);
                cell4.setCellStyle(style2);
                if(marketReportData.getOrderCount()==null){
                    cell4.setCellValue(new HSSFRichTextString(" "));
                }else{
                    cell4.setCellValue(new HSSFRichTextString(""+marketReportData.getOrderCount()));
                }

                HSSFCell cell5 = rowContact.createCell(5);
                cell5.setCellStyle(style2);
                cell5.setCellValue(new HSSFRichTextString(marketReportData.getMoney()));

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            workbook.write(out);
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

