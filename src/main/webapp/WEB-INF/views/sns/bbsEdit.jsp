<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>掲示板
		<c:choose>
			<c:when test="${mode == 'delete'}">削除画面</c:when>
			<c:when test="${mode == 'modify'}">更新画面</c:when>
			<c:when test="${mode == 'reply'}">返信画面</c:when>
		</c:choose>
	</title>
</head>
<body>
　　　　<h2>
		<c:choose>
			<c:when test="${mode == 'delete'}">削除画面</h2><br>以下の投稿を削除する？</c:when>
			<c:when test="${mode == 'modify'}">更新画面</h2>投稿を更新後ボタンを押下する</c:when>
			<c:when test="${mode == 'reply'}">返信画面</h2>返信後にボタンを押下する</c:when>
		</c:choose>
	<form modelAttribute="bbsEntity" method="POST" action="/bbs_<c:choose><c:when test="${mode == 'delete'}">delete</c:when><c:when test="${mode == 'modify'}">modify</c:when><c:when test="${mode == 'reply'}">reply</c:when></c:choose>">
			利用者名<br>
			<input required="required" type="text" name="display_name" value="${bbsEntity.getDisplay_name()}"/><br><br>
			投稿内容<br><c:if test="${mode == 'reply'}">返信対象<br><textarea rows="4" cols="20" readonly name="reply"><c:out value="${reply}"/></textarea><br></c:if>
			<textarea required="required" name="content" rows="8" cols="40"><c:if test="${mode != 'reply'}">><c:out value="${bbsEntity.getContent()}" /></c:if></textarea><br><br>
			<%-- CSRFを有効化にした場合、この宣言をしないとPOSTできなくなる --%>
			<input type="hidden" 	name="${_csrf.parameterName}" 	value="${_csrf.token}"/>
			<c:choose>
				<c:when test="${mode == 'delete'}"><input type="submit" name="btn1" value="削除する"></c:when>
				<c:when test="${mode == 'modify'}"><input type="submit" name="btn1" value="更新する"></c:when>
				<c:when test="${mode == 'reply'}"><input type="submit" name="btn1" value="返信する"></c:when>
			</c:choose>
		    <button type="button" onclick="history.back()">戻る</button>
			<input type="hidden" name="id" value="${bbsEntity.getId()}"/>
			<input type="hidden" name="username" value="${bbsEntity.getUsername()}"/>
			<input type="hidden" name="created_time" value="${bbsEntity.getCreated_time()}"/>
	</form>
</body>
</html>