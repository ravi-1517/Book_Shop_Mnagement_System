<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="models.Book"%>
<%@ page import="models.Cart"%>
<%
String contextPath = request.getContextPath();
Book book = (Book) request.getAttribute("book");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book details</title>
<jsp:include page="includes/header.jsp" />
</head>
<body>
	<jsp:include page="includes/nav.jsp" />


	<section class="py-5">
		<div class="container px-4 px-lg-5 my-5">
			<div class="row gx-4 gx-lg-5 align-items-center">
				<div class="col-md-6">
					<img class="card-img-top mb-5 mb-md-0"
						src="<%=contextPath%>/uploads/<%=book.getImage()%>" alt="..." />
				</div>
				<div class="col-md-6">
					<div class="small mb-1">
						<b>ISBN:</b>
						<%=book.getIsbn()%></div>
					<div class="small mb-1">
						<b>Author:</b>
						<%=book.getAuthor()%></div>
					<div class="small mb-1">
						<b>Publisher:</b>
						<%=book.getPublisher()%></div>
					<h1 class="display-5 fw-bolder"><%=book.getBookName()%></h1>
					<div class="fs-5 mb-5">
						<span>Rs. <%=book.getPrice()%></span>
					</div>
					<p class="lead"><%=book.getDescription()%></p>
					<div class="d-flex">
						<a class="btn btn-warning mt-auto"
							href="CustomerAddToCartServlet?id=<%=book.getId()%>"
							onclick="return addToCart()">Add to
							cart</a>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script>
    function addToCart() {
        alert('Book  added to cart successfully!');
        return true;
    }
</script>
</html>