package services.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DbConnection;
import models.ShoppingCart;

public class CartService {
	private static PreparedStatement preparedStatement;
	private static Connection connection;

	public static boolean addToCart(ShoppingCart c) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();

			String query = "INSERT INTO cart (id, name, price, quantity,total) VALUES(?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, c.getId());
			preparedStatement.setString(2, c.getName());
			preparedStatement.setDouble(3, c.getPrice());
			preparedStatement.setInt(4, c.getQuantity());
			preparedStatement.setDouble(5, c.getTotal());

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

	public static boolean increaseQty(int id) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();

			String query = "UPDATE cart c " +
                    "INNER JOIN books b ON c.id = b.id " +
                    "SET c.quantity = c.quantity + 1, c.total = b.price * (c.quantity + 1) " +
                    "WHERE c.id = ? AND b.quantity >= c.quantity + 1";
			
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

	public static boolean decreaseQty(int id) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();

			String query = "UPDATE cart SET quantity = quantity - 1, total = price * quantity WHERE id = ?";
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

	public static boolean deleteFromCart(int id) {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();

			String query = "DELETE FROM cart WHERE id = ?";
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
	
	public static boolean clearCartDb() {
		boolean result = false;

		try {
			connection = DbConnection.getDbConnection();

			String query = "DELETE FROM cart";
			preparedStatement = connection.prepareStatement(query);
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
