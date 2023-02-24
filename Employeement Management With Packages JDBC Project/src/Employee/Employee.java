package Employee;

public class Employee {
private int id;
private String name;
private double salary;
private int age;
private String Adress;
private String position;

public Employee() {
	
}

public Employee(int id, String name, double salary, int age, String adress, String position) {
	super();
	this.id = id;
	this.name = name;
	this.salary = salary;
	this.age = age;
	Adress = adress;
	this.position = position;
}

@Override
public String toString() {
	return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", age=" + age + ", Adress=" + Adress
			+ ", position=" + position + "]";
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public double getSalary() {
	return salary;
}

public void setSalary(double salary) {
	this.salary = salary;
}

public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}

public String getAdress() {
	return Adress;
}

public void setAdress(String adress) {
	Adress = adress;
}

public String getPosition() {
	return position;
}

public void setPosition(String position) {
	this.position = position;
}
}
