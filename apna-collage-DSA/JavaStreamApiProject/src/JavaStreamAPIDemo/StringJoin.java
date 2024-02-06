package JavaStreamAPIDemo;

import java.util.Arrays;
import java.util.List;

public class StringJoin {

    public static void main(String[] args) {
        joinString();
    }

    private static void joinString() {
        // join the list of elements with -
        List<String> nos = Arrays.asList("1", "2", "3", "4");
        String res = String.join("-",nos);
        System.out.println(res);

    }
}
