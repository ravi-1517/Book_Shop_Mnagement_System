package controllers.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Supplier;
import services.admin.AdminService;

/**
 * Servlet implementation class AdminSearchSupplierServlet
 */
@WebServlet("/AdminSearchSupplierServlet")
public class AdminSearchSupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSearchSupplierServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("search_supplier");
		Supplier supplier = AdminService.searchSupplier(query);
		ArrayList<Supplier> search_result = new ArrayList<Supplier>();
		search_result.add(supplier);
		
		if(supplier != null) {
			request.setAttribute("suppliers", search_result);
			request.getRequestDispatcher("WEB-INF/admin/index.jsp").forward(request, response);
		}else {
//			request.setAttribute("suppliers", );
			request.getRequestDispatcher("WEB-INF/admin/index.jsp").forward(request, response);
		}
	}

}
