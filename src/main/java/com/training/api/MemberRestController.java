package com.training.api;

import com.training.config.ConstData;
import com.training.domain.Lesson;
import com.training.domain.Member;
import com.training.domain.Training;
import com.training.service.*;
import com.training.entity.*;
import com.training.common.*;
import com.training.util.IDUtils;
import com.training.util.RequestContextHelper;
import com.training.util.ut;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.training.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * member API控制器
 * Created by huai23 on 2018-05-26 13:39:33.
 */
@RestController
@RequestMapping("/api/member")
public class MemberRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCouponService memberCouponService;

    /**
     * 新增实体
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody MemberEntity member,HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  add  member = {}",member);
        return memberService.add(member);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<Member> page = memberService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "memberMedalList", method = RequestMethod.GET)
    public ResponseEntity<String> memberMedalList(@ModelAttribute MemberQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<Member> page = memberService.memberMedalList(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute MemberQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = memberService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        MemberEntity memberDB = memberService.getById(id);
        if(memberDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(memberDB);
    }

    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody MemberEntity member,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  member = {}",member);
        return memberService.update(member);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-05-26 13:39:33.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return memberService.delete(id);
    }

    /**
     * 根据ID闭环
     * @param id
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "close/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> close(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  close  id = {}",id);
        return memberService.close(id);
    }

    /**
     * 根据ID恢复为意向
     * @param id
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "restore/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> restore(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  restore  id = {}",id);
        return memberService.restore(id);
    }

    /**
     * 根据手机号码发送验证码
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "sendCode", method = RequestMethod.POST)
    public ResponseEntity<String> sendCode(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  sendCode  member = {}",member);
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" memberRestController  sendCode  memberRequest = {}",memberRequest);
        return memberService.sendCode(member);
    }

    /**
     * 根据手机号码发送验证码
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "sendOrderCode", method = RequestMethod.POST)
    public ResponseEntity<String> sendOrderCode(@ModelAttribute Member member, HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  sendOrderCode  member = {}",member);
        return memberService.sendOrderCode(member);
    }

    /**
     * 根据手机号码绑定会员
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "bind", method = RequestMethod.POST)
    public ResponseEntity<String> bind(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  bind  member = {}",member);
        Member memberRequest = RequestContextHelper.getMember();
        logger.info(" memberRestController  bind  memberRequest = {}",memberRequest);
        return memberService.bind(member);
    }

    /**
     * 根据ID查询实体
     * @param memberId
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "getValidLessonType/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<String> getValidLessonType(@PathVariable String memberId,HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  getValidLessonType  memberId = {}",memberId);
        return memberService.getValidLessonType(memberId);
    }

    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "modify", method = RequestMethod.POST)
    public ResponseEntity<String> modify(@RequestBody MemberEntity member,HttpServletRequest request, HttpServletResponse response){
        logger.info("  modify  member = {}",member);
        return memberService.modify(member);
    }

    /**
     * 根据实体更新
     * @param training
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "signIn", method = RequestMethod.POST)
    public ResponseEntity<String> signIn(@RequestBody Training training, HttpServletRequest request, HttpServletResponse response){
        logger.info("  signIn  training = {}",training);
        return memberService.signIn(training);
    }

    /**
     * 根据ID查询实体
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "getCanUseCardList", method = RequestMethod.GET)
    public ResponseEntity<String> getCanUseCardList(@ModelAttribute Lesson lesson, HttpServletRequest request, HttpServletResponse response){
        logger.info(" memberRestController  getCanUseCardList  lesson = {}",lesson);
        return memberService.getCanUseCardList(lesson);
    }


    /**
     * 根据实体更新
     * @param member
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "updateImage", method = RequestMethod.POST)
    public ResponseEntity<String> updateImage(@RequestBody MemberEntity member,HttpServletRequest request, HttpServletResponse response){
//        logger.info("  updateImage  member = {}",member);
        return memberService.updateImage(member);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-30 10:02:47.
     */
    @RequestMapping (value = "couponList", method = RequestMethod.GET)
    public ResponseEntity<String> couponList(@ModelAttribute MemberCouponQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Member member = RequestContextHelper.getMember();
        String memberId = member.getMemberId();
        query.setMemberId(memberId);
        logger.info(" memberRestController  couponList  query = {} , pageRequest = {} ",query,pageRequest);
        return memberCouponService.couponList(query,pageRequest);
    }

    /**
     * 注销微信用户
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "logoff", method = RequestMethod.POST)
    public ResponseEntity<String> logoff(HttpServletRequest request, HttpServletResponse response){
        logger.info("  logoff   ");
        return memberService.logoff();
    }

    /**
     * 注销微信用户
     * Created by huai23 on 2018-05-26 13:39:33.
     */
    @RequestMapping (value = "logoffByStaff", method = RequestMethod.POST)
    public ResponseEntity<String> logoffByStaff(HttpServletRequest request, HttpServletResponse response){
        logger.info("  logoffByStaff   ");
        return memberService.logoffByStaff();
    }



    @RequestMapping(value = "exportMember")
    public ResponseEntity<String> exportMember(@ModelAttribute MemberQuery query , HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        logger.info(" exportContact  start MemberQuery = {}",query);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(100000);
        Page<Member> page = memberService.find(query,pageRequest);
        logger.info(" exportMember  page.getContent().size() = {} ",page.getContent().size());
        String path = request.getSession().getServletContext().getRealPath("/export/member");
        logger.info(" path = {} ",path);
        String[] headers = { "姓名", "手机号","性别","年龄","身高", "来源" , "教练", "备注", "状态", "创建时间"};
        String fileName = "member-"+System.currentTimeMillis()+".xls";
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
            exportMemberExcel("member", headers,page.getContent(), new FileOutputStream(targetFile));
            logger.info("filename = {}",targetFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map result = new HashMap();
        String id = IDUtils.getId();
        ConstData.data.put(id,fileName);
        String url = "/api/export/file/"+id;
        result.put("url",url);
        return ResponseUtil.success("导出会员成功！",result);
    }


    public void exportMemberExcel(String title, String[] headers, List<Member> dataset, OutputStream out) {
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
                Member member = dataset.get(i);
                HSSFCell cell0 = rowContact.createCell(0);
                cell0.setCellStyle(style2);
                cell0.setCellValue(new HSSFRichTextString(member.getName()));

                HSSFCell cell1 = rowContact.createCell(1);
                cell1.setCellStyle(style2);
                cell1.setCellValue(new HSSFRichTextString(member.getPhone()));

                HSSFCell cell2 = rowContact.createCell(2);
                cell2.setCellStyle(style2);
                if(member.getGender()!=null){
                    cell2.setCellValue(new HSSFRichTextString(GenderEnum.getEnumByKey(member.getGender()).getDesc()));
                }else{
                    cell2.setCellValue(new HSSFRichTextString(" "));
                }
                HSSFCell cell3 = rowContact.createCell(3);
                cell3.setCellStyle(style2);
                if(member.getAge()==null){
                    cell3.setCellValue(new HSSFRichTextString(" "));
                }else{
                    cell3.setCellValue(new HSSFRichTextString(""+member.getAge()));
                }

                HSSFCell cell4 = rowContact.createCell(4);
                cell4.setCellStyle(style2);
                if(member.getHeight()==null){
                    cell4.setCellValue(new HSSFRichTextString(" "));
                }else{
                    cell4.setCellValue(new HSSFRichTextString(""+member.getHeight()));
                }

                HSSFCell cell5 = rowContact.createCell(5);
                cell5.setCellStyle(style2);
                cell5.setCellValue(new HSSFRichTextString(member.getOrigin()));

                HSSFCell cell6 = rowContact.createCell(6);
                cell6.setCellStyle(style2);
                cell6.setCellValue(new HSSFRichTextString(member.getCoachName()));

                HSSFCell cell7 = rowContact.createCell(7);
                cell7.setCellStyle(style2);
                cell7.setCellValue(new HSSFRichTextString(member.getRemark()));

                HSSFCell cell8 = rowContact.createCell(8);
                cell8.setCellStyle(style2);
                cell8.setCellValue(new HSSFRichTextString(member.getShowStatus()));

                HSSFCell cell9 = rowContact.createCell(9);
                cell9.setCellStyle(style2);
                cell9.setCellValue(new HSSFRichTextString(ut.df_day.format(member.getCreated())));

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
     * 会员能否自己停课
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "canPauseBySelf", method = RequestMethod.GET)
    public ResponseEntity<String> canPauseBySelf(HttpServletRequest request, HttpServletResponse response){
        Member member = RequestContextHelper.getMember();
        String memberId = member.getMemberId();
        logger.info("  canPauseBySelf  memberId = {}",memberId);
        return memberService.canPauseBySelf(memberId);
    }


    /**
     * 会员能否自己复课
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "canRestoreBySelf", method = RequestMethod.GET)
    public ResponseEntity<String> canRestoreBySelf(HttpServletRequest request, HttpServletResponse response){
        Member member = RequestContextHelper.getMember();
        String memberId = member.getMemberId();
        logger.info("  canRestoreBySelf  memberId = {}",memberId);
        return memberService.canRestoreBySelf(memberId);
    }


    /**
     * 会员自己停课
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "pauseMemberBySelf", method = RequestMethod.POST)
    public ResponseEntity<String> pauseMemberBySelf(HttpServletRequest request, HttpServletResponse response){
        Member member = RequestContextHelper.getMember();
        String memberId = member.getMemberId();
        logger.info("  pauseMemberBySelf  memberId = {}",memberId);
        return memberService.pauseMemberBySelf(memberId);
    }

    /**
     * 会员自己复课
     * Created by huai23 on 2018-05-26 13:55:30.
     */
    @RequestMapping (value = "restoreMemberBySelf", method = RequestMethod.POST)
    public ResponseEntity<String> restoreMemberBySelf(HttpServletRequest request, HttpServletResponse response){
        Member member = RequestContextHelper.getMember();
        String memberId = member.getMemberId();
        logger.info("  restoreMemberBySelf  memberId = {}",memberId);
        return memberService.restoreMemberBySelf(memberId);
    }

}

