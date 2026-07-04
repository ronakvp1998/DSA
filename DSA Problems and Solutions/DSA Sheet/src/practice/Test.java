package practice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {

    public static List<Integer> solveBruteForce(int[] A, int[] B, int K) {
        return Arrays.stream(A)
                .boxed()
                .flatMap(a -> Arrays.stream(B).mapToObj(b -> a+b))
                .sorted(Collections.reverseOrder())
                .limit(K)
                .collect(Collectors.toList());
    }



}
