import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Integer> list = List.of(10,8,15,49,25,98,15,32,98);
        System.out.println(list.stream()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()>1).collect(Collectors.toList()));

//                .entryset().map(e->e.getValues()).filter(e->e>1).collect(Collectors.toList());

        // String non repeated char in it using Stream
        String str = "Java artical are Awesome";
        System.out.println(Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new,Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()==1).findFirst().get());

        // how to count each word from String ArrayList in java8
        List<String> list1 = List.of("SAI","SHYAM","SAI","RAM");
        System.out.println(list1.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting())));

    }
}