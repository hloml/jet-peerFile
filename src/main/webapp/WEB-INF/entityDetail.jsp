<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

</script>
    <title>PeerFile - Entity detail
    </title>
  </head>
  <body style="padding-top: 60px;">
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">      
            <span class="sr-only">Toggle navigation
            </span>
            <span class="icon-bar">
            </span>
            <span class="icon-bar">
            </span>
            <span class="icon-bar">
            </span>
          </button>
          <a class="navbar-brand" href="home">PeerFile</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="monitoring"><span class="glyphicon glyphicon-stats" aria-hidden="true"></span> Monitoring</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li>
            <a href="logout"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Logout</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
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

     <form action="update_entity" method="post" class="form-vertical" role="form">
		

		<fieldset>
    		<legend>Basic Information</legend>

		
		<c:forEach var="attr" items="${basic_attr}">
	
  			<div class="form-group">
  			
    			<label class="control-label col-sm-3" for="email">${attr.name}:</label>
    			<div class="col-sm-3">
      				<input type="${attr.data_type}" class="form-control" id="${attr.name}" value="${attr.value}"  
      					<c:if  test="${attr.read_only}"> disabled </c:if>
					>
    			</div>
  			</div>

       </c:forEach>
       
       	</fieldset>
       
       
       <c:if test="${fn:length(advance_attr) gt 0}">
       	<fieldset>
    	<legend>Advance Information</legend>

		
		<c:forEach var="attr" items="${advance_attr}">
	
  			<div class="form-group">
  			
    			<label class="control-label col-sm-3" for="email">${attr.name}:</label>
    			<div class="col-sm-3">
      				<input type="${attr.data_type}" class="form-control" id="${attr.name}" value="${attr.value}"  
      					<c:if  test="${attr.read_only}"> disabled </c:if>
					>
    			</div>
  			</div>

       </c:forEach>
      </fieldset>
     </c:if>  
       
       
     <br>
        <button type="submit" class="btn btn-default btn-lg pull-right">Submit</button>
        

		
    
	</form>
    
    
    </div>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="res/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
  </body>
</html>