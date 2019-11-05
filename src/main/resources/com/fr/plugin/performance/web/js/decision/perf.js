;
(function() {
    /**
     *
     */
    var main = BI.inherit(BI.Widget, {
        render: function () {

        }
    });
    BI.shortcut("dec.management.crm_performance", main);

    /**
     * 通过config配置CRM管理下模板性能组件
     */
    BI.config("dec.constant.management.navigation", function (items) {
        items.push({
            value: "performanceManage",
            text: BI.i18nText("Plugin-Performance-P_Decision_Manage_Performance_Display"),
            cardType: "dec.management.crm_performance",
            id: "decision-management-crm-performance",
            pId: "decision-management-crm-main",
            cls: "management-maintenance-detect-font"
        });
        return items;
    });
})();