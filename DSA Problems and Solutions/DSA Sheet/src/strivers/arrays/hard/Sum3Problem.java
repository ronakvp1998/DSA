package strivers.arrays.hard;
import java.util.*;

/**
 * ============================================================================
 * MASTERCLASS: 3 SUM (Find Unique Triplets That Add Up To Zero)
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * --- 1. HEADER & PROBLEM CONTEXT ---
 * Problem Statement:
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
 * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * Notice that the solution set must not contain duplicate triplets.
 * * Input/Output Formats:
 * Input: An array of N integers.
 * Output: A List of Lists containing the unique triplets.
 * * Constraints (Standard LeetCode):
 * - 3 <= nums.length <= 3000
 * - -10^5 <= nums[i] <= 10^5
 *
 * * Example 1:
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation: Out of all possible unique triplets possible, [-1,-1,2] and [-1,0,1]
 * satisfy the condition of summing up to zero with i!=j!=k
 *
 * * Example 2:
 * Input: nums = [-1,0,1,0]
 * Output: [[-1,0,1]] (Note: Adjusted to reflect standard unique triplet logic)
 * Explanation: The only unique triplet that sums to zero is [-1,0,1].
 * * Conceptual Visualization (Two Pointer Approach):
 * Array: [-1, 0, 1, 2, -1, -4]
 * Sorted: [-4, -1, -1, 0, 1, 2]
 *          ^    ^            ^
 *          i   left        right
 * * Fixing 'i', we reduce the 3Sum problem to a 2Sum problem for the remaining array,
 * which can be efficiently solved using two pointers on a sorted array.
 * ============================================================================
 */
public class Sum3Problem {

    /**
     * ========================================================================
     * PHASE 1: BEST AND RECOMMENDED APPROACH (Sorting + Two Pointers)
     * ========================================================================
     * Approach and Steps:
     * 1. Sort the input array. This is crucial for both the two-pointer technique
     * and for easily skipping duplicate elements.
     * 2. Iterate through the array with a pointer 'i' from 0 to n-3.
     * 3. If nums[i] > 0, we can break early because all subsequent numbers are
     * positive, making it impossible to sum to 0.
     * 4. Skip duplicates for 'i' to ensure unique triplets.
     * 5. Use two pointers, 'left' (i + 1) and 'right' (n - 1).
     * 6. Calculate the sum. If sum == 0, record the triplet, then move both pointers
     * inward while skipping duplicates to avoid duplicate triplets.
     * 7. If sum < 0, increment 'left'. If sum > 0, decrement 'right'.
     * * Detailed Intuition:
     * The brute force requires three nested loops. By sorting the array, we can
     * fix one number (nums[i]) and find the other two numbers (which must sum to
     * -nums[i]) in a single linear scan using the two-pointer approach. Sorting
     * also elegantly handles the "unique triplets" constraint by grouping duplicates.
     * * Complexity Analysis:
     * - Time (O): O(N log N) for sorting + O(N^2) for the nested loops = O(N^2).
     * - Space (O): O(1) auxiliary space (excluding the output array). Depending on
     * the sorting algorithm under the hood (like TimSort/Dual-Pivot Quicksort),
     * it might take O(N) or O(log N) stack space.
     * ========================================================================
     */
    public static List<List<Integer>> threeSumOptimal(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) return result;

        Arrays.sort(nums); // Step 1: Sort the array

