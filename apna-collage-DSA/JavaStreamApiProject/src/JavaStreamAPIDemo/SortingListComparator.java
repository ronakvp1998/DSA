package JavaStreamAPIDemo;
import java.util.*;
import java.util.stream.Collectors;

class Employee{
    private int id;
    private String name;
    private String dept;

    public Employee(int id, String name, String dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}

public class SortingListComparator {

    public static void main(String[] args) {
        sortListPrimitive();
//        sortCustomList();
    }
    public static void sortListPrimitive() {
        List<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(2);
        list.add(12);
        list.add(4);
        Collections.sort(list);
        System.out.println(list);
        Collections.reverse(list);
        System.out.println(list);
        Collections.reverse(list);
        System.out.println(list);
    }
    private static void sortCustomList() {
        List<Employee> elist = new ArrayList<>();
        elist.add(new Employee(1,"ronak","aIT"));
        elist.add(new Employee(10,"aronak","dIT"));
        elist.add(new Employee(6,"bronak","dIT"));
        elist.add(new Employee(16,"eronak","eIT"));

        // using lamdba, sort by id
        System.out.println(elist.stream()
                .sorted((a,b) -> a.getId() - b.getId())
                .map(e->e.getId()).collect(Collectors.toList()));
//        [1, 6, 10, 16]

        // sort by name
        System.out.println(elist.stream()
                .sorted((a,b) -> a.getName().compareTo(b.getName()))
                .map(e->e.getId()).collect(Collectors.toList()));
//        [10, 6, 16, 1]

        // using Comparator.comparing()
        System.out.println(elist.stream()
                .sorted(Comparator.comparing(a -> a.getId()))
                .map(e->e.getId()).collect(Collectors.toList()));
//        [1, 6, 10, 16]

        // using Comparator.comparing()
        System.out.println(elist.stream()
                .sorted(Comparator.comparing(Employee::getDept))
                .map(e->e.getId()).collect(Collectors.toList()));
//        [1, 10, 6, 16]

        System.out.println(elist.stream()
                .sorted(Comparator.comparing(Employee::getId))
                .map(e->e.getId()).collect(Collectors.toList()));
//        [1, 6, 10, 16]



    }


}

