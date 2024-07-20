package controllers.employee;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Book;
import models.Employee;
import services.employee.EmployeeService;

/**
 * Servlet implementation class EmployeeSendMessageToAdmin
 */
@WebServlet("/EmployeeSendMessageToAdmin")
public class EmployeeSendMessageToAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeSendMessageToAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		request.setAttribute("adminMsg", "Book details are send to the admin");
		ArrayList<Book> books = EmployeeService.getLowQtyBooks();
		request.setAttribute("books", books);
		request.setAttribute("msg", "lowQty");
		session.setAttribute("lowQtyBooks", books);
		EmployeeService.lowBooks(books);
		request.getRequestDispatcher("WEB-INF/employee/index.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
