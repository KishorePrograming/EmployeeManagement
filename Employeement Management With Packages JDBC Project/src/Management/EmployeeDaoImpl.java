package Management;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Employee.Employee;

public class EmployeeDaoImpl implements EmployeeDaoInterface {
	static final Scanner sc = new Scanner(System.in);
	private static String sql;

	public static boolean passwordCheck(String str, Employee emp, Connection con, Statement st) {
		boolean check = false;
		try {
			System.out.println("Please enter the id : ");
			String id = sc.next();
			int i = Integer.parseInt(id);
			emp.setId(i);
			System.out.println("Enter the password : ");
			String pwd = sc.next();
			sql = "Select * from " + str + " ;";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				if (str.equalsIgnoreCase("Admin")) {
					String s = Integer.toString(rs.getInt(1));
					if (s.equals(id) && rs.getString(3).equals(pwd)) {
						check = true;
					}
				} else if (str.equals("maintanance")) {
					String s1 = Integer.toString(rs.getInt(1));
					if (s1.equals(id) && rs.getString(3).equals(pwd)) {
						check = true;
						AcessCheck(id, str, st, con);
					}
				} else if (str.equalsIgnoreCase("Admin")) {
					String s = Integer.toString(rs.getInt(1));
					if (s.equals(id) && rs.getString(2).equals(pwd)) {
						check = true;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return check;
	}

	private static void AcessCheck(String id, String d, Statement st, Connection con) {
		try {
			sql = "Select * from " + d + " ;";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String s = Integer.toString(rs.getInt(1));
				if (s.equals(id) && rs.getString(4).equalsIgnoreCase("yes")) {
					System.out.println(rs.getString(4)+" You have access..");
					break;
				} else if (s.equals(id) && rs.getString(4).equalsIgnoreCase("No")) {
					System.out.println("You don't have access sorry please reach Admin");
					System.gc();
					System.exit(0);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void createEmployee(Employee emp, Connection con, Statement st) {
		try {
			sql = "INSERT INTO employee VALUES(" + emp.getId() + ", '" + emp.getName() + "', " + emp.getAge() + ", '"
					+ emp.getAdress() + "', " + emp.getSalary() + ", '" + emp.getPosition() + "'); ";
			st = con.createStatement();
			int row = st.executeUpdate(sql);
			boolean check = row > 0 ? true : false;
			System.out.println("Employee is" + (check == true ? " " : " Not ") + "Updated Successfully...");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void showAllEmployee(Connection con, Statement st) {
		sql = "Select * from Employee ;";
		try {
			st = con.createStatement();
			view(st, sql, con);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	private void view(Statement st, String sql, Connection con) {
		try {
			ResultSet rs = st.executeQuery(sql);
//		System.out.format("%s\t%s\t%s\t%s\t%s\t%s", "ID", "NAME", "AGE", "ADDRESS", "SALARY", "POSITION");
			while (rs.next()) {
//			(id INT,NAME VARCHAR(55),age INT,Address VARCHAR(200),salary INT,POSITION VARCHAR(30));
//			System.out.format("\n%d\t%s\t%d\t%s\t%d\t%s", rs.getInt(1), rs.getString(2), rs.getInt(3),
//					rs.getString(4), rs.getInt(5), rs.getString(6) + "\n");
				System.out.println("______________________________________");
				System.out.println("Employee " + rs.getString(2) + " Given Below");
				System.out.println("______________________________________");
				System.out.println("ID       : " + rs.getInt(1));
				System.out.println("NAME     : " + rs.getString(2));
				System.out.println("AGE      : " + rs.getInt(3));
				System.out.println("ADDRESS  : " + rs.getString(4));
				System.out.println("SALARY   : " + rs.getInt(5));
				System.out.println("POSITION : " + rs.getString(6));
				System.out.println("======================================");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void showBasedOnIdEmployee(Connection con, Statement st) {
		try {
			System.out.println("Enter Employee id : ");
			int empid = sc.nextInt();
			sql = "select * from employee where id =" + empid;
			st = con.createStatement();
			view(st, sql, con);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void deleteEmployee(Connection con, Statement st) {
		try {
			con = DbConnection.createDBConnection();
			System.out.println("Enter Employee id : ");
			int empid = sc.nextInt();
			sql = "delete from employee where id =" + empid;
			st = con.createStatement();
			int row = st.executeUpdate(sql);
			System.out.println("Deleted rows : " + row);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void UpdateEmployee(Connection con, Statement st) {
		int empid, row;
		try {
			while (true) {
				System.out.println("What do you want to update : ");
				System.out.println("1. Name\n" + "2. Address\n" + "3. Age\n" + "4. Exit");
				System.out.println("Enter the choice : ");
				String c = sc.next();
				if (c.equalsIgnoreCase("1") || c.equalsIgnoreCase("Name")) {
					System.out.println("Which Employee name do you want to change enter ID");
					empid = sc.nextInt();
					System.out.println("Enter new name ");
					String name = sc.next();
					sql = "Update employee set Name = '" + name + "' where id=" + empid;
					st = con.createStatement();
					row = st.executeUpdate(sql);
					System.out.println("Updated row " + row);
					break;
				} else if (c.equalsIgnoreCase("2") || c.equalsIgnoreCase("Address")) {
					System.out.println("Which Employee Address do you want to change enter ID");
					empid = sc.nextInt();
					System.out.println("Enter new Address ");
					String Address = sc.next();
					sql = "Update employee set Address = '" + Address + "' where id=" + empid;
					st = con.createStatement();
					row = st.executeUpdate(sql);
					System.out.println("Updated row " + row);
					break;
				} else if (c.equalsIgnoreCase("3") || c.equalsIgnoreCase("Age")) {
					System.out.println("Which Employee Address do you want to change enter ID");
					empid = sc.nextInt();
					System.out.println("Enter new age ");
					String age = sc.next();
					sql = "Update employee set age = '" + age + "' where id=" + empid;
					st = con.createStatement();
					row = st.executeUpdate(sql);
					System.out.println("Updated row " + row);
					break;
				} else if (c.equalsIgnoreCase("4") || c.equalsIgnoreCase("exit")) {
					System.out.println("Okay Thanks for using this fecility...");
					break;
				} else {
					System.out.println("Please enter valid choice try again");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static boolean passwordSetChange(int id, Connection con, Statement st) {

		while (true) {
			System.out.println("Do you want to set or change password 1. yes 2. No");
			String s = sc.next();
			if (s.equalsIgnoreCase("yes") || s.equals("1")) {
				SetPassword(id, con, st);
				return true;
			} else if (s.equalsIgnoreCase("No") || s.equals("2")) {
				System.out.println("Carry on with your id..");
				return true;
			} else {
				System.out.println("Please enter valid input..");
			}
		}
	}

	private static void SetPassword(int id, Connection con, Statement st) {
		String pwd = Admin.CheckValidationPwd(con, st);
		try {
			con = DbConnection.createDBConnection();
			sql = "Update maintanance set PASSWORD = '" + pwd + "' where id=" + id;
			st = con.createStatement();
			int row = st.executeUpdate(sql);
			System.out.println("Updated row " + row);
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
