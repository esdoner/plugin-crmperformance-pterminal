package com.fr.plugin.performance.design.table.ui;

import com.fr.base.Parameter;
import com.fr.design.data.tabledata.tabledatapane.AbstractTableDataPane;
import com.fr.design.dialog.BasicDialog;
import com.fr.design.dialog.DialogActionAdapter;
import com.fr.design.gui.ibutton.UIButton;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.itableeditorpane.ParameterTableModel;
import com.fr.design.gui.itableeditorpane.UITableEditAction;
import com.fr.design.gui.itableeditorpane.UITableEditorPane;
import com.fr.design.gui.itextfield.UITextField;
import com.fr.design.i18n.Toolkit;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.layout.TableLayoutHelper;
import com.fr.general.IOUtils;
import com.fr.plugin.performance.design.table.core.PreDefinedViewTableData;
import com.fr.script.Calculator;
import com.fr.stable.ParameterProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

/**
 * Created by yuwh on 2019/3/13
 * Description:基本copy的ClassTableDataPane然后修改
 */
public class PreDefinedViewTableDataPane extends AbstractTableDataPane<PreDefinedViewTableData> {
    /*也需要两种按钮，命名下*/
    private static final String PREVIEW_BUTTON = Toolkit.i18nText("Plugin-Performance-P_Preview");
    /*south放参数编辑列表*/
    private UITableEditorPane<ParameterProvider> editorPane;
    /*north放一个框体来选择对应analysis view class*/
    private UITextField classNameTextField;

    public PreDefinedViewTableDataPane() {
        this.setLayout(FRGUIPaneFactory.createBorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        double var1 = -2.0D;
        double[] var3 = new double[]{var1, var1};
        double[] var4 = new double[]{var1, var1};
        JPanel var5 = FRGUIPaneFactory.createBoxFlowInnerContainer_S_Pane();

        this.classNameTextField = new UITextField(36);
        var5.add(this.classNameTextField);

        /*选择按钮*/
        UIButton var6 = new UIButton(Toolkit.i18nText("Plugin-Performance-P_Select"));
        /*按钮适应文本框体高度，自适应宽度*/
        var6.setPreferredSize(new Dimension(var6.getPreferredSize().width, this.classNameTextField.getPreferredSize().height));
        var5.add(var6);
        var6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                /*重要：这里要看下怎么把这个文件树换成插件内的*/
                /*好像没办法直接用，copy修改一下*/
                final PreDefinedViewNameSelectPane var2 = new PreDefinedViewNameSelectPane();
                /*点开的时候查找文本框中的class并展开*/
                var2.setClassPath(PreDefinedViewTableDataPane.this.classNameTextField.getText());
                /*将class设置到文本框中*/
                BasicDialog var3 = var2.showWindow((Dialog)SwingUtilities.getWindowAncestor(PreDefinedViewTableDataPane.this), new DialogActionAdapter() {
                    public void doOk() {
                        PreDefinedViewTableDataPane.this.classNameTextField.setText(var2.getClassPath());
                    }
                });
                var3.setVisible(true);
            }
        });
        /*上部框体组合*/
        Component[][] var8 = new Component[][]{
                {new UILabel(Toolkit.i18nText("Plugin-Performance-P_View_Name") + ":"), var5},
                {null, new UILabel(Toolkit.i18nText("Plugin-Performance-P_View_Select_Tips") )},
        };
        JPanel var9 = TableLayoutHelper.createTableLayoutPane(var8, var3, var4);
        this.add(var9, "North");
        /*下部框体组合*/
        this.add(this.initSouthPanel(), "South");
    }

    @Override
    protected String title4PopupWindow() { return Toolkit.i18nText("Plugin-Performance-P_defined_View_Table_Data"); }

    /*
    * @params []
    * @return javax.swing.JPanel
    * @description: 定义下方的参数面板
    */
    private JPanel initSouthPanel() {
        JPanel var1 = new JPanel();
        var1.setPreferredSize(new Dimension(-1, 150));
        var1.setLayout(new BorderLayout());
        this.editorPane = new UITableEditorPane(new ParameterTableModel() {
            public UITableEditAction[] createAction() {
                return new UITableEditAction[]{PreDefinedViewTableDataPane.this.new AddParaAction(), PreDefinedViewTableDataPane.this.new RemoveParaAction()};
            }
        }, " " + Toolkit.i18nText("Fine-Design_Basic_TableData_Default_Para"));
        var1.add(this.editorPane, "Center");
        return var1;
    }

    /*
    * @params [var1]
    * @return void
    * @description: none
    */
    public void populateBean(PreDefinedViewTableData var1) {
        this.editorPane.populate(var1.getParameters(Calculator.createCalculator()));
        this.classNameTextField.setText(var1.getClassName());
    }

    /*
    * @params []
    * @return com.fr.plugin.performance.design.table.core.PreDefinedViewTableData
    * @description:
    */
    public PreDefinedViewTableData updateBean() {
        PreDefinedViewTableData var1 = new PreDefinedViewTableData(this.classNameTextField.getText());
        List var2 = this.editorPane.update();
        var1.setParameters((ParameterProvider[])var2.toArray(new ParameterProvider[var2.size()]));
        return var1;
    }

    private class RemoveParaAction extends UITableEditAction {
        public RemoveParaAction() {
            this.setName(Toolkit.i18nText("Fine-Design_Basic_Remove"));
            this.setSmallIcon(IOUtils.readIcon("/com/fr/design/images/control/remove.png"));
        }

        public void actionPerformed(ActionEvent var1) {
            ParameterProvider var2 = (ParameterProvider) PreDefinedViewTableDataPane.this.editorPane.getTableModel().getSelectedValue();
            List var3 = PreDefinedViewTableDataPane.this.editorPane.update();
            var3.remove(var2);
            PreDefinedViewTableDataPane.this.editorPane.populate((ParameterProvider[]) var3.toArray(new ParameterProvider[var3.size()]));
        }

        public void checkEnabled() {
        }
    }

    public class AddParaAction extends UITableEditAction {
        public AddParaAction() {
            this.setName(Toolkit.i18nText("Fine-Design_Basic_Add"));
            this.setSmallIcon(IOUtils.readIcon("/com/fr/design/images/buttonicon/add.png"));
        }

        public void actionPerformed(ActionEvent var1) {
            List var2 = PreDefinedViewTableDataPane.this.editorPane.update();
            var2.add(new Parameter());
            PreDefinedViewTableDataPane.this.editorPane.populate((ParameterProvider[]) var2.toArray(new ParameterProvider[var2.size()]));
        }

        public void checkEnabled() {
        }
    }
}