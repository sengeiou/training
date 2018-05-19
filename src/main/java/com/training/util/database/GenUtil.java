package com.training.util.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huai23 on 2017/11/1.
 */
public class GenUtil {

    private static final String URL ="jdbc:mysql://localhost:3306/";
    private static final String NAME = "root";
    private static final String PASS = "jordan23";
    private static final String DRIVER ="com.mysql.jdbc.Driver";

    public static List<String> getAllTables(String table_schema){
        List<String> tables = new ArrayList<>();
        PreparedStatement pStemt = null;
        Connection con = null;
        String sql = "select table_name from information_schema.tables where table_schema='"+table_schema+"' and table_type='base table'";
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(URL+table_schema,NAME,PASS);
            pStemt = con.prepareStatement(sql);
            ResultSet rs = pStemt.executeQuery();
            while (rs.next()){
                String name = rs.getString(1);
//                System.out.println("name = "+name);
                tables.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     * @param str
     * @return
     */
    public static String initcap(String str) {
        char[] ch = str.toCharArray();
        if(ch[0] >= 'a' && ch[0] <= 'z'){
            ch[0] = (char)(ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 功能：获得列的数据类型
     * @param sqlType
     * @return
     */
    public static String sqlType2JavaType(String sqlType) {
        if(sqlType.equalsIgnoreCase("bit")){
            return "boolean";
        }else if(sqlType.toLowerCase().indexOf("tinyint")>=0){
            return "Integer";
        }else if(sqlType.equalsIgnoreCase("smallint")){
            return "Short";
        }else if(sqlType.equalsIgnoreCase("int")){
            return "Integer";
        }else if(sqlType.toLowerCase().indexOf("bigint")>=0){
            return "Long";
        }else if(sqlType.equalsIgnoreCase("float")){
            return "Float";
        }else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")){
            return "BigDecimal";
        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")){
            return "String";
        }else if(sqlType.equalsIgnoreCase("datetime")){
            return "Date";
        }
        return null;
    }

}
