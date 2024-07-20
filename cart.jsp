<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="models.Book"%>
<%@ page import="models.Cart" %>
<%@ page import="models.User" %>
<% double subTotal = 0; %>
<% User user = (User)session.getAttribute("user"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart</title>
<jsp:include page="includes/header.jsp" />
</head>
<body>
	<jsp:include page="includes/nav.jsp" />

	<section class="py-5">
		<div class="container px-4 px-lg-5">
			<h3 class="">SHOPPING CART</h3>
			<div
				class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
				<% ArrayList<Cart> cart = (ArrayList<Cart>)session.getAttribute("cart"); %>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Id</th>
							<td>Name</td>
							<td>Price</td>
							<td>Quantity</td>
							<td>Total</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<% if (cart != null && !cart.isEmpty()) { %>
						<% for(Cart cartItem: cart){ %>
						<tr>
							<td><%=cartItem.getId() %></td>
							<td><%=cartItem.getBookName() %></td>
							<td><%=cartItem.getPrice() %></td>
							<td>
									<a href="DecQty?id=<%=cartItem.getId() %>" class="btn btn-outline-secondary me-2">-</a>
									<%=cartItem.getQuantity() %>
									<a href="IncQty?id=<%=cartItem.getId() %>" class="btn btn-outline-secondary ms-2">+</a>
							</td>
							<% subTotal += cartItem.getPrice() * cartItem.getQuantity(); %>
							<td><%=cartItem.getPrice() * cartItem.getQuantity() %></td>
							<td> <a href="RemoveBook?id=<%=cartItem.getId() %>" class="btn btn-danger">Remove</a> </td>
						</tr>
						<%}} %>
					</tbody>
				</table>

				
			</div>
			<% if(user!=null){ %>
			<a href="GoToCheckoutServlet" class="mt-4 btn btn-success">Sub total - Rs <%=subTotal %>0  </a>
			<%} %>
				
		</div>
	</section>
</body>
</html>