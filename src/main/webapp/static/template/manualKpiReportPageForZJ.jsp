<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.training.config.ContextUtil"%>
<%@ page language="java" import="org.springframework.jdbc.core.JdbcTemplate"%>
<%
      System.out.println(" ****************     manualKpiReportPageForZJ.jsp   ");
      JdbcTemplate jdbcTemplate = (JdbcTemplate)ContextUtil.getBean("jdbcTemplate");
      System.out.println(" ****************     jdbcTemplate = "+jdbcTemplate);
//      List data = jdbcTemplate.queryForList(" select * from staff ");
//      for (int i = 0; i < data.size(); i++) {
//            System.out.println(data.get(i));
//      }

%>
<a href="/static/template/main.jsp">main</a>