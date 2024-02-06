package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UniqueelementsString {

    public static void main(String[] args) {
//        countChar();
//        duplicateFromString();
        uniqueFromString();
    }

    public static void uniqueFromString() {
        // Java Program to find all unique elements from a given String
        String str = "ilovejavatechie";
        Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream().filter(k->k.getValue() == 1)
                .forEach(k -> System.out.print(k.getKey() + "=" + k.getValue() + " "));
        System.out.println();


        List<String> unique = Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream().filter(k->k.getValue() == 1)
                .map(k -> k.getKey()).collect(Collectors.toList());
        System.out.println(unique);
    }
}
