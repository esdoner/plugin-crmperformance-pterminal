package com.fr.plugin.performance;

import com.fr.stable.fun.impl.AbstractJavaScriptFileHandler;

/**
 * Created by richie on 16/4/20.
 */
public class JavascriptFileLoader extends AbstractJavaScriptFileHandler {
    @Override
    public String[] pathsForFiles() {
        return new String[]{
                "/com/fr/plugin/performance/web/js/feedback.js"
        };
    }
}