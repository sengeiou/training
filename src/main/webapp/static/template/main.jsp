<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.training.config.ContextUtil"%>
<%@ page language="java" import="org.springframework.jdbc.core.JdbcTemplate"%>
<%@ include file="SessionFilter.jsp" %>
<%
System.out.println(" ****************     main.jsp   ");
JdbcTemplate jdbcTemplate = (JdbcTemplate)ContextUtil.getBean("jdbcTemplate");


%>
main.jsp