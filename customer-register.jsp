<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="includes/header.jsp" />
</head>
<body>
	<jsp:include page="includes/nav.jsp" />

	<div class="container-fluid h-100">
		<div class="row justify-content-center align-items-center h-100">
			<div class="col col-sm-6 col-md-6 col-lg-4 col-xl-3">
				<form action="RegisterServlet" class="mt-4" method="post">
				<h2>Sign up</h2>
					<div class="form-group mb-3">
						<label>First Name</label> 
						<input type="text" name="first_name" class="form-control" required />
					</div>
					<div class="form-group mb-3">
						<label>Last Name</label> 
						<input type="text" name="last_name" class="form-control" required />
					</div>
					<div class="form-group mb-3">
						<label>Address</label> 
						<input type="text" name="address" class="form-control" required />
					</div>
					<div class="form-group mb-3">
						<label>Phone</label> 
						<input type="text" name="phone" class="form-control" pattern="[0-9]{10}" required />
					</div>
					<div class="form-group mb-3">
						<label>NIC</label> 
						<input type="text" name="nic" class="form-control" required />
					</div>
					<div class="form-group mb-3">
						<label>Email</label> 
						<input type="email" name="email" class="form-control" required />
					</div>
					<div class="form-group mb-3">
						<label>Password</label> 
						<input type="password" name="password" class="form-control" required />
					</div>
					<div class="form-group mb-3">
						<input type="submit" name="submit" class="btn btn-primary"
							value="Register" />
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>