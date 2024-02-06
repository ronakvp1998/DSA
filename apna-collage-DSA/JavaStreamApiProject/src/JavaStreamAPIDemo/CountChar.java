package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountChar {

    public static void main(String[] args) {
        countChar();
    }

    public static void countChar() {
        String str = "ilovejavatechie";
        // use java 8 group by
        System.out.println(Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

    }
}
