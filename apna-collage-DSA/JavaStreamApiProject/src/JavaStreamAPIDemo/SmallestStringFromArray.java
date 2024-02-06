package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SmallestStringFromArray {

    public static void main(String[] args) {
        smallestString();
    }

    private static void smallestString() {
        // Array
        String[] strArray = {"java", "techie", "springboot", "microservices"};

        //1  using reduce method
        String smallestStr = Arrays.stream(strArray)
                .reduce((a,b)-> a.length()<b.length() ? a:b)
                .get();
        System.out.println(smallestStr);

        // List
        List<String> list = Arrays.asList("java","techie","springboot","microservices");
        smallestStr = list.stream()
                .reduce((a,b) -> a.length() < b.length() ? a:b)
                .get();
        System.out.println(smallestStr);

        // Using the Sorted Method, using the comparator to compare the length and sorted it
        smallestStr = Arrays.stream(strArray)
                .sorted((a,b) -> Integer.compare(a.length() ,b.length())).findFirst().get();
        System.out.println(smallestStr);

        // Using the max Method, using the comparator to compare the length and sorted it
        smallestStr = Arrays.stream(strArray)
                .min(Comparator.comparingInt(String::length)).get();
        System.out.println(smallestStr);

    }
}