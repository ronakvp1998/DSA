package JavaStreamAPIDemo;

import java.util.*;
import java.util.stream.Collectors;

public class SortingMap {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("eight", 8);
        map.put("four", 4);
        map.put("ten", 10);
        map.put("two", 2);

//        traverseMap(map);
//        sortMap(map);
        // sorting map using stream API
//        sortMapStream(map);
//        sortCustomMap();
//        sortCustomMapStream();
    }

    private static void sortCustomMapStream() {
        Map<Employee, Integer> emap = new TreeMap<>((a, b) -> a.getId() - b.getId());
        emap.put(new Employee(12, "aronak", "IT"), 2);
        emap.put(new Employee(2, "hronak", "D"), 44);
        emap.put(new Employee(1, "cronak", "F"), 4);
        emap.put(new Employee(7, "dronak", "B"), 14);

        // sort by department in asc
        emap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Employee::getDept)))
                .forEach(System.out::print);
        System.out.println();
//        Employee{id=7, name='dronak', dept='B'}=14Employee{id=2, name='hronak', dept='D'}=44
//    Employee{id=1, name='cronak', dept='F'}=4Employee{id=12, name='aronak', dept='IT'}=2

        // sort by department in desc
        emap.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey((a,b) -> b.getDept().compareTo(a.getDept())))
                                .forEach(System.out::print);
        System.out.println();
//        Employee{id=12, name='aronak', dept='IT'}=2Employee{id=1, name='cronak', dept='F'}=4
//        Employee{id=2, name='hronak', dept='D'}=44Employee{id=7, name='dronak', dept='B'}=14


        // sort by id in desc
        emap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((a,b) -> b.getId() - a.getId()))
                .forEach(System.out::print);
        System.out.println();
//        Employee{id=12, name='aronak', dept='IT'}=2Employee{id=7, name='dronak', dept='B'}=14
//        Employee{id=2, name='hronak', dept='D'}=44Employee{id=1, name='cronak', dept='F'}=4

        emap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Employee::getId).reversed()))
                .forEach(System.out::print);
        System.out.println();
//        Employee{id=12, name='aronak', dept='IT'}=2Employee{id=7, name='dronak', dept='B'}=14
//        Employee{id=2, name='hronak', dept='D'}=44Employee{id=1, name='cronak', dept='F'}=4

        // sort by values asc
        emap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Integer::valueOf)))
                .forEach(System.out::print);
        System.out.println();
//        Employee{id=12, name='aronak', dept='IT'}=2Employee{id=1, name='cronak', dept='F'}=4
//        Employee{id=7, name='dronak', dept='B'}=14Employee{id=2, name='hronak', dept='D'}=44

        emap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue((a,b) -> a-b))
                                .forEach(System.out::print);
        System.out.println();
//        Employee{id=12, name='aronak', dept='IT'}=2Employee{id=1, name='cronak', dept='F'}=4
//        Employee{id=7, name='dronak', dept='B'}=14Employee{id=2, name='hronak', dept='D'}=44

        // sort by values desc
        emap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a,b) -> b - a))
                .forEach(System.out::print);
        System.out.println();
