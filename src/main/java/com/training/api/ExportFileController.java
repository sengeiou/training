package com.training.api;

import com.training.config.ConstData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 上传文件 API控制器
 */
@RestController
@RequestMapping("/api/export")
public class ExportFileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "file/{id}")
    public ResponseEntity<String> file(@PathVariable String id, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        logger.info(" exportContact  start File.id = {}",id);
        try {
            response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
            String fileName = "member-1538063172871.xls";
            logger.info(" exportContact  ConstData.data = {}",ConstData.data);
            if(ConstData.data.containsKey(id)){
                fileName = ConstData.data.get(id).toString();
            }
//          getResponse().setContentType("application/x-download;charset=UTF-8");
            response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes(),"iso-8859-1"));
            //读取文件
            String path = request.getSession().getServletContext().getRealPath("/export/member/");

            InputStream in = new FileInputStream(path+fileName);
            OutputStream out = response.getOutputStream();
            //写文件
            int b;
            while((b=in.read())!= -1)
            {
                out.write(b);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
