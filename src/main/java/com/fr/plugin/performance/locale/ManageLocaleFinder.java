package com.fr.plugin.performance.locale;

import com.fr.stable.fun.impl.AbstractLocaleFinder;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/12
 * Description:none
 */
public class ManageLocaleFinder extends AbstractLocaleFinder {
    private static final String LOCALE= "com/fr/plugin/performance/locale/manage";

    @Override
    public String find() {
        return LOCALE;
    }
}
