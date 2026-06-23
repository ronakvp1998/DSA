package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 735. Asteroid Collision
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * We are given an array asteroids of integers representing asteroids in a row.
 * The indices of the asteroid in the array represent their relative position in space.
 * For each asteroid, the absolute value represents its size, and the sign represents
 * its direction (positive meaning right, negative meaning left). Each asteroid
 * moves at the same speed.
 * * Find out the state of the asteroids after all collisions. If two asteroids meet,
 * the smaller one will explode. If both are the same size, both will explode.
 * Two asteroids moving in the same direction will never meet.
 *
 * Example 1:
 * Input: asteroids = [5,10,-5]
 * Output: [5,10]
 * Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.
 *
 * Example 2:
 * Input: asteroids = [8,-8]
 * Output: []
 * Explanation: The 8 and -8 collide exploding each other.
 *
 * Example 3:
 * Input: asteroids = [10,2,-5]
 * Output: [10]
 * Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.
 *
 * Example 4:
 * Input: asteroids = [3,5,-6,2,-1,4]
 * Output: [-6,2,4]
 * Explanation: The asteroid -6 makes the asteroid 3 and 5 explode, and then continues
 * going left. On the other side, the asteroid 2 makes the asteroid -1 explode and
 * then continues going right, without reaching asteroid 4.
 *
 * CONSTRAINTS:
 * 2 <= asteroids.length <= 10^4
 * -1000 <= asteroids[i] <= 1000
 * asteroids[i] != 0
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Stack Simulation (Deque)
 * Phase 2: Brute Force Approach - Iterative Array Reduction
 * Phase 3: Alternative Approach - In-Place Two Pointers (O(1) Auxiliary Space)
 * ============================================================================
 */
