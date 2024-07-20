package services.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Cart;
import models.Employee;
import models.Order;
import models.User;
import utils.DbConnection;

public class CustomerService {
	private static Connection connection;
	private static PreparedStatement preparedStatement, orderItemStatement, quantityStatement, currentBooksStatement;
	private static ResultSet resultSet;
	
	public static boolean registerCustomer(User user) {
		boolean result = false;
		
		
		try {
			connection = DbConnection.getDbConnection();
			String query = "INSERT INTO users (first_name, last_name, address, phone, nic, email, password) VALUES(?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getAddress());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getNic());
			preparedStatement.setString(6, user.getEmail());
			preparedStatement.setString(7, user.getPassword());

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
	
	public static User loginUser(String email, String password) {
		User user = null;
		
		try {
			connection = DbConnection.getDbConnection();
			String query = "select * from users where email = ? and password = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setAddress(resultSet.getString("address"));
				user.setEmail(resultSet.getString("email"));
				user.setPhone(resultSet.getString("phone"));
				user.setNic(resultSet.getString("nic"));
				user.setPassword(resultSet.getString("password"));
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
		
		return user;
	}
	
	public static boolean makeAnOrder(Order order, ArrayList<Cart> cart) {
		boolean result = false;
		
		try {
			connection = DbConnection.getDbConnection();
			
			String orderItemQuery = "INSERT INTO order_items (order_id, book_id, quantity, price) VALUES(?,?,?,?)";
			orderItemStatement = connection.prepareStatement(orderItemQuery);
			
			String updateQuery = "UPDATE books SET quantity = quantity - ? WHERE id = ?";
			quantityStatement = connection.prepareStatement(updateQuery);
			
			String updateCurrentBooksQuery = "UPDATE current_books SET quantity = quantity - ? WHERE id = ?";
			currentBooksStatement = connection.prepareStatement(updateCurrentBooksQuery);
			
			String query = "INSERT INTO orders (customer_id, order_number, price, method, status, order_date) VALUES(?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			
			preparedStatement.setInt(1, order.getCustomerId());
			preparedStatement.setString(2, order.getOrderNumber());
			preparedStatement.setDouble(3, order.getPrice());
			preparedStatement.setString(4, order.getPaymentMethod());
			preparedStatement.setString(5, order.getStatus());
			preparedStatement.setDate(6, sqlDate);

			result = preparedStatement.executeUpdate() > 0;
			
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                
                for(Cart cartItem:cart) {
                	
                	double totPrice = cartItem.getQuantity() * cartItem.getPrice();
    				
    				orderItemStatement.setInt(1, orderId);
    				orderItemStatement.setInt(2, cartItem.getId());
    				orderItemStatement.setInt(3, cartItem.getQuantity());
    				orderItemStatement.setDouble(4, totPrice);
    				
    				orderItemStatement.executeUpdate();
    				
    				quantityStatement.setInt(1, cartItem.getQuantity());
    				quantityStatement.setInt(2, cartItem.getId());
    				
    				quantityStatement.executeUpdate();
    				
    				currentBooksStatement.setInt(1, cartItem.getQuantity());
    				currentBooksStatement.setInt(2, cartItem.getId());
    				
    				currentBooksStatement.executeUpdate();
    			}
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
	
	public static ArrayList<Order> getMyOrders(int customerId){
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try {
			connection = DbConnection.getDbConnection();
			String query = "select * from orders where customer_id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, customerId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Order order = new Order();
				order.setOrderId(resultSet.getInt("order_id"));
				order.setCustomerId(customerId);
				order.setOrderNumber(resultSet.getString("order_number"));
				order.setPrice(resultSet.getDouble("price"));
				order.setPaymentMethod(resultSet.getString("method"));
				order.setStatus(resultSet.getString("status"));
				orders.add(order);
				
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
		
		return orders;
	}
	
	public static boolean addToCart(Cart cart) {
		boolean result = false;
		
		
		
		return result;
	}
	
}
