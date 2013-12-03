<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="SearchMenu.title"/></title>
    <meta name="menu" content="SearchMenu"/>
</head>
<body>
    <!-- BEGIN CONTAINER -->
    <div class="page-container">
		<div class="row">
			<div class="crumbs">
				<ul class="breadcrumb">
					<li>
						<i class="icon-home"></i>
						<a href="#">YCSrch</a> 
					</li>
					<li>
						<a href="#"><fmt:message key="SearchMenu.title"/></a>
					</li>
				</ul>
				<ul class="crumb-buttons">
					<li class="collapseAll"><a title="Close Collapse" href="#"><i class="icon-resize-vertical"></i></a></li>
					<li class="dropdown">
						<a data-toggle="dropdown" title="" href="#"><i class="icon-plus-sign"></i><span>创建 </span><i class="icon-angle-down left-padding"></i></a> 
						<ul class="dropdown-menu pull-right"> 
							<li id="addReports"><a title="" href="#"><i class="icon-plus-sign"></i> 报表</a></li> 
							<li id="addTaskSrch"><a title="" href="#"><i class="icon-plus-sign"></i> 搜索任务</a></li> 
						</ul>
					</li>
					<li class="dropdown">
						<a data-toggle="dropdown" title="" href="#"><i class="icon-save"></i><span>保存 </span><i class="icon-angle-down left-padding"></i></a> 
						<ul class="dropdown-menu pull-right"> 
							<li id="addEvents"><a title="" href="#"><i class="icon-save"></i> 自定义事件</a></li> 
							<li id="addTasks"><a title="" href="#"><i class="icon-save"></i> 自定义任务</a></li> 
						</ul>
					</li>
					<li class="tour"><a title="Help Info" href="#"><i class=" icon-question-sign"></i></a></li>
				</ul>
			</div>
			<div id="srchDiv" class="col-md-12">
			    <div class="input-group" style="border: 2px solid #4D7496;border-radius:6px;">
			      <span class="input-group-btn">
			        <a id="srch_more" class="btn btn-default" data-toggle="collapse" data-parent="#moreCondition" href="#collapseMore">
			        	<i class="icon-cog"></i>
			        </a>
			        <a id="srch_chart" class="btn btn-default" data-toggle="collapse" data-parent="#moreCondition" href="#collapseChart">
			        	<i class="icon-bar-chart"></i>
			        </a>
				  <button id="srch-confcore" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
				    <span>${confCores[0]}</span> <b class="caret"></b>
				  </button>
				  <ul id="srch-confcore-menu" class="dropdown-menu" role="menu">
				  <c:forEach items="${confCores}" var="item">
				  	<li><a href="#">${item}</a></li>
				  </c:forEach>
				  </ul>
			      </span>
					<input type="text" id="srch_keyword" class="form-control"/>
					<span class="input-group-btn">
					  <button id="srch_daterange" type="button" class="btn btn-default" data-toggle="dropdown"><span>ALL TIME</span> <b class="caret"></b></button>
					  <button id="srch_btn" class="btn btn-default" type="button"><i class="icon-search"></i></button>
					</span>
			    </div><!-- /input-group -->
		    </div>
		</div><!-- /.row -->
		<div class="row">
			<div class="col-md-12">
				<div class="panel-group" id="moreCondition">
				  <div class="panel">
				    <div id="collapseMore" class="panel-collapse collapse">
				      <div class="panel-body">

				      </div>
				    </div>
				    <div id="collapseChart" class="panel-collapse collapse in">
						<div class="demo-container">
								<div id="placeholder" class="demo-placeholder col-md-9"></div>
								<div id="overview" class="demo-placeholder col-md-3" style="height: 60%"></div>
								<div id="area-btn" class="demo-placeholder col-md-3" style="height: 40%">
									<div class="col-md-12 btn-group btn-group-m text-center" id="char-mode">
										<button type="button" id="btn-chart-bar" onclick="Srch.genChart({},'bar')"  data-toggle="button" class="btn btn-default"><fmt:message key="Search.chartmode.bars"/></button>
										<button type="button" id="btn-chart-line" onclick="Srch.genChart({},'line')"  data-toggle="button" class="btn btn-default"><fmt:message key="Search.chartmode.lines"/></button>
									</div>
								</div>
						</div>
				    </div>
				  </div>
				 </div>
			 </div>
		</div>
		<div class="row">
			<div class="col-md-12  ">

			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div id="log-list-div" class="jtable"></div>
			</div>
		</div>
	  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="col-md-8 col-md-offset-2">
	      <div class="modal-content">
	        <div class="modal-header ">
	          <button type="button" id="detail-prev" onclick="Srch.search.page('prev')" class="btn btn-xs btn-blue"><i class="icon-arrow-left"></i> <fmt:message key="Search.page.prev"/></button>
	          <button type="button" id="detail-next" onclick="Srch.search.page('next')" class="btn btn-xs btn-blue"><fmt:message key="Search.page.next"/> <i class="icon-arrow-right"></i></button>
	          <button type="button" class="close" onclick="javascript:$('#myModal').modal('hide');">&times;</button>
	        </div>
	        </div-->
	        <div class="modal-body" style="overflow:auto;height:800px">
	          waiting...
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
		<div class="wizard" id="wizard-event">
		    <h1>自定义事件</h1>
		    <div class="wizard-card" data-cardname="card1">
				<h3>录入事件</h3>
				<div class="form-group">
					<label for="eventTitle">事件标题</label>
					<input type="text" class="form-control input-sm" id="eventTitle" placeholder="录入事件标题" data-validate="required">
				</div>
				<div class="form-group">
					<label for="eventDesc">事件说明</label>
					<textarea id="eventDesc" class="form-control input-sm" rows="2" placeholder="录入事件说明"></textarea>
				</div>
		    </div>
		    <div class="wizard-card" data-cardname="card2">
		        <h3>确认事件</h3>
				<div id="eventConfirm">
					<dl class="dl-horizontal">
					</dl>
				</div>
		    </div>
			<div class="wizard-success">
				<h3>保存结果</h3>
				<div class="alert alert-success">
					<span class="create-server-name"></span>
					事件创建 <strong>成功.</strong>
				</div>
				<div class="form-group text-right">
					<a class="btn btn-success im-done">关闭</a>
				</div>
			</div>
		</div><!-- EventWizard -->
		<div class="wizard" id="wizard-task">
		    <h1>自定义任务</h1>
		    <div class="wizard-card" data-cardname="card1">
				<h3>任务说明</h3>
				<div class="form-group">
					<label for="taskTitle">任务标题</label>
					<input type="text" class="form-control input-sm" id="taskTitle" placeholder="录入任务标题" data-validate="required">
				</div>
				<div class="form-group">
					<label for="taskDesc">任务说明</label>
					<textarea id="taskDesc" class="form-control input-sm" rows="2" placeholder="录入任务说明"></textarea>
				</div>
		    </div>
		    <div class="wizard-card" data-cardname="card2">
		        <h3>任务设定</h3>
				<fieldset disabled>
					<div class="form-group">
						<label for="taskCorns">执行频率</label>
						<input type="text" class="form-control input-sm" id="taskCorns" data-validate="required">
					</div>
				</fieldset>
				<div class="form-group">
					<span id="taskCornSpan"></span>
				</div>

		    </div>
		    <div class="wizard-card" data-cardname="card3">
		        <h3>任务警告阈值</h3>
				<div class="form-group">
					<label>设置阈值</label><br>
					<div class="btn-group" data-toggle="buttons">
					  <label class="btn btn-xs btn-default">
					    <input type="radio" name="options" id="option1" val="0"> 等于
					  </label>
					  <label class="btn btn-xs btn-default">
					    <input type="radio" name="options" id="option2" val="1"> 大于
					  </label>
					  <label class="btn btn-xs btn-default">
					    <input type="radio" name="options" id="option3"  val="2"> 小于
					  </label>
					  <label class="btn btn-xs btn-default">
					    <input type="radio" name="options" id="option3"  val="3"> 无&nbsp;&nbsp;
					  </label>
					</div>
					<input type="text" class="form-control input-sm" id="taskSetVal" >
				</div>



		    </div>
		    <div class="wizard-card" data-cardname="card4">
		        <h3>警告方式</h3>
					<div class="form-group">
						<input type="checkbox" id="emailsChk">邮件,邮件列表以“,”分隔
						<textarea id="taskAltEmails" class="form-control input-sm" rows="2" placeholder="邮件列表，号分隔"></textarea>
					</div>
					<div class="form-group">
						<input type="checkbox" id="smsChk">短信,号码列表以“,”分隔
						<textarea id="taskAltSms" class="form-control input-sm" rows="2" placeholder="号码列表，号分隔"></textarea>
					</div>
					<div class="form-group">
						<input type="checkbox" id="alertChk">警告列表
					</div>
		    </div>
		    <!--div class="wizard-card" data-cardname="card5">
		        <h3>任务确认</h3>
				<div id="taskConfirm">
					<dl class="dl-horizontal">
					</dl>
				</div>
		    </div-->
			<div class="wizard-success">
				<h3>保存结果</h3>
				<div class="alert alert-success">
					<span class="create-server-name"></span>
					任务创建 <strong>成功.</strong>
				</div>
				<div class="form-group text-right">
					<a class="btn btn-success im-done">关闭</a>
				</div>
			</div>
		</div><!-- TaskWizard -->
    </div>
	<!-- END CONTAINER -->
