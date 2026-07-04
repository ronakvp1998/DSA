package StreamProblems.flatMapExamples;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecursiveFlattenExample {

    /**
     * Recursive method to deeply flatten any nested collection.
     */
    public static Stream<Object> flattenDeep(Object element) {
        // If the element is a Collection, stream it and recursively call flattenDeep
        if (element instanceof Collection<?>) {
            return ((Collection<?>) element).stream()
                    .flatMap(RecursiveFlattenExample::flattenDeep);
        } else {
            // If it's just a regular object (leaf node), wrap it in a Stream
            return Stream.of(element);
        }
    }

    public static void main(String[] args) {
        // A deeply nested and mixed list: [1, [2, [3, 4], 5], 6]
        List<Object> deeplyNestedList = Arrays.asList(
                1,
                Arrays.asList(
                        2,
                        Arrays.asList(3, 4),
                        5
                ),
                6
        );

        List<Object> completelyFlat = deeplyNestedList.stream()
                // Call our recursive method
                .flatMap(RecursiveFlattenExample::flattenDeep)
                .collect(Collectors.toList());

        System.out.println(completelyFlat);
        // Output: [1, 2, 3, 4, 5, 6]
    }
}