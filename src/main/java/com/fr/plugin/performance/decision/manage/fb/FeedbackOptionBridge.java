package com.fr.plugin.performance.decision.manage.fb;

import com.fr.decision.authority.base.AuthorityConstants;
import com.fr.decision.authority.base.constant.AuthorityStaticItemId;
import com.fr.decision.fun.SystemOptionProvider;
import com.fr.decision.fun.impl.AbstractSystemOptionProvider;
import com.fr.plugin.performance.decision.manage.ManageStaticItemId;
import com.fr.stable.fun.mark.API;
import com.fr.web.struct.Atom;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/9/11
 * Description: 反馈管理模块
 */
@API(level = SystemOptionProvider.CURRENT_LEVEL)
public class FeedbackOptionBridge extends AbstractSystemOptionProvider {
    @Override
    public String id() {
        return ManageStaticItemId.DEC_MANAGEMENT_CRM_FEEDBACK_ID;
    }

    @Override
    public String displayName() {
        return "Dec_System_Option_Crm_Feedback";
    }

    @Override
    public int sortIndex() {
        return ManageStaticItemId.DEC_MANAGEMENT_CRM_FEEDBACK_INDEX;
    }

    @Override
    public Atom attach() {
        return com.fr.decision.web.MainComponent.KEY;
    }

    @Override
    public Atom client() {
        return FeedbackComponent.KEY;
    }

    @Override
    public String parentId() { return ManageStaticItemId.DEC_MANAGEMENT_CRM_MAIN_ID; }

    @Override
    public String fullPath() { return AuthorityStaticItemId.DEC_MANAGEMENT_ID + AuthorityConstants.FULL_PATH_SPLITTER + ManageStaticItemId.DEC_MANAGEMENT_CRM_MAIN_ID;}
}

