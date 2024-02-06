package JavaStreamAPIDemo;

import java.util.*;

class Employees {
    private int id;
    private String name;
    private String grade;
    private int salary;

    public Employees(int id, String name, String grade, int salary) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.salary = salary;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", salary=" + salary +
                '}';
    }
}

public class FilterMapReduce {

    public static void main(String[] args) {
        reduceDemo();
//        filterDemo();
    }
    private static List<Employees> getEmployees(){
        List<Employees> elist = new ArrayList<>();
        elist.add(new Employees(101,"john","A",60000));
        elist.add(new Employees(109,"peter","B",30000));
        elist.add(new Employees(102,"mak","A",80000));
        elist.add(new Employees(103,"kim","A",90000));
        elist.add(new Employees(104,"json","C",15000));

        return elist;
    }

    private static void filterDemo(){
        // get salaries of employees whose grade A do average of it
        double avgSalary = getEmployees().stream()
                    .filter(e->e.getGrade().equalsIgnoreCase("A"))
                    .map(e -> e.getSalary())
                    .mapToDouble(i -> i)
                    .average().getAsDouble();
        System.out.println(avgSalary);
//        76666.66666666667

        // get salaries of employees whose grade A and do sum of it
        double sumSalary = getEmployees().stream()
                .filter(e->e.getGrade().equalsIgnoreCase("A"))
                .map(e->e.getSalary())
                .mapToDouble(i->i).sum();
        System.out.println(sumSalary);
//        230000.0

    }

    public static void reduceDemo() {
        //normal sequential process
        List<Integer> numbers = Arrays.asList(3, 7, 8, 1, 5, 9);
        int sum = 0;
        for (int no : numbers) {
            sum = sum + no;
        }
        System.out.println(sum);
//        33

        // java 8 streams sum
        int sum1 = numbers.stream().mapToInt(e->e).sum();
        System.out.println("sum1: " + sum1);
//        sum1: 33

        // sum using reduce method
        sum1 = numbers.stream().reduce(0,(a,b) -> a+b);
        System.out.println("sum: " + sum1);
//        sum: 33

        // method reference reduce method sum
        Optional<Integer> reduceSumMethodRef = numbers.stream().reduce(Integer::sum);
        System.out.println(reduceSumMethodRef.get());
//        33


        // multiply using reduce
        System.out.println(numbers.stream().reduce(1,(a,b) -> a*b));
//        7560

        // max value
        System.out.println(numbers.stream().reduce(0,(a,b) -> a>b ? a:b));
//        9

        // max value method reference
        numbers.stream().reduce(Integer::max).get();
        numbers.stream().reduce(Integer::sum).get();
        numbers.stream().reduce(Integer::min);


       // find the longest word
        List<String> words=Arrays.asList("corejava","spring","hibernate");
        String maxWord = words.stream().reduce((a,b) -> a.length()> b.length() ? a:b).get();
        System.out.println(maxWord);
//        hibernate

    }



}
