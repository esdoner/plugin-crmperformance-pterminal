package com.fr.plugin.performance;

import com.fr.stable.fun.impl.AbstractJavaScriptFileHandler;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/6/17
 * Description:none
 */
public class JavascriptFileLoader extends AbstractJavaScriptFileHandler {
    private static String[] JS_ES;

    static{
        JS_ES= new String[]{
                "/com/fr/plugin/performance/web/js/fb/feedback.js"
        };
    }

    @Override
    public String[] pathsForFiles() {
        return JS_ES;
    }
}