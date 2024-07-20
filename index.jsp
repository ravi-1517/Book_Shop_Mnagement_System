<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="models.Book"%>
<%@ page import="models.Cart" %>
<%
  String contextPath = request.getContextPath();
  String selectedPublisher = request.getParameter("publisher");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Shop</title>
<jsp:include page="includes/header.jsp" />
<style>
/* CSS styles go here */
.py-5 {
  background-image: url("https://t4.ftcdn.net/jpg/01/46/40/35/360_F_146403506_381OxD624hppWMQcIbjLuSr8TCW1q2lj.jpg");
  background-size: cover;
  background-position: center;
}




.drop {
  background-image: url("https://img.freepik.com/premium-photo/old-books-wooden-table-education_220873-10039.jpg?w=826");
  background-size: cover;
  background-position: center;
}
</style>
</head>
<body>
	<jsp:include page="includes/nav.jsp" />

	<header class="bg-dark py-2">
		<div class="container px-4 px-lg-5 my-5">
			<div class="text-center text-white">
				<h1 class="display-4 fw-bolder">Eramanis Book shop</h1>
				<p class="lead fw-normal text-white-50 mb-0">Buy any book</p>
			</div>
		</div>
	</header>
	
	<!-- Dropdown -->
	<div class="drop ">
		<div class="row justify-content-left px-2 px-lg-2 mt-2">
			<div class="col-md-2">
				<form action="" method="GET">
					<div class="form-group">
						
						<select class="form-control" id="publisher" name="publisher" onchange="this.form.submit()">
							<option value="All" <% if (selectedPublisher == null || selectedPublisher.equals("All")) { %>selected<% } %>>All</option>
							<option value="Novels" <% if (selectedPublisher != null && selectedPublisher.equals("Novels")) { %>selected<% } %>>Novels</option>
							<option value="Translations" <% if (selectedPublisher != null && selectedPublisher.equals("Translations")) { %>selected<% } %>>Translations</option>
							<option value="Kids Books" <% if (selectedPublisher != null && selectedPublisher.equals("Kids Books")) { %>selected<% } %>>Kids Books</option>
							<option value="Short stories" <% if (selectedPublisher != null && selectedPublisher.equals("Short stories")) { %>selected<% } %>>Short stories</option>
						</select>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	

	<section class="py-5">
		<div class="container px-1 px-lg-2">
			<div
				class="row gx-5 gx-lg-2 row-cols-2 row-cols-md-3 row-cols-xl-5 justify-content-center">
				
				<% ArrayList<Book> books = (ArrayList<Book>)request.getAttribute("books"); %>
				
				<% if (books != null && !books.isEmpty()) { %>
					<% for(Book book: books){ %>
						<% if (selectedPublisher == null || selectedPublisher.equals("All") || selectedPublisher.equals(book.getPublisher())) { %>
							<div class="col mb-5">
								<div class="card h-100">
									<img style="width: 100%; height: 200px; object-fit: cover;" class="card-img-top" src="<%=contextPath %>/uploads/<%=book.getImage()%>" alt="..." />
									<div class="card-body p-4">
										<div class="text-center">
											<!-- Product name-->
											<h5 class="fw-bolder" style="text-transform: uppercase;">
												<%=book.getBookName() %></h5>
											<div><%=book.getPublisher() %> </div>
											
											<!-- Product price-->
											Rs. <%=book.getPrice() %>0
										</div>
									</div>
									<!-- Product actions-->
									<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
									<!-- Check if quantity is greater than 0 -->
                					<% if (book.getQuantity() > 0) { %>
                    					<!-- Display "View" link -->
                    					<div class="text-center">
											<a href="GetBookDetailsServlet?id=<%=book.getId() %>" class="btn btn-outline-secondary">View</a>
										</div>
                					<% } else { %>
                   				 		<!-- Display "Out of Stock" -->
                    					<div class="text-center text-danger">Out of Stock</div>
                					<% } %>
									</div>
								</div>
							</div>
						<% } %>
					<% } %>
				<% } %>
				
			</div>
		</div>
	</section>
</body>
</html>
