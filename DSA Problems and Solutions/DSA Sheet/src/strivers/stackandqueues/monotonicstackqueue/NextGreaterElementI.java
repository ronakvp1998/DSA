package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 496. Next Greater Element I
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * The next greater element of some element x in an array is the first greater
 * element that is to the right of x in the same array.
 * * You are given two distinct 0-indexed integer arrays nums1 and nums2, where
 * nums1 is a subset of nums2.
 * * For each 0 <= i < nums1.length, find the index j such that nums1[i] == nums2[j]
 * and determine the next greater element of nums2[j] in nums2. If there is no
 * next greater element, then the answer for this query is -1.
 * * Return an array ans of length nums1.length such that ans[i] is the next
 * greater element as described above.
 *
 * Example 1:
 * Input: nums1 = [4,1,2], nums2 = [1,3,4,2]
 * Output: [-1,3,-1]
 * Explanation: The next greater element for each value of nums1 is as follows:
 * - 4 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.
 * - 1 is underlined in nums2 = [1,3,4,2]. The next greater element is 3.
 * - 2 is underlined in nums2 = [1,3,4,2]. There is no next greater element, so the answer is -1.
 *
 * Example 2:
 * Input: nums1 = [2,4], nums2 = [1,2,3,4]
 * Output: [3,-1]
 * Explanation: The next greater element for each value of nums1 is as follows:
 * - 2 is underlined in nums2 = [1,2,3,4]. The next greater element is 3.
 * - 4 is underlined in nums2 = [1,2,3,4]. There is no next greater element, so the answer is -1.
 *
 * CONSTRAINTS:
 * 1 <= nums1.length <= nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 10^4
 * All integers in nums1 and nums2 are unique.
 * All the integers of nums1 also appear in nums2.
 * * Follow up: Could you find an O(nums1.length + nums2.length) solution?
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Monotonic Stack + HashMap (O(N + M) Time)
 * Phase 2: Brute Force Approach - Nested Loops (O(N * M) Time)
 * ============================================================================
 */
public class NextGreaterElementI {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Monotonic Stack + HashMap)
     * ============================================================================
     * Detailed Intuition:
     * To achieve the O(N + M) follow-up constraint, we must avoid scanning the
     * array repeatedly. We use a "Decreasing Monotonic Stack".
     * * Imagine standing in a line. If a tall person stands right behind a short person,
     * anyone looking from further ahead will only see the tall person; the short
     * person is completely obstructed. Therefore, the short person can NEVER be the
     * "Next Greater Element" for anyone to their left.
     * * 1. We iterate through `nums2` from right to left.
     * 2. We maintain a stack of numbers. We pop elements from the top of the stack
     * if they are smaller than or equal to the current number (they are "obstructed").
     * 3. After popping, whatever is left on top of the stack is strictly greater
     * than the current number. That is our Next Greater Element!
     * 4. We store this relationship (current number -> next greater) in a HashMap.
     * 5. Finally, we map `nums1` to their answers instantly using the HashMap.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N + M), where N is nums1.length and M is nums2.length.
     * We iterate through nums2 once. Each element is pushed and popped from the
     * stack at most once, making the stack operations amortized O(1).
     * - Space Complexity: O(M) auxiliary heap space to store the Stack and HashMap
     * for all elements in nums2.
     * ============================================================================
     *
     * Here are the core steps for the **Decreasing Monotonic Stack** algorithm, processing from **right to left**:
     * 1. **Initialize:** Create an empty stack and a structure to store your results (like a HashMap).
     * 2. **Iterate Backwards:** Loop through your target array starting from the last element down to the first.
     * 3. **Maintain Monotonicity (The Pop Phase):**
     * * While the stack is *not* empty **AND** the number on top of the stack is smaller than or equal to your current number...
     * * **Pop** it off the stack. *(It's obstructed and no longer useful).*
     * 4. **Record the Answer:**
     * * If the stack is empty: There is no greater element. Record **-1**.
     * * If the stack is not empty: The top of the stack is your Next Greater Element. **Record it**.
     * 5. **Push:** Push your current number onto the stack so it can serve as a candidate for the numbers to its left.
     * 6. **Map Results:** Finally, loop through your query array (`nums1`) and fetch the pre-computed answers from your HashMap.
     */
    public int[] nextGreaterElementOptimal(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreaterMap = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        // Process nums2 from right to left to build the NGE map
        for (int i = nums2.length - 1; i >= 0; i--) {
            int current = nums2[i];

            // Maintain a strictly decreasing monotonic stack
            while (!stack.isEmpty() && stack.peek() <= current) {
                stack.pop();
            }

            // If stack is empty, there is no greater element to the right
            nextGreaterMap.put(current, stack.isEmpty() ? -1 : stack.peek());

            // Push current element to the stack to be a candidate for numbers to its left
            stack.push(current);
        }

        // Use Java 8 Streams to elegantly map nums1 to their computed NGEs
        return Arrays.stream(nums1).map(nextGreaterMap::get).toArray();
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Nested Loops) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The most straightforward way to solve this is to simulate the exact definition.
     * For every number in `nums1`:
     * 1. Find its index in `nums2`.
     * 2. From that index, scan to the right until we find a number strictly greater.
     * 3. If we reach the end of the array without finding one, the answer is -1.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N * M), where N is nums1.length and M is nums2.length.
     * For each element in nums1, we potentially scan the entire nums2 array.
     * - Space Complexity: O(1) auxiliary space, as we only allocate variables to
     * store state (excluding the mandatory output array).
     * ============================================================================
     */
    public int[] nextGreaterElementBruteForce(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {
            int target = nums1[i];
            int nextGreater = -1;
            boolean foundTarget = false;

            // Linear scan to find the target, then the next greater element
            for (int j = 0; j < nums2.length; j++) {
                if (nums2[j] == target) {
                    foundTarget = true;
                }
                // Once target is found, look for the first element strictly greater
                if (foundTarget && nums2[j] > target) {
                    nextGreater = nums2[j];
                    break;
                }
            }
            ans[i] = nextGreater;
        }

        return ans;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        NextGreaterElementI solver = new NextGreaterElementI();

        System.out.println("=== Test Case 1: Standard Input (Example 1) ===");
        int[] nums1_1 = {4, 1, 2};
        int[] nums2_1 = {1, 3, 4, 2};
        System.out.println("Optimal Output:     " + Arrays.toString(solver.nextGreaterElementOptimal(nums1_1, nums2_1)));
        System.out.println("Brute Force Output: " + Arrays.toString(solver.nextGreaterElementBruteForce(nums1_1, nums2_1)));

        System.out.println("\n=== Test Case 2: Subset Input (Example 2) ===");
        int[] nums1_2 = {2, 4};
        int[] nums2_2 = {1, 2, 3, 4};
        System.out.println("Optimal Output:     " + Arrays.toString(solver.nextGreaterElementOptimal(nums1_2, nums2_2)));

        System.out.println("\n=== Test Case 3: Completely Decreasing Array (Worst Case for No NGE) ===");
        int[] nums1_3 = {5, 4, 3};
        int[] nums2_3 = {5, 4, 3, 2, 1};
        System.out.println("Optimal Output:     " + Arrays.toString(solver.nextGreaterElementOptimal(nums1_3, nums2_3)));

        System.out.println("\n=== Test Case 4: Completely Increasing Array (Best Case for Stack) ===");
        int[] nums1_4 = {10, 20};
        int[] nums2_4 = {10, 20, 30, 40};
        System.out.println("Optimal Output:     " + Arrays.toString(solver.nextGreaterElementOptimal(nums1_4, nums2_4)));
    }
}