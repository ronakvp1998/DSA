package Demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoTest {

    public static void main(String[] args) {
        String s = "abcdabewrreabdsa";
        String part = "ab";
        String[] sarr = s.split("ab");
        System.out.println(Arrays.toString(sarr));
//        System.out.println(Stream.of(s.split("")).filter(e-> !e.contains("ab")).collect(Collectors.toList()));
    }
}
