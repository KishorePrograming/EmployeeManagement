package Structures;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;

public class DbConnection {

	static Connection con;
	private String db;

	public String getDb() {
		return db;
	}

	public static Connection createDBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("db conection");
			// if use need to run you can change dbName instead _ajinkya 
			String url = "jdbc:mysql://localhost:3306/ajinkya?useSSL=false";
			String user = "root";
			String pwd = "ROOT";
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("Connection Established");
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

	public static Connection useDBConnection(DbConnection db) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/" + db.getDb() + "?useSSL=false";
			System.out.println(url);
			final String user = "root";
			final String pwd = "ROOT";
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("Success using ");
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public static ArrayList<Connection> useDBConnection(ArrayList<String> astr,ArrayList<Connection> cons) {
		try {
			cons = new ArrayList<Connection>();
			Iterator<String> i = astr.iterator();
			while (i.hasNext() == true) {
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://localhost:3306/" + i.next() + "?useSSL=false";
				final String user = "root";
				final String pwd = "ROOT";
				con = DriverManager.getConnection(url, user, pwd);
				cons.add(con);
				System.out.println("Successfully using : "+ i.next());
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return cons;
	}
}
