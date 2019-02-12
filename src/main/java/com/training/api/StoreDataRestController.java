package com.training.api;

import com.alibaba.fastjson.JSONObject;
import com.training.common.GenderEnum;
import com.training.common.Page;
import com.training.common.PageRequest;
import com.training.config.ConstData;
import com.training.domain.*;
import com.training.entity.MemberQuery;
import com.training.entity.StoreDataQuery;
import com.training.entity.StoreEntity;
import com.training.entity.StoreQuery;
import com.training.service.StoreDataService;
import com.training.service.StoreService;
import com.training.util.ExportUtil;
import com.training.util.IDUtils;
import com.training.util.ResponseUtil;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
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

    /**
     * 查询次卡报表
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryFinanceTimesCardReport", method = RequestMethod.GET)
    public ResponseEntity<String> queryFinanceTimesCardReport(@ModelAttribute StoreDataQuery query, @ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info("  queryFinanceTimesCardReport  query = {} request = {} ",query,request);
        Page<FinanceTimesCardReportData> dataList = storeDataService.queryFinanceTimesCardReport(query,pageRequest);
        JSONObject jo = new JSONObject();
        jo.put("data", dataList.getContent());
        logger.info("  queryFinanceTimesCardReport  dataList = {}",dataList.getContent().size());
        return ResponseUtil.success(dataList);
    }

    /**
     * 查询月卡报表
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryFinanceMonthCardReport", method = RequestMethod.GET)
    public ResponseEntity<String> queryFinanceMonthCardReport(@ModelAttribute StoreDataQuery query, @ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info("  queryFinanceMonthCardReport  query = {} request = {} ",query,request);
        Page<FinanceMonthCardReportData> dataList = storeDataService.queryFinanceMonthCardReport(query,pageRequest);
        JSONObject jo = new JSONObject();
        jo.put("data", dataList.getContent());
        logger.info("  queryFinanceMonthCardReport  dataList = {}",dataList.getContent().size());
        return ResponseUtil.success(dataList);
    }

    /**
     * 查询员工薪资报表
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "queryFinanceStaffReport", method = RequestMethod.GET)
    public ResponseEntity<String> queryFinanceStaffReport(@ModelAttribute StoreDataQuery query, @ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        logger.info("  queryFinanceStaffReport  query = {} request = {} ",query,request);
        Page<FinanceStaffReportData> dataList = storeDataService.queryFinanceStaffReport(query,pageRequest);
        JSONObject jo = new JSONObject();
        jo.put("data", dataList);
        logger.info("  queryFinanceStaffReport  dataList = {}",dataList);
        return ResponseUtil.success(dataList);
    }


    @RequestMapping(value = "exportMarketingReport")
    public ResponseEntity<String> exportMarketingReport(@ModelAttribute StoreDataQuery query , HttpServletRequest request, HttpServletResponse response) {
        logger.info(" exportMarketingReport  start query = {}",query);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        List<MarketReportData> dataList = storeDataService.queryMarketingReport(query);
        logger.info(" exportMarketingReport  dataList.size() = {} ",dataList.size());
        String path = request.getSession().getServletContext().getRealPath("/export/member");
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


    /**
     * 导出次卡报表
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "exportFinanceTimesCardReport", method = RequestMethod.GET)
    public ResponseEntity<String> exportFinanceTimesCardReport(@ModelAttribute StoreDataQuery query,HttpServletRequest request, HttpServletResponse response){
        logger.info("  exportFinanceTimesCardReport  query = {} request = {} ",query,request);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        Page<FinanceTimesCardReportData> page = storeDataService.queryFinanceTimesCardReport(query,pageRequest);
        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);
        String[] headers = { "店面", "销售额", "销售课程节数", "待销耗课程金额","待消耗课程节数","耗课收入","耗课节数", "死课收入", "死课节数", "延期收入","转出金额","转出节数","转入金额","转入节数","退课金额","退课节数"};
        String fileName = "FinanceTimesCardReport-"+System.currentTimeMillis()+".xls";
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
        String month = "";
        try {
            List<String[]> dataList = new ArrayList<>();
            for (FinanceTimesCardReportData financeTimesCardReportData : page.getContent()){
                if(financeTimesCardReportData==null){
                    continue;
                }
                if(StringUtils.isEmpty(month)){
                    month = financeTimesCardReportData.getMonth();
                }
                String[] row = new String[16];
                row[0] = financeTimesCardReportData.getStoreName();
                row[1] = financeTimesCardReportData.getSaleMoney();
                row[2] = financeTimesCardReportData.getSaleLessonCount();
                row[3] = financeTimesCardReportData.getWaitingLessonMoney();
                row[4] = financeTimesCardReportData.getWaitingLessonCount();
                row[5] = financeTimesCardReportData.getUsedLessonMoney();
                row[6] = financeTimesCardReportData.getUsedLessonCount();

                row[7] = financeTimesCardReportData.getDeadLessonMoney();
                row[8] = financeTimesCardReportData.getDeadLessonCount();
                row[9] = financeTimesCardReportData.getDelayMoney();
                row[10] = financeTimesCardReportData.getOutLessonMoney();
                row[11] = financeTimesCardReportData.getOutLessonCount();
                row[12] = financeTimesCardReportData.getInLessonMoney();
                row[13] = financeTimesCardReportData.getInLessonCount();
                row[14] = financeTimesCardReportData.getBackLessonMoney();
                row[15] = financeTimesCardReportData.getBackLessonCount();

                dataList.add(row);
            }
            String sheetName = "次卡财务报表"+month;
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
        return ResponseUtil.success("导出次卡财务报表成功！",result);
    }

    /**
     * 导出月卡报表
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "exportFinanceMonthCardReport", method = RequestMethod.GET)
    public ResponseEntity<String> exportFinanceMonthCardReport(@ModelAttribute StoreDataQuery query,HttpServletRequest request, HttpServletResponse response){
        logger.info("  exportFinanceMonthCardReport  query = {} request = {} ",query,request);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        Page<FinanceMonthCardReportData> page = storeDataService.queryFinanceMonthCardReport(query,pageRequest);
        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);

        String[] headers = { "店面", "销售额", "销售天数", "待销耗课程金额","待消耗天数","耗课收入","消耗天数", "转出金额", "转入金额", "退课金额"};
        String fileName = "FinanceMonthCardReport-"+System.currentTimeMillis()+".xls";
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
        String month = "";
        try {
            List<String[]> dataList = new ArrayList<>();
            for (FinanceMonthCardReportData financeMonthCardReportData : page.getContent()){
                if(financeMonthCardReportData==null){
                    continue;
                }
                if(StringUtils.isEmpty(month)){
                    month = financeMonthCardReportData.getMonth();
                }
                String[] row = new String[10];
                row[0] = financeMonthCardReportData.getStoreName();
                row[1] = financeMonthCardReportData.getSaleMoney();
                row[2] = financeMonthCardReportData.getSaleDaysCount();
                row[3] = financeMonthCardReportData.getWaitingDaysMoney();
                row[4] = financeMonthCardReportData.getWaitingDaysCount();
                row[5] = financeMonthCardReportData.getUsedDaysMoney();
                row[6] = financeMonthCardReportData.getUsedDaysCount();
                row[7] = financeMonthCardReportData.getOutDaysMoney();
                row[8] = financeMonthCardReportData.getInDaysMoney();
                row[9] = financeMonthCardReportData.getBackDaysMoney();

                dataList.add(row);
            }
            String sheetName = "月卡财务报表"+month;
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
        return ResponseUtil.success("导出月卡财务报表成功！",result);
    }

    /**
     * 导出员工薪资报表
     * @param query
     * Created by huai23 on 2018-05-26 13:43:38.
     */
    @RequestMapping (value = "exportFinanceStaffReport", method = RequestMethod.GET)
    public ResponseEntity<String> exportFinanceStaffReport(@ModelAttribute StoreDataQuery query,HttpServletRequest request, HttpServletResponse response){
        logger.info("  exportFinanceStaffReport  query = {} request = {} ",query,request);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        Page<FinanceStaffReportData> page = storeDataService.queryFinanceStaffReport(query,pageRequest);

        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);

        String[] headers = { "姓名", "手机号", "店面", "角色","星级","KPI模板","KPI分数", "销售额", "续课额", "次卡私教课", "月卡1V1私教课", "月卡1V2私教课", "体验课", "特色课", "团体课"};
        String fileName = "FinanceStaffReport-"+System.currentTimeMillis()+".xls";
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
        String month = "";
        try {
            List<String[]> dataList = new ArrayList<>();
            for (FinanceStaffReportData financeStaffReportData : page.getContent()){
                if(financeStaffReportData==null){
                    continue;
                }
                if(StringUtils.isEmpty(month)){
                    month = financeStaffReportData.getMonth();
                }
                String[] row = new String[15];
                row[0] = financeStaffReportData.getStaffName();
                row[1] = financeStaffReportData.getPhone();
                row[2] = financeStaffReportData.getStoreName();
                row[3] = financeStaffReportData.getJob();
                row[4] = financeStaffReportData.getStar();
                row[5] = financeStaffReportData.getTemplateName();
                row[6] = financeStaffReportData.getKpiScore();
                row[7] = financeStaffReportData.getSaleMoney();
                row[8] = financeStaffReportData.getXkMoney();
                row[9] = financeStaffReportData.getTimesCardLessonCount();
                row[10] = financeStaffReportData.getMonthCardSingleLessonCount();
                row[11] = financeStaffReportData.getMonthCardMultiLessonCount();
                row[12] = financeStaffReportData.getTyCardMultiLessonCount();
                row[13] = financeStaffReportData.getSpecialLessonCount();
                row[14] = financeStaffReportData.getTeamLessonCount();
                dataList.add(row);
            }
            String sheetName = "员工薪资报表"+month;
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
        return ResponseUtil.success("导出员工薪资报表成功！",result);
    }

}

