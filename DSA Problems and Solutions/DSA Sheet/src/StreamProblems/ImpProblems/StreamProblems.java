package StreamProblems.ImpProblems;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamProblems {

    public static void main(String[] args) {
//        1. Filter Even Numbers from a List
//        Question: Given a list of integers, filter the even numbers.
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
//        List<Integer> evenNum = numbers.stream()
//                .filter(n -> n%2 == 0)
//                .collect(Collectors.toList());

//        2. Find Maximum in a List
//        Question: Find the maximum number from a list of integers.
//        List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);
//        int max = numbers.stream().max((a,b) -> a-b).orElseThrow();
//        System.out.println(max);

//        3. Sort a List
//        Question: Sort a list of integers in descending order.
//        List<Integer> numbers = Arrays.asList(3, 5, 1, 4, 2);
//        List<Integer> sortedNum = numbers.stream().sorted().collect(Collectors.toList());
//        System.out.println(sortedNum);
//        List<Integer> sortedNum2 = numbers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
//        System.out.println(sortedNum2);

//        4. Count Strings with Specific Prefix
//        Question: Count strings starting with a specific prefix, e.g., “A”.
//        List<String> names = Arrays.asList("Alice", "Bob", "Annie", "Alex", "Charlie");
//        long count = names.stream().filter(e -> e.startsWith("A")).count();
//        System.out.println(count);

//        5. Find First Non-Repeated Character in a String
//        Question: Find the first non-repeated character in a string.
//        String input = "swiss";
//        Optional<Character> firstNonRepeated = input.chars().mapToObj(c -> (char)c).filter(e -> input.indexOf(e) == input.lastIndexOf(e)).findFirst();
//        System.out.println(firstNonRepeated.orElse(null));

//        6. Convert List of Strings to Uppercase
//        Question: Convert all strings in a list to uppercase.
//        List<String> names = Arrays.asList("java", "stream", "api");
//        List<String> uppercase = names.stream().map(e -> e.toUpperCase()).collect(Collectors.toList());
//        System.out.println(uppercase);

//        7. Sum of Numbers in a List
//        Question: Calculate the sum of all numbers in a list.
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
//        int sum = numbers.stream().reduce((a,b) -> a+b).get();
//        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
//        System.out.println(sum);

//        8. Check if Any String Matches a Condition
//        Question: Check if any string in a list contains “API”.

    }
}
