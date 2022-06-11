<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>

	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	
	   <title>Ellimina Satellite</title>
	   
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  		
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					<div class="alert alert-danger alert-dismissible fade show d-none" role="alert">
					  Esempio di operazione fallita!
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					<div class="alert alert-info alert-dismissible fade show d-none" role="alert">
					  Aggiungere d-none nelle class per non far apparire
					   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  		
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Elimina satellite selezionato</h5>
					    </div>
					    
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Id:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.id}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Denominazione:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.denominazione}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Codice:</dt>
							  <dd class="col-sm-9">${delete_satellite_attr.codice}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data Lancio:</dt>
							  <c:if test="${delete_satellite_attr.dataLancio == null }">
					    	  	<dd class="col-sm-9">TBD</dd>
					    	  </c:if>
							  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${delete_satellite_attr.dataLancio}" /></dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data Rientro:</dt>
							  <c:if test="${delete_satellite_attr.dataRientro == null }">
					    	  	<dd class="col-sm-9">TBD</dd>
					    	  </c:if>
							  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${delete_satellite_attr.dataRientro}" /></dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Stato :</dt>
							  <c:if test="${delete_satellite_attr.stato == null }">
					    	  	<dd class="col-sm-9">Non ancora definito</dd>
					    	  </c:if>
							  <dd class="col-sm-9">${delete_satellite_attr.stato}</dd>
					    	</dl>
					    	
					    	
					    </div>
					    <!-- end card body -->
					    	<a href="${pageContext.request.contextPath}/satellite" class='btn btn-outline-secondary' style='width:80px'>
								<i class='fa fa-chevron-left'></i> Back
							</a>
					        <a href="${pageContext.request.contextPath}/satellite/remove/${delete_satellite_attr.id}" class='btn btn-outline-danger' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Delete
					        </a>
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>