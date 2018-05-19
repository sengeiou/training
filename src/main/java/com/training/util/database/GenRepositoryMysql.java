package com.training.util.database;

import com.training.util.Underline2CamelUtil;
import com.training.util.ut;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;

public class GenRepositoryMysql {

    private static final String packageOutPath = "com.qy.travel.repository";//指定实体生成所在包的路径
    private static final String packageOfEntity = "com.qy.travel.entity";//指定实体生成所在包的路径
    private static final String authorName = "huai23";//作者名字
    private static final  String table_schema = "travel";//数据库名
    private String[] colnames; // 列名数组
    private String[] colTypes; //列名类型数组
    private int[] colSizes; //列名大小数组

    private static final boolean over_write = true;

    private static final  String javafilename_suffix = "Repository";
    private static final  String entity_suffix = "Entity";
    private static final  String query_suffix = "Query";

    private boolean lombok = true; // 是否需要导入lombok.Data注解
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*

    //数据库连接
    private static final String URL ="jdbc:mysql://localhost:3306/"+table_schema;
    private static final String NAME = "root";
    private static final String PASS = "jordan23";
    private static final String DRIVER ="com.mysql.jdbc.Driver";

    //创建连接
    Connection con = null;
    public GenRepositoryMysql() {
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

    public String getSource(String tableName){
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
            content = parse(tableName,colnames, colTypes, colSizes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return content;
    }

    public void makeJavaFile(String fileName,String source){
        try {
            File directory = new File("");
            System.out.println("绝对路径："+directory.getAbsolutePath());
            String outputPath = directory.getAbsolutePath()+ "/src/main/java/"+this.packageOutPath.replace(".", "/")+"/"+ fileName;
            System.out.println("outputPath:"+outputPath);
            File javaFile = new File(outputPath);
            if(javaFile.exists()){
                System.out.println("文件已存在！"+javaFile.getName());
                if(over_write){
                    javaFile.delete();
                }else{
                    return;
                }
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
    private String parse(String tableName,String[] colnames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ";\r\n\r\n");
        sb.append("import " + packageOfEntity + ".*;\r\n");
        sb.append("import org.apache.ibatis.annotations.*;\r\n");
        sb.append("import com.qy.travel.common.PageRequest;\r\n");
        sb.append("\r\nimport java.util.List;\r\n");

        sb.append("\r\n");
        //注释部分
        sb.append("/**\r\n");
        sb.append(" * "+tableName+" 数据库操作类\r\n");
        sb.append(" * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append(" */ \r\n");
        if(lombok){
            sb.append("@Mapper");
        }
        //实体部分
        sb.append("\r\npublic interface " + getInterfaceName(tableName) + " {\r\n\r\n");
//        processAllAttrs(sb);//属性

        processInsert(tableName,sb);
        processFind(tableName,sb);
        processCount(tableName,sb);
        processGetById(tableName,sb);
        processUpdate(tableName,sb);
        processDelete(tableName,sb);

        sb.append("\r\n}\r\n");
        return sb.toString();
    }

    private void processDelete(String tableName,StringBuffer sb) {
        sb.append("    @Update(\"<script> DELETE  FROM "+tableName+" \" +\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if(colnames[i].equals("created")||colnames[i].equals("modified")||colnames[i].equals("pk_id")){
                continue;
            }
            sb.append("            \" WHERE "+colnames[i]+" = #{id} \" +\r\n");
            break;
        }
        sb.append("            \"</script>\")\r\n");
        sb.append("    int delete(@Param(\"id\") String id);\r\n\r\n");
    }

    private void processUpdate(String tableName,StringBuffer sb) {
        sb.append("    @Update(\"<script> UPDATE "+tableName+" SET \" +\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if(colnames[i].equals("created")||colnames[i].equals("modified")||colnames[i].equals("pk_id")){
                continue;
            }
            sb.append("                \" <if test=\\\""+Underline2CamelUtil.underline2Camel(tableName)+"."+Underline2CamelUtil.underline2Camel(colnames[i])+" != null\\\"> "+colnames[i]+" = #{"+Underline2CamelUtil.underline2Camel(tableName)+"."+Underline2CamelUtil.underline2Camel(colnames[i])+"} , </if>\" +\r\n");
        }
        sb.append("                \" modified = now() \" +\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if(colnames[i].equals("created")||colnames[i].equals("modified")||colnames[i].equals("pk_id")){
                continue;
            }
            sb.append("            \" WHERE "+colnames[i]+" = #{"+Underline2CamelUtil.underline2Camel(tableName)+"."+Underline2CamelUtil.underline2Camel(colnames[i])+"} \" +\r\n");
            break;
        }
        sb.append("            \"</script>\")\r\n");
        sb.append("    int update(@Param(\""+Underline2CamelUtil.underline2Camel(tableName)+"\") "+getEntityName(tableName)+" "+getEntityParamName(tableName)+");\r\n\r\n");
    }

    private void processGetById(String tableName,StringBuffer sb) {
        sb.append("    @Select(\"<script> SELECT ");
        for (int i = 0; i < colnames.length; i++) {
            sb.append(colnames[i]);
            if(i<(colnames.length-1)){
                sb.append(",");
            }
        }
        sb.append(" \" +\r\n");
        sb.append("            \" FROM "+tableName+" \" +\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if(colnames[i].equals("created")||colnames[i].equals("modified")||colnames[i].equals("pk_id")){
                continue;
            }
            sb.append("            \" WHERE "+colnames[i]+" = #{id} \" +\r\n");
            break;
        }
        sb.append("            \"</script>\")\r\n");
        sb.append("    "+getEntityName(tableName)+" getById(@Param(\"id\") String id);\r\n\r\n");
    }

    private void processFind(String tableName,StringBuffer sb) {
        sb.append("    @Select(\"<script> SELECT ");
        for (int i = 0; i < colnames.length; i++) {
            sb.append(colnames[i]);
            if(i<(colnames.length-1)){
                sb.append(",");
            }
        }
        sb.append(" \" +\r\n");
        sb.append("            \" FROM "+tableName+" \" +\r\n");
        sb.append("            \" WHERE 1 = 1 \" +\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if(colnames[i].equals("created")||colnames[i].equals("modified")||colnames[i].equals("pk_id")){
                continue;
            }
            sb.append("            \" <if test=\\\"query."+Underline2CamelUtil.underline2Camel(colnames[i])+" != null\\\"> AND "+colnames[i]+" = #{query."+Underline2CamelUtil.underline2Camel(colnames[i])+"} </if>\" +\r\n");
        }
        sb.append("            \" LIMIT #{page.offset} , #{page.pageSize} \" +\r\n");
        sb.append("            \"</script>\")\r\n");
        sb.append("    List<"+getEntityName(tableName)+"> find(@Param(\"query\") "+getQueryName(tableName)+" "+getEntityParamName(tableName)+" , @Param(\"page\") PageRequest page);\r\n\r\n");
    }

