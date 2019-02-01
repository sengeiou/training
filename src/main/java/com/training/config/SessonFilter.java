package com.training.config;

import com.alibaba.fastjson.JSONObject;
import com.training.domain.Member;
import com.training.domain.Staff;
import com.training.domain.User;
import com.training.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class SessonFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtUtil jwt;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
        HttpServletRequest request = (HttpServletRequest)req;

//        logger.info("request.getRequestURL() = {}",request.getRequestURL());
//        logger.info("request.getRequestURI() = {}",request.getRequestURI());
//        logger.info("request.getContextPath() = {}",request.getContextPath());
//        logger.info("request.getServletPath() = {}",request.getServletPath());

//        for (Enumeration enumeration = request.getHeaderNames(); enumeration.hasMoreElements();) {
//            String s = (String) enumeration.nextElement();
//            String value= request.getHeader(s);
//            logger.info(" s = {} ,  value = {} ",s , value);
//        }
        // 请求的uri
        String uri = request.getRequestURI();
        String authorization = request.getHeader("Authorization");
        // 不过滤的uri
        String[] notFilter = new String[] {"/test","/auth/", "/api/upload","browser_error","/upload",".html","/api/wechat/code","/manual/","/exportReport",
                "/api/export/file", "missPwd", "/login", "/page" ,"/app/" ,"/logout", "/error","/refreshToken","/wechat/pay/callback","/measure/","/wechat/pay/group/callback","/api/groupOrder/addOrder",
                "/api/upload","/register","/authImage","/pic","/favicon.ico","index","api-doc","swagger",".js",".txt","/dingding","/static/","/admin","/public/","/order/",
                ".css",".jpg",".png",".jpeg",".gif","manualKpiReportPageForZJ.jsp"};
        // 是否过滤
        boolean doFilter = true;
        for (String s : notFilter)
        {
            if (uri.equals("/")){
                doFilter = false;
                break;
            }
            if (uri.indexOf(s) != -1){
                // 如果uri中包含不过滤的uri，则不进行过滤
                doFilter = false;
                break;
            }
        }
        logger.info(" request.getRequestURI() = {} , doFilter = {},  Authorization = {} , ",request.getRequestURI(),doFilter,authorization);
        if (doFilter) {
            // 执行过滤
            // 从session中获取登录者实体
            if (StringUtils.isEmpty(authorization)) {
//                boolean isAjaxRequest = isAjaxRequest(request);
//                if (isAjaxRequest) {
//                    response.setCharacterEncoding("UTF-8");
//                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "您已经太长时间没有操作,请刷新页面");
//                    return ;
//                }
                response.sendRedirect("/admin");
                return;
            } else {
                Claims claims = null;
                try {
                    claims = jwt.parseJWT(authorization);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("/api/error");
                    return;
                }
                String json = claims.getSubject();
                if(uri.indexOf("/manage/")>=0||uri.indexOf("/lessonSetting/")>=0||uri.indexOf("/api/memberLog")>=0){
                    Staff staff = JSONObject.parseObject(json, Staff.class);
                    req.setAttribute("staff",staff);
                }else{
                    Member member = JSONObject.parseObject(json, Member.class);
                    req.setAttribute("member",member);
                }
                logger.info("member={}",json);
                Staff staff = JSONObject.parseObject(json, Staff.class);
                if(StringUtils.isNotEmpty(staff.getStaffId())){
                    req.setAttribute("staff",staff);
                }
                // 如果session中存在登录者实体，则继续
                chain.doFilter(request, response);
            }
        } else {
            // 如果不执行过滤，则继续
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }

    /** 判断是否为Ajax请求
     * <功能详细描述>
     * @param request
     * @return 是true, 否false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }

}