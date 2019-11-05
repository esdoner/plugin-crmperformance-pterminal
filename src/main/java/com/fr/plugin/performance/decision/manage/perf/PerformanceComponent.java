package com.fr.plugin.performance.decision.manage.perf;

import com.fr.web.struct.AssembleComponent;
import com.fr.web.struct.Atom;
import com.fr.web.struct.Filter;
import com.fr.web.struct.category.ScriptPath;
import com.fr.web.struct.category.StylePath;
import com.fr.web.struct.impl.FineUI;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/11
 * Description:none
 */
public class PerformanceComponent extends AssembleComponent {
    public static final PerformanceComponent KEY = new PerformanceComponent();

    public PerformanceComponent() { }

    @Override
    public Atom[] refer() {
        return new Atom[]{ FineUI.KEY };
    }

    @Override
    public ScriptPath script() { return ScriptPath.build("com/fr/plugin/performance/web/js/decision/perf.js"); }

    @Override
    public StylePath style() { return StylePath.build("com/fr/plugin/performance/web/css/decision/perf.css"); }

    @Override
    public Filter filter() {
        return () -> true;
    }

}
