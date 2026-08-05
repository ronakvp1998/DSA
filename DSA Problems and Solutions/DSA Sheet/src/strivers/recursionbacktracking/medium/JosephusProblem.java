package strivers.recursionbacktracking.medium;


/**
 * ==============================================================================================
 * 🤖 THE JOSEPHUS PROBLEM (Find the Winner of the Circular Game)
 * ==============================================================================================
 *
 * PROBLEM STATEMENT:
 * ------------------
 * There are N people standing in a circle, numbered from 1 to N.
 * Starting from the first person, every K-th person is eliminated in the circle.
 * This elimination continues until only one person remains.
 * The task is to find the position of this last remaining person (safe position).
 *
 * CONSTRAINTS:
 * ------------
 * - 1 <= N <= 10^5
 * - 1 <= K <= 10^5
 *
 * EXAMPLES:
 * ---------
 * Example 1:
 * Input: N = 7, K = 3
 * Output: 4
 * Explanation:
 * 1st round: 3 is eliminated. Circle: 4, 5, 6, 7, 1, 2
 * 2nd round: 6 is eliminated. Circle: 7, 1, 2, 4, 5
 * 3rd round: 2 is eliminated. Circle: 4, 5, 7, 1
 * 4th round: 7 is eliminated. Circle: 1, 4, 5
 * 5th round: 5 is eliminated. Circle: 1, 4
 * 6th round: 1 is eliminated. Circle: 4 (Survivor!)
 *
 * Example 2:
 * Input: N = 5, K = 2
 * Output: 3
 * Explanation:
 * Eliminations in order: 2, 4, 1, 5. The winner is 3.
 *
 * CONCEPTUAL VISUALIZATION:
 * -------------------------
 * While this is solved with a recurrence relation, visually, it acts like a sliding window
 * over a shrinking domain.
 * For N=5, K=2:
 * [1, 2, 3, 4, 5] -> start at index 0, jump to index 1 (value 2). Eliminate 2. Next start index: 1 (value 3)
 * [1, 3, 4, 5]    -> start at index 1, jump to index 2 (value 4). Eliminate 4. Next start index: 2 (value 5)
 * [1, 3, 5]       -> start at index 2, wrap to index 0 (value 1). Eliminate 1. Next start index: 0 (value 3)
 * [3, 5]          -> start at index 0, jump to index 1 (value 5). Eliminate 5. Next start index: 0 (value 3)
 * [3]             -> Winner is 3.
 * ==============================================================================================
 */

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JosephusProblem {

    /**
     * ==========================================================================================
     * PHASE 1: OPTIMAL APPROACH (Iterative Mathematical - Bottom-Up DP)
     * ==========================================================================================
     *
     * Detailed Intuition:
     * This is the recommended and truly optimal approach. The provided recursive code is correct
     * logically but uses O(N) auxiliary stack space, which leads to StackOverflow errors for N > 10^4.
     *
     * By observing the recurrence relation `f(n, k) = (f(n-1, k) + k) % n`, we can compute this
     * iteratively (effectively 1D DP tabulation optimized to O(1) space).
     * Base case: For 1 person, the survivor is at index 0 (0-based indexing).
     * We iteratively compute the survivor's index for 2 people, 3 people, ... up to N people.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We iterate exactly N-1 times.
     * - Space Complexity: O(1). No auxiliary stack space is used; operations happen in-place.
     */
    public static int josephusOptimal(int n, int k) {
        int survivorIndex = 0; // Base case: For n = 1, survivor is at index 0

        // Build up the solution from 2 people to n people
        for (int i = 2; i <= n; i++) {
            survivorIndex = (survivorIndex + k) % i;
        }

        return survivorIndex + 1; // Convert 0-based index to 1-based index
    }

    /**
     * ==========================================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Simulation) - The "Think it" stage
     * ==========================================================================================
     *
     * Detailed Intuition:
     * To truly understand the problem, we just simulate the process. We create a list of 1 to N.
     * We maintain a pointer to the current person. When we count K people, we remove the K-th
     * person from the list. Because the list shrinks, the next start index shifts automatically
     * but we must handle the wrapping around the circular list using the modulo operator.
     *
     * We leverage the Java 8 Stream API here to cleanly initialize our collection of people.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). We iterate N-1 times to remove N-1 people. However, removing an
     *   element from an ArrayList takes O(N) time because subsequent elements must shift.
     * - Space Complexity: O(N) (Heap space). We allocate an ArrayList of size N.
     */
    public static int josephusBruteForce(int n, int k) {
        // Using Java 8 Streams to populate the list [1, 2, 3... N]
        List<Integer> people = IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toList());

        int currentIndex = 0;

        // Eliminate until 1 person remains
        while (people.size() > 1) {
            // Find the index to remove:
            // Add k - 1 (since current index counts as 1) and wrap around using modulo
            currentIndex = (currentIndex + k - 1) % people.size();
            people.remove(currentIndex);
        }

        return people.get(0);
    }

    /**
     * ==========================================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Recursive Mathematical) - The provided code
     * ==========================================================================================
     *
     * Detailed Intuition:
     * This is the mathematical induction approach provided in the prompt.
     * When person 'k' is killed, the circle shrinks to n-1 people. The next person to start
     * counting from is the one immediately after 'k'.
     * If we map the new positions back to the original circle (before elimination), the relation is:
     * Original_Position = (New_Position + k) % n.
     * We recursively ask for the survivor of a game with n-1 people, and map their index back up.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). We make N recursive calls.
     * - Space Complexity: O(N) (Auxiliary Stack space). The recursive depth reaches N, posing a
     *   heavy risk of a StackOverflowError for large inputs.
     */
    public static int josephusRecursive(int n, int k) {
        // Base case: when only one person is left, return 0 (safe position in 0-based indexing)
        if (n == 1) {
            return 0;
        }

        // Recursive case:
        // Safe position formula: (safe position of (n-1) people + k) % n
        return (josephusRecursive(n - 1, k) + k) % n;
    }

    // Wrapper for the recursive function to handle 1-based indexing output
    public static int josephusRecursiveWrapper(int n, int k) {
        return josephusRecursive(n, k) + 1;
    }


    /**
     * ==========================================================================================
     * 4. TESTING SUITE
     * ==========================================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Starting Test Suite for Josephus Problem...\n");

        int[][] testCases = {
                // {N, K, Expected Output}
                {7, 3, 4},   // Standard case provided in prompt
                {5, 2, 3},   // Standard case 2
                {1, 1, 1},   // Edge case: N=1 (Base case testing)
                {10, 5, 3},  // Larger jumps
                {14, 2, 13}, // Power of 2 nearest base (special case for k=2)
                {100, 10, 26} // Larger simulation test
        };

        for (int i = 0; i < testCases.length; i++) {
            int n = testCases[i][0];
            int k = testCases[i][1];
            int expected = testCases[i][2];

            System.out.printf("Test Case %d: N = %d, K = %d (Expected: %d)%n", i + 1, n, k, expected);

            // Phase 1: Optimal
            int optimalRes = josephusOptimal(n, k);
            System.out.printf("   Optimal Approach       -> %d [%s]%n",
                    optimalRes, (optimalRes == expected) ? "PASS" : "FAIL");

            // Phase 2: Brute Force
            int bruteRes = josephusBruteForce(n, k);
            System.out.printf("   Brute Force Simulation -> %d [%s]%n",
                    bruteRes, (bruteRes == expected) ? "PASS" : "FAIL");

            // Phase 3: Recursive (Provided code)
            int recRes = josephusRecursiveWrapper(n, k);
            System.out.printf("   Recursive Approach     -> %d [%s]%n",
                    recRes, (recRes == expected) ? "PASS" : "FAIL");

            System.out.println("---------------------------------------------------------");
        }

        System.out.println("\n✅ All test cases executed.");
    }
}
//
///*
//Problem Statement:
//------------------
//The Josephus Problem is a famous theoretical problem:
//- There are N people standing in a circle, numbered from 1 to N.
//- Starting from the first person, every K-th person is eliminated in the circle.
//- This elimination continues until only one person remains.
//- The task is to find the position of this last remaining person (safe position).
//
//Example:
//--------
//Input: N = 7, K = 3
//Elimination order: 3 → 6 → 2 → 7 → 5 → 1
//Safe position = 4
//
//Approach:
//---------
//We use recursion and mathematical induction.
//
//1. Base Case:
//   - If only one person remains (n = 1), the safe position is 0
//     (0-based index, which corresponds to position 1 in 1-based index).
//
//2. Recursive Case:
//   - Suppose we know the safe position for (n-1) people.
//   - When eliminating every k-th person in n people:
//     new_position = (previous_safe_position + k) % n
//
//   This recurrence ensures correct positioning after each elimination.
//
//3. Convert final answer to 1-based index (since people are usually numbered 1...N).
//
//Time Complexity:
//----------------
//- Each recursive call reduces n by 1.
//- Total recursive calls = O(n).
//- Hence, Time Complexity = O(n).
//
//Space Complexity:
//-----------------
//- Recursive stack depth = O(n).
//- No extra data structures used.
//- Hence, Space Complexity = O(n).
//*/
//
//public class JosephusProblem {
//
//    // Recursive function to find the safe position (0-based index)
//    private static int josephus(int n, int k) {
//        // Base case: when only one person is left, return 0 (safe position in 0-based indexing)
//        if (n == 1) {
//            return 0;
//        }
//
//        // Recursive case:
//        // Safe position formula:
//        // (safe position of (n-1) people + k) % n
//        return (josephus(n - 1, k) + k) % n;
//    }
//
//    public static void main(String[] args) {
//        int n = 7;  // Number of people in the circle
//        int k = 3;  // Step size (every 3rd person is eliminated)
//
//        // Convert result from 0-based index to 1-based index by adding +1
//        int safePosition = josephus(n, k) + 1;
//
//        System.out.println("The safe position is: " + safePosition);
//    }
//}
