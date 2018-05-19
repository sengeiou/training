package com.training.util.database;

import com.training.util.Underline2CamelUtil;
import com.training.util.ut;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GenService {

    private static final String packageOutPath = "com.training.service";//指定实体生成所在包的路径
    private static final String packageOfDao = "com.training.dao";//指定实体生成所在包的路径
    private static final String packageOfRepository = "com.training.repository";//指定实体生成所在包的路径
    private static final String packageOfEntity = "com.training.entity";//指定实体生成所在包的路径
    private static final String authorName = "huai23";//作者名字

    private static final  String table_schema = "training";//数据库名

    private static final boolean over_write = true;

    private static final  String service_suffix = "Service";
    private static final  String dao_suffix = "Dao";
    private static final  String repository_suffix = "Repository";
    private static final  String entity_suffix = "Entity";
    private static final  String query_suffix = "Query";


    public GenService() {

    }

    public String getSource(String tableName){
        String content = "";
        try {
            content = parse(tableName);
        } catch (Exception e) {
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
     * @param tableName
     * @return
     */
    private String parse(String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + packageOutPath + ";\r\n\r\n");
        sb.append("import " + packageOfDao + ".*;\r\n");
        sb.append("import " + packageOfEntity + ".*;\r\n");
        sb.append("import com.qy.travel.domain.User;\r\n");
        sb.append("import com.qy.travel.common.*;\r\n");
        sb.append("import org.slf4j.Logger;\r\n");
        sb.append("import org.slf4j.LoggerFactory;\r\n");
        sb.append("import org.springframework.stereotype.Service;\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
        sb.append("import org.springframework.http.ResponseEntity;\r\n");
        sb.append("import com.qy.travel.util.ResponseUtil;\r\n");
        sb.append("import com.qy.travel.util.RequestContextHelper;\r\n");

        sb.append("\r\nimport java.util.List;\r\n");

        sb.append("\r\n");
        //注释部分
        sb.append("/**\r\n");
        sb.append(" * "+tableName+" 核心业务操作类\r\n");
        sb.append(" * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append(" */ \r\n");
        sb.append("@Service");
        //实体部分
        sb.append("\r\npublic class " + getServiceName(tableName) + " {\r\n\r\n");

        processLogger(tableName,sb);
        processAutoware(tableName,sb);

        processInsert(tableName,sb);
        processFind(tableName,sb);
        processCount(tableName,sb);
        processGetById(tableName,sb);
        processUpdate(tableName,sb);
        processDelete(tableName,sb);

        sb.append("\r\n}\r\n");
        return sb.toString();
    }

    private void processLogger(String tableName,StringBuffer sb) {
        sb.append("    private final Logger logger = LoggerFactory.getLogger(this.getClass());\r\n\r\n");
    }

    private void processAutoware(String tableName,StringBuffer sb) {
        sb.append("    @Autowired\r\n");
        sb.append("    private "+getDaoName(tableName)+" "+getDaoPropertyName(tableName)+";\r\n\r\n");
    }

    private void processDelete(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 根据ID删除\r\n");
        sb.append("     * @param id\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    public ResponseEntity<String> delete(String id){\r\n");
        sb.append("        int n = "+getDaoPropertyName(tableName)+".delete(id);\r\n");
        sb.append("        if(n==1){\r\n");
        sb.append("            return ResponseUtil.success(\"删除成功\");\r\n");
        sb.append("        }\r\n");
        sb.append("        return ResponseUtil.exception(\"删除失败\");\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processUpdate(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 根据实体更新\r\n");
        sb.append("     * @param "+getEntityParamName(tableName)+"\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    public  ResponseEntity<String> update("+getEntityName(tableName)+" "+getEntityParamName(tableName)+"){\r\n");
        sb.append("        int n = "+getDaoPropertyName(tableName)+".update("+getEntityParamName(tableName)+");\r\n");
        sb.append("        if(n==1){\r\n");
        sb.append("            return ResponseUtil.success(\"修改成功\");\r\n");
        sb.append("        }\r\n");
        sb.append("        return ResponseUtil.exception(\"修改失败\");\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processGetById(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 根据ID查询实体\r\n");
        sb.append("     * @param id\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    public "+getEntityName(tableName)+" getById(String id){\r\n");
        sb.append("        "+getEntityName(tableName)+" "+getEntityParamName(tableName)+"DB = "+getDaoPropertyName(tableName)+".getById(id);\r\n");
        sb.append("        return "+getEntityParamName(tableName)+"DB;\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processFind(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 分页查询\r\n");
        sb.append("     * @param query\r\n");
        sb.append("     * @param page\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    public Page<"+getEntityName(tableName)+"> find("+getQueryName(tableName)+" query , PageRequest page){\r\n");
        sb.append("        List<"+getEntityName(tableName)+"> "+getEntityParamName(tableName)+"List = "+getDaoPropertyName(tableName)+".find(query,page);\r\n");
        sb.append("        Long count = "+getDaoPropertyName(tableName)+".count(query);\r\n");
        sb.append("        Page<"+getEntityName(tableName)+"> returnPage = new Page<>();\r\n");
        sb.append("        returnPage.setContent("+getEntityParamName(tableName)+"List);\r\n");
        sb.append("        returnPage.setPage(page.getPage());\r\n");
        sb.append("        returnPage.setSize(page.getPageSize());\r\n");
        sb.append("        returnPage.setTotalElements(count);\r\n");
        sb.append("        return returnPage;\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processCount(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 查询总数\r\n");
        sb.append("     * @param query\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    public Long count("+getQueryName(tableName)+" query){\r\n");
        sb.append("        Long count = "+getDaoPropertyName(tableName)+".count(query);\r\n");
        sb.append("        return count;\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processInsert(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 新增实体\r\n");
        sb.append("     * @param "+getEntityParamName(tableName)+"\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    public ResponseEntity<String> add("+getEntityName(tableName)+" "+getEntityParamName(tableName)+"){\r\n");
        sb.append("        User user = RequestContextHelper.getUser();\r\n");
        sb.append("        int n = "+getDaoPropertyName(tableName)+".add("+getEntityParamName(tableName)+");\r\n");
        sb.append("        if(n==1){\r\n");
        sb.append("            return ResponseUtil.success(\"添加成功\");\r\n");
        sb.append("        }\r\n");
        sb.append("        return ResponseUtil.exception(\"添加失败\");\r\n");
        sb.append("    }\r\n\r\n");
    }

    public String getServiceName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName))+service_suffix;
    }

    public String getDaoName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName))+dao_suffix;
    }

    public String getDaoPropertyName(String tableName){
        return Underline2CamelUtil.underline2Camel(tableName)+dao_suffix;
    }

    public String getRepositoryName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName))+repository_suffix;
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

    public String getRepositoryPropertyName(String tableName){
        return Underline2CamelUtil.underline2Camel(tableName)+repository_suffix;
    }


    public void genService(){
        List<String> tables = GenUtil.getAllTables(table_schema);
        for(String tableName : tables){
            genServiceByOne(tableName);
        }
    }

    public void genServiceByOne(String tableName){
        String source = getSource(tableName);
        String javaFileName = getServiceName(tableName) + ".java";
        makeJavaFile(javaFileName,source);
    }

    /**
     * 出口
     * @param args
     */
    public static void main(String[] args) {
        GenService gen = new GenService();
        gen.genServiceByOne("form_group");
//        gen.genService();
    }
}
