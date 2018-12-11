package com.training.config;

import com.training.util.ut;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by huai23 on 2018/12/11.
 */
@WebServlet(name = "ContextUtil", urlPatterns = "/ContextUtil",loadOnStartup=1)
public class ContextUtil  extends HttpServlet {

    private static WebApplicationContext context;

    @Override
    public void init() throws ServletException {
        context = WebApplicationContextUtils.getWebApplicationContext(this
                .getServletContext());
        ut.log(" ******   ContextUtil ************** context: "+context);
    }

    public static Object getBean(String id) {
        Object bean = null;
        try{
            bean = context.getBean(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static WebApplicationContext getContext() {
        return context;
    }


}