public class AsteroidCollision {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Stack Simulation)
     * ============================================================================
     * Detailed Intuition:
     * This problem is a classic stack simulation. We process asteroids from left
     * to right. A collision *only* occurs under one specific condition: the asteroid
     * on top of the stack is moving RIGHT (positive), and the incoming asteroid is
     * moving LEFT (negative).
     * If the stack is empty, or the top asteroid is moving left, or the incoming
     * asteroid is moving right, they will never collide, so we push it.
     * When a collision occurs, we evaluate their absolute sizes:
     * 1. Incoming is larger: Destroy the top of the stack (pop) and re-evaluate
     * the incoming asteroid against the new top.
     * 2. They are equal: Destroy both (pop the stack and discard incoming).
     * 3. Stack top is larger: Destroy incoming (discard it).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of asteroids. Each asteroid
     * is pushed onto the stack at most once and popped at most once.
     * - Space Complexity: O(N) auxiliary heap space for the Deque to store surviving
     * asteroids.
     * ============================================================================
     */
    public int[] asteroidCollisionOptimal(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (int ast : asteroids) {
            boolean destroyed = false;

            // Collision condition: Top of stack is moving right (>0) AND incoming is moving left (<0)
            while (!stack.isEmpty() && stack.peek() > 0 && ast < 0) {
                int topSize = stack.peek();
                int incomingSize = Math.abs(ast);

                if (topSize < incomingSize) {
                    // Top asteroid explodes, incoming continues to evaluate against next stack element
                    stack.pop();
                    continue;
                } else if (topSize == incomingSize) {
                    // Both explode
                    stack.pop();
                }

                // If topSize >= incomingSize, the incoming asteroid is destroyed
                destroyed = true;
                break;
            }

            if (!destroyed) {
                stack.push(ast);
            }
        }

        // Deque iterator returns elements from top to bottom, but we need
        // bottom to top to preserve original relative order.
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Iterative Array Reduction) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * A literal simulation of the problem without a stack. We scan the array
     * repeatedly. If we find an adjacent pair where the left is positive and the
     * right is negative, a collision happens. We resolve the collision, modify
     * the list, and start the scan over again. We keep doing this until a full
     * scan passes with zero collisions.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N^2). In the worst case (e.g., [10, 9, 8, 7, -100]),
     * a single large negative asteroid sweeps the array from right to left, requiring
     * N linear passes.
     * - Space Complexity: O(N) heap space to maintain the dynamic list of survivors.
     * ============================================================================
     */
    public int[] asteroidCollisionBruteForce(int[] asteroids) {
        List<Integer> list = Arrays.stream(asteroids).boxed().collect(Collectors.toList());
        boolean collisionHappened = true;

        while (collisionHappened) {
            collisionHappened = false;
            for (int i = 0; i < list.size() - 1; i++) {
                int left = list.get(i);
                int right = list.get(i + 1);

                // Check for collision
                if (left > 0 && right < 0) {
                    collisionHappened = true;
                    if (Math.abs(left) > Math.abs(right)) {
                        list.remove(i + 1); // Right explodes
                    } else if (Math.abs(left) < Math.abs(right)) {
                        list.remove(i);     // Left explodes
                    } else {
                        list.remove(i + 1); // Both explode
                        list.remove(i);
                    }
                    break; // Restart scan after list mutation
                }
            }
        }

        return list.stream().mapToInt(i -> i).toArray();
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (In-Place Two Pointers / Array Stack)
     * ============================================================================
     * Detailed Intuition:
     * System design constraints sometimes forbid allocating new objects like a Deque.
     * We can simulate the stack directly on the input array itself!
     * We use a `top` pointer to track the valid boundary of survivors.
     * - We iterate `i` through the array.
     * - `asteroids[top]` acts as `stack.peek()`.
     * - If a collision destroys the top, we simply decrement `top`.
     * - If the incoming asteroid survives, we increment `top` and write the
     * incoming asteroid to `asteroids[top]`.
     * This mutates the input array and avoids standard Collection overhead,
     * making it blazing fast with zero auxiliary memory footprint.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N). Single pass traversal, O(1) pointer updates.
     * - Space Complexity: O(1) auxiliary space. We mutate the input array and
     * only allocate memory for the final sized-down return array.
     * ============================================================================
     */
    public int[] asteroidCollisionInPlace(int[] asteroids) {
        int top = -1; // Represents the top of our "in-place stack"

        for (int i = 0; i < asteroids.length; i++) {
            int ast = asteroids[i];
            boolean destroyed = false;

            while (top >= 0 && asteroids[top] > 0 && ast < 0) {
                int topSize = asteroids[top];
                int incomingSize = Math.abs(ast);

                if (topSize < incomingSize) {
                    top--; // Top explodes, continue loop to test next asteroid
                    continue;
                } else if (topSize == incomingSize) {
                    top--; // Both explode
                }

                destroyed = true;
                break;
            }

            if (!destroyed) {
                top++;
                asteroids[top] = ast; // "Push" onto the in-place stack
            }
        }

        // The valid surviving asteroids are from index 0 to top.
        return Arrays.copyOfRange(asteroids, 0, top + 1);
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        AsteroidCollision solver = new AsteroidCollision();

        // Setup Test Cases
        int[][] testCases = {
                {5, 10, -5},               // Output: [5, 10]
                {8, -8},                   // Output: []
                {10, 2, -5},               // Output: [10]
                {3, 5, -6, 2, -1, 4},      // Output: [-6, 2, 4]
                {-2, -1, 1, 2},            // Output: [-2, -1, 1, 2] (Moving away, no collisions)
                {-2, -2, -2, 1},           // Output: [-2, -2, -2, 1] (No collisions)
                {10, 2, -5, -10}           // Output: [] (Chain reaction mutual destruction)
        };

        System.out.println("=== Asteroid Collision Testing Suite ===\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] original = testCases[i];

            // Clone to prevent in-place mutation from affecting other algorithms
            int[] optRes = solver.asteroidCollisionOptimal(original.clone());
            int[] bfRes = solver.asteroidCollisionBruteForce(original.clone());
            int[] inPlaceRes = solver.asteroidCollisionInPlace(original.clone());

            System.out.printf("Test Case %d: %s\n", i + 1, Arrays.toString(original));
            System.out.printf("  Optimal:  %s\n", Arrays.toString(optRes));
            System.out.printf("  Brute:    %s\n", Arrays.toString(bfRes));
            System.out.printf("  In-Place: %s\n", Arrays.toString(inPlaceRes));

            boolean match = Arrays.equals(optRes, bfRes) && Arrays.equals(optRes, inPlaceRes);
            System.out.printf("  Status:   %s\n\n", match ? "PASS \u2714" : "FAIL \u2718");
        }
    }
}
