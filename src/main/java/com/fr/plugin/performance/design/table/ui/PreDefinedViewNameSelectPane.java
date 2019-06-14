package com.fr.plugin.performance.design.table.ui;

import com.fr.design.dialog.BasicPane;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.i18n.Toolkit;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.log.FineLoggerFactory;
import com.fr.plugin.performance.design.table.core.PreDefinedViewFileTree;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yuwh on 2019/3/17
 * Description:none
 */
public class PreDefinedViewNameSelectPane extends BasicPane {
    private PreDefinedViewFileTree classFileTree;
    private String pluginroot = getRoot();

    private String getRoot(){
        String var0 = this.getClass().getResource("").getPath();
        var0 = var0.substring(var0.indexOf("/plugins"),var0.indexOf("/com"));
        FineLoggerFactory.getLogger().error(var0);
        return var0;
    }

    public PreDefinedViewNameSelectPane() {
        this.setLayout(new BorderLayout(0, 4));
        JPanel var1 = FRGUIPaneFactory.createBorderLayout_S_Pane();
        this.add(var1, "North");
        var1.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        var1.add(new UILabel(com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Basic_Function_Choose_Function_Class") + ":"));
        this.classFileTree = new PreDefinedViewFileTree(pluginroot);
        this.classFileTree.refreshEnv();
        this.classFileTree.getSelectionModel().setSelectionMode(1);
        this.add(new JScrollPane(this.classFileTree), "Center");
    }

    protected String title4PopupWindow() {
        return com.fr.design.i18n.Toolkit.i18nText("Fine-Design_Basic_Function_Function_Class_Name");
    }

    public void setClassPath(String var1) {
        this.classFileTree.setSelectedClassPath(var1 + ".class");
    }

    public String getClassPath() {
        return this.classFileTree.getSelectedClassPath();
    }

    public void checkValid() throws Exception {
        String var1 = this.classFileTree.getSelectedClassPath();
        if (var1 == null) {
            throw new Exception(Toolkit.i18nText("Fine-Design_Basic_Function_The_Selected_File_Cannot_Be_Null"));
        }
    }
}
