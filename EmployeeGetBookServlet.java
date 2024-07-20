package controllers.employee;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Book;
import models.Supplier;
import services.admin.AdminService;
import services.employee.EmployeeService;

/**
 * Servlet implementation class EmployeeGetBookServlet
 */
@WebServlet("/EmployeeGetBookServlet")
public class EmployeeGetBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeGetBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book book = EmployeeService.getSingleBook(id);
		ArrayList<Supplier> suppliers = AdminService.getAllSuppliers();
		request.setAttribute("book", book);
		request.setAttribute("suppliers", suppliers);
		request.getRequestDispatcher("WEB-INF/employee/update-book.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
