package strivers.stackandqueues.monotonicstackqueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

/**
 * ============================================================================
 * MASTERCLASS SOLUTION: DAILY TEMPERATURES
 * ============================================================================
 * * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 * * * 1. HEADER & PROBLEM CONTEXT
 * ----------------------------------------------------------------------------
 * Problem: 739. Daily Temperatures (Medium)
 * * Given an array of integers temperatures represents the daily temperatures,
 * return an array answer such that answer[i] is the number of days you have to
 * wait after the ith day to get a warmer temperature. If there is no future day
 * for which this is possible, keep answer[i] == 0 instead.
 * * Constraints:
 * - 1 <= temperatures.length <= 10^5
 * - 30 <= temperatures[i] <= 100
 * * Example 1:
 * Input: temperatures = [73,74,75,71,69,72,76,73]
 * Output: [1,1,4,2,1,1,0,0]
 * * Example 2:
 * Input: temperatures = [30,40,50,60]
 * Output: [1,1,1,0]
 * * Example 3:
 * Input: temperatures = [30,60,90]
 * Output: [1,1,0]
 * * * ----------------------------------------------------------------------------
 * 2.2 PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP)
 * ----------------------------------------------------------------------------
 * This problem asks for the "Next Greater Element" for each item in an array.
 * - Phase 1: Optimal Approach - Monotonic Decreasing Stack.
 * - Phase 2: Brute Force Approach - Nested loops checking every future day.
 * - Phase 3: Alternative Approach - Right-to-Left Array Jump Optimization
 * (Best space complexity).
 * ============================================================================
 */
public class DailyTemperatures {

    /**
     * ----------------------------------------------------------------------------
     * Phase 1: Optimal Approach (Monotonic Decreasing Stack)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * We want to find the number of days until a warmer temperature. Instead of
     * looking forward from each element (which causes repetitive work), we can
     * use a Monotonic Decreasing Stack to remember the days (indices) we haven't
     * found a warmer temperature for yet.
     * * As we iterate through the temperatures:
     * 1. We check if the current temperature is warmer than the temperature of the
     * day stored at the top of the stack.
     * 2. If it is, we've found the "next warmer day" for that top element! We pop
     * it off the stack and calculate the difference in indices (current day -
     * popped day). We do this in a while loop to resolve as many past days
     * as possible.
     * 3. Finally, we push the current day's index onto the stack to find its
     * future warmer day.
     * * Complexity Analysis:
     * - Time Complexity: O(N), where N is the number of temperatures. Although
     * there is a nested while loop, every index is pushed onto the stack exactly
     * once and popped exactly once. Thus, the inner loop runs at most N times
     * across the entire execution.
     * - Space Complexity: O(N) Auxiliary Stack Space. In the worst-case scenario
     * (temperatures are strictly decreasing, e.g., [100, 90, 80, 70]), the
     * stack will store all N indices. The output array uses O(N) Heap Space
     * (usually not counted against auxiliary space complexity).
     * ----------------------------------------------------------------------------
     */
    public int[] dailyTemperaturesPhase1(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];

        // Stack stores indices, not the actual temperatures.
        // ArrayDeque is preferred over Stack/LinkedList for speed.
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            int currentTemp = temperatures[i];

            // Resolve days waiting for a warmer temperature
            while (!stack.isEmpty() && temperatures[stack.peek()] < currentTemp) {
                int prevDayIndex = stack.pop();
                result[prevDayIndex] = i - prevDayIndex;
            }

