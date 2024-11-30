package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecondLowestNumArray {

    public static void main(String[] args) {
        secondLowest();
    }

    public static void secondLowest() {
        // get the second lowest number from the array
//        for array we need to user boxed
        int[] numbers = {5,9,11,2,8,21,1};
        int secondLowest = Arrays.stream(numbers).boxed()
                .sorted().collect(Collectors.toList()).get(1);
        System.out.println(secondLowest);

        secondLowest = Arrays.stream(numbers)
                .mapToObj(e->Integer.valueOf(e)).sorted().collect(Collectors.toList()).get(1);
        System.out.println(secondLowest);

        secondLowest = Arrays.stream(numbers)
                .mapToObj(e->Integer.valueOf(e)).sorted().skip(1).findFirst().get();
        System.out.println(secondLowest);

        // for list its already of boxed type
        List<Integer> list = Arrays.asList(5,9,11,2,8,21,1);
        secondLowest = list.stream().sorted()
                .collect(Collectors.toList()).get(1);
        System.out.println("secondLowest: " + secondLowest);
//        secondLowest: 2

        // using skip
        secondLowest = list.stream().sorted().skip(1).findFirst().get();
        System.out.println("secondLowest: " + secondLowest);
//        secondHighest: 2


    }
}