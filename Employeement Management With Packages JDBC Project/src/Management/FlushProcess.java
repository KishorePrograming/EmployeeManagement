package Management;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FlushProcess {
	private static Connection con;
	private static String sql;
	private static final Scanner sc = new Scanner(System.in);
	
	public static void processList() throws SQLException {
		int count = 0;
		ArrayList<Connection> adb = null;
		ArrayList<Integer> aint = new ArrayList<Integer>();
		ArrayList<String> astr = new ArrayList<String>();
		while (true) {
			System.out.println("1. show Process list\n2. delete processlist\n3.exit");
			String s = sc.next();
			if (s.equals("1")) {
				showProcessList(astr,aint);
				count++;
			} else if (s.equals("2")) {
				deleteProcessList(astr,adb,aint);
			} else if (s.equals("3")) {
				if (count == 0) {
					System.out.println("Thank you..");
					return;
				} else if (count > 0) {
					System.out.println("Updation completed..");
					return;
				}
			}
		}
	}
	private static void showProcessList(ArrayList<String> astr, ArrayList<Integer> aint) {
		try {
			con = DbConnection.createDBConnection();
			sql = "SHOW FULL PROCESSLIST;";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				System.out.println("______________________________________");
				System.out.println("ID       : " + rs.getInt(1));
				System.out.println("DATABASE : " + rs.getString(4));
				System.out.println("Command  : " + rs.getString(5));
				System.out.println("======================================");
				aint.add(rs.getInt(1));
				astr.add(rs.getString(4));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private static void deleteProcessList(ArrayList<String> astr, ArrayList<Connection> adb, ArrayList<Integer> aint) throws SQLException {
		con = DbConnection.createDBConnection();
		adb = Structures.DbConnection.useDBConnection(astr,adb);
		while(true) {
			System.out.println("1. Enter id and delete\n2. Enter start id and end id delete between\n3.exit");
			String s = sc.next();
			if(s.equals("1")) {
				System.out.println("Enter first id : ");
				int start = sc.nextInt();
				sql = "KILL "+start+";";
				Statement st = con.createStatement();
				System.out.println("Deleted row "+st.executeUpdate(sql));
			}else if(s.equals("2")) {
				Iterator<Connection> id = adb.iterator();
				int i=0;
				while (id.hasNext() == true) {
					sql = "KILL "+aint.get(i)+";";
					Statement st = id.next().createStatement();
					int rows = st.executeUpdate(sql);
					System.out.println("Deleted row : "+rows);
					i++;				
			}
			}
			else if(s.equals("3")) {
				System.out.println("Thanks for using our fecility..");
				return;
			}else {
				System.out.println("Please enter valid input..");
			}
		}
	}
	
}
