<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="tools.Formatter" %>

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
    <!-- Custom styles for monitoring -->
    <link href="res/monitoring.css" rel="stylesheet">
    <title>PeerFile - Monitoring
    </title>
  </head>
  <body style="padding-top: 80px;">
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
            <li class="active"><a href="monitoring"><span class="glyphicon glyphicon-stats" aria-hidden="true"></span> Monitoring</a></li>
          </ul>
          <c:if test="${isLogged}">
          <ul class="nav navbar-nav navbar-right">
            <li>
            <a href="logout"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Logout</a>
            </li>
          </ul>
          </c:if>
        </div>
      </div>
    </nav>
    <div class="container">
      
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingInstances">
      <h4 class="panel-title">
        <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseInstances" aria-expanded="true" aria-controls="collapseInstances">
          Instances
        </a>
      </h4>
    </div>
    <div id="collapseInstances" class="panel-collapse collapse<c:if test="${serverMonitor == null}"> in</c:if>" role="tabpanel" aria-labelledby="headingInstances">
      <div class="panel-body">
      
      <!-- Table -->
      <table id="servers" class="table">
        <thead>
          <tr>
            <th>Server code (key)
            </th>
            <th>Address
            </th>
            <th>Port
            </th>
            <th>Description
            </th>
          </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${serversList}" var="server">
          <tr>
            <td>
            <a href="monitoring?serverKey=${server.key}"><c:out value="${server.key}"></c:out></a>
            
      		</td>
      		<td>
      		${server.value.address}
            </td>
      		<td>
      		${server.value.port}
            </td>
      		<td>
      		${server.value.description}
            </td>
      	  </tr>
      	</c:forEach>
      	
        </tbody>
      </table>


      </div>
    </div>
  </div>


