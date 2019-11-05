package com.fr.plugin.performance.view.health;

import com.fr.general.data.TableDataException;
import com.fr.plugin.performance.analy.model.DiscreteSeries;
import com.fr.plugin.performance.analy.model.IrregularDiscreteSeries;
import com.fr.plugin.performance.analy.model.PeakValleyDiscreteSeries;
import com.fr.plugin.performance.conf.BaseConfProvider;
import com.fr.plugin.performance.util.dao.DaoFineSwift;
import com.fr.plugin.performance.view.BaseTableData;
import com.fr.report.cell.AbstractCellElement;
import com.fr.script.Calculator;
import com.fr.stable.ParameterProvider;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/22
 * Description:服务器RealTime状态数据分析TableData
 */
public class RealTimeTableData extends BaseTableData {
    private static final String[] COLUMN_NAMES =  {"id", "duration", "time"};
    private String[][] rowData;
    private String type;
    List<Map> list= new ArrayList<>();
    private DaoFineSwift dao;
    private StringBuffer sqlString;
    private ResultSet resultSet= null;

    public RealTimeTableData(){ unFinish(); }

    @Override
    public int getColumnCount() throws TableDataException {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int i) throws TableDataException {
        return COLUMN_NAMES[i];
    }

    @Override
    public int getRowCount() throws TableDataException {
        if( !isFinished() ){ init(); }
        return rowData.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        if( !isFinished() ){ init(); }
        return rowData[i][i1];
    }

    @Override
    public void init() {
        if(!isFinished()){
            this.prepareParas();
            try {
                this.initializeData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    private void prepareParas(){
        ParameterProvider[] parameters= this.getParameters(Calculator.createCalculator());
        String[] ins= new String[3];
        ins[0]= ((AbstractCellElement)((ArrayList)parameters[0].getValue()).get(0)).getValue().toString();;
        ins[1]= ((AbstractCellElement)((ArrayList)parameters[1].getValue()).get(0)).getValue().toString();;
        ins[2]= parameters[2].getValue().toString();
        type= parameters[3].getValue().toString();
        sqlString= new StringBuffer("SELECT time,cpu,memory FROM fine_real_time_usage WHERE ");
        sqlString.append("time>= '").append(ins[0]).append("'");
        sqlString.append(" AND ").append("time<= '").append(ins[1]).append("'");
        if( !ins[2].equals("") ) {
            sqlString.append(" AND ").append("node= '").append(ins[2]).append("' ");
        }
        sqlString.append(" ORDER BY time ASC");
    }

    private void initializeData() throws SQLException {
        dao = new DaoFineSwift("swiftdb");
        dao.createCon();
        resultSet = dao.executeDQL(sqlString.toString());
        ResultSetMetaData metaData=  resultSet.getMetaData();
        String[] columns= new String[metaData.getColumnCount()];
        for(int i= 0; i<columns.length; i++){
            columns[i]= metaData.getColumnName(i+1);
        }
        if(resultSet!= null){
            Map row= new HashMap();
            while(resultSet.next()){
                for(int i= 0; i<columns.length; i++) {
                    if(i== columns.length-1){
                        row.put(columns[i], resultSet.getFloat(i + 1)/BaseConfProvider.TOTAL_MEMORY);
                    } else {
                        row.put(columns[i], resultSet.getString(i + 1));
                    }
                }
                list.add((Map)((HashMap) row).clone());
            }
        }
        dao.closeCon();
        if(type.equals( "c" )){
            xCPU();
        } else if(type.equals( "m" )) {
            xMemory();
        }
    }

    private void xCPU(){
        DiscreteSeries ds= new IrregularDiscreteSeries();
        ds.init(list);
        ds.findPeak("cpu");
        PeakValleyDiscreteSeries peak = ((IrregularDiscreteSeries) ds).findPeak();
        rowData= peak.getPeakReport(BaseConfProvider.MAX_CPU_PERCENTAGE, new String[]{"time"});
    }

    private void xMemory(){
        DiscreteSeries ds= new IrregularDiscreteSeries();
        ds.init(list);
        ds.findPeak("memory");
        PeakValleyDiscreteSeries peak = ((IrregularDiscreteSeries) ds).findPeak();
        rowData= peak.getPeakReport(BaseConfProvider.MAX_MEMORY_PERCENTAGE, new String[]{"time"});
    }
}
