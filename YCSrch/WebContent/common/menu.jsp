<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm" permissions="rolesAdapter">
    <c:if test="${empty pageContext.request.remoteUser}">
        <li class="active">
            <a href="<c:url value="/login"/>"><i class="icon-signin"></i> <fmt:message key="login.title"/></a>
        </li>
    </c:if>
    <menu:displayMenu name="MainMenu"/>
    <menu:displayMenu name="SearchMenu"/>
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="AdminMenu"/>
    <!--menu:displayMenu name="Logout"/-->
</menu:useMenuDisplayer>

