package services.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Book;
import models.Employee;
import models.TopCustomers;
import utils.DbConnection;

public class EmployeeService {
	private static PreparedStatement preparedStatement,preparedStatement1;
	private static Connection connection;
	private static ResultSet resultSet;

	public static boolean registerEmployee(Employee employee) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			String query = "INSERT INTO employees (username, email, password) VALUES(?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, employee.getUsername());
			preparedStatement.setString(2, employee.getEmail());
			preparedStatement.setString(3, employee.getPassword());
			
			result = preparedStatement.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static Employee loginEmployee(String username, String password) {
		Employee employee = null;

		try {
			connection = DbConnection.getDbConnection();
			String query = "select * from employees where username = ? and password = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				employee = new Employee();
				employee.setId(resultSet.getInt("id"));
				employee.setUsername(resultSet.getString("username"));
				employee.setEmail(resultSet.getString("email"));
				employee.setPassword(resultSet.getString("password"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return employee;
	}

	public static boolean addNewBook(Book book) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			String query = "INSERT INTO books (name, price, quantity,supplier, description,image,author, publisher, isbn) VALUES(?,?,?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, book.getBookName());
			preparedStatement.setDouble(2, book.getPrice());
			preparedStatement.setInt(3, book.getQuantity());
			preparedStatement.setInt(4, book.getSupplier());
			preparedStatement.setString(5, book.getDescription());
			preparedStatement.setString(6, book.getImage());
			preparedStatement.setString(7, book.getAuthor());
			preparedStatement.setString(8, book.getPublisher());
			preparedStatement.setString(9, book.getIsbn());
			
			String query1 = "INSERT INTO current_books (name, price, quantity,supplier, description,image,author, publisher, isbn) VALUES(?,?,?,?,?,?,?,?,?)";
			preparedStatement1 = connection.prepareStatement(query1);
			preparedStatement1.setString(1, book.getBookName());
			preparedStatement1.setDouble(2, book.getPrice());
			preparedStatement1.setInt(3, book.getQuantity());
			preparedStatement1.setInt(4, book.getSupplier());
			preparedStatement1.setString(5, book.getDescription());
			preparedStatement1.setString(6, book.getImage());
			preparedStatement1.setString(7, book.getAuthor());
			preparedStatement1.setString(8, book.getPublisher());
			preparedStatement1.setString(9, book.getIsbn());

			result = preparedStatement.executeUpdate() > 0 && preparedStatement1.executeUpdate() >0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (preparedStatement1 !=null) {
					preparedStatement1.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = new ArrayList<>();

		try {
			connection = DbConnection.getDbConnection();
			String query = "select b.id , b.name, b.price, b.quantity , b.description, b.image, b.author, b.publisher, b.isbn, s.name as 'sup_name' from current_books b , suppliers s where b.supplier = s.id ";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Book book = new Book();
				book.setId(resultSet.getInt("id"));
				book.setBookName(resultSet.getString("name"));
				book.setPrice(resultSet.getDouble("price"));
				book.setQuantity(resultSet.getInt("quantity"));
				book.setSupplierName(resultSet.getString("sup_name"));
				book.setDescription(resultSet.getString("description"));
				book.setImage(resultSet.getString("image"));
				book.setAuthor(resultSet.getString("author"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setIsbn(resultSet.getString("isbn"));
				books.add(book);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}

	public static boolean deleteBook(int id) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			String query = "delete from current_books where id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static Book getSingleBook(int id) {
		Book book = null;

		try {
			connection = DbConnection.getDbConnection();
			String query = "select * from current_books where id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			
			String query1 = "select * from books where id = ?";
			preparedStatement1 = connection.prepareStatement(query1);
			preparedStatement1.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				book = new Book();
				book.setId(resultSet.getInt("id"));
				book.setBookName(resultSet.getString("name"));
				book.setPrice(resultSet.getDouble("price"));
				book.setQuantity(resultSet.getInt("quantity"));
				book.setImage(resultSet.getString("image"));
				book.setDescription(resultSet.getString("description"));
				book.setAuthor(resultSet.getString("author"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setIsbn(resultSet.getString("isbn"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return book;
	}

	public static boolean updateBook(Book book) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			String query = "update current_books set name=?, price=?, quantity=?, supplier=?, description=?, image=?, author = ?, publisher = ?, isbn = ? where id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, book.getBookName());
			preparedStatement.setDouble(2, book.getPrice());
			preparedStatement.setInt(3, book.getQuantity());
			preparedStatement.setInt(4, book.getSupplier());
			preparedStatement.setString(5, book.getDescription());
			preparedStatement.setString(6, book.getImage());
			preparedStatement.setString(7, book.getAuthor());
			preparedStatement.setString(8, book.getPublisher());
			preparedStatement.setString(9, book.getIsbn());
			preparedStatement.setInt(10, book.getId());
			
			String query1 = "update books set name=?, price=?, quantity=?, supplier=?, description=?, image=?, author = ?, publisher = ?, isbn = ? where id = ?";
			preparedStatement1 = connection.prepareStatement(query1);
			preparedStatement1.setString(1, book.getBookName());
			preparedStatement1.setDouble(2, book.getPrice());
			preparedStatement1.setInt(3, book.getQuantity());
			preparedStatement1.setInt(4, book.getSupplier());
			preparedStatement1.setString(5, book.getDescription());
			preparedStatement1.setString(6, book.getImage());
			preparedStatement1.setString(7, book.getAuthor());
			preparedStatement1.setString(8, book.getPublisher());
			preparedStatement1.setString(9, book.getIsbn());
			preparedStatement1.setInt(10, book.getId());
			result = preparedStatement.executeUpdate() > 0 && preparedStatement1.executeUpdate() >0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (preparedStatement1 != null) {
					preparedStatement1.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static ArrayList<Book> getLowQtyBooks() {
		ArrayList<Book> books = new ArrayList<Book>();
		try {
			connection = DbConnection.getDbConnection();
			String query = "select b.id , b.name, b.price, b.quantity , b.description, b.image, b.author, b.publisher, b.isbn, s.name as 'sup_name' from books b JOIN suppliers s ON b.supplier = s.id JOIN current_books cb ON b.id = cb.id where b.quantity < 5";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Book book = new Book();
				book.setId(resultSet.getInt("id"));
				book.setBookName(resultSet.getString("name"));
				book.setPrice(resultSet.getDouble("price"));
				book.setQuantity(resultSet.getInt("quantity"));
				book.setSupplierName(resultSet.getString("sup_name"));
				book.setImage(resultSet.getString("image"));
				book.setAuthor(resultSet.getString("author"));
				book.setPublisher(resultSet.getString("publisher"));
				book.setDescription(resultSet.getString("description"));
				book.setIsbn(resultSet.getString("isbn"));
				books.add(book);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}

	public static boolean lowBooks(ArrayList<Book> books) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();

			for (Book book : books) {
				String query = "INSERT INTO low_books (name, quantity, sup_name, isbn) VALUES (?,?,?,?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, book.getBookName());
				preparedStatement.setInt(2, book.getQuantity());
				preparedStatement.setString(3, book.getSupplierName());
				preparedStatement.setString(4, book.getIsbn());
				result = preparedStatement.executeUpdate() > 0;

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static ArrayList<Book> lowBooksReq() {
		ArrayList<Book> books = new ArrayList<Book>();
		try {
			connection = DbConnection.getDbConnection();
			String query = "SELECT * FROM low_books";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Book book = new Book();
				book.setId(resultSet.getInt("id"));
				book.setBookName(resultSet.getString("name"));
				book.setQuantity(resultSet.getInt("quantity"));
				book.setSupplierName(resultSet.getString("sup_name"));
				book.setIsbn(resultSet.getString("isbn"));
				books.add(book);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return books;
	}
	
	public static ArrayList<Book> getBookReport(){
		ArrayList<Book> books = new ArrayList<Book>();
		
		try {
			connection = DbConnection.getDbConnection();
			String query = "SELECT b.id, b.name, b.supplier, SUM(oi.quantity) AS total_quantity, b.price, s.name AS sup_name FROM orders o INNER JOIN order_items oi ON o.order_id = oi.order_id INNER JOIN books b ON oi.book_id = b.id INNER JOIN suppliers s ON b.supplier = s.id WHERE MONTH(o.order_date) = MONTH(CURRENT_DATE()) AND YEAR(o.order_date) = YEAR(CURRENT_DATE()) GROUP BY b.id, b.name, b.supplier ORDER BY total_quantity DESC";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Book book = new Book();
				book.setId(resultSet.getInt("id"));
				book.setBookName(resultSet.getString("name"));
				book.setQuantity(resultSet.getInt("total_quantity"));
				book.setPrice(resultSet.getDouble("price"));
				book.setSupplierName(resultSet.getString("sup_name"));
				books.add(book);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return books;
	}
	
	public static ArrayList<TopCustomers> getTopCustomers(){
		ArrayList<TopCustomers> customers = new ArrayList<TopCustomers>();
		
		try {
			connection = DbConnection.getDbConnection();
			String query = "SELECT o.customer_id, u.first_name, u.last_name, SUM(oi.quantity) AS total_quantity, SUM(oi.price) AS total_sales FROM orders o INNER JOIN order_items oi ON o.order_id = oi.order_id INNER JOIN users u ON u.id = o.customer_id WHERE MONTH(o.order_date) = MONTH(CURRENT_DATE()) AND YEAR(o.order_date) = YEAR(CURRENT_DATE()) GROUP BY o.customer_id ORDER BY total_quantity DESC LIMIT 10";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				TopCustomers topCustomers = new TopCustomers();
				topCustomers.setId(resultSet.getInt("customer_id"));
				topCustomers.setName(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
				topCustomers.setQuantity(resultSet.getInt("total_quantity"));
				topCustomers.setTotal(resultSet.getDouble("total_sales"));
				
				customers.add(topCustomers);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return customers;
	}

}