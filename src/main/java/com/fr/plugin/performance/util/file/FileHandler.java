package com.fr.plugin.performance.util.file;

import java.io.File;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/8/21
 * Description:none
 */
public class FileHandler {
    public static String getPostFix(File file){
        String[] var= file.getName().split("\\.");
        if(file.isFile() && var.length> 1){
            return  var[var.length-1];
        } else {
            return "";
        }
    }

    public static boolean isHiden(File file){
        return file.getName().substring(0, 1).equals(".");
    }
}
