package com.fr.plugin.performance;

import com.fr.stable.fun.impl.AbstractJavaScriptFileHandler;

/**
 * Created by think on 2019/06/17.
 */
public class JavascriptFileLoader extends AbstractJavaScriptFileHandler {
    @Override
    public String[] pathsForFiles() {
        return new String[]{
                "/com/fr/plugin/performance/web/js/feedback.js"
        };
    }
}