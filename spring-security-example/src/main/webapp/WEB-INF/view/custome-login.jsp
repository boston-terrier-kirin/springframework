<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<style>
		.failed {
			color: red;
		}
	</style>
</head>
<body>
	<h3>Custom Login Page</h3>
	<c:if test="${param.logout != null}">
		<i>You are logged out.</i>
	</c:if>
	<c:if test="${param.error != null}">
		<i class="failed">Invalid username or password.</i>
	</c:if>
	<form:form action="${pageContext.request.contextPath}/authenticateUser" method="post">
		User Name: <input type="text" name="username" />
		Password: <input type="password" name="password" />
		<button class="btn btn-primary">Login</button>
	</form:form>
	
	<p>
		<span>${_csrf.parameterName}</span>=<span>${_csrf.token}</span>
	</p>
</body>
</html>