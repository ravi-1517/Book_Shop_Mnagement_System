package services.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Order;
import models.Supplier;
import utils.DbConnection;

public class AdminService {

	private static PreparedStatement preparedStatement, preparedStatement1;
	private static Connection connection;
	private static ResultSet resultSet;

	public static boolean addSupplier(Supplier supplier) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			String query = "INSERT INTO suppliers (name, company, email,phone, address) VALUES(?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, supplier.getName());
			preparedStatement.setString(2, supplier.getCompany());
			preparedStatement.setString(3, supplier.getEmail());
			preparedStatement.setString(4, supplier.getPhone());
			preparedStatement.setString(5, supplier.getAddress());
			
			//to add values to current_supplier
			String query1 = "INSERT INTO current_suppliers (name, company, email,phone, address) VALUES(?,?,?,?,?)";
			preparedStatement1 = connection.prepareStatement(query1);
			preparedStatement1.setString(1, supplier.getName());
			preparedStatement1.setString(2, supplier.getCompany());
			preparedStatement1.setString(3, supplier.getEmail());
			preparedStatement1.setString(4, supplier.getPhone());
			preparedStatement1.setString(5, supplier.getAddress());
			

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

	public static ArrayList<Supplier> getAllSuppliers() {
		ArrayList<Supplier> suppliers = new ArrayList<Supplier>();

		try {
			connection = DbConnection.getDbConnection();
			String query = "select * from current_suppliers";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Supplier supplier = new Supplier();
				supplier.setId(resultSet.getInt("id"));
				supplier.setAddress(resultSet.getString("address"));
				supplier.setCompany(resultSet.getString("company"));
				supplier.setEmail(resultSet.getString("email"));
				supplier.setPhone(resultSet.getString("phone"));
				supplier.setName(resultSet.getString("name"));
				suppliers.add(supplier);
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

		return suppliers;
	}

	public static Supplier getSingleSupplier(int id) {
		Supplier supplier = null;

		try {
			connection = DbConnection.getDbConnection();
			String query = "select * from current_suppliers where id = ?";
			String query1 = "select * from suppliers where id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement1 = connection.prepareStatement(query1);
			preparedStatement1.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				supplier = new Supplier();
				supplier.setId(resultSet.getInt("id"));
				supplier.setAddress(resultSet.getString("address"));
				supplier.setCompany(resultSet.getString("company"));
				supplier.setEmail(resultSet.getString("email"));
				supplier.setPhone(resultSet.getString("phone"));
				supplier.setName(resultSet.getString("name"));
			}
			
			

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

		return supplier;
	}

	public static boolean updateSupplier(Supplier supplier) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			String query = "update current_suppliers set name=?, company=?, email=?, phone=?, address=? where id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, supplier.getName());
			preparedStatement.setString(2, supplier.getCompany());
			preparedStatement.setString(3, supplier.getEmail());
			preparedStatement.setString(4, supplier.getPhone());
			preparedStatement.setString(5, supplier.getAddress());
			preparedStatement.setInt(6, supplier.getId());
			
			int rowsAffectedCurrentSuppliers = preparedStatement.executeUpdate();
			
			String query1 = "update suppliers set name=?, company=?, email=?, phone=?, address=? where id = ?";
			preparedStatement1 = connection.prepareStatement(query1);
			preparedStatement1.setString(1, supplier.getName());
			preparedStatement1.setString(2, supplier.getCompany());
			preparedStatement1.setString(3, supplier.getEmail());
			preparedStatement1.setString(4, supplier.getPhone());
			preparedStatement1.setString(5, supplier.getAddress());
			preparedStatement1.setInt(6, supplier.getId());
			
			int rowsAffectedSuppliers = preparedStatement1.executeUpdate();
			
			result = rowsAffectedCurrentSuppliers > 0 && rowsAffectedSuppliers >0;

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

	public static boolean deleteSupplier(int id) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();
			String query = "delete from current_suppliers where id = ?";
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

	public static Supplier searchSupplier(String search_query) {
		Supplier supplier = null;

		try {
			connection = DbConnection.getDbConnection();
			String query = "select * from current_suppliers where name like ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%" + search_query + "%");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				supplier = new Supplier();
				supplier.setId(resultSet.getInt("id"));
				supplier.setAddress(resultSet.getString("address"));
				supplier.setCompany(resultSet.getString("company"));
				supplier.setEmail(resultSet.getString("email"));
				supplier.setPhone(resultSet.getString("phone"));
				supplier.setName(resultSet.getString("name"));
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

		return supplier;
	}
	
	public static ArrayList<Order> getAllOrders(){
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			connection = DbConnection.getDbConnection();
			String query = "select * from orders";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Order order = new Order();
				order.setOrderId(resultSet.getInt("order_id"));
				order.setOrderNumber(resultSet.getString("order_number"));
				order.setCustomerId(resultSet.getInt("customer_id"));
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
	
	public static boolean updateOrderStatus(int orderId, String status) {
		boolean result = false;
		
		try {
			connection = DbConnection.getDbConnection();
			String query = "update orders set status=? where order_id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, status);
			preparedStatement.setInt(2, orderId);
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
}