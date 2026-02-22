package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 45. Jump Game II (Medium)
 * ==================================================================================================
 * You are given a 0-indexed array of integers nums of length n. You are initially positioned at index 0.
 * Each element nums[i] represents the maximum length of a forward jump from index i.
 * Return the minimum number of jumps to reach index n - 1.
 * The test cases are generated such that you can reach index n - 1.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 *
 * Example 2:
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 * ==================================================================================================
 * APPROACH 1: GREEDY ALGORITHM (Implicit BFS / Window Tracking) - OPTIMAL
 * ==================================================================================================
 * To find the MINIMUM jumps, we can think of this as expanding windows.
 * We track the 'currentEnd' of our current level. As we iterate, we continuously calculate
 * the 'farthest' point we can reach for the NEXT level. When we hit 'currentEnd', we jump.
 * Time Complexity: O(N)
 * Space Complexity: O(1)
 * ==================================================================================================
 * APPROACH 2: BRUTE FORCE RECURSIVE (Explores all paths)
 * ==================================================================================================
 * This approach tries every single valid combination of jumps to reach the end.
 * From any given position, it branches out to try jumping 1 step, 2 steps, all the way to nums[position] steps.
 * It recursively calculates the jumps needed from each of those landing spots, adds 1 (for the current jump),
 * and returns the absolute minimum among all paths.
 * * Drawback: Because it calculates overlapping subproblems repeatedly, it has an exponential time complexity.
 * It will trigger a Time Limit Exceeded (TLE) error on LeetCode for large arrays.
 * Time Complexity: O(max_jump^N) (Exponential)
 * Space Complexity: O(N) due to the call stack depth.
 * ==================================================================================================
 */