    private void processCount(String tableName,StringBuffer sb) {
        sb.append("    @Select(\"<script> SELECT COUNT(1) FROM "+tableName+" \" +\r\n");
        sb.append("            \" WHERE 1 = 1 \" +\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if(colnames[i].equals("created")||colnames[i].equals("modified")||colnames[i].equals("pk_id")){
                continue;
            }
            sb.append("            \" <if test=\\\"query."+Underline2CamelUtil.underline2Camel(colnames[i])+" != null\\\"> AND "+colnames[i]+" = #{query."+Underline2CamelUtil.underline2Camel(colnames[i])+"} </if>\" +\r\n");
        }
        sb.append("            \"</script>\")\r\n");
        sb.append("    Long count(@Param(\"query\") "+getQueryName(tableName)+" "+getEntityParamName(tableName)+");\r\n\r\n");
    }


    private void processInsert(String tableName,StringBuffer sb) {
        sb.append("    @Insert(\"<script> INSERT INTO "+tableName+" ( \" +\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if(colnames[i].equals("created")||colnames[i].equals("modified")||colnames[i].equals("pk_id")){
                continue;
            }
            sb.append("                \" <if test=\\\""+Underline2CamelUtil.underline2Camel(tableName)+"."+Underline2CamelUtil.underline2Camel(colnames[i])+" != null\\\"> "+colnames[i]+", </if>\" +\r\n");
        }
        sb.append("                \" created , \" +\r\n");
        sb.append("                \" modified \" +\r\n");
        sb.append("            \" ) VALUES ( \" +\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if(colnames[i].equals("created")||colnames[i].equals("modified")||colnames[i].equals("pk_id")){
                continue;
            }
            sb.append("                \" <if test=\\\""+Underline2CamelUtil.underline2Camel(tableName)+"."+Underline2CamelUtil.underline2Camel(colnames[i])+" != null\\\"> #{"+Underline2CamelUtil.underline2Camel(tableName)+"."+Underline2CamelUtil.underline2Camel(colnames[i])+"}, </if>\" +\r\n");
        }
        sb.append("                \" now() , \" +\r\n");
        sb.append("                \" now() \" +\r\n");
        sb.append("            \" ) \" +\r\n");
        sb.append("            \"</script>\")\r\n");
        sb.append("    int add(@Param(\""+Underline2CamelUtil.underline2Camel(tableName)+"\") "+getEntityName(tableName)+" "+getEntityParamName(tableName)+");\r\n\r\n");
    }

    public String getInterfaceName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName))+javafilename_suffix;
    }

    public String getEntityName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName))+entity_suffix;
    }

    public String getQueryName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName))+query_suffix;
    }

    public String getEntityParamName(String tableName){
        return Underline2CamelUtil.underline2Camel(tableName);
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

    public void genRepository(){
        List<String> tables = GenUtil.getAllTables(table_schema);
        for(String tableName : tables){
            genRepositoryByOne(tableName);
        }
    }

    public void genRepositoryByOne(String tableName){
        String source = getSource(tableName);
        String javaFileName = getInterfaceName(tableName) + ".java";
        makeJavaFile(javaFileName,source);
    }

    /**
     * 出口
     * @param args
     */
    public static void main(String[] args) {
        GenRepositoryMysql gen = new GenRepositoryMysql();
        gen.genRepositoryByOne("message_user");
//        gen.genRepository();
    }
}
