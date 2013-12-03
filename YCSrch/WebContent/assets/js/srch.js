Date.prototype.format =function(format)
{
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
	};
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,RegExp.$1.length==1? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
	return format;
};
String.prototype.format = function () {
	  var args = arguments;
	  return this.replace(/\{(\d+)\}/g, function (m, n) { return args[n]; });
};
var Srch = function () {
	// search area date range picker .
	var handleEventWizard = function(){
	    var options = {};
	    var wizard = $("#wizard-event").wizard(options);
		var cnMap={'keyWord':'关键字','bgnTm':'开始时间','endTm':'结束时间','confNm':'系统','host':'主机','path':'路径'},
			dtTemplate="<dt>{0}</dt><dd>{1}</dd>";
	    
	    wizard.cards["card2"].on("selected", function(card) {
	    	$('#eventConfirm .dl-horizontal').empty();
	    	$('#eventConfirm .dl-horizontal').append(dtTemplate.format('标题',$('#eventTitle').val()));
	    	$('#eventConfirm .dl-horizontal').append(dtTemplate.format('说明',$('#eventDesc').val()));
	    	var data = handleSearchAreaOper.condition();
	    	for(var d in data){
	    		if(data[d].length != 0){
	    			$('#eventConfirm .dl-horizontal').append(dtTemplate.format(cnMap[d],data[d]));
	    		}
	    	}
	    });
		wizard.on("reset", function(wizard) {
			wizard.el.find("#eventTitle").val("");
			wizard.el.find("#eventDesc").val("");
		});
		wizard.on("submit", function(wizard) {
			
			setTimeout(function() {
				wizard.trigger("success");
				wizard.hideButtons();
				wizard._submitting = false;
				wizard.showSubmitCard("success");
				wizard.updateProgressBar(0);
			}, 500);
		});
		wizard.el.find(".wizard-success .im-done").click(function() {
			wizard.reset().close();
		});
		
		$("#addEvents").click(function() {
			wizard.show();
		});
	};
	var handleTaskWizard = function(){
	    var options = {};
	    var wizard = $("#wizard-task").wizard(options);
		var cnMap={'keyWord':'关键字','bgnTm':'开始时间','endTm':'结束时间','confNm':'系统','host':'主机','path':'路径'},
			dtTemplate="<dt>{0}</dt><dd>{1}</dd>";
		
	    /*wizard.cards["card5"].on("selected", function(card) {
	    	$('#taskConfirm .dl-horizontal').empty();
	    	$('#taskConfirm .dl-horizontal').append(dtTemplate.format('标题',$('#eventTitle').val()));
	    	$('#taskConfirm .dl-horizontal').append(dtTemplate.format('说明',$('#eventDesc').val()));
	    	var data = handleSearchAreaOper.condition();
	    	for(var d in data){
	    		if(data[d].length != 0){
	    			$('#taskConfirm .dl-horizontal').append(dtTemplate.format(cnMap[d],data[d]));
	    		}
	    	}
	    });*/
	    /*wizard.cards["card5"].on("selected", function(card) {
	    	$('#taskConfirm .dl-horizontal').empty();
	    	$('#taskConfirm .dl-horizontal').append(dtTemplate.format('标题',$('#eventTitle').val()));
	    	$('#taskConfirm .dl-horizontal').append(dtTemplate.format('说明',$('#eventDesc').val()));
	    	var data = handleSearchAreaOper.condition();
	    	for(var d in data){
	    		if(data[d].length != 0){
	    			$('#taskConfirm .dl-horizontal').append(dtTemplate.format(cnMap[d],data[d]));
	    		}
	    	}
	    	$('#taskConfirm .dl-horizontal').append(dtTemplate.format('Corn表达式',$('#taskCorns').val()));
	    	$('#taskConfirm .dl-horizontal').append(dtTemplate.format('告警阈值',$('input[@name=options][@checked]').val()+"--"+$('#taskSetVal').val()));
	    	//$('#taskConfirm .dl-horizontal').append(dtTemplate.format('告警方式',$('input[@name=options][@checked]').val()+"--"+$('#taskSetVal').val()));
	    });*/
	    $("#taskAltSms").css("display", "none");
	    $("#taskAltEmails").css("display", "none");
	    
	    $('#taskCornSpan').cron({
	        initial: "* * * * *",
	        onChange: function() {
	            $('#taskCorns').val($(this).cron("value"));
	        }
	    });
        $("#emailsChk").click(function () {
            if ($("#emailsChk").is(":checked"))
                $("#taskAltEmails").css("display", "block");
            else
                $("#taskAltEmails").css("display", "none");
        });
        $("#smsChk").click(function () {
            if ($("#smsChk").is(":checked"))
                $("#taskAltSms").css("display", "block");
            else 
                $("#taskAltSms").css("display", "none");
        });
		wizard.on("reset", function(wizard) {
			wizard.el.find("#taskTitle").val("");
			wizard.el.find("#taskDesc").val("");
		});
		wizard.on("submit", function(wizard) {
			
			setTimeout(function() {
				wizard.trigger("success");
				wizard.hideButtons();
				wizard._submitting = false;
				wizard.showSubmitCard("success");
				wizard.updateProgressBar(0);
			}, 500);
		});
		wizard.el.find(".wizard-success .im-done").click(function() {
			wizard.reset().close();
		});
		
		$("#addTasks").click(function() {
			wizard.show();
		});
	};
	var wizardValidate={
			required:function(el){
			    var val = el.val(),retValue = {};
			    if ($.trim(val).length==0) {
			        retValue.status = false;
			        retValue.msg = "此处不可为空。";
			    }
			    else {
			        retValue.status = true;
			    }
			    return retValue;
			}	
	};
	
	// search area date range picker .
	var handleDateRangePickers = function(){
        $('#srch_daterange').daterangepicker(
                {
                   ranges: {
                      '今天': ['today', 'today'],
                      '昨天': ['yesterday', 'yesterday'],
                      '最近 7 天': [Date.today().add({ days: -6 }), 'today'],
                      '最近 30 天': [Date.today().add({ days: -29 }), 'today']
                   },
                   opens: 'left',format: 'yyyy/MM/dd',separator: ' to ',startDate: Date.today().add({ days: -29 }),
                   endDate: Date.today(),
                   locale: {
                       applyLabel: '确定',fromLabel: '从',toLabel: '至',
                       customRangeLabel: '自定义',firstDay: 1
                   },
                   showWeekNumbers: true,
                   buttonClasses: ['btn-danger btn-xs']
                }, 
                function(start, end) {
                   $('#srch_daterange span').html(start.toString('yyyy/MM/dd') + ' - ' + end.toString('yyyy/MM/dd'));
                   $('#log-list-div').jtable('load',{'condition':JSON.stringify(Srch.search.condition())},genCharts);
                }
             );
             $('#srch_daterange span').html(Date.today().add({ days: -29 }).toString('yyyy/MM/dd') + ' - ' + Date.today().toString('yyyy/MM/dd'));
	};
	/**
	 * search chart area function
	 * handleChartGraphs(data) for ajax function
	 * handleChartGraphs(data,flg) for toggle line or bar.
	 */
	var handleChartGraphs = function(data,flg){
		var d;
		if(flg=='line'||flg=='bar'){
			d=Srch.search.charData;
		}
		else{
			d=data.DateFacet;
			Srch.search.charData=d;
		}
		
		for (var i = 0; i < d.length; ++i) {
			d[i][0] += 60 * 60 * 1000;
		}

		var options = {
				colors: ["#668EB0", "#cb4b4b", "#4da74d", "#9440ed","#edc240"],
				series: {
					bars: {
						show: true,align: "center",
						barWidth: 60*60*1000,
						fill: true,fillColor: "#A9C0D3"
					}
				},
				xaxis: {mode: "time"},
				selection: {mode: "x"},
				grid: {
	                hoverable: true,clickable: true,
	                tickColor: "#eee",color:"#cccccc",
					borderWidth:1
				}
			};
		$('#btn-chart-bar').addClass('active');
		$('#btn-chart-line').removeClass('active');
    	if(flg=='line'){
    		options = {
    				colors: ["#668EB0", "#cb4b4b", "#4da74d", "#9440ed","#edc240"],
    				series: {
    	                lines: {
    	                    show: true,lineWidth: 2,fill: true,
    	                    fillColor: {colors: [{opacity: 0.3}, {opacity: 0.05}]}
    	                },
    	                points: {show: true,radius: 2,lineWidth: 2},
    	                shadowSize: 2
    				},
    				xaxis: {mode: "time"},
    				selection: {mode: "x"},
    				grid: {
    	                hoverable: true,clickable: true,
    	                tickColor: "#eee",color:"#cccccc",
    					borderWidth:1
    				}
    			};
    		$('#btn-chart-bar').removeClass('active');
    	}
        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + parseInt(contents) + '</div>').css({
                position: 'absolute',
                display: 'none',
                top: y + 5,
                left: x + 15,
                border: '1px solid #333',
                padding: '4px',
                color: '#fff',
                'border-radius': '3px',
                'background-color': '#333',
                opacity: 0.80
            }).appendTo("body").fadeIn(200);
        }

        var previousPoint = null;
        $("#placeholder").bind("plothover", function (event, pos, item) {
            $("#x").text(pos.x.toFixed(2));
            $("#y").text(pos.y.toFixed(2));

            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                        y = item.datapoint[1].toFixed(2);

                    showTooltip(item.pageX, item.pageY, y);
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });
        
		var plot = $.plot("#placeholder", [d], options);
		var overviewOpts={
				colors: ["#668EB0", "#cb4b4b", "#4da74d", "#9440ed","#edc240"],
				series: {
					bars: {
						show: true,
						barWidth: 1,
						align: "center"
					}
				},
				xaxis: {
					ticks: [],
					mode: "time"
				},
				yaxis: {
					ticks: [],
					min: 0,
					autoscaleMargin: 0.1
				},
				selection: {
					mode: "x"
				},
				grid: {
					color:"#cccccc",
					borderWidth:1
				}
			};
		if(flg=='line'){
			overviewOpts = {
    				colors: ["#668EB0", "#cb4b4b", "#4da74d", "#9440ed","#edc240"],
    				series: {
    	                lines: {
    	                    show: true,lineWidth: 2,fill: true,
    	                    fillColor: {colors: [{opacity: 0.3}, {opacity: 0.05}]}
    	                },
    	                points: {show: true,radius: 1,lineWidth: 1},
    	                shadowSize: 1
    				},
    				xaxis: {mode: "time"},
    				selection: {mode: "x"},
    				grid: {
    	                hoverable: true,clickable: true,
    	                tickColor: "#eee",color:"#cccccc",
    					borderWidth:1
    				}
    			};
    	}
		
		var overview = $.plot("#overview", [d], overviewOpts);
		$("#placeholder").bind("plotselected", function (event, ranges) {
			plot = $.plot("#placeholder", [d], $.extend(true, {}, options, {
				xaxis: {
					min: ranges.xaxis.from,
					max: ranges.xaxis.to
				}
			}));
			overview.setSelection(ranges, true);
		});

		$("#overview").bind("plotselected", function (event, ranges) {
			plot.setSelection(ranges);
		});
		setTimeout(function () {
			Srch.unblockUI($('#collapseChart'));
		},500);
		
		if(flg=='success'){
			//more bgn
			$('#collapseMore div.panel-body').empty();
			var divTemplate ='<div id="{0}-btn" class="col-md-12 btn-group btn-group-xs"></div>';
			var btnTemplate ='<button class="btn btn-default" id="{0}-{1}" style="font-weight:normal" data-toggle="button" type="button">{2} (<b>{3}</b>)</button>';
			var factMap =data.factMap;
			for(var factkey in factMap){
				var divObj = $(divTemplate.format(factkey));
				for(var i in factMap[factkey]){
					var factObj = factMap[factkey][i];
					for (var key in factObj){
						$(btnTemplate.format(factkey,key,key,factObj[key])).appendTo(divObj).click(function(){
							handleSearchAreaOper.select(this);
						});
					}
				}
				divObj.appendTo($('#collapseMore div.panel-body'));
			}
			//more end
		}
		
	};
	var handleSearchAreaOper ={
		confMenu:function(obj){
			$('#srch-confcore-menu a').click(function(e){
				$('#srch-confcore span').text($(this).text());
			});
		},
		select: function(obj){
			//alert(obj.id);
		},
		condition:function(){
			var dateRange = $('#srch_daterange span').text();
			var filterStr = $('#srch_daterange span').text();
			return {
				keyWord:$('#srch_keyword').val(),
				bgnTm:$.trim((dateRange.split('-')[0])),
				endTm:$.trim((dateRange.split('-')[1])),
				host:'',
				path:'',
				confNm:$.trim(($('#srch-confcore span').text()))
			};
		},
		prevnext:{},
		charData:[],
		page:function(flg){
			var data = Srch.search.prevnext;
			data.flg=flg;
			data.lineNum=$('#lineNum-'+flg).val();
			Srch.blockUI($('#myModal div.modal-body'));
			$('#myModal div.modal-body').load('/YCSrch/search/detail',
					{'condition':JSON.stringify(data)},function(){
                    	$('#myModal div.modal-body').height($(window).height()-100);
						setTimeout(function () {
							Srch.unblockUI($('#myModal div.modal-body'));
							$('#myModal div.modal-body').height($(window).height()-100);
						},500);
					});
		},
		detail:{prevNum:'',nextNum:'',
			getFilter:function(bgn,end){
				
			},
			init:function(){
				$('#detail-prev').click(function(e){
					Srch.blockUI($('#myModal div.modal-body'));
					$('#myModal div.modal-body').empty();
					var data = Srch.search.prevnext;
					data.lineNum="${bgnLineNum}";
					data.flg='prev';
					alert(JSON.stringify(data));
					$('#myModal div.modal-body').load('/YCSrch/search/detail',
							{'condition':JSON.stringify(data)},function(){
	                        	$('#myModal div.modal-body').height($(window).height()-100);
								setTimeout(function () {
									Srch.unblockUI($('#myModal div.modal-body'));
								},500);
							});
				});
			}
		},
		tour:function(){
			var $tourBtn ,tour;
			$tourBtn = $(".crumbs>.crumb-buttons>li.tour");
			tour= new Tour({
				onStart: function() {
			        return $tourBtn.addClass("disabled", true);
			      },
			      onEnd: function() {
			        return $tourBtn.removeClass("disabled", true);
			      }
			});
			tour.addSteps([
						{title: "搜索工具栏",element: "#srchDiv", placement: "bottom",backdrop:true,content: "这里是搜索工具栏，教你怎么使用搜索。"},
						{title: "主机、路径筛选器",element: "#srch_more",backdrop:true,content: "这里是搜索工具栏-筛选器，教你怎么使用搜索-筛选器。"},
						{title: "时间图表",element: "#srch_chart",backdrop:true,content: "这里是搜索工具栏-时间图表，教你怎么使用搜索-时间图表。"},
						{title: "业务系统列表",element: "#srch-confcore",backdrop:true,content: "这里是业务系统列表,控制用户权限。"},
						{title: "搜索输入框",element: "#srch_keyword",placement: "bottom",backdrop:true,content: "这里是搜索输入框,输入关键字进行搜索 ,支持and、or、not、()等语法。"},
						{title: "时间区间选择器",element: "#srch_daterange",placement: "left",backdrop:true,content: "这里是时间区间选择器,可以选择相对时间以及绝对时间段。"},
						{title: "搜索按键",element: "#srch_btn",placement: "left",backdrop:true,content: "这里是搜索按键,点击这里触发搜索功能。"},
						{title: "功能键",element: ".crumbs>.crumb-buttons>li.dropdown",placement: "bottom",backdrop:true,content: "这里是功能键，此处可用来将当前的搜索保存为事件、任务。"},
						{title: "时间轴控件",element: "#collapseChart",placement: "bottom",backdrop:true,content: "这里是时间轴控件,时间轴控件。"},
						{title: "图表",element: "#placeholder",backdrop:true,content: "这里是图表,可用鼠标进行框选,来选择查看范围。"},
						{title: "图表缩略图",element: "#overview",placement: "bottom",backdrop:true,content: "这里是图表缩略图,可用鼠标进行框选。"},
						{title: "图形模式",element: "#char-mode",placement: "bottom",backdrop:true,content: "这里是图形模式,可点击切换图形模式。"},
						{title: "搜索结果",element: "#log-list-div",placement: "top",backdrop:true,content: "这里是搜索结果,搜索结果。"},
						{title: "记录操作",element: "tr.jtable-data-row td:first",placement: "top",backdrop:true,content: "这里操作区,点击可查看记录相关主机、路径、以及文件详情"},
						{title: "时间",element: "tr.jtable-data-row td:nth-child(2):first",placement: "top",backdrop:true,content: "这里是日志记录时间,若日志内时间格式无法解析默认用系统时间"},
						{title: "内容",element: "tr.jtable-data-row td:nth-child(3):first",placement: "top",backdrop:true,content: "这里是日志记录内容,默认显示300个字符，详情通过记录操作的查看详情查看。"},
						{title: "分页组件",element: ".jtable-bottom-panel>.jtable-left-area",placement: "top",backdrop:true,content: "这里分页组件,用于页面跳转并可以设置每页记录数等信息。"},
						{title: "功能区-帮助",element: ".crumbs>.crumb-buttons>li.tour",placement: "left",backdrop:true,content: "这里功能区-帮助,用于提供页面的操作说明及帮助信息。"}
					]);
			tour.start();
			$tourBtn.click(function(e){
				e.preventDefault();
			      if ($(this).hasClass("disabled")) {
			        return false;
			      }
			      tour.restart();
			      return true;
			});
			
			return true;
		},
		collapseAll:function(){
			$(".crumbs>.crumb-buttons>li.collapseAll").click(function(e){
				$('#moreCondition').collapse('toggle');
			});
		},
		init:function(){
			$('#detail-prev').click(function(e){
				Srch.blockUI($('#myModal div.modal-body'));
				$('#myModal div.modal-body').empty();
				var data = Srch.search.prevnext;
				data.lineNum="${bgnLineNum}";
				data.flg='prev';
				alert(JSON.stringify(data));
				$('#myModal div.modal-body').load('/YCSrch/search/detail',
						{'condition':JSON.stringify(data)},function(){
                        	$('#myModal div.modal-body').height($(window).height()-100);
							setTimeout(function () {
								Srch.unblockUI($('#myModal div.modal-body'));
							},500);
						});
			});
			$('#detail-next').click(function(e){
				Srch.blockUI($('#myModal div.modal-body'));
				$('#myModal div.modal-body').empty();
				var data = Srch.search.prevnext;

				$('#myModal div.modal-body').load('/YCSrch/search/detail',
						{'condition':JSON.stringify(data)},function(){
                        	$('#myModal div.modal-body').height($(window).height()-100);
							setTimeout(function () {
								Srch.unblockUI($('#myModal div.modal-body'));
							},500);
						});
			});
		}
	};
	/**
	 * tasks/events/reports menu ctrl
	 */
	var handleMainCollapseMenu = function(){
		var mainMenuCps = $(".navbar-blue>.navbar-collapse>.navbar-right").find("a[data-toggle='collapse']");
		mainMenuCps.click(function(event){
			$(".navbar-blue>.navbar-collapse>.navbar-right>li").not($(this).parent()).removeClass("active");
			$(this).parent().toggleClass("active");
			event.preventDefault();
		});
	};
	var modalTemplate = '<div class="modal fade" id="modal_{0}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
	    '<div class="col-md-8 col-md-offset-2">'+
	      '<div class="modal-content"><div class="modal-body"></div></div>'+
	    '</div>'+
	  '</div>';	
	return {
		initMain:function(){
			handleDateRangePickers();
			handleSearchAreaOper.confMenu();
			handleMainCollapseMenu();
			handleSearchAreaOper.tour();
			handleSearchAreaOper.collapseAll();
			handleEventWizard();
			handleTaskWizard();
		},
		genChart:handleChartGraphs,
		wValidate:wizardValidate,
		search:handleSearchAreaOper,
		modalTemplate:modalTemplate,
        blockUI: function (el, loaderOnTop) {
            lastBlockedUI = el;
            jQuery(el).block({
                message: '<img src="/YCSrch/assets/img/loading.gif" align="absmiddle">',
                css: {
                    border: 'none',
                    padding: '2px',
                    backgroundColor: 'none'
                },
                overlayCSS: {
                    backgroundColor: '#000',
                    opacity: 0.2,
                    cursor: 'wait'
                }
            });
        },
        unblockUI: function (el) {
            jQuery(el).unblock({
                onUnblock: function () {
                    jQuery(el).removeAttr("style");
                }
            });
        }
	};
}();