package JavaStreamAPIDemo;

import java.util.*;
import java.util.stream.Collectors;

public class SecondLowestHighest {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(22,12,45,222,2,22,43,43,22222);
        printSecondLowestHighest(list);

    }

    public static void printSecondLowestHighest(List<Integer> list){
        //  highest
        System.out.println(list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(1));
        //  lowest
        System.out.println(list.stream().sorted().collect(Collectors.toList()).get(1));

        // second highest
        System.out.println(list.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst());
        System.out.println(list.stream().sorted().skip(1).findFirst());


    }
}
