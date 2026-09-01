package StreamProblems.ImpProblems;


import java.util.*;
import java.util.stream.Collectors;

public class StreamProblems {

    public static void main(String[] args) {
        List<Employee> employees = EmployeeData.getEmployees();

//        List departments sorted by total salary, highest first (preserve order)
//        Q46. List departments sorted by total salary, highest first (preserve order).
//                employees.stream()
//                .collect(Collectors.groupingBy(Employee::getDepartment,
//                        Collectors.summingDouble(Employee::getSalary)))
//                .entrySet().stream()
//                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (a, b) -> a, LinkedHashMap::new));

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                Collectors.summingDouble(Employee::getSalary)))
                .entrySet().stream()
                .sorted(Map.Entry.<String,Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new));

    }
}
