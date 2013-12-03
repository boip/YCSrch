/**
* var str = '<div>{0}-{1}</div>';
* str.format('e','f');
* str = <div>e-f</div>
**/
String.prototype.format = function () {
  var args = arguments;
  return this.replace(/\{(\d+)\}/g, function (m, n) { return args[n]; });
};
var App = function () {

    var isIE8 = false; // IE8 mode
    var isIE9 = false;
    var currentPage = ''; // current page

    var ajaxRequest=function (args) {
    	//args{url:'',data:{},success:func}
        var opts = {type: 'POST',dataType: 'json'};
        if (args.data) {opts.data=args.data;}
        if (args.url) {opts.url=args.url;}
        opts.success = function (data) {
            if (args.success) {
            	args.success(data);
            }
        };
        opts.error = function () {
            if (args.error) {
            	args.error();
            }
        };
        opts.complete = function () {
            if (args.complete) {
            	args.complete();
            }
        };
        $.ajax(opts);
    };
    var setSelectByAjax = function(selectObj,url,isAdd){
        selectObj.empty();
        if(!isAdd)
        	$('<option value="">请选择</option>').appendTo(selectObj);
        ajaxRequest({
        	url:url,
        	success:function(data){
                if (data.errorMsg==null||!data.errorMsg=='') {
                    alert(data.errorMsg);
                    return;
                }
                var options = data.Options;
                for (var i = 0; i < options.length; i++) {
                    $('<option>' + options[i].DisplayText + '</option>').val(options[i].Value)
                        .appendTo(selectObj);
                }
                
        	}
        });
    };
    // useful function to make equal height for contacts stand side by side
    var setEqualHeight = function (columns) {
        var tallestColumn = 0;
        columns = jQuery(columns);
        columns.each(function () {
            var currentHeight = $(this).height();
            if (currentHeight > tallestColumn) {
                tallestColumn = currentHeight;
            }
        });
        columns.height(tallestColumn);
    }

    // this function handles responsive layout on screen size resize or mobile device rotate.
    var handleResponsive = function () {
        if (jQuery.browser.msie && jQuery.browser.version.substr(0, 1) == 8) {
            isIE8 = true; // checkes for IE8 browser version
            $('.visible-ie8').show(); //
        }
        if (jQuery.browser.msie && jQuery.browser.version.substr(0, 1) == 9) {
            isIE9 = true;
        }

        var isIE10 = !! navigator.userAgent.match(/MSIE 10/);

        if (isIE10) {
            jQuery('html').addClass('ie10'); // set ie10 class on html element.
        }

        // loops all page elements with "responsive" class and applied classes for tablet mode
        // For metornic  1280px or less set as tablet mode to display the content properly
        var handleTabletElements = function () {
            if ($(window).width() <= 1280) {
                $(".responsive").each(function () {
                    var forTablet = $(this).attr('data-tablet');
                    var forDesktop = $(this).attr('data-desktop');
                    if (forTablet) {
                        $(this).removeClass(forDesktop);
                        $(this).addClass(forTablet);
                    }
                });
                handleTooltip();
            }
        }

        // loops all page elements with "responsive" class and applied classes for desktop mode
        // For metornic  higher 1280px set as desktop mode to display the content properly
        var handleDesktopElements = function () {
            if ($(window).width() > 1280) {
                $(".responsive").each(function () {
                    var forTablet = $(this).attr('data-tablet');
                    var forDesktop = $(this).attr('data-desktop');
                    if (forTablet) {
                        $(this).removeClass(forTablet);
                        $(this).addClass(forDesktop);
                    }
                });
                handleTooltip();
            }
        }

        // handle all elements which require to re-initialize on screen width change(on resize or on rotate mobile device)
        var handleElements = function () {
            if (App.isPage("index")) {
                handleDashboardCalendar(); // handles full calendar for main page
                jQuery('.vmaps').each(function () {
                    var map = jQuery(this);
                    map.width(map.parent().width());
                });
            }

            if (App.isPage("charts")) {
                handleChartGraphs();
            }

            if (App.isPage("maps_vector")) { // jqvector maps requires to fix the width on screen resized.
                jQuery('.vmaps').each(function () {
                    var map = jQuery(this);
                    map.width(map.parent().width());
                });
            }

            if (App.isPage("calendar")) { // full calendar requires to fix the width on screen resized.
                handleCalendar();
            }

            if ($(window).width() < 900) { // remove sidebar toggler
                $.cookie('sidebar-closed', null);
                $('.page-container').removeClass("sidebar-closed");
            }

            handleTabletElements();
            handleDesktopElements();
        }

        // handles responsive breakpoints.
        $(window).setBreakpoints({
            breakpoints: [320, 480, 768, 900, 1024, 1280]
        });

        $(window).bind('exitBreakpoint320', function () {
            handleElements();
        });
        $(window).bind('enterBreakpoint320', function () {
            handleElements();
        });

        $(window).bind('exitBreakpoint480', function () {
            handleElements();
        });
        $(window).bind('enterBreakpoint480', function () {
            handleElements();
        });

        $(window).bind('exitBreakpoint768', function () {
            handleElements();
        });
        $(window).bind('enterBreakpoint768', function () {
            handleElements();
        });

        $(window).bind('exitBreakpoint900', function () {
            handleElements();
        });
        $(window).bind('enterBreakpoint900', function () {
            handleElements();
        });

        $(window).bind('exitBreakpoint1024', function () {
            handleElements();
        });
        $(window).bind('enterBreakpoint1024', function () {
            handleElements();
        });

        $(window).bind('exitBreakpoint1280', function () {
            handleElements();
        });
        $(window).bind('enterBreakpoint1280', function () {
            handleElements();
        });
    }

    var handleMainMenu = function () {
        jQuery('.page-sidebar .has-sub > a').click(function () {

            var handleContentHeight = function () {
                var content = $('.page-content');
                var sidebar = $('.page-sidebar');

                if (!content.attr("data-height")) {
                    content.attr("data-height", content.height());
                }


                if (sidebar.height() > content.height()) {
                    content.css("min-height", sidebar.height() + 20);
                } else {
                    content.css("min-height", content.attr("data-height"));
                }
            }

            var last = jQuery('.has-sub.open', $('.page-sidebar'));
            if (last.size() == 0) {
                //last = jQuery('.has-sub.active', $('.page-sidebar'));
            }
            last.removeClass("open");
            jQuery('.arrow', last).removeClass("open");
            jQuery('.sub', last).slideUp(200);

            var sub = jQuery(this).next();
            if (sub.is(":visible")) {
                jQuery('.arrow', jQuery(this)).removeClass("open");
                jQuery(this).parent().removeClass("open");
                sub.slideUp(200, function () {
                    handleContentHeight();
                });
            } else {
                jQuery('.arrow', jQuery(this)).addClass("open");
                jQuery(this).parent().addClass("open");
                sub.slideDown(200, function () {
                    handleContentHeight();
                });
            }
        });
    }

    var handleSidebarToggler = function () {

        var container = $(".page-container");
		var header = $(".header");
        if ($.cookie('sidebar-closed') == 1) {
            container.addClass("sidebar-closed");
            $(".page-sidebar > .user").addClass("sidebar-closed");
            header.css("margin-left","35px");
        }

        // handle sidebar show/hide
        $('.page-sidebar .sidebar-toggler').click(function () {
            $(".sidebar-search").removeClass("open");
            var container = $(".page-container");
            var header = $(".header");
            if (container.hasClass("sidebar-closed") === true) {
            	header.css("margin-left","205px");
            	$(".page-sidebar > .user").removeClass("sidebar-closed");
                container.removeClass("sidebar-closed");
                $.cookie('sidebar-closed', null);
            } else {
            	$(".page-sidebar > .user").addClass("sidebar-closed");
            	header.css("margin-left","35px");
                container.addClass("sidebar-closed");
                $.cookie('sidebar-closed', 1);
            }
			return false;
        });

        // handle the search bar close
        $('.sidebar-search .remove').click(function () {
            $('.sidebar-search').removeClass("open");
        });

        // handle the search query submit on enter press
        $('.sidebar-search input').keypress(function (e) {
            if (e.which == 13) {
                window.location.href = "extra_search.html";
                return false; //<---- Add this line
            }
        });

        // handle the search submit
        $('.sidebar-search .submit').click(function () {
            if ($('.page-container').hasClass("sidebar-closed")) {
                if ($('.sidebar-search').hasClass('open') == false) {
                    $('.sidebar-search').addClass("open");
                } else {
                    window.location.href = "extra_search.html";
                }
            } else {
                window.location.href = "extra_search.html";
            }
        });
    }

    var handlePortletTools = function () {
        jQuery('.portlet .tools a.remove').click(function () {
            var removable = jQuery(this).parents(".portlet");
            if (removable.next().hasClass('portlet') || removable.prev().hasClass('portlet')) {
                jQuery(this).parents(".portlet").remove();
            } else {
                jQuery(this).parents(".portlet").parent().remove();
            }
        });

        jQuery('.portlet .tools a.reload').click(function () {
            var el = jQuery(this).parents(".portlet");
            App.blockUI(el);
            window.setTimeout(function () {
                App.unblockUI(el);
            }, 1000);
        });

        jQuery('.portlet .tools .collapse, .portlet .tools .expand').click(function () {
            var el = jQuery(this).parents(".portlet").children(".portlet-body");
            if (jQuery(this).hasClass("collapse")) {
                jQuery(this).removeClass("collapse").addClass("expand");
                el.slideUp(200);
            } else {
                jQuery(this).removeClass("expand").addClass("collapse");
                el.slideDown(200);
            }
        });

        /*
        sample code to handle portlet config popup on close
        $('#portlet-config').on('hide', function (e) {
            //alert(1);
            //if (!data) return e.preventDefault() // stops modal from being shown
        });
        */
    }

    var handlePortletSortable = function () {
        if (!jQuery().sortable) {
            return;
        }

        $("#sortable_portlets").sortable({
            connectWith: ".portlet",
            items: ".portlet",
            opacity: 0.8,
            coneHelperSize: true,
            placeholder: 'sortable-box-placeholder round-all',
            forcePlaceholderSize: true,
            tolerance: "pointer"
        });

        $(".column").disableSelection();

    }

    var handleFancyBox = function () {

        if (!jQuery.fancybox) {
            return;
        }

        if (jQuery(".fancybox-button").size() > 0) {
            jQuery(".fancybox-button").fancybox({
                groupAttr: 'data-rel',
                prevEffect: 'none',
                nextEffect: 'none',
                closeBtn: true,
                helpers: {
                    title: {
                        type: 'inside'
                    }
                }
            });
        }
    }

    var handleLoginForm = function () {

        $('#login-form').validate({
            errorElement: 'label', //default input error message container
            errorClass: 'help-inline', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
            	user_id: {
                    required: true
                },
                password: {
                    required: true
                },
                remember: {
                    required: false
                }
            },

            messages: {
            	user_id: {
                    required: "用户名为必输."
                },
                password: {
                    required: "密码为必输."
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit   
                $('.alert-error', $('.login-form')).show();
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.control-group').addClass('error'); // set error class to the control group
            },

            success: function (label) {
                label.closest('.control-group').removeClass('error');
                label.remove();
            },

            errorPlacement: function (error, element) {
                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function (form) {
            	form.submit();
            }
        });

        $(document).keypress(function (e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                	$('#login-form').submit();
                    //window.location.href = "main.do";
                }
                return false;
            }
        });


    };

    var handleFixInputPlaceholderForIE = function () {
        //fix html5 placeholder attribute for ie7 & ie8
    	
        if (/msie/.test(navigator.userAgent.toLowerCase()) && !$.support.leadingWhitespace) { // ie7&ie8

            // this is html5 placeholder fix for inputs, inputs with placeholder-no-fix class will be skipped(e.g: we need this for password fields)
            jQuery('input[placeholder]:not(.placeholder-no-fix), textarea[placeholder]:not(.placeholder-no-fix)').each(function () {

                var input = jQuery(this);

                jQuery(input).addClass("placeholder").val(input.attr('placeholder'));

                jQuery(input).focus(function () {
                    if (input.val() == input.attr('placeholder')) {
                        input.val('');
                    }
                });

                jQuery(input).blur(function () {
                    if (input.val() == '' || input.val() == input.attr('placeholder')) {
                        input.val(input.attr('placeholder'));
                    }
                });
            });
        }
    };


    var handleTooltip = function () {
        if (App.isTouchDevice()) { // if touch device, some tooltips can be skipped in order to not conflict with click events
            jQuery('.tooltips:not(.no-tooltip-on-touch-device)').tooltip();
        } else {
            jQuery('.tooltips').tooltip();
        }
    };

    var handlePopover = function () {
        jQuery('.popovers').popover();
    };

    var handleChoosenSelect = function () {
        if (!jQuery().chosen) {
            return;
        }
        $(".chosen").chosen();

        $(".chosen-with-diselect").chosen({
            allow_single_deselect: true
        })
    };

    var initChosenSelect = function (els) {
        $(els).chosen({
            allow_single_deselect: true
        })
    };



    var handleDateTimePickers = function () {

        if (jQuery().datepicker) {
            $('.date-picker').datepicker();
        }

        if (jQuery().timepicker) {
            $('.timepicker-default').timepicker();
            $('.timepicker-24').timepicker({
                minuteStep: 1,
                showSeconds: true,
                showMeridian: false
            });
        }

        if (!jQuery().daterangepicker) {
            return;
        }

        $('.date-range').daterangepicker();

        $('#dashboard-report-range').daterangepicker({
            ranges: {
                'Today': ['today', 'today'],
                'Yesterday': ['yesterday', 'yesterday'],
                'Last 7 Days': [Date.today().add({
                    days: -6
                }), 'today'],
                'Last 30 Days': [Date.today().add({
                    days: -29
                }), 'today'],
                'This Month': [Date.today().moveToFirstDayOfMonth(), Date.today().moveToLastDayOfMonth()],
                'Last Month': [Date.today().moveToFirstDayOfMonth().add({
                    months: -1
                }), Date.today().moveToFirstDayOfMonth().add({
                    days: -1
                })]
            },
            opens: 'left',
            format: 'MM/dd/yyyy',
            separator: ' to ',
            startDate: Date.today().add({
                days: -29
            }),
            endDate: Date.today(),
            minDate: '01/01/2012',
            maxDate: '12/31/2014',
            locale: {
                applyLabel: 'Submit',
                fromLabel: 'From',
                toLabel: 'To',
                customRangeLabel: 'Custom Range',
                daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
                monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                firstDay: 1
            },
            showWeekNumbers: true,
            buttonClasses: ['btn-danger']
        },

        function (start, end) {
            App.blockUI(jQuery("#dashboard"));
            setTimeout(function () {
                App.unblockUI(jQuery("#dashboard"));
                $.gritter.add({
                    title: 'Dashboard',
                    text: 'Dashboard date range updated.'
                });
                App.scrollTo();
            }, 1000);
            $('#dashboard-report-range span').html(start.toString('MMMM d, yyyy') + ' - ' + end.toString('MMMM d, yyyy'));

        });

        $('#dashboard-report-range').show();

        $('#dashboard-report-range span').html(Date.today().add({
            days: -29
        }).toString('MMMM d, yyyy') + ' - ' + Date.today().toString('MMMM d, yyyy'));

        $('#form-date-range').daterangepicker({
            ranges: {
                'Today': ['today', 'today'],
                'Yesterday': ['yesterday', 'yesterday'],
                'Last 7 Days': [Date.today().add({
                    days: -6
                }), 'today'],
                'Last 30 Days': [Date.today().add({
                    days: -29
                }), 'today'],
                'This Month': [Date.today().moveToFirstDayOfMonth(), Date.today().moveToLastDayOfMonth()],
                'Last Month': [Date.today().moveToFirstDayOfMonth().add({
                    months: -1
                }), Date.today().moveToFirstDayOfMonth().add({
                    days: -1
                })]
            },
            opens: 'right',
            format: 'MM/dd/yyyy',
            separator: ' to ',
            startDate: Date.today().add({
                days: -29
            }),
            endDate: Date.today(),
            minDate: '01/01/2012',
            maxDate: '12/31/2014',
            locale: {
                applyLabel: 'Submit',
                fromLabel: 'From',
                toLabel: 'To',
                customRangeLabel: 'Custom Range',
                daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
                monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                firstDay: 1
            },
            showWeekNumbers: true,
            buttonClasses: ['btn-danger']
        },

        function (start, end) {
            $('#form-date-range span').html(start.toString('MMMM d, yyyy') + ' - ' + end.toString('MMMM d, yyyy'));
        });

        $('#form-date-range span').html(Date.today().add({
            days: -29
        }).toString('MMMM d, yyyy') + ' - ' + Date.today().toString('MMMM d, yyyy'));


        if (!jQuery().datepicker || !jQuery().timepicker) {
            return;
        }
    }

    var handleClockfaceTimePickers = function () {

        if (!jQuery().clockface) {
            return;
        }

        $('#clockface_1').clockface();

        $('#clockface_2').clockface({
            format: 'HH:mm',
            trigger: 'manual'
        });

        $('#clockface_2_toggle-btn').click(function (e) {
            e.stopPropagation();
            $('#clockface_2').clockface('toggle');
        });

        $('#clockface_3').clockface({
            format: 'H:mm'
        }).clockface('show', '14:30');
    }

    var handleAccordions = function () {
        $(".accordion").collapse().height('auto');

        var lastClicked;

        //add scrollable class name if you need scrollable panes
        jQuery('.accordion.scrollable .accordion-toggle').click(function () {
            lastClicked = jQuery(this);
        }); //move to faq section

        jQuery('.accordion.scrollable').on('shown', function () {
            jQuery('html,body').animate({
                scrollTop: lastClicked.offset().top - 150
            }, 'slow');
        });
    }

    var handleSliders = function () {
        // basic
        $(".slider-basic").slider(); // basic sliders

        // snap inc
        $("#slider-snap-inc").slider({
            value: 100,
            min: 0,
            max: 1000,
            step: 100,
            slide: function (event, ui) {
                $("#slider-snap-inc-amount").text("$" + ui.value);
            }
        });

        $("#slider-snap-inc-amount").text("$" + $("#slider-snap-inc").slider("value"));

        // range slider
        $("#slider-range").slider({
            range: true,
            min: 0,
            max: 500,
            values: [75, 300],
            slide: function (event, ui) {
                $("#slider-range-amount").text("$" + ui.values[0] + " - $" + ui.values[1]);
            }
        });

        $("#slider-range-amount").text("$" + $("#slider-range").slider("values", 0) + " - $" + $("#slider-range").slider("values", 1));

        //range max

        $("#slider-range-max").slider({
            range: "max",
            min: 1,
            max: 10,
            value: 2,
            slide: function (event, ui) {
                $("#slider-range-max-amount").text(ui.value);
            }
        });

        $("#slider-range-max-amount").text($("#slider-range-max").slider("value"));

        // range min
        $("#slider-range-min").slider({
            range: "min",
            value: 37,
            min: 1,
            max: 700,
            slide: function (event, ui) {
                $("#slider-range-min-amount").text("$" + ui.value);
            }
        });

        $("#slider-range-min-amount").text("$" + $("#slider-range-min").slider("value"));

        // 
        // setup graphic EQ
        $("#slider-eq > span").each(function () {
            // read initial values from markup and remove that
            var value = parseInt($(this).text(), 10);
            $(this).empty().slider({
                value: value,
                range: "min",
                animate: true,
                orientation: "vertical"
            });
        });

        // vertical slider
        $("#slider-vertical").slider({
            orientation: "vertical",
            range: "min",
            min: 0,
            max: 100,
            value: 60,
            slide: function (event, ui) {
                $("#slider-vertical-amount").text(ui.value);
            }
        });
        $("#slider-vertical-amount").text($("#slider-vertical").slider("value"));

        // vertical range sliders
        $("#slider-range-vertical").slider({
            orientation: "vertical",
            range: true,
            values: [17, 67],
            slide: function (event, ui) {
                $("#slider-range-vertical-amount").text("$" + ui.values[0] + " - $" + ui.values[1]);
            }
        });

        $("#slider-range-vertical-amount").text("$" + $("#slider-range-vertical").slider("values", 0) + " - $" + $("#slider-range-vertical").slider("values", 1));
    }

    var handleGoTop = function () {
        /* set variables locally for increased performance */
        jQuery('.footer .go-top').click(function () {
            App.scrollTo();
        });
    }

    var handleStyler = function () {

        var panel = $('.color-panel');

        $('.icon-color', panel).click(function () {
            $('.color-mode').show();
            $('.icon-color-close').show();
        });

        $('.icon-color-close', panel).click(function () {
            $('.color-mode').hide();
            $('.icon-color-close').hide();
        });

        $('li', panel).click(function () {
            var color = $(this).attr("data-style");
            setColor(color);
            $('.inline li', panel).removeClass("current");
            $(this).addClass("current");
        });

        $('input', panel).change(function () {
            setLayout();
        });

        var setColor = function (color) {
            $('#style_color').attr("href", "assets/css/style_" + color + ".css");
        }

        var setLayout = function () {
            if ($('input.header', panel).is(":checked")) {
                $("body").addClass("fixed-top");
                $(".header").addClass("navbar-fixed-top");
            } else {
                $("body").removeClass("fixed-top");
                $(".header").removeClass("navbar-fixed-top");
            }
        }
    };

    var handleFormWizards = function () {
        if (!jQuery().bootstrapWizard) {
            return;
        }

        // default form wizard
        $('#form_wizard_1').bootstrapWizard({
            'nextSelector': '.button-next',
            'previousSelector': '.button-previous',
            onTabClick: function (tab, navigation, index) {
                alert('on tab click disabled');
                return false;
            },
            onNext: function (tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title', $('#form_wizard_1')).text('Step ' + (index + 1) + ' of ' + total);
                // set done steps
                jQuery('li', $('#form_wizard_1')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }

                if (current == 1) {
                    $('#form_wizard_1').find('.button-previous').hide();
                } else {
                    $('#form_wizard_1').find('.button-previous').show();
                }

                if (current >= total) {
                    $('#form_wizard_1').find('.button-next').hide();
                    $('#form_wizard_1').find('.button-submit').show();
                } else {
                    $('#form_wizard_1').find('.button-next').show();
                    $('#form_wizard_1').find('.button-submit').hide();
                }
                App.scrollTo($('.page-title'));
            },
            onPrevious: function (tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title', $('#form_wizard_1')).text('Step ' + (index + 1) + ' of ' + total);
                // set done steps
                jQuery('li', $('#form_wizard_1')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }

                if (current == 1) {
                    $('#form_wizard_1').find('.button-previous').hide();
                } else {
                    $('#form_wizard_1').find('.button-previous').show();
                }

                if (current >= total) {
                    $('#form_wizard_1').find('.button-next').hide();
                    $('#form_wizard_1').find('.button-submit').show();
                } else {
                    $('#form_wizard_1').find('.button-next').show();
                    $('#form_wizard_1').find('.button-submit').hide();
                }

                App.scrollTo($('.page-title'));
            },
            onTabShow: function (tab, navigation, index) {
                var total = navigation.find('li').length;
                var current = index + 1;
                var $percent = (current / total) * 100;
                $('#form_wizard_1').find('.bar').css({
                    width: $percent + '%'
                });
            }
        });

        $('#form_wizard_1').find('.button-previous').hide();
        $('#form_wizard_1 .button-submit').click(function () {
            alert('Finished! Hope you like it :)');
        }).hide();
    };

    
    
	//sizeChange main-frame
	var handleSizeChange = function(){
		$('.page-sidebar > ul').height(top.$(window).height());
		var mainFrame = top.$('#mainFrame'),pageContent=top.$(".page-content");
		var	minHeight = $(window).height()-46,
			pageHeight= mainFrame.contents().height();
		
		pageContent.css("min-height",minHeight+"px");
		mainFrame.height((minHeight>pageHeight)?minHeight:pageHeight);
		return false;
	};
	var handleIframeLoad =  function (){
		$('#mainFrame').attr("onload","App.resize()");
	};
	var handlePortletToggle =function(){
		$("button[class^='portlet-ctrl-toggle']").click(function () {
			var operDiv = $("button[class^='portlet-ctrl-toggle']").parents().parents().next().filter('.portlet-body');
	        if (operDiv.is(":hidden") === true) {
	        	$("button[class^='portlet-ctrl-toggle']").html('<i class="icon-chevron-down"></i> 关闭');
	        	operDiv.show();
	        } else {
	        	$("button[class^='portlet-ctrl-toggle']").html('<i class="icon-chevron-up"></i> 展开');
	        	operDiv.hide();
	        }
	        App.resize();
	        return false;
	    });
	};

	
	var handleQueryTaskLoad =  function (actions,type){
        $('#task-list-div').jtable({
            title: '<i class="icon-reorder"></i>任务列表',
            selecting: true, //Enable selecting 
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            //sorting: true, //Enable sorting
            dataIColl:'iColl_queryTasks',
            columnResizable:false,openChildAsAccordion: true,
            actions: actions,dialogs:{height:'450px',top:'300px'},
            fields: {
            	task_number: {
                    key: true,
                    list: false,
                    create: false,
                    edit: false
                },
                taskLink: {
                    title: '',
                    width: '5%',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (taskData) {
                        //Create an image that will be used to open child table
                        var $icon = $('<button title="任务链" class="jtable-command-button"><i class="icon-sitemap" /></button>');
                        //Open child table when user clicks the image
                        $icon.click(function () {
                            $('#task-list-div').jtable('openChildTable',
                                    $icon.closest('tr'),
                                    {
                                        title: taskData.record.task + ' - 任务链',
                                        actions: {
                                            listAction: actions.childAction+ taskData.record.task_number,
                                        },
                                        dataIColl:'iColl_taskTree',
                                        treeable:true,
                                        fields: {
                                        	task_number: {key: true,list:false},
                                            parent_id: {list: false,pkey:true},
                                            task: {title: '任务名称',width:'20%',className:'span7',inputClass:'span7'},
                                            leader: {title: '负责人'},
                                            progress: {title: '进度',width:'20%',list:true,create: false,display:function(obj){
                                            	var record = obj.record;
                                            	var bars = [[75,'progress-success'],[50,'progress-info'],[25,'progress-warning'],[0,'progress-danger']];
                                            	for (var bar in bars) {
                                            		var progressTxt = '<div class="progress {0}"><div class="bar" style="width: {1}%;">{1}%</div></div>';
                                            		if(parseInt(bars[bar][0])<=parseInt(record.progress))
                                            			return progressTxt.format(bars[bar][1],record.progress,record.progress); 
                                            	}
                                            	return record.progress; 
                                            }}
                                        }
                                    }, function (data) { //opened handler
                                        data.childTable.jtable('load');
                                    });
                        });
                        return $icon;
                    }
                },
                task: {title: '任务名称',width:'20%',className:'span7',inputClass:'span7'},
                parent_id: {
                	title:'父任务',
                    list: false,
                    create: true,
                    edit: true,
                    className:'span5',
                    inputClass:'span5',
                    type:'chosen',
                    chosen:{allow_single_deselect: true},
                    options:actions.taskOption
                },
                imports: {title: '重点',list:false,id:'imports',options:function(){
                	var data={};
                	for(var i=0;i<params.imports.length;i++){
                		data[params.imports[i].key]=params.imports[i].val;
                	}
                	return data;
            	},display:function(obj){
                	for(var i=0;i<params[this.id].length;i++){
                		if(params[this.id][i].key==obj.record[this.id])
                			return params[this.id][i].val;
                	}
                }},
                unit_id: {title: '部门',list: false,options:actions.unitOption},
            	group_id: {title: '工作组',list:false,dependsOn:'unit_id',options:function(data){
                    if (data.source == 'list') {
                        return actions.groupOption;
                    }
                	return actions.groupOption+'&unit_id='+data.dependedValues.unit_id;
                }},
                circle: {title: '周期',id:'circle',options:function(){
                	var data={};
                	for(var i=0;i<params.circle.length;i++){
                		data[params.circle[i].key]=params.circle[i].val;
                	}
                	return data;
            	},display:function(obj){
                	for(var i=0;i<params[this.id].length;i++){
                		if(params[this.id][i].key==obj.record[this.id])
                			return params[this.id][i].val;
                	}
                }},
                leader: {title: '负责人',list: false,type:'chosen',options:actions.userOption},
                organization: {title: '机构',options:function(){
                	var data={};
                	for(var i=0;i<params.organization.length;i++){
                		data[params.organization[i].key]=params.organization[i].val;
                	}
                	return data;
            	},list: false},
                object1: {title: '对象1',id:'object1',options:function(data){
                	var data={};
                	for(var i=0;i<params.object1.length;i++){
                		data[params.object1[i].key]=params.object1[i].val;
                	}
                	return data;
            	}},
                object2: {title: '对象2',id:'object2',dependsOn:'object1',options:function(obj){
                	//for(var key in data.dependedValues)alert(key+"="+data.dependedValues[key]);
                	
                	var data={};
                	for(var i=0;i<params.object2.length;i++){
                		if(params.object2[i].pid==obj.dependedValues.object1)
                			data[params.object2[i].key]=params.object2[i].val;
                	}
                	//alert(JSON.stringify(data));
                	return data;
                }},
                object3: {title: '对象3',id:'object3',dependsOn:'object2',options:function(obj){
                	var data={};
                	for(var i=0;i<params.object3.length;i++){
                		if(params.object3[i].pid==obj.dependedValues.object2)
                			data[params.object3[i].key]=params.object3[i].val;
                	}
                	return data;
                }},
                item1: {title: '事项1',id:'item1',options:function(obj){
                	var data={};
                	for(var i=0;i<params.item1.length;i++){
                		data[params.item1[i].key]=params.item1[i].val;
                	}
                	return data;
            	}},
                item2: {title: '事项2',id:'item2',className:'span8',dependsOn:'item1',options:function(obj){
                	var data={};
                	for(var i=0;i<params.item2.length;i++){
                		if(params.item2[i].pid==obj.dependedValues.item1)
                			data[params.item2[i].key]=params.item2[i].val;
                	}
                	return data;
                }},
                work_group: {title: '工作组',list: false,create: false,edit: false},
                pl_dateRange: {title: '拟起止日期',list: false,create: true,edit: false,type:'daterange',display:function(obj){
                	return obj.record.pl_start_time+"-"+obj.record.pl_end_time;
                }},
                dateRange: {title: '起止日期',list: false,create: true,type:'daterange',display:function(obj){
                	return obj.record.start_time+"-"+obj.record.end_time;
                }},
                pl_start_time: {title: '拟起日期',list: false,create: false,edit: false},
                pl_end_time: {title: '拟止日期',list: false,create: false,edit: false},
                start_time: {title: '开始日期',list: false,create: false,edit: false},
                end_time: {title: '结束日期',list: false,create: false,edit: false},
                create_time: {title: '创建日期',list: false,create: false,edit: false},
                status: {title: '状态',list: false,create: false,edit: false},
                progress: {title: '进度',width:'20%',list:true,create: false,display:function(obj){
                	var record = obj.record;
                	var bars = [[75,'progress-success'],[50,'progress-info'],[25,'progress-warning'],[0,'progress-danger']];
                	//var bars = {'50':'progress-success','0':'progress-info'};
                	//var bars=[[50,'progress-success'],[0,'progress-info']];
                	for (var bar in bars) {
                		var progressTxt = '<div class="progress {0}"><div class="bar" style="width: {1}%;">{1}%</div></div>';
                		if(parseInt(bars[bar][0])<=parseInt(record.progress))
                			return progressTxt.format(bars[bar][1],record.progress,record.progress); 
                	}
                	return record.progress; 
                }
                }
            },                              
            recordAdded: function(event, data){
                $('#task-list-div').jtable('reload');
            },
            recordUpdated: function(event, data){
                $('#task-list-div').jtable('reload');
            }
        });
        $('#task-list-div').jtable('load',{},App.resize);
	};
	var handleQueryWeekByTaskLoad =  function (actions,data){
		var datas = data;
        $('#week-list-div').jtable({
            title: '<i class="icon-th-list"></i>周报列表',
            selecting: true,paging: true,pageSize: 10,columnResizable:false,
            dataIColl:'iColl_week',actions: actions,
            fields: {
            	id: {
                    key: true,
                    list: false,
                    create: false,
                    edit: false
                },
                dateRange: {title: '开始结束日期',list: false,className:'span10',type:'daterange',display:function(obj){
                	return obj.record.begin_time+"-"+obj.record.end_time;
                }},
                task_number: {title: '任务编号',list:false,create: true,className:'span2',edit: true,type: 'hidden'},
                weeks: {title: '周数',width:'10%',edit: false,create: false,},
                this_week_content: {title: '本周情况',type:'ckedit',inputClass:'ckeditor',width:'35%',className:'span6'},
                plan_week_content: {title: '下周计划',type:'ckedit',inputClass:'ckeditor',width:'35%',className:'span6'},
                create_time: {title: '创建日期',width:'20%',create: false,edit: false},
                begin_time: {title: '创建日期',width:'20%',list:false,create: false,edit: false},
                end_time: {title: '创建日期',width:'20%',list:false,create: false,edit: false}
            },
            formCreated:function(event, data){
            	$('#Edit-task_number').val(datas.task_number);
            	$('select[id^=Edit-task_number]').attr('multiple','multiple');
            	$('select[id^=Edit-task_number]').css('height','125px');
            },
            recordAdded: function(event, data){
                $('#week-list-div').jtable('load');
            },
            recordUpdated: function(event, data){
                $('#week-list-div').jtable('load');
            }
        });
        $('#week-list-div').jtable('load',{},App.resize);
	};
	var handleQueryUserByTaskLoad =  function (actions,data){
		var datas = data;
        $('#user-list-div').jtable({
            title: '<i class="icon-th-list"></i>用户列表',dialogClass:'span4',
            messages:{deleteConfirmation:'确定让此人离开？',deleteText:'确定'},
            selecting: true,paging: false,columnResizable:false,
            dataIColl:'iColl_taskMember',actions: actions,
            fields: {
            	id: {title:'ID',key: true,list: false,className:'span4'},
                task_number: {title: '任务编号',list:false,className:'span4',type: 'hidden'},
                member_number: {title: '成员编号',className:'span4'},
                member_name: {title: '成员名称',className:'span4'},
                begin_time: {title: '进入日期',create: false,className:'span4'},
                end_time: {title: '离开日期',create: false,className:'span4'}
            },      
            formCreated:function(event, data){
            	$('#Edit-task_number').val(datas.task_number);
            },
            recordAdded: function(event, data){
                $('#user-list-div').jtable('load');
            },
            recordDeleted: function(event, data){
                $('#user-list-div').jtable('load');
            }
        });
        $('#user-list-div').jtable('load',{},App.resize);
	};
	var handleQueryUnitLoad = function (actions,data){
        $('#unit-list-div').jtable({
            title: '<i class="icon-th-list"></i>部门列表',dialogClass:'span4',
            selecting: true,columnResizable:false,paging: true,pageSize: 10,
            dataIColl:'iColl_unit',actions: actions,
            fields: {
            	unit_id: {title:'部门编号',key: true,className:'span4'},
                unit_name: {title: '部门名称',className:'span4'}
            },                              
            recordAdded: function(event, data){
                $('#unit-list-div').jtable('load');
            },
            recordDeleted: function(event, data){
                $('#unit-list-div').jtable('load');
            },
            recordUpdated: function(event, data){
                $('#unit-list-div').jtable('load');
            }
        });
        $('#searchBtn').click(function (e) {
            e.preventDefault();
            $('#unit-list-div').jtable('load', {
                unit_id: $('#unit_id').val(),
                unit_name: $('#unit_name').val()
            },App.resize);
        });
        $('#unit-list-div').jtable('load',{},App.resize);
	};
	var handleQueryGroupLoad = function (actions,data){
        $('#group-list-div').jtable({
            title: '<i class="icon-th-list"></i>工作组列表',dialogClass:'span4',
            selecting: true,columnResizable:false,paging: true,pageSize: 10,
            dataIColl:'iColl_group',actions: actions,
            fields: {
            	group_id: {title:'工作组编号',key: true,className:'span4'},
                unit_name: {title: '部门名称',className:'span4',create:false,edit:false},
                unit_id: {title: '部门',list: false,options:actions.unitOption},
                group_name: {title: '工作组名称',className:'span4'}
            },                              
            recordAdded: function(event, data){
                $('#group-list-div').jtable('load');
            },
            recordDeleted: function(event, data){
                $('#group-list-div').jtable('load');
            },
            recordUpdated: function(event, data){
                $('#group-list-div').jtable('load');
            }
        });
    $('#searchBtn').click(function (e) {
        e.preventDefault();
        $('#group-list-div').jtable('load', {
            group_id: $('#group_id').val(),
            unit_id: $('#unit_name').val(),
            group_name: $('#group_name').val()
        },App.resize);
    });
    $('#group-list-div').jtable('load',{},App.resize);
	};
	
	var handleQueryUserLoad = function (actions,data){
        $('#user-list-div').jtable({
            title: '<i class="icon-th-list"></i>用户列表',dialogClass:'span8',dialogs:{height:'400px'},
            selecting: true,columnResizable:false,paging: true,pageSize: 10,
            dataIColl:'IColl_user',actions: actions,
            fields: {
                unit_name: {title: '部门名称',className:'span4',create:false,edit:false},
                unit_id: {title: '部门',list: false,options:actions.unitOption},
            	'group_user_IColl.group_id': {title: '工作组',type:'chosen',list:false,dependsOn:'unit_id',options:function(data){
                    if (data.source == 'list') {
                        return actions.groupOption;
                    }
                	return actions.groupOption+'&unit_id='+data.dependedValues.unit_id;
                }},
                group_name: {title: '工作组名称',className:'span4',create:false,edit:false},
                user_id: {title:'用户编号',key: true,create:true,className:'span4'},
                user_name: {title: '用户名',className:'span4',create:true,edit:true},
                password: {title: '密码',className:'span4',list:false,create:true,edit:true,type:'password'}
            },
            formCreated:function(event, data){
            	
            	$('select[id^=Edit-group_user_IColl]').attr('multiple','multiple');
            	$('select[id^=Edit-group_user_IColl]').css('height','125px');
            },
            recordAdded: function(event, data){
                $('#user-list-div').jtable('load');
            },
            recordDeleted: function(event, data){
                $('#user-list-div').jtable('load');
            },
            recordUpdated: function(event, data){
                $('#user-list-div').jtable('load');
            }
        });
    $('#searchBtn').click(function (e) {
        e.preventDefault();
        $('#user-list-div').jtable('load', {
            user_id: $('#user_id').val(),
            unit_id: $.trim($('#unit_name').val()),
            group_id: $.trim($('#group_name').val()),
            user_name: $('#user_name').val(),
        },App.resize);
    });
    $('#user-list-div').jtable('load',{},App.resize);
	};
	var Validate = {
		test:function(field, rules, i, options){
			if(field.val()=='11')
				return '输入信息有误';
		}
	};
	var handleSearchAreaOper = {
		select: function(obj){
			var selectedArea = $('#search-more-selected');
			if(obj.className=="btn btn-link active"){
				selectedArea.find('#selected_'+obj.id).remove();
				
				if(obj.id.indexOf("object1-")!=-1){
					var pid = obj.id.substring(8,obj.id.length);
					this.delParams("object2", params.object2,pid);
				}else if(obj.id.indexOf("object2-")!=-1){
					var pid = obj.id.substring(8,obj.id.length);
					this.delParams("object3", params.object3,pid);
				}else if(obj.id.indexOf("item1-")!=-1){
					var pid = obj.id.substring(6,obj.id.length);
					this.delParams("item2", params.item2,pid);
				}
			}else{
				var spanTxt = "<span class='label label-important' id='selected_{0}'>{1}&nbsp;&nbsp;<span class='small'>X</span></span>";
				selectedArea.append(spanTxt.format(obj.id,obj.innerHTML)).find(".small").click(function(){
						handleSearchAreaOper.remove(this);
					});
				
				if(obj.id.indexOf("object1-")!=-1){
					var pid = obj.id.substring(8,obj.id.length);
					this.addParams("object2", params.object2,pid);
				}else if(obj.id.indexOf("object2-")!=-1){
					var pid = obj.id.substring(8,obj.id.length);
					this.addParams("object3", params.object3,pid);
				}else if(obj.id.indexOf("item1-")!=-1){
					var pid = obj.id.substring(6,obj.id.length);
					this.addParams("item2", params.item2,pid);
				}
			}
			handleSearchAreaOper.query();
		},
		addParams:function(id,datas,parentId){
			for(var i=0;i<datas.length;i++){
				var data =datas[i];
				if(data.pid&&data.pid==parentId){
					var btnTxt ='<button class="btn btn-link" id="{0}-{1}" data-toggle="button" type="button">{2}</button>';
					$(btnTxt.format(id,data.key,data.val)).appendTo('#td-'+id).click(function(){
						handleSearchAreaOper.select(this);
					});
				}
			}
			if($('#tr-'+id).is(":hidden")&&
					$('#td-'+id).find("button[class^='btn btn-link']").size()>0) $('#tr-'+id).show();
		},
		delParams:function(id,datas,parentId){
			for(var i=0;i<datas.length;i++){
				var data =datas[i];
				if(data.pid&&data.pid==parentId){
					$('#'+id+"-"+data.key).remove();
				}
			}
			if(!$('#tr-'+id).is(":hidden")&&
					!$('#td-'+id).find("button[class^='btn btn-link']").size()>0) $('#tr-'+id).hide();
		},
		removeAll:function(){
			$('#search-more-selected').empty();
		},
		remove:function(obj){
			var pid= $(obj).parent().attr("id");
			var id = pid.substring(9,pid.length);
			$(obj).parent().remove();
			$('#'+id).removeClass('active');
			handleSearchAreaOper.query();
		},
		more:function(){
	        if ($.cookie('search-closed') == 1) {
				$("#search-more-div").hide();
				$("#search-more i").removeClass().addClass("icon-chevron-up");
	        }else{
	        	$("#search-more-div").show();
	        	$("#search-more i").removeClass().addClass("icon-chevron-down");
	        }

	        // handle search div show/hide
	        $("#search-more").click(function () {
	            if ($("#search-more-div").is(":hidden") === true) {
					$("#search-more-div").show();
					$("#search-more i").removeClass().addClass("icon-chevron-down");
	                $.cookie('search-closed', null);
	            } else {
					$("#search-more-div").hide();
					$("#search-more i").removeClass().addClass("icon-chevron-up");
	                $.cookie('search-closed', 1);
	            }
	            App.resize();
	            return false;
	        });
		},
		index:function(){
	        $("#index_plus").click(function () {
	            if ($("#search-more-selected").is(":empty") === true) {
					alert('未选择条件');
	            } else {
	            	handleSearchAreaOper.condition();
	            }
	            return false;
	        });
		},
		condition:function(){
			
			var selecteds = $('#search-more-selected').find("span[id^='selected_']");
			var content ={};
			selecteds.each(function(i){
				var objId = selecteds[i].id.substring(9,selecteds[i].id.indexOf('-'));
				var objVal = selecteds[i].id.substring(selecteds[i].id.indexOf('-')+1,selecteds[i].id.length);
				
				if(content[objId]){
					
					content[objId].push(objVal);
				}else{
					content[objId]=[];
					content[objId].push(objVal);
				}
			});
			return content;
		},
		block:function(){
            App.blockUI($("#search-more-div"));
            
            setTimeout(function () {
                App.unblockUI($("#search-more-div"));
            }, 1000);
		},
		query:function(){
			var data = handleSearchAreaOper.condition();
			//alert(JSON.stringify(data));
			data.task=$('#taskNameIpt').val();
			
			$('#task-list-div').jtable('load',{'condition':JSON.stringify(data)},App.resize);
		},
		initCondition:function(){
			if(params){
				for (var param in params) {
					if(param=='object2'||param=='object3'||param=='item2') continue;
					
					for(var i=0;i<params[param].length;i++){
						$('#td-'+param).append('<button class="btn btn-link" id="{0}-{1}" data-toggle="button" type="button">{2}</button>'
								.format(param,params[param][i].key,params[param][i].val));
					}
					
				}
			}
            setTimeout(function () {
                App.unblockUI($("#search-more-div"));
            }, 1000);
		},
		init:function(actions){
			App.blockUI($("#search-more-div"));
			handleSearchAreaOper.more();
			handleSearchAreaOper.index();
			handleSearchAreaOper.initCondition();
			var buttons = $('#search-more-div').find("button[class^='btn btn-link']");
			buttons.each(function(i){
				buttons[i].onclick = function(){
					handleSearchAreaOper.select(buttons[i]);
				};
			});
			
			$('#searchBtn').click(function(){
				handleSearchAreaOper.query();
			});
			$('#treelistBtn').click(function(){
				window.location.href=actions.treeAction;
				window.event.returnValue = false;
			});
		}
		
	};
    return {

        //main function to initiate template pages
        init: function () {
            handleMainMenu(); // handles main menu
            handleSidebarToggler(); // handles sidebar hide/show
            handleSizeChange();// handles mainFrame auto heigh
            handleIframeLoad();// handles mainFrame auto heigh
        },

        // login page setup
        initLogin: function () {
            handleLoginForm(); // handles login form
            //handleUniform(); // // handles uniform elements
            handleFixInputPlaceholderForIE(); // fixes/enables html5 placeholder attribute for IE9, IE8
        },
        // common page setup
        initPage: function () {
			//handleSearchAreaOper.init();
			//handleQueryTaskLoad();
        },
        // query task page
        initQueryTaskPage: function (actions,type) {
        	//handleQueryTaskLoad(actions,type);
			handleSearchAreaOper.init(actions);
        },
        // query task detail page
        initQueryTaskDetailPage:function(actions,data){
        	handleQueryWeekByTaskLoad(actions.weeks,data);
        	handleQueryUserByTaskLoad(actions.users,data);
        },
        // query week by task detail page
        initQueryWeekByTaskPage:function(actions,data){
        	handlePortletToggle();
        	handleQueryWeekByTaskLoad(actions,data);
        },
        // org unit group and so on..
        initCreatTaskPage:function(actions,type){
        	handleFormWizards();
        	handleSizeChange;
        },
        // org unit group and so on..
        initQueryOrgPage:function(actions,type){
        	if(type=='unit')
        		handleQueryUnitLoad(actions);

        	if(type=='group')
        		handleQueryGroupLoad(actions);
        	
        	if(type=='user')
        		handleQueryUserLoad(actions);
        },
		// iframe page init
		resize: function(){
			handleSizeChange();// handles mainFrame auto heigh
			return false;
		},
        // wrapper function to scroll to an element
        scrollTo: function (el, offeset) {
            pos = el ? el.offset().top : 0;
            jQuery('html,body').animate({
                scrollTop: pos + (offeset ? offeset : 0)
            }, 'slow');
        },

        // wrapper function to  block element(indicate loading)
        blockUI: function (el, loaderOnTop) {
            lastBlockedUI = el;
            jQuery(el).block({
                message: '<img src="/tms/assets/img/loading.gif" align="absmiddle">',
                css: {
                    border: 'none',
                    padding: '2px',
                    backgroundColor: 'none'
                },
                overlayCSS: {
                    backgroundColor: '#000',
                    opacity: 0.05,
                    cursor: 'wait'
                }
            });
        },

        // wrapper function to  un-block element(finish loading)
        unblockUI: function (el) {
            jQuery(el).unblock({
                onUnblock: function () {
                    jQuery(el).removeAttr("style");
                }
            });
        },

        // public method to initialize uniform inputs
        initFancybox: function () {
            handleFancyBox();
        },

        // initializes uniform elements
        initUniform: function (el) {
            initUniform(el);
        },

        // initializes choosen dropdowns
        initChosenSelect: function (el) {
            initChosenSelect(el);
        },

        getActualVal: function (el) {
            var el = jQuery(el);
            if (el.val() === el.attr("placeholder")) {
                return "";
            }

            return el.val();
        },

        // set map page
        setPage: function (name) {
            currentPage = name;
        },

        // check current page
        isPage: function (name) {
            return currentPage == name ? true : false;
        },
		
        // check for device touch support
        isTouchDevice: function () {
            try {
                document.createEvent("TouchEvent");
                return true;
            } catch (e) {
                return false;
            }
        },
        Validate:Validate,
        ajaxRequest:ajaxRequest,
        setSelectByAjax:setSelectByAjax

    };

}();