<c:if test="${serverMonitor != null}">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingMonitor">
      <h4 class="panel-title">
        <span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseMonitor" aria-expanded="false" aria-controls="collapseMonitor">
          Monitoring
        </a>
      </h4>
    </div>
    <div id="collapseMonitor" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingMonitor">
      <div class="panel-body">
	  
	  <div class="row">
	  
	    <!-- Info -->
        <div class="col-md-6">
        
	      <h4>Instance info</h4>
	      
	      <div class="row">
            <div class="col-md-7">
            Server key:
            </div>
            <div class="col-md-5">
            ${choosenServer.getCode()}
            </div>
	      </div>
        
	      <div class="row">
            <div class="col-md-7">
            Address:
            </div>
            <div class="col-md-5">
            <a href="${choosenServer.getAddress()}:${choosenServer.getPort()}">${choosenServer.getAddress()}:${choosenServer.getPort()}</a>
            </div>
	      </div>
	      
	      <div class="row">
            <div class="col-md-7">
            PeerFile version:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getPf_version()}
            </div>
	      </div>
        
	      <div class="row">
            <div class="col-md-7">
            Instance ID:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getInstance_id()}
            </div>
	      </div>
        
	      <div class="row">
            <div class="col-md-7">
            System load:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getSystem_load()}
            </div>
	      </div>
        
	      <div class="row">
            <div class="col-md-7">
            Sessions count:
            </div>
            <div class="col-md-5">
            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseSessions" aria-expanded="false" aria-controls="collapseSessions">
            ${serverMonitor.getSessions_count()}
            </a>
            </div>
	      </div>
	  
      <hr>
	      <h4>Replication configuration</h4>
      
	      <div class="row">
            <div class="col-md-7">
            Use replica:
            </div>
            <div class="col-md-5">
            <c:choose><c:when test="${serverMonitor.getReplication_config()[0]}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
            </div>
	      </div>
	      
	      <div class="row">
            <div class="col-md-7">
            Acts as replica
            </div>
            <div class="col-md-5">
            <c:choose><c:when test="${serverMonitor.getReplication_config()[1]}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
            </div>
	      </div>
	      
      <hr>
	      <h4>Replica Master</h4>
      
	      <div class="row">
            <div class="col-md-7">
            Incomplete messages:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getReplica_master_info()[0]}
            </div>
	      </div>
      
	      <div class="row">
            <div class="col-md-7">
            Unprocessed messages:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getReplica_master_info()[1]}
            </div>
	      </div>
      
	      <div class="row">
            <div class="col-md-7">
            Failed messages:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getReplica_master_info()[2]}
            </div>
	      </div>
	      
      <hr>
	      <h4>Replica Slave</h4>
      
	      <div class="row">
            <div class="col-md-7">
            Incomplete messages:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getReplica_slave_info()[0]}
            </div>
	      </div>
      
	      <div class="row">
            <div class="col-md-7">
            Unprocessed messages:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getReplica_slave_info()[1]}
            </div>
	      </div>
      
	      <div class="row">
            <div class="col-md-7">
            Failed messages:
            </div>
            <div class="col-md-5">
            ${serverMonitor.getReplica_slave_info()[2]}
            </div>
	      </div>
	      
	    </div>
	    
	    <!-- Graphs -->
	    <div class="col-md-6">
	    
	    <h4>Instance usage info</h4>
	    <h5>System load</h5>
	      <div class="progress">
            <div class="progress-bar progress-bar-custom" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${serverMonitor.getSystem_load()}" />;">
              <span><fmt:formatNumber type="percent" maxFractionDigits="2" value="${serverMonitor.getSystem_load()}" /></span>
            </div>
          </div>
          
	    <h5>Memory usage</h5>
	      <div class="progress">
            <div class="progress-bar progress-bar-custom" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${serverMonitor.getMemory_info()[1]/serverMonitor.getMemory_info()[0]}" />;">
              <span>${Formatter.convertBytes(serverMonitor.getMemory_info()[1])} / ${Formatter.convertBytes(serverMonitor.getMemory_info()[0])}</span>
              
            </div>
          </div>
          
          <hr>
          
          <h4>Disk info</h4>
		  <c:forEach var="diskSpace" items="${serverMonitor.getDisk_space()}">
	    <h5><span class="glyphicon glyphicon-hdd" aria-hidden="true"></span> ${diskSpace.getDriveName()}</h5>
	      <div class="progress">
            <div class="progress-bar progress-bar-custom" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${diskSpace.getFreeSpace()/diskSpace.getTotalSpace()}" />;">
              <span>${Formatter.convertBytes(diskSpace.getFreeSpace())} / ${Formatter.convertBytes(diskSpace.getTotalSpace())}</span>
              
            </div>
          </div>
		</c:forEach>
		
          
          
	    </div>
	    
	  </div>
	  
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingSessions">
      <h4 class="panel-title">
        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseSessions" aria-expanded="false" aria-controls="collapseSessions">
          Sessions info
        </a>
      </h4>
    </div>
    <div id="collapseSessions" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSessions">
      <div class="panel-body">
	  
	  <c:choose>
	    <c:when test="${serverMonitor.getSessions_count() < 1}">
	      There are currently no sessions active.
	    </c:when>
	    <c:otherwise>
	    
	      <!-- Table -->
	      <table id="servers" class="table">
	        <thead>
	          <tr>
	            <th>Session name
	            </th>
	            <th>Session code
	            </th>
	            <th>Session start
	            </th>
	            <th>Last request
	            </th>
	            <th>User name
	            </th>
	          </tr>
	        </thead>
	        <tbody>
	        
	        <c:forEach var="sessionInfo" items="${serverMonitor.getSessions_info()}">
	          <tr>
	            <td>
	            ${sessionInfo.getSessionName()}
	      		</td>
	      		<td>
	      		${sessionInfo.getSessionCode()}
	            </td>
	      		<td>
	      		${sessionInfo.getSessionStart()}
	            </td>
	      		<td>
	      		${sessionInfo.getLastRequest()}
	            </td>
	      		<td>
	      		${sessionInfo.getUserName()}
	            </td>
	      	  </tr>
	      	</c:forEach>
	      	
	        </tbody>
	      </table>
	      
	    </c:otherwise>
	  </c:choose>
	  
	  </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingPassenger">
      <h4 class="panel-title">
        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapsePassenger" aria-expanded="false" aria-controls="collapsePassenger">
          Passenger info
        </a>
      </h4>
    </div>
    <div id="collapsePassenger" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingPassenger">
      <div class="panel-body">
	  
	  	<pre>${serverMonitor.getPassenger_info()}</pre>
	  	
	  </div>
    </div>
  </div>
</c:if>
</div>
	  
      
    </div>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="res/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
  </body>
</html>