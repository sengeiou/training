package com.training.util.database;

import com.training.util.Underline2CamelUtil;
import com.training.util.ut;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;

public class GenEntityMysql {

    private static final String packageOutPath = "com.training.entity";//指定实体生成所在包的路径
    private static final String packageOfService = "com.training.service";//指定实体生成所在包的路径
    private static final String packageOfDao = "com.training.dao";//指定实体生成所在包的路径
    private static final String packageOfRepository = "com.training.repository";//指定实体生成所在包的路径
    private static final String packageOfEntity = "com.training.entity";//指定实体生成所在包的路径
    private static final String authorName = "huai23";//作者名字
    private static final  String table_schema = "training";//数据库名
    private String[] colnames; // 列名数组
    private String[] colTypes; //列名类型数组
    private int[] colSizes; //列名大小数组

    private static final  String entity_suffix = "Entity";
    private static final  String query_suffix = "Query";

    private boolean lombok = true; // 是否需要导入lombok.Data注解
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*

    //数据库连接
    private static final String URL ="jdbc:mysql://127.0.0.1:3306/training?characterEncoding=utf-8";
    private static final String NAME = "root";
    private static final String PASS = "jordan23";
    private static final String DRIVER ="com.mysql.jdbc.Driver";

    //创建连接
    Connection con = null;
    public GenEntityMysql() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(URL,NAME,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEntitySource(String tableName){
        PreparedStatement pStemt = null;
        String sql = "select * from " + tableName;
        String content = "";
        try {
            pStemt = con.prepareStatement(sql);
            ResultSetMetaData rsmd = pStemt.getMetaData();
            int size = rsmd.getColumnCount();   //统计列
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                if (colTypes[i].equalsIgnoreCase("datetime")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            content = parseEntity(tableName,colnames, colTypes, colSizes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String getQuerySource(String tableName){
        PreparedStatement pStemt = null;
        String sql = "select * from " + tableName;
        String content = "";
        try {
            pStemt = con.prepareStatement(sql);
            ResultSetMetaData rsmd = pStemt.getMetaData();
            int size = rsmd.getColumnCount();   //统计列
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                if (colTypes[i].equalsIgnoreCase("datetime")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            content = parseQuery(tableName,colnames, colTypes, colSizes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return content;
    }

    public void makeEntityFile(String tableName,String source){
        try {
            String fileName = getEntityClassName(tableName) + ".java";
            File directory = new File("");
            System.out.println("绝对路径："+directory.getAbsolutePath());
            String outputPath = directory.getAbsolutePath()+ "/src/main/java/"+this.packageOutPath.replace(".", "/")+"/"+ fileName;
            System.out.println("outputPath:"+outputPath);
            File javaFile = new File(outputPath);
            if(javaFile.exists()){
                System.out.println("文件已存在！"+javaFile.getName());
//                return;
                javaFile.delete();
            }
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(source);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeQueryFile(String tableName,String source){
        try {
            String fileName = getQueryClassName(tableName) + ".java";
            File directory = new File("");
            System.out.println("绝对路径："+directory.getAbsolutePath());
            String outputPath = directory.getAbsolutePath()+ "/src/main/java/"+this.packageOutPath.replace(".", "/")+"/"+ fileName;
            System.out.println("outputPath:"+outputPath);
            File javaFile = new File(outputPath);
            if(javaFile.exists()){
                System.out.println("文件已存在！"+javaFile.getName());
//                return;
                javaFile.delete();
            }
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(source);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：生成实体类主体代码
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @return
     */
    private String parseEntity(String tableName,String[] colnames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ";\r\n\r\n");
        //判断是否导入工具包
        if(lombok){
            sb.append("import lombok.Data;\r\n");
        }
        if(f_util){
            sb.append("import java.util.Date;\r\n");
        }
        if(f_sql){
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("\r\n");
        //注释部分
        sb.append("/**\r\n");
        sb.append(" * "+tableName+" 实体类\r\n");
        sb.append(" * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append(" */ \r\n");
        if(lombok){
            sb.append("@Data");
        }
        //实体部分
        sb.append("\r\npublic class " + getEntityClassName(tableName) + " {\r\n\r\n");
        processAllAttrs(sb);//属性
        sb.append("\r\n}\r\n");
        return sb.toString();
    }

    /**
     * 功能：生成实体类主体代码
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @return
     */
    private String parseQuery(String tableName,String[] colnames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ";\r\n\r\n");
        //判断是否导入工具包
        if(lombok){
            sb.append("import lombok.Data;\r\n");
        }
        if(f_util){
            sb.append("import java.util.Date;\r\n");
        }
        if(f_sql){
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("\r\n");
        //注释部分
        sb.append("/**\r\n");
        sb.append(" * "+tableName+" 查询类\r\n");
        sb.append(" * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append(" */ \r\n");
        if(lombok){
            sb.append("@Data");
        }
        //实体部分
        sb.append("\r\npublic class " + getQueryClassName(tableName) + " {\r\n\r\n");
        processAllAttrs(sb);//属性
        sb.append("\r\n}\r\n");
        return sb.toString();
    }

    public String getEntityClassName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName,true))+entity_suffix;
    }

    public String getQueryClassName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName,true))+query_suffix;
    }

    /**
     * 功能：生成所有属性
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
//            System.out.println("colTypes[i] = "+colTypes[i]);
            sb.append("    private " + GenUtil.sqlType2JavaType(colTypes[i]) + " " + Underline2CamelUtil.underline2Camel(colnames[i],true) + ";\r\n\r\n");
        }
    }

    public void genEntity(){
        List<String> tables = GenUtil.getAllTables(table_schema);
        for(String tableName : tables){
            genEntityByOne(tableName);
        }
    }

    public void genEntityByOne(String tableName){
        String entitySource = getEntitySource(tableName);
        makeEntityFile(tableName,entitySource);

        String querySource = getQuerySource(tableName);
        makeQueryFile(tableName,querySource);
    }


    /**
     * 出口
     * @param args
     */
    public static void main(String[] args) {
        GenEntityMysql gen = new GenEntityMysql();
//        gen.getAllTables();
//        gen.genEntityByOne("message_user");
//        gen.genEntity();
    }
}
