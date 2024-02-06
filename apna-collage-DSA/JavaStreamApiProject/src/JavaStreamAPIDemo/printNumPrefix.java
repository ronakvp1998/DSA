package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class printNumPrefix {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(22,12,45,222,2,22,43,43,22222);
        printNumStartsWithPrefix(list);
        printNumStartsWithPrefix2(list);

    }

    public static void printNumStartsWithPrefix(List<Integer> list){
        System.out.println(list.stream().map(e -> e+"").filter(e->e.startsWith("2"))
                .map(e->Integer.parseInt(e)).collect(Collectors.toList()));
    }

    public static void printNumStartsWithPrefix2(List<Integer> list){
        System.out.println(list.stream().map(e -> e+"").filter(e->e.startsWith("2"))
                .map(e->Integer.parseInt(e)).collect(Collectors.toList()));
    }



}
