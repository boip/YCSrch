<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="menu" content="MainMenu"/>
</head>
<body class="home">
<div class="crumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-home"></i>
			<a href="#">YCSrch</a> 
		</li>
		<li>
			<a href="#"><fmt:message key="mainMenu.title"/></a>
		</li>
	</ul>
</div>

<div class="row row-bg"> 
	<div class="col-sm-6 col-md-3"> 
		<div class="statbox widget box box-shadow"> 
			<div class="widget-content"> 
				<div class="visual cyan"> 
				<div class="statbox-sparkline"> <i class=" icon-cloud"></i> </div> 
				</div> <div class="title">业务系统主机数量</div> 
				<div class="value">22</div> 
				<a href="javascript:void(0);" class="more">查看更多 <i class="pull-right icon-angle-right"></i></a> 
			</div> 
		</div> 
	</div> 
	<div class="col-sm-6 col-md-3"> 
		<div class="statbox widget box box-shadow"> 
			<div class="widget-content"> 
				<div class="visual green"> 
				<div class="statbox-sparkline"> <i class=" icon-reorder"></i> </div> 
				</div> 
				<div class="title">日志文档记录数量</div> 
				<div class="value">121 714</div> 
				<a href="javascript:void(0);" class="more">查看更多 <i class="pull-right icon-angle-right"></i></a> 
			</div> 
		</div> 
	</div> 
	<div class="col-sm-6 col-md-3 hidden-xs"> 
		<div class="statbox widget box box-shadow"> 
			<div class="widget-content"> 
				<div class="visual yellow"> <i class="icon-bookmark"></i> </div> 
				<div class="title">我的自定义事件数量</div> 
				<div class="value">22</div> 
				<a href="javascript:void(0);" class="more">查看更多 <i class="pull-right icon-angle-right"></i></a> 
			</div> 
		</div> 
	</div> 
	<div class="col-sm-6 col-md-3 hidden-xs"> 
		<div class="statbox widget box box-shadow"> 
			<div class="widget-content"> 
				<div class="visual red"> <i class="icon-warning-sign"></i> </div> 
				<div class="title">我的告警任务数量</div> <div class="value">10</div> 
				<a href="javascript:void(0);" class="more">查看更多 <i class="pull-right icon-angle-right"></i></a> 
			</div> 
		</div> 
	</div> 
</div>
<BR><BR><BR><BR>待定<BR><BR><BR><BR>
<div class="wizard" id="some-wizard">
 
    <h1>Wizard Title</h1>
 
    <div class="wizard-card" data-cardname="card1">
        <h3>Card 1</h3>
        Some content
    </div>
 
    <div class="wizard-card" data-cardname="card2">
        <h3>Card 2</h3>
        Some other content
    </div>
 
</div>
<!--h2><fmt:message key="mainMenu.heading"/></h2>
<p><fmt:message key="mainMenu.message"/></p>

<ul class="glassList">
    <li>
        <a href="<c:url value='/userform'/>"><fmt:message key="menu.user"/></a>
    </li>
    <li>
        <a href="<c:url value='/fileupload'/>"><fmt:message key="menu.selectFile"/></a>
    </li>
</ul-->
<c:set var="scripts" scope="request">
	<script src="<c:url value='/assets/wizard/bootstrap-wizard.js'/>"></script>
	<script type="text/javascript">
	$(function() {
	    var options = {};
	    var wizard = $("#some-wizard").wizard(options);
	   // wizard.show();
	});
	</script>
</c:set>
</body>
