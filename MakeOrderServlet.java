package controllers.customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Cart;
import models.Order;
import models.ShoppingCart;
import models.User;
import services.cart.CartService;
import services.customer.CustomerService;

/**
 * Servlet implementation class MakeOrderServlet
 */
@WebServlet("/MakeOrderServlet")
public class MakeOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MakeOrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	private boolean isValidEmail(String email) {
		// Add email validation logic here
		return email.contains("@");
	}

	private boolean isValidPhone(String phone) {
		// Add phone validation logic here
		return phone.matches("\\d{10}");
	}

	private boolean isValidCardNumber(String cardNumber) {
		// Add card number validation logic here
		return cardNumber.matches("\\d{16}");
	}

	private boolean isValidExp(String exp) {
		// Add expiration date validation logic here
		return exp.matches("\\d{2}/\\d{2}");
	}

	private boolean isValidCVV(String cvv) {
		// Add CVV validation logic here
		return cvv.matches("\\d{3}");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ArrayList<Cart> cart = (ArrayList<Cart>) session.getAttribute("cart");

		List<String> errors = new ArrayList<>();
		
		double totalPrice = 0;

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String method = request.getParameter("method");

		String nameCard = request.getParameter("name_card");
		String cardNumber = request.getParameter("card_number");
		String exp = request.getParameter("exp");
		String cvv = request.getParameter("cvv");

		if (firstName == null || firstName.isEmpty()) {
			errors.add("First name is required.");
		}

		// Validate last name
		if (lastName == null || lastName.isEmpty()) {
			errors.add("Last name is required.");
		}

		// Validate email
		if (email == null || email.isEmpty() || !isValidEmail(email)) {
			errors.add("Invalid email address.");
		}

		// Validate phone
		if (phone == null || phone.isEmpty() || !isValidPhone(phone)) {
			errors.add("Invalid phone number.");
		}

		// Validate address
		if (address == null || address.isEmpty()) {
			errors.add("Address is required.");
		}

		if (method.equals("card")) {
			// Validate name on card
			if (nameCard == null || nameCard.isEmpty()) {
				errors.add("Name on card is required.");
			}

			// Validate card number
			if (cardNumber == null || cardNumber.isEmpty() || !isValidCardNumber(cardNumber)) {
				errors.add("Invalid card number.");
			}

			// Validate expiration date
			if (exp == null || exp.isEmpty() || !isValidExp(exp)) {
				errors.add("Invalid expiration date.");
			}

			// Validate CVV
			if (cvv == null || cvv.isEmpty() || !isValidCVV(cvv)) {
				errors.add("Invalid CVV.");
			}
		}

		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("WEB-INF/checkout.jsp").forward(request, response);
		} else {
			UUID uuid = UUID.randomUUID();
			Order order = new Order();
			
			for(Cart cartItem:cart) {
				totalPrice += cartItem.getQuantity() * cartItem.getPrice();
				


			}

			String orderNumber = uuid.toString().substring(0,8);
			int customerId = user.getId();

			order.setCustomerId(customerId);
			order.setOrderNumber(orderNumber);
			order.setPrice(totalPrice);
			order.setPaymentMethod(method);
			order.setStatus("Pending");
			
			boolean result = CustomerService.makeAnOrder(order, cart);
			
			
			if(result==true) {
				CartService.clearCartDb();
				session.removeAttribute("cart");
				request.setAttribute("orderNumber", orderNumber);
				request.getRequestDispatcher("WEB-INF/order-confirm.jsp").forward(request, response);
			}else {
				response.sendRedirect("error.jsp");
			}
			
	

		}

	}

}
