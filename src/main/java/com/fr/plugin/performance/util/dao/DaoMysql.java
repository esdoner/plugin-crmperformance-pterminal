package com.fr.plugin.performance.util.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yuwh on 2019/3/18
 * Description:mysql数据库管理
 */
public class DaoMysql extends DaoConnectBasic{

    public DaoMysql(String dbname){
        super(dbname);
    }

    @Override
    public ResultSet executeDQL(String sql) {
        try {
            return super.getStmt().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object executeDML(String sql) {
        try {
            return super.getStmt().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Object executeDDL(String var1) { return null; }

    @Override
    public Object executeDCL(String var4) { return null; }
}
