<%@ include file="/common/taglibs.jsp" %>
<input type="hidden" id="lineNum-prev" value="<c:out value="${bgnLineNum}"/>"/>
<input type="hidden" id="lineNum-next" value="<c:out value="${endLineNum}"/>"/>
<table class="table table-hover" style="font-size: 12px;">
<c:forEach items="${contentList}" var="item" varStatus="status">
	<tr <c:if test="${item.lineNum==lineNum}">class="success"</c:if>><td>${status.count}</td><td>${item.content}</td></tr>
</c:forEach>
</table>

