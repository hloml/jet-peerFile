<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="res/DataTables-1.10.6/media/css/jquery.dataTables.min.css">
    <!-- Bootstrap -->
    <link href="res/bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="res/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css" rel="stylesheet">
    <!-- jQuery -->
    <script type="text/javascript" charset="utf-8" src="res/DataTables-1.10.6/media/js/jquery.js"></script>
    <!-- DataTables -->
    <script type="text/javascript" charset="utf-8" src="res/DataTables-1.10.6/media/js/jquery.dataTables.min.js"></script>
    <!-- Initialization -->
<script type="text/javascript" class="init">
$(document).ready( function () {
  var table = $('#files').DataTable(
    { "columns" : [
      {    },
      {    "render": formatSize },
      {    },
      {    }
      ] 
    }
  );
} );
function formatSize(data, type, row)
{
  if (type == "sort" || type == 'type')
    return data;
  if(isNaN(data)){
    return data;
  } else {
    num = data;
    var i = 0;
    var k = 1024;
    var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    while(num >= k && i < sizes.length) {
      num = (num / k).toFixed(2);
      i++;
    }
    return num + " " + sizes[i];
  }
}
</script>
    <title>PeerFile - File Explorer
    </title>
  </head>
  <body style="padding-top: 60px;">
	
	<jsp:include page="navigation.jsp"/>

    <div class="container">
      <c:if  test="${not empty errorMessage}">
      	<div class="alert alert-danger" role="alert">
      		<c:forEach var="message" items="${errorMessage}">
        		<c:out value="${message}"/> <br>
        	</c:forEach>
      	</div>
      </c:if>

      <c:forEach var="temp" items="${path}">
      /
      <span class="btn btn-xs btn-link">
          <c:choose>
      	      <c:when test="${empty temp.fileName}">
      		      <a href="browse?fileCode=${temp.fileCode}"><c:out value="${temp.fileCode}"></c:out></a>
      		  </c:when>
      	      <c:otherwise>
      			  <a href="browse?fileCode=${temp.fileCode}"><c:out value="${temp.fileName}"></c:out></a>
      		  </c:otherwise>
      	  </c:choose>
		    
	  </span>
      </c:forEach>
    
      <hr style="margin-top: 6px; margin-bottom: 6px; padding-bottom: 6px;">
      
      <!-- Table -->
      <table id="files" class="table">
        <thead>
          <tr>
            <th>Name
            </th>
            <th>Size
            </th>
            <th>Created
            </th>
            <th>Updated
            </th>
          </tr>
        </thead>
        <tbody>
        
        <c:forEach var="temp" items="${files}">
          <tr>
            <td>
      		<c:choose>
      			<c:when test="${temp.isfolder}">
      				<a href="browse?fileCode=${temp.code}"><c:out value="${temp.name}~${temp.code}~"></c:out></a>
      			</c:when>
      			<c:when test="${temp.size eq -1}">
    				<a href="entity_detail?fileCode=${temp.code}"><c:out value="${temp.name}~${temp.code}~"></c:out></a>
  				</c:when>
      			<c:otherwise>
      				<a href="download?fileCode=${temp.code}&name=${temp.name}&parentCode=${parentCode}"><c:out value="${temp.name}~${temp.code}~"/></a>
      			</c:otherwise>
      		</c:choose>
      		  </td>
      		  <td>
      		<c:choose>
      			<c:when test="${temp.isfolder}">
      				<span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>
      			</c:when>
      			<c:when test="${temp.size eq -1}">
    				<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
  				</c:when>
      			<c:otherwise>
      				<c:out value="${temp.size}"/>
      			</c:otherwise>
      		</c:choose>
            </td>
      		  <td><c:out value="${temp.create_time}"/></td>
      		  <td><c:out value="${temp.update_time}"/></td>
      		</tr>
      	</c:forEach>
      	
        </tbody>
      </table>
    </div>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="res/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
  </body>
</html>