<c:set var="scripts" scope="request">
	<script src="<c:url value='/assets/jtable/jquery.jtable.js'/>"></script>
	<script src="<c:url value='/assets/bootstrap-daterangepicker/date.js'/>"></script>
	<script src="<c:url value='/assets/wizard/bootstrap-wizard.js'/>"></script>
	<script src="<c:url value='/assets/bootstrap/js/bootstrap-dialog.js'/>"></script>
	<script src="<c:url value='/assets/bootstrap-daterangepicker/daterangepicker.js'/>"></script>
	<script src="<c:url value='/assets/js/jquery.cron.js'/>"></script>
	<script src="<c:url value='/assets/js/jquery.slimscroll.min.js'/>"></script>
	<script src="<c:url value='/assets/js/jquery.cookie.js'/>"></script>
	<!--[if lt IE 9]>
	<script src="<c:url value='/assets/js/excanvas.min.js'/>"></script>
	<script src="<c:url value='/assets/js/respond.min.js'/>"></script>
	<![endif]-->
	<script src="<c:url value='/assets/tour/bootstrap-tour.js'/>"></script>
	<script src="<c:url value='/assets/js/jquery.blockui.js'/>"></script>
	<script src="<c:url value='/assets/flot/jquery.flot.js'/>"></script>
	<script src="<c:url value='/assets/flot/jquery.flot.time.js'/>"></script>
	<script src="<c:url value='/assets/flot/jquery.flot.resize.js'/>"></script>
	<script src="<c:url value='/assets/flot/jquery.flot.selection.js'/>"></script>
	<script src="<c:url value='/assets/js/srch.js'/>"></script>
	
              <script type="text/javascript">
       		var handleQueryUnitLoad = function (actions,data){
    	        $('#log-list-div').jtable({
    	            title: '',
    	            selecting: true,columnResizable:false,paging: true,pageSize: 10,
    	            dataIColl:'Results',actions: actions,
    	            fields: {
    	            	id: {title:'<fmt:message key="Search.table.id"/>',key: true,list:false},
    	                link: {
    	                    title: '',
    	                    width: '3%',
    	                    sorting: false,
    	                    edit: false,
    	                    create: false,
    	                    display: function (data) {
    	                        var $icon = $('<div class="btn-group"><button title="Detail" class="jtable-command-button" data-toggle="dropdown"><i class="icon-th-large" /></button>'+
    	                        		  '<ul class="dropdown-menu" role="menu">'+
    	                        		    '<li><a href="#">'+data.record.host+'</a></li>'+
    	                        		    '<li><a href="#">'+data.record.path+'</a></li>'+
    	                        		    '<li><a href="#" id="modal-'+data.record.id+'" class="modal-btn">More</a></li>'+
    	                        		  '</ul></div>');
    	                        
    	                        return $icon;
    	                    }
    	                },
    	                time: {title: '<fmt:message key="Search.table.time"/>',width: '17%',display:function(obj){
    	                	return new Date(obj.record.time).format("yyyy-MM-dd hh:mm:ss");
    	                }},
    	                host: {title: '<fmt:message key="Search.table.host"/>',list:false},
    	                path: {title: '<fmt:message key="Search.table.path"/>',list:false},
    	                content: {title: '<fmt:message key="Search.table.content"/>',list:false},
    	                highlightContent: {title: '<fmt:message key="Search.table.highlightContent"/>',width: '80%'}
    	            }
    	        });
    	        $('#srch_btn').click(function (e) {
    	            e.preventDefault();
    	            $('#log-list-div').jtable('load',{'condition':JSON.stringify(Srch.search.condition())},genCharts);
    	        });
    	        $('#log-list-div').jtable('load',{'condition':JSON.stringify(Srch.search.condition())},genCharts);
    		};
 			var genCharts=function(){
 				Srch.blockUI($('#collapseChart'));
 				$.ajax({
   					url: '/YCSrch/search/ajaxChart',
   					type: "GET",
   					data:{'condition':JSON.stringify(Srch.search.condition())},
   					dataType: "json",
   					success: Srch.genChart
   				});
                $('.modal-btn').click(function(event){
    				var $selectedRows = $('#log-list-div').jtable('selectedRows');
					var data = Srch.search.condition();
					data.lineNum=$selectedRows.data('record').lineNum;
					data.host=$selectedRows.data('record').host;
					data.path=$selectedRows.data('record').path;
					data.flg='mid';
					Srch.search.prevnext=data;
					Srch.search.prevnext.keyWord="";
					$('#myModal div.modal-body').load('/YCSrch/search/detail',
							{'condition':JSON.stringify(Srch.search.prevnext)},function(){
	                        	$('#myModal').modal('show');
							});
                    event.preventDefault();
                });
 			};
               
               $(document).ready(function() {
            	   $('#srch_keyword').focus();
            	   //$('#myModal div.modal-body').height($(window).height()-100);
            	   $("#srch_keyword").autocomplete({
            		   source: function( request, response ) {
            			   $.ajax({
          					url: '/YCSrch/search/ajaxSuggest',
          					type: "GET",
          					data:{'condition':JSON.stringify(Srch.search.condition())},
          					dataType: "json",
          					success: function(data){
          						response($.map(data.Suggest,function(item){
          							return item;
          						}));
          					}
          				});
            		   },
            		   minLength: 2
            	   });
            	   
                   $('#srch_keyword').keypress(function (e) {
                       if (e.which == 13) {
                    	   $('#log-list-div').jtable('load',{'condition':JSON.stringify(Srch.search.condition())},genCharts);
                       }
                   });
                   
       				var actions= {
       	                listAction: '/YCSrch/search/ajax'
       	            };
       				Srch.initMain();
       				
       				handleQueryUnitLoad(actions);
       				
    				$(".demo-container").resizable({
    					maxWidth: $('.demo-container').width(),
    					maxHeight: 500,
    					minWidth: $('.demo-container').width(),
    					minHeight: 200,
    				});

               });
               </script>
	<!-- END JAVASCRIPTS -->
</c:set>
 
</body>
<!-- END BODY -->
</html>
