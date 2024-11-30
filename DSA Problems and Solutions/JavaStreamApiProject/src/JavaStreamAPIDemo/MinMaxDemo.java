package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MinMaxDemo {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(22,12,45,222,2,22,43,43,22222);
        minMax2(list);
        minMax(list);

    }

    public static void minMax2(List<Integer> list){
        System.out.println(list.stream().min(Comparator.comparing(Integer::valueOf)).get());
        System.out.println(list.stream().max(Comparator.comparing(Integer::valueOf)).get());
    }

    public static void minMax(List<Integer> list){
        System.out.println(list.stream().mapToInt(e->e).max().getAsInt());
        System.out.println(list.stream().mapToInt(e->e).min().getAsInt());

    }
}
