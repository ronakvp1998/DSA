package strivers.stackandqueues.monotonicstackqueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * MASTERCLASS SOLUTION: CAR FLEET
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem: 853. Car Fleet (Medium)
 * * There are n cars at given miles away from the starting mile 0, traveling to
 * reach the mile target.
 * You are given two integer arrays position and speed, both of length n, where
 * position[i] is the starting mile of the ith car and speed[i] is the speed of
 * the ith car in miles per hour.
 * * A car cannot pass another car, but it can catch up and then travel next to
 * it at the speed of the slower car.
 * A car fleet is a single car or a group of cars driving next to each other.
 * The speed of the car fleet is the minimum speed of any car in the fleet.
 * If a car catches up to a car fleet at the mile target, it will still be
 * considered as part of the car fleet.
 * * Return the number of car fleets that will arrive at the destination.
 * * Constraints:
 * - n == position.length == speed.length
 * - 1 <= n <= 10^5
 * - 0 < target <= 10^6
 * - 0 <= position[i] < target
 * - All the values of position are unique.
 * - 0 < speed[i] <= 10^6
 * * Example 1:
 * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 * Output: 3
 * Explanation:
 * - Cars at 10 (speed 2) and 8 (speed 4) become a fleet, meeting at 12.
 * - Car at 0 (speed 1) does not catch up, fleet by itself.
 * - Cars at 5 (speed 1) and 3 (speed 3) become a fleet, meeting at 6.
 * * Example 2:
 * Input: target = 10, position = [3], speed = [3]
 * Output: 1
 * * Example 3:
 * Input: target = 100, position = [0,2,4], speed = [4,2,1]
 * Output: 1
 * * * ----------------------------------------------------------------------------
 * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ----------------------------------------------------------------------------
 * - Phase 1: Optimal Approach - Array Iteration with Time Tracking.
 * - Phase 2: Brute Force Approach - N^2 Collision Checking (Conceptual).
 * - Phase 3: Alternative Approaches - Monotonic Stack.
 * ============================================================================
 */
public class CarFleet {

    /**
     * ----------------------------------------------------------------------------
     * Phase 1: Optimal Approach (Array Iteration with Time Tracking)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * To determine if a car will catch up to another, we must evaluate the time
     * it takes for each car to reach the target independently.
     * Formula: Time = (target - position) / speed.
     * * If we sort the cars by their starting position in descending order (closest
     * to the target first), we can iterate from the front of the pack to the back.
     * A car driving behind another can ONLY join its fleet if its independent
     * arrival time is LESS THAN OR EQUAL TO the arrival time of the fleet ahead
     * of it. If it takes longer, it will never catch up and thus forms a new
     * fleet of its own. We track the `maxTime` (slowest fleet ahead) and
     * increment our fleet counter whenever a car's time exceeds `maxTime`.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N) dominated by the sorting step. The iteration
     * through the array is O(N).
     * - Space Complexity: O(N) Heap Space to store the paired position and time
     * data for sorting. O(log N) Auxiliary Stack Space for the sorting algorithm
     * (Dual-Pivot Quicksort / TimSort).
     * ----------------------------------------------------------------------------
     */
    public int carFleetPhase1(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;

        // Array to hold position and time pairs
        // cars[i][0] = position, cars[i][1] = time to target
        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = (double) (target - position[i]) / speed[i];
        }

