package com.fr.plugin.performance.decision.manage;

import com.fr.decision.fun.SystemOptionProvider;
import com.fr.decision.fun.impl.AbstractSystemOptionProvider;;
import com.fr.stable.fun.mark.API;
import com.fr.web.struct.Atom;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/5
 * Description:none
 */
@API(level = SystemOptionProvider.CURRENT_LEVEL)
public class MainOptionBridge extends AbstractSystemOptionProvider{
    @Override
    public String id() {
        return "decision-management-crm-main";
    }

    @Override
    public String displayName() {
        return "Dec_System_Option_Crm";
    }

    @Override
    public int sortIndex() {
        return ManageStaticItemId.DEC_MANAGEMENT_CRM_MAIN_INDEX;
    }

    @Override
    public Atom attach() {
        return com.fr.decision.web.MainComponent.KEY;
    }

    @Override
    public Atom client() { return com.fr.plugin.performance.decision.manage.MainComponent.KEY; }
}
