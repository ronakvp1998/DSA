package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class sortList {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(22,12,45,222,2,22,43,43,22222);
        sortList(list);

    }
    public static void sortList(List<Integer> list){
        System.out.println(list.stream().sorted().collect(Collectors.toList()));
        System.out.println(list.stream()
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

}
