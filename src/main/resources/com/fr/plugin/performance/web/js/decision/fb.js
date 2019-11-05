;
(function() {
    /**
     *
     */
    var main = BI.inherit(BI.Widget, {
        render: function () {
            return {
                type: "bi.vtape",
                cls: "dec-system-management",
                items: [{
                    type:"bi.htape",
                    cls: "bi-card bi-border-bottom",
                    height:40,
                    items: [{
                        el: {
                            type: "bi.flex_vertical_adapt",
                            cls: "decision-line-segment dec-font-weight-bold bi-button-group",
                            height:40,
                            items:[{
                                el:{
                                    type: "bi.text_button",
                                    text: "测试1",
                                }
                            },{
                                el:{
                                    type: "bi.text_button",
                                    text: "测试2",
                                }
                            },{
                                el:{
                                    type: "bi.text_button",
                                    text: "测试3",
                                }
                            }
                            ]
                        },
                        width: 600,
                    }]
                },{

                }]
            };
        }
    });
    BI.shortcut("dec.management.crm_feedback", main);

    /**
     * 菜单栏
     */
    var tab1 = BI.inherit(BI.Widget, {

    })
    /**
     * 通过config配置CRM管理下模板反馈组件
     */
    BI.config("dec.constant.management.navigation", function (items) {
        items.push({
            value: "feedbackManage",
            text: BI.i18nText("Plugin-Performance-P_Decision_Manage_Feedback_Display"),
            cardType: "dec.management.crm_feedback",
            id: "decision-management-crm-feedback",
            pId: "decision-management-crm-main",
            cls: "management-user-font"
        });
        return items;
    });
})();