package Management;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

	static Connection con;

	public static Connection createDBConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			final String url = "jdbc:mysql://localhost:3306/EmployeeDb?useSSL=false";
			final String user = "root";
			final String pwd = "ROOT";
			con = DriverManager.getConnection(url, user, pwd);
		}catch(Exception e) {
			System.out.println(e);
		}
		return con;
	}
}
