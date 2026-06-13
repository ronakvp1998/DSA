package strivers.stackandqueues.learning;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 155. Min Stack
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Design a stack that supports push, pop, top, and retrieving the minimum
 * element in constant time.
 * * Implement the MinStack class:
 * - MinStack() initializes the stack object.
 * - void push(int value) pushes the element value onto the stack.
 * - void pop() removes the element on the top of the stack.
 * - int top() gets the top element of the stack.
 * - int getMin() retrieves the minimum element in the stack.
 * * You must implement a solution with O(1) time complexity for each function.
 *
 * Example 1:
 * Input
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * Output
 * [null,null,null,null,-3,null,0,-2]
 * * Explanation:
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // return -3
 * minStack.pop();
 * minStack.top();    // return 0
 * minStack.getMin(); // return -2
 *
 * CONSTRAINTS:
 * -2^31 <= val <= 2^31 - 1
 * Methods pop, top and getMin operations will always be called on non-empty stacks.
 * At most 3 * 10^4 calls will be made to push, pop, top, and getMin.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Two Stacks (Data Stack + Min Stack)
 * Phase 2: Brute Force Approach - Single Stack + O(N) Search for Min
 * Phase 3: Alternative Approach - Single Stack with Mathematical Encoding (O(1) Space)
 * ============================================================================
 */
