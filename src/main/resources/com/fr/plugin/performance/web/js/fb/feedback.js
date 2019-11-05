;
(function($) {
    var pluginName = 'feedback',
        defaults = {
            'border-radius': '2px',
            'background': '#000',
            'color': '#FFF',
            'text-align': 'center',
            'height':'48px',
            'width':'48px',
            'transition': 'all 0.2s linear',
            'cursor': 'pointer',
            'user-select': 'none',
            'z-index':'8001',
            'font-size':'12px'
        };
    function FeedbackBall($element, options){
        this.$element = $element;
        this.settings = $.extend({}, defaults, options);
        this.init();
    }

    FeedbackBall.prototype = {
        init: function(){
            this.settings['line-height']= this.settings['height'];
            this.$element.css(this.settings);
            this.findUpperX();
            this.renderStyle();
        },
        //locat the ball element
        findUpperX: function(){
            this.upperX = this.$element.attr('upper');
            this.indexX = Number(this.$element.attr('index'));
            this.levelX= Number(this.$element.attr('level'));
            this.$elder=$("#"+this.upperX).children('[index=\''+(this.indexX-1)+'\']')
            this.$younger=$("#"+this.upperX).children('[index=\''+(this.indexX+1)+'\']')

            if(this.$elder[0]){
                this.$elder.after(this.$element);
            } else if(this.$younger[0]){
                this.$younger.before(this.$element);
            } else {
                this.$element.appendTo($("#"+this.upperX));
            }
        },
        //render absolute style, judged by level
        renderStyle:function(){
            var _style= {},
                _zIndex= String(1000-this.levelX);
            if(this.levelX== 1){
                $.extend(_style,{
                    'position':'fixed',
                    //'z-index':_zIndex,
                    'opacity':0.5
                });
            } else {
                $.extend(_style,{
                    'position':'absolute',
                    'top':'0',
                    //'z-index':_zIndex,
                    'opacity':0
                });
            }
            this.$element.css(_style);
        },
        showAction:function(options){
            this.stressAction(options.stressaction);
            var $nodes= this.$element.children();
            $nodes.css('opacity',1);
            for(i=0;i<$nodes.length;i++){
                $($nodes[i]).css('top',34*(i+1)+'px');
            }
        },
        hideAction:function(options){
            this.stressAction(options.stressaction);
            var $nodes= this.$element.children();
            $nodes.css('opacity',0);
            for(i=0;i<$nodes.length;i++){
                $($nodes[i]).css('top','0px');
            };
        },
        clickAction:function(options){
            var $target= null,
                src= '',
                op= '';
            if(options.hasOwnProperty('use')){
                if(options.hasOwnProperty('target')){
                    try{
                        $target= $('#'+options['target']);
                        src= options['use']['viewlet'];
                        op= options['use']['op'];
                        this.$element.click(function(){
                            $.drawBlackBoard(src, op, $target);
                        });
                    } catch(err) {
                        console.trace(err);
                    }
                }
            }
        },
        //stress the ball you selected with options
        stressAction:function(options){
            this.$element.css(options);
        }
    }

    $.fn.extend({
        //instance getter
        FeedbackBall: function(options) {
            var ball = new FeedbackBall($(this), options);
            return ball;
        },
        //make the element can de draged
        beDrag: function(options) {
            var draging= false,
                $element= $(this);
            $element.on('dragstart selectstart',function(){
                return false;
            });
            $element.children().mousedown(function () {
                event.stopPropagation();
            });
            $element.mousedown(function () {
                draging= true;
            });
            $(document).on('mouseup',function () {
                draging = false;
            });
            $element.on('mouseup',function () {
                draging = false;
            });
            $(document).mousemove(function (e) {
                if (draging) {
                    var e = e || window.event;
                    var height = $(document.body).height(),
                        width = $(window).width(),
                        beingWidth = $element.width(),
                        beingHeight = $element.height(),
                        left = e.clientX - beingWidth / 2,
                        top = e.clientY - 18 + $(document).scrollTop(),
                        location= {};
                    if (top >= 0 && top < height - beingHeight) {
                        $.extend(location,{"top": top});
                    }
                    if (left >= 0 && left < width - beingWidth) {
                        $.extend(location,{"left": left});
                    }
                    return $element.css(location);
                }
            });
        }
    });

    $.extend({
        //disable on touch devices
        validateDevice: function () {
            if(!window.navigator) {
                return false;
            } else {
                return !(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini|Mobile/i.test(navigator.userAgent));
            }
        },
        //simply find url param,maybe useless in some way
        getUrlParam: function(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
         },
        //show blackboard
        drawBlackBoard:function(src, op, $blackBoard){
            /**/
            var paras = {
                sfineuser: FR.cjkEncode(FR.formulaEvaluator('$fine_username', '', true)()),
                sreportname: FR.cjkEncode(FR.formulaEvaluator('reportName', '', true)()),
                sformletname: FR.cjkEncode(FR.formulaEvaluator('formletName', '', true)()),
                sSessionID: FR.cjkEncode(FR.formulaEvaluator('sessionID', '', true)()),
                nofdball_def: 1
            };
            src= FR.servletURL+ '?'+ $.param($.extend({}, {viewlet: src, op: op}, paras));
            $blackBoard.find('iframe').attr('src', src);
            $blackBoard.css('display', 'block');
        },
        //judge whether the page is wrong
        isCorrectPage: function(){
            return $('title').text()!= decodeURI("%E5%87%BA%E9%94%99%E9%A1%B5%E9%9D%A2") && FR;
        }
    });
})(jQuery);
$(document).ready(function(){
    if($('#newdemo')[0] != undefined){
        return;
    }
    if($.validateDevice() && FR && $.isCorrectPage() && $.getUrlParam('nofdball_def')!= 1){
        var ctx= '<div id="newdemo" level="1">反馈</div>' +
            '<div id="sball2" upper="newdemo" level="2" index="2">建议</div>' +
            '<div id="sball1" upper="newdemo" level="2" index="1">太慢</div>' +
            '<div id="sball3" upper="newdemo" level="2" index="3">关于</div>';
        ctx= ctx+ '<div id="crm_fb_blackboard" style="display:none">'+
            '<div id="crm_fd_byebye"></div>'+
            '<div id="crm_fb_pad"><iframe src=""></iframe></div>'+
            '</div>';
        if($('body')[0]){
            $('body').append(ctx);
        } else {
            $('html').append('<body>'+ ctx+ '</body>');
        }

        var level1Style= {
                'height':'32px',
                'width':'32px',
                'background': 'rgba(43, 153, 255,1)',
                'transition': 'all 0s linear',
                'right':'20px'
            },
            level2Style= {
                'height':'26px',
                'width':'26px',
                'background':'#FFF',
                'color':'rgba(43, 153, 255,1)',
                'border':'1px solid rgba(43, 153, 255,1)',
                'margin':'3px'
            };
        var newdemo= $('#newdemo').FeedbackBall(level1Style),
            sball1= $('#sball1').FeedbackBall(level2Style),
            sball2= $('#sball2').FeedbackBall(level2Style),
            sball3= $('#sball3').FeedbackBall(level2Style),
        timer= null;
        $('#newdemo').mouseover(function(){
            clearInterval(timer);
            newdemo.showAction({stressaction:{opacity:'1','font-size':'13px'}});
        });
        $('#newdemo').mouseout(function(){
            timer= setTimeout(function(){
                newdemo.hideAction({stressaction:{opacity:'0.5','font-size':'12px'}});
            }, 300);
        });
        $('#newdemo').beDrag();

        var servletURL= FR.serverURL+FR.fineServletURL+'/view/report';
        sball1.clickAction({use:{viewlet:'performance2018/fdball/fbb_1.cpt',op:'write'},target:'crm_fb_blackboard'});
        sball2.clickAction({use:{viewlet:'performance2018/fdball/fbb_3.cpt',op:'write'},target:'crm_fb_blackboard'});
        sball3.clickAction({use:{viewlet:'performance2018/fdball/fbb_4.cpt',op:'write'},target:'crm_fb_blackboard'});

        $('#crm_fd_byebye').click(function(){
            $('#crm_fb_blackboard').css('display','none');
            $('#crm_fb_pad>iframe').attr('src','');
        })

        $('#crm_fd_byebye').on('mouseover', function(){
            $(this).css('transform', 'rotate(180deg)');
        })

        $('#crm_fd_byebye').on('mouseout mouseleave', function(){
            $(this).css('transform', 'rotate(0deg)');
        })
    }
});