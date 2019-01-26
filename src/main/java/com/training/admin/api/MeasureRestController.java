package com.training.admin.api;

import com.training.admin.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户 API控制器
 */
@RestController
@RequestMapping("/measure")
public class MeasureRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MeasureService measureService;

    @GetMapping("query")
    public Object query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info(" MeasureRestController  query     ");
        measureService.queryAll();
        return "MeasureRestController  query执行成功";
    }

}
