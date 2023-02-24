package Management;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import Employee.Employee;

public class Admin extends EmployeeDaoImpl {
	static EmployeeDaoInterface dao = new EmployeeDaoImpl();
	static final Scanner sc = new Scanner(System.in);
	private static String sql;

	public static void startProcess() {
		try {
			Connection con = DbConnection.createDBConnection();
			Statement st = con.createStatement();
			System.out.println("...........Welcome to Employee Managment Application...........");
			while (true) {
				System.out.println("Which type of user are you :\n1.Admin\n2.Managing person\n3.Exit");
				System.out.println("Enter the choice : ");
				String ch = sc.next();
				if (ch.equals("1")) {
					Admin.verification("Admin", con,st);
				} else if (ch.equals("2")) {
					Admin.verification("maintanance", con,st);
				} else if (ch.equals("3")) {
					System.out.println("Thanks for visiting..");
					con.close();
					st.close();
					break;
				} else {
					System.out.println("Please enter valid input try again..");
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updateSalary(Connection con,Statement st) {
		try {
			System.out.println("Enter Employee id : ");
			String empid = sc.next();
			System.out.println("Enter new salary : ");
			String salary = sc.next();
			sql = "Update employee set salary = '" + salary + "' where id=" + empid;
			st = con.createStatement();
			int row = st.executeUpdate(sql);
			System.out.println("Updated rows : " + row);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updatePostion(Connection con, Statement st) {
		try {
			System.out.println("Enter Employee id : ");
			String empid = sc.next();
			System.out.println("Enter new position : ");
			String pos = sc.next();
			sql = "Update employee set POSITION = '" + pos + "' where id=" + empid;
			st = con.createStatement();
			int row = st.executeUpdate(sql);
			System.out.println("Updated rows : " + row);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void SetlockOrRelease(Connection con, Statement st) {
		try {
			System.out.println("Enter Employee id : ");
			String empid = sc.next();
			String status = setAccess();
			sql = "Update maintanance set Access = '" + status + "' where id=" + empid;
			st = con.createStatement();
			int row = st.executeUpdate(sql);
			if (row > 0) {
				System.out.println("Updated rows : " + row);
			} else {
				System.out.println("Not matched your entry..");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String setAccess() {
		String status = "";
		while (true) {
			System.out.println("Select the number or type Yes||No");
			System.out.println("1. Give Access[yes]");
			System.out.println("2. Lock Access[No]");
			String s = sc.next();
			if (s.equalsIgnoreCase("1") || s.equalsIgnoreCase("yes")) {
				status = "yes";
				break;
			} else if (s.equalsIgnoreCase("2") || s.equalsIgnoreCase("no")) {
				status = "no";
				break;
			} else {
				System.out.println("Please enter valid input try again");
			}
		}
		return status;
	}

	public void insertManaginPerson(Connection con, Statement st) {
		try {
			System.out.println("Enter Id No : ");
			String id = sc.next();
			System.out.println("Enter the Name : ");
			String Name = sc.next();
			System.out.println("Enter the password: ");
			String pwd = sc.next();
			String status = setAccess();
			sql = "INSERT INTO maintanance VALUES(" + id + ", '" + Name + "', '" + pwd + "', '" + status + "');";
			st = con.createStatement();
			int row = st.executeUpdate(sql);
			boolean check = row > 0 ? true : false;
			System.out.println("Employee is" + (check == true ? " " : " Not ") + "Updated Successfully...");
			check = false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void getAllManagingPerson(Connection con, Statement st) {
		try {
			sql = "Select * from maintanance ;";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				System.out.println("______________________________________");
				System.out.println("Managers " + rs.getString(2) + " Given Below");
				System.out.println("______________________________________");
				System.out.println("ID       : " + rs.getInt(1));
				System.out.println("NAME 	 : " + rs.getString(2));
				System.out.println("ACCESS   : " + rs.getString(4));
				System.out.println("======================================");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static String CheckValidationPwd(Connection con, Statement st) {
		String password = "";
		try {
			try (Scanner sc = new Scanner(System.in)) {

				while (true) {
					System.out.println("Please enter password given type only ");
					System.out
							.println("digit(0-9),characters(8 to 15),letter(a-z),letter(A-Z),specified ( #, %, &, $)");
					System.out.println("Enter the password : ");
					password = sc.nextLine();
					boolean returned = checkPassword(password);
					if (returned == true) {
						System.out.println("Valid password");
						break;
					} else {
						System.out.println("Not valid password please try again");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return password;
	}

	private static boolean checkPassword(String password) {
		boolean flag = false;
		if (password.length() >= 8 && password.length() <= 15) {
			for (int i = 0; i < password.length(); i++) {
				char check = password.charAt(i);
				if (check == ' ') {
					System.out.println("Space not applicable please try again..");
					flag = false;
				} else {
					if ((isSpecialChar(password) && isNumeric(password) && isCapitalLetter(password)
							&& isSmallLetter(password)) == true) {
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		} else {
			System.out.println("Please enter 8 letters to 15 letters..");
		}
		return flag;
	}

	private static boolean isSmallLetter(String password) {
		for (int i = 0; i < password.length(); i++) {
			{
				if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
					return true;
			}
		}
		return false;
	}

	private static boolean isCapitalLetter(String password) {
		for (int i = 0; i < password.length(); i++) {
			{
				if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
					return true;
			}
		}
		return false;
	}

	private static boolean isNumeric(String password) {
		for (int i = 0; i < password.length(); i++) {
			{
				if (password.charAt(i) >= '0' && password.charAt(i) <= '9')
					return true;
			}
		}
		return false;
	}

	private static boolean isSpecialChar(String password) {
		for (int i = 0; i < password.length(); i++) {
			{
				if (password.charAt(i) >= '#' && password.charAt(i) <= '&')
					return true;
			}
		}
		return false;
	}

	public static void verification(String string, Connection con, Statement st) throws SQLException {
		while (true) {
			Employee e = new Employee();
			
			boolean c = EmployeeDaoImpl.passwordCheck(string, e, con, st);
			if (c == true) {
				System.out.println("user verified..");
				if (string.equalsIgnoreCase("Admin")) {
					while (true) {
						System.out.println("1. Basic\n2. Other Main importent maintanace\n"
								/* 3. Process Activity\n */ + "3. Exit");
						System.out.println("Enter the choice : ");
						String s = sc.next();
						if (s.equals("1")) {
							EmployeeUpdation(con, st);
						} else if (s.contains("2")) {
							MainImportantUpdation(con, st);
						}
//						else if (s.contains("3")) {
//							processList();
//						} 
						else if (s.contains("3")) {
							System.out.println("Thank you for using our fecility");
							return;
						} else {
							System.out.println("Invalid input please try again..");
						}
					}
				} else if (string.equalsIgnoreCase("Maintanance")) {
					while (true) {
						System.out.println("1. Basic Options\n2. change password and RerunProgram \n3. Exit ");
						System.out.println("Enter the choice : ");
						String s = sc.next();
						if (s.equals("1")) {
							EmployeeUpdation(con, st);
						} else if (s.equals("2")) {
							EmployeeDaoImpl.passwordSetChange(e.getId(), con, st);
							System.out.println("Your Password is updated please ReRun ..");
							System.gc();
							System.exit(0);
						} else if (s.equals("3")) {
							System.out.println("Thank you for using our fecility");
							break;
						} else {
							System.out.println("Invalid input please try again..");
						}
					}
					break;
				}
			} else {
				System.out.println("invalid password Please Try again ");
			}
		}
	}

	public static void MainImportantUpdation(Connection con, Statement st) {
		Admin a = new Admin();
		while (true) {
			System.out.println("which do you want to Update :\n1. salary\n" + "2. Position\n"
					+ "3. Managing person lock release\n" + "4. Insert managing person\n"
					+ "5. See all Managing person id and access\n" + "6. show All Employee\n" + "7. Exit");
			System.out.println("Enter the choice : ");
			String c = sc.next();
			if (c.equals("1")) {
				a.updateSalary(con, st);
			} else if (c.equals("2")) {
				a.updatePostion(con, st);
			} else if (c.equals("3")) {
				a.SetlockOrRelease(con, st);
			} else if (c.equals("4")) {
				a.insertManaginPerson(con, st);
			} else if (c.equals("5")) {
				a.getAllManagingPerson(con, st);
			} else if (c.equals("6")) {
				dao.showAllEmployee(con, st);
			} else if (c.equalsIgnoreCase("Exit") || c.equals("7")) {
				System.out.println("Thanks for using our fecility");
				break;
			}
		}
	}

	private static Employee setinfo(Employee emp) {
		System.out.println("Enter Employee Id : ");
		emp.setId(sc.nextInt());
		System.out.println("Enter the Name : ");
		emp.setName(sc.next());
		System.out.println("Enter the age : ");
		emp.setAge(sc.nextInt());
		System.out.println("Enter the Address : ");
		emp.setAdress(sc.next());
		System.out.println("Enter the salary : ");
		emp.setSalary(sc.nextDouble());
		System.out.println("Enter the position : ");
		emp.setPosition(sc.next());
		return emp;
	}

	public static void EmployeeUpdation(Connection con, Statement st) {
		do {
			System.out.println("1. Add Employee\n" + "2. show All Employee\n" + "3. show employee based on id\n"
					+ "4. update employee\n" + "5. Delete the employee\n" + "6. exit.");
			System.out.println("Enter the choice : ");
			int ch = sc.nextInt();
			switch (ch) {
			case 1:
				Employee emp = new Employee();
				dao.createEmployee(setinfo(emp), con, st);
				break;
			case 2:
				dao.showAllEmployee(con, st);
				break;
			case 3:
				dao.showBasedOnIdEmployee(con, st);
				break;
			case 4:
				dao.UpdateEmployee(con, st);
				break;
			case 5:
				dao.deleteEmployee(con, st);
				break;
			case 6:
				System.out.println("Thank you for using our Application !!!! ");
				return;
			default:
				System.out.println("Enter valid choice try again");
			}
		} while (true);

	}
}
