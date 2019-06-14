package com.fr.plugin.performance.util.dao;

import com.fr.file.DatasourceManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by yuwh on 2019/3/18
 * Description:连接基本统一，操作基本不同
 */
public abstract class DaoConnectBasic implements DaoBasic{
    private com.fr.data.impl.Connection _conn;
    private Connection _con = null;
    private Statement _stmt = null;
    private String _dbname;

    public DaoConnectBasic(String dbname){
        this._dbname = dbname;
    }

    @Override
    public boolean createCon() {
        _conn = DatasourceManager.getInstance().getConnection(_dbname);
        if(_conn != null){
            try {
                _con = _conn.createConnection();
                _stmt = _con.createStatement();
                return true;
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(_dbname+" connect error");
            return false;
        }
        return false;
    }

    @Override
    public boolean closeCon() {
        try {
            if (_stmt != null) {
                _stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (_con != null) {
                _con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Statement getStmt(){ return this._stmt;}
}
