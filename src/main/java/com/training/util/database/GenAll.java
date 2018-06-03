package com.training.util.database;

public class GenAll {

    private static final  String table_name = "sys_log";//数据库名

    /**
     * 出口
     * @param args
     */
    public static void main(String[] args) {
        GenEntityMysql genEntityMysql = new GenEntityMysql();
        genEntityMysql.genEntityByOne(table_name);
        GenRepositoryMysql gen = new GenRepositoryMysql();
        gen.genRepositoryByOne(table_name);
        GenDao genDao = new GenDao();
        genDao.genDaoByOne(table_name);
        GenService genService = new GenService();
        genService.genServiceByOne(table_name);
        GenController genController = new GenController();
        genController.genControllerByOne(table_name);
    }
}