            // Push current day onto the stack
            stack.push(i);
        }

        return result;
    }

    /**
     * ----------------------------------------------------------------------------
     * Phase 2: Brute Force Approach ("Think it" stage)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * The most straightforward way to solve this is to simulate the exact wording
     * of the problem. For every day, we start looking at the next day, and the
     * day after that, until we find a temperature strictly greater than the
     * current day's temperature.
     * * Complexity Analysis:
     * - Time Complexity: O(N^2). For each element, we potentially scan the rest
     * of the array. Worst case: decreasing array where we never find a warmer
     * day, causing roughly (N * (N-1)) / 2 operations. Given N <= 10^5, this
     * will result in Time Limit Exceeded (TLE) on most platforms.
     * - Space Complexity: O(1) Auxiliary Space. We only use primitive variables
     * for iteration. (Output array requires O(N) Heap Space).
     * ----------------------------------------------------------------------------
     */
    public int[] dailyTemperaturesPhase2(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * ----------------------------------------------------------------------------
     * Phase 3: Alternative Approach (Right-to-Left Array Jump Optimization)
     * ----------------------------------------------------------------------------
     * Detailed Intuition:
     * We can achieve O(1) auxiliary space by processing the array from right to
     * left and using the `result` array itself to skip days.
     * * For a given day `i`, we look at the next day `j = i + 1`.
     * - If `temperatures[j] > temperatures[i]`, we found it immediately: ans is 1.
     * - If `temperatures[j] <= temperatures[i]`, we know day `j` is NOT the answer.
     * BUT, we already calculated the answer for day `j`! Instead of checking
     * `j + 1` linearly, we can instantly "jump" to the next warmer day for `j`,
     * which is `j + result[j]`. We keep jumping until we find a warmer day or
     * reach the end (where result[j] == 0).
     * * Complexity Analysis:
     * - Time Complexity: O(N). Each element is visited but jumps allow us to skip
     * redundant checks. The amortized cost per element is constant.
     * - Space Complexity: O(1) Auxiliary Space. We completely bypass the need for
     * an external Stack, utilizing only pointers. The required return array is
     * not counted towards auxiliary space.
     * ----------------------------------------------------------------------------
     */
    public int[] dailyTemperaturesPhase3(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];

        // Start from the second to last element and go backwards
        for (int i = n - 2; i >= 0; i--) {
            int currentTemp = temperatures[i];
            int j = i + 1;

            while (true) {
                if (temperatures[j] > currentTemp) {
                    result[i] = j - i;
                    break;
                } else if (result[j] == 0) {
                    // We jumped to a day that has no warmer future day.
                    // Because currentTemp >= temperatures[j], currentTemp ALSO
                    // has no warmer future day in that direction.
                    result[i] = 0;
                    break;
                } else {
                    // Jump to the next known warmer day for j
                    j += result[j];
                }
            }
        }

        return result;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        DailyTemperatures solver = new DailyTemperatures();

        // Define Test Cases
        List<TestCase> testCases = Arrays.asList(
                new TestCase(
                        new int[]{73, 74, 75, 71, 69, 72, 76, 73},
                        new int[]{1, 1, 4, 2, 1, 1, 0, 0},
                        "Example 1"
                ),
                new TestCase(
                        new int[]{30, 40, 50, 60},
                        new int[]{1, 1, 1, 0},
                        "Example 2 (Strictly Increasing)"
                ),
                new TestCase(
                        new int[]{30, 60, 90},
                        new int[]{1, 1, 0},
                        "Example 3"
                ),
                new TestCase(
                        new int[]{90, 80, 70, 60},
                        new int[]{0, 0, 0, 0},
                        "Edge Case: Strictly Decreasing"
                ),
                new TestCase(
                        new int[]{50, 50, 50},
                        new int[]{0, 0, 0},
                        "Edge Case: All Identical"
                )
        );

        System.out.println("--- Starting Evaluation Test Suite ---");

        // Method references to test
        List<MethodReference> methods = Arrays.asList(
                new MethodReference("Phase 1: Monotonic Stack", solver::dailyTemperaturesPhase1),
                new MethodReference("Phase 2: Brute Force", solver::dailyTemperaturesPhase2),
                new MethodReference("Phase 3: Right-to-Left Array Jump", solver::dailyTemperaturesPhase3)
        );

        // Java 8 Streams execution
        methods.forEach(method -> {
            System.out.println("\nExecuting " + method.name + "...");
            testCases.stream().forEach(test -> {
                int[] result = method.function.apply(test.temperatures);
                boolean passed = Arrays.equals(result, test.expectedOutput);

                System.out.printf("[%s] %s | Expected: %s | Got: %s%n",
                        passed ? "PASS" : "FAIL",
                        test.description,
                        Arrays.toString(test.expectedOutput),
                        Arrays.toString(result));
            });
        });
    }

    // Helper classes for clean testing
    static class TestCase {
        int[] temperatures;
        int[] expectedOutput;
        String description;

        TestCase(int[] temperatures, int[] expectedOutput, String description) {
            this.temperatures = temperatures;
            this.expectedOutput = expectedOutput;
            this.description = description;
        }
    }

    static class MethodReference {
        String name;
        Function<int[], int[]> function;

        MethodReference(String name, Function<int[], int[]> function) {
            this.name = name;
            this.function = function;
        }
    }
}