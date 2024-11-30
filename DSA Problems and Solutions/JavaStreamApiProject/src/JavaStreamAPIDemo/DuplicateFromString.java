package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DuplicateFromString {

    public static void main(String[] args) {
//        countChar();
        duplicateFromString();
    }

    private static void duplicateFromString(){
        String str = "ilovejavatechie";
        System.out.println(Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()>1).map(e->e.getKey()).collect(Collectors.toList()));

        Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()>1)
                .forEach(k -> System.out.print(k.getKey() + "=" + k.getValue() + " "));
    }

}
