package Management;
import java.sql.Connection;
import java.sql.Statement;

import Employee.Employee;

public interface EmployeeDaoInterface {
// create employee
	public void createEmployee(Employee emp,Connection con,Statement st);
// show all employee
	public void showAllEmployee(Connection con,Statement st);
// show employee based on id
	public void showBasedOnIdEmployee(Connection con,Statement st);
// update employee 
	public void UpdateEmployee(Connection con,Statement st);
// delete employee
	public void deleteEmployee(Connection con,Statement st);
}

