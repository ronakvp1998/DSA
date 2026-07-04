package StreamProblems.flatMapExamples;

import java.util.Arrays;

public class NestedArrayExample {
    public static void main(String[] args) {
        // 1. Create a 2D Array (Array of Arrays)
        String[][] nestedArray = {
                {"Red", "Green"},
                {"Blue", "Yellow"},
                {"Black", "White"}
        };

        // 2. Flatten using flatMap
        String[] flatArray = Arrays.stream(nestedArray)
                // Turn each inner array into a stream and merge them
                .flatMap(Arrays::stream)
                // Note: Arrays::stream is shorthand for innerArray -> Arrays.stream(innerArray)
                .toArray(String[]::new); // Collect back into a 1D String array

        System.out.println("Flattened Array: " + Arrays.toString(flatArray));
        // Output: Flattened Array: [Red, Green, Blue, Yellow, Black, White]
    }
}