package strivers.stackandqueues.implementationproblem;

import java.util.*;

/**
 * ============================================================================
 * 901. Online Stock Span
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Design an algorithm that collects daily price quotes for some stock and returns
 * the span of that stock's price for the current day.
 * * The span of the stock's price in one day is the maximum number of consecutive
 * days (starting from that day and going backward) for which the stock price
 * was less than or equal to the price of that day.
 * * For example, if the prices of the stock in the last four days is [7,2,1,2]
 * and the price of the stock today is 2, then the span of today is 4 because
 * starting from today, the price of the stock was less than or equal 2 for
 * 4 consecutive days.
 *
 * Example 1:
 * Input
 * ["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
 * [[], [100], [80], [60], [70], [60], [75], [85]]
 * Output
 * [null, 1, 1, 1, 2, 1, 4, 6]
 * Explanation:
 * StockSpanner stockSpanner = new StockSpanner();
 * stockSpanner.next(100); // return 1
 * stockSpanner.next(80);  // return 1
 * stockSpanner.next(60);  // return 1
 * stockSpanner.next(70);  // return 2
 * stockSpanner.next(60);  // return 1
 * stockSpanner.next(75);  // return 4, because the last 4 prices
 * // (including today's price of 75) were <= 75.
 * stockSpanner.next(85);  // return 6
 *
 * CONSTRAINTS:
 * 1 <= price <= 10^5
 * At most 10^4 calls will be made to next.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Note: Because this problem requires a stateful object, the implementations
 * are provided as inner static classes to represent the different design phases.
 * * Phase 1: Optimal Approach - Monotonic Decreasing Stack (Aggregating Spans)
 * Phase 2: Brute Force Approach - Dynamic List Traversal
 * Phase 3: Alternative Approach - Monotonic Stack (Index-based Tracking)
 * ============================================================================
 */
