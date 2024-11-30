package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SumFirstFiveNumberLimit2 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(22,12,45,222,2,22,43,43,22222);
        sumFirstFiveNumberLimit2(list);
        sumFirstFiveNumberLimit(list);
        sumFirstFiveNumberSkip(list);
    }

    public static void sumFirstFiveNumberLimit2(List<Integer> list){
        System.out.println(list.stream().limit(5).reduce((a,b) -> a+b).get());
    }
    public static void sumFirstFiveNumberLimit(List<Integer> list){
        System.out.println(list.stream().limit(5).mapToInt(e->e).sum());
    }


    // skip :- skip 1st 5 numbers and sum this remaining  numbers
    public static void sumFirstFiveNumberSkip(List<Integer> list){
        System.out.println(list.stream().skip(5).mapToInt(e->e).sum());
        System.out.println(list.stream().skip(5).reduce((a,b) -> a+b));
    }

}
