package JavaStreamAPIDemo;

import java.util.stream.IntStream;

public class IntStreamRange {

    public static void main(String[] args) {
        skipLimit();
    }

    private static void skipLimit() {
        IntStream.range(1,10).forEach(System.out::print);
        System.out.println();
//        123456789

        // array of 1 to 10 print only 2 to 9
        // IntStream.range(1,10) -> start inclusive end exclusive
        IntStream.range(1,10).skip(1).limit(10).forEach(System.out::print);
        System.out.println();
//        23456789

        // IntStream.rangeClosed(1,10) -> start end both inclusive
        IntStream.rangeClosed(1,10).skip(1).limit(10).forEach(System.out::print);
        System.out.println();
//        2345678910

    }
}