public class MinStackMasterclass {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Two Stacks)
     * ============================================================================
     * Detailed Intuition:
     * To retrieve the minimum element in O(1) time, we need to memorize the minimum
     * state at every level of the stack. A standard stack natively keeps track of
     * historical states via its LIFO nature. We can maintain a secondary stack
     * (`minStack`) specifically to store the minimum values.
     * - When we push a value `x`, we push it to the main stack. We also look at the
     * top of `minStack`. If `minStack` is empty or `x` is less than or equal to
     * the current minimum, we push `x` onto `minStack`.
     * - When we pop a value, we check if the popped value from the main stack equals
     * the top of `minStack`. If it does, it means the minimum value is being removed,
     * so we must pop it from `minStack` as well.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for push(), pop(), top(), and getMin().
     * - Space Complexity: O(N) auxiliary space. In the worst case (a descending
     * sequence of pushed numbers), the `minStack` will hold N elements.
     * ============================================================================
     */
    public static class MinStackOptimal {
        private final Deque<Integer> stack;
        private final Deque<Integer> minStack;

        public MinStackOptimal() {
            stack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }

        public void push(int val) {
            stack.push(val);
            // Push to minStack if it's the first element or a new/equal minimum
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            int popped = stack.pop();
            // If the popped element was the minimum, remove it from minStack too
            // Note: Use .equals() instead of == for Object comparison in Java
            // to avoid [-128, 127] Integer caching bugs
            if (minStack.peek().equals(popped)) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Single Stack + Search) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If we only maintain a standard stack, push, pop, and top remain strictly O(1).
     * However, to find the minimum, we have no historical cache. We must look at
     * every single element currently inside the stack to find the absolute minimum.
     * While this severely violates the O(1) constraint for `getMin()`, it serves as
     * the baseline mental model.
     * * Here we leverage the Java 8 Stream API to cleanly search the underlying
     * collection for the minimum value.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for push, pop, top. O(N) for getMin(), as it iterates
     * through the entire stack.
     * - Space Complexity: O(N) heap space to store the stack elements. O(1) auxiliary space.
     * ============================================================================
     */
    public static class MinStackBruteForce {
        private final Deque<Integer> stack;

        public MinStackBruteForce() {
            stack = new ArrayDeque<>();
        }

        public void push(int val) {
            stack.push(val);
        }

        public void pop() {
            stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            // Brute force O(N) scan using Streams
            return stack.stream()
                    .min(Integer::compareTo)
                    .orElseThrow(() -> new RuntimeException("Stack is empty"));
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Mathematical Encoding) -> "Perfect it"
     * ============================================================================
     * Detailed Intuition:
     * Can we achieve O(1) time and STRICTLY O(1) extra space (using only ONE stack)?
     * Yes, through mathematical encoding, but it requires careful long casting to
     * prevent integer overflow.
     * - We maintain a `min` variable.
     * - When pushing a new minimum `val`, instead of storing `val`, we store a
     * flag value: `(2 * val) - min`. Because `val < min`, this flag will ALWAYS
     * be less than `val`. We then update `min = val`.
     * - When we `top()`, if the top value is less than `min` (identifying it as our flag),
     * we know the actual value is exactly `min`.
     * - When we `pop()`, if the popped flag is less than `min`, we must restore the
     * previous minimum using the inverse formula: `previous_min = (2 * min) - flag`.
     * This is a senior-level masterclass trick that proves deep algorithmic understanding.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for all operations.
     * - Space Complexity: O(N) overall structure space, but strictly O(1) auxiliary
     * space (no secondary stack or node wrappers needed).
     * ============================================================================
     */
    public static class MinStackSpaceOptimized {
        // Store Long to prevent overflow when performing (2 * val) - min
        private final Deque<Long> stack;
        private long currentMin;

        public MinStackSpaceOptimized() {
            stack = new ArrayDeque<>();
        }

        public void push(int val) {
            long valLong = (long) val;
            if (stack.isEmpty()) {
                currentMin = valLong;
                stack.push(valLong);
            } else if (valLong < currentMin) {
                // Encode the new minimum
                stack.push(2L * valLong - currentMin);
                currentMin = valLong;
            } else {
                stack.push(valLong);
            }
        }

        public void pop() {
            long popped = stack.pop();
            if (popped < currentMin) {
                // We are popping the current minimum, so we must restore the previous minimum
                currentMin = 2L * currentMin - popped;
            }
        }

        public int top() {
            long topValue = stack.peek();
            if (topValue < currentMin) {
                // If top is encoded, the actual value is the current minimum
                return (int) currentMin;
            }
            return (int) topValue;
        }

        public int getMin() {
            return (int) currentMin;
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Standard Optimal (Two Stacks) ===");
        MinStackOptimal minStack1 = new MinStackOptimal();
        minStack1.push(-2);
        minStack1.push(0);
        minStack1.push(-3);
        System.out.println("getMin() expected -3 -> Actual: " + minStack1.getMin());
        minStack1.pop();
        System.out.println("top() expected 0 -> Actual: " + minStack1.top());
        System.out.println("getMin() expected -2 -> Actual: " + minStack1.getMin());

        System.out.println("\n=== Test Case 2: Brute Force (Stream O(N)) ===");
        MinStackBruteForce minStack2 = new MinStackBruteForce();
        minStack2.push(-2);
        minStack2.push(0);
        minStack2.push(-3);
        System.out.println("getMin() expected -3 -> Actual: " + minStack2.getMin());
        minStack2.pop();
        System.out.println("top() expected 0 -> Actual: " + minStack2.top());
        System.out.println("getMin() expected -2 -> Actual: " + minStack2.getMin());

        System.out.println("\n=== Test Case 3: Space Optimized (Mathematical Encoding) ===");
        MinStackSpaceOptimized minStack3 = new MinStackSpaceOptimized();
        minStack3.push(-2);
        minStack3.push(0);
        minStack3.push(-3);
        System.out.println("getMin() expected -3 -> Actual: " + minStack3.getMin());
        minStack3.pop();
        System.out.println("top() expected 0 -> Actual: " + minStack3.top());
        System.out.println("getMin() expected -2 -> Actual: " + minStack3.getMin());

        System.out.println("\n=== Test Case 4: Extreme Values (Integer Overflow Edge Case) ===");
        MinStackSpaceOptimized minStack4 = new MinStackSpaceOptimized();
        minStack4.push(Integer.MAX_VALUE);
        minStack4.push(Integer.MIN_VALUE);
        System.out.println("getMin() expected " + Integer.MIN_VALUE + " -> Actual: " + minStack4.getMin());
        minStack4.pop();
        System.out.println("getMin() expected " + Integer.MAX_VALUE + " -> Actual: " + minStack4.getMin());
    }
}