package JavaStreamAPIDemo;
import java.util.*;
import java.util.stream.Collectors;

public class MyntraJavaQuestion {

    public static void main(String[] args) {
        nthHighestSalary();
    }

    public static void nthHighestSalary() {
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("anil", 1000);
        map1.put("bhavna", 1300);
        map1.put("micael", 1500);
        map1.put("tom", 1600);
        map1.put("ankit", 1200);
        map1.put("daniel", 1700);
        map1.put("james", 1400);

        // find out the second highest salary
        Map.Entry<String, Integer> max2 = map1.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList()).get(1);
        System.out.println(max2);
//        tom=1600

        max2 = map1.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList()).get(1);
        System.out.println(max2);
//        tom=1600

        // getNthHighestSalary
        getNthHighestSalary(3,map1);

        Map<String,Integer> map2 = new HashMap<>();
        map2.put("anil",1000);
        map2.put("bhavna",1200);
        map2.put("micael",1200);
        map2.put("tom",1200);
        map2.put("ankit",1000);
        map2.put("daniel",1300);
        map2.put("james",1300);

        getNthHighestSalary(2,map2);
//        james=1300
        // The output we got is incorrect we need second higest salary which is 1200
        // Solution do group by on the value and based on it do the sorting

        Map<Integer,List<Map.Entry<String,Integer>>> map =map2.entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue));
        System.out.println(map);
//        {1200=[tom=1200, micael=1200, bhavna=1200], 1300=[daniel=1300, james=1300], 1000=[ankit=1000, anil=1000]}

        Map<Integer, List<String>> mapFix = map2.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey,Collectors.toList())));
        System.out.println(mapFix);
//        {1200=[tom, micael, bhavna], 1300=[daniel, james], 1000=[ankit, anil]}

        Map.Entry<Integer, List<String>> salaryResult = map2.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey,Collectors.toList()))).entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toList()).get(1);
        System.out.println(salaryResult);
//        1200=[tom, micael, bhavna]

        getNthHighestSalaryFix(2,map2);
    }
    public static void getNthHighestSalaryFix(int num,Map<String,Integer> map2){

        Map.Entry<Integer, List<String>> salaryResult = map2.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey,Collectors.toList())))
                .entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toList()).get(num-1);
        System.out.println(salaryResult);
    }
    public static void getNthHighestSalary(int num,Map<String,Integer> map){
        Map.Entry<String,Integer>  max2 = map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList()).get(num-1);
        System.out.println("max2 " + max2 );

        System.out.println(map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(e->e.getValue()).distinct().collect(Collectors.toList()).get(1));

    }

}