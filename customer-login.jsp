<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer login</title>
<jsp:include page="includes/header.jsp" />
</head>
<body>
	<jsp:include page="includes/nav.jsp" />

	<div class="container-fluid h-100">
		<div class="row justify-content-center align-items-center h-100">
			<div class="col col-sm-6 col-md-6 col-lg-4 col-xl-3">
				<form action="LoginServlet" class="mt-4" method="post">
				<h2>Sign in</h2>
				
				<% String msg = (String)request.getAttribute("error"); %>
				<% if(msg!=null){ %>
					<div class="alert alert-danger" role="alert">
					  <%=msg %>
					</div>
				<%} %>
				
					<div class="form-group mb-3">
						<label>Email</label> <input type="text" name="email"
							class="form-control" required />
					</div>
					<div class="form-group mb-3">
						<label>Password</label> <input type="password" name="password"
							class="form-control" required />
					</div>
					<div class="form-group mb-3">
						<input type="submit" name="submit" class="btn btn-primary"
							value="Login" />
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>