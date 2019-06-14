package com.fr.plugin.performance.util.dao;

/**
 * Created by yuwh on 2019/3/18
 * Description:数据库系统连接+管理方法
 */
public interface DaoBasic {
    /*检查并开启连接*/
    boolean createCon();
    /*关闭连接*/
    boolean closeCon();
    /*定义*/
    Object executeDDL(String var1);
    /*查询*/
    Object executeDQL(String var2);
    /*操作*/
    Object executeDML(String var3);
    /*控制*/
    Object executeDCL(String var4);
}
