
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>title</title>
</head>
<body>
	<a href="logout">Logout</a>
	Actual path:
	<c:forEach var="temp" items="${path}">
		<a href="browse?fileCode=${temp.getFileCode()}"><c:out value="${temp.getFileName()}"></c:out></a>
	</c:forEach>
	<br>
	<br>
	
	<c:forEach var="temp" items="${files}">
		<c:choose>
			<c:when test="${temp.getIsfolder()}">
				<a href="browse?fileCode=${temp.getCode()}"><c:out
						value="${temp.getName()}"></c:out></a>
			</c:when>

			<c:otherwise>
				<c:out value="${temp.getName()}"/>
			</c:otherwise>
		</c:choose>
		<c:out value="${temp.getSize()}"/>
		<c:out value="${temp.getCreate_time()}"/>
		<c:out value="${temp.getUpdate_time()}"/>
		<br>
	</c:forEach>


</body>
</html>