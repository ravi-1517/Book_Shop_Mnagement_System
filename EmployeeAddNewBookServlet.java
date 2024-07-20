package controllers.employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.Book;
import services.employee.EmployeeService;

/**
 * Servlet implementation class EmployeeAddNewBookServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)
@WebServlet("/EmployeeAddNewBookServlet")
public class EmployeeAddNewBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeAddNewBookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
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

	private boolean isValidISBN(String isbn) {
		String regexISBN10 = "^\\d{9}[0-9X]$";
		String regexISBN13 = "^(?:\\d{13}|\\d{9}X)$";

		Pattern patternISBN10 = Pattern.compile(regexISBN10);
		Pattern patternISBN13 = Pattern.compile(regexISBN13);

		Matcher matcherISBN10 = patternISBN10.matcher(isbn);
		Matcher matcherISBN13 = patternISBN13.matcher(isbn);

		return matcherISBN10.matches() || matcherISBN13.matches();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
		ArrayList<String> errors = new ArrayList<String>();

		final Part filePart = request.getPart("image");
		final String fileName = getFileName(filePart);
		String imagePath = null;

		Book book = new Book();

		OutputStream out = null;
		InputStream filecontent = null;

		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			out = new FileOutputStream(new File(uploadPath + File.separator + fileName));
			imagePath = uploadPath + File.separator + fileName;
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

		} catch (FileNotFoundException fne) {

		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}

		}

		String name = request.getParameter("book_name");
		Double price = Double.parseDouble(request.getParameter("price"));
		String author = request.getParameter("author");
		String publisher = request.getParameter("publisher");
		String isbn = request.getParameter("isbn");
		int quantity = Integer.parseInt(request.getParameter("qty"));
		int supplier = Integer.parseInt(request.getParameter("supplier"));
		String description = request.getParameter("description");

		if (!isValidISBN(isbn)) {
			errors.add("Invalid ISBN");
		}

		if (errors.size() > 0) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("WEB-INF/employee/index.jsp").forward(request, response);
		} else {
			book.setBookName(name);
			book.setPrice(price);
			book.setQuantity(quantity);
			book.setSupplier(supplier);
			book.setDescription(description);
			book.setImage(fileName);
			book.setAuthor(author);
			book.setPublisher(publisher);
			book.setIsbn(isbn);

			boolean result = EmployeeService.addNewBook(book);

			if (result == true) {
				response.sendRedirect("EmployeeNavigationServlet?to=EmpHome");
			} else {
				response.sendRedirect("EmployeeNavigationServlet?to=NewBook");
			}
		}

	}

}
