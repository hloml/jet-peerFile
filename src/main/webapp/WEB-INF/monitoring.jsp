<%@page import="monitoring.JsonMonitoring"%>
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
    <!-- Initialization -->
    <script type="text/javascript" class="init">
    // tooltip initialization
    $(function () {
      $('[data-toggle="tooltip"]').tooltip()
    })
    // open accordion on url hashtag
    $(document).ready(function() {
      var anchor = window.location.hash;
      $(anchor).addClass("in");
    });
    // reload page
    var toId;
    $(document).ready(function () { // init autoRefresh
      var autoRefreshTimer = ${autoRefreshTimer};
      var urlString = window.location.href;
      var hash = urlString.split("#")[1];
      $('.setRefreshTimer').hide(); // comment this line to show autorefresh
      $('.stopRefreshTimer').hide();
      autoRefresh(hash, autoRefreshTimer);
    })
    function reloadPage(hash) { // reload once
      window.location.href = "#" + hash;
      location.reload();
    }
    function autoRefresh(hash, timer) { // set autoRefresh on hash with timer
      if (timer > 0) {
        if (window.location.href.indexOf("refresh") < 0) {
          var urlString = window.location.href;
          var url = urlString.split("#")[0];
          if (url.indexOf("?") < 0) {
          	window.location.href = "?refresh=" + timer;
          } else {
          	window.location.href = url + "&refresh=" + timer + "#" + hash;
          }
        }
        toId = setTimeout(function(){
          reloadPage(hash);
        }, (timer*1000));
        $('.setRefreshTimer').hide();
        $('.stopRefreshTimer').show();
      }
    }
    function stopAutoRefresh() { // stop autoRefresh
      clearTimeout(toId);
      $('.setRefreshTimer').show();
      $('.stopRefreshTimer').hide();
    }
    </script>
    <title>PeerFile - Monitoring
    </title>
  </head>
  <body style="padding-top: 80px;">
    
    <jsp:include page="navigation.jsp"/>
    
    <div class="container">
      
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingInstances">
      <h4 class="panel-title">
        <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseInstances" aria-expanded="true" aria-controls="collapseInstances">
          Instances
        </a>
        <c:if test="${monitoredServer != null}">
        -- <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> <strong>${monitoredServer.getCode()}</strong>
        </c:if>
      </h4>
    </div>
    <div id="collapseInstances" class="panel-collapse collapse<c:if test="${monitoredServer == null}"> in</c:if>" role="tabpanel" aria-labelledby="headingInstances">
      <div class="panel-body">
      
      <!-- Table -->
      <table id="servers" class="table">
        <thead>
          <tr>
            <th><span data-toggle="tooltip" data-placement="top" title="Server code">Code</span>
            </th>
            <th><span data-toggle="tooltip" data-placement="top" title="Server address">Address</span>
            </th>
            <th><span data-toggle="tooltip" data-placement="top" title="Description">Description</span>
            </th>
            <!-- monitoring json -->
            <th><span data-toggle="tooltip" data-placement="top" title="PeerFile version">PFv.</span> <!-- PeerFile version -->
            </th>
            <th><span data-toggle="tooltip" data-placement="top" title="ID">ID</span>
            </th>
            <th><span data-toggle="tooltip" data-placement="top" title="Sessions count">S.</span>
            </th>
            <th><span data-toggle="tooltip" data-placement="top" title="Replication">Repl.</span>
            </th>
            <th><span data-toggle="tooltip" data-placement="top" title="System load">Load</span>
            </th>
            <th><span data-toggle="tooltip" data-placement="top" title="Memory usage">Mem.</span>
            </th>
            <th><span data-toggle="tooltip" data-placement="top" title="Disk info">Disk</span>
            </th>
          </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${serversList}" var="server">
          <tr>
            <td>
            <a <c:if test="${monitoredServer == server}">data-toggle="collapse" data-parent="#accordion" aria-expanded="false" aria-controls="collapseMonitor"</c:if>
            href="monitoring?serverKey=${server.code}#collapseMonitor"><c:out value="${server.code}"></c:out></a>
      		</td>
      		<td>
      		<a href="${server.getAddress()}:${server.getPort()}">${server.getAddress()}:${server.getPort()}</a>
            </td>
      		<td>
      		${server.description}
            </td>
            
      		<c:choose>
      		  <c:when test="${server.monitoring().isMonitoringAvailable()}">
      		  
      		<td>
      		${server.monitoring().getPf_version()}
            </td>
      		<td>
      		${server.monitoring().getInstance_id()}
            </td>
      		<td>
      		<a <c:if test="${monitoredServer == server}">data-toggle="collapse" data-parent="#accordion" aria-expanded="false" aria-controls="collapseSessions"</c:if>
      		href="monitoring?serverKey=${server.code}#collapseSessions">${server.monitoring().getSessions_count()}</a>
            </td>
      		<td>
      		<c:choose>
      		  <c:when test="${server.monitoring().getReplication_config()[0]}">
      		    <span data-toggle="tooltip" data-placement="left" title="Replica Master">M</span>
      		    <span data-toggle="tooltip" data-placement="top" title="Incomplete messages">${server.monitoring().getReplica_master_info()[0]}</span>/<span data-toggle="tooltip" data-placement="bottom" title="Unprocessed messages">${server.monitoring().getReplica_master_info()[1]}</span>/<span data-toggle="tooltip" data-placement="right" title="Failed messages">${server.monitoring().getReplica_master_info()[2]}</span>
      		  </c:when>
      		  <c:when test="${server.monitoring().getReplication_config()[1]}">
      		    <span data-toggle="tooltip" data-placement="left" title="Replica Slave">S</span>
      		    <span data-toggle="tooltip" data-placement="top" title="Incomplete messages">${server.monitoring().getReplica_slave_info()[0]}</span>/<span data-toggle="tooltip" data-placement="bottom" title="Unprocessed messages">${server.monitoring().getReplica_slave_info()[1]}</span>/<span data-toggle="tooltip" data-placement="right" title="Failed messages">${server.monitoring().getReplica_slave_info()[2]}</span>
      		  </c:when>
      		  <c:otherwise>
      		    No
      		  </c:otherwise>
      		</c:choose>

            </td>
      		<td>
	      <div class="progress progress-small" data-toggle="tooltip" data-placement="top" title="${server.monitoring().getSystem_load()}">
            <div class="progress-bar progress-bar-custom-${Formatter.workloadState(Formatter.serverLoadPercent(server.monitoring().getSystem_load()))}" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${Formatter.serverLoadPercent(server.monitoring().getSystem_load())}" />;">
            </div>
          </div>
            </td>
      		<td>
	      <div class="progress progress-small" data-toggle="tooltip" data-placement="top" title="${Formatter.convertKiloBytes(server.monitoring().getMemory_info()[2])} / ${Formatter.convertKiloBytes(server.monitoring().getMemory_info()[0])}">
            <div class="progress-bar progress-bar-custom-${Formatter.workloadState(server.monitoring().getMemory_info()[2], server.monitoring().getMemory_info()[0])}" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${server.monitoring().getMemory_info()[2]/server.monitoring().getMemory_info()[0]}" />;">
            </div>
          </div>
            </td>
      		<td>
	      <div class="progress progress-mini" data-toggle="tooltip" data-placement="top" title="${server.monitoring().getDisk_space()[0].getDriveName()}: ${Formatter.convertKiloBytes(server.monitoring().getDisk_space()[0].getUsedSpace())} / ${Formatter.convertKiloBytes(server.monitoring().getDisk_space()[0].getTotalSpace())}">
            <div class="progress-bar progress-bar-custom-${Formatter.workloadState(server.monitoring().getDisk_space()[0].getUsedSpace(), server.monitoring().getDisk_space()[0].getTotalSpace())}" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${server.monitoring().getDisk_space()[0].getUsedSpace()/server.monitoring().getDisk_space()[0].getTotalSpace()}" />;">
            </div>
          </div>
	      <div class="progress progress-mini" data-toggle="tooltip" data-placement="bottom" title="${server.monitoring().getDisk_space()[1].getDriveName()}: ${Formatter.convertKiloBytes(server.monitoring().getDisk_space()[1].getUsedSpace())} / ${Formatter.convertKiloBytes(server.monitoring().getDisk_space()[1].getTotalSpace())}">
            <div class="progress-bar progress-bar-custom-${Formatter.workloadState(server.monitoring().getDisk_space()[1].getUsedSpace(), server.monitoring().getDisk_space()[1].getTotalSpace())}" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${server.monitoring().getDisk_space()[1].getUsedSpace()/server.monitoring().getDisk_space()[1].getTotalSpace()}" />;">
            </div>
          </div>
            </td>
            
      		  </c:when>
      		  <c:otherwise>
      		    <td colspan="7">
                  <span class="alert alert-danger alert-small" role="alert" style="margin-top:20px;">
                    <strong>Error:</strong> Monitoring instance is not available.
                  </span>
      		    </td>
      		  </c:otherwise>
      		</c:choose>
            
      	  </tr>
      	</c:forEach>
      	
        </tbody>
      </table>


      <hr>
      <button type="button" class="btn btn-default"
        onclick="reloadPage('collapseInstances')"
        data-toggle="tooltip" data-placement="top"
        title="Refresh">
        <span class="glyphicon glyphicon-refresh"
          aria-hidden="true"></span>
      </button>
                
                <button type="button" class="btn btn-default setRefreshTimer"
                  onclick="autoRefresh('collapseInstances', 2)"
                  data-toggle="tooltip" data-placement="top"
                  title="Auto Refresh">
                  <span class="glyphicon glyphicon-play"
                    aria-hidden="true"></span> Set Auto Refresh
                </button>
                
                <button type="button" class="btn btn-default stopRefreshTimer"
                  onclick="stopAutoRefresh()"
                  data-toggle="tooltip" data-placement="top"
                  title="Stop Auto Refresh">
                  <span class="glyphicon glyphicon-pause"
                    aria-hidden="true"></span> Stop Auto Refresh
                </button>
                

      </div>
    </div>
  </div>


