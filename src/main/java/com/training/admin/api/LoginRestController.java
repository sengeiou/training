package com.training.admin.api;

import com.training.admin.service.LoginService;
import com.training.domain.Staff;
import com.training.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户 API控制器
 */
@RestController
@RequestMapping("/admin")
public class LoginRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwt;

    @PostMapping("login")
    public Object login(@RequestBody Staff staff, HttpServletRequest request, HttpServletResponse response){
        logger.info(" LoginRestController   login  staff = {}",staff);
        return loginService.doLogin(staff);
    }

}
