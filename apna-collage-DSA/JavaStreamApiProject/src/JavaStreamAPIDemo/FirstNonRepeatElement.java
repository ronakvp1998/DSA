package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstNonRepeatElement {

    public static void main(String[] args) {
//        countChar();
//        duplicateFromString();
//        uniqueFromString();
        firstNonRepeatElement();
    }

    private static void firstNonRepeatElement() {
        // Java Program to find first non repeat element from the string
        String str = "ilovejavatechie";
        System.out.println(Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()==1).findFirst().get().getKey());
        System.out.println(Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()==1).findFirst().get());

        // Java Program to find first repeat element from the string
        System.out.println(Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(),LinkedHashMap::new,Collectors.counting()))
                .entrySet().stream().filter(e->e.getValue()>1).findFirst().get());

    }
}