<c:if test="${monitoredServer != null}">
<c:choose>
<c:when test="${monitoredServer.monitoring().isMonitoringAvailable()}">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingMonitor">
      <h4 class="panel-title">
        <span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseMonitor" aria-expanded="false" aria-controls="collapseMonitor">
          Monitoring
        </a>
      </h4>
    </div>
    <div id="collapseMonitor" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingMonitor">
      <div class="panel-body">
	  
	  <div class="row">
	  
	    <!-- Info -->
        <div class="col-md-6">
        
	      <h4>Instance info</h4>
	      
	      <div class="row">
            <div class="col-md-7">
            Server code:
            </div>
            <div class="col-md-5">
            ${monitoredServer.getCode()}
            </div>
	      </div>
        
	      <div class="row">
            <div class="col-md-7">
            Address:
            </div>
            <div class="col-md-5">
            <a href="${monitoredServer.getAddress()}:${monitoredServer.getPort()}">${monitoredServer.getAddress()}:${monitoredServer.getPort()}</a>
            </div>
	      </div>
	      
	      <div class="row">
            <div class="col-md-7">
            PeerFile version:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getPf_version()}
            </div>
	      </div>
        
	      <div class="row">
            <div class="col-md-7">
            Instance ID:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getInstance_id()}
            </div>
	      </div>
        
	      <div class="row">
            <div class="col-md-7">
            System load:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getSystem_load()}
            </div>
	      </div>
        
	      <div class="row">
            <div class="col-md-7">
            Sessions count:
            </div>
            <div class="col-md-5">
            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseSessions" aria-expanded="false" aria-controls="collapseSessions">
            ${monitoredServer.monitoring().getSessions_count()}
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
            <c:choose><c:when test="${monitoredServer.monitoring().getReplication_config()[0]}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
            </div>
	      </div>
	      
	      <div class="row">
            <div class="col-md-7">
            Acts as replica
            </div>
            <div class="col-md-5">
            <c:choose><c:when test="${monitoredServer.monitoring().getReplication_config()[1]}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
            </div>
	      </div>
	      
      <hr>
	      <h4>Replica Master</h4>
      
	      <div class="row">
            <div class="col-md-7">
            Incomplete messages:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getReplica_master_info()[0]}
            </div>
	      </div>
      
	      <div class="row">
            <div class="col-md-7">
            Unprocessed messages:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getReplica_master_info()[1]}
            </div>
	      </div>
      
	      <div class="row">
            <div class="col-md-7">
            Failed messages:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getReplica_master_info()[2]}
            </div>
	      </div>
	      
      <hr>
	      <h4>Replica Slave</h4>
      
	      <div class="row">
            <div class="col-md-7">
            Incomplete messages:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getReplica_slave_info()[0]}
            </div>
	      </div>
      
	      <div class="row">
            <div class="col-md-7">
            Unprocessed messages:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getReplica_slave_info()[1]}
            </div>
	      </div>
      
	      <div class="row">
            <div class="col-md-7">
            Failed messages:
            </div>
            <div class="col-md-5">
            ${monitoredServer.monitoring().getReplica_slave_info()[2]}
            </div>
	      </div>
	      
	    </div>
	    
	    <!-- Graphs -->
	    <div class="col-md-6">
	    
	    <h4>Instance usage info</h4>
	    <h5>System load</h5>
	      <div class="progress progress-large">
            <div class="progress-bar progress-bar-custom-${Formatter.workloadState(Formatter.serverLoadPercent(monitoredServer.monitoring().getSystem_load()))} progress-bar-custom-large" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${Formatter.serverLoadPercent(monitoredServer.monitoring().getSystem_load())}" />;">
              <span>${monitoredServer.monitoring().getSystem_load()}</span>
            </div>
          </div>
          
	    <h5>Memory usage</h5>
	      <div class="progress progress-large">
            <div class="progress-bar progress-bar-custom-${Formatter.workloadState(monitoredServer.monitoring().getMemory_info()[2], monitoredServer.monitoring().getMemory_info()[0])} progress-bar-custom-large" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${monitoredServer.monitoring().getMemory_info()[2]/monitoredServer.monitoring().getMemory_info()[0]}" />;">
              <span>${Formatter.convertKiloBytes(monitoredServer.monitoring().getMemory_info()[2])} / ${Formatter.convertKiloBytes(monitoredServer.monitoring().getMemory_info()[0])}</span>
              
            </div>
          </div>
          
          <hr>
          
          <h4>Disk info</h4>
		  <c:forEach var="diskSpace" items="${monitoredServer.monitoring().getDisk_space()}">
	    <h5><span class="glyphicon glyphicon-hdd" aria-hidden="true"></span> ${diskSpace.getDriveName()}</h5>
	      <div class="progress progress-large">
            <div class="progress-bar progress-bar-custom-${Formatter.workloadState(diskSpace.getUsedSpace(), diskSpace.getTotalSpace())} progress-bar-custom-large" role="progressbar" style="width: <fmt:formatNumber type="percent" maxFractionDigits="0" value="${diskSpace.getUsedSpace()/diskSpace.getTotalSpace()}" />;">
              <span>${Formatter.convertKiloBytes(diskSpace.getUsedSpace())} / ${Formatter.convertKiloBytes(diskSpace.getTotalSpace())}</span>
              
            </div>
          </div>
		</c:forEach>
		
          
	    </div>
	    
	  </div>
          <hr>
          <button type="button" class="btn btn-default" onclick="reloadPage('collapseMonitor')" data-toggle="tooltip" data-placement="top" title="Refresh">
            <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
          </button>
	  
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
	    <c:when test="${monitoredServer.monitoring().getSessions_count() < 1}">
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
	        
	        <c:forEach var="sessionInfo" items="${monitoredServer.monitoring().getSessions_info()}">
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
	  
        <hr>
        <button type="button" class="btn btn-default" onclick="reloadPage('collapseSessions')" data-toggle="tooltip" data-placement="top" title="Refresh">
          <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
        </button>
          
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
	  
	  	<pre>${monitoredServer.monitoring().getPassenger_info()}</pre>
	  	
      
        <hr>
        <button type="button" class="btn btn-default" onclick="reloadPage('collapsePassenger')" data-toggle="tooltip" data-placement="top" title="Refresh">
          <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
        </button>
          
	  </div>
    </div>
  </div>
</c:when>
<c:otherwise>
  <div class="alert alert-danger" role="alert" style="margin-top:20px;">
    <strong>Error:</strong> Monitoring instance <strong><i>${monitoredServer.getCode()}</i></strong> is not available.
  </div>
</c:otherwise>
</c:choose>
</c:if>
</div>
	  
      
    </div>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="res/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
  </body>
</html>