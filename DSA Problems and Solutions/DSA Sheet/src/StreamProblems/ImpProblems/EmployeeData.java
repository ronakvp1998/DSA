package StreamProblems.ImpProblems;

import java.util.Arrays;
import java.util.List;

public class EmployeeData {
    private EmployeeData() {
        // utility class, no instances
    }

    // (id, name, gender, age, department, city, salary, yearOfJoining, skills)
    public static List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(101, "Aarav Mehta", "Male", 28, "IT", "Pune",
                        75000, 2019, Arrays.asList("Java", "Spring", "SQL")),
                new Employee(102, "Priya Sharma", "Female", 34, "HR", "Delhi",
                        55000, 2015, Arrays.asList("Recruitment", "Excel")),
                new Employee(103, "Rohit Verma", "Male", 41, "IT", "Bangalore",
                        125000, 2012, Arrays.asList("Java", "Microservices", "AWS", "Kafka")),
                new Employee(104, "Sneha Kulkarni", "Female", 30, "Finance", "Mumbai",
                        68000, 2018, Arrays.asList("Excel", "Tally", "SQL")),
                new Employee(105, "Imran Khan", "Male", 25, "IT", "Pune",
                        48000, 2022, Arrays.asList("Java", "SQL")),
                new Employee(106, "Neha Gupta", "Female", 38, "Marketing", "Delhi",
                        92000, 2014, Arrays.asList("SEO", "Content", "Analytics")),
                new Employee(107, "Vikram Rao", "Male", 45, "Finance", "Bangalore",
                        140000, 2010, Arrays.asList("Excel", "SAP", "Audit")),
                new Employee(108, "Anjali Nair", "Female", 32, "IT", "Chennai",
                        88000, 2017, Arrays.asList("Python", "SQL", "AWS")),
                new Employee(109, "Karan Singh", "Male", 29, "Sales", "Jaipur",
                        52000, 2020, Arrays.asList("Negotiation", "CRM")),
                new Employee(110, "Meera Iyer", "Female", 27, "HR", "Chennai",
                        46000, 2021, Arrays.asList("Payroll", "Excel")),
                new Employee(111, "Suresh Patil", "Male", 50, "Sales", "Pune",
                        99000, 2008, Arrays.asList("Negotiation", "CRM", "Leadership")),
                new Employee(112, "Divya Menon", "Female", 31, "Marketing", "Mumbai",
                        71000, 2019, Arrays.asList("SEO", "Analytics")),
                new Employee(113, "Arjun Reddy", "Male", 36, "IT", "Hyderabad",
                        110000, 2013, Arrays.asList("Java", "Spring", "Kafka", "Docker")),
                new Employee(114, "Fatima Sheikh", "Female", 43, "Finance", "Hyderabad",
                        118000, 2011, Arrays.asList("SAP", "Audit", "Excel")),
                new Employee(115, "Manish Joshi", "Male", 24, "Sales", "Jaipur",
                        40000, 2023, Arrays.asList("CRM"))
        );
    }
}
