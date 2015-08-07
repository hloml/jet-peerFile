<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
            <li <c:if test="${navActive eq 'monitoring'}">class="active"</c:if>><a href="monitoring<c:if test="${not empty sessionScope.code}">?serverKey=${sessionScope.server}#collapseMonitor</c:if>"><span class="glyphicon glyphicon-stats" aria-hidden="true"></span> Monitoring</a></li>
          </ul>
          <c:if test="${not empty sessionScope.code}">
          <ul class="nav navbar-nav navbar-right">
            <li>
            <a href="logout"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Logout</a>
            </li>
          </ul>
          </c:if>
        </div>
      </div>
    </nav>