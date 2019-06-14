package com.fr.plugin.performance.design.table.core;

import com.fr.design.gui.itree.filetree.EnvFileTree;
import com.fr.file.filetree.FileNode;
import com.fr.file.filetree.IOFileNodeFilter;

import java.io.File;

/**
 * Created by yuwh on 2019/3/17
 * Description:none
 */
public class PreDefinedViewFileTree extends EnvFileTree {
    /*这里把原来classes改成插件的目录即可*/
    private String pluginroot = this.treeRootPath;

    public PreDefinedViewFileTree(String pluginroot) {
        super("classes", (String[])null, new IOFileNodeFilter(new String[]{"class"}));
    }

    public void setSelectedClassPath(String var1) {
        if (var1 != null && var1.endsWith(".class")) {
            String[] var2 = var1.split("\\.");
            StringBuffer var3 = new StringBuffer();

            for(int var4 = 0; var4 < var2.length; ++var4) {
                if (var4 == var2.length - 1) {
                    var3.append('.');
                } else if (var4 > 0) {
                    var3.append(File.separatorChar);
                }

                var3.append(var2[var4]);
            }

            this.selectPath(var3.toString());
        }
    }

    public String getSelectedClassPath() {
        FileNode var1 = this.getSelectedFileNode();
        if (var1 != null && !var1.isDirectory()) {
            String var2 = var1.getEnvPath();
            if (var2.startsWith(pluginroot)) {
                String var3 = var2.substring(pluginroot.length() + 1);
                var3 = var3.replaceAll("[/\\\\]", ".");
                if (var3.toLowerCase().endsWith(".class")) {
                    var3 = var3.substring(0, var3.length() - ".class".length());
                }

                return var3;
            }
        }

        return null;
    }
}
