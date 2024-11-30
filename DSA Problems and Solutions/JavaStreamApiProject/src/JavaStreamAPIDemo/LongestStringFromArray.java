package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LongestStringFromArray {

    public static void main(String[] args) {
        longestString();
    }

    private static void longestString() {
        // Array
        String[] strArray = {"java","techie","springboot","microservices"};
//        Java program to find longest string from given array
        String largestStr = Arrays.stream(strArray)
                .reduce((a,b) -> a.length() > b.length() ? a:b).get();
        System.out.println(largestStr);

        // List
//        Java program to find Smallest string from given array
        List<String> list = Arrays.asList("java","techie","springboot","microservices");
        largestStr = list.stream().reduce((a,b) -> a.length() < b.length() ? a:b).get();
        System.out.println(largestStr);

        // Using the Sorted Method, using the comparator to compare the length and sorted it
        largestStr = Arrays.stream(strArray)
                .sorted((a, b) -> Integer.compare(b.length(), a.length()))
                .findFirst()
                .orElse(null);
        System.out.println(largestStr);

        // Using max() method with a comparator to find the longest string
        largestStr = Arrays.stream(strArray)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
        System.out.println(largestStr);

    }
}
