package strivers.arrays.medium;

/**
 * ==================================================================================================
 * 1. HEADER & PROBLEM CONTEXT
 * ==================================================================================================
 * PROBLEM STATEMENT: 621. Task Scheduler
 * --------------------------------------------------------------------------------------------------
 * You are given an array of CPU tasks, each labeled with a letter from A to Z, and a number n.
 * Each CPU interval can be idle or allow the completion of one task. Tasks can be completed in
 * any order, but there's a constraint: there has to be a gap of at least n intervals between two
 * tasks with the same label.
 * * Return the minimum number of CPU intervals required to complete all tasks.
 * * EXAMPLES:
 * Example 1:
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * Output: 8
 * Explanation: A possible sequence is: A -> B -> idle -> A -> B -> idle -> A -> B.
 * * Example 2:
 * Input: tasks = ["A","C","A","B","D","B"], n = 1
 * Output: 6
 * Explanation: A possible sequence is: A -> B -> C -> D -> A -> B.
 * * Example 3:
 * Input: tasks = ["A","A","A", "B","B","B"], n = 3
 * Output: 10
 * Explanation: A possible sequence is: A -> B -> idle -> idle -> A -> B -> idle -> idle -> A -> B.
 * * CONSTRAINTS:
 * - 1 <= tasks.length <= 10^4
 * - tasks[i] is an uppercase English letter.
 * - 0 <= n <= 100
 * * NOTE: This is a Greedy/Scheduling problem, not a Dynamic Programming problem. Therefore,
 * we will follow the Non-DP Progressive Implementation Roadmap.
 * ==================================================================================================
 */

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskScheduler {

    /**
     * ==============================================================================================
     * Phase 1: Optimal Approach - Greedy / Math Formula (The Recommended Approach)
     * ==============================================================================================
     * Detailed Intuition:
     * The bottleneck in scheduling is the task with the highest frequency. Let's say task 'A'
     * appears most often (maxFreq times). We must schedule 'A', then wait 'n' cycles, then 'A'
     * again. This creates blocks or "chunks" of execution.
     * * We will have (maxFreq - 1) such chunks, and each chunk requires a length of (n + 1) intervals
     * to satisfy the cooldown. The very last chunk doesn't need trailing idle time; it just needs
     * to hold the tasks that tied for the maximum frequency.
     * * Formula: `intervals = (maxFreq - 1) * (n + 1) + maxCount`
     * * If the array has so many other distinct tasks that they fill all the idle slots and require
     * even more space, the total intervals will just be the length of the `tasks` array (no idle
     * time needed at all). Thus, the answer is `Math.max(tasks.length, calculated_intervals)`.
     * * Complexity Analysis:
     * - Time O(N): We iterate over the tasks array once to build the frequency map, and iterate
     * over an array of size 26 (constant) to find max frequency. Overall O(N).
     * - Space O(1): We use an array of size 26 to store frequencies. Auxiliary heap space is O(1).
     */
    public static int leastIntervalOptimal(char[] tasks, int n) {
        if (n == 0) return tasks.length;

        int[] frequencies = new int[26];
        int maxFreq = 0;

        // Count frequencies and find the highest frequency
        for (char task : tasks) {
            frequencies[task - 'A']++;
            maxFreq = Math.max(maxFreq, frequencies[task - 'A']);
        }

        // Count how many tasks share this maximum frequency
        int maxCount = 0;
        for (int freq : frequencies) {
            if (freq == maxFreq) {
                maxCount++;
            }
        }

        // Calculate intervals using the math formula
        int requiredIntervals = (maxFreq - 1) * (n + 1) + maxCount;

        // The answer is the maximum between the required intervals (with idles)
        // and the actual number of tasks (if no idles are needed).
        return Math.max(tasks.length, requiredIntervals);
    }

    /**
     * ==============================================================================================
     * Phase 2: Brute Force Approach - Priority Queue Simulation (The "Think it" stage)
     * ==============================================================================================
     * Detailed Intuition:
     * If we don't immediately see the math formula, we can simulate the CPU cycles step by step.
     * At any given time, we want to execute the available task that has the highest remaining count.
     * A Max Heap (PriorityQueue) is perfect for this.
     * * We process tasks in chunks of size `n + 1` (a full cooldown cycle). In each chunk, we pull
     * up to `n + 1` tasks from the Max Heap, execute them, decrement their counts, and hold them
     * in a temporary waitlist. Once the cycle finishes, we push the remaining tasks back into the
     * heap. If the heap is empty, the total time for that cycle is just the number of tasks we ran;
     * otherwise, it took the full `n + 1` cycles (including idles).
     * * Complexity Analysis:
     * - Time O(N * log(26)): In the worst case, we process tasks. Each heap operation takes O(log K)
     * where K is at most 26. Since 26 is a constant, this simplifies to O(N).
     * - Space O(1): The PriorityQueue and waitlist store at most 26 elements.
     */
    public static int leastIntervalSimulation(char[] tasks, int n) {
        if (n == 0) return tasks.length;

        int[] frequencies = new int[26];
        for (char task : tasks) {
            frequencies[task - 'A']++;
        }

        // Max Heap based on task frequency
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        for (int freq : frequencies) {
            if (freq > 0) {
                maxHeap.add(freq);
            }
        }

        int totalTime = 0;

        while (!maxHeap.isEmpty()) {
            List<Integer> waitlist = new ArrayList<>();
            int cycle = n + 1; // We can run n + 1 tasks before cooldown wears off for the first
            int tasksExecuted = 0;

            while (cycle > 0 && !maxHeap.isEmpty()) {
                int currentFreq = maxHeap.poll();
                if (currentFreq > 1) {
                    waitlist.add(currentFreq - 1);
                }
                tasksExecuted++;
                cycle--;
            }

            // Put tasks back into the heap for the next cycle
            waitlist.forEach(maxHeap::add);

            // If heap is empty, we don't add idle times at the end.
            if (maxHeap.isEmpty()) {
                totalTime += tasksExecuted;
            } else {
                // If not empty, we had to wait the full cycle time (including potential idles)
                totalTime += (n + 1);
            }
        }

        return totalTime;
    }

    /**
     * ==============================================================================================
     * Phase 3: Alternative Approach - Stream API + Math Formula
     * ==============================================================================================
     * Detailed Intuition:
     * This relies on the exact same mathematical logic as Phase 1, but utilizes Java 8 Streams to
     * elegantly compute the frequencies, identify the maximum frequency, and count ties. It serves
     * as an excellent demonstration of declarative programming and modern Java features, though it
     * introduces some boxing/unboxing overhead compared to primitive arrays.
     * * Complexity Analysis:
     * - Time O(N): Grouping and counting elements takes O(N) time.
     * - Space O(U): Where U is the number of unique elements (at most 26), so O(1) auxiliary space
     * for the Map, though with a higher constant factor than the primitive array approach.
     */
    public static int leastIntervalStreamAPI(char[] tasks, int n) {
        if (n == 0) return tasks.length;

        // Convert char[] to Character Stream, group by char, and count frequencies
        Map<Character, Long> freqMap = IntStream.range(0, tasks.length)
                .mapToObj(i -> tasks[i])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Find the maximum frequency
        long maxFreq = Collections.max(freqMap.values());

        // Count how many tasks have the maximum frequency
        long maxCount = freqMap.values().stream()
                .filter(count -> count == maxFreq)
                .count();

        // Apply formula
        int requiredIntervals = (int) ((maxFreq - 1) * (n + 1) + maxCount);

        return Math.max(tasks.length, requiredIntervals);
    }

    /**
     * ==============================================================================================
     * 4. TESTING SUITE
     * ==============================================================================================
     * Main method to thoroughly test all approaches against standard and edge cases.
     */
    public static void main(String[] args) {
        // Test Cases Setup
        class TestCase {
            char[] tasks;
            int n;
            String description;

            TestCase(char[] tasks, int n, String description) {
                this.tasks = tasks;
                this.n = n;
                this.description = description;
            }
        }

        TestCase[] testCases = {
                new TestCase(new char[]{'A','A','A','B','B','B'}, 2, "Standard Case 1"),
                new TestCase(new char[]{'A','C','A','B','D','B'}, 1, "Standard Case 2 (Short Cooldown)"),
                new TestCase(new char[]{'A','A','A','B','B','B'}, 3, "Standard Case 3 (Long Cooldown)"),
                new TestCase(new char[]{'A','B','C','D','E','F'}, 2, "Edge Case: All distinct tasks"),
                new TestCase(new char[]{'A','A','A','A'}, 0, "Edge Case: Zero Cooldown (n=0)"),
                new TestCase(new char[]{'A','A','A','A','B','C','D','E','F','G'}, 2, "Edge Case: No Idles Needed")
        };

        System.out.println("==========================================================");
        System.out.println("Running Testing Suite: 621. Task Scheduler");
        System.out.println("==========================================================");

        for (int i = 0; i < testCases.length; i++) {
            TestCase tc = testCases[i];
            System.out.println("\nTest Case " + (i + 1) + ": " + tc.description);
            System.out.println("Tasks: " + Arrays.toString(tc.tasks) + " | n = " + tc.n);

            // Execute all three phases
            int optimalResult = leastIntervalOptimal(tc.tasks, tc.n);
            int simResult     = leastIntervalSimulation(tc.tasks, tc.n);
            int streamResult  = leastIntervalStreamAPI(tc.tasks, tc.n);

            // Display Results
            System.out.println("  -> Phase 1 (Optimal Math)     : " + optimalResult);
            System.out.println("  -> Phase 2 (PQ Simulation)    : " + simResult);
            System.out.println("  -> Phase 3 (Stream API Math)  : " + streamResult);

            // Assert Correctness
            boolean isCorrect = (optimalResult == simResult) && (simResult == streamResult);
            System.out.println("  -> Consistency Check          : " + (isCorrect ? "PASS ✅" : "FAIL ❌"));
        }
    }
}