package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.*;
import java.util.stream.Collectors;

public class DuplicateNumList {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(22,12,45,222,2,22,43,43,22222);
        printDuplicateFromList2(list);
        printDuplicateFromList1(list);

    }

    // print duplicate number
    public static void printDuplicateFromList2(List<Integer> list) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> duplicateItems = list.stream().filter(e -> !set.add(e)).collect(Collectors.toSet());
        System.out.println(duplicateItems);

    }

    public static void printDuplicateFromList1(List<Integer> list) {
        Set<Integer> deplicate = list.stream().filter(e->Collections.frequency(list,e)>1).collect(Collectors.toSet());
        System.out.println(deplicate);

    }

}