        for (int i = 0; i < nums.length - 2; i++) {
            // Step 3: Early optimization
            if (nums[i] > 0) break;

            // Step 4: Skip duplicates for 'i'
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Step 6: Skip duplicates for 'left' and 'right'
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    // Move both pointers inward after finding a valid triplet
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++; // Need a larger sum, move left pointer rightwards
                } else {
                    right--; // Need a smaller sum, move right pointer leftwards
                }
            }
        }
        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: BRUTE FORCE APPROACH - The "Think it" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Use three nested loops to generate all possible triplets (i, j, k).
     * 2. Check if nums[i] + nums[j] + nums[k] == 0.
     * 3. To ensure uniqueness, sort the valid triplet and insert it into a HashSet.
     * 4. Convert the HashSet back to a List and return.
     * * Detailed Intuition:
     * This is the most foundational way to solve the problem. We check every single
     * combination. However, because order doesn't matter for the triplets, we must
     * sort each valid triplet before adding it to a Set to filter out duplicates
     * (e.g., [-1, 0, 1] and [0, -1, 1] are the same).
     * * Complexity Analysis:
     * - Time (O): O(N^3) for the three nested loops. Storing in the HashSet takes
     * O(log(unique_triplets)) time per valid triplet. Overall Time: O(N^3).
     * - Space (O): O(unique_triplets) to store the triplets in the HashSet.
     * Auxiliary space is O(1).
     * ========================================================================
     */
    public static List<List<Integer>> threeSumBruteForce(int[] nums) {
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> temp = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(temp); // Sort to ensure uniqueness in Set
                        uniqueTriplets.add(temp);
                    }
                }
            }
        }
        return new ArrayList<>(uniqueTriplets);
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Hashing) - The "Trade-off" stage
     * ========================================================================
     * Approach and Steps:
     * 1. Iterate through the array with 'i' (fixing the first element).
     * 2. Use an inner loop 'j' starting from i + 1.
     * 3. Maintain a HashSet for the elements between i and j.
     * 4. We need a third element 'k' such that nums[k] = -(nums[i] + nums[j]).
     * 5. If this element exists in our HashSet, we found a triplet.
     * 6. Sort the triplet and add to a global Set to maintain uniqueness.
     * 7. Add nums[j] to the inner HashSet for future 'j' iterations.
     * * Detailed Intuition:
     * We transition from O(N^3) to O(N^2) by trading space for time. Instead of
     * looping for the third element, we use a Hash Set to look it up in O(1) time.
     * We still need an outer Set to manage duplicate triplets because we haven't
     * sorted the initial array to skip duplicates efficiently.
     * * Complexity Analysis:
     * - Time (O): O(N^2 * log(unique_triplets)) -> The N^2 is for the nested loops,
     * and the log factor is for inserting into the global Set.
     * - Space (O): O(N) for the inner HashSet + O(unique_triplets) for the global Set.
     * ========================================================================
     */
    public static List<List<Integer>> threeSumHashing(int[] nums) {
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        int n = nums.length;

        for (int i = 0; i < n - 1; i++) {
            Set<Integer> hashSet = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                int needed = -(nums[i] + nums[j]);
                if (hashSet.contains(needed)) {
                    List<Integer> temp = Arrays.asList(nums[i], nums[j], needed);
                    Collections.sort(temp);
                    uniqueTriplets.add(temp);
                }
                hashSet.add(nums[j]);
            }
        }
        return new ArrayList<>(uniqueTriplets);
    }

    /**
     * ========================================================================
     * PHASE 4: TESTING SUITE
     * ========================================================================
     * A comprehensive main method to validate all approaches against standard,
     * duplicate-heavy, and edge-case inputs.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== 3 SUM MASTERCLASS TESTING SUITE ===");

        int[][] testCases = {
                {-1, 0, 1, 2, -1, -4},  // Standard Case 1
                {-1, 0, 1, 0},          // Standard Case 2
                {0, 0, 0, 0},           // Edge Case: All zeros
                {1, 2, -2, -1},         // Edge Case: No valid triplets
                {},                     // Edge Case: Empty array
                {0, 0}                  // Edge Case: Less than 3 elements
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] tc = testCases[i];
            System.out.println("\nTest Case " + (i + 1) + ": " + Arrays.toString(tc));

            // Test Brute Force
            long start1 = System.nanoTime();
            List<List<Integer>> res1 = threeSumBruteForce(tc);
            long end1 = System.nanoTime();
            System.out.println("Brute Force Output: " + res1 + " (Time: " + (end1 - start1) / 1000 + " us)");

            // Test Hashing
            long start2 = System.nanoTime();
            List<List<Integer>> res2 = threeSumHashing(tc);
            long end2 = System.nanoTime();
            System.out.println("Hashing Output:     " + res2 + " (Time: " + (end2 - start2) / 1000 + " us)");

            // Test Optimal (Two Pointers)
            // Note: Optimal modifies the array (sorts it), so we pass a clone
            int[] tcClone = tc.clone();
            long start3 = System.nanoTime();
            List<List<Integer>> res3 = threeSumOptimal(tcClone);
            long end3 = System.nanoTime();
            System.out.println("Optimal Output:     " + res3 + " (Time: " + (end3 - start3) / 1000 + " us)");

            // Verification
            boolean isMatch = res1.size() == res2.size() && res2.size() == res3.size();
            System.out.println("Sanity Check Passed: " + isMatch);
        }
    }
}