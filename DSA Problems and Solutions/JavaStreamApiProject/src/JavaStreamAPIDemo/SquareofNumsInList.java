package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SquareofNumsInList {

    // 2 Square of number in the list

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,10,20,30,15);
        System.out.println(squareOfNumberList(list));
        System.out.println(squareOfNumberListEven(list));
        System.out.println(squareOfNumberListGreaterThanN(list,20));
        printEvenOddList(list);

    }

    // print even and odd
    public static void printEvenOddList(List<Integer> list){
        System.out.println(list.stream().filter(e->e%2==0).collect(Collectors.toList()));
        System.out.println(list.stream().filter(e -> e%2!=0).collect(Collectors.toList()));
    }

    public static List<Integer> squareOfNumberList(List<Integer> list){
        return list.stream().map(e -> e*e).collect(Collectors.toList());
    }
    public static List<Integer> squareOfNumberListEven(List<Integer> list){
        return list.stream().filter(i -> i%2==0).map(e->e*e).collect(Collectors.toList());
    }
    public static List<Integer> squareOfNumberListGreaterThanN(List<Integer> list,int n){
        return list.stream().filter(i -> i>n).map(e->e*e).collect(Collectors.toList());
    }

}
