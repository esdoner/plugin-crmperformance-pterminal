package com.fr.plugin.performance.view;

import com.fr.general.data.TableDataException;
import com.fr.plugin.performance.view.BaseTableData;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/3
 * Description:none
 */
public class DemoTableData extends BaseTableData {
    private String[][] rowData;
    /**
     * TableData的计算方法
     */
    public DemoTableData(){
        unFinish();
    }

    @Override
    public void init() {
        rowData= new String[5][3];
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                rowData[i][j]=String.valueOf(i+j);
            }
        }
        finish();
    }

    @Override
    public int getColumnCount() throws TableDataException {
        return 3;
    }

    @Override
    public String getColumnName(int i) throws TableDataException {
        return "col"+i;
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
}
