<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<!--[if IE 8]> <html lang="zh_CN" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="zh_CN" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="zh_CN"> <!--<![endif]-->
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel="shortcut icon" href="<c:url value='/assets/images/favicon.ico'/>" />
    <title><decorator:title/> | <fmt:message key="webapp.name"/></title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta content="yuChengTech Tasks" name="description" />
	<meta content="Lidw" name="author" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/bootstrap/css/bootstrap.min.css'/>"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/jquery-ui/css/smoothness/jquery-ui.css'/>"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/font-awesome/css/font-awesome.css'/>"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/bootstrap-daterangepicker/daterangepicker.css'/>"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/jtable/themes/metro/jtable_metro_base.min.css'/>"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/jtable/themes/metro/lightgray/jtable.css'/>"/>
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/wizard/bootstrap-wizard.css'/>"/>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/style.css'/>" />
	<link type="text/css" rel="stylesheet" href="<c:url value='/assets/style/srch.css'/>"/>
	<!--[if lte IE 8]><script src="<c:url value='/assets/js/html5shiv.js'/>"></script><![endif]-->
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
    <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>
	<!-- BEGIN HEADER -->
    <div class="navbar navbar-fixed-top navbar-blue">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<c:url value='/'/>"><img alt="" src="<c:url value='/assets/images/logo.png'/>" /><fmt:message key="webapp.name"/></a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
			<%@ include file="/common/menu.jsp" %>
          </ul>
			<ul class="nav navbar-nav navbar-right">
			<c:if test="${ !empty pageContext.request.remoteUser}">
				<li><a title="<fmt:message key="mainMenu.eventMenu"/>" href="#" data-toggle="collapse" data-parent="#collapseTop" data-target="#collapseEvents"><i class="icon-list"></i> <fmt:message key="mainMenu.eventMenu"/></a></li>
				<li><a title="<fmt:message key="mainMenu.taskMenu"/>" href="#" data-toggle="collapse" data-parent="#collapseTop" data-target="#collapseTasks"><i class="icon-warning-sign"></i> <fmt:message key="mainMenu.taskMenu"/></a></li>
				<li><a title="<fmt:message key="mainMenu.reportMenu"/>" href="#" data-toggle="collapse" data-parent="#collapseTop" data-target="#collapseReport"><i class="icon-bar-chart"></i> <fmt:message key="mainMenu.reportMenu"/></a></li>
				<li><a title="<fmt:message key="mainMenu.logOut"/>" href="<c:url value='/logout'/>"><i class="icon-signout"></i> <fmt:message key="mainMenu.logOut"/></a></li>
			</c:if>
			</ul>
        </div><!--/.nav-collapse -->
		<div class="panel-group" id="collapseTop">
		  <div class="panel">
		    <div id="collapseReport" class="panel-collapse collapse">
		      <div class="panel-body">
				<div class="row">
					<div class="col-md-8">
						<ol>
							<li>NCS2-计费系统错误事件</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
						</ol>
					</div>
					<div class="col-md-4">
						<div class="bs-callout">
					      <h4>自定义报表</h4>
					      <p>自定义报表。。。。。。<br>自定义报表。。。。。。</p>
					    </div>
					</div>
				</div>
		      </div>
		    </div>
		    <div id="collapseTasks" class="panel-collapse collapse">
		      <div class="panel-body">
				<div class="row">
					<div class="col-md-8">
						<ol>
							<li>NCS2-计费系统错误事件</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
						</ol>
					</div>
					<div class="col-md-4">
						<div class="bs-callout">
					      <h4>自定义任务</h4>
					      <p>
					      	任务，指用户通过搜索页内"另存为任务"的功能进行保存的任务信息。<br>
					      	可根据设定的（系统、时间区间、过滤器选择、关键词组合）或者指定的事件进行任务相关配置按照设定的时间、corn表达式来进行自动定时运行，
					      	并可以设定阈值来按照设定的通知方式进行通知。
					      </p>
					    </div>
					</div>
				</div>
		      </div>
		    </div>
	        <div id="collapseEvents" class="collapse off">
		      <div class="panel-body">
				<div class="row">
					<div class="col-md-8">
						<ol>
							<li>NCS2-计费系统错误事件</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
							<li>NCS2-NPCS Error Event</li>
						</ol>
					</div>
					<div class="col-md-4">
						<div class="bs-callout">
					      <h4>自定义事件</h4>
					      <p>
					      	事件，指用户在搜索页内"另存为事件"的功能进行保存的事件信息。<br>
					      	可根据设定的系统、时间区间、过滤器选择、关键词组合等信息来定义的事件，可以将常用的功能方便的保存以方便多次查看。
					      </p>
					    </div>
					</div>
				</div>
		      </div>
			</div>
			</div>
		</div>
		
    </div>
	<!-- END HEADER -->
 




    <div class="container-fluid">
        <%@ include file="/common/messages.jsp" %>
        <div class="row-fluid">
            <decorator:body/>

            <c:if test="${currentMenu == 'AdminMenu'}">
                <div class="col-md-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="AdminMenu"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
        </div>
    </div>

    <div id="footer" class="copyright">
        	<fmt:message key="webapp.version"/>
            <c:if test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
            </c:if>
            &copy; <fmt:message key="copyright.year"/> <a href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
    </div>
    <script src="<c:url value='/assets/js/jquery-1.10.2.min.js'/>"></script>
    <script src="<c:url value='/assets/jquery-ui/js/jquery-ui-1.10.3.custom.js'/>"></script>
    <script src="<c:url value='/assets/bootstrap/js/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/assets/bootstrap/js/respond.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/script.js'/>"></script>
<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
</body>
</html>
