package StreamProblems.flatMapExamples;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NestedListExample {
    public static void main(String[] args) {
        // 1. Create a List of Lists
        List<List<String>> nestedLists = Arrays.asList(
                Arrays.asList("Apple", "Banana"),
                Arrays.asList("Cherry", "Date"),
                Arrays.asList("Elderberry", "Fig")
        );

        // 2. Flatten using flatMap
        List<String> flatList = nestedLists.stream()
                // Turn each inner list into a stream and merge them
                .flatMap(Collection::stream)
                // Note: Collection::stream is shorthand for list -> list.stream()
                .collect(Collectors.toList());

        System.out.println("Flattened List: " + flatList);
        // Output: Flattened List: [Apple, Banana, Cherry, Date, Elderberry, Fig]
    }
}