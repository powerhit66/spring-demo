<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import= "java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/css/create.css"/>
<script type="text/javascript" src="/js/create.js"></script>
<title>Insert title here</title>
</head>
	<body>
	<a href="/">トップへ戻る</a>
		<form modelAttribute="bbsEntity" method="POST" action="/bbs" >
			<input required="required" type="text" name="display_name"/><br><br>投稿内容<br>
			<textarea required="required" name="content" rows="8" cols="40"></textarea><br><br>
			<input type="submit" name="btn1" value="投稿する">
			<%-- CSRFを有効化にした場合、この宣言をしないとPOSTできなくなる --%>
			<input type="hidden" 	name="${_csrf.parameterName}" 	value="${_csrf.token}"/>
		</form>
	    <button type="button" onclick="history.back()">戻る</button>
		<div class="posts">
			<c:forEach var="item" items="${pages.content}">
			    <form modelAttribute="bbsEntity" method="POST" action="/bbs_editForm">
					<p name="display_name" path="display_name"><c:out value="${item.getId()}"/>、投稿者：<c:out value="${item.getDisplay_name()}" /></p>
					<p name="updated_time"><c:out value="${item.getReply_to()}" /><br><c:out value="${item.getContent()}" /><br>更新時間：<c:out value="${item.timeToString(item.getUpdated_time())}" /></p>
					<p name="created_time">投稿時間：<c:out value="${item.timeToString(item.getCreated_time())}" /></p>
					<p name="reply_to"><c:out value="${item.getReply_to()}" /></p>
					<button type="submit" name="submit" value="reply">返信</button>
					<c:if test="${username == item.getUsername()}">
				        <button type="submit" name="submit" value="modify">編集</button>
				    	<button type="submit" name="submit" value="delete">削除</button>
				    </c:if>
				    <input type="hidden" name="id" value="${item.getId()}"/>
   				    <input type="hidden" name="username" value="${item.getUsername()}"/>
		    		<input type="hidden" name="${_csrf.parameterName}" 	value="${_csrf.token}"/>
			    </form>
			    <hr>
			</c:forEach>
		</div>
		    <!-- ページリンク -->
        <ul class="paging">
            <li>
                <c:if test="${pages.first}"><span>&lt;&lt;</span></c:if>
                <c:if test="${!pages.first}"><a href='<c:out value="/bbs?page=0"/>'/>&lt;&lt;</a></c:if>
            </li>
            <!-- 中間のページリンク -->
            <c:forEach var="i" begin="0" end="${pages.totalPages-1}" varStatus="loop">
	            <li>
	                <c:choose>
		                <c:when test="${pages.number!=loop.index}">
		                	<a href="/bbs?page=<c:out value='${loop.index}'/>"><c:out value="${loop.index+1}"/></a>
		                </c:when>
            		    <c:when test="${pages.number==loop.index}">
            		    	<c:out value="${loop.index+1}"/>
            		    </c:when>
	                </c:choose>
	            </li>
            </c:forEach>
            <li>
                <c:if test="${pages.last}"><span>&gt;&gt;</span></c:if>
                <c:if test="${!pages.last}">
                <a href='<c:out value="/bbs?page=${pages.totalPages-1}"/>'/>&gt;&gt;</a></c:if>
                </a>
            </li>
        </ul>
	</body>
</html>