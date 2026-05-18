package strivers.binarysearch.bsonanswers;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * Allocate Minimum Number of Pages
 * Solved | Hard
 * * * PROBLEM STATEMENT:
 * Given an array 'arr' of integer numbers, 'arr[i]' represents the number of
 * pages in the 'i-th' book. There are a 'm' number of students, and the task
 * is to allocate all the books to the students.
 * * Allocate books in such a way that:
 * 1. Each student gets at least one book.
 * 2. Each book should be allocated to only one student.
 * 3. Book allocation should be in a contiguous manner.
 * * You have to allocate the book to 'm' students such that the maximum number
 * of pages assigned to a student is minimum. If the allocation of books is not
 * possible, return -1.
 * * * EXAMPLES:
 * Example 1:
 * Input Format: n = 4, m = 2, arr[] = {12, 34, 67, 90}
 * Result: 113
 * Explanation: The allocation of books will be 12, 34, 67 | 90. One student
 * will get the first 3 books and the other will get the last one.
 * * Example 2:
 * Input Format: n = 5, m = 4, arr[] = {25, 46, 28, 49, 24}
 * Result: 71
 * Explanation: The allocation of books will be 25, 46 | 28 | 49 | 24.
 * * * CONSTRAINTS:
 * - 1 <= n <= 10^5
 * - 1 <= arr[i] <= 10^9
 * - 1 <= m <= 10^5
 * ============================================================================
 */
    public class AllocateMinimumNumberOfPages {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Answer Space)
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Base Case: If the number of students `m` is greater than the number
     * of books `n`, allocation is impossible (since a student must get at least
     * one book). Return -1.
     * 2. Identify the Search Space:
     * - The absolute minimum possible pages a student could be forced to read
     * is the largest single book in the array (`max(arr)`). Why? Because that
     * book cannot be split; whoever gets it reads at least that many pages.
     * - The absolute maximum possible pages is if one poor student has to read
     * all the books (`sum(arr)`).
     * 3. Apply Binary Search: Calculate `mid` as the proposed maximum page limit.
     * 4. Greedily Check Feasibility: Use a helper function `countStudents` to
     * iterate through the books. Keep adding pages to a student until adding
     * the next book would exceed `mid`. If it exceeds, hand the book to the
     * next student.
     * 5. Decision Logic:
     * - If `studentsRequired <= m`, this `mid` is a valid capacity limit! We
     * record it, but aggressively try to squeeze the limit tighter by searching
     * the left half (`high = mid - 1`).
     * - If `studentsRequired > m`, the capacity `mid` is too small, forcing us
     * to use too many students. We must relax the limit by searching the right
     * half (`low = mid + 1`).
     * * * DETAILED INTUITION:
     * This is the classic "Minimax" pattern—minimizing the maximum capacity.
     * Because the required number of students drops monotonically as we increase
     * the allowed page limit, we can completely abandon linear searching.
     * Binary search allows us to skip massive chunks of invalid capacities.
     * * Note on `countStudents <= m`: Even if the helper returns fewer students
     * than `m`, it is still a valid configuration. We can always take a student's
     * pile and split it to artificially introduce another student without
     * increasing the maximum page load.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * log(Sum - Max)), where N is the number of books.
     * Finding the Max and Sum takes O(N). The binary search range is `Sum - Max`,
     * so it halves `log(Sum - Max)` times. Each check requires an O(N) iteration
     * over the array.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative binary search, no recursion).
     * - Heap Space: O(1) (No new dynamic objects created).
     */
    public static int findPagesOptimal(int[] arr, int n, int m) {
        if (m > n) {
            return -1; // Impossible to allocate
        }

        int low = 0;
        long high = 0; // Use long to prevent integer overflow on the sum

        for (int pages : arr) {
            low = Math.max(low, pages);
            high += pages;
        }

        long optimalResult = -1;

        while (low <= high) {
            long mid = low + (high - low) / 2;

            if (countStudents(arr, mid) <= m) {
                optimalResult = mid; // Valid, but try to find a smaller maximum
                high = mid - 1;
            } else {
                low = (int) mid + 1; // Limit too tight, need more pages per student
            }
        }

        return (int) optimalResult;
    }

    // Helper method to simulate book allocation
    private static int countStudents(int[] arr, long pageLimit) {
        int studentsRequired = 1;
        long currentPages = 0;

        for (int pages : arr) {
            if (currentPages + pages > pageLimit) {
                // Current student cannot take this book, assign to a new student
                studentsRequired++;
                currentPages = pages;
            } else {
                // Current student takes the book
                currentPages += pages;
            }
        }

        return studentsRequired;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Validate `m > n` edge case.
     * 2. Find `low` (max element) and `high` (sum of elements).
     * 3. Linearly scan every possible page limit starting from `low` up to `high`.
     * 4. For each limit, simulate the allocation.
     * 5. The first limit that allows allocation within `<= m` students is our
     * guaranteed minimum, so we return it immediately.
     * * * DETAILED INTUITION:
     * This is the "trial and error" method. We start with the tightest possible
     * constraint (the largest single book) and slowly relax the constraint one
     * page at a time. The moment our helper function says "Yes, we can do this
     * with m or fewer students", we stop. Because we started from the smallest
     * possible limit, it is guaranteed to be the minimum.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(N * (Sum - Max)). The loop runs `Sum - Max` times,
     * and for every iteration, we do an O(N) array traversal. If the array
     * contains large numbers, this will strictly result in Time Limit Exceeded (TLE).
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int findPagesBruteForce(int[] arr, int n, int m) {
        if (m > n) {
            return -1;
        }

        int low = 0;
        long high = 0;

        for (int pages : arr) {
            low = Math.max(low, pages);
            high += pages;
        }

        // Linear scan over the answer space
        for (long limit = low; limit <= high; limit++) {
            if (countStudents(arr, limit) <= m) {
                return (int) limit;
            }
        }

        return (int) low;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * Dynamic Programming (Matrix Chain Multiplication Partitioning):
     * - This problem can theoretically be solved using DP by breaking the array
     * into `m` contiguous partitions.
     * - Approach: `dp[i][j]` stores the minimum of the maximum sum of `j`
     * partitions for the first `i` books.
     * - Drawback: DP requires iterating through the array to place partition
     * boundaries, resulting in a time complexity of O(m * n^2). Given that
     * `n` can be up to 10^5, `n^2` is 10^10 operations. This makes DP completely
     * unviable for competitive programming constraints compared to Binary Search.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Thorough testing against standard cases, impossible states, and boundaries.
     */
    public static void main(String[] args) {
        System.out.println("Running Allocate Minimum Number of Pages Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[] arr1 = {12, 34, 67, 90};
        int n1 = 4, m1 = 2;
        runTestCase(1, arr1, n1, m1, 113);

        // Test Case 2: Standard case (Example 2)
        int[] arr2 = {25, 46, 28, 49, 24};
        int n2 = 5, m2 = 4;
        runTestCase(2, arr2, n2, m2, 71);

        // Test Case 3: Impossible Case (m > n)
        int[] arr3 = {10, 20, 30};
        int n3 = 3, m3 = 4;
        runTestCase(3, arr3, n3, m3, -1);

        // Test Case 4: Edge Case - 1 Student (Must read everything)
        int[] arr4 = {10, 20, 30, 40};
        int n4 = 4, m4 = 1;
        runTestCase(4, arr4, n4, m4, 100);

        // Test Case 5: Edge Case - Students == Books (Max element is the bottleneck)
        int[] arr5 = {10, 20, 30, 40};
        int n5 = 4, m5 = 4;
        runTestCase(5, arr5, n5, m5, 40);

        // Test Case 6: Integer Overflow Prevention
        // Very large numbers that sum up beyond Integer.MAX_VALUE
        int[] arr6 = {1000000000, 1000000000, 1000000000};
        int n6 = 3, m6 = 2;
        runTestCase(6, arr6, n6, m6, 2000000000);
    }

    private static void runTestCase(int testNumber, int[] arr, int n, int m, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = findPagesOptimal(arr, n, m);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Input: arr = " + java.util.Arrays.toString(arr) + ", m = " + m);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}