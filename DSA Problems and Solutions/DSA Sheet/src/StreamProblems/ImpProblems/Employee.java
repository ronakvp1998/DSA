package StreamProblems.ImpProblems;

import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String department;
    private String city;
    private double salary;
    private int yearOfJoining;
    private List<String> skills;
    public Employee(int id, String name, String gender, int age, String department,
                    String city, double salary, int yearOfJoining, List<String> skills) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.department = department;
        this.city = city;
        this.salary = salary;
        this.yearOfJoining = yearOfJoining;
        this.skills = skills;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public String getDepartment() { return department; }
    public String getCity() { return city; }
    public double getSalary() { return salary; }
    public int getYearOfJoining() { return yearOfJoining; }
    public List<String> getSkills() { return skills; }
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", gender=" + gender
                + ", age=" + age + ", department=" + department + ", city=" + city
                + ", salary=" + salary + ", yearOfJoining=" + yearOfJoining
                + ", skills=" + skills + "]";
    }
}
