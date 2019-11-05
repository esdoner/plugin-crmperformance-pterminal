package com.fr.plugin.performance.locale;

import com.fr.stable.fun.impl.AbstractLocaleFinder;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/6/17
 * Description:none
 */
public class PerformanceLocaleFinder extends AbstractLocaleFinder {
    private static final String LOCALE= "com/fr/plugin/performance/locale/pterminal";

    @Override
    public String find() {
        return LOCALE;
    }
}