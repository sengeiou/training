package com.training.util.database;

import com.training.util.Underline2CamelUtil;
import com.training.util.ut;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GenController {

    private static final String packageOutPath = "com.qy.travel.api";//指定实体生成所在包的路径
    private static final String packageOfService = "com.qy.travel.service";//指定实体生成所在包的路径
    private static final String packageOfDao = "com.qy.travel.dao";//指定实体生成所在包的路径
    private static final String packageOfRepository = "com.qy.travel.repository";//指定实体生成所在包的路径
    private static final String packageOfEntity = "com.qy.travel.entity";//指定实体生成所在包的路径
    private static final String authorName = "huai23";//作者名字

    private static final  String table_schema = "travel";//数据库名

    private static final boolean over_write = true;

    private static final  String controller_suffix = "RestController";
    private static final  String service_suffix = "Service";
    private static final  String dao_suffix = "Dao";
    private static final  String repository_suffix = "Repository";
    private static final  String entity_suffix = "Entity";
    private static final  String query_suffix = "Query";


    public GenController() {

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
        sb.append("import " + packageOfService + ".*;\r\n");
        sb.append("import " + packageOfEntity + ".*;\r\n");
        sb.append("import com.qy.travel.common.*;\r\n");
        sb.append("import org.slf4j.Logger;\r\n");
        sb.append("import org.slf4j.LoggerFactory;\r\n");
        sb.append("import com.qy.travel.util.ResponseUtil;\r\n");
        sb.append("import org.springframework.http.ResponseEntity;\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
        sb.append("import org.springframework.web.bind.annotation.RestController;\r\n");
        sb.append("import org.springframework.web.bind.annotation.*;\r\n");
        sb.append("import javax.servlet.http.*;\r\n");
        sb.append("import com.alibaba.fastjson.JSONObject;\r\n");
        sb.append("import java.io.IOException;\r\n");

        sb.append("\r\nimport java.util.List;\r\n");

        sb.append("\r\n");
        //注释部分
        sb.append("/**\r\n");
        sb.append(" * "+tableName+" API控制器\r\n");
        sb.append(" * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append(" */ \r\n");
        sb.append("@RestController\r\n");
        sb.append("@RequestMapping(\"/api/"+getEntityParamName(tableName)+"\")");
        //实体部分
        sb.append("\r\npublic class " + getControllerName(tableName) + " {\r\n\r\n");

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
        sb.append("    private "+getServiceName(tableName)+" "+getServicePropertyName(tableName)+";\r\n\r\n");
    }

    private void processDelete(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 根据ID删除\r\n");
        sb.append("     * @param id\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    @RequestMapping (value = \"delete/{id}\", method = RequestMethod.POST)\r\n");
        sb.append("    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){\r\n");
        sb.append("        logger.info(\"  delete  id = {}\",id);\r\n");
        sb.append("        return "+getServicePropertyName(tableName)+".delete(id);\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processUpdate(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 根据实体更新\r\n");
        sb.append("     * @param "+getEntityParamName(tableName)+"\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    @RequestMapping (value = \"update\", method = RequestMethod.POST)\r\n");
        sb.append("    public ResponseEntity<String> update(@RequestBody "+getEntityName(tableName)+" "+getEntityParamName(tableName)+",HttpServletRequest request, HttpServletResponse response){\r\n");
        sb.append("        logger.info(\"  update  "+getEntityParamName(tableName)+" = {}\","+getEntityParamName(tableName)+");\r\n");
        sb.append("        return "+getServicePropertyName(tableName)+".update("+getEntityParamName(tableName)+");\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processGetById(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 根据ID查询实体\r\n");
        sb.append("     * @param id\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    @RequestMapping (value = \"get/{id}\", method = RequestMethod.GET)\r\n");
        sb.append("    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){\r\n");
        sb.append("        "+getEntityName(tableName)+" "+getEntityParamName(tableName)+"DB = "+getServicePropertyName(tableName)+".getById(id);\r\n");
        sb.append("        if("+getEntityParamName(tableName)+"DB==null){\r\n");
        sb.append("            return ResponseUtil.exception(\"查无数据\");\r\n");
        sb.append("        }\r\n");
        sb.append("        return ResponseUtil.success("+getEntityParamName(tableName)+"DB);\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processFind(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 分页查询\r\n");
        sb.append("     * @param query\r\n");
        sb.append("     * @param pageRequest\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    @RequestMapping (value = \"find\", method = RequestMethod.GET)\r\n");
        sb.append("    public ResponseEntity<String> find(@ModelAttribute "+getQueryName(tableName)+" query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){\r\n");
        sb.append("        Page<"+getEntityName(tableName)+"> page = "+getServicePropertyName(tableName)+".find(query,pageRequest);\r\n");
        sb.append("        return ResponseUtil.success(page);\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processCount(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 查询总数\r\n");
        sb.append("     * @param query\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    @RequestMapping (value = \"count\", method = RequestMethod.GET)\r\n");
        sb.append("    public ResponseEntity<String> count(@ModelAttribute "+getQueryName(tableName)+" query,HttpServletRequest request, HttpServletResponse response){\r\n");
        sb.append("        Long count = "+getServicePropertyName(tableName)+".count(query);\r\n");
        sb.append("        JSONObject jo = new JSONObject();\r\n");
        sb.append("        jo.put(\"count\", count);\r\n");
        sb.append("        return ResponseUtil.success(jo);\r\n");
        sb.append("    }\r\n\r\n");
    }

    private void processInsert(String tableName,StringBuffer sb) {
        sb.append("    /**\r\n");
        sb.append("     * 新增实体\r\n");
        sb.append("     * @param "+getEntityParamName(tableName)+"\r\n");
        sb.append("     * Created by "+this.authorName+" on "+ ut.currentTime()+".\r\n");
        sb.append("     */ \r\n");
        sb.append("    @RequestMapping (value = \"add\", method = RequestMethod.POST)\r\n");
        sb.append("    public ResponseEntity<String> add(@RequestBody "+getEntityName(tableName)+" "+getEntityParamName(tableName)+",HttpServletRequest request, HttpServletResponse response){\r\n");
        sb.append("        logger.info(\" "+tableName+"RestController  add  "+getEntityParamName(tableName)+" = {}\","+getEntityParamName(tableName)+");\r\n");
        sb.append("        return "+getServicePropertyName(tableName)+".add("+getEntityParamName(tableName)+");\r\n");
        sb.append("    }\r\n\r\n");
    }

    public String getControllerName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName))+controller_suffix;
    }

    public String getServiceName(String tableName){
        return GenUtil.initcap(Underline2CamelUtil.underline2Camel(tableName))+service_suffix;
    }

    public String getServicePropertyName(String tableName){
        return Underline2CamelUtil.underline2Camel(tableName)+service_suffix;
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


    public void genController(){
        List<String> tables = GenUtil.getAllTables(table_schema);
        for(String tableName : tables){
            genControllerByOne(tableName);
        }
    }

    public void genControllerByOne(String tableName){
        String source = getSource(tableName);
        String javaFileName = getControllerName(tableName) + ".java";
        makeJavaFile(javaFileName,source);
    }

    /**
     * 出口
     * @param args
     */
    public static void main(String[] args) {
        GenController gen = new GenController();
        gen.genControllerByOne("form_group");
//        gen.genController();
    }
}