        // Sort descending based on position (closest to target first)
        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));

        int fleets = 0;
        double maxTime = 0.0; // Tracks the time of the slowest car ahead

        for (int i = 0; i < n; i++) {
            // If the current car takes strictly longer than the fleet ahead of it,
            // it cannot catch up. It forms a new fleet, and dictates the new pace.
            if (cars[i][1] > maxTime) {
                fleets++;
                maxTime = cars[i][1];
            }
        }

        return fleets;
    }

    /**
     * ----------------------------------------------------------------------------
     * Phase 2: Brute Force Approach ("Think it" stage)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * In the "Think it" stage, one might attempt to physically simulate the cars
     * moving step-by-step or explicitly checking every car behind against every
     * car ahead to calculate exact collision points.
     * We pair them up, sort them, and for every car, we look at ALL cars ahead
     * to see if they collide before the target. While mathematically sound, this
     * requires nested loops and constantly updating the speeds of cars as they
     * merge into fleets.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2) worst-case. For every car, checking all cars
     * ahead to recalculate collision scenarios. With N = 10^5, this leads to
     * Time Limit Exceeded (TLE).
     * - Space Complexity: O(N) Heap Space for pairs, O(1) Auxiliary Space.
     * * NOTE: This implementation is kept brief to demonstrate the naive logic
     * before arriving at the linear scan realization in Phase 1.
     * ----------------------------------------------------------------------------
     */
    public int carFleetPhase2(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;

        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = (double) (target - position[i]) / speed[i];
        }
        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));

        int fleets = 0;
        // O(N^2) nested loop checking if car i merges with any fleet j ahead
        for (int i = 0; i < n; i++) {
            boolean catchesUp = false;
            for (int j = 0; j < i; j++) {
                // If it takes less or equal time than a car ahead of it, it merges
                if (cars[i][1] <= cars[j][1]) {
                    catchesUp = true;
                    // It assumes the time of the fleet it caught up to
                    cars[i][1] = cars[j][1];
                    break;
                }
            }
            if (!catchesUp) {
                fleets++;
            }
        }
        return fleets;
    }

    /**
     * ----------------------------------------------------------------------------
     * Phase 3: Alternative Approach (Monotonic Stack)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * This problem is fundamentally a Monotonic Stack problem. As we iterate from
     * the car closest to the target down to the furthest, we push their arrival
     * times onto a stack.
     * If the stack has at least two elements and the top element (car further back)
     * has a time LESS THAN OR EQUAL TO the element just below it (car ahead),
     * it means the car further back will crash into the car ahead. We pop the
     * top element because it merges into the slower fleet ahead of it.
     * The size of the stack at the end represents the number of distinct fleets.
     * * Complexity Analysis:
     * - Time Complexity: O(N log N) for sorting. The stack operations take O(N)
     * time since each element is pushed and popped at most once.
     * - Space Complexity: O(N) Auxiliary Stack Space for the Monotonic Stack,
     * plus O(N) Heap Space to store the initial positional pairs.
     * ----------------------------------------------------------------------------
     */
    public int carFleetPhase3(int target, int[] position, int[] speed) {
        int n = position.length;
        if (n == 0) return 0;

        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = (double) (target - position[i]) / speed[i];
        }

        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0])); // descending

        Deque<Double> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            stack.push(cars[i][1]);

            // If the car we just pushed takes less/equal time to arrive than the
            // car ahead of it (which is at stack.peek() if we pop), they merge.
            if (stack.size() >= 2) {
                double carBehind = stack.pop();
                double carAhead = stack.peek();

                if (carBehind <= carAhead) {
                    // They merge. The fleet's time is dictated by the slower car
                    // ahead, which is already on the stack. We just discard carBehind.
                } else {
                    // They don't merge. Put it back.
                    stack.push(carBehind);
                }
            }
        }

        return stack.size();
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        CarFleet solver = new CarFleet();

        // Define Test Cases
        List<TestCase> testCases = Arrays.asList(
                new TestCase(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}, 3, "Example 1"),
                new TestCase(10, new int[]{3}, new int[]{3}, 1, "Example 2: Single Car"),
                new TestCase(100, new int[]{0, 2, 4}, new int[]{4, 2, 1}, 1, "Example 3: Cascade Merge"),
                new TestCase(10, new int[]{6, 8}, new int[]{3, 2}, 2, "Edge Case: Never Catch Up"),
                new TestCase(10, new int[]{8, 6}, new int[]{2, 3}, 1, "Edge Case: Catch Up Immediately"),
                new TestCase(10, new int[]{}, new int[]{}, 0, "Edge Case: Zero Cars")
        );

        System.out.println("--- Starting Evaluation Test Suite ---");

        // Method references to test
        List<MethodReference> methods = Arrays.asList(
                new MethodReference("Phase 1: Optimal Array Iteration", solver::carFleetPhase1),
                new MethodReference("Phase 2: Brute Force N^2 Merge", solver::carFleetPhase2),
                new MethodReference("Phase 3: Monotonic Stack", solver::carFleetPhase3)
        );

        // Java 8 Streams execution
        methods.forEach(method -> {
            System.out.println("\nExecuting " + method.name + "...");
            testCases.forEach(test -> {
                int result = method.function.apply(test);
                boolean passed = result == test.expectedOutput;
                System.out.printf("[%s] %s | Expected: %d | Got: %d%n",
                        passed ? "PASS" : "FAIL", test.description, test.expectedOutput, result);
            });
        });
    }

    // Helper classes for clean testing structure
    static class TestCase {
        int target;
        int[] position;
        int[] speed;
        int expectedOutput;
        String description;

        TestCase(int target, int[] position, int[] speed, int expectedOutput, String description) {
            this.target = target;
            this.position = position;
            this.speed = speed;
            this.expectedOutput = expectedOutput;
            this.description = description;
        }
    }

    // Wrapper to handle multiple arguments in the function reference
    static class MethodReference {
        String name;
        Function<TestCase, Integer> function;

        MethodReference(String name, TriFunction<Integer, int[], int[], Integer> func) {
            this.name = name;
            this.function = (test) -> func.apply(test.target, test.position, test.speed);
        }
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }
}