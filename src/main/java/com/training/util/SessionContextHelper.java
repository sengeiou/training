package com.training.util;

import com.training.entity.AdminEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionContextHelper {

    public static AdminEntity getAdmin(){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        AdminEntity admin = (AdminEntity)session.getAttribute("admin");
        return admin;
    }

    public static void setAdmin(AdminEntity admin){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute("admin",admin);
    }

}
