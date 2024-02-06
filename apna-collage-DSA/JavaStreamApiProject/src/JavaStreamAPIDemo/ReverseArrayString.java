package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReverseArrayString {

    public static void main(String[] args) {
        reverseArray(new int[]{1,2,3,4,5,});
        reverseString("RonakPanchal");
    }

    private static void reverseArray(int[] ints){
//      1  Using IntStream.range() and mapToObj():
        int[] reversedArray = IntStream.range(0,ints.length)
                .mapToObj(i -> ints[ints.length - 1 -i])
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println(Arrays.toString(reversedArray));

//      2  Using IntStream.rangeClosed() and boxed():
        reversedArray = IntStream.rangeClosed(1,ints.length)
                .mapToObj(i -> ints[ints.length - i])
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println(Arrays.toString(reversedArray));

        reversedArray = IntStream.range(0,ints.length)
                .mapToObj(i -> ints[ints.length - i -1])
                .mapToInt(Integer::intValue)
                .toArray();
        System.out.println(Arrays.toString(reversedArray));


//      3  Using IntStream.iterate():
        reversedArray = IntStream.iterate(ints.length-1,i->i-1)
                .limit(ints.length)
                .map(i -> ints[i])
                .toArray();
        System.out.println(Arrays.toString(reversedArray));


    }

    private static void reverseString(String str){
//      Using IntStream.range() and collect():
        String reversedString = IntStream.range(0,str.length())
                .mapToObj(i -> String.valueOf(str.charAt(str.length()-i-1)))
                .collect(Collectors.joining(""));
        System.out.println(reversedString);

    }
}
