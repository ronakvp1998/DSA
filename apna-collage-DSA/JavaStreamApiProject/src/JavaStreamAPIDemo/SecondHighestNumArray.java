package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SecondHighestNumArray {

    public static void main(String[] args) {
        secondHighest();
    }

    private static void secondHighest(){
        // get the second highest number from the array
//        .boxed(): Converts the primitive stream of integers into a stream of boxed integers (Integer objects).
        int[] numbers = {5,9,11,2,8,21,1};
        System.out.println(Arrays.stream(numbers).boxed()
                .sorted(Collections.reverseOrder())
                .skip(1).findFirst().get());

//        the mapToObj(Integer::valueOf) is used to convert each int value
//        to its corresponding Integer object before further processing
        System.out.println(Arrays.stream(numbers)
                .mapToObj(e->Integer.valueOf(e))
                .sorted(Collections.reverseOrder())
                .skip(1)
                .findFirst().get());

        // for list its already of boxed type
        List<Integer> list = Arrays.asList(5,9,11,2,8,21,1);
        int secondHighest = list.stream().sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()).get(1);
        System.out.println("secondHighest: " + secondHighest);
//        secondHighest: 11

        // using skip
        secondHighest = list.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().get();
        System.out.println("secondHighest: " + secondHighest);
//        secondHighest: 11

    }
}
