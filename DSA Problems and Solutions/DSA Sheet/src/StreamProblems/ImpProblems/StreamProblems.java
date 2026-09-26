package StreamProblems.ImpProblems;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamProblems {

    public static void main(String[] args) {
        List<Employee> employees = EmployeeData.getEmployees();

        employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.summingDouble(Employee::getSalary)))
                .entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a,LinkedHashMap::new));

    }
}

