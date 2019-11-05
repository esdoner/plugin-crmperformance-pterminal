package com.fr.plugin.performance;

import com.fr.stable.fun.impl.AbstractCssFileHandler;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/6/17
 * Description:none
 */
public class CssFileLoader extends AbstractCssFileHandler {
    private static String[] CSS_ES;

    static{
        CSS_ES= new String[]{
                "/com/fr/plugin/performance/web/css/fb/feedback.css"
        };
    }

    @Override
    public String[] pathsForFiles() {
        return CSS_ES;
    }
}