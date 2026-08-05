package strivers.recursionbacktracking.medium.stacksqueuesproblem;

/**
 * ============================================================================
 * 🤖 Java-Centric DSA Prompt Template: Senior Interviewer & Evaluator
 * ============================================================================
 *
 * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem Statement: Sort a Stack using Recursion
 *
 * Given a stack of integers, sort it in ascending order (where the largest
 * element is at the top of the stack). You are not allowed to use any
 * additional data structures like arrays, lists, or queues. You can only use
 * standard stack operations: push(), pop(), peek(), isEmpty().
 *
 * Constraints:
 * - 1 <= stack.size() <= 100
 * - -1000 <= stack.peek() <= 1000
 * - Must be solved using recursion (implicit call stack).
 *
 * Input/Output Formats:
 * - Input: A java.util.Stack object.
 * - Output: The same Stack object, mutated to be sorted.
 *
 * Examples:
 *
 * Example 1:
 * Input:  Bottom [3, 1, 4, 2] Top
 * Output: Bottom [1, 2, 3, 4] Top
 *
 * Example 2:
 * Input:  Bottom [-5, 9, 0, -2, 7] Top
 * Output: Bottom [-5, -2, 0, 7, 9] Top
 *
 * ============================================================================
 * 2.2. PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 */

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SortStackRecursion {

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Hybrid Recursion with Temporary List)
     * ============================================================================
     *
     * Detailed Intuition:
     * This is based on the logic you initially attempted. It uses recursion to strip
     * the stack, but uses a temporary List (iteratively) to handle the insertion logic.
     * This avoids the infinite loops of iterative push/pop cycles, though it technically
     * violates strict "no extra data structure" rules.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2) - Iterating through the stack via pops/pushes per depth.
     * - Space Complexity: O(N) Auxiliary Call Stack + O(N) Heap Space for the Lists
     *   created at each level of recursion. Total space is heavily sub-optimal compared
     *   to Phase 1.
     */
    public static void sortStackHybrid(Stack<Integer> stack) {
        // Base cases
        if (stack.isEmpty() || stack.size() == 1) {
            return;
        }

        int a = stack.pop();
        sortStackHybrid(stack);

        List<Integer> temp = new ArrayList<>();

        // Safely extract elements larger than 'a'
        while (!stack.isEmpty() && stack.peek() > a) {
            temp.add(stack.pop());
        }

        stack.push(a);

        // Push back in reverse order to maintain stack properties
        for (int i = temp.size() - 1; i >= 0; i--) {
            stack.push(temp.get(i));
        }
    }

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Pure Recursion)
     * ============================================================================
     *
     * Detailed Intuition:
     * We need to sort the stack without explicitly using other data structures.
     * However, recursion naturally provides us with an implicit "stack" (the Call Stack).
     *
     * 1. Empty the stack: Recursively pop elements until the stack is empty. The
     *    popped elements are safely held in the local variables of the Call Stack.
     * 2. Insert Sorted: As the recursive calls return, we take the held element
     *    and insert it into the (now sorted) stack. If the element belongs further
     *    down, we use a *second* recursive method to temporarily pop elements off
     *    until we find the correct spot, push the target element, and then push
     *    the held elements back on top.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2) - In the worst case (reverse sorted stack), the
     *   insert method takes O(N) time. Since we call insert N times, total time is O(N^2).
     * - Space Complexity: O(N) Auxiliary Stack Space for the recursion depth.
     *   O(1) Heap Space since we allocate no new objects.
     */
    public static void sortStackOptimal(Stack<Integer> stack) {
        // Base case: An empty stack is inherently sorted.
        if (stack.isEmpty()) {
            return;
        }

        // 1. Hold the top element in the call stack
        int topElement = stack.pop();

        // 2. Recursively sort the remaining elements
        sortStackOptimal(stack);

        // 3. Insert the held element back in its sorted position
        insertSorted(stack, topElement);
    }

    private static void insertSorted(Stack<Integer> stack, int element) {
        // Base case: stack is empty, or element is greater than the top
        if (stack.isEmpty() || stack.peek() <= element) {
            stack.push(element);
            return;
        }

        // If the element is smaller than the top, we need to push it further down.
        // Hold the current top in the call stack.
        int temp = stack.pop();

        // Recursively find the right spot
        insertSorted(stack, element);

        // Put the temporarily removed element back on top
        stack.push(temp);
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Iterative with Auxiliary Stack)
     * ============================================================================
     *
     * Detailed Intuition:
     * If the strict "pure recursion" constraint is relaxed but we still must use stacks,
     * we can use an explicit auxiliary stack. We pop an element from the input stack,
     * and while the auxiliary stack's top is greater than our element, we shift elements
     * from the auxiliary stack back to the input stack. Then we push our element.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2) - We might shift N elements back and forth.
     * - Space Complexity: O(N) Heap Space for the auxiliary stack. O(1) Call Stack.
     */
    public static void sortStackBruteForce(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty()) return;

        Stack<Integer> auxStack = new Stack<>();

        while (!stack.isEmpty()) {
            int current = stack.pop();

            // While auxStack is not empty and top is greater than current element
            while (!auxStack.isEmpty() && auxStack.peek() > current) {
                stack.push(auxStack.pop());
            }
            auxStack.push(current);
        }

        // Move sorted elements back to original stack
        while (!auxStack.isEmpty()) {
            stack.push(auxStack.pop());
        }
    }


    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Executing Test Suite: Sort Stack Using Recursion\n");

        // Test Case 1: Standard Unsorted Stack
        runTest("Test Case 1: Standard Stack", new int[]{3, 1, 4, 2});

        // Test Case 2: Already Sorted Stack
        runTest("Test Case 2: Already Sorted", new int[]{1, 2, 3, 4, 5});

        // Test Case 3: Reverse Sorted Stack
        runTest("Test Case 3: Reverse Sorted", new int[]{9, 7, 5, 3, 1});

        // Test Case 4: Stack with Negatives and Duplicates
        runTest("Test Case 4: Negatives & Duplicates", new int[]{0, -5, 2, -5, 8, 2});

        // Test Case 5: Empty Stack (Edge Case)
        runTest("Test Case 5: Empty Stack", new int[]{});

        // Test Case 6: Single Element (Edge Case)
        runTest("Test Case 6: Single Element", new int[]{42});
    }

    /**
     * Helper method to initialize, sort, and print test cases cleanly.
     * Utilizes Java 8 Streams for elegant console output.
     */
    private static void runTest(String testName, int[] elements) {
        System.out.println("--- " + testName + " ---");

        Stack<Integer> stack = new Stack<>();
        for (int el : elements) {
            stack.push(el);
        }

        // Print initial state using Java 8 Stream API
        String initial = stack.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Initial State (Bottom -> Top): " + initial);

        // Execute optimal sorting algorithm
        sortStackOptimal(stack);

        // Print final state using Java 8 Stream API
        String sorted = stack.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Sorted State  (Bottom -> Top): " + sorted);
        System.out.println();
    }
}