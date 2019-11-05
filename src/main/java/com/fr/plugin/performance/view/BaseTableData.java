package com.fr.plugin.performance.view;

import com.fr.data.AbstractTableData;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/22
 * Description:none
 */
public abstract class BaseTableData extends AbstractTableData {
    private boolean finished= false;

    /**
     * TableData的计算方法
     */
    public abstract void init();

    protected boolean isFinished(){ return finished; }

    protected void finish(){ finished= true; }

    protected void unFinish(){ finished= false; }
}
