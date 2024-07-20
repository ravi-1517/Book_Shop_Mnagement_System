package services.admin;

public class AdminAuthService {
	
	public static boolean authenticateAdmin(String username, String password) {
		return username.equals("admin") && password.equals("admin");
	}
	
}
