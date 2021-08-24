<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- It didn't work because this library is different from the one I copied. He uses
different libraries for different pages.  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save Customer</title>
<link type = "text/css"
	rel= "stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css">
	
<link type = "text/css"
	rel= "stylesheet"
	href="${pageContext.request.contextPath}/resources/css/add-customer-style.css">
</head>
<body>
<div id="wrapper">
	<div id="header">
		<h2> CRM- Customer Relationship Manager</h2>
	</div>
</div>
<div id= "container">
	<h3>Save Customer</h3>
	<!-- modelAttribute has already been set to prepopulate.  It didnt before becuase
	it was empty when the page was previously returned -->
	<form:form action="saveCustomer" modelAttribute="customer" method="POST">
	<!-- associate data with customer id -->
	<form:hidden path ="id" />
	
	<table>
		<tbody>
		<tr>
			<td><label>First name:</label></td>
			<td><form:input path="firstName" /></td>
		</tr>
		
		<tr>
			<td><label>Last name:</label></td>
			<td><form:input path="lastName" /></td>
		</tr>
		
		<tr>
			<td><label>Email:</label></td>
			<td><form:input path="email" /></td>
		</tr>
		
		<tr>
			<td><label></label></td>
			<td><input type="submit" value="Save" class="save" /></td>
		</tr>
		
		</tbody>
	</table>
	</form:form>
</div>
</body>
</html>
