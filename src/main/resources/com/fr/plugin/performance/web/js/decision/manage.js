;
(function(){
    /**
     * 通过config配置管理系统下CRM管理组件
     */
    BI.config("dec.constant.management.navigation", function (items) {
        items.push({
            value: "crmManage",
            text: BI.i18nText("Plugin-Performance-P_Decision_Manage_Main_Display"),
            id: "decision-management-crm-main",
            cls: "setting-font"
        });
        return items;
    });
})();