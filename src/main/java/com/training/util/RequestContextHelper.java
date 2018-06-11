package com.training.util;

import com.training.domain.Member;
import com.training.domain.Staff;
import com.training.domain.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestContextHelper {

    public static User getUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = (User)request.getAttribute("user");
        return user;
    }

    public static Member getMember(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Member member = (Member)request.getAttribute("member");
        return member;
    }

    public static Staff getStaff(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Staff staff = (Staff)request.getAttribute("staff");
        return staff;
    }

}