//        Employee{id=2, name='hronak', dept='D'}=44Employee{id=7, name='dronak', dept='B'}=14
//        Employee{id=1, name='cronak', dept='F'}=4Employee{id=12, name='aronak', dept='IT'}=2

    }

        private static void sortCustomMap() {
        // 1 normal way to sort by name in asc
        Map<Employee,Integer> emap = new TreeMap<>(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        emap.put(new Employee(12,"aronak","IT"),2);
        emap.put(new Employee(2,"hronak","d"),44);
        emap.put(new Employee(22,"zronak","d"),444);
        emap.put(new Employee(1,"cronak","f"),4);

        for(Map.Entry e : emap.entrySet()){
            System.out.print(e.getKey() + "->" + e.getValue() +",");
        }
        System.out.println();
//        Employee{id=12, name='aronak', dept='IT'}->2,Employee{id=1, name='cronak', dept='f'}->4,
//        Employee{id=2, name='hronak', dept='d'}->44,Employee{id=22, name='zronak', dept='d'}->444,

        // 2 Sort by id using lambda exp
        Map<Employee,Integer> emap1 = new TreeMap<>((a,b) -> a.getId() - b.getId());
        emap1.put(new Employee(12,"aronak","IT"),2);
        emap1.put(new Employee(2,"hronak","d"),44);
        emap1.put(new Employee(1,"cronak","f"),4);
        emap1.put(new Employee(15,"rronak","j"),12);
        for(Map.Entry e : emap1.entrySet()){
            System.out.print(e.getKey() + "->" + e.getValue() + ",");
        }
        System.out.println();
//        Employee{id=1, name='cronak', dept='f'}->4,Employee{id=2, name='hronak', dept='d'}->44,
//        Employee{id=12, name='aronak', dept='IT'}->2,Employee{id=15, name='rronak', dept='j'}->12,

        // 3 sort by values of map
        List<Map.Entry<Employee,Integer>> entryList = new ArrayList<>(emap1.entrySet());
        entryList.sort(Comparator.comparingInt(Map.Entry::getValue));
        for(Map.Entry<Employee,Integer> e : entryList){
            System.out.print(e.getKey() + "->" +e.getValue() +",");
        }
        System.out.println();
//        Employee{id=12, name='aronak', dept='IT'}->2,Employee{id=1, name='cronak', dept='f'}->4,
//        Employee{id=15, name='rronak', dept='j'}->12,Employee{id=2, name='hronak', dept='d'}->44,


        // 4 lambda sort in desc order by id
        Map<Employee,Integer> emap2 = new TreeMap<>((a,b) -> b.getId() - a.getId());
        Map<Employee,Integer> emap3 = new TreeMap<>(Comparator.comparing(Employee::getId).reversed());
        emap3.put(new Employee(12,"aronak","IT"),2);
        emap3.put(new Employee(22,"hronak","d"),44);
        emap3.put(new Employee(1,"cronak","f"),4);
        emap3.put(new Employee(7,"fronak","j"),24);
        for(Map.Entry e : emap3.entrySet()){
            System.out.print(e.getKey() + "->" + e.getValue() + " ");
        }
        System.out.println();
//        Employee{id=22, name='hronak', dept='d'}->44 Employee{id=12, name='aronak', dept='IT'}->2
//        Employee{id=7, name='fronak', dept='j'}->24 Employee{id=1, name='cronak', dept='f'}->4


    }

        private static void sortMapStream(Map<String, Integer> map) {

        // sorting/comparing by key
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()).forEach(System.out::print);
        System.out.println();

        // sorting / comparing by value
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).forEach(System.out::print);
        System.out.println();

        // reverse sorting key
        map.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByKey().reversed())
                .forEach(System.out::print);
        System.out.println();

        // reverse sorting value
        map.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .forEach(System.out::print);
        System.out.println();



    }

        private static void sortMap(Map<String, Integer> map){

        // override comparemethod
//        map.entrySet().forEach(e->System.out.print(e + "->"));
//        System.out.println();

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        for(Map.Entry<String, Integer> e : entries){
            System.out.print(e.getKey() + "->" + e.getValue() + ", ");
        }
        System.out.println();

        Collections.sort(entries,(a,b) -> a.getKey().compareTo(b.getKey()));
        for(Map.Entry<String, Integer> e : entries){
            System.out.print(e.getKey() + "->" + e.getValue() + ", ");
        }
        System.out.println();

        // dec
        Collections.sort(entries,(a,b) -> b.getKey().compareTo(a.getKey()));
        for(Map.Entry<String,Integer> e : entries){
            System.out.print(e.getKey() + "->" + e.getValue() + ",");
        }
        System.out.println();

    }


    private static void traverseMap(Map<String, Integer> map) {

        // using List
        // foreach
        map.entrySet().forEach(System.out::print);
        System.out.println();
        List<Map.Entry> entries = new ArrayList<>(map.entrySet());
        for(Map.Entry e: entries){
            System.out.print(e + "->");
        }
        System.out.println();

        List<Map.Entry<String,Integer>> entries2 = new ArrayList<>(map.entrySet());
        for(Map.Entry e : entries2){
            System.out.print(e + "->");
        }
        System.out.println();

        // using Set
        Set<Map.Entry<String,Integer>> entrySet = map.entrySet();
        for(Map.Entry e: entrySet){
            System.out.print(e + "->");
        }
        System.out.println();

        // foreach
        map.entrySet().forEach(System.out::print);
        System.out.println();

        // entrySet
        for(Map.Entry entry : map.entrySet()){
            System.out.print(entry.getKey() + "->" + entry.getValue());
        }

    }

}
