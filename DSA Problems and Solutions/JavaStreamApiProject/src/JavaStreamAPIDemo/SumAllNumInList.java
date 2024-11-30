package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;

public class SumAllNumInList {

    // 1 sum all numbers in the list

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,4);
        System.out.println(sumAllNumber1(list));
        System.out.println(sumAllNumber2(list));

    }

    public static int sumAllNumber1(List<Integer> list){
        return list.stream().mapToInt(e->e).sum();
    }
    public static int sumAllNumber2(List<Integer> list){
        return list.stream().reduce((a,b) -> a+b).get();
    }

}
