<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<title>Insert title here</title>
</head>
<body>
	<h1>Welcome</h1>

	<div>
		<security:authentication property="principal.username" /> / <security:authentication property="principal.authorities" />
	</div>

	<security:authorize access="hasRole('MANAGER')">
		<div>
			<a href="${pageContext.request.contextPath}/leaders">LeaderShip Meeting</a>
		</div>
	</security:authorize>

	<security:authorize access="hasRole('ADMIN')">
		<div>
			<a href="${pageContext.request.contextPath}/systems">Admin Console</a>
		</div>
	</security:authorize>

	<form:form action="${pageContext.request.contextPath}/logout" method="post">
		<button class="btn btn-primary">Logout</button>
	</form:form>
</body>
</html>
