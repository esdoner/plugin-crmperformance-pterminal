package com.fr.plugin.performance.design.table.core;

import com.fr.base.TableData;
import com.fr.config.holder.Conf;
import com.fr.config.holder.factory.Holders;
import com.fr.data.AbstractParameterTableData;
import com.fr.general.ComparatorUtils;
import com.fr.general.GeneralUtils;
import com.fr.general.data.DataModel;
import com.fr.log.FineLoggerFactory;
import com.fr.plugin.transform.ExecuteFunctionRecord;
import com.fr.plugin.transform.FunctionRecorder;
import com.fr.script.Calculator;
import com.fr.stable.ArrayUtils;
import com.fr.stable.ParameterProvider;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;

/**
 * Created by yuwh on 2019/3/13
 * Description:none
 */
@FunctionRecorder(localeKey="FS_PLUGIN_PreDefinedViewTableData")
public class PreDefinedViewTableData extends AbstractParameterTableData implements XMLable {
    private Conf<String> className = Holders.simple("");
    private TableData  _tableData = null;

    public PreDefinedViewTableData() { }

    public PreDefinedViewTableData(String var1) { this.setClassName(var1); }

    public String getClassName() { return (String)this.className.get(); }

    public void setClassName(String var1) { this.className.set(var1); }

    public void setParameters(ParameterProvider[] var1) {
        super.setParameters(var1);
        this.createTableData().setParameters(var1);
    }

    private TableData createTableData() {
        if (this._tableData == null) {
            try {
                this._tableData = (TableData) GeneralUtils.classForName(this.getClassName()).newInstance();
                Calculator var1 = Calculator.createCalculator();
                ParameterProvider[] var2 = this._tableData.getParameters(var1);
                if (ArrayUtils.isEmpty(var2)) {
                    this._tableData.setParameters(super.getParameters(var1));
                }
            } catch (Exception var3) {
                FineLoggerFactory.getLogger().error("ClassName: " + this.getClassName());
                FineLoggerFactory.getLogger().error(var3.getMessage(), var3);
                this._tableData = TableData.EMPTY_TABLEDATA;
            } catch (Error var4) {
                FineLoggerFactory.getLogger().error("ClassName: " + this.getClassName());
                FineLoggerFactory.getLogger().error(var4.getMessage(), var4);
                this._tableData = TableData.EMPTY_TABLEDATA;
            }
        }

        return this._tableData;
    }

    @Override
    @ExecuteFunctionRecord
    public DataModel createDataModel(Calculator var1) {
        return this.createTableData().createDataModel(var1);
    }

    public ParameterProvider[] getParameters(Calculator var1) {
        return this.createTableData().getParameters(var1);
    }

    public String toString() {
        return "ClassTableData[Class:" + this.getClassName() + "]";
    }

    public boolean equals(Object var1) {
        return var1 instanceof PreDefinedViewTableData && super.equals(var1) && ComparatorUtils.equals(this.className, ((PreDefinedViewTableData)var1).className);
    }

    public Object clone() throws CloneNotSupportedException {
        PreDefinedViewTableData var1 = (PreDefinedViewTableData)super.clone();
        var1.className = (Conf)this.className.clone();
        return var1;
    }

    public void readXML(XMLableReader var1) {
        super.readXML(var1);
        String var2;
        if (var1.isChildNode() && var1.getTagName().equals("PreDefinedViewTableDataAttr") && (var2 = var1.getAttrAsString("className", (String)null)) != null) {
            this.setClassName(var2);
        }

    }

    public void writeXML(XMLPrintWriter var1) {
        super.writeXML(var1);
        var1.startTAG("PreDefinedViewTableDataAttr").attr("className", this.getClassName()).end();
    }
}