public class JumpGameII {

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 1, 1, 4};
        int[] nums2 = {2, 3, 0, 1, 4};
        int[] nums3 = {0};

        System.out.println("--- TESTING OPTIMAL GREEDY APPROACH ---");
        System.out.println("Test Case 1 (Expected: 2) -> Result: " + jump(nums1));
        System.out.println("Test Case 2 (Expected: 2) -> Result: " + jump(nums2));
        System.out.println("Test Case 3 (Expected: 0) -> Result: " + jump(nums3));

        System.out.println("\n--- TESTING BRUTE FORCE RECURSIVE APPROACH ---");
        System.out.println("Test Case 1 (Expected: 2) -> Result: " + jumpRecursive(nums1));
        System.out.println("Test Case 2 (Expected: 2) -> Result: " + jumpRecursive(nums2));
        System.out.println("Test Case 3 (Expected: 0) -> Result: " + jumpRecursive(nums3));
    }

    /**
     * APPROACH 1: OPTIMAL GREEDY (Implicit BFS / Window Tracking)
     * ==============================================================================================
     * Conceptually, this approach groups the array into "levels" or "windows", exactly like a
     * Breadth-First Search (BFS), but without the overhead of a Queue.
     * * - Level 0: The starting index (0).
     * - Level 1: All indices you can reach in exactly 1 jump from Level 0.
     * - Level 2: All indices you can reach in exactly 1 jump from anywhere in Level 1.
     * * How it works:
     * 1. 'currentEnd' marks the boundary of our current level.
     * 2. As we iterate through the elements in the current level, we continuously calculate
     * the 'farthest' point we could reach for the NEXT level.
     * 3. When our loop index 'i' hits the 'currentEnd', we have explored all options for this
     * level. We are now forced to make a jump to proceed, so we increment our jump count
     * and update 'currentEnd' to the 'farthest' point we found.
     * * Time Complexity: O(N)   - We scan the array exactly once.
     * Space Complexity: O(1)  - Only primitive variables are used.
     * ==============================================================================================
     */
    public static int jump(int[] nums) {
        // Edge Case: If the array has 1 or 0 elements, we are already at the destination.
        if (nums.length <= 1) return 0;

        int jumps = 0;      // Total number of jumps made so far
        int currentEnd = 0; // The farthest index we can reach with our CURRENT number of jumps
        int farthest = 0;   // The farthest index we can reach with the NEXT jump

        // We iterate only up to the second-to-last element (nums.length - 1).
        // Reason: If we are standing at the last index, we are done. We don't need to jump
        // from it. Processing the last index could unnecessarily trigger an extra jump.
        for (int i = 0; i < nums.length - 1; i++) {

            // 1. Greedily track the maximum reach from our current position
            farthest = Math.max(farthest, i + nums[i]);

            // 2. If we have finished exploring all elements in the current jump window...
            if (i == currentEnd) {

                // ...we MUST commit to a jump to move into the next window.
                jumps++;

                // The next window's boundary is the farthest point we just discovered.
                currentEnd = farthest;

                // 3. Early Exit Optimization:
                // If our new window boundary already covers or exceeds the final destination,
                // we are guaranteed to reach the end. No need to process the rest of the array.
                if (currentEnd >= nums.length - 1) {
                    break;
                }
            }
        }

        return jumps;
    }
    /*
Imagine you are playing a board game where each square tells you the maximum number of spaces you can jump forward. You want to reach the end in the fewest turns possible.
Instead of guessing and checking every path, you use a radar (farthest).
As you walk through the squares you can currently reach (your currentEnd), your radar is constantly scanning the horizon: "If I jump from THIS square, how far could I go?"
You don't actually make a jump immediately. You keep walking and scanning until you reach the absolute edge of your current reachable area (i == currentEnd).
Once you hit that boundary, you say: "Okay, my turn is over.
Out of all the squares I just scanned, the best one takes me to index X."
You count one jump (jumps++), set your new boundary to X (currentEnd = farthest), and repeat until your boundary includes the final square.

2. The Dry Run
Let's trace the algorithm using the standard LeetCode example:
nums = [2, 3, 1, 1, 4]
Target index to reach: nums.length - 1 = 4

Initial State:
jumps = 0
currentEnd = 0 (We start at index 0, so our first "window" ends exactly where we stand)
farthest = 0

showing BFS levels and greedy choices]

Iteration 1: i = 0 (Value = 2)
Update Radar: What is the farthest we can reach from here?
farthest = Math.max(0, 0 + 2)  👉 farthest = 2
Check Boundary: Are we at the end of our current window? (i == currentEnd) -> (0 == 0). YES.
We must jump to leave the starting line.
jumps++ 👉 jumps = 1
currentEnd = farthest 👉 currentEnd = 2
Early Exit Check: Is currentEnd (2) >= Target (4)? No.
(Now, our current window of exploration is from index 1 to index 2. We will scan both before committing to our next jump).

Iteration 2: i = 1 (Value = 3)
Update Radar: What is the farthest we can reach from here?
farthest = Math.max(2, 1 + 3) 👉 farthest = 4
Check Boundary: Are we at the end of our current window? (i == currentEnd) -> (1 == 2). NO.
We don't jump yet. We just remember that jumping from here could get us to index 4.

Iteration 3: i = 2 (Value = 1)
Update Radar: What is the farthest we can reach from here?
farthest = Math.max(4, 2 + 1) 👉 farthest = 4 (It doesn't beat our previous best).
Check Boundary: Are we at the end of our current window? (i == currentEnd) -> (2 == 2). YES.
We have finished exploring our level 1 window. Time to commit to the best jump we found!
jumps++ 👉 jumps = 2
currentEnd = farthest 👉 currentEnd = 4

Early Exit Check: Is currentEnd (4) >= Target (4)? YES!

We trigger the break statement and exit the loop early.

Final Output:
The loop terminates, and we return jumps = 2.
     */

    /**
     * APPROACH 2: BRUTE FORCE RECURSIVE (Wrapper Method)
     * Kicks off the recursive helper from the starting index (0).
     */
    public static int jumpRecursive(int[] nums) {
        Integer[] memo = new Integer[nums.length];
        return minJumpsFromPosition(0, nums,memo);
    }

    /**
     * Helper method for the recursive approach.
     * @param position The current index we are standing on.
     * @param nums The original array of maximum jump lengths.
     * @return The absolute minimum jumps required to reach the end from this position.
     */
    private static int minJumpsFromPosition(int position, int[] nums,Integer []memo) {
        // BASE CASE:
        // If our current position is at or beyond the last index, we need exactly 0 more jumps!
        if (position >= nums.length - 1) {
            return 0;
        }
        // ==========================================
        // If this position is NOT null in our cache, we have been here before!
        // Return the saved answer immediately instead of running the loop.
        if (memo[position] != null) {
            return memo[position];
        }
        // Initialize the minimum jumps for this position to a safely large number.
        // We do NOT use Integer.MAX_VALUE because adding 1 to it later would cause an integer overflow,
        // turning it into a negative number and breaking the Math.min comparison.
        int minJumps = 10000;

        // Determine the maximum jump size allowed from our current position.
        int maxJumpLength = nums[position];

        // RECURSIVE STEP:
        // Try every possible jump size from 1 up to maxJumpLength.
        for (int jumpSize = 1; jumpSize <= maxJumpLength; jumpSize++) {

            // Recursively calculate how many jumps it takes to finish the game from our NEW landing spot.
            int jumpsFromNextPos = minJumpsFromPosition(position + jumpSize, nums,memo);

            // Update the minimum jumps found so far.
            // We add 1 to 'jumpsFromNextPos' because we just took 1 jump to get to that next position.
            minJumps = Math.min(minJumps, 1 + jumpsFromNextPos);
        }
        // CACHE THE RESULT
        // ==========================================
        // Before returning our hard-earned answer, save it in the memo array!
        memo[position] = minJumps;

        return minJumps;
    }
}
/**
 * ==================================================================================================
 * COMPLEXITY ANALYSIS FOR ALL APPROACHES
 * ==================================================================================================
 * * APPROACH 1: GREEDY ALGORITHM (Implicit BFS / Window Tracking)
 * --------------------------------------------------------------------------------------------------
 * * Time Complexity: O(N)
 * Reasoning: We iterate through the 'nums' array exactly one time using a single loop.
 * Inside the loop, we only perform basic arithmetic and variable assignments (like Math.max),
 * which all take constant O(1) time.
 * * Space Complexity: O(1)
 * Reasoning: We only allocate three primitive integer variables (jumps, currentEnd, farthest)
 * to keep track of our state. The memory used remains constant regardless of the array size.
 *
 * * APPROACH 2: BRUTE FORCE RECURSIVE (Without Memoization)
 * --------------------------------------------------------------------------------------------------
 * * Time Complexity: O(K^N) -> Exponential (Where K is the max jump length)
 * Reasoning: At every index, the algorithm branches out up to K times (trying a jump of 1, 2,
 * up to nums[i]). For an array of length N, the recursion tree explodes in size, calculating
 * the exact same overlapping paths millions of times.
 * * Space Complexity: O(N)
 * Reasoning: We don't create any arrays, but the recursion call stack takes up memory. In the
 * worst-case scenario (jumping 1 step at a time), the recursive functions will stack N levels
 * deep in memory before hitting the base case.
 *
 * * APPROACH 3: TOP-DOWN DYNAMIC PROGRAMMING (Memoization)
 * --------------------------------------------------------------------------------------------------
 * * Time Complexity: O(N^2)
 * Reasoning: Thanks to the cache, we calculate the answer for each index exactly ONCE.
 * There are N indices to process. For each index, we run a loop up to nums[i] times (which in
 * the worst case is N jumps). Calculating N states, doing up to N work per state = O(N^2).
 * * Space Complexity: O(N)
 * Reasoning: We use O(N) extra space for two reasons:
 * 1. The `Integer[] memo` array of size N.
 * 2. The recursion call stack, which can still go up to N levels deep in the worst case.
 * ==================================================================================================
 */