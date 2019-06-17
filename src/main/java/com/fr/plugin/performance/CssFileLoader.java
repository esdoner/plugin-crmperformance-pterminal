package com.fr.plugin.performance;

import com.fr.stable.fun.impl.AbstractCssFileHandler;

/**
 * Created by think on 2019/06/17
 */
public class CssFileLoader extends AbstractCssFileHandler {
    @Override
    public String[] pathsForFiles() {
        return new String[]{
                "/com/fr/plugin/performance/web/css/feedback.css"
        };
    }
}