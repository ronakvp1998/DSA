package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FindElementStartsWith1 {

    public static void main(String[] args) {
        startsWith1();
    }

    private static void startsWith1() {
        // using mapToObject
        int[] numbers = {5,9,11,2,8,21,1};
        System.out.println(Arrays.stream(numbers)
                .mapToObj(e->e).map(e->e.toString())
                .filter(e->e.startsWith("1")).collect(Collectors.toList()));

        // using boxed
        System.out.println(Arrays.stream(numbers).boxed()
                .map(e->e.toString())
                .filter(e->e.startsWith("1")).collect(Collectors.toList()));

        List<Integer> list = Arrays.asList(5,9,11,2,8,21,1);
        System.out.println(list.stream().map(e->e.toString())
                        .filter(e->e.startsWith("1"))
                .collect(Collectors.toList()));

    }
}