package Structures;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Creatingss {
	private static final Scanner sc = new Scanner(System.in);
	private static String sql;
	private static int affect;
	private static boolean check;
	private static Connection con;
	static DbConnection db;

	@SuppressWarnings("static-access")
	public static Connection CreateDatabase() throws SQLException {
		Connection con = DbConnection.createDBConnection();
//		System.out.println("Enter Database Name ");
		db = new DbConnection();
		db.setDb("EmployeeDb");
		sql = "CREATE DATABASE " + db.getDb();
		Statement st = con.createStatement();
		affect = st.executeUpdate(sql);
		check = (affect > 0) ? true : false;
		System.out.println("Database is " + ((check == true) ? "created" : "Not created"));
		check = false;
		con = db.useDBConnection(db);
		CreateTableEmployee(con, db);
		CreateTableAdmin(con, db);
		CreateTableMaintanance(con, db);
		return con;
	}

	public static void CreateTableEmployee(Connection con, DbConnection db) throws SQLException {
		sql = "CREATE TABLE employee(id INT,NAME VARCHAR(55),age INT,Address VARCHAR(200),salary INT,POSITION VARCHAR(30));";
		System.out.println(sql);
		Statement st = con.createStatement();
		affect = st.executeUpdate(sql);
		check = (affect == 0) ? true : false;
		System.out.println(affect);
		System.out.println("Employee Table is " + ((check == true) ? "created" : "Not created"));
		check = false;
	}

	private static void CreateTableAdmin(Connection con, DbConnection db) throws SQLException {
		sql = "CREATE TABLE Admin(id INT PRIMARY KEY,NAME VARCHAR(30),PASSWORD VARCHAR(200));";
		Statement st = con.createStatement();
		affect = st.executeUpdate(sql);
		check = (affect == 0) ? true : false;
		System.out.println("Admin Table is " + ((check == true) ? "created" : "Not created"));
		check = false;
		System.out.println("How many Admin do you want in your Database :");
		int count = sc.nextInt();
		while (count > 0) {
			System.out.println("Enter Id ");
			String id = sc.next();
			System.out.println("Enter the correct Name : ");
			String Name = sc.next();
			System.out.println("Enter the Password : ");
			String Password = sc.next();
			sql = "INSERT INTO Admin VALUES(" + id + ", '" + Name + "', +'" + Password + "');";
			st = con.createStatement();
			affect = st.executeUpdate(sql);
			check = (affect > 0) ? true : false;
			System.out.println("Admin user is" + ((check == true) ? " Updated." : " Not Updated."));
			check = false;
			count--;
		}
	}

	private static void CreateTableMaintanance(Connection con, DbConnection db) throws SQLException {
		sql = "CREATE TABLE Maintanance(id INT PRIMARY KEY,NAME VARCHAR(30),PASSWORD VARCHAR(30),Access VARCHAR(30));";
		Statement st = con.createStatement();
		affect = st.executeUpdate(sql);
		check = (affect == 0) ? true : false;
		System.out.println("Maintanance Table is " + ((check == true) ? "created" : "Not created"));
		check = false;
		System.out.println("How many Maintanance Employee do you want in your Database :");
		int count = sc.nextInt();
		while (count > 0) {
			System.out.println("Enter Id ");
			String id = sc.next();
			System.out.println("Enter the correct Name : ");
			String Name = sc.next();
			System.out.println("Enter the Password : ");
			String Password = sc.next();
			String Access = "yes";
			sql = "INSERT INTO Maintanance VALUES(" + id + ", '" + Name + "', '" + Password + "', '" + Access + "');";
			st = con.createStatement();
			affect = st.executeUpdate(sql);
			check = (affect > 0) ? true : false;
			System.out.println("Maintanance user is" + ((check == true) ? " Updated." : " Not Updated."));
			if (check == false) {
				System.out.println("Sorry for inconvinones");
				System.gc();
				System.exit(0);
			}
			check = false;
			count--;
		}
	}

	public static void main(String[] args) {
		while (true) {
			System.out.println("Do you want to create all tables 1. Yes 2.No");
			String ch = sc.next();
			if (ch.equals("1") || ch.equalsIgnoreCase("Yes")) {
				try {
					con = CreateDatabase();
					con.close();
					break;
				} catch (Exception e) {
					System.out.println(e);
				}
			} else if (ch.equals("2") || ch.equalsIgnoreCase("No")) {
				System.out.println("Thanks for using our fecility...");
			} else {
				System.out.println("Invalid input please try again..");
			}
		}
	}
}