public class OnlineStockSpan {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Monotonic Decreasing Stack)
     * ============================================================================
     * Detailed Intuition:
     * This is an online algorithmic problem, meaning we don't have the full array
     * in advance. As prices stream in, we need to efficiently find the span.
     * We can use a strictly decreasing Monotonic Stack.
     * * Instead of just storing the price, we store a pair: `[price, current_span]`.
     * When a new `price` comes in, it inherently "hides" or "absorbs" all smaller
     * or equal prices that came immediately before it. Because those previous
     * prices are smaller, any future price that is greater than the current `price`
     * will also automatically be greater than all those absorbed prices.
     * * 1. Initialize the span for the current day as 1.
     * 2. While the stack is not empty and the top price is less than or equal to
     * the current price, we pop the stack and ADD the popped span to our current span.
     * 3. Push the new `[price, accumulated_span]` onto the stack.
     *
     * Complexity Analysis:
     * - Time Complexity: Amortized O(1) per `next()` call. Even though there is a
     * `while` loop, every price is pushed onto the stack exactly once and popped
     * at most once across the entire lifecycle of the object.
     * - Space Complexity: O(N) auxiliary heap space, where N is the number of
     * `next()` calls, to store the stack elements.
     * ============================================================================
     */
    public static class StockSpannerOptimal {
        // Stack stores integer arrays of size 2: {price, span}
        private final Deque<int[]> stack;

        public StockSpannerOptimal() {
            stack = new ArrayDeque<>();
        }

        public int next(int price) {
            int span = 1;

            // Absorb the spans of all previous days with a lesser or equal price
            while (!stack.isEmpty() && stack.peek()[0] <= price) {
                span += stack.pop()[1];
            }

            stack.push(new int[]{price, span});
            return span;
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Dynamic List Traversal) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * The most intuitive way to solve this is to simply store every single price
     * in a historical list as it arrives. Whenever a new price is queried via
     * `next()`, we iterate backward through our historical list and count how many
     * consecutive days the price was less than or equal to today's price.
     * We stop counting the moment we hit a price strictly greater than today's.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) per `next()` call. In the worst-case scenario
     * (a strictly increasing sequence of prices), we have to traverse the entire
     * history of length N every single time. Total time for N calls is O(N^2).
     * - Space Complexity: O(N) auxiliary heap space to store the price history.
     * ============================================================================
     */
    public static class StockSpannerBruteForce {
        private final List<Integer> prices;

        public StockSpannerBruteForce() {
            prices = new ArrayList<>();
        }

        public int next(int price) {
            prices.add(price);
            int span = 0;

            // Traverse backward to count consecutive days
            for (int i = prices.size() - 1; i >= 0; i--) {
                if (prices.get(i) <= price) {
                    span++;
                } else {
                    break;
                }
            }

            return span;
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Monotonic Stack - Index-based)
     * ============================================================================
     * Detailed Intuition:
     * Instead of aggregating spans like in Phase 1, we can simulate an array by
     * keeping track of the current "day index".
     * * We maintain a Monotonic Decreasing Stack that stores `[day_index, price]`.
     * To find the span of today's price, we pop all smaller or equal prices to
     * find the `day_index` of the Previous Greater Element (PGE).
     * The span is simply the mathematical difference: `current_day - PGE_day`.
     * * * Trick: If the stack becomes empty (meaning today's price is the highest
     * seen so far), the PGE is conceptually at day `-1`.
     *
     * Complexity Analysis:
     * - Time Complexity: Amortized O(1) per `next()` call.
     * - Space Complexity: O(N) auxiliary heap space for the Deque.
     * ============================================================================
     */
    public static class StockSpannerAlternative {
        // Stack stores integer arrays of size 2: {day_index, price}
        private final Deque<int[]> stack;
        private int currentDay;

        public StockSpannerAlternative() {
            stack = new ArrayDeque<>();
            currentDay = 0;
        }

        public int next(int price) {
            // Maintain decreasing monotonicity
            while (!stack.isEmpty() && stack.peek()[1] <= price) {
                stack.pop();
            }

            // If stack is empty, previous greater element is at conceptual index -1
            int previousGreaterDay = stack.isEmpty() ? -1 : stack.peek()[0];
            int span = currentDay - previousGreaterDay;

            // Record today's state
            stack.push(new int[]{currentDay, price});
            currentDay++;

            return span;
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
        System.out.println("=== Online Stock Span Testing Suite ===\n");

        int[] prices1 = {100, 80, 60, 70, 60, 75, 85};
        int[] expected1 = {1, 1, 1, 2, 1, 4, 6};

        runTest("Test Case 1: Standard LeetCode Example", prices1, expected1);

        int[] prices2 = {10, 10, 10, 10};
        int[] expected2 = {1, 2, 3, 4};

        runTest("Test Case 2: Identical Prices (Equal values absorbed)", prices2, expected2);

        int[] prices3 = {50, 40, 30, 20, 10};
        int[] expected3 = {1, 1, 1, 1, 1};

        runTest("Test Case 3: Strictly Decreasing Prices", prices3, expected3);

        int[] prices4 = {10, 20, 30, 40, 50};
        int[] expected4 = {1, 2, 3, 4, 5};

        runTest("Test Case 4: Strictly Increasing Prices", prices4, expected4);
    }

    private static void runTest(String testName, int[] prices, int[] expected) {
        StockSpannerOptimal optimal = new StockSpannerOptimal();
        StockSpannerBruteForce bruteForce = new StockSpannerBruteForce();
        StockSpannerAlternative alternative = new StockSpannerAlternative();

        boolean allMatch = true;
        System.out.println(testName);
        System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", "Price", "Expected", "Optimal", "Brute", "Alt");
        System.out.println("-----------------------------------------------------");

        for (int i = 0; i < prices.length; i++) {
            int p = prices[i];
            int exp = expected[i];
            int optRes = optimal.next(p);
            int bfRes = bruteForce.next(p);
            int altRes = alternative.next(p);

            if (optRes != exp || bfRes != exp || altRes != exp) {
                allMatch = false;
            }

            System.out.printf("%-10d %-10d %-10d %-10d %-10d\n", p, exp, optRes, bfRes, altRes);
        }

        System.out.printf("\nStatus: %s\n\n", allMatch ? "PASS \u2714" : "FAIL \u2718");
    }
}