<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.training.config.ContextUtil"%>
<%@ page language="java" import="com.training.domain.User"%>
<%@ page language="java" import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@ page import="com.training.domain.User" %>
<%
User user = (User)session.getAttribute("user");
System.out.println(" ****************     session.jsp  user = "+user);
if(user==null){
    System.out.println(" **************** goto index ");
    response.sendRedirect("/static/template/index.jsp");
}
%>