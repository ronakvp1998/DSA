package strivers.recursionbacktracking.medium.stacksqueuesproblem;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: Reverse a Stack
 *
 * Write a program to reverse a stack. For the optimal recursive approach,
 * you are not allowed to use any extra space or other data structures like
 * arrays, lists, queues, or other stacks. You can only use the standard stack
 * operations: push(), pop(), peek(), and isEmpty().
 *
 * Constraints:
 * - 1 <= stack.size() <= 10^4
 * - -10^5 <= stack.peek() <= 10^5
 * - The optimal solution must be done in-place using implicit recursion.
 *
 * Input/Output Formats:
 * - Input: A java.util.Stack object.
 * - Output: The exact same Stack object, modified in-place so elements are reversed.
 *
 * Examples:
 *
 * Example 1:
 * Input:  Bottom [1, 2, 3, 4] Top
 * Output: Bottom [4, 3, 2, 1] Top
 *
 * Example 2:
 * Input:  Bottom [10, -5, 8] Top
 * Output: Bottom [8, -5, 10] Top
 *
 * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 */

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ReverseStackMasterclass {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Pure Recursion)
     * ============================================================================
     *
     * Detailed Intuition:
     * To reverse a stack without any explicit auxiliary data structures, we must rely
     * on the system's Call Stack. We recursively pop elements until the stack is empty.
     * As the recursive calls return (unwind), we take each held element and insert it
     * at the absolute *bottom* of the current stack. Since we only have access to the
     * top of the stack, inserting at the bottom requires a second recursive helper
     * method that temporarily pops all existing elements, places the target element
     * at the bottom, and repushes everything else.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2)
     *   For each of the N elements, `insertAtBottom` is called. In the worst case,
     *   `insertAtBottom` pops N elements to reach the bottom, resulting in
     *   1 + 2 + 3 + ... + N operations = O(N^2).
     * - Space Complexity: O(N) Auxiliary Stack Space
     *   The call stack depth will reach N for both `reverseOptimal` and `insertAtBottom`.
     *   O(1) Heap Space since we do not allocate any new collections.
     */
    public static void reverseOptimal(Stack<Integer> stack) {
        // Base case: If stack is empty, return
        if (stack.isEmpty()) {
            return;
        }

        // 1. Hold the top element in the call stack
        int topElement = stack.pop();

        // 2. Recursively reverse the remaining stack
        reverseOptimal(stack);

        // 3. Insert the held element at the very bottom
        insertAtBottom(stack, topElement);
    }

    private static void insertAtBottom(Stack<Integer> stack, int val) {
        // Base case: When stack is empty, pushing places the item at the bottom
        if (stack.isEmpty()) {
            stack.push(val);
            return;
        }

        // Otherwise, pop the current top and hold it
        int top = stack.pop();

        // Recursively dig down to the bottom
        insertAtBottom(stack, val);

        // Put the held elements back on top
        stack.push(top);
    }


    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Using an Auxiliary Queue)
     * ============================================================================
     *
     * Detailed Intuition:
     * If the strict "no extra data structures" rule is relaxed, a Queue provides the
     * most straightforward way to reverse a stack. By popping all elements off the
     * stack, we extract them in reverse-insertion order. Adding them to a Queue (FIFO)
     * preserves this new reversed order. When we dequeue them and push them back onto
     * the stack, the stack is perfectly reversed.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   We pop N elements and push N elements exactly once.
     * - Space Complexity: O(N) Heap Space
     *   We allocate an explicit Queue that scales linearly with the stack size.
     *   O(1) Call Stack Space since there's no recursion.
     */
    public static void reverseBruteForce(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty()) return;

        Queue<Integer> queue = new LinkedList<>();

        // Transfer all elements from Stack to Queue
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }

        // Transfer all elements back from Queue to Stack
        while (!queue.isEmpty()) {
            stack.push(queue.poll());
        }
    }


    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Using Two Auxiliary Stacks)
     * ============================================================================
     *
     * Detailed Intuition:
     * Another classic variant of this interview question allows you to use extra
     * stacks, but no other data structures like Lists or Queues. To reverse elements
     * using stacks, transferring elements from Stack A to Stack B inherently reverses
     * their order. Doing it a second time (B to C) restores the original order but in
     * a new stack. Doing it a third time (C back to A) successfully reverses the
     * elements back in the original stack.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N)
     *   We iterate through the N elements exactly 3 times. O(3N) simplifies to O(N).
     * - Space Complexity: O(N) Heap Space
     *   We allocate two explicit Stacks, taking O(2N) extra space which simplifies to O(N).
     */
    public static void reverseWithTwoStacks(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty()) return;

        Stack<Integer> aux1 = new Stack<>();
        Stack<Integer> aux2 = new Stack<>();

        // Step 1: Transfer from original to aux1 (Reverses order)
        while (!stack.isEmpty()) {
            aux1.push(stack.pop());
        }

        // Step 2: Transfer from aux1 to aux2 (Original order, but in aux2)
        while (!aux1.isEmpty()) {
            aux2.push(aux1.pop());
        }

        // Step 3: Transfer from aux2 back to original (Final Reversed order)
        while (!aux2.isEmpty()) {
            stack.push(aux2.pop());
        }
    }


    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Executing Test Suite: Reverse Stack\n");

        // Test Phase 1: Optimal Recursion
        System.out.println(">>> TESTING PHASE 1: OPTIMAL RECURSIVE APPROACH");
        runTest("Standard Case", new int[]{1, 2, 3, 4}, 1);
        runTest("Empty Stack Edge Case", new int[]{}, 1);
        runTest("Single Element", new int[]{42}, 1);
        System.out.println("--------------------------------------------------\n");

        // Test Phase 2: Brute Force Queue
        System.out.println(">>> TESTING PHASE 2: BRUTE FORCE (QUEUE) APPROACH");
        runTest("Standard Case", new int[]{10, 20, 30, 40}, 2);
        System.out.println("--------------------------------------------------\n");

        // Test Phase 3: Two Stacks
        System.out.println(">>> TESTING PHASE 3: ALTERNATIVE (TWO STACKS) APPROACH");
        runTest("Standard Case", new int[]{5, 10, 15, 20, 25}, 3);
        System.out.println("--------------------------------------------------\n");
    }

    /**
     * Helper method to initialize, run the specified algorithm phase, and print
     * results cleanly using Java 8 Stream API.
     */
    private static void runTest(String testName, int[] elements, int phase) {
        System.out.println("Test: " + testName);

        Stack<Integer> stack = new Stack<>();
        for (int el : elements) {
            stack.push(el);
        }

        // Snapshot initial state
        String initial = stack.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("  Initial (Bottom -> Top): " + initial);

        // Execute corresponding phase algorithm
        switch (phase) {
            case 1:
                reverseOptimal(stack);
                break;
            case 2:
                reverseBruteForce(stack);
                break;
            case 3:
                reverseWithTwoStacks(stack);
                break;
        }

        // Snapshot final state
        String reversed = stack.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("  Reversed(Bottom -> Top): " + reversed);
        System.out.println();
    }
}