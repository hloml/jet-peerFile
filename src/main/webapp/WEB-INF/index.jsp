<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
    <!-- Bootstrap -->
    <link href="res/bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="res/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css" rel="stylesheet">
    <!-- Custom styles for sing in -->
    <link href="res/signin.css" rel="stylesheet">
    <!-- jQuery -->
<script type="text/javascript" charset="utf-8" src="res/DataTables-1.10.6/media/js/jquery.js"></script>
    <title>peerfile - login
    </title>
  </head>
  <body style="padding-top: 60px;">
    
    <jsp:include page="navigation.jsp"/>
    
    <div class="container">
      <form action="login" method="post" class="form-signin">
      
      
      <c:if  test="${not empty errorMessage}">
      	<div class="alert alert-danger" role="alert">
        	<c:forEach var="message" items="${errorMessage}">
        		<c:out value="${message}"/> <br>
        	</c:forEach>
      	</div>
      </c:if>
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="username">Username
        </label>
        <input type="text" id="username" name="username" class="form-control"placeholder="Username" required autofocus>
        
        <label for="password">Password
        </label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>  
        
        <label for="server" >Server
        </label>
		<select name="server" class="form-control">
    		<c:forEach items="${serversList}" var="server">
        		<option value="${server.key}" ${server.key == chosenServer ? 'selected' : ''}>${server.key}</option>
    		</c:forEach>
		</select>

        <br>
       
        
        <!--
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        -->
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in
        </button>
      </form>
    </div>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
<!--
<script src="res/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
-->
  </body>
</html>