<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.training.config.ContextUtil"%>
<%@ page language="java" import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.training.domain.User" %>
<%
System.out.println(" ****************     index.jsp   ");
JdbcTemplate jdbcTemplate = (JdbcTemplate)ContextUtil.getBean("jdbcTemplate");
String id = request.getParameter("id");
if(StringUtils.isNotEmpty(id)){
    session.setAttribute("user",new User());
}
%>
index.jsp
<a href="/static/template/main.jsp">main</a>

<a href="/static/template/index.jsp?id=123">login</a>