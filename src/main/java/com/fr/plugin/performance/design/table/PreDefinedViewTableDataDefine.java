package com.fr.plugin.performance.design.table;

import com.fr.base.TableData;
import com.fr.design.data.tabledata.tabledatapane.AbstractTableDataPane;
import com.fr.design.fun.ServerTableDataDefineProvider;
import com.fr.design.fun.impl.AbstractTableDataDefineProvider;
import com.fr.design.i18n.Toolkit;
import com.fr.plugin.performance.design.table.core.PreDefinedViewTableData;
import com.fr.plugin.performance.design.table.ui.PreDefinedViewTableDataPane;

/**
 * Created by yuwh on 2019/3/13
 * Description:none
 */
public class PreDefinedViewTableDataDefine extends AbstractTableDataDefineProvider implements ServerTableDataDefineProvider {

    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public Class<? extends TableData> classForTableData() {
        return PreDefinedViewTableData.class;
    }

    @Override
    public Class<? extends TableData> classForInitTableData() {
        return PreDefinedViewTableData.class;
    }

    @Override
    public Class<? extends AbstractTableDataPane> appearanceForTableData() {
        return PreDefinedViewTableDataPane.class;
    }

    @Override
    public String nameForTableData() {
        return Toolkit.i18nText("Plugin-Performance-P_defined_View_Table_Data");
    }

    @Override
    public String prefixForTableData() { return Toolkit.i18nText("Plugin-Performance-P_defined_View_Table_Data_Prefix"); }

    @Override
    public String iconPathForTableData() { return Toolkit.i18nText("Plugin-Performance-P_defined_View_Table_Data_icon"); }
}
