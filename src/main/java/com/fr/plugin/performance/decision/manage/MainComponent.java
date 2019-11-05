package com.fr.plugin.performance.decision.manage;

import com.fr.web.struct.AssembleComponent;
import com.fr.web.struct.Atom;
import com.fr.web.struct.Filter;
import com.fr.web.struct.category.ScriptPath;
import com.fr.web.struct.category.StylePath;
import com.fr.web.struct.impl.FineUI;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/6
 * Description:none
 */
public final class MainComponent extends AssembleComponent {
    public static final MainComponent KEY= new MainComponent();

    public MainComponent() { }

    @Override
    public Atom[] refer() {
        return new Atom[]{ FineUI.KEY };
    }

    @Override
    public ScriptPath script() {
        return ScriptPath.build("com/fr/plugin/performance/web/js/decision/manage.js");
    }

    @Override
    public StylePath style() {
        return StylePath.build("com/fr/plugin/performance/web/css/decision/manage.css");
    }

    @Override
    public Filter filter() {
        return () -> true;
    }
}
