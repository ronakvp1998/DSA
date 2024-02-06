package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;

public class AvgOfNumInList {

    // 2 avg all numbers in the list

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,4);
        System.out.println(avgAllNumber1(list));
        System.out.println(avgAllNumber2(list));

    }

    public static double avgAllNumber1(List<Integer> list){
        return list.stream().mapToInt(e->e).average().getAsDouble();
    }
    public static int avgAllNumber2(List<Integer> list){
        int sum = list.stream().reduce((a,b) -> a+b).get();
        return sum/list.size();
    }
}
