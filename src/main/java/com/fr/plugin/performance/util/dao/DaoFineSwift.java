package com.fr.plugin.performance.util.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yuwh on 2019/3/18
 * Description:产品的swift数据库，只有查询
 */
public class DaoFineSwift extends DaoConnectBasic {
    public DaoFineSwift(String dbname){
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
    public Object executeDML(String sql) { return null; }

    @Override
    public Object executeDDL(String var1) { return null; }

    @Override
    public Object executeDCL(String var4) { return null; }
}
